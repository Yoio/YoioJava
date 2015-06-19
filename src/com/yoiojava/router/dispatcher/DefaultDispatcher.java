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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoiojava.router.Router;
import com.yoiojava.router.handler.Dispatcher;
import com.yoiojava.router.handler.HandleDispatcherRequest;

/**
 * default router dispather.
 * @author Yoio<Yoio@3cto.net>
 */
@SuppressWarnings("serial")
public final class DefaultDispatcher extends HttpServlet implements Router {
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doDispatcher(req, resp);
	}
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doDispatcher(req, resp);
	}
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doDispatcher(req, resp);
	}
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param arg0
	 * @param arg1
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void doOptions(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		this.doDispatcher(arg0, arg1);
	}
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doDispatcher(req, resp);
	}
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doDispatcher(req, resp);
	}
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param arg0
	 * @param arg1
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void doTrace(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		this.doDispatcher(arg0, arg1);
	}
	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param arg0
	 * @param arg1
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	protected final void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		this.doDispatcher(arg0, arg1);
	}


	/**
	 * doDispatcher HttpServletRequest & HttpServletResponse
	 * @param req
	 * @param resp
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
		Dispatcher dispatcher = new HandleDispatcherRequest(req,resp);
		DispatcherMapper dm = new DispatcherMapper(dispatcher);
		dm.parseMapper();
	}

}
