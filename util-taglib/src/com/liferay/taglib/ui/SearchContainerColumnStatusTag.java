/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.dao.search.StatusSearchEntry;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * @author Eudaldo Alonso
 */
public class SearchContainerColumnStatusTag<R>
	extends SearchContainerColumnTag {

	@Override
	public int doEndTag() {
		try {
			SearchContainerRowTag<R> searchContainerRowTag =
				(SearchContainerRowTag<R>)findAncestorWithClass(
					this, SearchContainerRowTag.class);

			ResultRow resultRow = searchContainerRowTag.getRow();

			if (Validator.isNotNull(_property)) {
				_value = (Integer)BeanPropertiesUtil.getObject(
					resultRow.getObject(), _property);
			}

			if (index <= -1) {
				List<SearchEntry> searchEntries = resultRow.getEntries();

				index = searchEntries.size();
			}

			if (resultRow.isRestricted()) {
				_href = null;
			}

			StatusSearchEntry statusSearchEntry = new StatusSearchEntry();

			statusSearchEntry.setAlign(getAlign());
			statusSearchEntry.setColspan(getColspan());
			statusSearchEntry.setCssClass(getCssClass());
			statusSearchEntry.setRequest(
				(HttpServletRequest)pageContext.getRequest());
			statusSearchEntry.setResponse(
				(HttpServletResponse)pageContext.getResponse());
			statusSearchEntry.setServletContext(
				pageContext.getServletContext());
			statusSearchEntry.setStatusByUserId(_statusByUserId);
			statusSearchEntry.setStatusDate(_statusDate);
			statusSearchEntry.setStatus(_value);
			statusSearchEntry.setValign(getValign());

			resultRow.addSearchEntry(index, statusSearchEntry);

			return EVAL_PAGE;
		}
		finally {
			index = -1;
			_statusByUserId = -1;
			_statusDate = null;
			_value = -1;

			if (!ServerDetector.isResin()) {
				align = SearchEntry.DEFAULT_ALIGN;
				colspan = SearchEntry.DEFAULT_COLSPAN;
				cssClass = SearchEntry.DEFAULT_CSS_CLASS;
				_href = null;
				name = null;
				_orderable = false;
				_orderableProperty = null;
				_property = null;
				valign = SearchEntry.DEFAULT_VALIGN;
			}
		}
	}

	@Override
	public int doStartTag() throws JspException {
		if (_orderable && Validator.isNull(_orderableProperty)) {
			_orderableProperty = name;
		}

		SearchContainerRowTag<R> searchContainerRowTag =
			(SearchContainerRowTag<R>)findAncestorWithClass(
				this, SearchContainerRowTag.class);

		if (searchContainerRowTag == null) {
			throw new JspTagException(
				"Requires liferay-ui:search-container-row");
		}

		if (!searchContainerRowTag.isHeaderNamesAssigned()) {
			List<String> headerNames = searchContainerRowTag.getHeaderNames();

			String name = getName();

			if (Validator.isNull(name) && Validator.isNotNull(_property)) {
				name = _property;
			}

			headerNames.add(name);

			if (_orderable) {
				Map<String, String> orderableHeaders =
					searchContainerRowTag.getOrderableHeaders();

				if (Validator.isNotNull(_orderableProperty)) {
					orderableHeaders.put(name, _orderableProperty);
				}
				else if (Validator.isNotNull(_property)) {
					orderableHeaders.put(name, _property);
				}
				else if (Validator.isNotNull(name)) {
					orderableHeaders.put(name, name);
				}
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	public Object getHref() {
		if (Validator.isNotNull(_href) && (_href instanceof PortletURL)) {
			_href = _href.toString();
		}

		return _href;
	}

	public String getOrderableProperty() {
		return _orderableProperty;
	}

	public String getProperty() {
		return _property;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public int getValue() {
		return _value;
	}

	public boolean isOrderable() {
		return _orderable;
	}

	public void setHref(Object href) {
		_href = href;
	}

	public void setOrderable(boolean orderable) {
		_orderable = orderable;
	}

	public void setOrderableProperty(String orderableProperty) {
		_orderableProperty = orderableProperty;
	}

	public void setProperty(String property) {
		_property = property;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	public void setValue(int value) {
		_value = value;
	}

	private Object _href;
	private boolean _orderable;
	private String _orderableProperty;
	private String _property;
	private long _statusByUserId;
	private Date _statusDate;
	private int _value;

}