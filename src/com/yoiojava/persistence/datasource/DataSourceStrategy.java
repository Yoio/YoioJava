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

import java.beans.PropertyVetoException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.globle.utils.StringUtils;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;
import com.yoiojava.persistence.datasource.pooled.PoolState;
import com.yoiojava.persistence.datasource.pooled.PooledDataSource;
import com.yoiojava.persistence.datasource.unpooled.UnpooledDataSource;
import com.yoiojava.persistence.kit.DataSourceKit;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class DataSourceStrategy {
	private static final Config conf = ConfigurationImpl.getInstance();
	private static final Log log = LogFactory.getLog(DataSourceStrategy.class);

	public static DataSource getDataSource() {
		String dstype = (String) conf.C("DS_TYPE");

		if (StringUtils.isNotNothing(dstype)) {
			if (dstype.equals(Constants.DS_TYPE.JNDI)) {
				DataSource ds = null;
				try {
					Context ctx = new InitialContext();
					ds = (DataSource) ctx.lookup((String) conf.C("INITIAL_CONTEXT"));
				} catch (Exception e) {
					throw new DataSourceException(
							"cannot initial jndi configuration.", e);
				}
				if (log.isDebugEnabled()) {
					log.debug("[YoioJava]use jndi datasource.");
				}
				return ds;
			} else if (dstype.equals(Constants.DS_TYPE.C3P0)) {
				ComboPooledDataSource cpds = new ComboPooledDataSource();
				try {
					cpds.setDriverClass((String) conf.C("DB_DRIVER"));
				} catch (PropertyVetoException e) {
					throw new DataSourceException(
							"driver cannot loaded! cause:", e);
				}
				cpds.setJdbcUrl(DataSourceKit.datasourceUrlBuilder());
				cpds.setUser((String) conf.C("DB_USER"));
				cpds.setPassword((String) conf.C("DB_PWD"));
				if (log.isDebugEnabled()) {
					log.debug("[YoioJava]use c3p0 datasource.");
				}
				
				return cpds;
			} else if (dstype.equals(Constants.DS_TYPE.POOLED)) {
				PooledDataSource ds = new PooledDataSource();
				ds.setDriver((String) conf.C("DB_DRIVER"));
				ds.setUrl(DataSourceKit.datasourceUrlBuilder());
				ds.setUsername((String) conf.C("DB_USER"));
				ds.setPassword((String) conf.C("DB_PWD"));
				PoolState ps = new PoolState(ds);
				if (log.isDebugEnabled()) {
					log.debug("[YoioJava]use defalut pooled datasource.");
					if(log.isTraceEnabled()){
						log.trace(ps.toString());
					}
				}
				return ds;
			} else if (dstype.equals(Constants.DS_TYPE.UNPOOLED)) {
				UnpooledDataSource ds = new UnpooledDataSource();
				ds.setDriver((String) conf.C("DB_DRIVER"));
				ds.setUrl(DataSourceKit.datasourceUrlBuilder());
				ds.setUsername((String) conf.C("DB_USER"));
				ds.setPassword((String) conf.C("DB_PWD"));
				if (log.isDebugEnabled()) {
					log.debug("[YoioJava]use unpooled datasource.");
				}
				return ds;
			}

		}
		return null;
	}
}
