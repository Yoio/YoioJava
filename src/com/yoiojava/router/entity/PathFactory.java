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
package com.yoiojava.router.entity;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.utils.StringUtils;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class PathFactory {
	private static Config conf = ConfigurationImpl.getInstance();
	
	public static PathInfo getPathEntity() {
		String module = (String) conf.C("APP_MODULE");
		if(StringUtils.isNothing(module)){
			return new PathEntity();
		}else{
			return new ModulePathEntity();
		}
		
	}

}
