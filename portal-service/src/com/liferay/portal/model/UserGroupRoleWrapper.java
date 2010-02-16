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
 * <a href="UserGroupRoleSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserGroupRole}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupRole
 * @generated
 */
public class UserGroupRoleWrapper implements UserGroupRole {
	public UserGroupRoleWrapper(UserGroupRole userGroupRole) {
		_userGroupRole = userGroupRole;
	}

	public com.liferay.portal.service.persistence.UserGroupRolePK getPrimaryKey() {
		return _userGroupRole.getPrimaryKey();
	}

	public void setPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupRolePK pk) {
		_userGroupRole.setPrimaryKey(pk);
	}

	public long getUserId() {
		return _userGroupRole.getUserId();
	}

	public void setUserId(long userId) {
		_userGroupRole.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_userGroupRole.setUserUuid(userUuid);
	}

	public long getGroupId() {
		return _userGroupRole.getGroupId();
	}

	public void setGroupId(long groupId) {
		_userGroupRole.setGroupId(groupId);
	}

	public long getRoleId() {
		return _userGroupRole.getRoleId();
	}

	public void setRoleId(long roleId) {
		_userGroupRole.setRoleId(roleId);
	}

	public com.liferay.portal.model.UserGroupRole toEscapedModel() {
		return _userGroupRole.toEscapedModel();
	}

	public boolean isNew() {
		return _userGroupRole.isNew();
	}

	public boolean setNew(boolean n) {
		return _userGroupRole.setNew(n);
	}

	public boolean isCachedModel() {
		return _userGroupRole.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userGroupRole.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userGroupRole.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userGroupRole.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroupRole.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroupRole.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userGroupRole.clone();
	}

	public int compareTo(com.liferay.portal.model.UserGroupRole userGroupRole) {
		return _userGroupRole.compareTo(userGroupRole);
	}

	public int hashCode() {
		return _userGroupRole.hashCode();
	}

	public java.lang.String toString() {
		return _userGroupRole.toString();
	}

	public java.lang.String toXmlString() {
		return _userGroupRole.toXmlString();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getGroup();
	}

	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getRole();
	}

	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userGroupRole.getUser();
	}

	public UserGroupRole getWrappedUserGroupRole() {
		return _userGroupRole;
	}

	private UserGroupRole _userGroupRole;
}