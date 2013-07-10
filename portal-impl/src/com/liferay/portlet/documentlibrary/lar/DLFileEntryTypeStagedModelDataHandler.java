/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class DLFileEntryTypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<DLFileEntryType> {

	public static final String[] CLASS_NAMES =
		{DLFileEntryType.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
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
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, ddmStructure);

			portletDataContext.addReferenceElement(
				fileEntryType, fileEntryTypeElement, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG, false);
		}

		portletDataContext.addClassedModel(
			fileEntryTypeElement,
			ExportImportPathUtil.getModelPath(fileEntryType), fileEntryType,
			DLPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws Exception {

		long userId = portletDataContext.getUserId(fileEntryType.getUserUuid());

		List<Element> ddmStructureElements =
			portletDataContext.getReferenceDataElements(
				fileEntryType, DDMStructure.class);

		for (Element ddmStructureElement : ddmStructureElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmStructureElement);
		}

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		long[] ddmStructureIdsArray = new long[ddmStructures.size()];

		for (int i = 0; i < ddmStructures.size(); i++) {
			DDMStructure ddmStructure = ddmStructures.get(i);

			ddmStructureIdsArray[i] = MapUtil.getLong(
				ddmStructureIds, ddmStructure.getStructureId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntryType, DLPortletDataHandler.NAMESPACE);

		DLFileEntryType importedDLFileEntryType = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileEntryType existingDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.
					fetchDLFileEntryTypeByUuidAndGroupId(
						fileEntryType.getUuid(),
						portletDataContext.getScopeGroupId());

			if (existingDLFileEntryType == null) {
				existingDLFileEntryType =
					DLFileEntryTypeLocalServiceUtil.
						fetchDLFileEntryTypeByUuidAndGroupId(
							fileEntryType.getUuid(),
							portletDataContext.getCompanyGroupId());
			}

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
				if (!isFileEntryTypeGlobal(
						portletDataContext.getCompanyId(),
						existingDLFileEntryType)) {

					DLFileEntryTypeLocalServiceUtil.updateFileEntryType(
						userId, existingDLFileEntryType.getFileEntryTypeId(),
						fileEntryType.getNameMap(),
						fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
						serviceContext);
				}

				importedDLFileEntryType = existingDLFileEntryType;
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

		if (isFileEntryTypeGlobal(
				portletDataContext.getCompanyId(), importedDLFileEntryType)) {

			return;
		}

		portletDataContext.importClassedModel(
			fileEntryType, importedDLFileEntryType,
			DLPortletDataHandler.NAMESPACE);

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

			importedDDMStructure.setStructureKey(
				importedDLFileEntryDDMStructureKey);

			DDMStructureLocalServiceUtil.updateDDMStructure(
				importedDDMStructure);
		}
	}

	protected boolean isFileEntryTypeGlobal(
			long companyId, DLFileEntryType dlFileEntryType)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

		if (dlFileEntryType.getGroupId() == group.getGroupId()) {
			return true;
		}

		return false;
	}

}