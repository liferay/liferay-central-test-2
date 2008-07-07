/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.journal.service.JournalArticleLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.journal.service.JournalArticleLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalArticleLocalService
 * @see com.liferay.portlet.journal.service.JournalArticleLocalServiceFactory
 *
 */
public class JournalArticleLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalArticle addJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addJournalArticle(journalArticle);
	}

	public static void deleteJournalArticle(long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.deleteJournalArticle(id);
	}

	public static void deleteJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.deleteJournalArticle(journalArticle);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getJournalArticle(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getJournalArticle(id);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateJournalArticle(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, java.lang.String articleId, boolean autoArticleId,
		long plid, java.lang.String title, java.lang.String description,
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
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
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
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, prefs, tagsEntries,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, java.lang.String articleId, boolean autoArticleId,
		long plid, double version, java.lang.String title,
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
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addArticle(userId, articleId,
			autoArticleId, plid, version, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, prefs, tagsEntries,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		java.lang.String uuid, long userId, java.lang.String articleId,
		boolean autoArticleId, long plid, double version,
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
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addArticle(uuid, userId, articleId,
			autoArticleId, plid, version, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, prefs, tagsEntries,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		long userId, java.lang.String articleId, boolean autoArticleId,
		long plid, java.lang.String title, java.lang.String description,
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
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs,
		java.lang.String[] tagsEntries,
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
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, prefs, tagsEntries,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		java.lang.String uuid, long userId, java.lang.String articleId,
		boolean autoArticleId, long plid, double version,
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
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs,
		java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addArticle(uuid, userId, articleId,
			autoArticleId, plid, version, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, prefs, tagsEntries,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticleToGroup(
		java.lang.String uuid, long userId, java.lang.String articleId,
		boolean autoArticleId, long groupId, double version,
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
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs,
		java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.addArticleToGroup(uuid, userId,
			articleId, autoArticleId, groupId, version, title, description,
			content, type, structureId, templateId, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public static void addArticleResources(long groupId,
		java.lang.String articleId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.addArticleResources(groupId, articleId,
			addCommunityPermissions, addGuestPermissions);
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

	public static void addArticleResources(long groupId,
		java.lang.String articleId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.addArticleResources(groupId, articleId,
			communityPermissions, guestPermissions);
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
		long userId, long groupId, java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.approveArticle(userId, groupId,
			articleId, version, articleURL, prefs);
	}

	public static com.liferay.portlet.journal.model.JournalArticle checkArticleResourcePrimKey(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.checkArticleResourcePrimKey(groupId,
			articleId, version);
	}

	public static void checkArticles()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.checkArticles();
	}

	public static void checkNewLine(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.checkNewLine(groupId, articleId, version);
	}

	public static void checkStructure(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.checkStructure(groupId, articleId, version);
	}

	public static void deleteArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.deleteArticle(groupId, articleId, version,
			articleURL, prefs);
	}

	public static void deleteArticle(
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.deleteArticle(article, articleURL, prefs);
	}

	public static void deleteArticles(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.deleteArticles(groupId);
	}

	public static void expireArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.expireArticle(groupId, articleId, version,
			articleURL, prefs);
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
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticle(id);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticle(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticle(groupId, articleId, version);
	}

	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleContent(groupId, articleId,
			languageId, themeDisplay);
	}

	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String templateId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleContent(groupId, articleId,
			templateId, languageId, themeDisplay);
	}

	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleContent(groupId, articleId,
			version, languageId, themeDisplay);
	}

	public static java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version,
		java.lang.String templateId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleContent(groupId, articleId,
			version, templateId, languageId, themeDisplay);
	}

	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleDisplay(groupId, articleId,
			languageId, themeDisplay);
	}

	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String languageId,
		int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleDisplay(groupId, articleId,
			languageId, page, xmlRequest, themeDisplay);
	}

	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String templateId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleDisplay(groupId, articleId,
			templateId, languageId, themeDisplay);
	}

	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, java.lang.String templateId,
		java.lang.String languageId, int page, java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleDisplay(groupId, articleId,
			templateId, languageId, page, xmlRequest, themeDisplay);
	}

	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String templateId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleDisplay(groupId, articleId,
			version, templateId, languageId, themeDisplay);
	}

	public static com.liferay.portlet.journal.model.JournalArticleDisplay getArticleDisplay(
		long groupId, java.lang.String articleId, double version,
		java.lang.String templateId, java.lang.String languageId, int page,
		java.lang.String xmlRequest,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticleDisplay(groupId, articleId,
			version, templateId, languageId, page, xmlRequest, themeDisplay);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles()
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticles(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticles(groupId, articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getArticlesBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticlesBySmallImageId(smallImageId);
	}

	public static int getArticlesCount(long groupId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getArticlesCount(groupId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getDisplayArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getDisplayArticle(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getLatestArticle(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getLatestArticle(
		long groupId, java.lang.String articleId, java.lang.Boolean approved)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getLatestArticle(groupId, articleId,
			approved);
	}

	public static double getLatestVersion(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getLatestVersion(groupId, articleId);
	}

	public static double getLatestVersion(long groupId,
		java.lang.String articleId, java.lang.Boolean approved)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getLatestVersion(groupId, articleId,
			approved);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getStructureArticles(groupId,
			structureId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getStructureArticles(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getStructureArticles(groupId,
			structureId, start, end, obc);
	}

	public static int getStructureArticlesCount(long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getStructureArticlesCount(groupId,
			structureId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getTemplateArticles(groupId,
			templateId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> getTemplateArticles(
		long groupId, java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getTemplateArticles(groupId,
			templateId, start, end, obc);
	}

	public static int getTemplateArticlesCount(long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.getTemplateArticlesCount(groupId,
			templateId);
	}

	public static boolean hasArticle(long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.hasArticle(groupId, articleId);
	}

	public static boolean isLatestVersion(long groupId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.isLatestVersion(groupId, articleId,
			version);
	}

	public static boolean isLatestVersion(long groupId,
		java.lang.String articleId, double version, java.lang.Boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.isLatestVersion(groupId, articleId,
			version, active);
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.reIndex(ids);
	}

	public static com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.removeArticleLocale(groupId,
			articleId, version, languageId);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, keywords,
			start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.lang.String keywords, java.lang.String sortField,
		int start, int end) throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, keywords,
			sortField, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.lang.String keywords,
		java.lang.Double version, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.lang.Boolean approved, java.lang.Boolean expired,
		java.util.Date reviewDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, keywords,
			version, type, structureId, templateId, displayDateGT,
			displayDateLT, approved, expired, reviewDate, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.lang.Boolean approved,
		java.lang.Boolean expired, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, articleId,
			version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, approved, expired,
			reviewDate, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> search(
		long companyId, long groupId, java.lang.String articleId,
		java.lang.Double version, java.lang.String title,
		java.lang.String description, java.lang.String content,
		java.lang.String type, java.lang.String[] structureIds,
		java.lang.String[] templateIds, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.lang.Boolean approved,
		java.lang.Boolean expired, java.util.Date reviewDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.search(companyId, groupId, articleId,
			version, title, description, content, type, structureIds,
			templateIds, displayDateGT, displayDateLT, approved, expired,
			reviewDate, andOperator, start, end, obc);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords, java.lang.Double version,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.util.Date displayDateGT,
		java.util.Date displayDateLT, java.lang.Boolean approved,
		java.lang.Boolean expired, java.util.Date reviewDate)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.searchCount(companyId, groupId,
			keywords, version, type, structureId, templateId, displayDateGT,
			displayDateLT, approved, expired, reviewDate);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.lang.Boolean approved, java.lang.Boolean expired,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.searchCount(companyId, groupId,
			articleId, version, title, description, content, type, structureId,
			templateId, displayDateGT, displayDateLT, approved, expired,
			reviewDate, andOperator);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String articleId, java.lang.Double version,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String[] structureIds, java.lang.String[] templateIds,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.lang.Boolean approved, java.lang.Boolean expired,
		java.util.Date reviewDate, boolean andOperator)
		throws com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.searchCount(companyId, groupId,
			articleId, version, title, description, content, type,
			structureIds, templateIds, displayDateGT, displayDateLT, approved,
			expired, reviewDate, andOperator);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
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
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs,
		java.lang.String[] tagsEntries)
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
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, prefs, tagsEntries);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateContent(
		long groupId, java.lang.String articleId, double version,
		java.lang.String content)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		return journalArticleLocalService.updateContent(groupId, articleId,
			version, content);
	}

	public static void updateTagsAsset(long userId,
		com.liferay.portlet.journal.model.JournalArticle article,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleLocalService journalArticleLocalService = JournalArticleLocalServiceFactory.getService();

		journalArticleLocalService.updateTagsAsset(userId, article, tagsEntries);
	}
}