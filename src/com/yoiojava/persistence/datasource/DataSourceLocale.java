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
package com.yoiojava.persistence.datasource;

import javax.sql.DataSource;

import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;

/**
 * globle the DataSource for common release source. not use on this version.
 * 
 * @author Yoio<Yoio@3cto.net>
 */
public class DataSourceLocale {
	private static final Log log = LogFactory.getLog(DataSourceLocale.class);
	private static DataSourceLocale instance;
	private DataSource datasource;

	public static DataSourceLocale getInstance() {
		if (instance == null) {
			instance = new DataSourceLocale();
			// The datasource and single cases
			instance.datasource = DataSourceStrategy.getDataSource();
			if (log.isDebugEnabled()) {
				log.debug("single cases datasource [" + instance.datasource
						+ "]");
			}
		}

		return instance;
	}

	public DataSource getDatasource() {
		return datasource;
	}

}
