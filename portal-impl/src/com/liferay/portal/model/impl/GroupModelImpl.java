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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.XSSUtil;

import java.io.Serializable;

import java.sql.Types;

/**
 * <a href="GroupModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Group_</code> table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.Group
 * @see com.liferay.portal.service.model.GroupModel
 * @see com.liferay.portal.service.model.impl.GroupImpl
 *
 */
public class GroupModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Group_";
	public static Object[][] TABLE_COLUMNS = {
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "creatorUserId", new Integer(Types.BIGINT) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "classPK", new Integer(Types.BIGINT) },
			{ "parentGroupId", new Integer(Types.BIGINT) },
			{ "liveGroupId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.VARCHAR) },
			{ "friendlyURL", new Integer(Types.VARCHAR) },
			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static String TABLE_SQL_CREATE = "create table Group_ (groupId LONG not null primary key,companyId LONG,creatorUserId LONG,classNameId LONG,classPK LONG,parentGroupId LONG,liveGroupId LONG,name VARCHAR(75) null,description STRING null,type_ VARCHAR(75) null,friendlyURL VARCHAR(100) null,active_ BOOLEAN)";
	public static String TABLE_SQL_DROP = "drop table Group_";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Group"), XSS_ALLOW);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Group.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Group.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Group.type"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_FRIENDLYURL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Group.friendlyURL"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.GroupModel"));

	public GroupModelImpl() {
	}

	public long getPrimaryKey() {
		return _groupId;
	}

	public void setPrimaryKey(long pk) {
		setGroupId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_groupId);
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public void setCreatorUserId(long creatorUserId) {
		if (creatorUserId != _creatorUserId) {
			_creatorUserId = creatorUserId;
		}
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (classNameId != _classNameId) {
			_classNameId = classNameId;
		}
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (classPK != _classPK) {
			_classPK = classPK;
		}
	}

	public long getParentGroupId() {
		return _parentGroupId;
	}

	public void setParentGroupId(long parentGroupId) {
		if (parentGroupId != _parentGroupId) {
			_parentGroupId = parentGroupId;
		}
	}

	public long getLiveGroupId() {
		return _liveGroupId;
	}

	public void setLiveGroupId(long liveGroupId) {
		if (liveGroupId != _liveGroupId) {
			_liveGroupId = liveGroupId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
		}
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			if (!XSS_ALLOW_DESCRIPTION) {
				description = XSSUtil.strip(description);
			}

			_description = description;
		}
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			if (!XSS_ALLOW_TYPE) {
				type = XSSUtil.strip(type);
			}

			_type = type;
		}
	}

	public String getFriendlyURL() {
		return GetterUtil.getString(_friendlyURL);
	}

	public void setFriendlyURL(String friendlyURL) {
		if (((friendlyURL == null) && (_friendlyURL != null)) ||
				((friendlyURL != null) && (_friendlyURL == null)) ||
				((friendlyURL != null) && (_friendlyURL != null) &&
				!friendlyURL.equals(_friendlyURL))) {
			if (!XSS_ALLOW_FRIENDLYURL) {
				friendlyURL = XSSUtil.strip(friendlyURL);
			}

			_friendlyURL = friendlyURL;
		}
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		if (active != _active) {
			_active = active;
		}
	}

	public Object clone() {
		GroupImpl clone = new GroupImpl();
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setCreatorUserId(getCreatorUserId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setParentGroupId(getParentGroupId());
		clone.setLiveGroupId(getLiveGroupId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setType(getType());
		clone.setFriendlyURL(getFriendlyURL());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		GroupImpl group = (GroupImpl)obj;
		int value = 0;
		value = getName().toLowerCase().compareTo(group.getName().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		GroupImpl group = null;

		try {
			group = (GroupImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = group.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _groupId;
	private long _companyId;
	private long _creatorUserId;
	private long _classNameId;
	private long _classPK;
	private long _parentGroupId;
	private long _liveGroupId;
	private String _name;
	private String _description;
	private String _type;
	private String _friendlyURL;
	private boolean _active;
}