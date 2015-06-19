/*
 * Copyright 2009-2012 the original author or authors.
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
package com.yoiojava.core;

import com.yoiojava.cache.Cache;
import com.yoiojava.cache.CacheFactory;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.model.Model;
import com.yoiojava.util.KeyGenerator;
import com.yoiojava.util.WebUtils;
import com.yoiojava.view.View;
import com.yoiojava.view.freemaker.FreeMakerImpl;
import com.yoiojava.persistence.M;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class Controller {
	private static View view;

	protected void show(String content) {
		WebUtils.show(content);
	}

	protected String C(String key) {
		return WebUtils.C(key);
	}

	protected void C(String key, Object value) {
		WebUtils.C(key, value);
	}

	@SuppressWarnings("rawtypes")
	public M M(String tablename) {
		return new Model(tablename);
	}

	@SuppressWarnings("rawtypes")
	public M M() {
		return new Model();
	}

	public String I(String param) {
		return WebUtils.getParameter(param);
	}

	public void assign(String attribute, String value) {
		if (Constants.NULL.OBJECT_NULL == view) {
			view = new FreeMakerImpl();
			WebUtils.initViewParameter(view);
		}
		view.assign(attribute, value);
	}

	public void render() {
		if (Constants.NULL.OBJECT_NULL == view) {
			view = new FreeMakerImpl();
			WebUtils.initViewParameter(view);
		}
		view.render();
	}

	public Object session(String attribute) {
		return WebUtils.session(attribute);
	}

	public void session(String attribute, Object value) {
		WebUtils.session(attribute, value);
	}

	public Object cache(String key) {
		Cache c = CacheFactory.getCache();
		return c.getObject(KeyGenerator.getKey(key));
	}

	public void cache(String key, Object value) {
		Cache c = CacheFactory.getCache();
		c.putObject(KeyGenerator.getKey(key), value);
	}

	public void success(String msg) {
		WebUtils.success(msg);
	}

	public void success(String msg, String url) {
		WebUtils.success(msg, url);
	}

	public void success(String msg, String url, Integer second) {
		WebUtils.success(msg, url, second);
	}

	public void error(String msg) {
		WebUtils.error(msg);
	}

	public void error(String msg, String url) {
		WebUtils.error(msg, url);
	}

	public void error(String msg, String url, Integer second) {
		WebUtils.error(msg, url, second);
	}

	public String U(String path) {
		return WebUtils.U(path);
	}
}
