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

package com.liferay.portlet.documentlibrary.social;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * <a href="DLActivityInterpreter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ryan Park
 */
public class DLActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			activity.getClassPK());

		if (!DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.VIEW)) {

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
				"/document_library/get_file?folderId=" +
					fileEntry.getFolderId() + "&name=" + fileEntry.getName();

		// Title

		String titlePattern = null;

		if (activityType == DLActivityKeys.ADD_FILE_ENTRY) {
			titlePattern = "activity-document-library-add-file";
		}
		else if (activityType == DLActivityKeys.UPDATE_FILE_ENTRY) {
			titlePattern = "activity-document-library-update-file";
		}

		if (Validator.isNotNull(groupName)) {
			titlePattern += "-in";
		}

		String fileTitle = wrapLink(
			link, HtmlUtil.escape(cleanContent(fileEntry.getTitle())));

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, fileTitle
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		StringBundler sb = new StringBundler(3);

		String fileEntryLink =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/document_library/find_file_entry?fileEntryId=" +
					fileEntry.getFileEntryId();

		sb.append(wrapLink(fileEntryLink, "view-document", themeDisplay));
		sb.append(StringPool.SPACE);

		String folderLink =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/document_library/find_folder?folderId=" +
					fileEntry.getFolderId();

		sb.append(wrapLink(folderLink, "go-to-folder", themeDisplay));

		String body = sb.toString();

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		DLFileEntry.class.getName()
	};

}