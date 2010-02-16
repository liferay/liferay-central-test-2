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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalArticleServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalArticleService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleService
 * @generated
 */
public class JournalArticleServiceWrapper implements JournalArticleService {
	public JournalArticleServiceWrapper(
		JournalArticleService journalArticleService) {
		_journalArticleService = journalArticleService;
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
		long groupId, java.lang.String articleId, boolean autoArticleId,
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
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.addArticle(groupId, articleId,
			autoArticleId, title, description, content, type, structureId,
			templateId, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, smallImage, smallImageURL, smallFile, images,
			articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle addArticle(
		long groupId, java.lang.String articleId, boolean autoArticleId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
		int reviewDateDay, int reviewDateYear, int reviewDateHour,
		int reviewDateMinute, boolean neverReview, boolean indexable,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.addArticle(groupId, articleId,
			autoArticleId, title, description, content, type, structureId,
			templateId, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle copyArticle(
		long groupId, java.lang.String oldArticleId,
		java.lang.String newArticleId, boolean autoArticleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.copyArticle(groupId, oldArticleId,
			newArticleId, autoArticleId, version);
	}

	public void deleteArticle(long groupId, java.lang.String articleId,
		double version, java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleService.deleteArticle(groupId, articleId, version,
			articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.getArticle(groupId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticle(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.getArticle(groupId, articleId, version);
	}

	public com.liferay.portlet.journal.model.JournalArticle getArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.getArticleByUrlTitle(groupId, urlTitle);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, double version,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.getArticleContent(groupId, articleId,
			version, languageId, themeDisplay);
	}

	public java.lang.String getArticleContent(long groupId,
		java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.getArticleContent(groupId, articleId,
			languageId, themeDisplay);
	}

	public void removeArticleLocale(long companyId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleService.removeArticleLocale(companyId, languageId);
	}

	public com.liferay.portlet.journal.model.JournalArticle removeArticleLocale(
		long groupId, java.lang.String articleId, double version,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.removeArticleLocale(groupId, articleId,
			version, languageId);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long groupId, java.lang.String articleId, double version,
		boolean incrementVersion, java.lang.String content)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.updateArticle(groupId, articleId,
			version, incrementVersion, content);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateArticle(
		long groupId, java.lang.String articleId, double version,
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
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.updateArticle(groupId, articleId,
			version, incrementVersion, title, description, content, type,
			structureId, templateId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage,
			smallImageURL, smallFile, images, articleURL, serviceContext);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateContent(
		long groupId, java.lang.String articleId, double version,
		java.lang.String content)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.updateContent(groupId, articleId,
			version, content);
	}

	public com.liferay.portlet.journal.model.JournalArticle updateStatus(
		long groupId, java.lang.String articleId, double version, int status,
		java.lang.String articleURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleService.updateStatus(groupId, articleId, version,
			status, articleURL, serviceContext);
	}

	public JournalArticleService getWrappedJournalArticleService() {
		return _journalArticleService;
	}

	private JournalArticleService _journalArticleService;
}