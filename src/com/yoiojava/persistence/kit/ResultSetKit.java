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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.persistence.ResultSetException;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ResultSetKit {
	private static final Config conf = ConfigurationImpl.getInstance();

	/**
	 * immediatlly you wanna different type of result type,just use
	 * C("RESULTTYPE","Column") method to get result in [Action].
	 * 
	 * [Action] Controller Method
	 * 
	 * @param rs
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static <T> T getRow(ResultSet rs) {
		if (Constants.RESULTTYPE.LIST.equals(conf.C("RESULTTYPE"))) {
			return getResult(rs);
		}
		return getResultWithColumn(rs);
	}

	public static <T> T getResult(ResultSet rs) {
		List<List<String>> data = new Vector<List<String>>();
		int size;
		try {
			size = rs.getMetaData().getColumnCount();
		} catch (SQLException e1) {
			throw new ResultSetException("[YoioJava] resultset error. cause:",
					e1);
		}
		try {
			while (rs.next()) {

				List<String> tmp = new Vector<String>();
				for (int i = 1; i < size + 1; i++) {
					tmp.add(rs.getString(i));
				}
				data.add(tmp);
			}
		} catch (SQLException e) {
			throw new ResultSetException("[YoioJava] resultset error. cause:",
					e);
		}
		return (T) data;
	}

	public static <T> T getResultWithColumn(ResultSet rs) {
		List<Map<String, String>> data = new Vector<Map<String, String>>();
		List<String> colname = new Vector<String>();
		int size;
		try {
			ResultSetMetaData md = rs.getMetaData();
			size = md.getColumnCount();
			for (int i = 1; i < size + 1; i++) {
				colname.add(md.getColumnName(i));
			}
		} catch (SQLException e1) {
			throw new ResultSetException("[YoioJava] resultset error. cause:",
					e1);
		}

		try {
			while (rs.next()) {
				Map<String, String> tmp = new HashMap<String, String>();
				for (int i = 1; i < size + 1; i++) {
					tmp.put(colname.get(i - 1), rs.getString(i));
				}
				data.add(tmp);
			}
		} catch (SQLException e) {
			throw new ResultSetException("[YoioJava] resultset error. cause:",
					e);
		}
		return (T) data;
	}
}
