default: build

PORT ?= 8080
ADMIN_PORT ?= 8181
DATABASE_URL ?= postgres://postgres@localhost:5432/tss

VERSION := 0.99.1
TARGET := target/TextSecureServer-$(VERSION).jar
CONFIG := config/default.yml
JAVA := java $(JAVA_OPTS)
DATABASE_USERNAME := $(shell echo $(DATABASE_URL) | awk -F'://' {'print $$2'} | awk -F':' {'print $$1'})
DATABASE_PASSWORD := $(shell echo $(DATABASE_URL) | awk -F'://' {'print $$2'} | awk -F':' {'print $$2'} | awk -F'@' {'print $$1'})
DATABASE_HOST := $(shell echo $(DATABASE_URL) | awk -F'@' {'print $$2'})
ARGS := \
		-Ddw.twilio.accountId=$(TWILIO_ACCOUNTID) \
		-Ddw.twilio.accountToken=$(TWILIO_ACCOUNTTOKEN) \
		-Ddw.twilio.numbers[0]=$(TWILIO_NUMBERS) \
		-Ddw.twilio.localDomain=$(TWILIO_LOCALDOMAIN) \
		-Ddw.push.host=$(PUSHSERVER_HOST) \
		-Ddw.push.port=$(PUSHSERVER_PORT) \
		-Ddw.push.username=$(PUSHSERVER_USERNAME) \
		-Ddw.push.password=$(PUSHSERVER_PASSWORD) \
		-Ddw.s3.accessKey=$(S3_ACCESSKEY) \
		-Ddw.s3.accessSecret=$(S3_SECRET) \
		-Ddw.s3.attachmentsBucket=$(S3_ATTACHMENTSBUCKET) \
		-Ddw.directory.url=$(REDIS_URL)/0 \
		-Ddw.cache.url=$(REDIS_URL)/1 \
		-Ddw.server.applicationConnectors[0].port=$(PORT) \
		-Ddw.server.adminConnectors[0].port=$(ADMIN_PORT) \
		-Ddw.database.user=$(DATABASE_USERNAME) \
		-Ddw.database.password=$(DATABASE_PASSWORD) \
		-Ddw.database.url=jdbc:postgresql://$(DATABASE_HOST) \
		-Ddw.messageStore.user=$(DATABASE_USERNAME) \
		-Ddw.messageStore.password=$(DATABASE_PASSWORD) \
		-Ddw.messageStore.url=jdbc:postgresql://$(DATABASE_HOST)

RUN := $(JAVA) $(ARGS) -jar $(TARGET)

build:
  mvn install

dbmigrate:
  $(RUN) accountdb migrate $(CONFIG)
  $(RUN) messagedb migrate $(CONFIG)

run: dbmigrate
  $(RUN) server $(CONFIG)