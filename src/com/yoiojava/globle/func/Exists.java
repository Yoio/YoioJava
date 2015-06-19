package com.yoiojava.globle.func;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
/**
 * 核心库  判断类、方法、字段是否存在静态方法
 * @author Yoio <Yoio@3cto.net>
 * @time 下午3:38:13
 */
public class Exists {
	public static boolean class_exists(String cls) {
		try {
			Class.forName(cls);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean field_exists(Class cls,String fieldname) {
		Field[] fields = cls.getDeclaredFields();
		for(Field f:fields){
			if(f.getName().equals(fieldname)){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean method_exists(Class cls,String methodname){
		Method[] methods = cls.getDeclaredMethods();
		for(Method f:methods){
			if(f.getName().equals(methodname)){
				return true;
			}
		}
		return false;
	}
}
