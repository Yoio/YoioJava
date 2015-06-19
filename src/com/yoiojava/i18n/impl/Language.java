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
package com.yoiojava.i18n.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.i18n.Lang;
import com.yoiojava.i18n.LangException;
import com.yoiojava.io.Resources;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class Language implements Lang {
	private static HashMap<String, String> lang = new HashMap<String, String>();
	private static final Log log = LogFactory.getLog(Language.class);
	private static Language instance;
	private static Config conf = ConfigurationImpl.getInstance();

	public static Language getInstance() {
		if (instance == null) {
			instance = new Language();
			instance.initConf();
		}
		return instance;
	}

	private void initConf() {
		String file = "com.yoiojava.i18n.language."
				+ conf.C("DEFAULT_LANGUAGE");
		loadConstant(file);
	}

	/**
	 * refuse construct.
	 */
	private Language() {
	}

	/**
	 * @param key
	 * @param value
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void L(String key, String value) {
		lang.put(key, value);
	}

	/**
	 * @param key
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String L(String key) {
		return lang.get(key);
	}

	/**
	 * @param file
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void loadLangFile(String file) {
		if (file.contains("properties")) {
			loadProperties(file);
		} else {
			loadConstant(file);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadProperties(String app_package) {
		Properties prop = null;
		try {
			prop = Resources.getResourceAsProperties(app_package);
			Enumeration<String> propertyNames = (Enumeration<String>) prop
					.propertyNames();
			while (propertyNames.hasMoreElements()) {
				String key = (String) propertyNames.nextElement();
				L(key, prop.getProperty(key));
			}
			if (log.isDebugEnabled()) {
				log.debug("[YoioJava]application parameter [" + app_package
						+ "] loaded！");
			}
		} catch (IOException e) {
			// ignore
		}

	}

	@SuppressWarnings("rawtypes")
	private void loadConstant(String app_package) {
		try {
			Class c = Class.forName(app_package);
			Object obj = c.newInstance();
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				lang.put(f.getName(), (String) f.get(obj));
			}
			if (log.isDebugEnabled()) {
				log.debug("[YoioJava]globle parameters [" + app_package
						+ "] loaded！");
			}
		} catch (Throwable t) {
			throw new LangException(
					"[YoioJava]init globle parameters load error.  cause: " + t,
					t);
		}
	}
}
