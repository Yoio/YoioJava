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
package com.yoiojava.router.dispatcher;

import com.yoiojava.WebApplication;
import com.yoiojava.core.WebApplicationEngine;
import com.yoiojava.router.builder.ClassPathBuilder;
import com.yoiojava.router.dispatcher.utils.UriCode;
import com.yoiojava.router.entity.PathInfo;
import com.yoiojava.router.handler.Dispatcher;
import com.yoiojava.router.strategy.DispatcherMapperStrategy;

/**
 * DispatcherMapper
 * 
 * @author Yoio<Yoio@3cto.net>
 */
public class DispatcherMapper {
	private Dispatcher hdr;
	private MapperParser mp;

	public DispatcherMapper(Dispatcher dispatcher) {
		this.hdr = dispatcher;
		this.mp = new Mapper();
	}

	protected class Mapper implements MapperParser {

		@Override
		public void parseMapper() {
			String context = hdr.getReq().getRequestURI()
					.replace(hdr.getReq().getContextPath(), UriCode.BLANK);
			DispatcherMapperStrategy strategy = new DispatcherMapperStrategy(
					context);
			PathInfo pathinfo = strategy.resolveContext();
			ClassPathBuilder builder = new ClassPathBuilder(pathinfo);
			PathinfoHolder.getInstance().holdPathinfo(pathinfo);
			WebApplication app = new WebApplicationEngine(builder, pathinfo);
			app.onStartup(hdr);
		}

	}

	public void parseMapper() {
		mp.parseMapper();
	}
}
