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

package com.liferay.portlet.journalcontent.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.io.OutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="WebContentAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class WebContentAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletPreferences preferences = actionRequest.getPreferences();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if (groupId < 1) {
			groupId = GetterUtil.getLong(
				preferences.getValue("group-id", StringPool.BLANK));
		}

		String articleId = ParamUtil.getString(actionRequest, "articleId");
		String templateId = ParamUtil.getString(actionRequest, "templateId");

		if (Validator.isNull(articleId)) {
			articleId = GetterUtil.getString(
				preferences.getValue("article-id", StringPool.BLANK));
			templateId = GetterUtil.getString(
				preferences.getValue("template-id", StringPool.BLANK));
		}

		String viewMode = ParamUtil.getString(actionRequest, "viewMode");
		String languageId = LanguageUtil.getLanguageId(actionRequest);
		int page = ParamUtil.getInteger(actionRequest, "page", 1);

		String xmlRequest = PortletRequestUtil.toXML(
			actionRequest, actionResponse);

		if ((groupId > 0) && Validator.isNotNull(articleId)) {
			JournalContentUtil.getDisplay(
				groupId, articleId, templateId, viewMode, languageId,
				themeDisplay, page, xmlRequest);
		}
	}

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String contentType = ParamUtil.getString(
			resourceRequest, "contentType");

		if (Validator.isNotNull(contentType)) {
			resourceResponse.setContentType(contentType);
		}

		if (resourceRequest.getResourceID() != null) {
			super.serveResource(
				mapping, form, portletConfig, resourceRequest,
				resourceResponse);
		}
		else {
			PortletPreferences preferences = resourceRequest.getPreferences();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(resourceRequest, "groupId");

			if (groupId < 1) {
				groupId = GetterUtil.getLong(
					preferences.getValue("group-id", StringPool.BLANK));
			}

			String articleId = ParamUtil.getString(
				resourceRequest, "articleId");
			String templateId = ParamUtil.getString(
				resourceRequest, "templateId");

			if (Validator.isNull(articleId)) {
				articleId = GetterUtil.getString(
					preferences.getValue("article-id", StringPool.BLANK));
				templateId = GetterUtil.getString(
					preferences.getValue("template-id", StringPool.BLANK));
			}

			String viewMode = ParamUtil.getString(resourceRequest, "viewMode");
			String languageId = LanguageUtil.getLanguageId(resourceRequest);
			int page = ParamUtil.getInteger(resourceRequest, "page", 1);
			String xmlRequest = PortletRequestUtil.toXML(
				resourceRequest, resourceResponse);

			JournalArticleDisplay articleDisplay = null;

			if ((groupId > 0) && Validator.isNotNull(articleId)) {
				articleDisplay = JournalContentUtil.getDisplay(
					groupId, articleId, templateId, viewMode, languageId,
					themeDisplay, page, xmlRequest);
			}

			if (articleDisplay != null) {
				OutputStream os = resourceResponse.getPortletOutputStream();

				try {
					String content = articleDisplay.getContent();

					byte[] bytes = content.getBytes(StringPool.UTF8);

					os.write(bytes);
				}
				finally {
					os.close();
				}
			}
		}
	}

}