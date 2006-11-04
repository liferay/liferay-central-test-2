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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.spring.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.spring.JournalArticleService;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="JournalArticleServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleServiceImpl
	extends PrincipalBean implements JournalArticleService {

	public JournalArticle addArticle(
			String articleId, boolean autoArticleId, String plid, String title,
			String description, String content, String type, String structureId,
			String templateId, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, Map images,
			String articleURL, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.JOURNAL,
			ActionKeys.ADD_ARTICLE);

		return JournalArticleLocalServiceUtil.addArticle(
			getUserId(), articleId, autoArticleId, plid, title, description,
			content, type, structureId, templateId, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, images, articleURL, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public JournalArticle approveArticle(
			String articleId, double version, String plid, String articleURL,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		User user = getUser();

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.JOURNAL,
			ActionKeys.APPROVE_ARTICLE);

		return JournalArticleLocalServiceUtil.approveArticle(
			user.getUserId(), articleId, version, articleURL, prefs);
	}

	public JournalArticle getArticle(
			String companyId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), companyId, articleId, ActionKeys.VIEW);

		return JournalArticleLocalServiceUtil.getArticle(
			companyId, articleId, version);
	}

	public String getArticleContent(
			String companyId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), companyId, articleId, ActionKeys.VIEW);

		return JournalArticleLocalServiceUtil.getArticleContent(
			companyId, articleId, languageId, themeDisplay);
	}

	public String getArticleContent(
			String companyId, String articleId, double version,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), companyId, articleId, ActionKeys.VIEW);

		return JournalArticleLocalServiceUtil.getArticleContent(
			companyId, articleId, version, languageId, themeDisplay);
	}

	public void deleteArticle(
			String companyId, String articleId, double version,
			String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), companyId, articleId, ActionKeys.DELETE);

		JournalArticleLocalServiceUtil.deleteArticle(
			companyId, articleId, version, articleURL, prefs);
	}

	public void expireArticle(
			String companyId, String articleId, double version,
			String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), companyId, articleId, ActionKeys.EXPIRE);

		JournalArticleLocalServiceUtil.expireArticle(
			companyId, articleId, version, articleURL, prefs);
	}

	public void removeArticleLocale(String companyId, String languageId)
		throws PortalException, SystemException {

		Iterator itr = JournalArticleUtil.findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			JournalArticle article = (JournalArticle)itr.next();

			removeArticleLocale(
				companyId, article.getArticleId(), article.getVersion(),
				languageId);
		}
	}

	public JournalArticle removeArticleLocale(
			String companyId, String articleId, double version,
			String languageId)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), companyId, articleId, ActionKeys.UPDATE);

		return JournalArticleLocalServiceUtil.removeArticleLocale(
			companyId, articleId, version, languageId);
	}

	public JournalArticle updateArticle(
			String companyId, String articleId, double version,
			boolean incrementVersion, String title, String description,
			String content, String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, Map images, String articleURL,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticlePermission.check(
			getPermissionChecker(), companyId, articleId, ActionKeys.UPDATE);

		return JournalArticleLocalServiceUtil.updateArticle(
			getUserId(), articleId, version, incrementVersion, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, images, articleURL,
			prefs);
	}

}