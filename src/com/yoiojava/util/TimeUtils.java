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
package com.yoiojava.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yoiojava.globle.config.Constants;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class TimeUtils {
	private static TimeUtils instance;
	private static SimpleDateFormat sdf;

	public static TimeUtils getInstance(String pattern) {
		if (Constants.NULL.OBJECT_NULL == instance) {
			instance = new TimeUtils();
		}
		if (Constants.NULL.OBJECT_NULL == sdf) {
			sdf = new SimpleDateFormat();
		}
		sdf.applyPattern(pattern);
		return instance;
	}

	public static String formatDate(String date) {
		try {
			return sdf.format(sdf.parse(date));
		} catch (ParseException e) {
			throw new UtilException("[YoioJava] cannot formatDate.cause:", e);
		}
	}

	public static String formatDate(Date date) {
		return sdf.format(date);
	}

	public Date parseString(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new UtilException("[YoioJava] cannot formatDate.cause:", e);
		}
	}
}
