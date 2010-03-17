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

import com.liferay.portal.model.Release;

/**
 * <a href="ReleasePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReleasePersistenceImpl
 * @see       ReleaseUtil
 * @generated
 */
public interface ReleasePersistence extends BasePersistence<Release> {
	public void cacheResult(com.liferay.portal.model.Release release);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Release> releases);

	public com.liferay.portal.model.Release create(long releaseId);

	public com.liferay.portal.model.Release remove(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Release updateImpl(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Release findByPrimaryKey(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Release fetchByPrimaryKey(long releaseId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Release findByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Release> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Release> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Release> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByServletContextName(java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByServletContextName(java.lang.String servletContextName)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}