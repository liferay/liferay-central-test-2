/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalArticleLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleLocalService
 * @generated
 */
public class JournalArticleLocalServiceWrapper
	implements JournalArticleLocalService {
	public JournalArticleLocalServiceWrapper(
		JournalArticleLocalService journalArticleLocalService) {
		_journalArticleLocalService = journalArticleLocalService;
	}

	public com.liferay.portlet.journal.model.JournalArticle addJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.addJournalArticle(journalArticle);
	}

	public com.liferay.portlet.journal.model.JournalArticle createJournalArticle(
		long id) {
		return _journalArticleLocalService.createJournalArticle(id);
	}

	public void deleteJournalArticle(long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.deleteJournalArticle(id);
	}

	public void deleteJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		_journalArticleLocalService.deleteJournalArticle(journalArticle);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.journal.model.JournalArticle getJournalArticle(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getJournalArticle(id);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getJournalArticles(
		int start, int end) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getJournalArticles(start, end);
	}

	public int getJournalArticlesCount()
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getJournalArticlesCount();
	}

	public com.liferay.portlet.journal.model.JournalArticle updateJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean merge) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateJournalArticle(journalArticle,
			merge);
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, long groupId, java.lang.String articleId,
		boolean autoArticleId, double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.addArticle(userId, groupId,
			articleId, autoArticleId, version, title, description, content,
			type, structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, long groupId, java.lang.String articleId,
		boolean autoArticleId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.addArticle(userId, groupId,
			articleId, autoArticleId, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
		java.lang.String uuid, long userId, long groupId,
		java.lang.String articleId, boolean autoArticleId, double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.addArticle(uuid, userId, groupId,
			articleId, autoArticleId, version, title, description, content,
			type, structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, serviceContext);
	}

	public void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.addArticleResources(article,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.addArticleResources(article,
			communityPermissions, guestPermissions);
	}

	public void addArticleResources(long groupId, java.lang.String articleId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.addArticleResources(groupId, articleId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(long groupId, java.lang.String articleId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.addArticleResources(groupId, articleId,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalArticle checkArticleResourcePrimKey(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.checkArticleResourcePrimKey(groupId,
			articleId, version);
	}

	public void checkArticles()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.checkArticles();
	}

	public void checkNewLine(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.checkNewLine(groupId, articleId, version);
	}

	public void checkStructure(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.checkStructure(groupId, articleId, version);
	}

	public com.liferay.portlet.journal.model.JournalArticle copyArticle(
		long userId, long groupId, java.lang.String oldArticleId,
		java.lang.String newArticleId, boolean autoArticleId, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.copyArticle(userId, groupId,
			oldArticleId, newArticleId, autoArticleId, version);
	}

	public void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.deleteArticle(article, articleURL,
			serviceContext);
	}

	public void deleteArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.deleteArticle(groupId, articleId, version,
			articleURL, serviceContext);
	}

	public void deleteArticles(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.deleteArticles(groupId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticle(id);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticle(groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticle(groupId, articleId,
			version);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleByUrlTitle(groupId,
			urlTitle);
	}

	public java.lang.String getArticleContent(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String templateId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleContent(article,
			templateId, viewMode, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version, java.lang.String viewMode,
		java.lang.String templateId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, version, viewMode, templateId, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, version, viewMode, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String viewMode,
		java.lang.String templateId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, viewMode, templateId, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleContent(groupId,
			articleId, viewMode, languageId, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String templateId, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleDisplay(article,
			templateId, viewMode, languageId, page, xmlRequest, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String templateId, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, version, templateId, viewMode, languageId, page,
			xmlRequest, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String templateId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, version, templateId, viewMode, languageId, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, viewMode, languageId, page, xmlRequest, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String templateId,
		java.lang.String viewMode, java.lang.String languageId, int page,
		java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, templateId, viewMode, languageId, page, xmlRequest,
			themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String templateId,
		java.lang.String viewMode, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, templateId, viewMode, languageId, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String viewMode,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticleDisplay(groupId,
			articleId, viewMode, languageId, themeDisplay);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles()
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticles();
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticles(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticles(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticles(groupId, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticles(groupId, articleId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticlesBySmallImageId(smallImageId);
	}

	public int getArticlesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getArticlesCount(groupId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getDisplayArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getDisplayArticle(groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getLatestArticle(resourcePrimKey);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long resourcePrimKey, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getLatestArticle(resourcePrimKey,
			status);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getLatestArticle(groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getLatestArticle(groupId, articleId,
			status);
	}

	public com.liferay.portlet.journal.model.JournalArticle getLatestArticleByUrlTitle(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getLatestArticleByUrlTitle(groupId,
			urlTitle, status);
	}

	public double getLatestVersion(long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getLatestVersion(groupId, articleId);
	}

	public double getLatestVersion(long groupId, java.lang.String articleId,
		int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.getLatestVersion(groupId, articleId,
			status);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getStructureArticles(groupId,
			structureId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getStructureArticles(groupId,
			structureId, start, end, obc);
	}

	public int getStructureArticlesCount(long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getStructureArticlesCount(groupId,
			structureId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getTemplateArticles(groupId,
			templateId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getTemplateArticles(groupId,
			templateId, start, end, obc);
	}

	public int getTemplateArticlesCount(long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.getTemplateArticlesCount(groupId,
			templateId);
	}

	public boolean hasArticle(long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.hasArticle(groupId, articleId);
	}

	public boolean isLatestVersion(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.isLatestVersion(groupId, articleId,
			version);
	}

	public boolean isLatestVersion(long groupId, java.lang.String articleId,
		double version, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.isLatestVersion(groupId, articleId,
			version, status);
	}

	public void reIndex(
		com.liferay.portlet.journal.model.JournalArticle article)
		throws com.liferay.portal.SystemException {
		_journalArticleLocalService.reIndex(article);
	}

	public void reIndex(long resourcePrimKey)
		throws com.liferay.portal.SystemException {
		_journalArticleLocalService.reIndex(resourcePrimKey);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		_journalArticleLocalService.reIndex(ids);
	}

	public com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.removeArticleLocale(groupId,
			articleId, version, languageId);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId, userId,
			keywords, start, end);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords,
		java.lang.String type, int start, int end)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId, userId,
			keywords, type, start, end);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords,
		java.lang.String type,
		java.util.List<com.liferay.portal.kernel.search.BooleanClause> booleanClauses,
		com.liferay.portal.kernel.search.Sort[] sorts, int start, int end)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId, userId,
			keywords, type, booleanClauses, sorts, start, end);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords,
		java.lang.String type, com.liferay.portal.kernel.search.Sort sort,
		int start, int end) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId, userId,
			keywords, type, sort, start, end);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords,
		java.lang.String type, com.liferay.portal.kernel.search.Sort[] sorts,
		int start, int end) throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId, userId,
			keywords, type, sorts, start, end);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.lang.String keywords,
		java.lang.Double version, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId, keywords,
			version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId,
			articleId, version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, status, reviewDate,
			andOperator, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String[] structureIds,
		java.lang.String[] templateIds, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.search(companyId, groupId,
			articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, status,
			reviewDate, andOperator, start, end, obc);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, int status, java.util.Date reviewDate)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.searchCount(companyId, groupId,
			keywords, version, type, structureId, templateId, displayDateGT,
			displayDateLT, status, reviewDate);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.searchCount(companyId, groupId,
			articleId, version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, status, reviewDate,
			andOperator);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT, int status,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return _journalArticleLocalService.searchCount(companyId, groupId,
			articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, status,
			reviewDate, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, java.lang.String articleId, double version,
		boolean incrementVersion, java.lang.String content)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateArticle(userId, groupId,
			articleId, version, incrementVersion, content);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long userId, long groupId, java.lang.String articleId, double version,
		boolean incrementVersion, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		int reviewDateMonth, int reviewDateDay, int reviewDateYear,
		int reviewDateHour, int reviewDateMinute, boolean neverReview,
		boolean indexable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.util.Map<String, byte[]> images,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateArticle(userId, groupId,
			articleId, version, incrementVersion, title, description, content,
			type, structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, serviceContext);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.journal.model.JournalArticle article,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalArticleLocalService.updateAsset(userId, article,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateContent(
		long groupId, java.lang.String articleId, double version,
		java.lang.String content)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateContent(groupId, articleId,
			version, content);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, com.liferay.portlet.journal.model.JournalArticle article,
		int status, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateStatus(userId, article,
			status, articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, long resourcePrimKey, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateStatus(userId,
			resourcePrimKey, status);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long userId, long groupId, java.lang.String articleId, double version,
		int status, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalArticleLocalService.updateStatus(userId, groupId,
			articleId, version, status, articleURL, serviceContext);
	}

	public JournalArticleLocalService getWrappedJournalArticleLocalService() {
		return _journalArticleLocalService;
	}

	private JournalArticleLocalService _journalArticleLocalService;
}