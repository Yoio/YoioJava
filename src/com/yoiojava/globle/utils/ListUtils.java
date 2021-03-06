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
package com.yoiojava.globle.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ListUtils {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List string2list(String[] s, int start) {
		List tmp = new ArrayList();
		for (int i = start; i < s.length; i++) {
			tmp.add(s[i]);
		}
		return tmp;
	}

	public static String list2string(List<String> list, String split) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == (list.size() - 1)) {
				result.append(list.get(i));
			} else {
				result.append(list.get(i));
				result.append(split);
			}

		}
		return result.toString();
	}
}
