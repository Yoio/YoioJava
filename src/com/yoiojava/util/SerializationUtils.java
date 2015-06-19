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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.yoiojava.globle.config.Constants;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class SerializationUtils {

	public static byte[] objectToByte(java.lang.Object obj) {
		byte[] bytes = null;
		try {
			if (Constants.NULL.OBJECT_NULL == obj) {
				return null;
			}
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();

			bo.close();
			oo.close();
		} catch (Exception e) {
			throw new UtilException(
					"[YoioJava]cannot transform ObjectToByte.cause:", e);
		}
		return (bytes);
	}

	public static java.lang.Object byteToObject(byte[] bytes) {
		java.lang.Object obj = new java.lang.Object();
		try {
			if (Constants.NULL.OBJECT_NULL == bytes) {
				return null;
			}
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);

			obj = oi.readObject();

			bi.close();
			oi.close();
		} catch (Exception e) {
			throw new UtilException(
					"[YoioJava]cannot transform ByteToObject.cause:", e);
		}
		return obj;
	}
}
