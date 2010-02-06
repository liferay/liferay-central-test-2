/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.taglib.ui;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * <a href="InputPermissionsTagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Chan
 */
public class InputPermissionsTagUtil {

	public static void doEndTag(
			String page, String formName, String modelName,
			PageContext pageContext)
		throws JspException {

		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.setAttribute(
				"liferay-ui:input-permissions:formName", formName);

			if (modelName != null) {
				List<String> supportedActions =
					ResourceActionsUtil.getModelResourceActions(modelName);
				List<String> communityDefaultActions =
					ResourceActionsUtil.getModelResourceCommunityDefaultActions(
						modelName);
				List<String> guestDefaultActions =
					ResourceActionsUtil.getModelResourceGuestDefaultActions(
						modelName);
				List<String> guestUnsupportedActions =
					ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
						modelName);

				request.setAttribute(
					"liferay-ui:input-permissions:modelName", modelName);
				request.setAttribute(
					"liferay-ui:input-permissions:supportedActions",
					supportedActions);
				request.setAttribute(
					"liferay-ui:input-permissions:communityDefaultActions",
					communityDefaultActions);
				request.setAttribute(
					"liferay-ui:input-permissions:guestDefaultActions",
					guestDefaultActions);
				request.setAttribute(
					"liferay-ui:input-permissions:guestUnsupportedActions",
					guestUnsupportedActions);
			}

			PortalIncludeUtil.include(pageContext, page);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new JspException(e);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(InputPermissionsTagUtil.class);

}