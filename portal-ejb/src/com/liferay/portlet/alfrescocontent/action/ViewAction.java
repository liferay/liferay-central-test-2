/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.alfrescocontent.action;

import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.alfresco.service.spring.AlfrescoContentLocalServiceUtil;
import com.liferay.portlet.alfrescocontent.util.AlfrescoContentUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ViewAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		PortletPreferences prefs = req.getPreferences();

		String baseURL = prefs.getValue("base-url", StringPool.BLANK);
		String contentUuid = prefs.getValue("content-uuid", StringPool.BLANK);
		String userId = prefs.getValue("user-id", StringPool.BLANK);
		String password = prefs.getValue("password", StringPool.BLANK);
		boolean maximizeLinks = GetterUtil.getBoolean(
			prefs.getValue("maximize-links", StringPool.BLANK));

//		String url = baseURL + "/" + ParamUtil.getString(req, "url", indexURL);
//
		String previewURL = ParamUtil.getString(req, "previewURL");
//
//		if (Validator.isNotNull(previewURL)) {
//			url = previewURL;
//		}

//		String content = AlfrescoContentUtil.getContent(
//			url, userId, password, maximizeLinks, res);

		String content = AlfrescoContentLocalServiceUtil.getContent(contentUuid);
		
		req.setAttribute(WebKeys.ALFRESCO_CONTENT, content);

		if (Validator.isNull(previewURL)) {
			return mapping.findForward("portlet.alfresco_content.view_1");
		}
		else {
			return mapping.findForward("portlet.alfresco_content.view_2");
		}
	}

}