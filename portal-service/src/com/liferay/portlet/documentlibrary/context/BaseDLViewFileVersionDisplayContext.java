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
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.io.IOException;

import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class BaseDLViewFileVersionDisplayContext
	extends BaseDLDisplayContext<DLViewFileVersionDisplayContext>
	implements DLViewFileVersionDisplayContext {

	public BaseDLViewFileVersionDisplayContext(
		UUID uuid, DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(uuid, parentDLDisplayContext, request, response);

		this.fileVersion = fileVersion;
	}

	@Override
	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		return parentDLDisplayContext.getDDMFormValues(ddmStructure);
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		return parentDLDisplayContext.getDDMStructures();
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		return parentDLDisplayContext.getMenuItems();
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		return parentDLDisplayContext.getToolbarItems();
	}

	@Override
	public boolean isDownloadLinkVisible() throws PortalException {
		return parentDLDisplayContext.isDownloadLinkVisible();
	}

	@Override
	public boolean isVersionInfoVisible() throws PortalException {
		return parentDLDisplayContext.isVersionInfoVisible();
	}

	@Override
	public void renderPreview(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		parentDLDisplayContext.renderPreview(request, response);
	}

	protected FileVersion fileVersion;

}