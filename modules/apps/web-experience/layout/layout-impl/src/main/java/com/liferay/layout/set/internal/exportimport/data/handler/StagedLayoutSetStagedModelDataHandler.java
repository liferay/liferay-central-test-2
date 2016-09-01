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

package com.liferay.layout.set.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.lar.ThemeExporter;
import com.liferay.exportimport.lar.ThemeImporter;
import com.liferay.layout.set.internal.exportimport.staged.model.repository.StagedLayoutSetStagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.io.File;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedLayoutSetStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedLayoutSet> {

	public static final String[] CLASS_NAMES =
		{StagedLayoutSet.class.getName()};

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws Exception {

		exportLayouts(portletDataContext, stagedLayoutSet);
		exportLogo(portletDataContext, stagedLayoutSet);
		exportTheme(portletDataContext, stagedLayoutSet);

		// Serialization

		Element stagedLayoutSetElement =
			portletDataContext.getExportDataElement(stagedLayoutSet);

		portletDataContext.addClassedModel(
			stagedLayoutSetElement,
			ExportImportPathUtil.getModelPath(stagedLayoutSet),
			stagedLayoutSet);

		// Last Publish Date

		boolean updateLastPublishDate = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (ExportImportThreadLocal.isStagingInProcess() &&
			updateLastPublishDate) {

			ExportImportProcessCallbackRegistryUtil.registerCallback(
				new UpdateLayoutSetLastPublishDateCallable(
					portletDataContext.getDateRange(),
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout()));
		}
	}

	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws Exception {

		Optional<StagedLayoutSet> existingLayoutSetOptional =
			_stagedLayoutSetStagedModelRepository.fetchExistingLayoutSet(
				portletDataContext.getScopeGroupId(),
				stagedLayoutSet.isPrivateLayout());

		StagedLayoutSet importedStagedLayoutSet =
			(StagedLayoutSet)stagedLayoutSet.clone();

		importedStagedLayoutSet.setGroupId(
			portletDataContext.getScopeGroupId());

		if (existingLayoutSetOptional.isPresent()) {
			importedStagedLayoutSet =
				_stagedLayoutSetStagedModelRepository.updateStagedModel(
					portletDataContext, importedStagedLayoutSet);
		}

		importLogo(portletDataContext);
		importTheme(portletDataContext, stagedLayoutSet);

		portletDataContext.importClassedModel(
			stagedLayoutSet, importedStagedLayoutSet);
	}

	protected void exportLayouts(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		// Force to always have a Layout group element

		portletDataContext.getExportDataGroupElement(Layout.class);

		List<StagedModel> stagedModels =
			_stagedLayoutSetStagedModelRepository.fetchChildrenStagedModels(
				portletDataContext, stagedLayoutSet);

		for (StagedModel stagedModel : stagedModels) {
			Layout layout = (Layout)stagedModel;

			try {
				if (!LayoutStagingUtil.prepareLayoutStagingHandler(
						portletDataContext, layout)) {

					return;
				}

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedLayoutSet, layout,
					PortletDataContext.REFERENCE_TYPE_CHILD);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Cannot export layout " + layout.getName(), e);
				}
			}
		}
	}

	protected void exportLogo(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		boolean logo = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.LOGO);

		if (!logo) {
			return;
		}

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				layoutSetBranchId);

		Image image = null;

		if (layoutSetBranch != null) {
			try {
				image = _imageLocalService.getImage(
					layoutSetBranch.getLogoId());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Cannot retrieve logo for layout set branch " +
							layoutSetBranch.getLayoutSetBranchId(),
						pe);
				}
			}
		}
		else {
			try {
				image = _imageLocalService.getImage(
					stagedLayoutSet.getLogoId());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Cannot retrieve logo for layout set " +
							stagedLayoutSet.getLayoutSetId(),
						pe);
				}
			}
		}

		if ((image != null) && (image.getTextObj() != null)) {
			String logoPath = ExportImportPathUtil.getRootPath(
				portletDataContext);

			logoPath += "/logo";

			Element rootElement = portletDataContext.getExportDataRootElement();

			Element headerElement = rootElement.element("header");

			headerElement.addAttribute("logo-path", logoPath);

			portletDataContext.addZipEntry(logoPath, image.getTextObj());
		}
	}

	protected void exportTheme(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				layoutSetBranchId);

		ThemeExporter themeExporter = ThemeExporter.getInstance();

		if (layoutSetBranch != null) {
			try {
				themeExporter.exportTheme(portletDataContext, layoutSetBranch);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Cannot export theme reference for layout set branch " +
							layoutSetBranch.getLayoutSetBranchId(),
						e);
				}
			}
		}
		else {
			try {
				themeExporter.exportTheme(portletDataContext, stagedLayoutSet);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Cannot export theme reference for layout set " +
							stagedLayoutSet.getLayoutSetId(),
						e);
				}
			}
		}
	}

	protected void importLogo(PortletDataContext portletDataContext) {
		boolean logo = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), PortletDataHandlerKeys.LOGO);

		if (!logo) {
			return;
		}

		Element rootElement = portletDataContext.getExportDataRootElement();

		Element headerElement = rootElement.element("header");

		String logoPath = headerElement.attributeValue("logo-path");

		byte[] iconBytes = portletDataContext.getZipEntryAsByteArray(logoPath);

		try {
			if (ArrayUtil.isNotEmpty(iconBytes)) {
				_layoutSetLocalService.updateLogo(
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout(), true, iconBytes);
			}
			else {
				_layoutSetLocalService.updateLogo(
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout(), false, (File)null);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot import logo", pe);
			}
		}
	}

	protected void importTheme(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		ThemeImporter themeImporter = ThemeImporter.getInstance();

		try {
			themeImporter.importTheme(portletDataContext, stagedLayoutSet);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cannot import theme reference " +
						stagedLayoutSet.getThemeId(),
					e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedLayoutSetStagedModelDataHandler.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private StagedLayoutSetStagedModelRepository
		_stagedLayoutSetStagedModelRepository;

	private class UpdateLayoutSetLastPublishDateCallable
		implements Callable<Void> {

		public UpdateLayoutSetLastPublishDateCallable(
			DateRange dateRange, long groupId, boolean privateLayout) {

			_dateRange = dateRange;
			_groupId = groupId;
			_privateLayout = privateLayout;
		}

		@Override
		public Void call() throws PortalException {
			Group group = _groupLocalService.getGroup(_groupId);

			Date endDate = null;

			if (_dateRange != null) {
				endDate = _dateRange.getEndDate();
			}

			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				ExportImportDateUtil.updateLastPublishDate(
					stagingGroup.getGroupId(), _privateLayout, _dateRange,
					endDate);
			}
			else {
				ExportImportDateUtil.updateLastPublishDate(
					_groupId, _privateLayout, _dateRange, endDate);
			}

			return null;
		}

		private final DateRange _dateRange;
		private final long _groupId;
		private final boolean _privateLayout;

	}

}