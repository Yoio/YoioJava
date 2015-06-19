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
package com.yoiojava.persistence;

import java.util.Map;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public interface Database{
	Database cache(Boolean isCache);
	
	Database setPK(String _pk);

	Database setTableName(String tablename);

	Database set(String column, Object value);

	Database set(Map<String, Object> entity);

	Database limit(String page, String row);

	Database addParameter(Object param);

	Database where(String where);

	Database field(String field);

	Database distinct(String field);

	Database order(String order);

	Database groupBy(String groupBy);

	Database having(String having);

	Database join(String join);

	Database innerJoin(String innerJoin);

	Database outerJoin(String outerJoin);

	Database leftOuterJoin(String leftOuterJoin);

	Database rightOuterJoin(String rightOuterJoin);

	<T> T find(String id);

	<T> T find(Integer id);

	<T> T find();

	<T> T select();

	<T> T query(String sql);

	String delete();

	String delete(Integer id);

	String delete(String id);

	String save();

	String add();

	void commit();

	void rollback();

	void clean();
}
