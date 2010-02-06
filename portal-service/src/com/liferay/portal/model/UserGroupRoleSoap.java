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

package com.liferay.portal.model;

import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="UserGroupRoleSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.UserGroupRoleServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.UserGroupRoleServiceSoap
 * @generated
 */
public class UserGroupRoleSoap implements Serializable {
	public static UserGroupRoleSoap toSoapModel(UserGroupRole model) {
		UserGroupRoleSoap soapModel = new UserGroupRoleSoap();

		soapModel.setUserId(model.getUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setRoleId(model.getRoleId());

		return soapModel;
	}

	public static UserGroupRoleSoap[] toSoapModels(UserGroupRole[] models) {
		UserGroupRoleSoap[] soapModels = new UserGroupRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserGroupRoleSoap[][] toSoapModels(UserGroupRole[][] models) {
		UserGroupRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserGroupRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserGroupRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserGroupRoleSoap[] toSoapModels(List<UserGroupRole> models) {
		List<UserGroupRoleSoap> soapModels = new ArrayList<UserGroupRoleSoap>(models.size());

		for (UserGroupRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserGroupRoleSoap[soapModels.size()]);
	}

	public UserGroupRoleSoap() {
	}

	public UserGroupRolePK getPrimaryKey() {
		return new UserGroupRolePK(_userId, _groupId, _roleId);
	}

	public void setPrimaryKey(UserGroupRolePK pk) {
		setUserId(pk.userId);
		setGroupId(pk.groupId);
		setRoleId(pk.roleId);
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	private long _userId;
	private long _groupId;
	private long _roleId;
}