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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordImpl;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetImpl;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLPermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateImpl;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class DDLPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "dynamic_data_lists";

	public DDLPortletDataHandler() {
		setDataLocalized(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DDLRecord.class),
			new StagedModelType(DDLRecordSet.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "record-sets", true, false, null,
				DDLRecordSet.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "records", true, false, null,
				DDLRecord.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "data-definitions", true, false, null,
				DDMStructure.class.getName(), DDLRecordSet.class.getName()));

		XStreamAliasRegistryUtil.register(DDLRecordImpl.class, "DDLRecord");
		XStreamAliasRegistryUtil.register(
			DDLRecordSetImpl.class, "DDLRecordSet");
		XStreamAliasRegistryUtil.register(
			DDMStructureImpl.class, "DDMStructure");
		XStreamAliasRegistryUtil.register(DDMTemplateImpl.class, "DDMTemplate");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DDLPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		DDLRecordSetLocalServiceUtil.deleteRecordSets(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(DDLPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "data-definitions")) {

			List<DDMTemplate> ddmTemplates = new ArrayList<>();

			ActionableDynamicQuery ddmStructureActionableDynamicQuery =
				getDDMStructureActionableDynamicQuery(
					portletDataContext, ddmTemplates);

			ddmStructureActionableDynamicQuery.performActions();

			for (DDMTemplate ddmTemplate : ddmTemplates) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmTemplate);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "record-sets")) {
			ActionableDynamicQuery recordSetActionableDynamicQuery =
				DDLRecordSetLocalServiceUtil.getExportActionableDynamicQuery(
					portletDataContext);

			recordSetActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "records")) {
			ActionableDynamicQuery recordActionableDynamicQuery =
				getRecordActionableDynamicQuery(portletDataContext);

			recordActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			DDLPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "data-definitions")) {

			Element ddmStructuresElement =
				portletDataContext.getImportDataGroupElement(
					DDMStructure.class);

			List<Element> ddmStructureElements =
				ddmStructuresElement.elements();

			for (Element ddmStructureElement : ddmStructureElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmStructureElement);
			}

			Element ddmTemplatesElement =
				portletDataContext.getImportDataGroupElement(DDMTemplate.class);

			List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

			for (Element ddmTemplateElement : ddmTemplateElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmTemplateElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "record-sets")) {
			Element recordSetsElement =
				portletDataContext.getImportDataGroupElement(
					DDLRecordSet.class);

			List<Element> recordSetElements = recordSetsElement.elements();

			for (Element recordSetElement : recordSetElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, recordSetElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "records")) {
			Element recordsElement =
				portletDataContext.getImportDataGroupElement(DDLRecord.class);

			List<Element> recordElements = recordsElement.elements();

			for (Element recordElement : recordElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, recordElement);
			}
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		List<DDMTemplate> ddmTemplates = new ArrayList<>();

		ActionableDynamicQuery ddmStructureActionableDynamicQuery =
			getDDMStructureActionableDynamicQuery(
				portletDataContext, ddmTemplates);

		ddmStructureActionableDynamicQuery.performCount();

		ActionableDynamicQuery recordSetActionableDynamicQuery =
			DDLRecordSetLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		recordSetActionableDynamicQuery.performCount();

		ActionableDynamicQuery recordActionableDynamicQuery =
			getRecordActionableDynamicQuery(portletDataContext);

		recordActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getDDMStructureActionableDynamicQuery(
		final PortletDataContext portletDataContext,
		final List<DDMTemplate> ddmTemplates) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			DDMStructureLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long classNameId = PortalUtil.getClassNameId(
						DDLRecordSet.class);

					dynamicQuery.add(classNameIdProperty.eq(classNameId));
				}

			});
		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DDMStructure ddmStructure = (DDMStructure)object;

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmStructure);

					try {
						ddmTemplates.addAll(ddmStructure.getTemplates());
					}
					catch (SystemException se) {
					}
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				DDMStructure.class.getName(), DDLRecordSet.class.getName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getRecordActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			DDLRecordLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property recordIdProperty = PropertyFactoryUtil.forName(
						"recordId");

					DynamicQuery recordVersionDynamicQuery =
						DynamicQueryFactoryUtil.forClass(
							DDLRecordVersion.class, "recordVersion",
							PortalClassLoaderUtil.getClassLoader());

					recordVersionDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("recordId"));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					StagedModelDataHandler<?> stagedModelDataHandler =
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
								DDLRecord.class.getName());

					recordVersionDynamicQuery.add(
						statusProperty.in(
							stagedModelDataHandler.getExportableStatuses()));

					recordVersionDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"recordVersion.version", "version"));

					recordVersionDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"recordVersion.recordId", "recordId"));

					dynamicQuery.add(
						recordIdProperty.in(recordVersionDynamicQuery));
				}

			});

		return actionableDynamicQuery;
	}

}