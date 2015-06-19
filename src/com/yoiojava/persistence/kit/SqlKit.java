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
package com.yoiojava.persistence.kit;

import java.util.List;

import com.yoiojava.globle.config.Constants;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class SqlKit {
	public static String insertColumn(List<String> column) {
		StringBuffer col = new StringBuffer();
		col.append(Constants.COMMON_ICON.OPENING_PARENTHESES);
		for (int i = 0; i < column.size(); i++) {
			col.append(column.get(i));
			if (i != column.size() - 1) {
				col.append(Constants.COMMON_ICON.COMMA);
			}
		}
		col.append(Constants.COMMON_ICON.CLOSING_PARETHESES);
		return col.toString();
	}
}
