/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.exportimport.service.persistence.impl;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.persistence.ExportImportConfigurationPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExportImportConfigurationFinderBaseImpl extends BasePersistenceImpl<ExportImportConfiguration> {
	public ExportImportConfigurationFinderBaseImpl() {
		setModelClass(ExportImportConfiguration.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");
			dbColumnNames.put("settings", "settings_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getExportImportConfigurationPersistence().getBadColumnNames();
	}

	/**
	 * Returns the export import configuration persistence.
	 *
	 * @return the export import configuration persistence
	 */
	public ExportImportConfigurationPersistence getExportImportConfigurationPersistence() {
		return exportImportConfigurationPersistence;
	}

	/**
	 * Sets the export import configuration persistence.
	 *
	 * @param exportImportConfigurationPersistence the export import configuration persistence
	 */
	public void setExportImportConfigurationPersistence(
		ExportImportConfigurationPersistence exportImportConfigurationPersistence) {
		this.exportImportConfigurationPersistence = exportImportConfigurationPersistence;
	}

	@BeanReference(type = ExportImportConfigurationPersistence.class)
	protected ExportImportConfigurationPersistence exportImportConfigurationPersistence;
	private static final Log _log = LogFactoryUtil.getLog(ExportImportConfigurationFinderBaseImpl.class);
}