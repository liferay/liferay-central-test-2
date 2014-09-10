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

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordSetStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDLRecordSet> {

	public static final String[] CLASS_NAMES = {DDLRecordSet.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDLRecordSet ddlRecordSet = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ddlRecordSet != null) {
			DDLRecordSetLocalServiceUtil.deleteRecordSet(ddlRecordSet);
		}
	}

	@Override
	public DDLRecordSet fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DDLRecordSet> recordSets =
			DDLRecordSetLocalServiceUtil.getDDLRecordSetsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DDLRecordSet>());

		if (ListUtil.isEmpty(recordSets)) {
			return null;
		}

		return recordSets.get(0);
	}

	@Override
	public DDLRecordSet fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return DDLRecordSetLocalServiceUtil.fetchDDLRecordSetByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDLRecordSet recordSet) {
		return recordSet.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDLRecordSet recordSet)
		throws Exception {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, recordSet, ddmStructure,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		Element recordSetElement = portletDataContext.getExportDataElement(
			recordSet);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, recordSet, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		portletDataContext.addClassedModel(
			recordSetElement, ExportImportPathUtil.getModelPath(recordSet),
			recordSet);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long recordSetId)
		throws Exception {

		DDLRecordSet existingRecordSet = fetchMissingReference(uuid, groupId);

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		recordSetIds.put(recordSetId, existingRecordSet.getRecordSetId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDLRecordSet recordSet)
		throws Exception {

		long userId = portletDataContext.getUserId(recordSet.getUserUuid());

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long ddmStructureId = MapUtil.getLong(
			ddmStructureIds, recordSet.getDDMStructureId(),
			recordSet.getDDMStructureId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			recordSet);

		DDLRecordSet importedRecordSet = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDLRecordSet existingRecordSet = fetchStagedModelByUuidAndGroupId(
				recordSet.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRecordSet == null) {
				serviceContext.setUuid(recordSet.getUuid());

				importedRecordSet = DDLRecordSetLocalServiceUtil.addRecordSet(
					userId, portletDataContext.getScopeGroupId(),
					ddmStructureId, recordSet.getRecordSetKey(),
					recordSet.getNameMap(), recordSet.getDescriptionMap(),
					recordSet.getMinDisplayRows(), recordSet.getScope(),
					serviceContext);
			}
			else {
				importedRecordSet =
					DDLRecordSetLocalServiceUtil.updateRecordSet(
						existingRecordSet.getRecordSetId(), ddmStructureId,
						recordSet.getNameMap(), recordSet.getDescriptionMap(),
						recordSet.getMinDisplayRows(), serviceContext);
			}
		}
		else {
			importedRecordSet = DDLRecordSetLocalServiceUtil.addRecordSet(
				userId, portletDataContext.getScopeGroupId(), ddmStructureId,
				recordSet.getRecordSetKey(), recordSet.getNameMap(),
				recordSet.getDescriptionMap(), recordSet.getMinDisplayRows(),
				recordSet.getScope(), serviceContext);
		}

		portletDataContext.importClassedModel(recordSet, importedRecordSet);
	}

}