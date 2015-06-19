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
package com.yoiojava.globle.config;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class Convention {
	/* 调试模式 */
	public static final Boolean DEBUG = true;

	/* 初始化设定 */
	public static final String APP_NAME = "Yoio";
	public static final String APP_MODULE = "Yoio,Admin,UserCenter,Home";
	public static final String DEFAULT_PACKAGE_PREFIX = "com.application"; // 默认模块包前缀
	public static final String URL_PATHINFO_DEPR = "/";
	public static final String URL_INTERCEPTOR_METHOD = "preHandle";// not use.

	/* 默认设定 */
	public static final String DEFAULT_DATA_TYPE = "json"; // 默认数据类型
	public static final String DEFAULT_M_LAYER = "Module"; // 模块
	public static final String DEFAULT_C_LAYER = "Controller"; // 控制器
	public static final String DEFAULT_A_LAYER = "Action"; // 动作
	public static final String DEFAULT_V_LAYER = "Tpl";
	public static final String DEFAULT_CONTROLLER = "Index";
	public static final String DEFAULT_ACTION = "index";
	public static final String APP_CHARSET = "utf-8";// 应用全局编码
	public static final String DEFAULT_TIMEZONE = "PRC";// 默认时区

	/* 数据库设置 */
	public static final String DS_TYPE = "pooled"; // datasource五种类型 jndi
														// c3p0 pooled unpooled
	// jndi数据库连接参数
	public static final String INITIAL_CONTEXT = "java:comp/env/jdbc/mysql";
	public static final String DATA_SOURCE = "data_source";
	// 数据库连接参数
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";// 数据库驱动
	public static final String DB_URL = "";// 数据库连接url
	public static final String DB_TYPE = "mysql";// 数据库类型，不需要定义
	public static final String DB_HOST = "localhost";// 服务器地址
	public static final String DB_NAME = "discuz";// 数据库名
	public static final String DB_USER = "root";// 用户名
	public static final String DB_PWD = "blackhatyoio";// 密码
	public static final String DB_PORT = "3306";// 端口
	public static final String DB_PREFIX = "pre_";// 数据库表前缀
	public static final String DB_CHARSET = "utf-8";// 数据库表前缀
	// 数据库缓存开关
	public static final Boolean DB_CACHE = false;// 数据库表前缀
	// 数据库事务
	public static final Boolean AUTOCOMMIT = true;// 是否自动提交
	// ResultSet
	public static final String RESULTTYPE = "Column";// 返回的结果集，Column带数据库字段名，List
	// 防止 拒绝服务攻击 设置最大数据量
	public static final Integer LIMIT_ROWNUMBER = 100;
	// table primary key
	public static final String DEFAULT_PK = "id";

	/* Session 机制 */
	public static final Integer SESSION_TIME = 6;// SESSION时效设置，单位：分钟

	/* Cache数据缓存设置 */
	public static final Integer CACHE_TIMEOUT = 10000;// Cache连接url
	public static final String CACHE_TYPE = "Redis";// Cache类型 Redis Perpetual
	public static final String CACHE_HOST = "127.0.0.1";// 服务器地址
	public static final Integer CACHE_DATABASE = 1;// Cache数据库
	public static final String CACHE_PWD = "";// 密码
	public static final Integer CACHE_PORT = 6379;// 端口

	/* 错误设置 */
	public static final String ERROR_PAGE = "";// 错误定向页面

	/* 日志设置 */
	public static final Boolean SQL_FORMAT = false;// 日志sql是否格式化记录
	public static final int LOG_FILE_SIZE = 2097152; // 日志文件大小限制
	public static final Boolean LOG_EXCEPTION_RECORD = false; // 是否记录异常信息日志

	/* 模板引擎设置 */
	public static final String TMPL_TEMPLATE_SUFFIX = ".htm";
	public static final String TMPL_TEMPLATE_PATH = "WEB-INF/tpl"; // WEB-INF目录下
	public static final String TMPL_SYS_PATH = "com/yoiojava/tpl"; // package目录下
	public static final String TMPL_EXCEPTION_FILE = "page_exception.tpl";
	public static final String TMPL_ACTION_SUCCESS = "dispatch_jump.tpl";
	public static final String TMPL_ACTION_ERROR = "dispatch_jump.tpl";

	/* i18n 国际化 */
	public static final String DEFAULT_LANGUAGE = "EN_US"; //EN_US ZH_CN

	/* 视图 */
	public static final String  VIEW_PUBLIC = "assets";
	public static final Boolean VIEW_CACHE = true;
	public static final Integer VIEW_MAX_STRONG_SIZE = 100;
	public static final Integer VIEW_MAX_SOFT_SIZE = 2500;

	/* 框架信息 */
	public static final String FRAMEWORK_NAME = "YoioJava Web Framework";
	public static final String FRAMEWORK_VERSION = "1.0.1";
	public static final String FRAMEWORK_AUTHOR = "Email:yoio@3cto.net";
	public static final String FRAMEWORK_DESCRIPTION = "YoioJava Web Framework - 是由Yoio开发维护的一款将于2015年06月开源的MVC结构的Java Web框架,为敏捷WEB应用开发而诞生。大道至简。";
	public static final String FRAMEWORK_DECLARATION = "Make It Easy";
	public static final String FRAMEWORK_THANKS = "程颖飞，韩俊";
}
