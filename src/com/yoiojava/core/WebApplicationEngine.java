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

import java.lang.reflect.Method;

import com.yoiojava.WebApplication;
import com.yoiojava.annotations.After;
import com.yoiojava.annotations.Before;
import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.i18n.Lang;
import com.yoiojava.i18n.impl.Language;
import com.yoiojava.router.builder.ClassPathBuilder;
import com.yoiojava.router.entity.PathInfo;
import com.yoiojava.router.handler.Dispatcher;
import com.yoiojava.util.WebUtils;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class WebApplicationEngine implements WebApplication {
	private static Config conf = ConfigurationImpl.getInstance();
	private static Lang lang = Language.getInstance();
	private ClassPathBuilder builder;
	private PathInfo pathinfo;

	public WebApplicationEngine(ClassPathBuilder builder, PathInfo pathinfo) {
		this.builder = builder;
		this.pathinfo = pathinfo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onStartup(Dispatcher hdr) {
		Class target;

		try {
			target = Class.forName(builder.getControllerPath());

			Object o = target.newInstance();

			Method[] ms = target.getDeclaredMethods();
			for (Method method : ms) {
				if (method.isAnnotationPresent(Before.class)) {
					Before annotation = method.getAnnotation(Before.class);
					Object newInstance = annotation.value().newInstance();
					Method inject = annotation.value().getDeclaredMethod(
							(String) conf.C("URL_INTERCEPTOR_METHOD"));
					inject.invoke(newInstance);

					method.invoke(o);
				}
			}

			Method m = target.getDeclaredMethod(pathinfo.getAction());
			m.invoke(o);

			for (Method method : ms) {
				if (method.isAnnotationPresent(After.class)) {
					After annotation = method.getAnnotation(After.class);
					Object newInstance = annotation.value().newInstance();
					Method inject = annotation.value().getDeclaredMethod(
							(String) conf.C("URL_INTERCEPTOR_METHOD"));
					inject.invoke(newInstance);

					method.invoke(o);
				}
			}
		} catch (Exception e) {
			if (Constants.NULL.STRING_NULL != e.getMessage()) {
				if ((Boolean) conf.C("DEBUG")) {
					WebUtils.exception(lang.L("CANNOT_LOAD_THE_MODULE"),
							e.getMessage());
				}else {
					WebUtils.send404();
				}
			}else{
				e.printStackTrace();
			}
		}
	}

}
