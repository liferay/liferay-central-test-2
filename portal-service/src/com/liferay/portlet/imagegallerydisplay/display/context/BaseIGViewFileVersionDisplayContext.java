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

package com.liferay.portlet.imagegallerydisplay.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class BaseIGViewFileVersionDisplayContext
	extends BaseIGDisplayContext<IGViewFileVersionDisplayContext>
	implements IGViewFileVersionDisplayContext {

	public BaseIGViewFileVersionDisplayContext(
		UUID uuid, IGViewFileVersionDisplayContext parentIGDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(uuid, parentIGDisplayContext, request, response);

		this.fileVersion = fileVersion;
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		return parentDisplayContext.getMenuItems();
	}

	protected FileVersion fileVersion;

}