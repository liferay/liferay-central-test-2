/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
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
 *
 */
public class PortletURLAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Layout layout = themeDisplay.getLayout();

			String cacheability = ParamUtil.getString(
				request, "cacheability");

			boolean copyCurrentRenderParameters = ParamUtil.getBoolean(
				request, "copyCurrentRenderParameters");

			long doAsUserId = ParamUtil.getLong(
				request, "doAsUserId");

			boolean encrypt = ParamUtil.getBoolean(
				request, "encrypt");

			boolean escapeXml = ParamUtil.getBoolean(
				request, "escapeXml");

			String lifecycle = ParamUtil.getString(
				request, "lifecycle");

			String name = ParamUtil.getString(
				request, "name");

			String param = ParamUtil.getString(
				request, "param");

			boolean portletConfiguration = ParamUtil.getBoolean(
				request, "portletConfiguration");

			String portletMode = ParamUtil.getString(
				request, "portletMode");

			String portletId = ParamUtil.getString(
				request, "portletId");

			String resourceId = ParamUtil.getString(
				request, "resourceId");

			boolean secure = ParamUtil.getBoolean(
				request, "secure");

			String windowState = ParamUtil.getString(
				request, "windowState");

			PortletURLImpl portletURL = new PortletURLImpl(
				request, portletId, layout.getPlid(), lifecycle);

			if (Validator.isNotNull(cacheability)) {
				portletURL.setCacheability(cacheability);
			}

			if (Validator.isNotNull(encrypt)) {
				portletURL.setCopyCurrentRenderParameters(
						copyCurrentRenderParameters);
			}

			if (Validator.isNotNull(doAsUserId)) {
				if (doAsUserId > 0) {
					portletURL.setDoAsUserId(doAsUserId);
				}
			}

			if (Validator.isNotNull(encrypt)) {
				portletURL.setEncrypt(encrypt);
			}

			if (Validator.isNotNull(escapeXml)) {
				portletURL.setEscapeXml(escapeXml);
			}

			if (lifecycle.equals(PortletRequest.ACTION_PHASE) &&
				Validator.isNotNull(name)) {

				portletURL.setParameter(ActionRequest.ACTION_NAME, name);
			}

			if (Validator.isNotNull(portletId)) {
				portletURL.setPortletId(portletId);
			}

			if (Validator.isNotNull(portletConfiguration) &&
				portletConfiguration) {

				String returnToFullPageURL = ParamUtil.getString(
					request, "returnToFullPageURL");
				String portletResource = ParamUtil.getString(
					request, "portletResource");
				String previewWidth = ParamUtil.getString(
					request, "previewWidth");

				portletURL.setParameter(
					"struts_action",
					"/portlet_configuration/edit_configuration");
				portletURL.setParameter(
					"returnToFullPageURL", returnToFullPageURL);
				portletURL.setParameter("portletResource", portletResource);
				portletURL.setParameter("previewWidth", previewWidth);
			}

			if (Validator.isNotNull(portletMode)) {
				portletURL.setPortletMode(
					PortletModeFactory.getPortletMode(portletMode));
			}

			if (Validator.isNotNull(resourceId)) {
				portletURL.setResourceID(resourceId);
			}

			if (Validator.isNotNull(secure)) {
				portletURL.setSecure(secure);
			}
			else {
				portletURL.setSecure(request.isSecure());
			}

			if (Validator.isNotNull(windowState)) {
				portletURL.setWindowState(
					WindowStateFactory.getWindowState(windowState));
			}

			if (Validator.isNotNull(param)) {
				Map<String, String> params =
					(Map<String, String>)JSONFactoryUtil.deserialize(param);

				Iterator<String> itr = params.keySet().iterator();

				while (itr.hasNext()) {
					String paramKey = (String)itr.next();
					String paramValue = (String)params.get(paramKey);

					portletURL.setParameter(paramKey, paramValue);
				}
			}

			ServletResponseUtil.write(response, portletURL.toString());
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}

		return mapping.findForward(ActionConstants.COMMON_NULL);
	}
}