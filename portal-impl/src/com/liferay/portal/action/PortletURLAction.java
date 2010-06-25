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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="PortletURLAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class PortletURLAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			String portletURL = getPortletURL(request);

			ServletResponseUtil.write(response, portletURL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}

		return null;
	}

	protected String getPortletURL(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cacheability = ParamUtil.getString(request, "cacheability");
		boolean copyCurrentRenderParameters = ParamUtil.getBoolean(
			request, "copyCurrentRenderParameters");
		long doAsUserId = ParamUtil.getLong(request, "doAsUserId");
		String doAsUserLanguageId = ParamUtil.getString(
			request, "doAsUserLanguageId");
		boolean encrypt = ParamUtil.getBoolean(request, "encrypt");
		boolean escapeXml = ParamUtil.getBoolean(request, "escapeXml");
		String lifecycle = ParamUtil.getString(request, "lifecycle");
		String name = ParamUtil.getString(request, "name");
		boolean portletConfiguration = ParamUtil.getBoolean(
			request, "portletConfiguration");
		String portletId = ParamUtil.getString(request, "portletId");
		String portletMode = ParamUtil.getString(request, "portletMode");
		String resourceId = ParamUtil.getString(request, "resourceId");
		String returnToFullPageURL = ParamUtil.getString(
			request, "returnToFullPageURL");
		boolean secure = ParamUtil.getBoolean(request, "secure");
		String windowState = ParamUtil.getString(request, "windowState");

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, portletId, themeDisplay.getPlid(), lifecycle);

		if (Validator.isNotNull(cacheability)) {
			liferayPortletURL.setCacheability(cacheability);
		}

		liferayPortletURL.setCopyCurrentRenderParameters(
			copyCurrentRenderParameters);

		if (doAsUserId > 0) {
			liferayPortletURL.setDoAsUserId(doAsUserId);
		}

		if (Validator.isNotNull(doAsUserLanguageId)) {
			liferayPortletURL.setDoAsUserLanguageId(doAsUserLanguageId);
		}

		liferayPortletURL.setEncrypt(encrypt);
		liferayPortletURL.setEscapeXml(escapeXml);

		if (lifecycle.equals(PortletRequest.ACTION_PHASE) &&
			Validator.isNotNull(name)) {

			liferayPortletURL.setParameter(ActionRequest.ACTION_NAME, name);
		}

		liferayPortletURL.setPortletId(portletId);

		if (portletConfiguration) {
			String portletResource = ParamUtil.getString(
				request, "portletResource");
			String previewWidth = ParamUtil.getString(request, "previewWidth");

			liferayPortletURL.setParameter(
				"struts_action", "/portlet_configuration/edit_configuration");
			liferayPortletURL.setParameter(
				"returnToFullPageURL", returnToFullPageURL);
			liferayPortletURL.setParameter("portletResource", portletResource);
			liferayPortletURL.setParameter("previewWidth", previewWidth);
		}

		if (Validator.isNotNull(portletMode)) {
			liferayPortletURL.setPortletMode(
				PortletModeFactory.getPortletMode(portletMode));
		}

		if (Validator.isNotNull(resourceId)) {
			liferayPortletURL.setResourceID(resourceId);
		}

		if (!themeDisplay.isStateMaximized()) {
			if (Validator.isNotNull(returnToFullPageURL)) {
				liferayPortletURL.setParameter(
					"returnToFullPageURL", returnToFullPageURL);
			}
		}

		liferayPortletURL.setSecure(secure);

		if (Validator.isNotNull(windowState)) {
			liferayPortletURL.setWindowState(
				WindowStateFactory.getWindowState(windowState));
		}

		String parameterMapString = ParamUtil.getString(
			request, "parameterMap");

		if (Validator.isNotNull(parameterMapString)) {
			Map<String, String> parameterMap =
				(Map<String, String>)JSONFactoryUtil.deserialize(
					parameterMapString);

			Iterator<String> itr = parameterMap.keySet().iterator();

			while (itr.hasNext()) {
				String paramName = itr.next();

				String paramValue = parameterMap.get(paramName);

				liferayPortletURL.setParameter(paramName, paramValue);
			}
		}

		return liferayPortletURL.toString();
	}

}