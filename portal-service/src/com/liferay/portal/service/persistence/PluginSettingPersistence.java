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

import com.liferay.portal.model.PluginSetting;

/**
 * <a href="PluginSettingPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSettingPersistenceImpl
 * @see       PluginSettingUtil
 * @generated
 */
public interface PluginSettingPersistence extends BasePersistence<PluginSetting> {
	public void cacheResult(
		com.liferay.portal.model.PluginSetting pluginSetting);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.PluginSetting> pluginSettings);

	public com.liferay.portal.model.PluginSetting create(long pluginSettingId);

	public com.liferay.portal.model.PluginSetting remove(long pluginSettingId)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting updateImpl(
		com.liferay.portal.model.PluginSetting pluginSetting, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting findByPrimaryKey(
		long pluginSettingId)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting fetchByPrimaryKey(
		long pluginSettingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting[] findByCompanyId_PrevAndNext(
		long pluginSettingId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting findByC_I_T(long companyId,
		java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting fetchByC_I_T(long companyId,
		java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PluginSetting fetchByC_I_T(long companyId,
		java.lang.String pluginId, java.lang.String pluginType,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_I_T(long companyId, java.lang.String pluginId,
		java.lang.String pluginType)
		throws com.liferay.portal.NoSuchPluginSettingException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_I_T(long companyId, java.lang.String pluginId,
		java.lang.String pluginType)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}