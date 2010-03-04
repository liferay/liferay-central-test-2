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
 * <a href="InputTimeTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InputTimeTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:input-time:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-time:hourParam", _hourParam);
		request.setAttribute(
			"liferay-ui:input-time:hourValue", String.valueOf(_hourValue));
		request.setAttribute(
			"liferay-ui:input-time:hourNullable",
			String.valueOf(_hourNullable));
		request.setAttribute("liferay-ui:input-time:minuteParam", _minuteParam);
		request.setAttribute(
			"liferay-ui:input-time:minuteValue", String.valueOf(_minuteValue));
		request.setAttribute(
			"liferay-ui:input-time:minuteNullable",
			String.valueOf(_minuteNullable));
		request.setAttribute(
			"liferay-ui:input-time:minuteInterval",
			String.valueOf(_minuteInterval));
		request.setAttribute("liferay-ui:input-time:amPmParam", _amPmParam);
		request.setAttribute(
			"liferay-ui:input-time:amPmValue", String.valueOf(_amPmValue));
		request.setAttribute(
			"liferay-ui:input-time:amPmNullable",
			String.valueOf(_amPmNullable));
		request.setAttribute(
			"liferay-ui:input-time:disabled", String.valueOf(_disabled));

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}
	public void setHourParam(String hourParam) {
		_hourParam = hourParam;
	}

	public void setHourValue(int hourValue) {
		_hourValue = hourValue;
	}

	public void setHourNullable(boolean hourNullable) {
		_hourNullable = hourNullable;
	}

	public void setMinuteParam(String minuteParam) {
		_minuteParam = minuteParam;
	}

	public void setMinuteValue(int minuteValue) {
		_minuteValue = minuteValue;
	}

	public void setMinuteNullable(boolean minuteNullable) {
		_minuteNullable = minuteNullable;
	}

	public void setMinuteInterval(int minuteInterval) {
		_minuteInterval = minuteInterval;
	}

	public void setAmPmParam(String amPmParam) {
		_amPmParam = amPmParam;
	}

	public void setAmPmValue(int amPmValue) {
		_amPmValue = amPmValue;
	}

	public void setAmPmNullable(boolean amPmNullable) {
		_amPmNullable = amPmNullable;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/input_time/page.jsp";

	private String _cssClass;
	private String _hourParam;
	private int _hourValue;
	private boolean _hourNullable;
	private String _minuteParam;
	private int _minuteValue;
	private boolean _minuteNullable;
	private int _minuteInterval;
	private String _amPmParam;
	private int _amPmValue;
	private boolean _amPmNullable;
	private boolean _disabled;

}