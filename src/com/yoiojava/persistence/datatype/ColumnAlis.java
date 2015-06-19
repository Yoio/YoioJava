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
package com.yoiojava.persistence.datatype;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoiojava.globle.config.Constants;
import com.yoiojava.globle.utils.ListUtils;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ColumnAlis {
	private Map<String, String> alis = new HashMap<String, String>();

	public ColumnAlis() {
	}

	public void registerAlis(List<String> columnNames) {
		for (String col : columnNames) {
			alis.put(col, col);
		}
	}

	public void registerAlis(String columnName, String alisName) {
		alis.put(columnName, alisName);
	}

	public Map<String, String> getColumnAlis() {
		return alis;
	}

	/**
	 * 
	 * @return any alis name with ","
	 * @author Yoio<Yoio@3cto.net>
	 */
	public String getAlisName() {
		Collection<String> alisname = alis.values();
		return ListUtils.list2string((List<String>) alisname,
				Constants.COMMON_ICON.DATABASE_SPLITE_ICON);

	}

}
