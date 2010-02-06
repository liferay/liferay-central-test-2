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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.base.JournalArticleServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;

import java.io.File;

import java.util.Map;

/**
 * <a href="JournalArticleServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalArticleServiceImpl extends JournalArticleServiceBaseImpl {

	public JournalArticle addArticle(
			long groupId, String articleId, boolean autoArticleId, String title,
			String description, String content, String type, String structureId,
			String templateId, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, File smallFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.addArticle(
			getUserId(), groupId, articleId, autoArticleId, title, description,
			content, type, structureId, templateId, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage, smallImageURL,
			smallFile, images, articleURL, serviceContext);
	}

	public JournalArticle addArticle(
			long groupId, String articleId, boolean autoArticleId, String title,
			String description, String content, String type, String structureId,
			String templateId, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.addArticle(
			getUserId(), groupId, articleId, autoArticleId, title, description,
			content, type, structureId, templateId, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, false, null, null, null,
			articleURL, serviceContext);
	}

	public JournalArticle copyArticle(
			long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ARTICLE);

		return journalArticleLocalService.copyArticle(
			getUserId(), groupId, oldArticleId, newArticleId, autoArticleId,
			version);
	}

	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.DELETE);

		journalArticleLocalService.deleteArticle(
			groupId, articleId, version, articleURL, serviceContext);
	}

	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.VIEW);

		return journalArticleLocalService.getArticle(groupId, articleId);
	}

	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.VIEW);

		return journalArticleLocalService.getArticle(
			groupId, articleId, version);
	}

	public JournalArticle getArticleByUrlTitle(long groupId, String urlTitle)
		throws PortalException, SystemException {

		JournalArticle article =
			journalArticleLocalService.getArticleByUrlTitle(groupId, urlTitle);

		JournalArticlePermission.check(
			getPermissionChecker(), article, ActionKeys.VIEW);

		return article;
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, version, null, languageId, themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, ActionKeys.VIEW);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, null, languageId, themeDisplay);
	}

	public void removeArticleLocale(long companyId, String languageId)
		throws PortalException, SystemException {

		for (JournalArticle article :
				journalArticlePersistence.findByCompanyId(companyId)) {

			removeArticleLocale(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), languageId);
		}
	}

	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.removeArticleLocale(
			groupId, articleId, version, languageId);
	}

	public JournalArticle updateArticle(
			long groupId, String articleId, double version,
			boolean incrementVersion, String content)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticle(
			getUserId(), groupId, articleId, version, incrementVersion,
			content);
	}

	public JournalArticle updateArticle(
			long groupId, String articleId, double version,
			boolean incrementVersion, String title, String description,
			String content, String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateArticle(
			getUserId(), groupId, articleId, version, incrementVersion, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL,
			serviceContext);
	}

	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), groupId, articleId, version,
			ActionKeys.UPDATE);

		return journalArticleLocalService.updateContent(
			groupId, articleId, version, content);
	}

	public JournalArticle updateStatus(
			long groupId, String articleId, double version, int status,
			String articleURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (status == StatusConstants.APPROVED) {
			JournalPermission.check(
				getPermissionChecker(), groupId, ActionKeys.APPROVE_ARTICLE);
		}
		else if (status == StatusConstants.EXPIRED) {
			JournalPermission.check(
				getPermissionChecker(), groupId, ActionKeys.EXPIRE);
		}

		return journalArticleLocalService.updateStatus(
			getUserId(), groupId, articleId, version, status, articleURL,
			serviceContext);
	}

}