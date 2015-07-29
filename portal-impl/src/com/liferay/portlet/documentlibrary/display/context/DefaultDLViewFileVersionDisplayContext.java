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

package com.liferay.portlet.documentlibrary.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.portlet.documentlibrary.display.context.logic.DLVisualizationHelper;
import com.liferay.portlet.documentlibrary.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.portlet.documentlibrary.display.context.logic.FileVersionDisplayContextHelper;
import com.liferay.portlet.documentlibrary.display.context.logic.UIItemsBuilder;
import com.liferay.portlet.documentlibrary.display.context.util.DLRequestHelper;
import com.liferay.portlet.documentlibrary.display.context.util.JSPRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.StorageEngineManagerUtil;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DefaultDLViewFileVersionDisplayContext
	implements DLViewFileVersionDisplayContext {

	public DefaultDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileShortcut fileShortcut)
		throws PortalException {

		this(request, response, fileShortcut.getFileVersion(), fileShortcut);
	}

	public DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		this(request, response, fileVersion, null);
	}

	@Override
	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
				ddmStructure.getStructureId(), _fileVersion.getFileVersionId());

		return StorageEngineManagerUtil.getDDMFormValues(
			dlFileEntryMetadata.getDDMStorageId());
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		if (_fileVersionDisplayContextHelper.isDLFileVersion()) {
			DLFileVersion dlFileVersion =
				(DLFileVersion)_fileVersion.getModel();

			return dlFileVersion.getDDMStructures();
		}

		return Collections.emptyList();
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = new Menu();

		String direction = "left";

		if (_dlVisualizationHelper.isShowMinimalActionsButton()) {
			direction = "down";
		}

		menu.setDirection(direction);

		boolean extended = true;

		if (_dlVisualizationHelper.isShowMinimalActionsButton()) {
			extended = false;
		}

		menu.setExtended(extended);

		String icon = null;

		if (_dlVisualizationHelper.isShowMinimalActionsButton()) {
			icon = StringPool.BLANK;
		}

		menu.setIcon(icon);

		menu.setMenuItems(_getMenuItems());

		String message = "actions";

		if (_dlVisualizationHelper.isShowMinimalActionsButton()) {
			message = StringPool.BLANK;
		}

		menu.setMessage(message);

		menu.setShowWhenSingleIcon(
			_dlVisualizationHelper.isShowWhenSingleIconActionButton());
		menu.setTriggerCssClass("btn btn-default");

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		_uiItemsBuilder.addDownloadToolbarItem(toolbarItems);

		_uiItemsBuilder.addOpenInMsOfficeToolbarItem(toolbarItems);

		_uiItemsBuilder.addEditToolbarItem(toolbarItems);

		_uiItemsBuilder.addMoveToolbarItem(toolbarItems);

		_uiItemsBuilder.addCheckoutToolbarItem(toolbarItems);

		_uiItemsBuilder.addCancelCheckoutToolbarItem(toolbarItems);

		_uiItemsBuilder.addCheckinToolbarItem(toolbarItems);

		_uiItemsBuilder.addPermissionsToolbarItem(toolbarItems);

		_uiItemsBuilder.addMoveToTheRecycleBinToolbarItem(toolbarItems);

		_uiItemsBuilder.addDeleteToolbarItem(toolbarItems);

		return toolbarItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isDownloadLinkVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isDownloadActionAvailable();
	}

	@Override
	public boolean isVersionInfoVisible() {
		return true;
	}

	@Override
	public void renderPreview(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		JSPRenderer jspRenderer = new JSPRenderer(
			"/html/portlet/document_library/view_file_entry_preview.jsp");

		jspRenderer.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, _fileVersion);

		jspRenderer.render(request, response);
	}

	private DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, FileShortcut fileShortcut) {

		try {
			_fileVersion = fileVersion;

			DLRequestHelper dlRequestHelper = new DLRequestHelper(request);

			_dlVisualizationHelper = new DLVisualizationHelper(dlRequestHelper);

			_dlPortletInstanceSettingsHelper =
				new DLPortletInstanceSettingsHelper(dlRequestHelper);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				dlRequestHelper.getPermissionChecker(),
				_getFileEntry(fileVersion));

			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(fileVersion);

			if (fileShortcut == null) {
				_uiItemsBuilder = new UIItemsBuilder(
					request, response, fileVersion);
			}
			else {
				_uiItemsBuilder = new UIItemsBuilder(
					request, response, fileShortcut);
			}
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build DefaultDLViewFileVersionDisplayContext for " +
					fileVersion,
				pe);
		}
	}

	private FileEntry _getFileEntry(FileVersion fileVersion)
		throws PortalException {

		if (fileVersion != null) {
			return fileVersion.getFileEntry();
		}

		return null;
	}

	private List<MenuItem> _getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<>();

		if (_dlPortletInstanceSettingsHelper.isShowActions()) {
			_uiItemsBuilder.addDownloadMenuItem(menuItems);

			_uiItemsBuilder.addOpenInMsOfficeMenuItem(menuItems);

			_uiItemsBuilder.addViewOriginalFileMenuItem(menuItems);

			_uiItemsBuilder.addEditMenuItem(menuItems);

			_uiItemsBuilder.addMoveMenuItem(menuItems);

			_uiItemsBuilder.addCheckoutMenuItem(menuItems);

			_uiItemsBuilder.addCheckinMenuItem(menuItems);

			_uiItemsBuilder.addCancelCheckoutMenuItem(menuItems);

			_uiItemsBuilder.addPermissionsMenuItem(menuItems);

			_uiItemsBuilder.addDeleteMenuItem(menuItems);
		}

		return menuItems;
	}

	private static final UUID _UUID = UUID.fromString(
		"85F6C50E-3893-4E32-9D63-208528A503FA");

	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final DLVisualizationHelper _dlVisualizationHelper;
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private final FileVersion _fileVersion;
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;
	private final UIItemsBuilder _uiItemsBuilder;

}