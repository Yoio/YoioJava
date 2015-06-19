/*
 *    Copyright 2009-2012 the original author or authors.
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
package com.yoiojava.persistence.transaction.managed;

import java.sql.Connection;

import javax.sql.DataSource;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.persistence.kit.TransactionIsolationLevel;
import com.yoiojava.persistence.transaction.Transaction;
import com.yoiojava.persistence.transaction.TransactionFactory;

/**
 * Creates {@link ManagedTransaction} instances.
 *
 * @see ManagedTransaction
 */
/**
 * @author Clinton Begin
 */
public class ManagedTransactionFactory implements TransactionFactory {
	private static Config conf = ConfigurationImpl.getInstance();

	@Override
	public Transaction newTransaction(Connection conn) {
		return new ManagedTransaction(conn, (Boolean) conf.C("AUTOCOMMIT"));
	}

	@Override
	public Transaction newTransaction(DataSource ds,
			TransactionIsolationLevel level, boolean autoCommit) {
		// Silently ignores autocommit and isolation level, as managed
		// transactions are entirely
		// controlled by an external manager. It's silently ignored so that
		// code remains portable between managed and unmanaged configurations.
		return new ManagedTransaction(ds, level, (Boolean) conf.C("AUTOCOMMIT"));
	}
}
