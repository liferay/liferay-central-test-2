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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PluginSetting;

import java.util.List;

/**
 * <a href="PluginSettingUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSettingPersistence
 * @see       PluginSettingPersistenceImpl
 * @generated
 */
public class PluginSettingUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static PluginSetting remove(PluginSetting pluginSetting)
		throws SystemException {
		return getPersistence().remove(pluginSetting);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PluginSetting update(PluginSetting pluginSetting,
		boolean merge) throws SystemException {
		return getPersistence().update(pluginSetting, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.PluginSetting pluginSetting) {
		getPersistence().cacheResult(pluginSetting);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PluginSetting> pluginSettings) {
		getPersistence().cacheResult(pluginSettings);
	}

	public static com.liferay.portal.model.PluginSetting create(
		long pluginSettingId) {
		return getPersistence().create(pluginSettingId);
	}

	public static com.liferay.portal.model.PluginSetting remove(
		long pluginSettingId)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(pluginSettingId);
	}

	public static com.liferay.portal.model.PluginSetting updateImpl(
		com.liferay.portal.model.PluginSetting pluginSetting, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(pluginSetting, merge);
	}

	public static com.liferay.portal.model.PluginSetting findByPrimaryKey(
		long pluginSettingId)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(pluginSettingId);
	}

	public static com.liferay.portal.model.PluginSetting fetchByPrimaryKey(
		long pluginSettingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(pluginSettingId);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.PluginSetting findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.PluginSetting findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.PluginSetting[] findByCompanyId_PrevAndNext(
		long pluginSettingId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(pluginSettingId, companyId, obc);
	}

	public static com.liferay.portal.model.PluginSetting findByC_I_T(
		long companyId, java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_I_T(companyId, pluginId, pluginType);
	}

	public static com.liferay.portal.model.PluginSetting fetchByC_I_T(
		long companyId, java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_I_T(companyId, pluginId, pluginType);
	}

	public static com.liferay.portal.model.PluginSetting fetchByC_I_T(
		long companyId, java.lang.String pluginId, java.lang.String pluginType,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_I_T(companyId, pluginId, pluginType,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_I_T(long companyId, java.lang.String pluginId,
		java.lang.String pluginType)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_I_T(companyId, pluginId, pluginType);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_I_T(long companyId, java.lang.String pluginId,
		java.lang.String pluginType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_I_T(companyId, pluginId, pluginType);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PluginSettingPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PluginSettingPersistence)PortalBeanLocatorUtil.locate(PluginSettingPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PluginSettingPersistence persistence) {
		_persistence = persistence;
	}

	private static PluginSettingPersistence _persistence;
}