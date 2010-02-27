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

import com.liferay.portal.model.Image;

/**
 * <a href="ImagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImagePersistenceImpl
 * @see       ImageUtil
 * @generated
 */
public interface ImagePersistence extends BasePersistence<Image> {
	public void cacheResult(com.liferay.portal.model.Image image);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Image> images);

	public com.liferay.portal.model.Image create(long imageId);

	public com.liferay.portal.model.Image remove(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Image updateImpl(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Image findByPrimaryKey(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Image fetchByPrimaryKey(long imageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findBySize(int size)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findBySize(int size,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findBySize(int size,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Image findBySize_First(int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Image findBySize_Last(int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Image[] findBySize_PrevAndNext(
		long imageId, int size,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Image> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeBySize(int size)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countBySize(int size)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}