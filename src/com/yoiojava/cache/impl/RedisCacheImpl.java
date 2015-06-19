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
package com.yoiojava.cache.impl;

import redis.clients.jedis.Jedis;

import com.yoiojava.cache.Cache;
import com.yoiojava.cache.driver.RedisDriver;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;
import com.yoiojava.util.SerializationUtils;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class RedisCacheImpl implements Cache {
	private static Log log = LogFactory.getLog(RedisCacheImpl.class);

	@Override
	public void putObject(String key, Object value) {
		Jedis jedis = RedisDriver.getJedis();
		if (jedis.exists(key.getBytes())) {
			jedis.del(key.getBytes());
		}

		jedis.set(key.getBytes(), SerializationUtils.objectToByte(value));
		if (log.isDebugEnabled()) {
			log.debug("[YoioJava]cache put a key:" + key);
		}
		RedisDriver.returnResource(jedis);
	}

	@Override
	public Object getObject(String key) {
		Jedis jedis = RedisDriver.getJedis();
		Object obj = SerializationUtils.byteToObject(jedis.get(key.getBytes()));
		RedisDriver.returnResource(jedis);
		if (log.isDebugEnabled()) {
			log.debug("[YoioJava]cache get a key:" + key);
		}
		return obj;
	}

	@Override
	public Object removeObject(String key) {
		Jedis jedis = RedisDriver.getJedis();
		Object obj = jedis.del(key.getBytes());
		RedisDriver.returnResource(jedis);
		if (log.isDebugEnabled()) {
			log.debug("[YoioJava]cache remove a key:" + key);
		}
		return obj;
	}

	@Override
	public void clear() {
		Jedis jedis = RedisDriver.getJedis();
		jedis.flushDB();
		if (log.isDebugEnabled()) {
			log.debug("[YoioJava]cache cleared");
		}
		RedisDriver.returnResource(jedis);
	}

	@Override
	public int getSize() {
		return 0;
	}

}
