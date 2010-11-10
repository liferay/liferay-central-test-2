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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.servlet.DirectServletContext;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.TrackedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * @author Brian Wing Shun Chan
 */
public class IncludeTag
	extends ParamAndPropertyAncestorTagImpl implements DynamicAttributes {

	public int doEndTag() throws JspException {
		try {
			String page = getPage();

			if (Validator.isNull(page)) {
				page = getEndPage();
			}

			callSetAttributes();

			if (Validator.isNotNull(page)) {
				_doInclude(page);
			}

			return EVAL_PAGE;
		}
		finally {
			_dynamicAttributes.clear();

			clearParams();
			clearProperties();

			cleanUpSetAttributes();

			if (!ServerDetector.isResin()) {
				setPage(null);

				cleanUp();
			}
		}
	}

	public int doStartTag() throws JspException {
		String page = getStartPage();

		if (Validator.isNull(page)) {
			return EVAL_BODY_BUFFERED;
		}

		callSetAttributes();

		_doInclude(page);

		return EVAL_BODY_INCLUDE;
	}

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	public void setPage(String page) {
		_page = page;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void runEndTag() throws JspException {
		doStartTag();
	}

	public void runStartTag() throws JspException {
		doStartTag();
	}

	public void runTag() throws JspException {
		doStartTag();
		doEndTag();
	}

	protected void callSetAttributes() {
		if (_calledSetAttributes) {
			return;
		}

		_calledSetAttributes = true;

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (isCleanUpSetAttributes()) {
			_trackedRequest = new TrackedServletRequest(request);

			request = _trackedRequest;
		}

		setAttributes(request);
	}

	protected void cleanUp() {
	}

	protected void cleanUpSetAttributes() {
		_calledSetAttributes = false;

		if (isCleanUpSetAttributes()) {
			for (String name : _trackedRequest.getSetAttributes()) {
				_trackedRequest.removeAttribute(name);
			}

			_trackedRequest = null;
		}
	}

	protected Map<String, Object> getDynamicAttributes() {
		return _dynamicAttributes;
	}

	protected String getEndPage() {
		return null;
	}

	protected String getPage() {
		return _page;
	}

	protected ServletContext getServletContext(
			ServletContext servletContext, HttpServletRequest request)
		throws SystemException {

		if (Validator.isNull(_portletId)) {
			return servletContext;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), _portletId);

		if (portlet == null) {
			return servletContext;
		}

		PortletApp portletApp = portlet.getPortletApp();

		if (!portletApp.isWARFile()) {
			return servletContext;
		}

		return PortalUtil.getServletContext(portlet, servletContext);
	}

	protected String getStartPage() {
		return null;
	}

	protected void include(String page) throws Exception {
		HttpServletRequest request = getServletRequest();

		request.setAttribute(WebKeys.SERVLET_PATH, page);

		ServletContext servletContext = getServletContext();

		servletContext = getServletContext(servletContext, request);

		if (_DIRECT_SERVLET_CONTEXT_ENABLED) {
			servletContext = new DirectServletContext(servletContext);
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(
			request, new PipingServletResponse(pageContext, isTrimNewLines()));
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isTrimNewLines() {
		return _TRIM_NEW_LINES;
	}

	protected void setAttributes(HttpServletRequest request) {
	}

	private void _doInclude(String page) throws JspException {
		try {
			include(page);
		}
		catch (Exception e) {
			HttpServletRequest request = getServletRequest();

			String currentURL = (String)request.getAttribute(
				WebKeys.CURRENT_URL);

			_log.error(
				"Current URL " + currentURL + " generates exception: " +
					e.getMessage());

			LogUtil.log(_log, e);

			if (e instanceof JspException) {
				throw (JspException)e;
			}
		}
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = false;

	private static final boolean _DIRECT_SERVLET_CONTEXT_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_ENABLED));

	private static final boolean _TRIM_NEW_LINES = false;

	private static Log _log = LogFactoryUtil.getLog(IncludeTag.class);

	private boolean _calledSetAttributes;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _page;
	private String _portletId;
	private TrackedServletRequest _trackedRequest;

}