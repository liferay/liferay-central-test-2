/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;

import java.sql.Connection;

import java.util.List;

import javax.sql.DataSource;

/**
 * <a href="BasePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BasePersistenceImpl implements BasePersistence, SessionFactory {

	public static final String COUNT_COLUMN_NAME = "COUNT_VALUE";

	public void clearCache() {
	}

	public void closeSession(Session session) {
		_sessionFactory.closeSession(session);
	}

	public DataSource getDataSource() {
		return _dataSource;
	}

	public Dialect getDialect() {
		return _dialect;
	}

	public ModelListener[] getListeners() {
		return listeners;
	}

	public Session openSession() throws ORMException {
		return _sessionFactory.openSession();
	}

	public Session openNewSession(Connection connection) throws ORMException {
		return _sessionFactory.openNewSession(connection);
	}

	public void registerListener(ModelListener listener) {
		List<ModelListener> listenersList = ListUtil.fromArray(listeners);

		listenersList.add(listener);

		listeners = listenersList.toArray(
			new ModelListener[listenersList.size()]);
	}

	public SystemException processException(Exception e) {
		if (!(e instanceof ORMException)) {
			_log.error("Caught unexpected exception " + e.getClass().getName());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(e, e);
		}

		return new SystemException(e);
	}

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
		_dialect = _sessionFactory.getDialect();
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listenersList = ListUtil.fromArray(listeners);

		listenersList.remove(listener);

		listeners = listenersList.toArray(
			new ModelListener[listenersList.size()]);
	}

	protected ModelListener[] listeners = new ModelListener[0];

	private static Log _log = LogFactoryUtil.getLog(BasePersistenceImpl.class);

	private DataSource _dataSource;
	private SessionFactory _sessionFactory;
	private Dialect _dialect;

}