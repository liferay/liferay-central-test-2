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

package com.liferay.document.library.web.internal.trash;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.trash.TrashHelper;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DLFileShortcutTrashRenderer extends BaseTrashRenderer {

	/**
	 * @deprecated As of 1.4.0
	 */
	@Deprecated
	public DLFileShortcutTrashRenderer(DLFileShortcut dlFileShortcut) {
		this(dlFileShortcut, null);
	}

	public DLFileShortcutTrashRenderer(
		DLFileShortcut dlFileShortcut, TrashHelper trashHelper) {

		_dlFileShortcut = dlFileShortcut;
		_trashHelper = trashHelper;
	}

	@Override
	public String getClassName() {
		return DLFileShortcutConstants.getClassName();
	}

	@Override
	public long getClassPK() {
		return _dlFileShortcut.getFileShortcutId();
	}

	@Override
	public String getPortletId() {
		return DLPortletKeys.DOCUMENT_LIBRARY;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return getTitle(null);
	}

	@Override
	public String getTitle(Locale locale) {
		return _trashHelper.getOriginalTitle(_dlFileShortcut.getToTitle());
	}

	@Override
	public String getType() {
		return "shortcut";
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFileEntryConstants.getClassName());

		TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
			_dlFileShortcut.getToFileEntryId());

		return trashRenderer.include(request, response, template);
	}

	private final DLFileShortcut _dlFileShortcut;
	private final TrashHelper _trashHelper;

}