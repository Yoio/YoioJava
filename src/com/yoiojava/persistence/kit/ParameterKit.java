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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ParameterKit {
	public static PreparedStatement setPreparedStatement(PreparedStatement ps,
			Object... param) throws SQLException {
		int i = 1;
		for (Object obj : param) {
			if (obj instanceof List) {
				List pss = (List) obj;
				for (int j = 0; j < pss.size(); j++) {
					if (pss.get(j) instanceof String) {
						ps.setString(i, (String) pss.get(j));
						i++;
					} else if (pss.get(j) instanceof BigDecimal) {
						ps.setBigDecimal(i, (BigDecimal) pss.get(j));
						i++;
					} else if (pss.get(j) instanceof Boolean) {
						ps.setBoolean(i, (Boolean) pss.get(j));
						i++;
					} else if (pss.get(j) instanceof Integer) {
						ps.setInt(i, (Integer) pss.get(j));
						i++;
					} else if (pss.get(j) instanceof Timestamp) {
						ps.setTimestamp(i, (Timestamp) pss.get(j));
						i++;
					} else if (pss.get(j) instanceof Date) {
						ps.setDate(
								i,
								new java.sql.Date(((java.util.Date) pss.get(j))
										.getTime()));
						i++;
					} else {
						ps.setObject(i, pss.get(j));
						i++;
					}
				}
			} else if (obj instanceof String) {
				ps.setString(i, (String) obj);
				i++;
			} else if (obj instanceof BigDecimal) {
				ps.setBigDecimal(i, (BigDecimal) obj);
				i++;
			} else if (obj instanceof Boolean) {
				ps.setBoolean(i, (Boolean) obj);
				i++;
			} else if (obj instanceof Integer) {
				ps.setInt(i, (Integer) obj);
				i++;
			} else if (obj instanceof Timestamp) {
				ps.setTimestamp(i, (Timestamp) obj);
				i++;
			} else if (obj instanceof Date) {
				ps.setDate(i,
						new java.sql.Date(((java.util.Date) obj).getTime()));
				i++;
			} else {
				ps.setObject(i, obj);
				i++;
			}

		}
		return ps;
	}

	/**
	 * 32byte uuid
	 * 
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static Timestamp nowTimestamp() {
		return new Timestamp(new Date().getTime());
	}

}
