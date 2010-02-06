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
 * <a href="OrgGroupPermissionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link OrgGroupPermission}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgGroupPermission
 * @generated
 */
public class OrgGroupPermissionWrapper implements OrgGroupPermission {
	public OrgGroupPermissionWrapper(OrgGroupPermission orgGroupPermission) {
		_orgGroupPermission = orgGroupPermission;
	}

	public com.liferay.portal.service.persistence.OrgGroupPermissionPK getPrimaryKey() {
		return _orgGroupPermission.getPrimaryKey();
	}

	public void setPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK pk) {
		_orgGroupPermission.setPrimaryKey(pk);
	}

	public long getOrganizationId() {
		return _orgGroupPermission.getOrganizationId();
	}

	public void setOrganizationId(long organizationId) {
		_orgGroupPermission.setOrganizationId(organizationId);
	}

	public long getGroupId() {
		return _orgGroupPermission.getGroupId();
	}

	public void setGroupId(long groupId) {
		_orgGroupPermission.setGroupId(groupId);
	}

	public long getPermissionId() {
		return _orgGroupPermission.getPermissionId();
	}

	public void setPermissionId(long permissionId) {
		_orgGroupPermission.setPermissionId(permissionId);
	}

	public com.liferay.portal.model.OrgGroupPermission toEscapedModel() {
		return _orgGroupPermission.toEscapedModel();
	}

	public boolean isNew() {
		return _orgGroupPermission.isNew();
	}

	public boolean setNew(boolean n) {
		return _orgGroupPermission.setNew(n);
	}

	public boolean isCachedModel() {
		return _orgGroupPermission.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_orgGroupPermission.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _orgGroupPermission.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_orgGroupPermission.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _orgGroupPermission.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _orgGroupPermission.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_orgGroupPermission.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _orgGroupPermission.clone();
	}

	public int compareTo(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission) {
		return _orgGroupPermission.compareTo(orgGroupPermission);
	}

	public int hashCode() {
		return _orgGroupPermission.hashCode();
	}

	public java.lang.String toString() {
		return _orgGroupPermission.toString();
	}

	public java.lang.String toXmlString() {
		return _orgGroupPermission.toXmlString();
	}

	public boolean containsOrganization(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return _orgGroupPermission.containsOrganization(organizations);
	}

	public boolean containsGroup(
		java.util.List<com.liferay.portal.model.Group> groups) {
		return _orgGroupPermission.containsGroup(groups);
	}

	public OrgGroupPermission getWrappedOrgGroupPermission() {
		return _orgGroupPermission;
	}

	private OrgGroupPermission _orgGroupPermission;
}