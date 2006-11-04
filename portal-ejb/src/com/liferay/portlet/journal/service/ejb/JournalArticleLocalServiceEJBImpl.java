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

package com.liferay.portlet.journal.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.journal.service.spring.JournalArticleLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="JournalArticleLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleLocalServiceEJBImpl
	implements JournalArticleLocalService, SessionBean {
	public static final String CLASS_NAME = JournalArticleLocalService.class.getName() +
		".transaction";

	public static JournalArticleLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (JournalArticleLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
		java.lang.String userId, java.lang.String articleId,
		boolean autoArticleId, java.lang.String plid, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		java.util.Map images, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addArticle(userId, articleId, autoArticleId, plid,
			title, description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, images, articleURL,
			prefs, addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(java.lang.String companyId,
		java.lang.String articleId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addArticleResources(companyId, articleId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addArticleResources(article, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalArticle approveArticle(
		java.lang.String userId, java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().approveArticle(userId, articleId, version,
			articleURL, prefs);
	}

	public void checkArticles()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().checkArticles();
	}

	public void checkNewLine(java.lang.String companyId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().checkNewLine(companyId, articleId, version);
	}

	public void deleteArticle(java.lang.String companyId,
		java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteArticle(companyId, articleId, version, articleURL,
			prefs);
	}

	public void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteArticle(article, articleURL, prefs);
	}

	public void deleteArticles(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteArticles(groupId);
	}

	public void expireArticle(java.lang.String companyId,
		java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().expireArticle(companyId, articleId, version, articleURL,
			prefs);
	}

	public void expireArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().expireArticle(article, articleURL, prefs);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		java.lang.String companyId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getArticle(companyId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		java.lang.String companyId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getArticle(companyId, articleId, version);
	}

	public java.lang.String getArticleContent(java.lang.String companyId,
		java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getArticleContent(companyId, articleId, languageId,
			themeDisplay);
	}

	public java.lang.String getArticleContent(java.lang.String companyId,
		java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getArticleContent(companyId, articleId, version,
			languageId, themeDisplay);
	}

	public java.util.List getArticles(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getArticles(groupId);
	}

	public java.util.List getArticles(java.lang.String groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getArticles(groupId, begin, end);
	}

	public java.util.List getArticles(java.lang.String groupId, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getArticles(groupId, begin, end, obc);
	}

	public int getArticlesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getArticlesCount(groupId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getDisplayArticle(
		java.lang.String companyId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getDisplayArticle(companyId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		java.lang.String companyId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getLatestArticle(companyId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		java.lang.String companyId, java.lang.String articleId,
		java.lang.Boolean approved)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getLatestArticle(companyId, articleId, approved);
	}

	public double getLatestVersion(java.lang.String companyId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getLatestVersion(companyId, articleId);
	}

	public java.util.List getStructureArticles(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getService().getStructureArticles(companyId, structureId);
	}

	public java.util.List getStructureArticles(java.lang.String companyId,
		java.lang.String structureId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getStructureArticles(companyId, structureId, begin,
			end, obc);
	}

	public int getStructureArticlesCount(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getService().getStructureArticlesCount(companyId, structureId);
	}

	public java.util.List getTemplateArticles(java.lang.String companyId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		return getService().getTemplateArticles(companyId, templateId);
	}

	public java.util.List getTemplateArticles(java.lang.String companyId,
		java.lang.String templateId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getTemplateArticles(companyId, templateId, begin,
			end, obc);
	}

	public int getTemplateArticlesCount(java.lang.String companyId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		return getService().getTemplateArticlesCount(companyId, templateId);
	}

	public boolean isLatestVersion(java.lang.String companyId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().isLatestVersion(companyId, articleId, version);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		getService().reIndex(ids);
	}

	public com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		java.lang.String companyId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().removeArticleLocale(companyId, articleId, version,
			languageId);
	}

	public com.liferay.util.lucene.Hits search(java.lang.String companyId,
		java.lang.String groupId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type) throws com.liferay.portal.SystemException {
		return getService().search(companyId, groupId, title, description,
			content, type);
	}

	public com.liferay.util.lucene.Hits search(java.lang.String companyId,
		java.lang.String groupId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, org.apache.lucene.search.Sort sort)
		throws com.liferay.portal.SystemException {
		return getService().search(companyId, groupId, title, description,
			content, type, sort);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String groupId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.lang.Boolean approved,
		java.lang.Boolean expired, java.util.Date reviewDate,
		boolean andOperator, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().search(companyId, articleId, version, groupId,
			title, description, content, type, structureId, templateId,
			displayDateGT, displayDateLT, approved, expired, reviewDate,
			andOperator, begin, end, obc);
	}

	public int searchCount(java.lang.String companyId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String groupId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.lang.Boolean approved,
		java.lang.Boolean expired, java.util.Date reviewDate,
		boolean andOperator) throws com.liferay.portal.SystemException {
		return getService().searchCount(companyId, articleId, version, groupId,
			title, description, content, type, structureId, templateId,
			displayDateGT, displayDateLT, approved, expired, reviewDate,
			andOperator);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		java.lang.String userId, java.lang.String articleId, double version,
		boolean incrementVersion, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		java.util.Map images, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateArticle(userId, articleId, version,
			incrementVersion, title, description, content, type, structureId,
			templateId, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			images, articleURL, prefs);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}