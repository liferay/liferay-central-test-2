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

package com.liferay.portlet.journal.service;

/**
 * <a href="JournalArticleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
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
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addArticle(userId, articleId,
			autoArticleId, plid, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
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
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addArticle(userId, articleId,
			autoArticleId, plid, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
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
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addArticle(userId, articleId,
			autoArticleId, plid, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addArticleResources(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.addArticleResources(companyId, groupId,
			articleId, addCommunityPermissions, addGuestPermissions);
	}

	public static void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.addArticleResources(article,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addArticleResources(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.addArticleResources(companyId, groupId,
			articleId, communityPermissions, guestPermissions);
	}

	public static void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.addArticleResources(article,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle approveArticle(
		java.lang.String userId, java.lang.String groupId,
		java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.approveArticle(userId, groupId,
			articleId, version, articleURL, prefs);
	}

	public static void checkArticles()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.checkArticles();
	}

	public static void checkNewLine(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.checkNewLine(companyId, groupId, articleId,
			version);
	}

	public static void deleteArticle(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.deleteArticle(companyId, groupId, articleId,
			version, articleURL, prefs);
	}

	public static void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.deleteArticle(article, articleURL, prefs);
	}

	public static void deleteArticles(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.deleteArticles(groupId);
	}

	public static void expireArticle(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.expireArticle(companyId, groupId, articleId,
			version, articleURL, prefs);
	}

	public static void expireArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.expireArticle(article, articleURL, prefs);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticle(companyId, groupId,
			articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticle(companyId, groupId,
			articleId, version);
	}

	public static java.lang.String getArticleContent(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleContent(companyId, groupId,
			articleId, languageId, themeDisplay);
	}

	public static java.lang.String getArticleContent(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleContent(companyId, groupId,
			articleId, version, languageId, themeDisplay);
	}

	public static java.util.List getArticles()
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles();
	}

	public static java.util.List getArticles(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles(groupId);
	}

	public static java.util.List getArticles(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles(groupId, begin, end);
	}

	public static java.util.List getArticles(java.lang.String groupId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles(groupId, begin, end, obc);
	}

	public static int getArticlesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticlesCount(groupId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getDisplayArticle(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getDisplayArticle(companyId, groupId,
			articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getLatestArticle(companyId, groupId,
			articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId, java.lang.Boolean approved)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getLatestArticle(companyId, groupId,
			articleId, approved);
	}

	public static double getLatestVersion(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getLatestVersion(companyId, groupId,
			articleId);
	}

	public static java.util.List getStructureArticles(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getStructureArticles(companyId,
			groupId, structureId);
	}

	public static java.util.List getStructureArticles(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String structureId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getStructureArticles(companyId,
			groupId, structureId, begin, end, obc);
	}

	public static int getStructureArticlesCount(java.lang.String companyId,
		java.lang.String groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getStructureArticlesCount(companyId,
			groupId, structureId);
	}

	public static java.util.List getTemplateArticles(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getTemplateArticles(companyId,
			groupId, templateId);
	}

	public static java.util.List getTemplateArticles(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String templateId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getTemplateArticles(companyId,
			groupId, templateId, begin, end, obc);
	}

	public static int getTemplateArticlesCount(java.lang.String companyId,
		java.lang.String groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getTemplateArticlesCount(companyId,
			groupId, templateId);
	}

	public static boolean isLatestVersion(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.isLatestVersion(companyId, groupId,
			articleId, version);
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();
		journalArticleLocalService.reIndex(ids);
	}

	public static com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId, double version, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.removeArticleLocale(companyId,
			groupId, articleId, version, languageId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, title,
			description, content, type);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String sortField) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, title,
			description, content, type, sortField);
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.lang.Boolean approved,
		java.lang.Boolean expired, java.util.Date reviewDate,
		boolean andOperator, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, articleId,
			version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, approved, expired,
			reviewDate, andOperator, begin, end, obc);
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.lang.Boolean approved,
		java.lang.Boolean expired, java.util.Date reviewDate,
		boolean andOperator) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.searchCount(companyId, groupId,
			articleId, version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, approved, expired,
			reviewDate, andOperator);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		java.lang.String userId, java.lang.String groupId,
		java.lang.String articleId, double version, boolean incrementVersion,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, java.util.Map images,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.updateArticle(userId, groupId,
			articleId, version, incrementVersion, title, description, content,
			type, structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateContent(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId, double version, java.lang.String content)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.updateContent(companyId, groupId,
			articleId, version, content);
	}
}