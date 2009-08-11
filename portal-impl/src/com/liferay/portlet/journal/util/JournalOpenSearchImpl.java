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

package com.liferay.portlet.journal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.search.HitsOpenSearchImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * <a href="JournalOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class JournalOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/journal/open_search";

	public static final String TITLE = "Liferay Journal Search: ";

	public Hits getHits(
			long companyId, long groupId, long userId, String keywords,
			int start, int end)
		throws Exception {

		return JournalArticleLocalServiceUtil.search(
			companyId, groupId, userId, keywords, start, end);
	}

	public String getSearchPath() {
		return SEARCH_PATH;
	}

	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

	protected String getURL(
			ThemeDisplay themeDisplay, long groupId, Document result,
			PortletURL portletURL)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String articleId = result.get(Field.ENTRY_CLASS_PK);
		String version = result.get("version");

		Layout layout = null;

		// Search current groupId

		layout = getLayoutWithJournalContent(
			GroupLocalServiceUtil.getGroup(groupId), articleId,
			permissionChecker, false);

		if (Validator.isNotNull(layout)) {
			return PortalUtil.getLayoutURL(layout, themeDisplay);
		}

		// Search user's groupIds

		List<Group> usersGroups = themeDisplay.getUser().getMyPlaces();

		for (Group group : usersGroups) {
			layout = getLayoutWithJournalContent(
				group, articleId, permissionChecker);

			if (Validator.isNotNull(layout)) {
				return PortalUtil.getLayoutURL(layout, themeDisplay);
			}

		}

		// Search public groups

		List<Group> publicGroups = GroupLocalServiceUtil.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : publicGroups) {
			layout = getLayoutWithJournalContent(
				group, articleId, permissionChecker, false);

			if (Validator.isNotNull(layout)) {
				return PortalUtil.getLayoutURL(layout, themeDisplay);
			}
		}

		// Return direct link to article

		StringBuilder sb = new StringBuilder();

		sb.append(themeDisplay.getPathMain());
		sb.append("/journal/view_article_content?groupId=");
		sb.append(groupId);
		sb.append("&articleId=");
		sb.append(articleId);
		sb.append("&version=");
		sb.append(version);

		return sb.toString();
	}

	protected Layout getLayoutWithJournalContent(
			Group group, String articleId, PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		// Search public layout

		Layout layout = getLayoutWithJournalContent(
			group, articleId, permissionChecker, false);

		if (Validator.isNotNull(layout)) {
			return layout;
		}

		// Search private layout

		layout = getLayoutWithJournalContent(
			group, articleId, permissionChecker, true);

		if (Validator.isNotNull(layout)) {
			return layout;
		}

		return null;
	}

	protected Layout getLayoutWithJournalContent(
			Group group, String articleId, PermissionChecker permissionChecker,
			boolean privateLayout)
		throws PortalException, SystemException {

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				group.getGroupId(), privateLayout, articleId);

		for (Long hitLayoutId : hitLayoutIds) {
			if (LayoutPermissionUtil.contains(
					permissionChecker, group.getGroupId(), privateLayout,
						hitLayoutId, ActionKeys.VIEW)) {

				Layout hitLayout = LayoutLocalServiceUtil.getLayout(
					group.getGroupId(), privateLayout, hitLayoutId.longValue());

				return hitLayout;
			}
		}

		return null;
	}

}