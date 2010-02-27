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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="NavigationTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class NavigationTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:navigation:bulletStyle", _bulletStyle);
		request.setAttribute(
			"liferay-ui:navigation:displayStyle", _displayStyle);
		request.setAttribute("liferay-ui:navigation:headerType", _headerType);
		request.setAttribute(
			"liferay-ui:navigation:rootLayoutType", _rootLayoutType);
		request.setAttribute(
			"liferay-ui:navigation:rootLayoutLevel",
			String.valueOf(_rootLayoutLevel));
		request.setAttribute(
			"liferay-ui:navigation:includedLayouts", _includedLayouts);
		request.setAttribute(
			"liferay-ui:navigation:nestedChildren",
			String.valueOf(_nestedChildren));

		return EVAL_BODY_BUFFERED;
	}

	public void setBulletStyle(String bulletStyle) {
		_bulletStyle = bulletStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setHeaderType(String headerType) {
		_headerType = headerType;
	}

	public void setRootLayoutType(String rootLayoutType) {
		_rootLayoutType = rootLayoutType;
	}

	public void setRootLayoutLevel(int rootLayoutLevel) {
		_rootLayoutLevel = rootLayoutLevel;
	}

	public void setIncludedLayouts(String includedLayouts) {
		_includedLayouts = includedLayouts;
	}

	public void setNestedChildren(boolean nestedChildren) {
		_nestedChildren = nestedChildren;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/navigation/page.jsp";

	private String _bulletStyle = "1";
	private String _displayStyle = "1";
	private String _headerType = "none";
	private String _rootLayoutType = "absolute";
	private int _rootLayoutLevel = 1;
	private String _includedLayouts = "auto";
	private boolean _nestedChildren = true;

}