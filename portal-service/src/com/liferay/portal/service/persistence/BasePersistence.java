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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;

import java.io.Serializable;

import java.util.List;

/**
 * <a href="BasePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface BasePersistence<T extends BaseModel<T>> {

	public void clearCache();

	public int countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException;

	public T fetchByPrimaryKey(Serializable primaryKey) throws SystemException;

	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException;

	public List<Object> findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end)
		throws SystemException;

	public List<Object> findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException;

	public ModelListener<T>[] getListeners();

	public void registerListener(ModelListener<T> listener);

	public T remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	public T remove(T model) throws SystemException;

	public void unregisterListener(ModelListener<T> listener);

	public T update(T model, boolean merge) throws SystemException;

}