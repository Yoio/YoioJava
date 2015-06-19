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
package com.yoiojava.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import javax.sql.DataSource;

import com.yoiojava.config.Config;
import com.yoiojava.config.configuration.ConfigurationImpl;
import com.yoiojava.exceptions.PersistenceException;
import com.yoiojava.globle.config.Constants;
import com.yoiojava.logging.Log;
import com.yoiojava.logging.LogFactory;
import com.yoiojava.persistence.datasource.DataSourceLocale;
import com.yoiojava.persistence.datasource.DataSourceStrategy;
import com.yoiojava.persistence.kit.ParameterKit;
import com.yoiojava.persistence.kit.ResultSetKit;
import com.yoiojava.persistence.kit.TransactionIsolationLevel;
import com.yoiojava.persistence.transaction.Transaction;
import com.yoiojava.persistence.transaction.TransactionException;
import com.yoiojava.persistence.transaction.jdbc.JdbcTransactionFactory;

/**
 * @author Yoio<Yoio@3cto.net>
 */
public class BasicPersistenceHandler implements PersistenceProvider {
	private static final Config conf = ConfigurationImpl.getInstance();
	private static final Log log = LogFactory
			.getLog(BasicPersistenceHandler.class);
	private Connection conn;
	private boolean autoCommit;
	private Transaction trans;
	private PreparedStatement ps;
	private ResultSet rs;

	public BasicPersistenceHandler(DataSource dataSource,
			TransactionIsolationLevel level, boolean autoCommit) {

		this.autoCommit = autoCommit;
		if (!autoCommit) {
			JdbcTransactionFactory jdbcfac = new JdbcTransactionFactory();

			this.trans = jdbcfac.newTransaction(dataSource, level, autoCommit);
			try {
				this.conn = trans.getConnection();
			} catch (SQLException e) {
				throw new TransactionException(
						"[yoiojava] cannot get connection from Transaction. cause:",
						e);
			}
		} else {
			try {
				this.conn = dataSource.getConnection();
			} catch (SQLException e) {
				throw new PersistenceException(
						"[yoiojava] cannot get connection from datasource. cause:",
						e);
			}
		}
	}

	public BasicPersistenceHandler(DataSource dataSource) {
		this(dataSource, TransactionIsolationLevel.REPEATABLE_READ,
				(Boolean) conf.C("AUTOCOMMIT"));
	}

	public BasicPersistenceHandler(DataSource dataSource,
			TransactionIsolationLevel level) {
		this(dataSource, level, (Boolean) conf.C("AUTOCOMMIT"));
	}

	/**
	 * if you don't wanna immediate change your database configuration,set the
	 * param singleCaseDatasource true. that's mean you can't switch your
	 * database in different [action]. set false or don't give a param the
	 * datasource can imediate change,meanwhile the little more system resource.
	 * 
	 * [action] Controller Method.
	 * 
	 * @param singleCaseDatasource
	 */
	public BasicPersistenceHandler(boolean singleCaseDatasource) {

		this(singleCaseDatasource ? DataSourceLocale.getInstance()
				.getDatasource() : DataSourceStrategy.getDataSource(),
				TransactionIsolationLevel.REPEATABLE_READ, (Boolean) conf
						.C("AUTOCOMMIT"));

	}

	/**
	 * 
	 */
	public BasicPersistenceHandler() {
		this(DataSourceStrategy.getDataSource(),
				TransactionIsolationLevel.REPEATABLE_READ, (Boolean) conf
						.C("AUTOCOMMIT"));
	}

	/**
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public Connection getConnection() {
		return conn;
	}

	/**
	 * 
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 * @throws SQLException
	 */
	@Override
	public void rollback() {
		if (!autoCommit) {
			try {
				trans.rollback();
			} catch (SQLException e) {
				throw new PersistenceException(
						"[YoioJava] cannot rollback transection. cause:", e);
			} finally {
				close();
			}
		}
	}

	/**
	 * 
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 * @throws SQLException
	 */
	@Override
	public void commit() {
		if (!autoCommit) {
			try {
				trans.commit();
			} catch (SQLException e) {
				try {
					trans.rollback();
				} catch (SQLException e1) {
					throw new PersistenceException(
							"[YoioJava] cannot rollback transection. cause:",
							e1);
				}
				throw new PersistenceException(
						"[YoioJava] cannot commit transection. cause:", e);
			} finally {
				close();
			}
		}
	}

	/**
	 * @param sql
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public <T> T select(String sql) {
		return query(sql, null);
	}

	/**
	 * @param sql
	 * @param parameter
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public <T> T select(String sql, Object... parameter) {
		return query(sql, null, parameter);
	}

	@Override
	public <T> T select(String sql, ResultHandler handler, Object... parameter) {
		return query(sql, handler, parameter);
	}

	/**
	 * @param sql
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String insert(String sql) {
		return update(sql, null);
	}

	/**
	 * @param sql
	 * @param parameter
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String insert(String sql, Object... parameter) {
		return update(sql, parameter);
	}

	/**
	 * @param sql
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String update(String sql) {
		return update(sql, null);
	}

	/**
	 * @param sql
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String delete(String sql) {
		return update(sql, null);
	}

	/**
	 * @param sql
	 * @param parameter
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String delete(String sql, Object... parameter) {
		return update(sql, parameter);
	}

	/**
	 * handle all staff on here
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	@Override
	public String update(String sql, Object... parameter) {
		String primaryKey = null;

		try {
			ps = conn.prepareStatement(
					sql.replace(Constants.COMMON_ICON.ENTER_SPACE,
							Constants.COMMON_ICON.BLANK_SPACE).toLowerCase(),
					Statement.RETURN_GENERATED_KEYS);
			if (Constants.NULL.OBJECT_NULL != parameter) {
				ps = ParameterKit.setPreparedStatement(ps, parameter);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				primaryKey = rs.getString(Constants.NUMBER.ONE);
			}
		} catch (SQLException e) {
			throw new PersistenceException("[YoioJava] SQL Error:"
					+ sql.replace(Constants.COMMON_ICON.ENTER_SPACE,
							Constants.COMMON_ICON.BLANK_SPACE).toLowerCase()
					+ ". cause:", e);
		}
		if ((Boolean) conf.C("SQL_FORMAT")) {
			log.info("YoioJava:" + Constants.COMMON_ICON.ENTER_SPACE + sql);
		} else {
			log.info("YoioJava:"
					+ sql.replace(Constants.COMMON_ICON.ENTER_SPACE,
							Constants.COMMON_ICON.BLANK_SPACE).toLowerCase());
		}
		return primaryKey;
	}

	/**
	 * handle all staff on here
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 * 
	 * @author Yoio<Yoio@3cto.net>
	 */
	private <T> T query(String sql, ResultHandler handler, Object... object) {
		List re = null;
		try {
			ps = conn.prepareStatement(sql.replace(
					Constants.COMMON_ICON.ENTER_SPACE,
					Constants.COMMON_ICON.BLANK_SPACE).toLowerCase());
			if (Constants.NUMBER.ZERO != object.length) {
				ps = ParameterKit.setPreparedStatement(ps, object);
			}
			rs = ps.executeQuery();
			if (null == handler) {
				re = ResultSetKit.getRow(rs);
			} else {
				re = handler.handle(rs);
			}

		} catch (SQLException e) {
			throw new PersistenceException("[YoioJava] SQL Error:"
					+ sql.replace(Constants.COMMON_ICON.ENTER_SPACE,
							Constants.COMMON_ICON.BLANK_SPACE).toLowerCase()
					+ ". cause:", e);
		} finally {
			close();
		}
		if ((Boolean) conf.C("SQL_FORMAT")) {
			log.info("YoioJava:" + Constants.COMMON_ICON.ENTER_SPACE + sql);
		} else {
			log.info("YoioJava:"
					+ sql.replace(Constants.COMMON_ICON.ENTER_SPACE,
							Constants.COMMON_ICON.BLANK_SPACE).toLowerCase());
		}
		return (T) re;
	}

	@Override
	public List<String> getColumn(String sql) {
		List<String> column = new Vector<String>();
		try {
			ps = conn.prepareStatement(sql.replace(
					Constants.COMMON_ICON.ENTER_SPACE,
					Constants.COMMON_ICON.BLANK_SPACE).toLowerCase());
			ResultSetMetaData md = ps.getMetaData();
			int size = md.getColumnCount();
			for (int i = 0; i < size; i++) {
				column.add(md.getColumnName(i + 1));
			}
		} catch (SQLException e) {
			throw new PersistenceException("[YoioJava] SQL Error:"
					+ sql.replace(Constants.COMMON_ICON.ENTER_SPACE,
							Constants.COMMON_ICON.BLANK_SPACE).toLowerCase()
					+ ". cause:", e);
		}
		return column;
	}

	@Override
	public void close() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// ignore
			}
		}
		if (null != ps) {
			try {
				ps.close();
			} catch (SQLException e) {
				// ignore
			}
		}
		if (null != trans) {
			try {
				trans.close();
			} catch (SQLException e) {
				// ignore
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}

}
