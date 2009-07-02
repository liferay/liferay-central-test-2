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

package com.liferay.portlet.communities.search;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserGroupGroupRoleLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * <a href="UserGroupGroupRoleChecker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brett Swaim
 *
 */
public class UserGroupGroupRoleChecker extends RowChecker {

	public UserGroupGroupRoleChecker(
		RenderResponse renderResponse, UserGroup userGroup, Group group) {

		super(renderResponse);

		_userGroup = userGroup;
		_group = group;
	}

	public UserGroupGroupRoleChecker(
		RenderResponse renderResponse, Role role, Group group) {

		super(renderResponse);

		_role = role;
		_group = group;
	}

	public boolean isChecked(Object obj) {
		try {
			if (Validator.isNull(_userGroup)) {
				UserGroup userGroup = (UserGroup) obj;

				return UserGroupGroupRoleLocalServiceUtil.hasUserGroupGroupRole(
					userGroup.getUserGroupId(), _group.getGroupId(),
					_role.getRoleId());
			}
			else {
				Role role = (Role) obj;

				return UserGroupGroupRoleLocalServiceUtil.hasUserGroupGroupRole(
					_userGroup.getUserGroupId(), _group.getGroupId(),
					role.getRoleId());
			}
		}
		catch (Exception e) {
			_log.error(e);

			return false;
		}
	}

	private Group _group;
	private Role _role;
	private UserGroup _userGroup;

	private static Log _log =
		LogFactoryUtil.getLog(UserGroupGroupRoleChecker.class);

}