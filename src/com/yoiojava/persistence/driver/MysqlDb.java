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
package com.yoiojava.persistence.driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import com.yoiojava.cache.Cache;
import com.yoiojava.cache.CacheFactory;
import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.globle.utils.StringUtils;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;
import com.yoiojava.persistence.BasicPersistenceHandler;
import com.yoiojava.persistence.Database;
import com.yoiojava.persistence.PersistenceProvider;
import com.yoiojava.persistence.jdbc.SQL;
import com.yoiojava.util.KeyGenerator;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class MysqlDb implements Database {
	private static Config conf = ConfigurationImpl.getInstance();
	private static Log log = LogFactory.getLog(MysqlDb.class);
	protected static SQL sql;
	private PersistenceProvider pp;
	private String tablename;
	private String _pk;
	private Boolean isCache=false;
	private List<String> columns;
	private List<Object> parameter = new Vector<Object>();
	private Map<String, Object> entity = new HashMap<String, Object>();

	public MysqlDb(String tablename) {
		sql = new SQL() {
		};
		sql.SELECT(Constants.COMMON_ICON.STAR_SPACE);
		this.pp = new BasicPersistenceHandler();
		this.tablename = conf.C("DB_PREFIX") + tablename;
	}

	public MysqlDb() {
	}

	private void reset() {
		sql = new SQL() {
		};
	}

	private void initColumn(String q) {
		String s = Constants.NULL.STRING_NULL;
		if (Constants.NULL.STRING_NULL == q) {
			sql.SELECT(Constants.COMMON_ICON.STAR_SPACE);
			s = sql.FROM(tablename).toString();
		} else {
			s = q;
		}
		this.columns = pp.getColumn(s);
	}

	/**
	 * @param where
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database where(String where) {
		sql.WHERE(where);
		return this;
	}

	/**
	 * @param field
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database field(String field) {
		reset();
		sql.SELECT(field);
		return this;
	}

	@Override
	public Database limit(String page, String row) {
		sql.LIMIT(page + "," + row);
		return this;
	}

	/**
	 * @param distinct
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database distinct(String field) {
		reset();
		sql.SELECT_DISTINCT(field);
		return this;
	}

	/**
	 * @param order
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database order(String order) {
		sql.ORDER_BY(order);
		return this;
	}

	/**
	 * @param groupBy
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database groupBy(String groupBy) {
		sql.GROUP_BY(groupBy);
		return this;
	}

	/**
	 * @param having
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database having(String having) {
		sql.HAVING(having);
		return this;
	}

	/**
	 * @param join
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database join(String join) {
		sql.JOIN(join);
		return this;
	}

	/**
	 * @param innerJoin
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database innerJoin(String innerJoin) {
		sql.INNER_JOIN(innerJoin);
		return this;
	}

	/**
	 * @param outerJoin
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database outerJoin(String outerJoin) {
		sql.OUTER_JOIN(outerJoin);
		return this;
	}

	/**
	 * @param leftOuterJoin
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database leftOuterJoin(String leftOuterJoin) {
		sql.LEFT_OUTER_JOIN(leftOuterJoin);
		return this;
	}

	/**
	 * @param rightOuterJoin
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Database rightOuterJoin(String rightOuterJoin) {
		sql.RIGHT_OUTER_JOIN(rightOuterJoin);
		return this;
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public <T> T find() {
		return find(Constants.NULL.STRING_NULL);
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String save() {
		Set<Entry<String, Object>> entrySet = entity.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			sql.SET(entry.getKey() + Constants.COMMON_ICON.EQAULS
					+ Constants.COMMON_ICON.QUESTION_SPACE);
			parameter.add(entry.getValue());
		}
		if (null == parameter) {
			return pp.update(sql.UPDATE(tablename).toString());
		}
		return pp.update(sql.UPDATE(tablename).toString(), parameter);
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String add() {
		reset();
		sql.INSERT_INTO(tablename);
		Set<Entry<String, Object>> entrySet = entity.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			sql.VALUES(entry.getKey(), Constants.COMMON_ICON.QUESTION_SPACE);
			parameter.add(entry.getValue());
		}
		String result;
		if (null == parameter) {
			result = pp.insert(sql.toString());
		}
		result = pp.insert(sql.toString(), parameter);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T select() {
		sql.FROM(tablename);
		String sqlStatement = sql.toString();
		Cache cache = CacheFactory.getCache();
		if ((Boolean) conf.C("DB_CACHE")|| isCache) {
			Object obj = cache.getObject(KeyGenerator.getKey(sqlStatement+parameter.toString()));
			if (Constants.NULL.OBJECT_NULL != obj) {
				log.info("[YoioJava] cache:" + sqlStatement);
				return (T) obj;
			}
		}
		Object data;
		if (Constants.NUMBER.ZERO == parameter.size()) {
			data = pp.select(sqlStatement);
		} else {
			data = pp.select(sqlStatement, parameter);
		}
		if ((Boolean) conf.C("DB_CACHE")|| isCache) {
			cache.putObject(KeyGenerator.getKey(sqlStatement+parameter.toString()), data);
		}
		return (T) data;
	}
	
	/**
	 * @param id
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T find(String id) {
		if (Constants.NULL.STRING_NULL != id) {
			String pk = (String) (StringUtils.isNotNothing(_pk) ? _pk : conf
					.C("DEFAULT_PK"));
			StringBuffer where = new StringBuffer();
			where.append(pk);
			where.append("=?");
			addParameter(id);
			sql.WHERE(where.toString());
		}
		sql.FROM(tablename);
		String sqlStatement = sql.toString();
		Cache cache = CacheFactory.getCache();
		if ((Boolean) conf.C("DB_CACHE")|| isCache) {
			Object obj = cache.getObject(KeyGenerator.getKey(sqlStatement+parameter.toString()));
			if (Constants.NULL.OBJECT_NULL != obj) {
				log.info("[YoioJava] cache:" + sqlStatement);
				return (T) obj;
			}
		}
		List result;
		if (null == parameter) {
			result = pp.select(sqlStatement);
		}
		result = pp.select(sqlStatement, parameter);
		if ((Boolean) conf.C("DB_CACHE")|| isCache) {
			cache.putObject(KeyGenerator.getKey(sqlStatement+parameter.toString()), result.get(Constants.NUMBER.ZERO));
		}
		return (T) result.get(Constants.NUMBER.ZERO);
	}

	@Override
	public Database addParameter(Object param) {
		parameter.add(param);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T query(String sql) {
		Cache cache = CacheFactory.getCache();
		if ((Boolean) conf.C("DB_CACHE") || isCache) {
			Object obj = cache.getObject(KeyGenerator.getKey(sql+parameter.toString()));
			if (Constants.NULL.OBJECT_NULL != obj) {
				log.info("[YoioJava] cache:" + sql);
				return (T) obj;
			}
		}
		Object data;
		if (Constants.NUMBER.ZERO == parameter.size()) {
			data = pp.select(sql);
		} else {
			data = pp.select(sql, parameter);
		}
		if ((Boolean) conf.C("DB_CACHE") || isCache) {
			cache.putObject(KeyGenerator.getKey(sql+parameter.toString()), data);
		}
		return (T) data;
	}

	@Override
	public Database setPK(String _pk) {
		this._pk = _pk;
		return this;
	}

	public Database set(String column, Object value) {
		this.entity.put(column, value);
		return this;
	}

	public Database set(Map<String, Object> entity) {
		this.entity = entity;
		return this;
	}

	@Override
	public Database setTableName(String tablename) {
		this.tablename = conf.C("DB_PREFIX") + tablename;
		return this;
	}

	public void rollback() {
		pp.rollback();
	};

	public void commit() {
		pp.commit();
	};

	@Override
	public void clean() {
		sql = null;
		reset();
		sql.SELECT(Constants.COMMON_ICON.STAR_SPACE);
		_pk = null;
		parameter.clear();
		entity.clear();
	}

	@Override
	public <T> T find(Integer id) {
		return find(String.valueOf(id));
	}

	@Override
	public String delete(Integer id) {
		return delete(Integer.toString(id));
	}

	@Override
	public String delete(String id) {
		if (Constants.NULL.STRING_NULL != id) {
			String pk = (String) (StringUtils.isNotNothing(_pk) ? _pk : conf
					.C("DEFAULT_PK"));
			StringBuffer where = new StringBuffer();
			where.append(pk);
			where.append("=?");
			addParameter(id);
			sql.WHERE(where.toString());
		}
		sql.DELETE_FROM(tablename);
		String sqlStatement = sql.toString();
		if (null == parameter) {
			return pp.delete(sqlStatement);
		}
		return pp.delete(sqlStatement, parameter);
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String delete() {
		return delete(Constants.NULL.STRING_NULL);
	}

	@Override
	public Database cache(Boolean isCache) {
		this.isCache = isCache;
		return this;
	}
}
