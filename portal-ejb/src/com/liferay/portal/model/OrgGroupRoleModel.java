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

package com.liferay.portal.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.OrgGroupRolePK;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="OrgGroupRoleModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgGroupRoleModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgGroupRole"), XSS_ALLOW);
	public static boolean XSS_ALLOW_ORGANIZATIONID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgGroupRole.organizationId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgGroupRole.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ROLEID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgGroupRole.roleId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.OrgGroupRoleModel"));

	public OrgGroupRoleModel() {
	}

	public OrgGroupRolePK getPrimaryKey() {
		return new OrgGroupRolePK(_organizationId, _groupId, _roleId);
	}

	public void setPrimaryKey(OrgGroupRolePK pk) {
		setOrganizationId(pk.organizationId);
		setGroupId(pk.groupId);
		setRoleId(pk.roleId);
	}

	public String getOrganizationId() {
		return GetterUtil.getString(_organizationId);
	}

	public void setOrganizationId(String organizationId) {
		if (((organizationId == null) && (_organizationId != null)) ||
				((organizationId != null) && (_organizationId == null)) ||
				((organizationId != null) && (_organizationId != null) &&
				!organizationId.equals(_organizationId))) {
			if (!XSS_ALLOW_ORGANIZATIONID) {
				organizationId = XSSUtil.strip(organizationId);
			}

			_organizationId = organizationId;
			setModified(true);
		}
	}

	public String getGroupId() {
		return GetterUtil.getString(_groupId);
	}

	public void setGroupId(String groupId) {
		if (((groupId == null) && (_groupId != null)) ||
				((groupId != null) && (_groupId == null)) ||
				((groupId != null) && (_groupId != null) &&
				!groupId.equals(_groupId))) {
			if (!XSS_ALLOW_GROUPID) {
				groupId = XSSUtil.strip(groupId);
			}

			_groupId = groupId;
			setModified(true);
		}
	}

	public String getRoleId() {
		return GetterUtil.getString(_roleId);
	}

	public void setRoleId(String roleId) {
		if (((roleId == null) && (_roleId != null)) ||
				((roleId != null) && (_roleId == null)) ||
				((roleId != null) && (_roleId != null) &&
				!roleId.equals(_roleId))) {
			if (!XSS_ALLOW_ROLEID) {
				roleId = XSSUtil.strip(roleId);
			}

			_roleId = roleId;
			setModified(true);
		}
	}

	public Object clone() {
		OrgGroupRole clone = new OrgGroupRole();
		clone.setOrganizationId(getOrganizationId());
		clone.setGroupId(getGroupId());
		clone.setRoleId(getRoleId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		OrgGroupRole orgGroupRole = (OrgGroupRole)obj;
		OrgGroupRolePK pk = orgGroupRole.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		OrgGroupRole orgGroupRole = null;

		try {
			orgGroupRole = (OrgGroupRole)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		OrgGroupRolePK pk = orgGroupRole.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _organizationId;
	private String _groupId;
	private String _roleId;
}