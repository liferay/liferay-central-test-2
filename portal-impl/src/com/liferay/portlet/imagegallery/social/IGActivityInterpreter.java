/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.social;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.permission.IGImagePermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * @author Ryan Park
 */
public class IGActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		IGImage image = IGImageLocalServiceUtil.getIGImage(
			activity.getClassPK());

		if (!IGImagePermission.contains(
				permissionChecker, image, ActionKeys.VIEW)) {

			return null;
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);

		int activityType = activity.getType();

		// Link

		String link =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/image_gallery/find_image?imageId=" + image.getImageId();

		// Title

		String titlePattern = null;

		if (activityType == IGActivityKeys.ADD_IMAGE) {
			titlePattern = "activity-image-gallery-add-image";
		}
		else if (activityType == IGActivityKeys.UPDATE_IMAGE) {
			titlePattern = "activity-image-gallery-update-image";
		}

		if (Validator.isNotNull(groupName)) {
			titlePattern += "-in";
		}

		String imageName = wrapLink(
			link, HtmlUtil.escape(cleanContent(image.getName())));

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, imageName
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		String folderLink =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/image_gallery/find_folder?folderId=" + image.getFolderId();

		String body = wrapLink(folderLink, "go-to-folder", themeDisplay);

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		IGImage.class.getName()
	};

}