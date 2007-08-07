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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetTemplateAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class GetTemplateAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			long groupId = ParamUtil.getLong(req, "groupId");
			String templateId = getTemplateId(req);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			Map tokens = JournalUtil.getTokens(groupId, themeDisplay);

			tokens.put("template_id", templateId);

			String languageId = LanguageUtil.getLanguageId(req);

			boolean transform = ParamUtil.get(req, "transform", true);

			JournalTemplate template =
				JournalTemplateLocalServiceUtil.getTemplate(
					groupId, templateId);

			String script = JournalUtil.getTemplateScript(
				template, tokens, languageId, transform);

			String extension = JournalTemplateImpl.LANG_TYPE_VM;

			if (template.getLangType() != null) {
				extension = template.getLangType();
			}

			String fileName = null;
			byte[] byteArray = script.getBytes();

			String contentType = Constants.TEXT_PLAIN;

			if (Validator.equals(
					extension, JournalTemplateImpl.LANG_TYPE_CSS)) {

				contentType = Constants.TEXT_CSS;
			}
			else if (Validator.equals(
					extension, JournalTemplateImpl.LANG_TYPE_XSL)) {

				contentType = Constants.TEXT_XML;
			}

			ServletResponseUtil.sendFile(
				res, fileName, byteArray, contentType + "; charset=UTF-8");

			return null;
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(Constants.COMMON_ERROR);
		}
	}

	protected String getTemplateId(HttpServletRequest req) {
		String templateId = ParamUtil.getString(req, "templateId");

		// Backwards compatibility

		if (Validator.isNull(templateId)) {
			templateId = ParamUtil.getString(req, "template_id");
		}

		return templateId;
	}

}