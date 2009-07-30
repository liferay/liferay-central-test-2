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

package com.liferay.portal.servlet.taglib.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * <a href="ActionURLTagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ActionURLTagUtil {

	public static String doEndTag(
			String lifecycle, String windowState, String portletMode,
			String var, String varImpl, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			String resourceID, String cacheability, long plid,
			String portletName, Boolean anchor, Boolean encrypt,
			long doAsUserId, Boolean portletConfiguration,
			Map<String, String[]> params, boolean writeOutput,
			PageContext pageContext)
		throws JspException {

		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			if (portletName == null) {
				portletName = TagUtil.getPortletName(request);
			}

			LiferayPortletURL portletURL = TagUtil.getLiferayPortletURL(
				request, plid, portletName, lifecycle);

			if (portletURL == null) {
				_log.error(
					"Render response is null because this tag is not being " +
						"called within the context of a portlet");

				return StringPool.BLANK;
			}

			if (Validator.isNotNull(windowState)) {
				portletURL.setWindowState(
					WindowStateFactory.getWindowState(windowState));
			}

			if (Validator.isNotNull(portletMode)) {
				portletURL.setPortletMode(
					PortletModeFactory.getPortletMode(portletMode));
			}

			if (secure != null) {
				portletURL.setSecure(secure.booleanValue());
			}
			else {
				portletURL.setSecure(request.isSecure());
			}

			if (copyCurrentRenderParameters != null) {
				portletURL.setCopyCurrentRenderParameters(
					copyCurrentRenderParameters.booleanValue());
			}

			if (escapeXml != null) {
				portletURL.setEscapeXml(escapeXml.booleanValue());
			}

			if (lifecycle.equals(PortletRequest.ACTION_PHASE) &&
				Validator.isNotNull(name)) {

				portletURL.setParameter(ActionRequest.ACTION_NAME, name);
			}

			if (resourceID != null) {
				portletURL.setResourceID(resourceID);
			}

			if (cacheability != null) {
				portletURL.setCacheability(cacheability);
			}

			if (anchor != null) {
				portletURL.setAnchor(anchor.booleanValue());
			}

			if (encrypt != null) {
				portletURL.setEncrypt(encrypt.booleanValue());
			}

			if (doAsUserId > 0) {
				portletURL.setDoAsUserId(doAsUserId);
			}

			if ((portletConfiguration != null) &&
				portletConfiguration.booleanValue()) {

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

			if (params != null) {
				MapUtil.merge(portletURL.getParameterMap(), params);

				portletURL.setParameters(params);
			}

			if (Validator.isNotNull(var)) {
				pageContext.setAttribute(var, portletURL.toString());
			}
			else if (Validator.isNotNull(varImpl)) {
				pageContext.setAttribute(varImpl, portletURL);
			}
			else if (writeOutput) {
				pageContext.getOut().print(portletURL.toString());
			}

			return portletURL.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new JspException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ActionURLTagUtil.class);

}