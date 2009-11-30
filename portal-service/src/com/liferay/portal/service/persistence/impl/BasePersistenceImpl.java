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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;

import java.io.Serializable;

import java.sql.Connection;

import java.util.List;

import javax.sql.DataSource;

/**
 * <a href="BasePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BasePersistenceImpl<T extends BaseModel<T>>
	implements BasePersistence<T>, SessionFactory {

	public static final String COUNT_COLUMN_NAME = "COUNT_VALUE";

	public void clearCache() {
	}

	public void closeSession(Session session) {
		_sessionFactory.closeSession(session);
	}

	@SuppressWarnings("unused")
	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {

		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {

		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public List<Object> findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {

		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public T fetchByPrimaryKey(Serializable primaryKey) throws SystemException {
		throw new UnsupportedOperationException();
	}

	public DataSource getDataSource() {
		return _dataSource;
	}

	public Dialect getDialect() {
		return _dialect;
	}

	public ModelListener<T>[] getListeners() {
		return listeners;
	}

	public Session openNewSession(Connection connection) throws ORMException {
		return _sessionFactory.openNewSession(connection);
	}

	public Session openSession() throws ORMException {
		return _sessionFactory.openSession();
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

	public void registerListener(ModelListener<T> listener) {
		List<ModelListener<T>> listenersList = ListUtil.fromArray(listeners);

		listenersList.add(listener);

		listeners = listenersList.toArray(
			new ModelListener[listenersList.size()]);
	}

	@SuppressWarnings("unused")
	public T remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {

		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public T remove(T model) throws SystemException {
		throw new UnsupportedOperationException();
	}

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
		_dialect = _sessionFactory.getDialect();
	}

	public void unregisterListener(ModelListener<T> listener) {
		List<ModelListener<T>> listenersList = ListUtil.fromArray(listeners);

		listenersList.remove(listener);

		listeners = listenersList.toArray(
			new ModelListener[listenersList.size()]);
	}

	/**
	 * Add, update, or merge, the model. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating a model.
	 *
	 * @param  model the model to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *		   value is false. Setting merge to true is more expensive and
	 *		   should only be true when model is transient. See LEP-5473 for a
	 *		   detailed discussion of this method.
	 * @return the model that was added, updated, or merged
	 */
	public T update(T model, boolean merge) throws SystemException {
		boolean isNew = model.isNew();

		for (ModelListener<T> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(model);
			}
			else {
				listener.onBeforeUpdate(model);
			}
		}

		model = updateImpl(model, merge);

		for (ModelListener<T> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(model);
			}
			else {
				listener.onAfterUpdate(model);
			}
		}

		return model;
	}

	@SuppressWarnings("unused")
	public T updateImpl(T model, boolean merge) throws SystemException {
		throw new UnsupportedOperationException();
	}

	protected void appendOrderByComparator(
		StringBundler query, String entityAlias, OrderByComparator obc) {

		query.append(ORDER_BY_CLAUSE);

		String[] orderByFields = obc.getOrderByFields();

		for (int i = 0; i < orderByFields.length; i++) {
			query.append(entityAlias);
			query.append(orderByFields[i]);

			if ((i + 1) < orderByFields.length) {
				if (obc.isAscending()) {
					query.append(ORDER_BY_ASC_HAS_NEXT);
				}
				else {
					query.append(ORDER_BY_DESC_HAS_NEXT);
				}
			}
			else {
				if (obc.isAscending()) {
					query.append(ORDER_BY_ASC);
				}
				else {
					query.append(ORDER_BY_DESC);
				}
			}
		}
	}

	protected ModelListener<T>[] listeners = new ModelListener[0];

	protected static final String ORDER_BY_ASC = " ASC";

	protected static final String ORDER_BY_ASC_HAS_NEXT = " ASC, ";

	protected static final String ORDER_BY_CLAUSE = " ORDER BY ";

	protected static final String ORDER_BY_DESC = " DESC";

	protected static final String ORDER_BY_DESC_HAS_NEXT = " DESC, ";

	private static Log _log = LogFactoryUtil.getLog(BasePersistenceImpl.class);

	private DataSource _dataSource;
	private Dialect _dialect;
	private SessionFactory _sessionFactory;

}