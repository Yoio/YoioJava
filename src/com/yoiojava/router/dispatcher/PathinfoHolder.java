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
package com.yoiojava.router.dispatcher;

import com.yoiojava.router.entity.PathInfo;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class PathinfoHolder {
	private PathInfo pi;
	private static PathinfoHolder _instance = new PathinfoHolder();

	public static PathinfoHolder getInstance() {
		return _instance;
	}

	public void holdPathinfo(PathInfo pathInfo) {
			this.pi = pathInfo;
	}

	public PathInfo getPathInfo() {
		return pi;
	}
}
