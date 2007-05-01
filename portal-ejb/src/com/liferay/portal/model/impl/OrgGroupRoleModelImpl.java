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

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.persistence.OrgGroupRolePK;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="OrgGroupRoleModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>OrgGroupRole</code> table in
 * the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.OrgGroupRole
 * @see com.liferay.portal.service.model.OrgGroupRoleModel
 * @see com.liferay.portal.service.model.impl.OrgGroupRoleImpl
 *
 */
public class OrgGroupRoleModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "OrgGroupRole";
	public static Object[][] TABLE_COLUMNS = {
			{ "organizationId", new Integer(Types.VARCHAR) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "roleId", new Integer(Types.BIGINT) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgGroupRole"), XSS_ALLOW);
	public static boolean XSS_ALLOW_ORGANIZATIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.OrgGroupRole.organizationId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.OrgGroupRoleModel"));

	public OrgGroupRoleModelImpl() {
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
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		if (roleId != _roleId) {
			_roleId = roleId;
		}
	}

	public Object clone() {
		OrgGroupRoleImpl clone = new OrgGroupRoleImpl();
		clone.setOrganizationId(getOrganizationId());
		clone.setGroupId(getGroupId());
		clone.setRoleId(getRoleId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		OrgGroupRoleImpl orgGroupRole = (OrgGroupRoleImpl)obj;
		OrgGroupRolePK pk = orgGroupRole.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		OrgGroupRoleImpl orgGroupRole = null;

		try {
			orgGroupRole = (OrgGroupRoleImpl)obj;
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
	private long _groupId;
	private long _roleId;
}