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
 * <a href="UserGroupSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserGroup}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroup
 * @generated
 */
public class UserGroupWrapper implements UserGroup {
	public UserGroupWrapper(UserGroup userGroup) {
		_userGroup = userGroup;
	}

	public long getPrimaryKey() {
		return _userGroup.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_userGroup.setPrimaryKey(pk);
	}

	public long getUserGroupId() {
		return _userGroup.getUserGroupId();
	}

	public void setUserGroupId(long userGroupId) {
		_userGroup.setUserGroupId(userGroupId);
	}

	public long getCompanyId() {
		return _userGroup.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_userGroup.setCompanyId(companyId);
	}

	public long getParentUserGroupId() {
		return _userGroup.getParentUserGroupId();
	}

	public void setParentUserGroupId(long parentUserGroupId) {
		_userGroup.setParentUserGroupId(parentUserGroupId);
	}

	public java.lang.String getName() {
		return _userGroup.getName();
	}

	public void setName(java.lang.String name) {
		_userGroup.setName(name);
	}

	public java.lang.String getDescription() {
		return _userGroup.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_userGroup.setDescription(description);
	}

	public com.liferay.portal.model.UserGroup toEscapedModel() {
		return _userGroup.toEscapedModel();
	}

	public boolean isNew() {
		return _userGroup.isNew();
	}

	public boolean setNew(boolean n) {
		return _userGroup.setNew(n);
	}

	public boolean isCachedModel() {
		return _userGroup.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userGroup.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userGroup.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userGroup.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userGroup.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userGroup.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userGroup.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userGroup.clone();
	}

	public int compareTo(com.liferay.portal.model.UserGroup userGroup) {
		return _userGroup.compareTo(userGroup);
	}

	public int hashCode() {
		return _userGroup.hashCode();
	}

	public java.lang.String toString() {
		return _userGroup.toString();
	}

	public java.lang.String toXmlString() {
		return _userGroup.toXmlString();
	}

	public com.liferay.portal.model.Group getGroup() {
		return _userGroup.getGroup();
	}

	public int getPrivateLayoutsPageCount() {
		return _userGroup.getPrivateLayoutsPageCount();
	}

	public boolean hasPrivateLayouts() {
		return _userGroup.hasPrivateLayouts();
	}

	public int getPublicLayoutsPageCount() {
		return _userGroup.getPublicLayoutsPageCount();
	}

	public boolean hasPublicLayouts() {
		return _userGroup.hasPublicLayouts();
	}

	public UserGroup getWrappedUserGroup() {
		return _userGroup;
	}

	private UserGroup _userGroup;
}