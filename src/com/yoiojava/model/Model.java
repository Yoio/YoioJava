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
package com.yoiojava.model;

import java.util.Map;

import com.yoiojava.persistence.Database;
import com.yoiojava.persistence.M;
import com.yoiojava.util.Reflections;

/**
 * for all the persistence interface
 * 
 * @author Yoio<Yoio@3cto.net>
 */
public class Model<T> extends BaseDao implements M {
	protected Class<T> entityClass;

	public Model() {
		this.entityClass = Reflections.getClassGenricType(getClass());
		setTableName(entityClass.getSimpleName());
	}

	public Model(String tablename) {
		setTableName(tablename);
	}

	protected Class<T> getEntityClass() {
		return this.entityClass;
	}

	@Override
	public Database setPK(String _pk) {
		return getDatabase().setPK(_pk);
	}

	@Override
	public Database set(String column, Object value) {
		return getDatabase().set(column, value);
	}

	@Override
	public Database set(Map<String, Object> entity) {
		return getDatabase().set(entity);
	}

	@Override
	public Database addParameter(Object param) {
		return getDatabase().addParameter(param);
	}

	@Override
	public Database where(String where) {
		return getDatabase().where(where);
	}

	@Override
	public Database field(String field) {
		return getDatabase().field(field);
	}

	@Override
	public Database distinct(String field) {
		return getDatabase().distinct(field);
	}

	@Override
	public Database order(String order) {
		return getDatabase().order(order);
	}

	@Override
	public Database groupBy(String groupBy) {
		return getDatabase().groupBy(groupBy);
	}

	@Override
	public Database having(String having) {
		return getDatabase().having(having);
	}

	@Override
	public Database join(String join) {
		return getDatabase().join(join);
	}

	@Override
	public Database innerJoin(String innerJoin) {
		return getDatabase().innerJoin(innerJoin);
	}

	@Override
	public Database outerJoin(String outerJoin) {
		return getDatabase().outerJoin(outerJoin);
	}

	@Override
	public Database leftOuterJoin(String leftOuterJoin) {
		return getDatabase().leftOuterJoin(leftOuterJoin);
	}

	@Override
	public Database rightOuterJoin(String rightOuterJoin) {
		return getDatabase().rightOuterJoin(rightOuterJoin);
	}

	@Override
	public <T> T find(String id) {
		return getDatabase().find(id);
	}

	@Override
	public <T> T find(Integer id) {
		return getDatabase().find(id);
	}

	@Override
	public <T> T find() {
		return getDatabase().find();
	}

	@Override
	public <T> T select() {
		return getDatabase().select();
	}

	@Override
	public <T> T query(String sql) {
		return getDatabase().query(sql);
	}

	@Override
	public String delete() {
		return getDatabase().delete();
	}

	@Override
	public String save() {
		return getDatabase().save();
	}

	@Override
	public String add() {
		return getDatabase().add();
	}

	@Override
	public void commit() {
		getDatabase().commit();
	}

	@Override
	public void clean() {
		getDatabase().clean();
	}

	@Override
	public void rollback() {
		getDatabase().rollback();
	}

	public Database limit(Integer page, Integer row) {
		return getDatabase().limit(String.valueOf(page), String.valueOf(row));
	}

	@Override
	public Database limit(String page, String row) {
		return getDatabase().limit(page, row);
	}

	@Override
	public String delete(Integer id) {
		return getDatabase().delete(id);
	}

	@Override
	public String delete(String id) {
		return getDatabase().delete(id);
	}

	@Override
	public Database cache(Boolean isCache) {
		return getDatabase().cache(isCache);
	}

}
