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
package com.yoiojava.globle.func;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoiojava.globle.config.Constants;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ParametersHelper {
	public static Map<String, String[]> convertParameters(List<String> yp,
			Map<String, String[]> rp) {
		Map<String, String[]> params = new HashMap<String, String[]>();
		if (Constants.NULL.OBJECT_NULL != yp) {
			for (int i = 0; i < yp.size(); i++) {
				if ((i < yp.size() - 1)
						&& Constants.NULL.STRING_NULL != yp.get(i)
						&& Constants.NULL.STRING_NULL != yp.get(i + 1)) {
					params.put(yp.get(i), yp.get(i + 1).split(","));
				}
			}
		}
		if (Constants.NULL.OBJECT_NULL != rp) {
			params.putAll(rp);
		}
		return params;
	}

	public static String stringArrayHandle(String[] array) {
		StringBuffer re = new StringBuffer();
		if (Constants.NUMBER.ZERO < array.length) {
			for (int i = 0; i < array.length; i++) {
				re.append(array[i]);
				if (i < array.length - 1) {
					re.append(Constants.COMMON_ICON.COMMA);
				}
			}
			return re.toString();
		}
		return array[Constants.NUMBER.ZERO];
	}
}
