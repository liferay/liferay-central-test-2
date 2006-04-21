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
import com.liferay.portal.service.persistence.UserIdMapperPK;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="UserIdMapperModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserIdMapperModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserIdMapper"), XSS_ALLOW);
	public static boolean XSS_ALLOW_USERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserIdMapper.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPE = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserIdMapper.type"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserIdMapper.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_EXTERNALUSERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserIdMapper.externalUserId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserIdMapperModel"));

	public UserIdMapperModel() {
	}

	public UserIdMapperPK getPrimaryKey() {
		return new UserIdMapperPK(_userId, _type);
	}

	public void setPrimaryKey(UserIdMapperPK pk) {
		setUserId(pk.userId);
		setType(pk.type);
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
			setModified(true);
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
			setModified(true);
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
			setModified(true);
		}
	}

	public String getExternalUserId() {
		return GetterUtil.getString(_externalUserId);
	}

	public void setExternalUserId(String externalUserId) {
		if (((externalUserId == null) && (_externalUserId != null)) ||
				((externalUserId != null) && (_externalUserId == null)) ||
				((externalUserId != null) && (_externalUserId != null) &&
				!externalUserId.equals(_externalUserId))) {
			if (!XSS_ALLOW_EXTERNALUSERID) {
				externalUserId = XSSUtil.strip(externalUserId);
			}

			_externalUserId = externalUserId;
			setModified(true);
		}
	}

	public Object clone() {
		UserIdMapper clone = new UserIdMapper();
		clone.setUserId(getUserId());
		clone.setType(getType());
		clone.setDescription(getDescription());
		clone.setExternalUserId(getExternalUserId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		UserIdMapper userIdMapper = (UserIdMapper)obj;
		UserIdMapperPK pk = userIdMapper.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserIdMapper userIdMapper = null;

		try {
			userIdMapper = (UserIdMapper)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		UserIdMapperPK pk = userIdMapper.getPrimaryKey();

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

	private String _userId;
	private String _type;
	private String _description;
	private String _externalUserId;
}