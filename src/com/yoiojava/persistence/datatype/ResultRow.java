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
package com.yoiojava.persistence.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class ResultRow<T> {
	private List<T> row = null;

	public ResultRow() {
		this.row = new ArrayList<T>();
	}

	public void put(T t) {
		row.add(t);
	}

	public T get(int i) {
		return row.get(i);
	}

	public List<T> getRow() {
		return row;
	}

	public void setRow(List<T> row) {
		this.row = row;
	}
}
