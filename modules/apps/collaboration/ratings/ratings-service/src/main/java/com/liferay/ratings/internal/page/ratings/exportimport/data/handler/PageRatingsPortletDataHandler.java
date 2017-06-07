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

package com.liferay.ratings.internal.page.ratings.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;
import com.liferay.ratings.page.ratings.constants.PageRatingsPortletKeys;

import java.util.List;
import java.util.concurrent.Callable;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"javax.portlet.name=" + PageRatingsPortletKeys.PAGE_RATINGS},
	service = PortletDataHandler.class
)
public class PageRatingsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "ratings";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(RatingsEntry.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "ratings-entries", true, false, null,
				RatingsEntry.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL));
		setImportControls(getExportControls());
		setPublishToLiveByDefault(true);
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (!portletDataContext.getBooleanParameter(
				NAMESPACE, "ratings-entries")) {

			return getExportDataRootElementString(rootElement);
		}

		ActionableDynamicQuery actionableDynamicQuery =
			getRatingsEntryActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		PortletDataContext clonedPortletDataContext =
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext);

		ExportImportProcessCallbackRegistryUtil.registerCallback(
			portletDataContext.getExportImportProcessId(),
			new ImportRatingsCallable(clonedPortletDataContext));

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			final PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		final ActionableDynamicQuery actionableDynamicQuery =
			getRatingsEntryCountActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performCount();
	}

	protected long getGroupId(RatingsEntry ratingsEntry)
		throws PortalException {

		PersistedModelLocalService persistedModelLocalService =
			PersistedModelLocalServiceRegistryUtil.
				getPersistedModelLocalService(ratingsEntry.getClassName());

		if (persistedModelLocalService == null) {
			return GroupConstants.DEFAULT_PARENT_GROUP_ID;
		}

		PersistedModel persistedModel = null;

		try {
			persistedModel = persistedModelLocalService.getPersistedModel(
				ratingsEntry.getClassPK());
		}
		catch (NoSuchModelException nsme) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsme.getMessage(), nsme);
			}

			return GroupConstants.DEFAULT_PARENT_GROUP_ID;
		}

		if (!(persistedModel instanceof GroupedModel)) {
			return GroupConstants.DEFAULT_PARENT_GROUP_ID;
		}

		GroupedModel groupedModel = (GroupedModel)persistedModel;

		return groupedModel.getGroupId();
	}

	protected ActionableDynamicQuery getRatingsEntryActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			_ratingsEntryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<RatingsEntry>() {

				@Override
				public void performAction(RatingsEntry ratingsEntry)
					throws PortalException {

					long groupId = getGroupId(ratingsEntry);

					if (groupId != portletDataContext.getScopeGroupId()) {
						return;
					}

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ratingsEntry);
				}

			});

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getRatingsEntryCountActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws PortalException {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ratingsEntryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<RatingsEntry>() {

				@Override
				public void performAction(RatingsEntry ratingsEntry)
					throws PortalException {

					long groupId = getGroupId(ratingsEntry);

					if (groupId != portletDataContext.getScopeGroupId()) {
						return;
					}

					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType =
						exportActionableDynamicQuery.getStagedModelType();

					manifestSummary.incrementModelAdditionCount(
						stagedModelType);
				}

			});
		exportActionableDynamicQuery.setPerformCountMethod(
			new ActionableDynamicQuery.PerformCountMethod() {

				@Override
				public long performCount() throws PortalException {
					exportActionableDynamicQuery.performActions();

					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType =
						exportActionableDynamicQuery.getStagedModelType();

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return manifestSummary.getModelAdditionCount(
						stagedModelType);
				}

			});

		return exportActionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {

		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageRatingsPortletDataHandler.class);

	private RatingsEntryLocalService _ratingsEntryLocalService;

	private static class ImportRatingsCallable implements Callable<Void> {

		public ImportRatingsCallable(PortletDataContext portletDataContext) {
			_portletDataContext = portletDataContext;
		}

		@Override
		public Void call() throws PortalException {
			if (!_portletDataContext.getBooleanParameter(
					NAMESPACE, "ratings-entries")) {

				return null;
			}

			Element entriesElement =
				_portletDataContext.getImportDataGroupElement(
					RatingsEntry.class);

			List<Element> entryElements = entriesElement.elements();

			for (Element entryElement : entryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					_portletDataContext, entryElement);
			}

			return null;
		}

		private final PortletDataContext _portletDataContext;

	}

}