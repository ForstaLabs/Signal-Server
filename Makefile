
default: build

ACCOUNT_DATABASE_URL ?= postgres://postgres:NOPE@localhost:5432/account
MESSAGE_DATABASE_URL ?= postgres://postgres:NOPE@localhost:5432/message

VERSION := 0.99.1
TARGET := target/TextSecureServer-$(VERSION).jar
CONFIG := config/seed.yml
JAVA := java $(JAVA_OPTS)

ifdef REDIS_URL
  REDIS_DIR_URL=$(REDIS_URL)/0
  REDIS_CACHE_URL=$(REDIS_URL)/0
endif

get_db_username = $(shell echo $(1) | awk -F'://' {'print $$2'} | awk -F':' {'print $$1'})
get_db_password = $(shell echo $(1) | awk -F'://' {'print $$2'} | awk -F':' {'print $$2'} | awk -F'@' {'print $$1'})
get_db_host = $(shell echo $(1) | awk -F'@' {'print $$2'})
ifset = $(if $(1),$(2)=$(1))

ARGS := \
		$(call ifset,$(TWILIO_ACCOUNTID),-Ddw.twilio.accountId) \
		$(call ifset,$(TWILIO_ACCOUNTTOKEN),-Ddw.twilio.accountToken) \
		$(call ifset,$(TWILIO_NUMBER),-Ddw.twilio.numbers[0]) \
		$(call ifset,$(TWILIO_LOCALDOMAIN),-Ddw.twilio.localDomain) \
		$(call ifset,$(PUSHSERVER_HOST),-Ddw.push.host) \
		$(call ifset,$(PUSHSERVER_PORT),-Ddw.push.port) \
		$(call ifset,$(PUSHSERVER_USERNAME),-Ddw.push.username) \
		$(call ifset,$(PUSHSERVER_PASSWORD),-Ddw.push.password) \
		$(call ifset,$(S3_ACCESSKEY),-Ddw.s3.accessKey) \
		$(call ifset,$(S3_ACCESSSECRET),-Ddw.s3.accessSecret) \
		$(call ifset,$(S3_ATTACHMENTSBUCKET),-Ddw.s3.attachmentsBucket) \
		$(call ifset,$(REDIS_DIR_URL),-Ddw.directory.url) \
		$(call ifset,$(REDIS_CACHE_URL),-Ddw.cache.url) \
		$(call ifset,$(PORT),-Ddw.server.applicationConnectors[0].port) \
		$(call ifset,$(ADMIN_PORT),-Ddw.server.adminConnectors[0].port) \
		-Ddw.database.url=jdbc:postgresql://$(call get_db_host,$(ACCOUNT_DATABASE_URL)) \
		-Ddw.database.user=$(call get_db_username,$(ACCOUNT_DATABASE_URL)) \
		-Ddw.database.password=$(call get_db_password,$(ACCOUNT_DATABASE_URL)) \
		-Ddw.messageStore.url=jdbc:postgresql://$(call get_db_host,$(MESSAGE_DATABASE_URL)) \
		-Ddw.messageStore.user=$(call get_db_username,$(MESSAGE_DATABASE_URL)) \
		-Ddw.messageStore.password=$(call get_db_password,$(MESSAGE_DATABASE_URL))

RUN := $(JAVA) $(ARGS) -jar $(TARGET)


build:
	mvn install

dbmigrate:
	$(RUN) accountdb migrate $(CONFIG)
	$(RUN) messagedb migrate $(CONFIG)

run: dbmigrate
	$(RUN) server $(CONFIG)
