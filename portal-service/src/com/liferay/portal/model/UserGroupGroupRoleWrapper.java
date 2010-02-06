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


/**
 * <a href="UserGroupGroupRoleSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserGroupGroupRole}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRole
 * @generated
 */
public class UserGroupGroupRoleWrapper implements UserGroupGroupRole {
	public UserGroupGroupRoleWrapper(UserGroupGroupRole userGroupGroupRole) {
		_userGroupGroupRole = userGroupGroupRole;
	}

	public com.liferay.portal.service.persistence.UserGroupGroupRolePK getPrimaryKey() {
		return _userGroupGroupRole.getPrimaryKey();
	}

	public void setPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK pk) {
		_userGroupGroupRole.setPrimaryKey(pk);
	}

	public long getUserGroupId() {
		return _userGroupGroupRole.getUserGroupId();
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupGroupRole.setUserGroupId(userGroupId);
	}

	public long getGroupId() {
		return _userGroupGroupRole.getGroupId();
	}

	public void setGroupId(long groupId) {
		_userGroupGroupRole.setGroupId(groupId);
	}

	public long getRoleId() {
		return _userGroupGroupRole.getRoleId();
	}

	public void setRoleId(long roleId) {
		_userGroupGroupRole.setRoleId(roleId);
	}

	public com.liferay.portal.model.UserGroupGroupRole toEscapedModel() {
		return _userGroupGroupRole.toEscapedModel();
	}

	public boolean isNew() {
		return _userGroupGroupRole.isNew();
	}

	public boolean setNew(boolean n) {
		return _userGroupGroupRole.setNew(n);
	}

	public boolean isCachedModel() {
		return _userGroupGroupRole.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userGroupGroupRole.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userGroupGroupRole.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userGroupGroupRole.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroupGroupRole.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroupGroupRole.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroupGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userGroupGroupRole.clone();
	}

	public int compareTo(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole) {
		return _userGroupGroupRole.compareTo(userGroupGroupRole);
	}

	public int hashCode() {
		return _userGroupGroupRole.hashCode();
	}

	public java.lang.String toString() {
		return _userGroupGroupRole.toString();
	}

	public java.lang.String toXmlString() {
		return _userGroupGroupRole.toXmlString();
	}

	public UserGroupGroupRole getWrappedUserGroupGroupRole() {
		return _userGroupGroupRole;
	}

	private UserGroupGroupRole _userGroupGroupRole;
}