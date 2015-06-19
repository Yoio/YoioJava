/*
 *    Copyright 2009-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.yoiojava.logging;

import java.lang.reflect.Constructor;

/**
 * @author Clinton Begin
 * @author Eduardo Macarron
 * @author Yoio
 */
public final class LogFactory {

	/**
	 * make a logging marker if it's enable.
	 */
	public static final String MARKER = "YOIOJAVA";

	private static Constructor<? extends Log> logConstructor;

	static {
		tryImplementation(new Runnable() {
			@Override
			public void run() {
				useSlf4jLogging();
			}
		});
		tryImplementation(new Runnable() {
			@Override
			public void run() {
				useCommonsLogging();
			}
		});
		tryImplementation(new Runnable() {
			@Override
			public void run() {
				useLog4J2Logging();
			}
		});
		tryImplementation(new Runnable() {
			@Override
			public void run() {
				useLog4JLogging();
			}
		});
		tryImplementation(new Runnable() {
			@Override
			public void run() {
				useJdkLogging();
			}
		});
		tryImplementation(new Runnable() {
			@Override
			public void run() {
				useNoLogging();
			}
		});
	}

	private LogFactory() {
		// disable construction
	}

	public static Log getLog(Class<?> aClass) {
		return getLog(aClass.getName());
	}

	public static Log getLog(String logger) {
		try {
			return logConstructor.newInstance(new Object[] { logger });
		} catch (Throwable t) {
			throw new LogException("logging newInstance error [" + logger
					+ "].  cause: " + t, t);
		}
	}

	public static synchronized void useCustomLogging(Class<? extends Log> clazz) {
		setImplementation(clazz);
	}

	public static synchronized void useSlf4jLogging() {
		setImplementation(com.yoiojava.logging.slf4j.Slf4jImpl.class);
	}

	public static synchronized void useCommonsLogging() {
		setImplementation(com.yoiojava.logging.commons.JakartaCommonsLoggingImpl.class);
	}

	public static synchronized void useLog4JLogging() {
		setImplementation(com.yoiojava.logging.log4j.Log4jImpl.class);
	}

	public static synchronized void useLog4J2Logging() {
		setImplementation(com.yoiojava.logging.log4j2.Log4j2Impl.class);
	}

	public static synchronized void useJdkLogging() {
		setImplementation(com.yoiojava.logging.jdk14.Jdk14LoggingImpl.class);
	}

	public static synchronized void useStdOutLogging() {
		setImplementation(com.yoiojava.logging.stdout.StdOutImpl.class);
	}

	public static synchronized void useNoLogging() {
		setImplementation(com.yoiojava.logging.nologging.NoLoggingImpl.class);
	}

	private static void tryImplementation(Runnable runnable) {
		if (logConstructor == null) {
			try {
				runnable.run();
			} catch (Throwable t) {
				// ignore
			}
		}
	}

	private static void setImplementation(Class<? extends Log> implClass) {
		try {
			Constructor<? extends Log> candidate = implClass
					.getConstructor(new Class[] { String.class });
			Log log = candidate.newInstance(new Object[] { LogFactory.class
					.getName() });
			log.debug("[YoioJava]logging successful using[" + implClass + "]");
			logConstructor = candidate;
		} catch (Throwable t) {
			throw new LogException("logging setImplementation error.  cause: "
					+ t, t);
		}
	}

}
