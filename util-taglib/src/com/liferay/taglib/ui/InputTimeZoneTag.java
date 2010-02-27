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

import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="InputTimeZoneTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InputTimeZoneTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:input-time-zone:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-time-zone:name", _name);
		request.setAttribute("liferay-ui:input-time-zone:value", _value);
		request.setAttribute(
			"liferay-ui:input-time-zone:nullable", String.valueOf(_nullable));
		request.setAttribute(
			"liferay-ui:input-time-zone:daylight", String.valueOf(_daylight));
		request.setAttribute(
			"liferay-ui:input-time-zone:displayStyle",
			String.valueOf(_displayStyle));
		request.setAttribute(
			"liferay-ui:input-time-zone:disabled", String.valueOf(_disabled));

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(String value) {
		_value = value;
	}

	public void setNullable(boolean nullable) {
		_nullable = nullable;
	}

	public void setDaylight(boolean daylight) {
		_daylight = daylight;
	}

	public void setDisplayStyle(int displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_time_zone/page.jsp";

	private String _cssClass;
	private String _name;
	private String _value = TimeZoneUtil.getDefault().getID();
	private boolean _nullable;
	private boolean _daylight;
	private int _displayStyle = TimeZone.LONG;
	private boolean _disabled;

}