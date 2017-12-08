TextSecure-Server
=================
Forsta fork of WhisperSystems' TextSecure-Server


Building
--------

    make


Running
--------

    make run



Configuration
--------

Configuration is achieved via setting env vars.

### Required

    ACCOUNT_DATABASE_URL
    MESSAGE_DATABASE_URL

    REDIS_URL

    PUSHSERVER_HOST
    PUSHSERVER_PORT
    PUSHSERVER_USERNAME
    PUSHSERVER_PASSWORD

    S3_ACCESSKEY
    S3_ACCESSSECRET
    S3_ATTACHMENTSBUCKET

### Optional

    PORT
    ADMIN_PORT
    CCSM_PARTNER_TOKEN


LICENSE
---------------------

Copyright 2013 Open Whisper Systems
Copyright 2017 Forsta Inc.

Licensed under the AGPLv3: https://www.gnu.org/licenses/agpl-3.0.html
