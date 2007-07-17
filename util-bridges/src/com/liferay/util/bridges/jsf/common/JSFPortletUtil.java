/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util.bridges.jsf.common;

import com.icesoft.faces.env.ServletEnvironmentRequest;
import com.icesoft.faces.webapp.http.portlet.PortletArtifactHack;

import com.liferay.util.GetterUtil;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

/**
 * <a href="JSFPortletUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Neil Griffin
 *
 */
public class JSFPortletUtil {

	public static long getCompanyId(FacesContext facesContext) {
		return getCompanyId(getPortletRequest(facesContext));
	}

	public static long getCompanyId(PortletRequest portletRequest) {
		long companyId = 0;

		Map userInfo = (Map)portletRequest.getAttribute(
			RenderRequest.USER_INFO);

		if (userInfo != null) {
			companyId = GetterUtil.getLong(
				(String)userInfo.get("liferay.company.id"));
		}

		return companyId;
	}

	public static Locale getLocale(FacesContext facesContext) {
		Locale locale = facesContext.getViewRoot().getLocale();

		if (locale == null) {
			locale = facesContext.getApplication().getDefaultLocale();
		}

		return (locale);
	}

	public static PortletPreferences getPortletPreferences(
		FacesContext facesContext) {

		return JSFPortletUtil.getPortletRequest(facesContext).getPreferences();
	}

	public static PortletRequest getPortletRequest(FacesContext facesContext) {
		PortletRequest portletRequest = null;

		Object request = facesContext.getExternalContext().getRequest();

		if (request instanceof PortletRequest) {
			portletRequest = (PortletRequest)request;
		}
		else if (request instanceof ServletEnvironmentRequest) {
			ServletEnvironmentRequest servletEnvironmentRequest =
				(ServletEnvironmentRequest)request;

			PortletArtifactHack portletArtifactHack =
				(PortletArtifactHack)servletEnvironmentRequest.getAttribute(
					PortletArtifactHack.PORTLET_HACK_KEY);

			if (portletArtifactHack != null) {
				portletRequest = portletArtifactHack.getPortletRequest();
			}
		}

		return portletRequest;
	}

	public static String getPreferenceValue(
		FacesContext facesContext, String preferenceName) {

		return getPreferenceValue(facesContext, preferenceName, null);
	}

	public static String getPreferenceValue(
		PortletPreferences portletPreferences, String preferenceName) {

		return getPreferenceValue(portletPreferences, preferenceName, null);
	}

	public static String getPreferenceValue(
		FacesContext facesContext, String preferenceName, String defaultValue) {

		return getPreferenceValue(
			getPortletPreferences(facesContext), preferenceName, defaultValue);
	}

	public static String getPreferenceValue(
		PortletPreferences portletPreferences, String preferenceName,
		String defaultValue) {

		String value = defaultValue;

		if (portletPreferences != null) {
			portletPreferences.getValue(preferenceName, defaultValue);
		}

		return value;
	}

}