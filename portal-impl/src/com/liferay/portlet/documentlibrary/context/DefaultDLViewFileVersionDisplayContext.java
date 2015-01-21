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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultDLViewFileVersionDisplayContext
	extends BaseDefaultDLViewFileVersionDisplayContext {

	public DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DLFileShortcut dlFileShortcut) {

		super(request, response, dlFileShortcut);
	}

	public DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(request, response, fileVersion);
	}

	@Override
	protected void buildMenuItems(List<MenuItem> menuItems)
		throws PortalException {

		addDownloadMenuItem(menuItems);

		addOpenInMsOfficeMenuItem(menuItems);

		addViewOriginalFileMenuItem(menuItems);

		addEditMenuItem(menuItems);

		addMoveMenuItem(menuItems);

		addCheckoutMenuItem(menuItems);

		addCheckinMenuItem(menuItems);

		addCancelCheckoutMenuItem(menuItems);

		addPermissionsMenuItem(menuItems);

		addDeleteMenuItem(menuItems);
	}

}