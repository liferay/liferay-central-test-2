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

package com.liferay.portlet.journal.service.spring;

/**
 * <a href="JournalArticleServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleServiceUtil {
	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		java.lang.String articleId, boolean autoArticleId,
		java.lang.String plid, java.lang.String title,
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
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.addArticle(articleId, autoArticleId, plid,
			title, description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, images, articleURL,
			prefs, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle addArticle(
		java.lang.String articleId, boolean autoArticleId,
		java.lang.String plid, java.lang.String title,
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
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.addArticle(articleId, autoArticleId, plid,
			title, description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, images, articleURL,
			prefs, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalArticle approveArticle(
		java.lang.String articleId, double version, java.lang.String plid,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.approveArticle(articleId, version, plid,
			articleURL, prefs);
	}

	public static com.liferay.portlet.journal.model.JournalArticle getArticle(
		java.lang.String companyId, java.lang.String articleId, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.getArticle(companyId, articleId, version);
	}

	public static java.lang.String getArticleContent(
		java.lang.String companyId, java.lang.String articleId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.getArticleContent(companyId, articleId,
			languageId, themeDisplay);
	}

	public static java.lang.String getArticleContent(
		java.lang.String companyId, java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.getArticleContent(companyId, articleId,
			version, languageId, themeDisplay);
	}

	public static void deleteArticle(java.lang.String companyId,
		java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();
		journalArticleService.deleteArticle(companyId, articleId, version,
			articleURL, prefs);
	}

	public static void expireArticle(java.lang.String companyId,
		java.lang.String articleId, double version,
		java.lang.String articleURL, javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();
		journalArticleService.expireArticle(companyId, articleId, version,
			articleURL, prefs);
	}

	public static void removeArticleLocale(java.lang.String companyId,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();
		journalArticleService.removeArticleLocale(companyId, languageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		java.lang.String companyId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.removeArticleLocale(companyId, articleId,
			version, languageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateArticle(
		java.lang.String companyId, java.lang.String articleId, double version,
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
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalArticleService journalArticleService = JournalArticleServiceFactory.getService();

		return journalArticleService.updateArticle(companyId, articleId,
			version, incrementVersion, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs);
	}
}