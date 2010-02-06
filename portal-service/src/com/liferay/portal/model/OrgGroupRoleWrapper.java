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
 * <a href="OrgGroupRoleSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link OrgGroupRole}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgGroupRole
 * @generated
 */
public class OrgGroupRoleWrapper implements OrgGroupRole {
	public OrgGroupRoleWrapper(OrgGroupRole orgGroupRole) {
		_orgGroupRole = orgGroupRole;
	}

	public com.liferay.portal.service.persistence.OrgGroupRolePK getPrimaryKey() {
		return _orgGroupRole.getPrimaryKey();
	}

	public void setPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK pk) {
		_orgGroupRole.setPrimaryKey(pk);
	}

	public long getOrganizationId() {
		return _orgGroupRole.getOrganizationId();
	}

	public void setOrganizationId(long organizationId) {
		_orgGroupRole.setOrganizationId(organizationId);
	}

	public long getGroupId() {
		return _orgGroupRole.getGroupId();
	}

	public void setGroupId(long groupId) {
		_orgGroupRole.setGroupId(groupId);
	}

	public long getRoleId() {
		return _orgGroupRole.getRoleId();
	}

	public void setRoleId(long roleId) {
		_orgGroupRole.setRoleId(roleId);
	}

	public com.liferay.portal.model.OrgGroupRole toEscapedModel() {
		return _orgGroupRole.toEscapedModel();
	}

	public boolean isNew() {
		return _orgGroupRole.isNew();
	}

	public boolean setNew(boolean n) {
		return _orgGroupRole.setNew(n);
	}

	public boolean isCachedModel() {
		return _orgGroupRole.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_orgGroupRole.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _orgGroupRole.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_orgGroupRole.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _orgGroupRole.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _orgGroupRole.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_orgGroupRole.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _orgGroupRole.clone();
	}

	public int compareTo(com.liferay.portal.model.OrgGroupRole orgGroupRole) {
		return _orgGroupRole.compareTo(orgGroupRole);
	}

	public int hashCode() {
		return _orgGroupRole.hashCode();
	}

	public java.lang.String toString() {
		return _orgGroupRole.toString();
	}

	public java.lang.String toXmlString() {
		return _orgGroupRole.toXmlString();
	}

	public boolean containsOrganization(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return _orgGroupRole.containsOrganization(organizations);
	}

	public boolean containsGroup(
		java.util.List<com.liferay.portal.model.Group> groups) {
		return _orgGroupRole.containsGroup(groups);
	}

	public OrgGroupRole getWrappedOrgGroupRole() {
		return _orgGroupRole;
	}

	private OrgGroupRole _orgGroupRole;
}