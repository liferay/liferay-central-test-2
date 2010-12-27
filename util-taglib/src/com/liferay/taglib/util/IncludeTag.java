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
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CustomJspRegistryUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IncludeTag
	extends ParamAndPropertyAncestorTagImpl implements DynamicAttributes {

	public int doEndTag() throws JspException {
		try {
			String page = getCustomPage();

			if (Validator.isNull(page)) {
				page = getPage();
			}

			if (Validator.isNull(page)) {
				page = getEndPage();
			}

			callSetAttributes();

			if (!FileAvailabilityUtil.isAvailable(getServletContext(), page)) {
				try {
					return processEndTag();
				}
				catch(Exception e) {
					throw new JspException(e);
				}
			}
			else {
				_doInclude(page);

				return EVAL_PAGE;
			}
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

		callSetAttributes();

		if (!FileAvailabilityUtil.isAvailable(getServletContext(), page)) {
			try {
				return processStartTag();
			}
			catch(Exception e) {
				throw new JspException(e);
			}
		}
		else {
			_doInclude(page);

			return EVAL_BODY_INCLUDE;
		}
	}

	public ServletContext getServletContext() {
		ServletContext servletContext = super.getServletContext();

		try {
			if (Validator.isNull(_portletId)) {
				return servletContext;
			}

			HttpServletRequest request = getServletRequest();

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
		catch (SystemException se) {
			return servletContext;
		}
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
		doEndTag();
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

	protected String getCustomPage() {
		HttpServletRequest request = getServletRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Validator.isNull(themeDisplay)) {
			return null;
		}

		Group group = themeDisplay.getScopeGroup();

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		String customJspServletContextName =
			typeSettingsProperties.getProperty("customJspServletContextName");

		if (Validator.isNull(customJspServletContextName)) {
			return null;
		}

		String page = getPage();

		if (Validator.isNull(page)) {
			page = getEndPage();
		}

		if (Validator.isNull(page)) {
			return null;
		}

		String customPage = CustomJspRegistryUtil.getCustomJspFileName(
			customJspServletContextName, page);

		if (FileAvailabilityUtil.isAvailable(getServletContext(), customPage)) {
			return customPage;
		}

		return null;
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

	protected String getStartPage() {
		return null;
	}

	protected void include(String page) throws Exception {
		ServletContext servletContext = getServletContext();

		if (_DIRECT_SERVLET_CONTEXT_ENABLED) {
			servletContext = new DirectServletContext(servletContext);
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		HttpServletRequest request = getServletRequest();

		request.setAttribute(WebKeys.SERVLET_PATH, page);

		HttpServletResponse response = new PipingServletResponse(
			pageContext, isTrimNewLines());

		if (!isWARFile(request)) {
			requestDispatcher.include(request, response);
		}
		else {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			Class<?> classObj = classLoader.loadClass(
				_LIFERAY_REQUEST_DISPATCHER);

			Constructor<?> constructor = classObj.getConstructor(
				RequestDispatcher.class, String.class);

			Object obj = constructor.newInstance(requestDispatcher, page);

			Method method = classObj.getMethod(
				"include", ServletRequest.class, ServletResponse.class,
				boolean.class);

			method.invoke(obj, request, response, true);
		}
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isTrimNewLines() {
		return _TRIM_NEW_LINES;
	}

	protected boolean isWARFile(HttpServletRequest request)
		throws SystemException {

		if (Validator.isNull(_portletId)) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), _portletId);

		if (portlet == null) {
			return false;
		}

		PortletApp portletApp = portlet.getPortletApp();

		return portletApp.isWARFile();
	}

	protected int processEndTag() throws Exception {
		return EVAL_PAGE;
	}

	protected int processStartTag() throws Exception {
		return EVAL_BODY_INCLUDE;
	}

	protected void setAttributes(HttpServletRequest request) {
	}

	protected void writeDynamicAttributes(JspWriter jspWriter)
		throws IOException {

		String dynamicAttributesString = InlineUtil.buildDynamicAttributes(
			getDynamicAttributes());

		if (Validator.isNotNull(dynamicAttributesString)) {
			jspWriter.write(dynamicAttributesString);
		}
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

	private static final String _LIFERAY_REQUEST_DISPATCHER =
		"com.liferay.portal.apache.bridges.struts.LiferayRequestDispatcher";

	private static final boolean _TRIM_NEW_LINES = false;

	private static Log _log = LogFactoryUtil.getLog(IncludeTag.class);

	private boolean _calledSetAttributes;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _page;
	private String _portletId;
	private TrackedServletRequest _trackedRequest;

}