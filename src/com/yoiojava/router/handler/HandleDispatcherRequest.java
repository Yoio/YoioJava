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
package com.yoiojava.router.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.context.RequestContext;
import com.yoiojava.exceptions.YoiojavaException;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * handle router request.
 * 
 * @author Yoio<Yoio@3cto.net>
 */
public class HandleDispatcherRequest implements Dispatcher {

	private HttpServletRequest req = null;

	private HttpServletResponse resp = null;

	private static final Log log = LogFactory
			.getLog(HandleDispatcherRequest.class);

	private Config conf = null;

	public HandleDispatcherRequest(HttpServletRequest req,
			HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		conf = ConfigurationImpl.getInstance();
		initEncoder();
	}

	private void initEncoder() {
		try {
			req.setCharacterEncoding((String) conf.C("APP_CHARSET"));
			resp.setCharacterEncoding((String) conf.C("APP_CHARSET"));
			resp.setHeader("content-type", "text/html");
			resp.setHeader("charset", "utf-8");
			req.getSession().setMaxInactiveInterval(
					(Integer) conf.C("SESSION_TIME"));
			if (log.isDebugEnabled()) {
				log.debug("[YoioJava]SET APPLICATION ENCODE IS "
						+ conf.C("APP_CHARSET"));
			}
			Dispatcher dispatcher = RequestContext.getInstance();
			dispatcher.setReq(req);
			dispatcher.setResp(resp);

			if (log.isDebugEnabled()) {
				log.debug("[YoioJava]GLOBLE REQUESTCONTEXT SUCCESS!");
			}
		} catch (Exception t) {
			throw new YoiojavaException("unsupported encoding. cause:", t);
		}
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public void setResp(HttpServletResponse resp) {
		this.resp = resp;
	}

}
