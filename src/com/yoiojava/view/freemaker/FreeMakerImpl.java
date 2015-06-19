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
package com.yoiojava.view.freemaker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.context.RequestContext;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.i18n.Lang;
import com.yoiojava.i18n.impl.Language;
import com.yoiojava.router.dispatcher.PathinfoHolder;
import com.yoiojava.router.entity.PathInfo;
import com.yoiojava.util.WebUtils;
import com.yoiojava.view.View;

import freemarker.cache.*;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class FreeMakerImpl implements View {
	private static Config conf = ConfigurationImpl.getInstance();
	private static Lang lang = Language.getInstance();
	private Configuration cfg;
	private Template tpl;
	private Map<String, Object> root;

	public FreeMakerImpl() {
		initConfiguration(null);
	}

	public FreeMakerImpl(String uri) {
		initConfiguration(uri);
	}

	/**
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public void render() {
		try {
			tpl.process(root, WebUtils.getWriter());
		} catch (Exception e) {
			// ignore
		}
	}

	@Override
	public void assign(String alis, Object value) {
		root.put(alis, value);
	}

	private void initConfiguration(String uri) {
		StringBuffer path = new StringBuffer();
		PathInfo pi = PathinfoHolder.getInstance().getPathInfo();
		if (Constants.NULL.STRING_NULL != pi.getModule()) {
			path.append(pi.getModule() + Constants.COMMON_ICON.SLASH_SPACE);
		}
		if (Constants.NULL.STRING_NULL != pi.getController()) {
			path.append(pi.getController());
		}
		String viewpath;
		if (Constants.NULL.STRING_NULL == uri) {
			viewpath = (String) conf.C("TMPL_TEMPLATE_PATH")
					+ Constants.COMMON_ICON.SLASH_SPACE + path.toString();
		} else {
			viewpath = (String) conf.C("TMPL_TEMPLATE_PATH");
		}
		this.cfg = new Configuration();

		cfg.setServletContextForTemplateLoading(RequestContext.getInstance()
				.getReq().getServletContext(), viewpath);
		String viewfile;
		if (Constants.NULL.STRING_NULL == uri) {
			viewfile = pi.getAction() + conf.C("TMPL_TEMPLATE_SUFFIX");
		} else {
			viewfile = uri + conf.C("TMPL_TEMPLATE_SUFFIX");
		}
		try {
			if ((Boolean) conf.C("VIEW_CACHE")) {
				cfg.setCacheStorage(new MruCacheStorage((Integer) conf
						.C("VIEW_MAX_STRONG_SIZE"), (Integer) conf
						.C("VIEW_MAX_SOFT_SIZE")));
			}
			this.tpl = cfg.getTemplate(viewfile);
			this.root = new HashMap<String, Object>();
		} catch (IOException e) {
			exception(viewpath, viewfile);
		}
	}

	private void exception(String viewpath, String viewfile) {
		if ((Boolean) conf.C("DEBUG")) {
			WebUtils.exception(lang.L("CANNOT_GET_TEMPLATE_FILE"), viewpath
					+ Constants.COMMON_ICON.SLASH_SPACE + viewfile);
		} else {
			WebUtils.send404();
		}
	}
}
