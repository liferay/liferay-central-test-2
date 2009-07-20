/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import java.text.DateFormat;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class CalendarTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(
			"liferay-ui:calendar:month", String.valueOf(_month));
		request.setAttribute("liferay-ui:calendar:day", String.valueOf(_day));
		request.setAttribute("liferay-ui:calendar:year", String.valueOf(_year));
		request.setAttribute(
			"liferay-ui:calendar:headerPattern", _headerPattern);
		request.setAttribute("liferay-ui:calendar:headerFormat", _headerFormat);
		request.setAttribute("liferay-ui:calendar:data", _data);
		request.setAttribute(
			"liferay-ui:calendar:showAllPotentialWeeks",
			String.valueOf(_showAllPotentialWeeks));

		return EVAL_BODY_BUFFERED;
	}

	public void setMonth(int month) {
		_month = month;
	}

	public void setDay(int day) {
		_day = day;
	}

	public void setYear(int year) {
		_year = year;
	}

	public void setHeaderPattern(String headerPattern) {
		_headerPattern = headerPattern;
	}

	public void setHeaderFormat(DateFormat headerFormat) {
		_headerFormat = headerFormat;
	}

	public void setData(Set<Integer> data) {
		_data = data;
	}

	public void setShowAllPotentialWeeks(boolean showAllPotentialWeeks) {
		_showAllPotentialWeeks = showAllPotentialWeeks;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/calendar/page.jsp";

	private int _month;
	private int _day;
	private int _year;
	private String _headerPattern;
	private DateFormat _headerFormat;
	private Set<Integer> _data;
	private boolean _showAllPotentialWeeks;

}