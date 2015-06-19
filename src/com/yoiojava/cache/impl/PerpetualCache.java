/*
 *    Copyright 2009-2014 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.yoiojava.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.yoiojava.cache.Cache;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * @author Clinton Begin
 */
public class PerpetualCache implements Cache {
	private static Log log = LogFactory.getLog(PerpetualCache.class);
	private static Map<Object, Object> cache = new HashMap<Object, Object>();

	@Override
	public int getSize() {
		return cache.size();
	}

	@Override
	public void putObject(String key, Object value) {
		cache.put(key, value);
		if(log.isDebugEnabled()){
			log.debug("[YoioJava]cache put a key:"+key);
		}
	}

	@Override
	public Object getObject(String key) {
		if(log.isDebugEnabled()){
			log.debug("[YoioJava]cache get a key:"+key);
		}
		return cache.get(key);
	}

	@Override
	public Object removeObject(String key) {
		if(log.isDebugEnabled()){
			log.debug("[YoioJava]cache remove a key:"+key);
		}
		return cache.remove(key);
	}

	@Override
	public void clear() {
		if(log.isDebugEnabled()){
			log.debug("[YoioJava]cache cleared.");
		}
		cache.clear();
	}

}
