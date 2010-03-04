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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="PageIteratorTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PageIteratorTag extends TagSupport {

	public int doStartTag() throws JspException {
		try {
			_pages = (int)Math.ceil((double)_total / _delta);

			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.setAttribute(
				"liferay-ui:page-iterator:formName", _formName);
			request.setAttribute(
				"liferay-ui:page-iterator:cur", String.valueOf(_cur));
			request.setAttribute(
				"liferay-ui:page-iterator:curParam", _curParam);
			request.setAttribute(
				"liferay-ui:page-iterator:delta", String.valueOf(_delta));
			request.setAttribute(
				"liferay-ui:page-iterator:deltaConfigurable",
				String.valueOf(_deltaConfigurable));
			request.setAttribute(
				"liferay-ui:page-iterator:deltaParam", _deltaParam);
			request.setAttribute("liferay-ui:page-iterator:jsCall", _jsCall);
			request.setAttribute(
				"liferay-ui:page-iterator:maxPages", String.valueOf(_maxPages));
			request.setAttribute("liferay-ui:page-iterator:target", _target);
			request.setAttribute(
				"liferay-ui:page-iterator:total", String.valueOf(_total));
			request.setAttribute("liferay-ui:page-iterator:url", _url);
			request.setAttribute(
				"liferay-ui:page-iterator:urlAnchor", _urlAnchor);
			request.setAttribute(
				"liferay-ui:page-iterator:pages", String.valueOf(_pages));
			request.setAttribute("liferay-ui:page-iterator:type", _type);

			PortalIncludeUtil.include(pageContext, getStartPage());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		try {
			if (_pages > 1) {
				PortalIncludeUtil.include(pageContext, getEndPage());
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setCur(int cur) {
		_cur = cur;
	}

	public void setCurParam(String curParam) {
		_curParam = curParam;
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public void setDeltaConfigurable(boolean deltaConfigurable) {
		_deltaConfigurable = deltaConfigurable;
	}

	public void setDeltaParam(String deltaParam) {
		_deltaParam = deltaParam;
	}

	public void setJsCall(String jsCall) {
		_jsCall = jsCall;
	}

	public void setMaxPages(int maxPages) {
		_maxPages = maxPages;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUrl(String url) {
		_url = url;
		_urlAnchor = StringPool.BLANK;

		int pos = _url.indexOf("#");

		if (pos != -1) {
			_url = url.substring(0, pos);
			_urlAnchor = url.substring(pos, url.length());
		}

		if (_url.indexOf("?") == -1) {
			_url += "?";
		}
		else if (!_url.endsWith("&")) {
			_url += "&";
		}
	}

	private static final String _START_PAGE =
		"/html/taglib/ui/page_iterator/start.jsp";

	private static final String _END_PAGE =
		"/html/taglib/ui/page_iterator/end.jsp";

	private String _startPage;
	private String _endPage;
	private String _formName = "fm";
	private int _cur;
	private String _curParam;
	private int _delta = SearchContainer.DEFAULT_DELTA;
	private boolean _deltaConfigurable =
		SearchContainer.DEFAULT_DELTA_CONFIGURABLE;
	private String _deltaParam = SearchContainer.DEFAULT_DELTA_PARAM;
	private String _jsCall;
	private int _maxPages = 10;
	private String _target = "_self";
	private int _total;
	private String _type = "regular";
	private String _url;
	private String _urlAnchor;
	private int _pages;

}