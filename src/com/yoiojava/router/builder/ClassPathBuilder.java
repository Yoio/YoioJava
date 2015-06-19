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
package com.yoiojava.router.builder;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.utils.StringUtils;
import com.yoiojava.router.entity.PathInfo;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ClassPathBuilder {
	private Config conf = ConfigurationImpl.getInstance();
	private PathInfo pathinfo;

	public ClassPathBuilder(PathInfo pathinfo) {
		this.pathinfo = pathinfo;
	}

	public String getControllerPath() {
		StringBuffer res = new StringBuffer();
		String pack = (String) conf.C("DEFAULT_PACKAGE_PREFIX");
		if (!StringUtils.isNothing(pack)) {
			res.append(pack + ".");
			res.append(fullPath());
		} else {
			res.append(fullPath());
		}

		return res.toString();
	}

	private String fullPath() {
		StringBuffer res = new StringBuffer();
		String module = (String) conf.C("APP_MODULE");
		String reqModule = pathinfo.getModule();
		if (!StringUtils.isEmpty(reqModule)) {
			if (module.contains(reqModule)) {
				res.append(reqModule + ".");
			}
		}
		res.append("controller.");
		String pa = res.toString().toLowerCase();
		pa = pa + pathinfo.getController()+"Controller";
		return pa;
	}

}
