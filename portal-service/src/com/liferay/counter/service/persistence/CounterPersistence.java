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

package com.liferay.counter.service.persistence;

import com.liferay.counter.model.Counter;
import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="CounterPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CounterPersistenceImpl
 * @see       CounterUtil
 * @generated
 */
public interface CounterPersistence extends BasePersistence<Counter> {
	public void cacheResult(com.liferay.counter.model.Counter counter);

	public void cacheResult(
		java.util.List<com.liferay.counter.model.Counter> counters);

	public com.liferay.counter.model.Counter create(java.lang.String name);

	public com.liferay.counter.model.Counter remove(java.lang.String name)
		throws com.liferay.counter.NoSuchCounterException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.counter.model.Counter updateImpl(
		com.liferay.counter.model.Counter counter, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.counter.model.Counter findByPrimaryKey(
		java.lang.String name)
		throws com.liferay.counter.NoSuchCounterException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.counter.model.Counter fetchByPrimaryKey(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.counter.model.Counter> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.counter.model.Counter> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.counter.model.Counter> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}