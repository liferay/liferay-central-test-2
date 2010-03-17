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

import com.liferay.portal.model.ListType;

/**
 * <a href="ListTypePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ListTypePersistenceImpl
 * @see       ListTypeUtil
 * @generated
 */
public interface ListTypePersistence extends BasePersistence<ListType> {
	public void cacheResult(com.liferay.portal.model.ListType listType);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ListType> listTypes);

	public com.liferay.portal.model.ListType create(int listTypeId);

	public com.liferay.portal.model.ListType remove(int listTypeId)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ListType updateImpl(
		com.liferay.portal.model.ListType listType, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ListType findByPrimaryKey(int listTypeId)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ListType fetchByPrimaryKey(int listTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ListType findByType_First(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ListType findByType_Last(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ListType[] findByType_PrevAndNext(
		int listTypeId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ListType> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByType(java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByType(java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}