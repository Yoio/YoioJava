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
package com.yoiojava.config;

import java.lang.reflect.Constructor;

import com.yoiojava.config.properties.PropertiesConfigImpl;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ConfigFactory {
	private static Constructor<? extends Config> configConstructor;
	private static final Log log = LogFactory.getLog(ConfigFactory.class);

	static {

		tryImplementation(new Runnable() {
			@Override
			public void run() {
				usePropertiesConfig();
			}
		});
	}

	public static synchronized void useDefaultConfig(
			Class<? extends Config> clazz) {
		setImplementation(clazz);
	}

	public static synchronized void usePropertiesConfig() {
		setImplementation(PropertiesConfigImpl.class);
	}

	private static void tryImplementation(Runnable runnable) {
		if (configConstructor == null) {
			try {
				runnable.run();
			} catch (Throwable t) {
				// ignore
			}
		}
	}

	/**
	 * 不允许构造
	 */
	private ConfigFactory() {
	}

	public static Config getConfig(Class<?> aClass) {
		return getConfig(aClass.getName());
	}

	public static Config getConfig(String config) {
		try {
			return configConstructor.newInstance(new Object[] { config });
		} catch (Throwable t) {
			throw new ConfigException("创建配置文件出错 " + config + ".  原因: " + t, t);
		}
	}

	private static void setImplementation(Class<? extends Config> implClass) {
		try {
			Constructor<? extends Config> candidate = implClass
					.getConstructor(new Class[] { String.class });
			candidate
					.newInstance(new Object[] { ConfigFactory.class.getName() });
			log.debug("全局配置器初始化使用 '" + implClass + "' 适配器.");
			configConstructor = candidate;
		} catch (Throwable t) {
			throw new ConfigException("设置配置项实现类错误.  原因: " + t, t);
		}
	}
}
