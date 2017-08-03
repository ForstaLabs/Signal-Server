/**
 * Copyright (C) 2013 Open WhisperSystems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.whispersystems.textsecuregcm.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whispersystems.dispatch.io.RedisPubSubConnectionFactory;
import org.whispersystems.dispatch.redis.PubSubConnection;
import org.whispersystems.textsecuregcm.util.Util;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisClientFactory implements RedisPubSubConnectionFactory {

  private final Logger logger = LoggerFactory.getLogger(RedisClientFactory.class);

  private final String    host;
  private final int       port;
  private final String    password;
  private final JedisPool jedisPool;

  public RedisClientFactory(String url) throws URISyntaxException {

    URI redisURI = new URI(url);

    this.host = redisURI.getHost();
    this.port = redisURI.getPort();
    
    String userInfo = redisURI.getUserInfo();
    if (userInfo != null) {  
        this.password = userInfo.split(":", 2)[1];  
    } else {
        this.password = null;
    }
    this.jedisPool = new JedisPool(url);
  }

  public JedisPool getRedisClientPool() {
    return jedisPool;
  }

  @Override
  public PubSubConnection connect() {
    while (true) {
      try {
        Socket socket = new Socket(host, port);
        return new PubSubConnection(socket, this.password);
      } catch (IOException e) {
        logger.warn("Error connecting", e);
        Util.sleep(1000);
      }
    }
  }
}
