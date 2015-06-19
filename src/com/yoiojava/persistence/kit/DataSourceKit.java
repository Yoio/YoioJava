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
package com.yoiojava.persistence.kit;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.globle.utils.StringUtils;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class DataSourceKit {
	private static final Config conf = ConfigurationImpl.getInstance();

	public static String datasourceUrlBuilder() {
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:");
		if (StringUtils.isNothing((String) conf.C("DB_URL"))) {
			if (((String) conf.C("DB_DRIVER"))
					.contains(Constants.DB_TYPE.MYSQL)) {
				sb.append(Constants.DB_TYPE.MYSQL + "://");
				sb.append((String) conf.C("DB_HOST"));
				sb.append(":" + (String) conf.C("DB_PORT"));
				sb.append("/" + (String) conf.C("DB_NAME"));
				sb.append("?characterEncoding=" + (String) conf.C("DB_CHARSET"));
			} else if (((String) conf.C("DB_DRIVER"))
					.contains(Constants.DB_TYPE.ORACLE)) {
				sb.append(Constants.DB_TYPE.ORACLE + ":thin:@");
				sb.append((String) conf.C("DB_HOST"));
				sb.append(":" + (String) conf.C("DB_PORT"));
				sb.append(":" + (String) conf.C("DB_NAME"));
			} else if (((String) conf.C("DB_DRIVER"))
					.contains(Constants.DB_TYPE.SQLSERVER)) {
				sb.append(Constants.DB_TYPE.SQLSERVER + "://");
				sb.append((String) conf.C("DB_HOST"));
				sb.append(":" + (String) conf.C("DB_PORT") + "; ");
				sb.append("DatabaseName=" + (String) conf.C("DB_NAME"));
			}
		}
		return sb.toString();
	}
}
