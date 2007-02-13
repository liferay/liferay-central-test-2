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

package com.liferay.portlet.journal.service.ejb;

import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="JournalArticleLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleLocalServiceEJBImpl
	implements JournalArticleLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
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
		javax.portlet.PortletPreferences prefs, java.lang.String[] tagsEntries,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().addArticle(userId,
			articleId, autoArticleId, plid, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs,
			tagsEntries, addCommunityPermissions, addGuestPermissions);
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
		javax.portlet.PortletPreferences prefs, java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().addArticle(userId,
			articleId, autoArticleId, plid, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs,
			tagsEntries, communityPermissions, guestPermissions);
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
		javax.portlet.PortletPreferences prefs, java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().addArticle(userId,
			articleId, autoArticleId, plid, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs,
			tagsEntries, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public void addArticleResources(java.lang.String companyId, long groupId,
		java.lang.String articleId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().addArticleResources(companyId,
			groupId, articleId, addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().addArticleResources(article,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(java.lang.String companyId, long groupId,
		java.lang.String articleId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().addArticleResources(companyId,
			groupId, articleId, communityPermissions, guestPermissions);
	}

	public void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().addArticleResources(article,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalArticle approveArticle(
		java.lang.String userId, long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().approveArticle(userId,
			groupId, articleId, version, articleURL, prefs);
	}

	public void checkArticles()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().checkArticles();
	}

	public void checkNewLine(java.lang.String companyId, long groupId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().checkNewLine(companyId,
			groupId, articleId, version);
	}

	public void deleteArticle(java.lang.String companyId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().deleteArticle(companyId,
			groupId, articleId, version, articleURL, prefs);
	}

	public void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().deleteArticle(article,
			articleURL, prefs);
	}

	public void deleteArticles(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().deleteArticles(groupId);
	}

	public void expireArticle(java.lang.String companyId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().expireArticle(companyId,
			groupId, articleId, version, articleURL, prefs);
	}

	public void expireArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().expireArticle(article,
			articleURL, prefs);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		java.lang.String companyId, long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticle(companyId,
			groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		java.lang.String companyId, long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticle(companyId,
			groupId, articleId, version);
	}

	public java.lang.String getArticleContent(java.lang.String companyId,
		long groupId, java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticleContent(companyId,
			groupId, articleId, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(java.lang.String companyId,
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticleContent(companyId,
			groupId, articleId, version, languageId, themeDisplay);
	}

	public java.util.List getArticles()
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticles();
	}

	public java.util.List getArticles(long groupId)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticles(groupId);
	}

	public java.util.List getArticles(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticles(groupId,
			begin, end);
	}

	public java.util.List getArticles(long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticles(groupId,
			begin, end, obc);
	}

	public int getArticlesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getArticlesCount(groupId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getDisplayArticle(
		java.lang.String companyId, long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getDisplayArticle(companyId,
			groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		java.lang.String companyId, long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getLatestArticle(companyId,
			groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		java.lang.String companyId, long groupId, java.lang.String articleId,
		java.lang.Boolean approved)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getLatestArticle(companyId,
			groupId, articleId, approved);
	}

	public double getLatestVersion(java.lang.String companyId, long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().getLatestVersion(companyId,
			groupId, articleId);
	}

	public java.util.List getStructureArticles(java.lang.String companyId,
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl()
												.getStructureArticles(companyId,
			groupId, structureId);
	}

	public java.util.List getStructureArticles(java.lang.String companyId,
		long groupId, java.lang.String structureId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl()
												.getStructureArticles(companyId,
			groupId, structureId, begin, end, obc);
	}

	public int getStructureArticlesCount(java.lang.String companyId,
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl()
												.getStructureArticlesCount(companyId,
			groupId, structureId);
	}

	public java.util.List getTemplateArticles(java.lang.String companyId,
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl()
												.getTemplateArticles(companyId,
			groupId, templateId);
	}

	public java.util.List getTemplateArticles(java.lang.String companyId,
		long groupId, java.lang.String templateId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl()
												.getTemplateArticles(companyId,
			groupId, templateId, begin, end, obc);
	}

	public int getTemplateArticlesCount(java.lang.String companyId,
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl()
												.getTemplateArticlesCount(companyId,
			groupId, templateId);
	}

	public boolean isLatestVersion(java.lang.String companyId, long groupId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().isLatestVersion(companyId,
			groupId, articleId, version);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalServiceFactory.getTxImpl().reIndex(ids);
	}

	public com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		java.lang.String companyId, long groupId, java.lang.String articleId,
		double version, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl()
												.removeArticleLocale(companyId,
			groupId, articleId, version, languageId);
	}

	public com.liferay.portal.kernel.search.Hits search(
		java.lang.String companyId, long groupId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type) throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().search(companyId,
			groupId, title, description, content, type);
	}

	public com.liferay.portal.kernel.search.Hits search(
		java.lang.String companyId, long groupId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String sortField)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().search(companyId,
			groupId, title, description, content, type, sortField);
	}

	public java.util.List search(java.lang.String companyId, long groupId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.lang.Boolean approved, java.lang.Boolean expired,
		java.util.Date reviewDate, boolean andOperator, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().search(companyId,
			groupId, articleId, version, title, description, content, type,
			structureId, templateId, displayDateGT, displayDateLT, approved,
			expired, reviewDate, andOperator, begin, end, obc);
	}

	public int searchCount(java.lang.String companyId, long groupId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.lang.Boolean approved, java.lang.Boolean expired,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().searchCount(companyId,
			groupId, articleId, version, title, description, content, type,
			structureId, templateId, displayDateGT, displayDateLT, approved,
			expired, reviewDate, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		java.lang.String userId, long groupId, java.lang.String articleId,
		double version, boolean incrementVersion, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		java.util.Map images, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs, java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().updateArticle(userId,
			groupId, articleId, version, incrementVersion, title, description,
			content, type, structureId, templateId, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, images, articleURL,
			prefs, tagsEntries);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateContent(
		java.lang.String companyId, long groupId, java.lang.String articleId,
		double version, java.lang.String content)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalArticleLocalServiceFactory.getTxImpl().updateContent(companyId,
			groupId, articleId, version, content);
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