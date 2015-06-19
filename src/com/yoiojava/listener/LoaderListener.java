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
package com.yoiojava.listener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yoiojava.config.Config;
import com.yoiojava.config.ConfigException;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.exceptions.YoiojavaException;
import com.yoiojava.globle.func.Exists;
import com.yoiojava.i18n.Lang;
import com.yoiojava.i18n.impl.Language;
import com.yoiojava.io.Resources;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class LoaderListener implements ServletContextListener {
	private static final Log log = LogFactory.getLog(LoaderListener.class);

	/**
	 * @param arg0
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	/**
	 * @param sce
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Config conf = ConfigurationImpl.getInstance();
		Language.getInstance();

		String app_package = (String) conf.C("DEFAULT_PACKAGE_PREFIX");
		loadProperties(conf, app_package);
		String app_conf = app_package + ".config.Config";
		if (Exists.class_exists(app_conf)) {
			try {
				Class c = Class.forName(app_conf);
				Object obj = c.newInstance();
				Field[] fields = c.getDeclaredFields();
				for (Field f : fields) {
					f.setAccessible(true);
					conf.C(f.getName(), f.get(obj));
				}
				if (log.isDebugEnabled()) {
					log.debug("[YoioJava]application parameter [" + app_conf
							+ "] loaded");
				}

			} catch (Throwable t) {
				throw new ConfigException(
						"initilize application parameter error. cause: " + t, t);
			}
		}
		log.debug("Welcome use YoioJava!");

	}

	@SuppressWarnings("unchecked")
	private void loadProperties(Config conf, String app_package) {
		String source = app_package + ".config.config";
		source = source.replace(".", "/");
		source = source + ".properties";
		Properties prop = null;
		try {
			prop = Resources.getResourceAsProperties(source);
			Enumeration<String> propertyNames = (Enumeration<String>) prop
					.propertyNames();
			while (propertyNames.hasMoreElements()) {
				String key = (String) propertyNames.nextElement();
				conf.C(key, prop.getProperty(key));
			}
			if (log.isDebugEnabled()) {
				log.debug("[YoioJava]application parameter [" + source
						+ "] loaded");
			}
		} catch (IOException e) {
			// ignore
		}

	}

}
