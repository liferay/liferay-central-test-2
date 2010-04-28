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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.social.model.SocialEquityHistory;

import java.util.List;

/**
 * <a href="SocialEquityHistoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistoryPersistence
 * @see       SocialEquityHistoryPersistenceImpl
 * @generated
 */
public class SocialEquityHistoryUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SocialEquityHistory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquityHistory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquityHistory remove(
		SocialEquityHistory socialEquityHistory) throws SystemException {
		return getPersistence().remove(socialEquityHistory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquityHistory update(
		SocialEquityHistory socialEquityHistory, boolean merge)
		throws SystemException {
		return getPersistence().update(socialEquityHistory, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory) {
		getPersistence().cacheResult(socialEquityHistory);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> socialEquityHistories) {
		getPersistence().cacheResult(socialEquityHistories);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory create(
		long equityHistoryId) {
		return getPersistence().create(equityHistoryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory remove(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException {
		return getPersistence().remove(equityHistoryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory updateImpl(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquityHistory, merge);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory findByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException {
		return getPersistence().findByPrimaryKey(equityHistoryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory fetchByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityHistoryId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityHistoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityHistoryPersistence)PortalBeanLocatorUtil.locate(SocialEquityHistoryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityHistoryPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialEquityHistoryPersistence _persistence;
}