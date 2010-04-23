/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
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
 * @author Shuyang Zhou
 */
public class BasePersistenceImpl<T extends BaseModel<T>>
	implements BasePersistence<T>, SessionFactory {

	public static final String COUNT_COLUMN_NAME = "COUNT_VALUE";

	public void clearCache() {
	}

	public void closeSession(Session session) {
		_sessionFactory.closeSession(session);
	}

	public int countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {

		dynamicQuery.setProjection(ProjectionFactoryUtil.rowCount());

		List<Object> results = findWithDynamicQuery(dynamicQuery);

		if (results.isEmpty()) {
			return 0;
		}
		else {
			return ((Long)results.get(0)).intValue();
		}
	}

	@SuppressWarnings("unused")
	public T fetchByPrimaryKey(Serializable primaryKey) throws SystemException {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {

		throw new UnsupportedOperationException();
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		OrderFactoryUtil.addOrderByComparator(dynamicQuery, orderByComparator);

		return findWithDynamicQuery(dynamicQuery, start, end);
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
		StringBundler query, String entityAlias,
		OrderByComparator orderByComparator) {

		query.append(ORDER_BY_CLAUSE);

		String[] orderByFields = orderByComparator.getOrderByFields();

		for (int i = 0; i < orderByFields.length; i++) {
			query.append(entityAlias);
			query.append(orderByFields[i]);

			if ((i + 1) < orderByFields.length) {
				if (orderByComparator.isAscending()) {
					query.append(ORDER_BY_ASC_HAS_NEXT);
				}
				else {
					query.append(ORDER_BY_DESC_HAS_NEXT);
				}
			}
			else {
				if (orderByComparator.isAscending()) {
					query.append(ORDER_BY_ASC);
				}
				else {
					query.append(ORDER_BY_DESC);
				}
			}
		}
	}

	protected static final String ORDER_BY_ASC = " ASC";

	protected static final String ORDER_BY_ASC_HAS_NEXT = " ASC, ";

	protected static final String ORDER_BY_CLAUSE = " ORDER BY ";

	protected static final String ORDER_BY_DESC = " DESC";

	protected static final String ORDER_BY_DESC_HAS_NEXT = " DESC, ";

	protected static final String WHERE_AND = " AND ";

	protected static final String WHERE_LESSER_THAN = " <= ? ";

	protected static final String WHERE_LESSER_THAN_HAS_NEXT = " <= ? AND ";

	protected static final String WHERE_LIMIT_2 = " LIMIT 2";

	protected static final String WHERE_GREATER_THAN = " >= ? ";

	protected static final String WHERE_GREATER_THAN_HAS_NEXT = " >= ? AND ";

	protected ModelListener<T>[] listeners = new ModelListener[0];

	private static Log _log = LogFactoryUtil.getLog(BasePersistenceImpl.class);

	private DataSource _dataSource;
	private Dialect _dialect;
	private SessionFactory _sessionFactory;

}