/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.GroupNames;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="GroupImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class GroupImpl extends GroupModelImpl implements Group {

	public static final long DEFAULT_PARENT_GROUP_ID = -1;

	public static final long DEFAULT_LIVE_GROUP_ID = -1;

	public static final String GUEST = GroupNames.GUEST;

	public static final String[] SYSTEM_GROUPS = GroupNames.SYSTEM_GROUPS;

	public static final String TYPE_COMMUNITY_OPEN = "COMMUNITY_OPEN";

	public static final String TYPE_COMMUNITY_CLOSED = "COMMUNITY_CLOSED";

	public GroupImpl() {
	}

	public boolean isCommunity() {
		String className = getClassName();
		String classPK = getClassPK();

		if (Validator.isNull(className) && Validator.isNull(classPK)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isOrganization() {
		String className = getClassName();
		String classPK = getClassPK();

		if (Validator.isNotNull(className) && Validator.isNotNull(classPK) &&
			className.equals(Organization.class.getName())) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isUser() {
		String className = getClassName();
		String classPK = getClassPK();

		if (Validator.isNotNull(className) && Validator.isNotNull(classPK) &&
			className.equals(User.class.getName())) {

			return true;
		}
		else {
			return false;
		}
	}

	public Group getLiveGroup() {
		if (!isStagingGroup()) {
			return null;
		}

		try {
			if (_liveGroup == null) {
				_liveGroup = GroupLocalServiceUtil.getGroup(
					getLiveGroupId());
			}

			return _liveGroup;
		}
		catch (Exception e) {
			_log.error("Error getting live group for " + getLiveGroupId(), e);

			return null;
		}
	}

	public Group getStagingGroup() {
		if (isStagingGroup()) {
			return null;
		}

		try {
			if (_stagingGroup == null) {
				_stagingGroup =
					GroupLocalServiceUtil.getStagingGroup(getGroupId());
			}

			return _stagingGroup;
		}
		catch (Exception e) {
			_log.error("Error getting staging group for " + getGroupId(), e);

			return null;
		}
	}

	public boolean hasStagingGroup() {
		if (getStagingGroup() == null) {
			return false;
		}
		else {
		    return true;
		}
	}

	public boolean isStagingGroup() {
		if (getLiveGroupId() == DEFAULT_LIVE_GROUP_ID) {
			return false;
		}
		else {
			return true;
		}
	}

	public String getPathFriendlyURL(
		boolean privateLayout, ThemeDisplay themeDisplay) {

		if (privateLayout) {
			if (isUser()) {
				return themeDisplay.getPathFriendlyURLPrivateUser();
			}
			else {
				return themeDisplay.getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			return themeDisplay.getPathFriendlyURLPublic();
		}
	}

	public String getDefaultFriendlyURL(boolean privateLayout)
		throws PortalException, SystemException {

		if (privateLayout && isUser()) {
			String userId = getClassPK();

			User user = UserLocalServiceUtil.getUserById(userId);

			return StringPool.SLASH + user.getScreenName();
		}
		else {
			return StringPool.SLASH + String.valueOf(getGroupId());
		}
	}

	public int getPrivateLayoutsPageCount() {
		try {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				LayoutImpl.PRIVATE + getGroupId());

			return layoutSet.getPageCount();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int getPublicLayoutsPageCount() {
		try {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				LayoutImpl.PUBLIC + getGroupId());

			return layoutSet.getPageCount();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(GroupImpl.class);

	private Group _stagingGroup;
	private Group _liveGroup;

}