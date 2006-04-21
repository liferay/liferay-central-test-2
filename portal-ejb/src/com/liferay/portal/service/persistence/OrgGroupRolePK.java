/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.util.StringPool;

import java.io.Serializable;

/**
 * <a href="OrgGroupRolePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgGroupRolePK implements Comparable, Serializable {
	public String organizationId;
	public String groupId;
	public String roleId;

	public OrgGroupRolePK() {
	}

	public OrgGroupRolePK(String organizationId, String groupId, String roleId) {
		this.organizationId = organizationId;
		this.groupId = groupId;
		this.roleId = roleId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		OrgGroupRolePK pk = (OrgGroupRolePK)obj;
		int value = 0;
		value = organizationId.compareTo(pk.organizationId);

		if (value != 0) {
			return value;
		}

		value = groupId.compareTo(pk.groupId);

		if (value != 0) {
			return value;
		}

		value = roleId.compareTo(pk.roleId);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		OrgGroupRolePK pk = null;

		try {
			pk = (OrgGroupRolePK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((organizationId.equals(pk.organizationId)) &&
				(groupId.equals(pk.groupId)) && (roleId.equals(pk.roleId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (organizationId + groupId + roleId).hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append("organizationId");
		sb.append(StringPool.EQUAL);
		sb.append(organizationId);
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