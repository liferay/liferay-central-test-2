/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.PwdGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class PanelTag extends IncludeTag {

	public void addToolTag(ToolTag toolTag) {
		if (_toolTags == null) {
			_toolTags = new ArrayList<ToolTag>();
		}

		_toolTags.add(toolTag);
	}

	public List<ToolTag> getToolTags() {
		return _toolTags;
	}

	public void setCollapsible(boolean collapsible) {
		_collapsible = collapsible;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	protected void cleanUp() {
		_collapsible = false;
		_id = null;
		_label = null;

		if (_toolTags != null) {
			for (ToolTag toolTag : _toolTags) {
				toolTag.cleanUp();
			}

			_toolTags = null;
		}
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected void setAttributes(HttpServletRequest request) {
		String id = _id;

		if (Validator.isNull(id)) {
			id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
		}

		request.setAttribute(
			"aui:panel:collapsible", String.valueOf(_collapsible));
		request.setAttribute("aui:panel:id", id);
		request.setAttribute("aui:panel:label", _label);
		request.setAttribute("aui:panel:toolTags", _toolTags);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE = "/html/taglib/aui/panel/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/panel/start.jsp";

	private boolean _collapsible;
	private String _id;
	private String _label;
	private List<ToolTag> _toolTags;

}