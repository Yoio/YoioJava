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

import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

import com.yoiojava.cache.Cache;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class MemcachedCache implements Cache {
	private static Log logger = LogFactory.getLog(MemcachedCache.class);
	protected static final CacheService CACHE_SERVICE = createMemcachedService();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private String id;
	private LinkedList<String> cacheKeys = new LinkedList<String>();

	public MemcachedCache(String id) {
		this.id = id;
	} // 创建缓存服务类，基于java-memcached-client

	protected static CacheService createMemcachedService() {
		JMemcachedClientAdapter memcachedAdapter;

		try {
			memcachedAdapter = new JMemcachedClientAdapter();
		} catch (Exception e) {
			String msg = "Initial the JMmemcachedClientAdapter Error.";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		}
		return new MemcachedService(memcachedAdapter);
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * @param key
	 * @param value
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void putObject(Object key, Object value) {
		String cacheKey = String.valueOf(key.hashCode());

		if (!cacheKeys.contains(cacheKey)) {
			cacheKeys.add(cacheKey);
		}
		CACHE_SERVICE.put(cacheKey, value);
	}

	/**
	 * @param key
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Object getObject(Object key) {
		String cacheKey = String.valueOf(key.hashCode());

		cacheKeys.remove(cacheKey);
		return CACHE_SERVICE.delete(cacheKey);
	}

	/**
	 * @param key
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Object removeObject(Object key) {
		return null;
	}

	/**
	 * 
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void clear() {
		for (int i = 0; i < cacheKeys.size(); i++) {
			String cacheKey = cacheKeys.get(i);
			CACHE_SERVICE.delete(cacheKey);
		}
		cacheKeys.clear();
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public int getSize() {
		return cacheKeys.size();
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

}
