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
 * <a href="RoleSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Role}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Role
 * @generated
 */
public class RoleWrapper implements Role {
	public RoleWrapper(Role role) {
		_role = role;
	}

	public long getPrimaryKey() {
		return _role.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_role.setPrimaryKey(pk);
	}

	public long getRoleId() {
		return _role.getRoleId();
	}

	public void setRoleId(long roleId) {
		_role.setRoleId(roleId);
	}

	public long getCompanyId() {
		return _role.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_role.setCompanyId(companyId);
	}

	public java.lang.String getClassName() {
		return _role.getClassName();
	}

	public long getClassNameId() {
		return _role.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_role.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _role.getClassPK();
	}

	public void setClassPK(long classPK) {
		_role.setClassPK(classPK);
	}

	public java.lang.String getName() {
		return _role.getName();
	}

	public void setName(java.lang.String name) {
		_role.setName(name);
	}

	public java.lang.String getTitle() {
		return _role.getTitle();
	}

	public java.lang.String getTitle(java.util.Locale locale) {
		return _role.getTitle(locale);
	}

	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _role.getTitle(locale, useDefault);
	}

	public java.lang.String getTitle(java.lang.String languageId) {
		return _role.getTitle(languageId);
	}

	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _role.getTitle(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, String> getTitleMap() {
		return _role.getTitleMap();
	}

	public void setTitle(java.lang.String title) {
		_role.setTitle(title);
	}

	public void setTitle(java.util.Locale locale, java.lang.String title) {
		_role.setTitle(locale, title);
	}

	public void setTitleMap(java.util.Map<java.util.Locale, String> titleMap) {
		_role.setTitleMap(titleMap);
	}

	public java.lang.String getDescription() {
		return _role.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_role.setDescription(description);
	}

	public int getType() {
		return _role.getType();
	}

	public void setType(int type) {
		_role.setType(type);
	}

	public java.lang.String getSubtype() {
		return _role.getSubtype();
	}

	public void setSubtype(java.lang.String subtype) {
		_role.setSubtype(subtype);
	}

	public com.liferay.portal.model.Role toEscapedModel() {
		return _role.toEscapedModel();
	}

	public boolean isNew() {
		return _role.isNew();
	}

	public boolean setNew(boolean n) {
		return _role.setNew(n);
	}

	public boolean isCachedModel() {
		return _role.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_role.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _role.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_role.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _role.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _role.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_role.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _role.clone();
	}

	public int compareTo(com.liferay.portal.model.Role role) {
		return _role.compareTo(role);
	}

	public int hashCode() {
		return _role.hashCode();
	}

	public java.lang.String toString() {
		return _role.toString();
	}

	public java.lang.String toXmlString() {
		return _role.toXmlString();
	}

	public java.lang.String getTypeLabel() {
		return _role.getTypeLabel();
	}

	public Role getWrappedRole() {
		return _role;
	}

	private Role _role;
}