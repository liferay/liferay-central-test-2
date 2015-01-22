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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.DeleteMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavascriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavascriptToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavascriptUIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLUIItem;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.context.helper.FileEntryDisplayContextHelper;
import com.liferay.portlet.documentlibrary.context.helper.FileVersionDisplayContextHelper;
import com.liferay.portlet.documentlibrary.context.util.JSPRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDefaultDLViewFileVersionDisplayContext
	implements DLViewFileVersionDisplayContext {

	public BaseDefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DLFileShortcut dlFileShortcut) {

		this(
			request, response, _getFileVersion(dlFileShortcut), dlFileShortcut);
	}

	public BaseDefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		this(request, response, fileVersion, null);
	}

	@Override
	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
				ddmStructure.getStructureId(), fileVersion.getFileVersionId());

		return StorageEngineUtil.getDDMFormValues(
			dlFileEntryMetadata.getDDMStorageId());
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		if (_fileVersionDisplayContextHelper.isDLFileVersion()) {
			DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

			return dlFileVersion.getDDMStructures();
		}

		return Collections.emptyList();
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<>();

		if (_isShowActions()) {
			buildMenuItems(menuItems);
		}

		return menuItems;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		_addDownloadToolbarItem(toolbarItems);

		_addOpenInMsOfficeToolbarItem(toolbarItems);

		_addEditToolbarItem(toolbarItems);

		_addMoveToolbarItem(toolbarItems);

		_addCheckoutToolbarItem(toolbarItems);

		_addCancelCheckoutToolbarItem(toolbarItems);

		_addCheckinToolbarItem(toolbarItems);

		_addPermissionsToolbarItem(toolbarItems);

		_addMoveToTheRecycleBinToolbarItem(toolbarItems);

		_addDeleteToolbarItem(toolbarItems);

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
	public boolean isVersionInfoVisible() throws PortalException {
		return true;
	}

	@Override
	public void renderPreview(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		JSPRenderer jspRenderer = new JSPRenderer(
			"/html/portlet/document_library/view_file_entry_preview.jsp");

		jspRenderer.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

		jspRenderer.render(request, response);
	}

	protected void addCancelCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable()) {

			return;
		}

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-remove",
			DLUIItemKeys.CANCEL_CHECKOUT, "cancel-checkout[document]",
			_getActionURL(
				"/document_library/edit_file_entry",
				Constants.CANCEL_CHECKOUT));
	}

	protected void addCheckinMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isCheckinActionAvailable()) {
			return;
		}

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-lock", DLUIItemKeys.CHECKIN,
			"checkin",
			_getActionURL(
				"/document_library/edit_file_entry", Constants.CHECKIN));
	}

	protected void addCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCheckoutDocumentActionAvailable()) {

			return;
		}

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-unlock", DLUIItemKeys.CHECKOUT,
			"checkout[document]",
			_getActionURL(
				"/document_library/edit_file_entry", Constants.CHECKOUT));
	}

	protected void addDeleteMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (isDeleteActionAvailable()) {
			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(DLUIItemKeys.DELETE);
			deleteMenuItem.setURL(
				_getActionURL(
					"/document_library/edit_file_entry", Constants.DELETE));

			menuItems.add(deleteMenuItem);
		}
		else if (isMoveToTheRecycleBinActionAvailable()) {
			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(DLUIItemKeys.DELETE);
			deleteMenuItem.setTrash(true);
			deleteMenuItem.setURL(
				_getActionURL(
					"/document_library/edit_file_entry",
					Constants.MOVE_TO_TRASH));

			menuItems.add(deleteMenuItem);
		}
	}

	protected void addDownloadMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isDownloadActionAvailable()) {
			return;
		}

		String label = TextFormatter.formatStorageSize(
			fileEntry.getSize(), _themeDisplay.getLocale());

		label = _themeDisplay.translate("download") + " (" + label + ")";

		String url = DLUtil.getDownloadURL(
			fileEntry, fileVersion, _themeDisplay, StringPool.BLANK, false,
			true);

		URLMenuItem urlMenuItem = _addURLUIItem(
			new URLMenuItem(), menuItems, "icon-download",
			DLUIItemKeys.DOWNLOAD, label, url);

		urlMenuItem.setMethod("get");
		urlMenuItem.setTarget("_blank");
	}

	protected void addEditMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isEditActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/edit_file_entry");

		portletURL.setParameter("backURL", _getCurrentURL());

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-edit", DLUIItemKeys.EDIT,
			"edit", portletURL.toString());
	}

	protected void addMoveMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isMoveActionAvailable()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/move_entry");

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectURL.setParameter("struts_action", "/document_library/view");
		redirectURL.setParameter("folderId", String.valueOf(_folderId));

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter(
			"fileEntryIds", String.valueOf(fileEntry.getFileEntryId()));

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-move", DLUIItemKeys.MOVE,
			"move", portletURL.toString());
	}

	protected void addOpenInMsOfficeMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isOpenInMsOfficeActionAvailable()) {
			return;
		}

		String webDavURL = DLUtil.getWebDavURL(
			_themeDisplay, fileEntry.getFolder(), fileEntry,
			PropsValues.
				DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED,
			true);

		String onClick = getNamespace() + "openDocument('" + webDavURL + "');";

		JavascriptMenuItem javascriptMenuItem = _addJavascriptUIItem(
			new JavascriptMenuItem(), menuItems, "icon-file-alt",
			DLUIItemKeys.OPEN_IN_MS_OFFICE, "open-in-ms-office", onClick);

		Map<String, String> context = new HashMap<>();

		context.put(
			"errorMessage", UnicodeLanguageUtil.get(
				request,
				"cannot-open-the-requested-document-due-to-the-following-" +
					"reason"));
		context.put("namespace", getNamespace());

		String javaScript = _processFreeMarkerTemplate(
			"/com/liferay/portlet/documentlibrary/context/dependencies" +
				"/open_in_ms_office_js.ftl",
			context);

		javascriptMenuItem.setJavascript(javaScript);
	}

	protected void addPermissionsMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isPermissionsButtonVisible()) {
			return;
		}

		String url = null;

		try {
			url = PermissionsURLTag.doTag(
				null, DLFileEntryConstants.getClassName(),
				HtmlUtil.unescape(fileEntry.getTitle()), null,
				String.valueOf(fileEntry.getFileEntryId()),
				LiferayWindowState.POP_UP.toString(), null, request);
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		URLMenuItem urlMenuItem = _addURLUIItem(
			new URLMenuItem(), menuItems, "icon-lock", DLUIItemKeys.PERMISSIONS,
			"permissions", url);

		urlMenuItem.setMethod("get");
		urlMenuItem.setUseDialog(true);
	}

	protected void addViewOriginalFileMenuItem(List<MenuItem> menuItems) {
		if (dlFileShortcut == null) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/view_file_entry");

		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(dlFileShortcut.getToFileEntryId()));

		_addURLUIItem(
			new URLMenuItem(), menuItems, "icon-search",
			DLUIItemKeys.VIEW_ORIGINAL_FILE, "view-original-file",
			portletURL.toString());
	}

	protected abstract void buildMenuItems(List<MenuItem> menuItems)
		throws PortalException;

	protected String getNamespace() {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		return liferayPortletResponse.getNamespace();
	}

	protected String getSubmitFormJavascript(String cmd, String redirect) {
		StringBundler sb = new StringBundler(18);

		sb.append("document.");
		sb.append(getNamespace());
		sb.append("fm.");
		sb.append(getNamespace());
		sb.append(Constants.CMD);
		sb.append(".value = '");
		sb.append(cmd);
		sb.append("';");

		if (redirect != null) {
			sb.append("document.");
			sb.append(getNamespace());
			sb.append("fm.");
			sb.append(getNamespace());
			sb.append("redirect.value = '");
			sb.append(redirect);
			sb.append("';");
		}

		sb.append("submitForm(document.");
		sb.append(getNamespace());
		sb.append("fm);");

		return sb.toString();
	}

	protected boolean isDeleteActionAvailable() throws PortalException {
		if (_fileEntryDisplayContextHelper.isFileEntryDeletable() &&
			!_isFileEntryTrashable()) {

			return true;
		}

		return false;
	}

	protected boolean isMoveToTheRecycleBinActionAvailable()
		throws PortalException {

		if (!isDeleteActionAvailable() &&
			_fileEntryDisplayContextHelper.isFileEntryDeletable()) {

			return true;
		}

		return false;
	}

	protected boolean isOpenInMsOfficeActionAvailable() throws PortalException {
		if (_fileEntryDisplayContextHelper.hasViewPermission() &&
			_fileVersionDisplayContextHelper.isMsOffice() &&
			_isWebDAVEnabled() && _isIEOnWin32()) {

			return true;
		}

		return false;
	}

	protected final DLFileShortcut dlFileShortcut;
	protected final FileEntry fileEntry;
	protected final FileVersion fileVersion;
	protected final HttpServletRequest request;

	private static FileVersion _getFileVersion(DLFileShortcut dlFileShortcut) {
		try {
			long fileEntryId = dlFileShortcut.getToFileEntryId();

			FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);

			return fileEntry.getFileVersion();
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to get file version from shortcut " +
					dlFileShortcut.getToTitle(),
				pe);
		}
	}

	private BaseDefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, DLFileShortcut dlFileShortcut) {

		try {
			this.request = request;
			this.fileVersion = fileVersion;
			this.dlFileShortcut = dlFileShortcut;

			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			this.fileEntry = fileEntry;

			_folderId = BeanParamUtil.getLong(
				this.fileEntry, request, "folderId");

			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				_themeDisplay.getPermissionChecker(), this.fileEntry);
			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(fileVersion);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build BaseDefaultDLViewFileVersionDisplayContext " +
					"for " + fileVersion,
				pe);
		}
	}

	private void _addCancelCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable()) {

			return;
		}

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-undo",
			DLUIItemKeys.CANCEL_CHECKOUT,
			UnicodeLanguageUtil.get(request, "cancel-checkout[document]"),
			getSubmitFormJavascript(Constants.CANCEL_CHECKOUT, null));
	}

	private void _addCheckinToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isCheckinActionAvailable()) {
			return;
		}

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-lock",
			DLUIItemKeys.CHECKIN, UnicodeLanguageUtil.get(request, "checkin"),
			getSubmitFormJavascript(Constants.CHECKIN, null));
	}

	private void _addCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCheckoutDocumentActionAvailable()) {

			return;
		}

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-unlock",
			DLUIItemKeys.CHECKOUT,
			UnicodeLanguageUtil.get(request, "checkout[document]"),
			getSubmitFormJavascript(Constants.CHECKOUT, null));
	}

	private void _addDeleteToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isDeleteActionAvailable()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/document_library/view");
		portletURL.setParameter(
			"folderId", String.valueOf(fileEntry.getFolderId()));

		StringBundler sb = new StringBundler(5);

		sb.append("if (confirm('");
		sb.append(
			UnicodeLanguageUtil.get(
				request, "are-you-sure-you-want-to-delete-this"));
		sb.append("')) {");
		sb.append(
			getSubmitFormJavascript(Constants.DELETE, portletURL.toString()));
		sb.append("}");

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-remove",
			DLUIItemKeys.DELETE, UnicodeLanguageUtil.get(request, "delete"),
			sb.toString());
	}

	private void _addDownloadToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isDownloadActionAvailable()) {
			return;
		}

		_addURLUIItem(
			new URLToolbarItem(), toolbarItems, "icon-download",
			DLUIItemKeys.DOWNLOAD, UnicodeLanguageUtil.get(request, "download"),
			DLUtil.getDownloadURL(
				fileEntry, fileVersion, _themeDisplay, StringPool.BLANK));
	}

	private void _addEditToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isEditActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/edit_file_entry");

		_addURLUIItem(
			new URLToolbarItem(), toolbarItems, "icon-edit", DLUIItemKeys.EDIT,
			UnicodeLanguageUtil.get(request, "edit"), portletURL.toString());
	}

	private <T extends JavascriptUIItem> T _addJavascriptUIItem(
		T javascriptUIItem, List<? super T> javascriptUIItems, String icon,
		String key, String label, String onClick ) {

		javascriptUIItem.setIcon(icon);
		javascriptUIItem.setKey(key);
		javascriptUIItem.setLabel(label);
		javascriptUIItem.setOnClick(onClick);

		javascriptUIItems.add(javascriptUIItem);

		return javascriptUIItem;
	}

	private void _addMoveToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isMoveActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getRenderURL("/document_library/move_entry");

		_addURLUIItem(
			new URLToolbarItem(), toolbarItems, "icon-move", DLUIItemKeys.MOVE,
			UnicodeLanguageUtil.get(request, "move"), portletURL.toString());
	}

	private void _addMoveToTheRecycleBinToolbarItem(
			List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isMoveToTheRecycleBinActionAvailable()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/document_library/view");
		portletURL.setParameter(
			"folderId", String.valueOf(fileEntry.getFolderId()));

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-trash",
			DLUIItemKeys.MOVE_TO_THE_RECYCLE_BIN,
			UnicodeLanguageUtil.get(request, "move-to-the-recycle-bin"),
			getSubmitFormJavascript(
				Constants.MOVE_TO_TRASH, portletURL.toString()));
	}

	private void _addOpenInMsOfficeToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isOpenInMsOfficeActionAvailable()) {
			return;
		}

		String webDavURL = DLUtil.getWebDavURL(
			_themeDisplay, fileEntry.getFolder(), fileEntry,
			PropsValues.
				DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED);

		StringBundler sb = new StringBundler(4);

		sb.append(getNamespace());
		sb.append("openDocument('");
		sb.append(webDavURL);
		sb.append("');");

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-file-alt",
			DLUIItemKeys.OPEN_IN_MS_OFFICE,
			UnicodeLanguageUtil.get(request, "open-in-ms-office"),
			sb.toString());
	}

	private void _addPermissionsToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isPermissionsButtonVisible()) {
			return;
		}

		String permissionsURL = null;

		try {
			permissionsURL = PermissionsURLTag.doTag(
				null, DLFileEntryConstants.getClassName(),
				HtmlUtil.unescape(fileEntry.getTitle()), null,
				String.valueOf(fileEntry.getFileEntryId()),
				LiferayWindowState.POP_UP.toString(), null, request
			);
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("Liferay.Util.openWindow({");
		sb.append("title: '");
		sb.append(UnicodeLanguageUtil.get(request, "permissions"));
		sb.append("', uri: '");
		sb.append(permissionsURL);
		sb.append("'});");

		_addJavascriptUIItem(
			new JavascriptToolbarItem(), toolbarItems, "icon-lock",
			DLUIItemKeys.PERMISSIONS,
			UnicodeLanguageUtil.get(request, "permissions"), sb.toString());
	}

	private <T extends URLUIItem> T _addURLUIItem(
		T urlUIItem, List<? super T> urlUIItems, String icon, String key,
		String label, String url ) {

		urlUIItem.setIcon(icon);
		urlUIItem.setKey(key);
		urlUIItem.setLabel(label);
		urlUIItem.setURL(url);

		urlUIItems.add(urlUIItem);

		return urlUIItem;
	}

	private String _getActionURL(String strutsAction, String cmd) {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("struts_action", strutsAction);
		portletURL.setParameter(Constants.CMD, cmd);
		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	private String _getCurrentURL() {
		if (_currentURL != null) {
			return _currentURL;
		}

		LiferayPortletRequest liferayPortletRequest =
			_getLiferayPortletRequest();

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_currentURL = portletURL.toString();

		return _currentURL;
	}

	private DLActionsDisplayContext _getDLActionsDisplayContext()
		throws PortalException {

		if (_dlActionsDisplayContext != null) {
			return _dlActionsDisplayContext;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			portletId = ParamUtil.getString(request, "portletResource");
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				_themeDisplay.getLayout(), portletId);

		_dlActionsDisplayContext = new DLActionsDisplayContext(
			request, dlPortletInstanceSettings);

		return _dlActionsDisplayContext;
	}

	private LiferayPortletRequest _getLiferayPortletRequest() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		return PortalUtil.getLiferayPortletRequest(portletRequest);
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private PortletURL _getRenderURL(String strutsAction) {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", strutsAction);
		portletURL.setParameter("redirect", _getCurrentURL());
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		return portletURL;
	}

	private boolean _isFileEntryTrashable() throws PortalException {
		if (_fileEntryDisplayContextHelper.isDLFileEntry() &&
			_isTrashEnabled()) {

			return true;
		}

		return false;
	}

	private boolean _isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(request);
		}

		return _ieOnWin32;
	}

	private boolean _isShowActions() throws PortalException {
		DLActionsDisplayContext dlActionsDisplayContext =
			_getDLActionsDisplayContext();

		return dlActionsDisplayContext.isShowActions();
	}

	private boolean _isTrashEnabled() throws PortalException {
		if (_trashEnabled == null) {
			_trashEnabled = TrashUtil.isTrashEnabled(
				_themeDisplay.getScopeGroupId());
		}

		return _trashEnabled;
	}

	private boolean _isWebDAVEnabled() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.isWebDAVEnabled();
	}

	private String _processFreeMarkerTemplate(
		String name, Map<String, String> context) {

		try {
			return FreeMarkerUtil.process(name, context);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to process Freemarker template", e);
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"85F6C50E-3893-4E32-9D63-208528A503FA");

	private String _currentURL;
	private DLActionsDisplayContext _dlActionsDisplayContext;
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;
	private final long _folderId;
	private Boolean _ieOnWin32;
	private final ThemeDisplay _themeDisplay;
	private Boolean _trashEnabled;

}