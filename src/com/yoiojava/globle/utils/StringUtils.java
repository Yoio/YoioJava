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

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class StringUtils {
	public static boolean isEmpty(String p) {
		if(null == p){
			return true;
		}
		return false;
	}
	/**
	 * "" return true
	 * @param p
	 * @return
	 *
	 * @author Yoio<Yoio@3cto.net>
	 */
	public static boolean isBlank(String p){
		if("".equals(p.trim())){
			return true;
		}
		return false;
	}
	
	public static boolean isNothing(String p) {
		
		if(null == p){
			return true;
		}
		if("".equals(p.trim())){
			return true;
		}
		return false;
	}
	
	public static boolean isNotNothing(String p) {
		return !isNothing(p);
	}
	
	public static boolean isStringArrayEmpty(String[] s,int i) {
		if(i>=s.length){
			return true;
		}else{
			return isEmpty(s[i]);
		}
	}
}
