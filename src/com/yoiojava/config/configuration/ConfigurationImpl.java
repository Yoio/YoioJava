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
package com.yoiojava.config.configuration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import com.yoiojava.config.Config;
import com.yoiojava.config.ConfigException;
import com.yoiojava.io.Resources;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ConfigurationImpl implements Config {
	private static HashMap<Object, Object> config = new HashMap<Object, Object>();
	private static ConfigurationImpl instance;
	private static final Log log = LogFactory.getLog(ConfigurationImpl.class);

	public static ConfigurationImpl getInstance() {
		if (instance == null) {
			instance = new ConfigurationImpl();
			instance.initConf();
		}
		return instance;
	}

	private void initConf() {
		loadConstant("com.yoiojava.globle.config.Convention");
	}

	/**
	 * refuse construct.
	 */
	private ConfigurationImpl() {
	}

	/**
	 * @param key
	 * @param value
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void C(String key, Object value) {
		config.put(key, value);
	}

	/**
	 * @param key
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Object C(String key) {
		return config.get(key);
	}
	
	/**
	 * quick load config file.
	 * @param file
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void loadConfigFile(String file) {
		if(file.contains("properties")){
			loadProperties(file);
		}else{
			loadConstant(file);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadProperties(String app_package) {
		Properties prop = null;
		try {
			prop = Resources.getResourceAsProperties(app_package);
			Enumeration<String> propertyNames = (Enumeration<String>) prop.propertyNames();
			while (propertyNames.hasMoreElements()) {
				String key = (String) propertyNames.nextElement();
				C(key, prop.getProperty(key));
			}
			if (log.isDebugEnabled()) {
				log.debug("[YoioJava]application parameter [" + app_package
						+ "] loaded！");
			}
		} catch (IOException e) {
			//ignore
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
				config.put(f.getName(), f.get(obj));
			}
			if (log.isDebugEnabled()) {
				log.debug("[YoioJava]globle parameters ["+app_package+"] loaded！");
			}
		} catch (Throwable t) {
			throw new ConfigException(
					"[YoioJava]init globle parameters load error.  cause: " + t,
					t);
		}
	}
}
