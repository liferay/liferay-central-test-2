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
 * This class is a wrapper for {@link Group}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Group
 * @generated
 */
public class GroupWrapper implements Group {
	public GroupWrapper(Group group) {
		_group = group;
	}

	public long getPrimaryKey() {
		return _group.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_group.setPrimaryKey(pk);
	}

	public long getGroupId() {
		return _group.getGroupId();
	}

	public void setGroupId(long groupId) {
		_group.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _group.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_group.setCompanyId(companyId);
	}

	public long getCreatorUserId() {
		return _group.getCreatorUserId();
	}

	public void setCreatorUserId(long creatorUserId) {
		_group.setCreatorUserId(creatorUserId);
	}

	public java.lang.String getCreatorUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getCreatorUserUuid();
	}

	public void setCreatorUserUuid(java.lang.String creatorUserUuid) {
		_group.setCreatorUserUuid(creatorUserUuid);
	}

	public java.lang.String getClassName() {
		return _group.getClassName();
	}

	public long getClassNameId() {
		return _group.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_group.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _group.getClassPK();
	}

	public void setClassPK(long classPK) {
		_group.setClassPK(classPK);
	}

	public long getParentGroupId() {
		return _group.getParentGroupId();
	}

	public void setParentGroupId(long parentGroupId) {
		_group.setParentGroupId(parentGroupId);
	}

	public long getLiveGroupId() {
		return _group.getLiveGroupId();
	}

	public void setLiveGroupId(long liveGroupId) {
		_group.setLiveGroupId(liveGroupId);
	}

	public java.lang.String getName() {
		return _group.getName();
	}

	public void setName(java.lang.String name) {
		_group.setName(name);
	}

	public java.lang.String getDescription() {
		return _group.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_group.setDescription(description);
	}

	public int getType() {
		return _group.getType();
	}

	public void setType(int type) {
		_group.setType(type);
	}

	public java.lang.String getTypeSettings() {
		return _group.getTypeSettings();
	}

	public void setTypeSettings(java.lang.String typeSettings) {
		_group.setTypeSettings(typeSettings);
	}

	public java.lang.String getFriendlyURL() {
		return _group.getFriendlyURL();
	}

	public void setFriendlyURL(java.lang.String friendlyURL) {
		_group.setFriendlyURL(friendlyURL);
	}

	public boolean getActive() {
		return _group.getActive();
	}

	public boolean isActive() {
		return _group.isActive();
	}

	public void setActive(boolean active) {
		_group.setActive(active);
	}

	public com.liferay.portal.model.Group toEscapedModel() {
		return _group.toEscapedModel();
	}

	public boolean isNew() {
		return _group.isNew();
	}

	public void setNew(boolean n) {
		_group.setNew(n);
	}

	public boolean isCachedModel() {
		return _group.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_group.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _group.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_group.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _group.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _group.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_group.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _group.clone();
	}

	public int compareTo(com.liferay.portal.model.Group group) {
		return _group.compareTo(group);
	}

	public int hashCode() {
		return _group.hashCode();
	}

	public java.lang.String toString() {
		return _group.toString();
	}

	public java.lang.String toXmlString() {
		return _group.toXmlString();
	}

	public long getDefaultPrivatePlid() {
		return _group.getDefaultPrivatePlid();
	}

	public long getDefaultPublicPlid() {
		return _group.getDefaultPublicPlid();
	}

	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getDescriptiveName();
	}

	public com.liferay.portal.model.Group getLiveGroup() {
		return _group.getLiveGroup();
	}

	public long getOrganizationId() {
		return _group.getOrganizationId();
	}

	public java.lang.String getPathFriendlyURL(boolean privateLayout,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getPathFriendlyURL(privateLayout, themeDisplay);
	}

	public com.liferay.portal.model.LayoutSet getPrivateLayoutSet() {
		return _group.getPrivateLayoutSet();
	}

	public int getPrivateLayoutsPageCount() {
		return _group.getPrivateLayoutsPageCount();
	}

	public com.liferay.portal.model.LayoutSet getPublicLayoutSet() {
		return _group.getPublicLayoutSet();
	}

	public int getPublicLayoutsPageCount() {
		return _group.getPublicLayoutsPageCount();
	}

	public com.liferay.portal.model.Group getStagingGroup() {
		return _group.getStagingGroup();
	}

	public java.lang.String getTypeLabel() {
		return _group.getTypeLabel();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _group.getTypeSettingsProperties();
	}

	public java.lang.String getTypeSettingsProperty(java.lang.String key) {
		return _group.getTypeSettingsProperty(key);
	}

	public java.lang.String getWorkflowRoleNames() {
		return _group.getWorkflowRoleNames();
	}

	public int getWorkflowStages() {
		return _group.getWorkflowStages();
	}

	public boolean hasPrivateLayouts() {
		return _group.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts() {
		return _group.hasPublicLayouts();
	}

	public boolean hasStagingGroup() {
		return _group.hasStagingGroup();
	}

	public boolean isCommunity() {
		return _group.isCommunity();
	}

	public boolean isCompany() {
		return _group.isCompany();
	}

	public boolean isControlPanel() {
		return _group.isControlPanel();
	}

	public boolean isLayout() {
		return _group.isLayout();
	}

	public boolean isLayoutPrototype() {
		return _group.isLayoutPrototype();
	}

	public boolean isLayoutSetPrototype() {
		return _group.isLayoutSetPrototype();
	}

	public boolean isOrganization() {
		return _group.isOrganization();
	}

	public boolean isStaged() {
		return _group.isStaged();
	}

	public boolean isStagedPortlet(java.lang.String portletId) {
		return _group.isStagedPortlet(portletId);
	}

	public boolean isStagedRemotely() {
		return _group.isStagedRemotely();
	}

	public boolean isStagingGroup() {
		return _group.isStagingGroup();
	}

	public boolean isUser() {
		return _group.isUser();
	}

	public boolean isUserGroup() {
		return _group.isUserGroup();
	}

	public boolean isWorkflowEnabled() {
		return _group.isWorkflowEnabled();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_group.setTypeSettingsProperties(typeSettingsProperties);
	}

	public Group getWrappedGroup() {
		return _group;
	}

	private Group _group;
}