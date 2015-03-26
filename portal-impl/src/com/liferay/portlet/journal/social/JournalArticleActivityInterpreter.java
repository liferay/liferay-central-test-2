/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.journal.social;

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;

/**
 * @author Roberto Diaz
 * @author Zsolt Berentey
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portlet.journal.model.JournalArticle"
	}
)
public class JournalArticleActivityInterpreter
	extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getPath(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				activity.getClassPK());

		Layout layout = article.getLayout();

		if (layout != null) {
			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				layout.getLayoutSet(), serviceContext.getThemeDisplay());

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
					article.getUrlTitle());
		}

		return null;
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == JournalActivityKeys.ADD_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-article-add-web-content";
			}
			else {
				return "activity-journal-article-add-web-content-in";
			}
		}
		else if (activityType == JournalActivityKeys.UPDATE_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-article-update-web-content";
			}
			else {
				return "activity-journal-article-update-web-content-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-article-move-to-trash";
			}
			else {
				return "activity-journal-article-move-to-trash-in";
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-journal-article-restore-from-trash";
			}
			else {
				return "activity-journal-article-restore-from-trash-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		int activityType = activity.getType();

		if (activityType == JournalActivityKeys.ADD_ARTICLE) {
			JournalArticle article =
				JournalArticleLocalServiceUtil.getLatestArticle(
					activity.getClassPK());

			return JournalFolderPermission.contains(
				permissionChecker, article.getGroupId(), article.getFolderId(),
				ActionKeys.ADD_ARTICLE);
		}
		else if (activityType == JournalActivityKeys.UPDATE_ARTICLE) {
			return JournalArticlePermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.UPDATE);
		}

		return JournalArticlePermission.contains(
			permissionChecker, activity.getClassPK(), actionId);
	}

	private static final String[] _CLASS_NAMES =
		{JournalArticle.class.getName()};

}