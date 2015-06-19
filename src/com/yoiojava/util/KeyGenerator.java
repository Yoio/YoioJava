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


/**
 * @author Yoio<Yoio@3cto.net>
 */
public class KeyGenerator {
	/**
	 * Only the key generation
	 * 
	 * @param key
	 * @return
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static String getKey(String key) {
		// StringBuffer ks = new StringBuffer();
		// ks.append(UUID.randomUUID());
		// ks.append(new Md5().getMD5ofStr(key));
		// return new BASE64Encoder().encode(ks.toString().getBytes());
		// 将MD5输出的二进制结果转换为小写的十六进制
		byte[] data = new Md5().getMD5ofStr(key).getBytes();
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(data[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}
}
