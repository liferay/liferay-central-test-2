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

import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.io.SAXReader;

/**
 * <a href="GetArticleContentAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class GetArticleAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			long groupId = ParamUtil.getLong(req, "groupId");
			String articleId =  ParamUtil.getString(req, "articleId");
			
			JournalArticle article = 
				JournalArticleLocalServiceUtil.getLatestArticle(groupId, 
						articleId, Boolean.TRUE);			

			String languageId = LanguageUtil.getLanguageId(req);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			Map tokens = JournalUtil.getTokens(groupId, themeDisplay);

			String xml = article.getContentByLocale(languageId);

			String contentType = Constants.TEXT_HTML;
			
			Document doc = null;

			Element root = null;
			
			if (article.isTemplateDriven()) {
				SAXReader reader = new SAXReader();

				doc = reader.read(new StringReader(xml));

				root = doc.getRootElement();
				
				_addProcessingInstructions(doc, themeDisplay, article, groupId);
				
				JournalUtil.addAllReservedEls(root, tokens, article);

				xml = JournalUtil.formatXML(doc);
				
				contentType = Constants.TEXT_XML;
			}

			byte[] byteArray = xml.getBytes();
			
			res.setContentLength(byteArray.length);
			
			res.setContentType(contentType + ";charset=UTF-8");
			
			ServletResponseUtil.write(res, byteArray);

			return null;
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(Constants.COMMON_ERROR);
		}
	}
	
	protected void _addProcessingInstructions(Document doc, 
		ThemeDisplay themeDisplay, JournalArticle article, long groupId) {
		
		// Add styleSheets in reverse order that they should appear in document.
		
		// Theme CSS
		
		String styleSheetUrl = themeDisplay.getPathThemeCss() + 
			"/main.css?companyId=" + themeDisplay.getCompanyId() + 
			"&themeId=" + themeDisplay.getTheme().getThemeId() +
			"&colorSchemeId=" + themeDisplay.getColorScheme().getColorSchemeId();
	
		Map arguments = new LinkedHashMap();
		arguments.put("type", "text/css");
		arguments.put("href", styleSheetUrl);
	
		_addStyleSheet(doc, styleSheetUrl, arguments);		

		// CSS Cached
		
		styleSheetUrl = themeDisplay.getPathMain() + 
			"/portal/css_cached?themeId=" + 
			themeDisplay.getTheme().getThemeId() + 
			"&colorSchemeId=" + themeDisplay.getColorScheme().getColorSchemeId();

		arguments = new LinkedHashMap();
		arguments.put("type", "text/css");
		arguments.put("href", styleSheetUrl);

		_addStyleSheet(doc, styleSheetUrl, arguments);
		
		// XSL Template
		
		String templateId = article.getTemplateId();
		
		if (Validator.isNotNull(templateId)) {
			JournalTemplate template = null;
			
			try {
				template = JournalTemplateLocalServiceUtil.getTemplate(
						groupId, templateId); 

				if (Validator.equals(template.getLangType(), 
						JournalTemplateImpl.LANG_TYPE_XSL)) {
		
					styleSheetUrl = themeDisplay.getPathMain() + 
						"/journal/get_template?groupId=" + groupId + 
						"&templateId=" + templateId;
			
					arguments = new LinkedHashMap();
					arguments.put("type", "text/xsl");
					arguments.put("href", styleSheetUrl);
					
					_addStyleSheet(doc, styleSheetUrl, arguments);			
				}			
			}
			catch (Exception e) {
			}			
		}
	}
	
	protected void _addStyleSheet(Document doc, String url, Map arguments) {
		List content = doc.content();

		ProcessingInstruction pi
				= DocumentFactory.getInstance().createProcessingInstruction(
						"xml-stylesheet", arguments);

		content.add(0, pi);
	}
}