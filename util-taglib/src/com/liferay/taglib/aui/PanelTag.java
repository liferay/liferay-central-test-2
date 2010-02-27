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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.PwdGenerator;
import com.liferay.util.TextFormatter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="PanelTag.java.html"><b><i>View Source</i></b></a>
 *
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

	public int doEndTag() throws JspException {
		try{
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.setAttribute(
				"aui:panel:collapsible", String.valueOf(_collapsible));
			request.setAttribute("aui:panel:id", _id);
			request.setAttribute("aui:panel:label", _label);
			request.setAttribute("aui:panel:toolTags", _toolTags);

			PortalIncludeUtil.include(pageContext, getEndPage());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_collapsible = false;
				_endPage = null;
				_id = null;
				_label = null;
				_startPage = null;
				_toolTags = null;
			}
		}
	}

	public int doStartTag() throws JspException {
		try{
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			if (_label == null) {
				_label = TextFormatter.format(_id, TextFormatter.K);
			}

			if (Validator.isNull(_id)) {
				_id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
			}

			request.setAttribute(
				"aui:panel:collapsible", String.valueOf(_collapsible));
			request.setAttribute("aui:panel:id", _id);
			request.setAttribute("aui:panel:label", _label);
			request.setAttribute("aui:panel:toolTags", _toolTags);

			PortalIncludeUtil.include(pageContext, getStartPage());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	public String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	public List<ToolTag> getToolTags() {
		return _toolTags;
	}

	public void setCollapsible(boolean collapsible) {
		_collapsible = collapsible;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	private static final String _END_PAGE = "/html/taglib/aui/panel/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/panel/start.jsp";

	private boolean _collapsible;
	private String _endPage;
	private String _id;
	private String _label;
	private String _startPage;
	private List<ToolTag> _toolTags;

}