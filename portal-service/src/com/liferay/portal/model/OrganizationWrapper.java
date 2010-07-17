/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Organization}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Organization
 * @generated
 */
public class OrganizationWrapper implements Organization {
	public OrganizationWrapper(Organization organization) {
		_organization = organization;
	}

	public long getPrimaryKey() {
		return _organization.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_organization.setPrimaryKey(pk);
	}

	public long getOrganizationId() {
		return _organization.getOrganizationId();
	}

	public void setOrganizationId(long organizationId) {
		_organization.setOrganizationId(organizationId);
	}

	public long getCompanyId() {
		return _organization.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_organization.setCompanyId(companyId);
	}

	public long getParentOrganizationId() {
		return _organization.getParentOrganizationId();
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_organization.setParentOrganizationId(parentOrganizationId);
	}

	public long getLeftOrganizationId() {
		return _organization.getLeftOrganizationId();
	}

	public void setLeftOrganizationId(long leftOrganizationId) {
		_organization.setLeftOrganizationId(leftOrganizationId);
	}

	public long getRightOrganizationId() {
		return _organization.getRightOrganizationId();
	}

	public void setRightOrganizationId(long rightOrganizationId) {
		_organization.setRightOrganizationId(rightOrganizationId);
	}

	public java.lang.String getName() {
		return _organization.getName();
	}

	public void setName(java.lang.String name) {
		_organization.setName(name);
	}

	public java.lang.String getType() {
		return _organization.getType();
	}

	public void setType(java.lang.String type) {
		_organization.setType(type);
	}

	public boolean getRecursable() {
		return _organization.getRecursable();
	}

	public boolean isRecursable() {
		return _organization.isRecursable();
	}

	public void setRecursable(boolean recursable) {
		_organization.setRecursable(recursable);
	}

	public long getRegionId() {
		return _organization.getRegionId();
	}

	public void setRegionId(long regionId) {
		_organization.setRegionId(regionId);
	}

	public long getCountryId() {
		return _organization.getCountryId();
	}

	public void setCountryId(long countryId) {
		_organization.setCountryId(countryId);
	}

	public int getStatusId() {
		return _organization.getStatusId();
	}

	public void setStatusId(int statusId) {
		_organization.setStatusId(statusId);
	}

	public java.lang.String getComments() {
		return _organization.getComments();
	}

	public void setComments(java.lang.String comments) {
		_organization.setComments(comments);
	}

	public com.liferay.portal.model.Organization toEscapedModel() {
		return _organization.toEscapedModel();
	}

	public boolean isNew() {
		return _organization.isNew();
	}

	public void setNew(boolean n) {
		_organization.setNew(n);
	}

	public boolean isCachedModel() {
		return _organization.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_organization.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _organization.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_organization.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _organization.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _organization.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_organization.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _organization.clone();
	}

	public int compareTo(com.liferay.portal.model.Organization organization) {
		return _organization.compareTo(organization);
	}

	public int hashCode() {
		return _organization.hashCode();
	}

	public java.lang.String toString() {
		return _organization.toString();
	}

	public java.lang.String toXmlString() {
		return _organization.toXmlString();
	}

	public java.util.List<com.liferay.portal.model.Organization> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.getAncestors();
	}

	public com.liferay.portal.model.Organization getParentOrganization()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organization.getParentOrganization();
	}

	public com.liferay.portal.model.Address getAddress() {
		return _organization.getAddress();
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getAddresses();
	}

	public java.lang.String[] getChildrenTypes() {
		return _organization.getChildrenTypes();
	}

	public java.util.List<com.liferay.portal.model.Organization> getDescendants()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getDescendants();
	}

	public com.liferay.portal.model.Group getGroup() {
		return _organization.getGroup();
	}

	public long getLogoId() {
		return _organization.getLogoId();
	}

	public javax.portlet.PortletPreferences getPreferences()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getPreferences();
	}

	public int getPrivateLayoutsPageCount() {
		return _organization.getPrivateLayoutsPageCount();
	}

	public int getPublicLayoutsPageCount() {
		return _organization.getPublicLayoutsPageCount();
	}

	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getReminderQueryQuestions(locale);
	}

	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getReminderQueryQuestions(languageId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getSuborganizations();
	}

	public int getSuborganizationsSize()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.getSuborganizationsSize();
	}

	public int getTypeOrder() {
		return _organization.getTypeOrder();
	}

	public boolean hasPrivateLayouts() {
		return _organization.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts() {
		return _organization.hasPublicLayouts();
	}

	public boolean hasSuborganizations()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organization.hasSuborganizations();
	}

	public boolean isParentable() {
		return _organization.isParentable();
	}

	public boolean isRoot() {
		return _organization.isRoot();
	}

	public Organization getWrappedOrganization() {
		return _organization;
	}

	private Organization _organization;
}