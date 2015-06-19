/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yoiojava.cache.driver;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.yoiojava.cache.CacheException;
import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.globle.utils.StringUtils;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class RedisDriver {
	private static Config conf = ConfigurationImpl.getInstance();
	private static Log log = LogFactory.getLog(RedisDriver.class);
	private static JedisPool pool = null;
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			pool = new JedisPool(config, (String) conf.C("CACHE_HOST"),
					(Integer) conf.C("CACHE_PORT"),
					(Integer) conf.C("CACHE_TIMEOUT"));

		} catch (Exception e) {
			throw new CacheException(
					"[YoioJava] cannot init redis pool. cause:", e);
		}
	}

	public synchronized static Jedis getJedis() {
		try {
			if (pool != null) {
				Jedis resource = pool.getResource();
				if (StringUtils.isNotNothing((String) conf.C("CACHE_PWD"))) {
					resource.auth((String) conf.C("CACHE_PWD"));
				}
				if (Constants.NULL.INTEGER_NULL != conf.C("CACHE_DATABASE")) {
					resource.select((Integer) conf.C("CACHE_DATABASE"));
				}
				log.debug("[YoioJava] RedisDriver provide a redis from pool "
						+ resource);
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new CacheException("[YoioJava] redis connat init. cause:", e);
		}
	}

	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			pool.returnResource(jedis);
			log.debug("[YoioJava] RedisDriver Recovery a redis to pool "
					+ jedis);
		}
	}

}
