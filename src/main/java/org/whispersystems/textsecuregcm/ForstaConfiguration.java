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
     * Return the Websocket configuration.
     *
     * ------------------------------------------------------------------------
     */
    public static WebsocketConfiguration getWebsocketConfiguration() {

        String enabled = System.getenv("WEBSOCKET_ENABLED");

        return new WebsocketConfiguration(Boolean.parseBoolean(enabled));
    }
}
