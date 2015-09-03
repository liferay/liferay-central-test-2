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

package com.liferay.document.library.lar;

import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DLFileEntryTypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<DLFileEntryType> {

	public static final String[] CLASS_NAMES =
		{DLFileEntryType.class.getName()};

	@Override
	public void deleteStagedModel(DLFileEntryType fileEntryType)
		throws PortalException {

		DLFileEntryTypeLocalServiceUtil.deleteFileEntryType(fileEntryType);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DLFileEntryType dlFileEntryType = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (dlFileEntryType != null) {
			deleteStagedModel(dlFileEntryType);
		}
	}

	@Override
	public DLFileEntryType fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return DLFileEntryTypeLocalServiceUtil.
			fetchDLFileEntryTypeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DLFileEntryType> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return DLFileEntryTypeLocalServiceUtil.
			getDLFileEntryTypesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFileEntryType>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, DLFileEntryType fileEntryType) {

		Map<String, String> referenceAttributes = new HashMap<>();

		referenceAttributes.put(
			"file-entry-type-key", fileEntryType.getFileEntryTypeKey());

		long defaultUserId = UserConstants.USER_ID_DEFAULT;

		try {
			defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				fileEntryType.getCompanyId());
		}
		catch (Exception e) {
		}

		boolean preloaded = false;

		if ((fileEntryType.getFileEntryTypeId() ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) ||
			(defaultUserId == fileEntryType.getUserId())) {

			preloaded = true;
		}

		referenceAttributes.put("preloaded", String.valueOf(preloaded));

		return referenceAttributes;
	}

	@Override
	public void importMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		String fileEntryTypeKey = referenceElement.attributeValue(
			"file-entry-type-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DLFileEntryType existingFileEntryType = null;

		existingFileEntryType = fetchExistingFileEntryType(
			uuid, groupId, fileEntryTypeKey, preloaded);

		Map<Long, Long> fileEntryTypeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntryType.class);

		long fileEntryTypeId = GetterUtil.getLong(
			referenceElement.attributeValue("class-pk"));

		fileEntryTypeIds.put(
			fileEntryTypeId, existingFileEntryType.getFileEntryTypeId());
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		validateMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		String fileEntryTypeKey = referenceElement.attributeValue(
			"file-entry-type-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DLFileEntryType existingFileEntryType = fetchExistingFileEntryType(
			uuid, groupId, fileEntryTypeKey, preloaded);

		if (existingFileEntryType == null) {
			return false;
		}

		return true;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws Exception {

		Element fileEntryTypeElement = portletDataContext.getExportDataElement(
			fileEntryType);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			com.liferay.dynamic.data.mapping.model.DDMStructure
				structure = _ddmStructureLocalService.getStructure(
					ddmStructure.getStructureId());

			Element referenceElement =
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, fileEntryType, structure,
					PortletDataContext.REFERENCE_TYPE_STRONG);

			referenceElement.addAttribute(
				"structure-id",
				StringUtil.valueOf(ddmStructure.getStructureId()));
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			fileEntryType.getCompanyId());

		if (defaultUserId == fileEntryType.getUserId()) {
			fileEntryTypeElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			fileEntryTypeElement,
			ExportImportPathUtil.getModelPath(fileEntryType), fileEntryType);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws Exception {

		long userId = portletDataContext.getUserId(fileEntryType.getUserUuid());

		List<Element> ddmStructureReferenceElements =
			portletDataContext.getReferenceElements(
				fileEntryType,
				com.liferay.dynamic.data.mapping.model.DDMStructure.class);

		long[] ddmStructureIdsArray =
			new long[ddmStructureReferenceElements.size()];

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				com.liferay.dynamic.data.mapping.model.DDMStructure.class);

		for (int i = 0; i < ddmStructureReferenceElements.size(); i++) {
			Element ddmStructureReferenceElement =
				ddmStructureReferenceElements.get(i);

			long ddmStructureId = GetterUtil.getLong(
				ddmStructureReferenceElement.attributeValue("class-pk"));

			ddmStructureIdsArray[i] = MapUtil.getLong(
				ddmStructureIds, ddmStructureId);
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntryType);

		DLFileEntryType importedDLFileEntryType = null;

		Element element = portletDataContext.getImportDataStagedModelElement(
			fileEntryType);

		boolean preloaded = GetterUtil.getBoolean(
			element.attributeValue("preloaded"));

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileEntryType existingDLFileEntryType =
				fetchExistingFileEntryType(
					fileEntryType.getUuid(),
					portletDataContext.getScopeGroupId(),
					fileEntryType.getFileEntryTypeKey(), preloaded);

			if (existingDLFileEntryType == null) {
				serviceContext.setUuid(fileEntryType.getUuid());

				importedDLFileEntryType =
					DLFileEntryTypeLocalServiceUtil.addFileEntryType(
						userId, portletDataContext.getScopeGroupId(),
						fileEntryType.getFileEntryTypeKey(),
						fileEntryType.getNameMap(),
						fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
						serviceContext);
			}
			else {
				DLFileEntryTypeLocalServiceUtil.updateFileEntryType(
					userId, existingDLFileEntryType.getFileEntryTypeId(),
					fileEntryType.getNameMap(),
					fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
					serviceContext);

				importedDLFileEntryType =
					DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
						existingDLFileEntryType.getFileEntryTypeId());
			}
		}
		else {
			importedDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					userId, portletDataContext.getScopeGroupId(),
					fileEntryType.getFileEntryTypeKey(),
					fileEntryType.getNameMap(),
					fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
					serviceContext);
		}

		portletDataContext.importClassedModel(
			fileEntryType, importedDLFileEntryType);

		if (preloaded) {
			return;
		}

		String importedDLFileEntryDDMStructureKey = DLUtil.getDDMStructureKey(
			importedDLFileEntryType);

		List<DDMStructure> importedDDMStructures =
			importedDLFileEntryType.getDDMStructures();

		for (DDMStructure importedDDMStructure : importedDDMStructures) {
			String ddmStructureKey = importedDDMStructure.getStructureKey();

			if (!DLUtil.isAutoGeneratedDLFileEntryTypeDDMStructureKey(
					ddmStructureKey)) {

				continue;
			}

			if (ddmStructureKey.equals(importedDLFileEntryDDMStructureKey)) {
				continue;
			}

			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(
					importedDDMStructure.getStructureId());

			ddmStructure.setStructureKey(importedDLFileEntryDDMStructureKey);

			_ddmStructureLocalService.updateDDMStructure(ddmStructure);
		}
	}

	protected DLFileEntryType fetchExistingFileEntryType(
		String uuid, long groupId, String fileEntryTypeKey, boolean preloaded) {

		DLFileEntryType existingDLFileEntryType = null;

		if (!preloaded) {
			existingDLFileEntryType = fetchStagedModelByUuidAndGroupId(
				uuid, groupId);
		}
		else {
			existingDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.fetchFileEntryType(
					groupId, fileEntryTypeKey);
		}

		return existingDLFileEntryType;
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private DDMStructureLocalService _ddmStructureLocalService;

}