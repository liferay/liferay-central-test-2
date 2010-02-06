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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="UserGroupGroupRolePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UserGroupGroupRolePK implements Comparable<UserGroupGroupRolePK>,
	Serializable {
	public long userGroupId;
	public long groupId;
	public long roleId;

	public UserGroupGroupRolePK() {
	}

	public UserGroupGroupRolePK(long userGroupId, long groupId, long roleId) {
		this.userGroupId = userGroupId;
		this.groupId = groupId;
		this.roleId = roleId;
	}

	public long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int compareTo(UserGroupGroupRolePK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (userGroupId < pk.userGroupId) {
			value = -1;
		}
		else if (userGroupId > pk.userGroupId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (groupId < pk.groupId) {
			value = -1;
		}
		else if (groupId > pk.groupId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (roleId < pk.roleId) {
			value = -1;
		}
		else if (roleId > pk.roleId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserGroupGroupRolePK pk = null;

		try {
			pk = (UserGroupGroupRolePK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((userGroupId == pk.userGroupId) && (groupId == pk.groupId) &&
				(roleId == pk.roleId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (String.valueOf(userGroupId) + String.valueOf(groupId) +
		String.valueOf(roleId)).hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("userGroupId");
		sb.append(StringPool.EQUAL);
		sb.append(userGroupId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("groupId");
		sb.append(StringPool.EQUAL);
		sb.append(groupId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("roleId");
		sb.append(StringPool.EQUAL);
		sb.append(roleId);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}