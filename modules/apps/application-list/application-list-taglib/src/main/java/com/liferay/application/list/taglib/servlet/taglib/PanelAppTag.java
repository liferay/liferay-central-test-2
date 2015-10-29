/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.application.list.taglib.servlet.taglib;

import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class PanelAppTag extends BasePanelTag {

	public void setActive(Boolean active) {
		_active = active;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setPanelApp(PanelApp panelApp) {
		_panelApp = panelApp;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_active = null;
		_data = null;
		_id = null;
		_label = null;
		_panelApp = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return "/panel_app/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		boolean active = false;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_active != null) {
			active = _active.booleanValue();
		}
		else {
			active = Validator.equals(
				themeDisplay.getPpid(), _panelApp.getPortletId());
		}

		request.setAttribute(
			"liferay-application-list:panel-app:active", active);

		if (_data == null) {
			_data = new HashMap<>();

			_data.put("navigation", true);
		}

		request.setAttribute("liferay-application-list:panel-app:data", _data);

		if (Validator.isNull(_id)) {
			_id = "portlet_" + _panelApp.getPortletId();
		}

		request.setAttribute("liferay-application-list:panel-app:id", _id);

		if (Validator.isNull(_label) && (_panelApp != null)) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), _panelApp.getPortletId());

			_label = PortalUtil.getPortletTitle(
				portlet, servletContext, themeDisplay.getLocale());
		}

		request.setAttribute(
			"liferay-application-list:panel-app:label", _label);

		request.setAttribute(
			"liferay-application-list:panel-app:panelApp", _panelApp);

		if (Validator.isNull(_url) && (_panelApp != null)) {
			PortletURL portletURL = null;

			try {
				portletURL = _panelApp.getPortletURL(request);
			}
			catch (PortalException pe) {
				_log.error(pe);
			}

			_url = portletURL.toString();
		}

		request.setAttribute("liferay-application-list:panel-app:url", _url);
	}

	private static final Log _log = LogFactoryUtil.getLog(PanelAppTag.class);

	private Boolean _active;
	private Map<String, Object> _data;
	private String _id;
	private String _label;
	private PanelApp _panelApp;
	private String _url;

}