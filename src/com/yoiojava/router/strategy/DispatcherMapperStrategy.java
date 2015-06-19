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
package com.yoiojava.router.strategy;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.func.UriHelper;
import com.yoiojava.globle.utils.ListUtils;
import com.yoiojava.globle.utils.StringUtils;
import com.yoiojava.router.entity.PathFactory;
import com.yoiojava.router.entity.PathInfo;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class DispatcherMapperStrategy {
	private String context = null;
	private static Config conf = ConfigurationImpl.getInstance();

	public DispatcherMapperStrategy(String context) {
		this.context = context;
	}

	public PathInfo resolveContext() {
		String[] path = context.split((String) conf.C("URL_PATHINFO_DEPR"));
		PathInfo info = PathFactory.getPathEntity();
		if (!StringUtils.isStringArrayEmpty(path,1)) {
			if (UriHelper.isModule(path[1])) {
				info.setModule(path[1]);
				if (!StringUtils.isStringArrayEmpty(path,2)) {
					info.setController(path[2]);
					if (!StringUtils.isStringArrayEmpty(path,3)) {
						info.setAction(path[3]);
						info.setParamater(ListUtils.string2list(path, 4));
					} else {
						info.setAction((String) conf.C("DEFAULT_ACTION"));
					}
				} else {
					info.setController((String) conf.C("DEFAULT_CONTROLLER"));
				}
			} else {
				if (!StringUtils.isStringArrayEmpty(path,1)) {
					info.setController(path[1]);
					if (!StringUtils.isStringArrayEmpty(path,2)) {
						info.setAction(path[2]);
						info.setParamater(ListUtils.string2list(path, 3));
					} else {
						info.setAction((String) conf.C("DEFAULT_ACTION"));
					}
				} else {
					info.setController((String) conf.C("DEFAULT_CONTROLLER"));
				}
			}
		} else {
			info.setController((String) conf.C("DEFAULT_CONTROLLER"));
			info.setAction((String) conf.C("DEFAULT_ACTION"));
		}
		return info;
	}

}
