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

import java.util.List;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class PathEntity implements PathInfo {
	protected String controller;
	protected String action;
	private List<String> param;

	public PathEntity() {
	}

	public PathEntity(String c, String a) {
		this.controller = c;
		this.action = a;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String getModule() {
		return null;
	}

	@Override
	public void setModule(String module) {
	}

	@Override
	public List<String> getParamater() {
		return param;
	}

	@Override
	public void setParamater(List<String> param) {
		this.param = param;
	}

}
