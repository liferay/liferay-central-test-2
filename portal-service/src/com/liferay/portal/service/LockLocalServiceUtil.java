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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="LockLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link LockLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LockLocalService
 * @generated
 */
public class LockLocalServiceUtil {
	public static com.liferay.portal.model.Lock addLock(
		com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLock(lock);
	}

	public static com.liferay.portal.model.Lock createLock(long lockId) {
		return getService().createLock(lockId);
	}

	public static void deleteLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLock(lockId);
	}

	public static void deleteLock(com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLock(lock);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Lock getLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLock(lockId);
	}

	public static java.util.List<com.liferay.portal.model.Lock> getLocks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLocks(start, end);
	}

	public static int getLocksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLocksCount();
	}

	public static com.liferay.portal.model.Lock updateLock(
		com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLock(lock);
	}

	public static com.liferay.portal.model.Lock updateLock(
		com.liferay.portal.model.Lock lock, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLock(lock, merge);
	}

	public static void clear()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clear();
	}

	public static com.liferay.portal.model.Lock getLock(
		java.lang.String className, long key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLock(className, key);
	}

	public static com.liferay.portal.model.Lock getLock(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLock(className, key);
	}

	public static boolean hasLock(long userId, java.lang.String className,
		long key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasLock(userId, className, key);
	}

	public static boolean hasLock(long userId, java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasLock(userId, className, key);
	}

	public static boolean isLocked(java.lang.String className, long key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isLocked(className, key);
	}

	public static boolean isLocked(java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isLocked(className, key);
	}

	public static com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, long key, java.lang.String owner,
		boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .lock(userId, className, key, owner, inheritable,
			expirationTime);
	}

	public static com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .lock(userId, className, key, owner, inheritable,
			expirationTime);
	}

	public static com.liferay.portal.model.Lock refresh(java.lang.String uuid,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().refresh(uuid, expirationTime);
	}

	public static void unlock(java.lang.String className, long key)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unlock(className, key);
	}

	public static void unlock(java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unlock(className, key);
	}

	public static LockLocalService getService() {
		if (_service == null) {
			_service = (LockLocalService)PortalBeanLocatorUtil.locate(LockLocalService.class.getName());
		}

		return _service;
	}

	public void setService(LockLocalService service) {
		_service = service;
	}

	private static LockLocalService _service;
}