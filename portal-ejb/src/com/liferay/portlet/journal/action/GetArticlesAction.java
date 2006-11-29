/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.dao.DAOParamUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.StringReader;

import java.text.DateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="GetArticlesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Raymond Auge
 * @author  Brian Wing Shun Chan
 *
 */
public class GetArticlesAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			List articles = getArticles(req);

			String fileName = "articles.xml";
			byte[] byteArray = getContent(req, articles);

			ServletResponseUtil.sendFile(res, fileName, byteArray);

			return null;
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(Constants.COMMON_ERROR);
		}
	}

	protected List getArticles(HttpServletRequest req) throws Exception {
		String companyId = PortalUtil.getCompanyId(req);
		String articleId = null;
		Double version = null;
		String groupId = DAOParamUtil.getString(req, "groupId");
		String title = null;
		String description = null;
		String content = null;
		String type = DAOParamUtil.getString(req, "type");
		String structureId = DAOParamUtil.getString(req, "structureId");
		String templateId = DAOParamUtil.getString(req, "templateId");

		Date gtDate = null;

		String gtDateParam = ParamUtil.getString(req, "gtDate");

		if (Validator.isNotNull(gtDateParam)) {
			DateFormat gtDateFormat = DateUtil.getISOFormat(gtDateParam);

			gtDate = GetterUtil.getDate(gtDateParam, gtDateFormat);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("gtDate is " + gtDate);
		}

		Date ltDate = null;

		String ltDateParam = ParamUtil.getString(req, "ltDate");

		if (Validator.isNotNull(ltDateParam)) {
			DateFormat ltDateFormat = DateUtil.getISOFormat(ltDateParam);

			ltDate = GetterUtil.getDate(ltDateParam, ltDateFormat);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("ltDate is " + ltDate);
		}

		Boolean approved = Boolean.TRUE;
		Boolean expired = Boolean.FALSE;
		Date reviewDate = null;
		boolean andOperator = true;
		int begin = 0;
		int end = ParamUtil.getInteger(req, "delta", 5);
		String orderBy = ParamUtil.getString(req, "orderBy");

		OrderByComparator obc = new ArticleModifiedDateComparator(false);

		if (orderBy.equals("displayDate")) {
			obc = new ArticleDisplayDateComparator(false);
		}

		return JournalArticleLocalServiceUtil.search(
			companyId, articleId, version, groupId, title, description, content,
			type, structureId, templateId, gtDate, ltDate, approved, expired,
			reviewDate, andOperator, begin, end, obc);
	}

	protected byte[] getContent(HttpServletRequest req, List articles)
		throws Exception {

		String languageId = LanguageUtil.getLanguageId(req);

		Document resultsDoc =
			DocumentFactory.getInstance().createDocument();

		Element resultSetEl = resultsDoc.addElement("result-set");

		SAXReader reader = new SAXReader();

		Iterator itr = articles.iterator();

		while (itr.hasNext()) {
			JournalArticle article = (JournalArticle)itr.next();

			Element resultEl = resultSetEl.addElement("result");

			resultEl.addAttribute("article-id", article.getArticleId());

			Element infoEl = resultEl.addElement("reserved-info");

			infoEl.addElement("version").addText(
				String.valueOf(article.getVersion()));

			infoEl.addElement("title").addText(article.getTitle());

			infoEl.addElement("description").addText(article.getDescription());

			infoEl.addElement("create-date").addText(
				Time.getRFC822(article.getCreateDate()));

			infoEl.addElement("modified-date").addText(
				Time.getRFC822(article.getModifiedDate()));

			infoEl.addElement("display-date").addText(
				Time.getRFC822(article.getDisplayDate()));

			infoEl.addElement("author-id").addText(
				String.valueOf(article.getUserId()));

			String userName = StringPool.BLANK;
			String userEmailAddress = StringPool.BLANK;

			User user = null;

			try {
				user = UserLocalServiceUtil.getUserById(article.getUserId());

				userName = user.getFullName();
				userEmailAddress = user.getEmailAddress();
			}
			catch (NoSuchUserException nsue) {
			}

			infoEl.addElement("author-name").addText(userName);

			infoEl.addElement("author-email-address").addText(userEmailAddress);

			Document articleDoc = reader.read(
				new StringReader(article.getContentByLocale(languageId)));

			resultEl.content().add(
				articleDoc.getRootElement().createCopy());
		}

		return JournalUtil.formatXML(resultsDoc).getBytes();
	}

	private static Log _log = LogFactory.getLog(GetArticlesAction.class);

}