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
import com.liferay.portal.service.persistence.GroupRelPK;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="GroupRelModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupRelModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.GroupRel"), XSS_ALLOW);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.GroupRel.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.GroupRel.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.GroupRel.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.GroupRelModel"));

	public GroupRelModel() {
	}

	public GroupRelPK getPrimaryKey() {
		return new GroupRelPK(_groupId, _className, _classPK);
	}

	public void setPrimaryKey(GroupRelPK pk) {
		setGroupId(pk.groupId);
		setClassName(pk.className);
		setClassPK(pk.classPK);
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

	public String getClassName() {
		return GetterUtil.getString(_className);
	}

	public void setClassName(String className) {
		if (((className == null) && (_className != null)) ||
				((className != null) && (_className == null)) ||
				((className != null) && (_className != null) &&
				!className.equals(_className))) {
			if (!XSS_ALLOW_CLASSNAME) {
				className = XSSUtil.strip(className);
			}

			_className = className;
			setModified(true);
		}
	}

	public String getClassPK() {
		return GetterUtil.getString(_classPK);
	}

	public void setClassPK(String classPK) {
		if (((classPK == null) && (_classPK != null)) ||
				((classPK != null) && (_classPK == null)) ||
				((classPK != null) && (_classPK != null) &&
				!classPK.equals(_classPK))) {
			if (!XSS_ALLOW_CLASSPK) {
				classPK = XSSUtil.strip(classPK);
			}

			_classPK = classPK;
			setModified(true);
		}
	}

	public Object clone() {
		GroupRel clone = new GroupRel();
		clone.setGroupId(getGroupId());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		GroupRel groupRel = (GroupRel)obj;
		GroupRelPK pk = groupRel.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		GroupRel groupRel = null;

		try {
			groupRel = (GroupRel)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		GroupRelPK pk = groupRel.getPrimaryKey();

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

	private String _groupId;
	private String _className;
	private String _classPK;
}