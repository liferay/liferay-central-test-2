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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMStructureManagerUtil {

	public static void addAttributes(
			long structureId, Document document, DDMFormValues ddmFormValues)
		throws PortalException {

		_ddmStructureManager.addAttributes(
			structureId, document, ddmFormValues);
	}

	public static DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		return _ddmStructureManager.addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	public static void deleteStructure(long structureId)
		throws PortalException {

		_ddmStructureManager.deleteStructure(structureId);
	}

	public static String extractAttributes(
			long structureId, DDMFormValues ddmFormValues, Locale locale)
		throws PortalException {

		return _ddmStructureManager.extractAttributes(
			structureId, ddmFormValues, locale);
	}

	public static DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		return _ddmStructureManager.fetchStructure(
			groupId, classNameId, structureKey);
	}

	public static DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmStructureManager.fetchStructureByUuidAndGroupId(
			uuid, groupId);
	}

	public static List<DDMStructure> getClassStructures(
		long companyId, long classNameId) {

		return _ddmStructureManager.getClassStructures(companyId, classNameId);
	}

	public static List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		return _ddmStructureManager.getClassStructures(
			companyId, classNameId, start, end);
	}

	public static DDMStructure getStructure(long structureId)
		throws PortalException {

		return _ddmStructureManager.getStructure(structureId);
	}

	public static DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		return _ddmStructureManager.getStructure(
			groupId, classNameId, structureKey);
	}

	public static DDMStructure getStructureByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return _ddmStructureManager.getStructureByUuidAndGroupId(uuid, groupId);
	}

	public static DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		return _ddmStructureManager.updateStructure(
			userId, structureId, parentStructureId, nameMap, descriptionMap,
			ddmForm, ddmFormLayout, serviceContext);
	}

	public static void updateStructureKey(long structureId, String structureKey)
		throws PortalException {

		_ddmStructureManager.updateStructureKey(structureId, structureKey);
	}

	private static final DDMStructureManager _ddmStructureManager =
		ProxyFactory.newServiceTrackedInstance(DDMStructureManager.class);

}