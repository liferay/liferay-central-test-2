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
 * <a href="PermissionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Permission}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Permission
 * @generated
 */
public class PermissionWrapper implements Permission {
	public PermissionWrapper(Permission permission) {
		_permission = permission;
	}

	public long getPrimaryKey() {
		return _permission.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_permission.setPrimaryKey(pk);
	}

	public long getPermissionId() {
		return _permission.getPermissionId();
	}

	public void setPermissionId(long permissionId) {
		_permission.setPermissionId(permissionId);
	}

	public long getCompanyId() {
		return _permission.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_permission.setCompanyId(companyId);
	}

	public java.lang.String getActionId() {
		return _permission.getActionId();
	}

	public void setActionId(java.lang.String actionId) {
		_permission.setActionId(actionId);
	}

	public long getResourceId() {
		return _permission.getResourceId();
	}

	public void setResourceId(long resourceId) {
		_permission.setResourceId(resourceId);
	}

	public com.liferay.portal.model.Permission toEscapedModel() {
		return _permission.toEscapedModel();
	}

	public boolean isNew() {
		return _permission.isNew();
	}

	public boolean setNew(boolean n) {
		return _permission.setNew(n);
	}

	public boolean isCachedModel() {
		return _permission.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_permission.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _permission.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_permission.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _permission.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _permission.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_permission.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _permission.clone();
	}

	public int compareTo(com.liferay.portal.model.Permission permission) {
		return _permission.compareTo(permission);
	}

	public int hashCode() {
		return _permission.hashCode();
	}

	public java.lang.String toString() {
		return _permission.toString();
	}

	public java.lang.String toXmlString() {
		return _permission.toXmlString();
	}

	public java.lang.String getName() {
		return _permission.getName();
	}

	public java.lang.String getPrimKey() {
		return _permission.getPrimKey();
	}

	public int getScope() {
		return _permission.getScope();
	}

	public void setName(java.lang.String name) {
		_permission.setName(name);
	}

	public void setPrimKey(java.lang.String primKey) {
		_permission.setPrimKey(primKey);
	}

	public void setScope(int scope) {
		_permission.setScope(scope);
	}

	public Permission getWrappedPermission() {
		return _permission;
	}

	private Permission _permission;
}