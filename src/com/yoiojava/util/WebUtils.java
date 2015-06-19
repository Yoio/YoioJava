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
package com.yoiojava.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.context.RequestContext;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.globle.func.ParametersHelper;
import com.yoiojava.i18n.Lang;
import com.yoiojava.i18n.impl.Language;
import com.yoiojava.io.Resources;
import com.yoiojava.router.dispatcher.PathinfoHolder;
import com.yoiojava.router.entity.PathInfo;
import com.yoiojava.router.handler.Dispatcher;
import com.yoiojava.view.View;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class WebUtils {
	private static Dispatcher dis = RequestContext.getInstance();
	private static Config conf = ConfigurationImpl.getInstance();
	private static Lang lang = Language.getInstance();

	/**
	 * ;p application level exception.
	 * 
	 * @param info
	 * @param msg
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void exception(String info, String msg) {
		Configuration cfg = new Configuration();
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("title", lang.L("SYSTEM_ERROR"));
		root.put("info", info);
		root.put("location", lang.L("ERROR_LOCATION"));
		root.put("website", lang.L("OFFICIAL_WEBSITE"));
		root.put("msg", msg);
		try {
			cfg.setDirectoryForTemplateLoading(Resources
					.getResourceAsFile((String) conf.C("TMPL_SYS_PATH")));
			Template tpl = cfg.getTemplate((String) conf
					.C("TMPL_EXCEPTION_FILE"));
			tpl.process(root, WebUtils.getWriter());
		} catch (TemplateException e) {
			throw new UtilException("[YoioJava] Template error. File:"
					+ (String) conf.C("TMPL_SYS_PATH")
					+ Constants.COMMON_ICON.SLASH_SPACE
					+ (String) conf.C("TMPL_EXCEPTION_FILE"), e);
		} catch (IOException e) {
			throw new UtilException("[YoioJava] Template error. File:"
					+ (String) conf.C("TMPL_SYS_PATH")
					+ Constants.COMMON_ICON.SLASH_SPACE
					+ (String) conf.C("TMPL_EXCEPTION_FILE"), e);
		}
	}

	public static void success(String msg) {
		success(msg, "history", 2);
	}

	public static void success(String msg, String url) {
		success(msg, url, 2);
	}

	/**
	 * send a success page.
	 * 
	 * @param msg
	 * @param url
	 * @param second
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void success(String msg, String url, Integer second) {
		Configuration cfg = new Configuration();
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("PROMPT_JUMP", lang.L("PROMPT_JUMP"));
		root.put("THE_SYSTEM_PROMPTS", lang.L("THE_SYSTEM_PROMPTS"));
		root.put("AUTOMATIC_PAGE", lang.L("AUTOMATIC_PAGE"));
		root.put("JUMP", lang.L("JUMP"));
		root.put("WAITING_TIME", lang.L("WAITING_TIME"));
		root.put("flag", "success");
		root.put("msg", msg);
		root.put("url", url);
		root.put("waitSecond", second);
		try {
			cfg.setDirectoryForTemplateLoading(Resources
					.getResourceAsFile((String) conf.C("TMPL_SYS_PATH")));
			Template tpl = cfg.getTemplate((String) conf
					.C("TMPL_ACTION_SUCCESS"));
			tpl.process(root, WebUtils.getWriter());
		} catch (TemplateException e) {
			throw new UtilException("[YoioJava] Template error. File:"
					+ (String) conf.C("TMPL_SYS_PATH")
					+ Constants.COMMON_ICON.SLASH_SPACE
					+ (String) conf.C("TMPL_ACTION_SUCCESS"), e);
		} catch (IOException e) {
			throw new UtilException("[YoioJava] Template error. File:"
					+ (String) conf.C("TMPL_SYS_PATH")
					+ Constants.COMMON_ICON.SLASH_SPACE
					+ (String) conf.C("TMPL_ACTION_SUCCESS"), e);
		}
	}

	public static void error(String msg) {
		error(msg, "history", 2);
	}

	public static void error(String msg, String url) {
		error(msg, url, 2);
	}

	/**
	 * send a error page.
	 * 
	 * @param msg
	 * @param url
	 * @param second
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void error(String msg, String url, Integer second) {
		Configuration cfg = new Configuration();
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("PROMPT_JUMP", lang.L("PROMPT_JUMP"));
		root.put("THE_SYSTEM_PROMPTS", lang.L("THE_SYSTEM_PROMPTS"));
		root.put("AUTOMATIC_PAGE", lang.L("AUTOMATIC_PAGE"));
		root.put("JUMP", lang.L("JUMP"));
		root.put("WAITING_TIME", lang.L("WAITING_TIME"));
		root.put("msg", msg);
		root.put("flag", "error");
		root.put("url", url);
		root.put("waitSecond", second);
		try {
			cfg.setDirectoryForTemplateLoading(Resources
					.getResourceAsFile((String) conf.C("TMPL_SYS_PATH")));
			Template tpl = cfg
					.getTemplate((String) conf.C("TMPL_ACTION_ERROR"));
			tpl.process(root, WebUtils.getWriter());
		} catch (TemplateException e) {
			throw new UtilException("[YoioJava] Template error. File:"
					+ (String) conf.C("TMPL_SYS_PATH")
					+ Constants.COMMON_ICON.SLASH_SPACE
					+ (String) conf.C("TMPL_ACTION_ERROR"), e);
		} catch (IOException e) {
			throw new UtilException("[YoioJava] Template error. File:"
					+ (String) conf.C("TMPL_SYS_PATH")
					+ Constants.COMMON_ICON.SLASH_SPACE
					+ (String) conf.C("TMPL_ACTION_ERROR"), e);
		}
	}

	/**
	 * print content on web page.
	 * 
	 * @param msg
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void show(String msg) {
		try {
			HttpServletResponse resp = dis.getResp();
			resp.getWriter().print(msg);
		} catch (IOException e) {
			throw new UtilException("[YoioJava] show error. cause:", e);
		}
	}

	/**
	 * quick get configuration value.
	 * 
	 * @param key
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static String C(String key) {
		return (String) conf.C(key);
	}

	/**
	 * quick configuration.
	 * 
	 * @param key
	 * @param value
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void C(String key, Object value) {
		conf.C(key, value);
	}

	/**
	 * debug the msg on console.
	 * 
	 * @param msg
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void debug(String msg) {
		System.out.println(msg);
	}

	/**
	 * get a page stream.
	 * 
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static PrintWriter getWriter() {
		try {
			return dis.getResp().getWriter();
		} catch (IOException e) {
			throw new UtilException("[YoioJava] cannot getWriter. cause:", e);
		}
	}

	/**
	 * send a 404 not found header info.
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void send404() {
		try {
			dis.getResp();
			dis.getResp().sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (IOException e) {
			throw new UtilException(
					"[YoioJava] The static field HttpServletResponse.SC_NOT_FOUND should be accessed in a static way. cause:",
					e);
		}
	}

	/**
	 * get i18n language value.
	 * 
	 * @param words
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static String L(String words) {
		return lang.L(words);
	}

	/**
	 * get a parameter from GET or POST action.
	 * 
	 * @param key
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static String getParameter(String key) {
		Map<String, String[]> parameters = ParametersHelper.convertParameters(
				PathinfoHolder.getInstance().getPathInfo().getParamater(), dis
						.getReq().getParameterMap());
		return ParametersHelper.stringArrayHandle(parameters.get(key));
	}

	/**
	 * set ROOT PUBLIC path on view.
	 * 
	 * @param v
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void initViewParameter(View v) {
		v.assign("ROOT", dis.getReq().getContextPath());
		v.assign("PUBLIC", dis.getReq().getContextPath()
				+ Constants.COMMON_ICON.SLASH_SPACE + C("VIEW_PUBLIC"));
	}

	/**
	 * set session attribute value
	 * 
	 * @param attribute
	 * @param value
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static void session(String attribute, Object value) {
		dis.getReq().getSession().setAttribute(attribute, value);
	}

	/**
	 * get session attribute value
	 * 
	 * @param attribute
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static Object session(String attribute) {
		if (Constants.NULL.STRING_NULL == attribute) {
			dis.getReq().getSession().invalidate();
		}
		return dis.getReq().getSession().getAttribute(attribute);
	}

	public static String U(String path) {
		PathInfo pathInfo = PathinfoHolder.getInstance().getPathInfo();
		StringBuffer url = new StringBuffer();
		url.append(dis.getReq().getContextPath());
		url.append(Constants.COMMON_ICON.SLASH_SPACE);
		url.append(pathInfo.getModule());
		url.append(Constants.COMMON_ICON.SLASH_SPACE);
		url.append(path);
		return url.toString();
	}

}
