//-----------------------------------------------------------------------------
// <copyright file="ForstaConfiguration.java" company="Forsta">
// Copyright © 2017
// </copyright>
//-----------------------------------------------------------------------------
package org.whispersystems.textsecuregcm;

import org.whispersystems.textsecuregcm.configuration.FederationConfiguration;
import org.whispersystems.textsecuregcm.configuration.GraphiteConfiguration;
import org.whispersystems.textsecuregcm.configuration.PushConfiguration;
import org.whispersystems.textsecuregcm.configuration.RateLimitsConfiguration;
import org.whispersystems.textsecuregcm.configuration.RedPhoneConfiguration;
import org.whispersystems.textsecuregcm.configuration.RedisConfiguration;
import org.whispersystems.textsecuregcm.configuration.MessageStoreConfiguration;
import org.whispersystems.textsecuregcm.configuration.S3Configuration;
import org.whispersystems.textsecuregcm.configuration.TestDeviceConfiguration;
import org.whispersystems.textsecuregcm.configuration.TwilioConfiguration;
import org.whispersystems.textsecuregcm.configuration.WebsocketConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

/**
 * ---------------------------------------------------------------------------
 * 
 * This provides configuration customizations for Forsta. This server will
 * ultimately be deployed on Heroku, and thus will want to rely on variables.
 * configured in the environment.
 * 
 * ----------------------------------------------------------------------------
 */
public class ForstaConfiguration {
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the Push Server configuration.
     *
     * ------------------------------------------------------------------------
     */
    public static PushConfiguration getPushConfiguration() {

        String host      = System.getenv("PUSHSERVER_HOST");
        String port      = System.getenv("PUSHSERVER_PORT");
        String username  = System.getenv("PUSHSERVER_USERNAME");
        String password  = System.getenv("PUSHSERVER_PASSWORD");
        String queueSize = System.getenv("PUSHSERVER_QUEUESIZE");
        
        return new PushConfiguration(host, Integer.parseInt(port), username, password, Integer.parseInt(queueSize));
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the Twilio configuration.
     *
     * -----------------------------------------------------------------------
     */
    public static TwilioConfiguration getTwilioConfiguration() {

        String accountId           = System.getenv("TWILIO_ACCOUNTID");
        String accountToken        = System.getenv("TWILIO_ACCOUNTTOKEN");
        String localDomain         = System.getenv("TWILIO_LOCALDOMAIN");
        String messagingServicesId = System.getenv("TWILIO_MESSAGINGSERVICESID");
        String numbers             = System.getenv("TWILIO_NUMBERS");

        return new TwilioConfiguration(accountId, accountToken, localDomain, messagingServicesId, numbers);
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the Message store configuration.
     *
     * -----------------------------------------------------------------------
     */
    public static MessageStoreConfiguration getMessageStoreConfiguration() {

        String url = System.getenv("MESSAGESTORE_URL");

        return new MessageStoreConfiguration(url);
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the database configuration.
     *
     * -----------------------------------------------------------------------
     */
    public static DataSourceFactory getMessageDatabaseConfiguration() {

        String driverClass = System.getenv("MESSAGESTORE_DRIVERCLASS");
        String username    = System.getenv("MESSAGESTORE_USERNAME");
        String password    = System.getenv("MESSAGESTORE_PASSWORD");
        String url         = System.getenv("MESSAGESTORE_URL");

        DataSourceFactory factory = new DataSourceFactory();
        
        factory.setDriverClass(driverClass);
        factory.setUser(username);
        factory.setPassword(password);
        factory.setUrl(url);
        
        return factory;
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the database configuration.
     *
     * -----------------------------------------------------------------------
     */
    public static DataSourceFactory getAccountDatabaseConfiguration() {

        String driverClass = System.getenv("ACCOUNTSTORE_DRIVERCLASS");
        String username    = System.getenv("ACCOUNTSTORE_USERNAME");
        String password    = System.getenv("ACCOUNTSTORE_PASSWORD");
        String url         = System.getenv("ACCOUNTSTORE_URL");

        DataSourceFactory factory = new DataSourceFactory();
        
        factory.setDriverClass(driverClass);
        factory.setUser(username);
        factory.setPassword(password);
        factory.setUrl(url);
        
        return factory;
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the Redis cache configuration.
     *
     * -----------------------------------------------------------------------
     */
    public static RedisConfiguration getRedisCacheConfiguration() {

        String url = System.getenv("REDIS_CACHEURL");

        return new RedisConfiguration(url);
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the Redis directory configuration.
     *
     * -----------------------------------------------------------------------
     */
    public static RedisConfiguration getRedisDirectoryConfiguration() {

        String url = System.getenv("REDIS_DIRECTORYURL");

        return new RedisConfiguration(url);
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the S3 configuration.
     *
     * ------------------------------------------------------------------------
     */
    public static S3Configuration getS3Configuration() {

        String accessKey         = System.getenv("S3_ACCESSKEY");
        String accessSecret      = System.getenv("S3_ACCESSSECRET");
        String attachmentsBucket = System.getenv("S3_ATTACHMENTSBUCKET");

        return new S3Configuration(accessKey, accessSecret, attachmentsBucket);
    }
    
    /**
     * -----------------------------------------------------------------------
     * 
     * Return the Websocket configuration.
     *
     * ------------------------------------------------------------------------
     */
    public static WebsocketConfiguration getWebsocketConfiguration() {

        String enabled = System.getenv("WEBSOCKET_ENABLED");

        return new WebsocketConfiguration(Boolean.parseBoolean(enabled));
    }
}
