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

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.util.servlet.ServletResponseUtil;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetArticlesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class GetArticlesAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			List<JournalArticle> articles = getArticles(request);

			String fileName = null;
			byte[] bytes = getContent(request, articles);

			ServletResponseUtil.sendFile(
				response, fileName, bytes, ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected List<JournalArticle> getArticles(HttpServletRequest request)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);
		long groupId = DAOParamUtil.getLong(request, "groupId");
		String articleId = null;
		Double version = null;
		String title = null;
		String description = null;
		String content = null;
		String type = DAOParamUtil.getString(request, "type");
		String[] structureIds = StringUtil.split(
			DAOParamUtil.getString(request, "structureId"));
		String[] templateIds = StringUtil.split(
			DAOParamUtil.getString(request, "templateId"));

		Date displayDateGT = null;

		String displayDateGTParam = ParamUtil.getString(
			request, "displayDateGT");

		if (Validator.isNotNull(displayDateGTParam)) {
			DateFormat displayDateGTFormat = DateUtil.getISOFormat(
				displayDateGTParam);

			displayDateGT = GetterUtil.getDate(
				displayDateGTParam, displayDateGTFormat);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("displayDateGT is " + displayDateGT);
		}

		Date displayDateLT = null;

		String displayDateLTParam = ParamUtil.getString(
			request, "displayDateLT");

		if (Validator.isNotNull(displayDateLTParam)) {
			DateFormat displayDateLTFormat = DateUtil.getISOFormat(
				displayDateLTParam);

			displayDateLT = GetterUtil.getDate(
				displayDateLTParam, displayDateLTFormat);
		}

		if (displayDateLT == null) {
			displayDateLT = new Date();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("displayDateLT is " + displayDateLT);
		}

		int status = StatusConstants.APPROVED;
		Date reviewDate = null;
		boolean andOperator = true;
		int start = 0;
		int end = ParamUtil.getInteger(request, "delta", 5);
		String orderBy = ParamUtil.getString(request, "orderBy");
		String orderByCol = ParamUtil.getString(request, "orderByCol", orderBy);
		String orderByType = ParamUtil.getString(request, "orderByType");
		boolean orderByAsc = orderByType.equals("asc");

		OrderByComparator obc = new ArticleModifiedDateComparator(orderByAsc);

		if (orderByCol.equals("display-date")) {
			obc = new ArticleDisplayDateComparator(orderByAsc);
		}

		return JournalArticleLocalServiceUtil.search(
			companyId, groupId, articleId, version, title, description, content,
			type, structureIds, templateIds, displayDateGT, displayDateLT,
			status, reviewDate, andOperator, start, end, obc);
	}

	protected byte[] getContent(
			HttpServletRequest request, List<JournalArticle> articles)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");

		String languageId = LanguageUtil.getLanguageId(request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> tokens = JournalUtil.getTokens(
			groupId, themeDisplay);

		Document resultsDoc = SAXReaderUtil.createDocument(StringPool.UTF8);

		Element resultSetEl = resultsDoc.addElement("result-set");

		for (JournalArticle article : articles) {
			Element resultEl = resultSetEl.addElement("result");

			Document articleDoc = SAXReaderUtil.read(
				article.getContentByLocale(languageId));

			resultEl.content().add(
				articleDoc.getRootElement().createCopy());

			resultEl = resultEl.element("root");

			JournalUtil.addAllReservedEls(resultEl, tokens, article);
		}

		return JournalUtil.formatXML(resultsDoc).getBytes(StringPool.UTF8);
	}

	private static Log _log = LogFactoryUtil.getLog(GetArticlesAction.class);

}