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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.JournalTemplateConstants;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetTemplateAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class GetTemplateAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long groupId = ParamUtil.getLong(request, "groupId");
			String templateId = getTemplateId(request);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Map<String, String> tokens = JournalUtil.getTokens(
				groupId, themeDisplay);

			tokens.put("template_id", templateId);

			String languageId = LanguageUtil.getLanguageId(request);

			boolean transform = ParamUtil.get(request, "transform", true);

			JournalTemplate template =
				JournalTemplateLocalServiceUtil.getTemplate(
					groupId, templateId);

			String script = JournalUtil.getTemplateScript(
				template, tokens, languageId, transform);

			String extension = JournalTemplateConstants.LANG_TYPE_VM;

			if (template.getLangType() != null) {
				extension = template.getLangType();
			}

			String fileName = null;
			byte[] bytes = script.getBytes();

			String contentType = ContentTypes.TEXT_PLAIN_UTF8;

			if (Validator.equals(
					extension, JournalTemplateConstants.LANG_TYPE_CSS)) {

				contentType = ContentTypes.TEXT_CSS_UTF8;
			}
			else if (Validator.equals(
					extension, JournalTemplateConstants.LANG_TYPE_XSL)) {

				contentType = ContentTypes.TEXT_XML_UTF8;
			}

			ServletResponseUtil.sendFile(
				response, fileName, bytes, contentType);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected String getTemplateId(HttpServletRequest request) {
		String templateId = ParamUtil.getString(request, "templateId");

		// Backwards compatibility

		if (Validator.isNull(templateId)) {
			templateId = ParamUtil.getString(request, "template_id");
		}

		return templateId;
	}

}