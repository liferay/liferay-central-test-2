/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.communities.search;

import com.liferay.portal.kernel.dao.search.RowChecker;

import javax.portlet.RenderResponse;

/**
 * @author Raymond Augé
 */
public class ExportPageChecker extends RowChecker {

	public ExportPageChecker(RenderResponse renderResponse) {
		super(renderResponse);
	}

	public ExportPageChecker(
		RenderResponse renderResponse, String align, String valign,
		String formName, String allRowsId, String rowId) {

		super(
			renderResponse, align, valign, COLSPAN, CSS_CLASS, formName,
			allRowsId, rowId);
	}

	public ExportPageChecker(
		RenderResponse renderResponse, String align, String valign, int colspan,
		String cssClass, String formName, String allRowsId, String rowId) {

		super(
			renderResponse, align, valign, colspan, cssClass, formName,
			allRowsId, rowId);
	}

	public boolean isChecked(Object obj) {
		return true;
	}

}