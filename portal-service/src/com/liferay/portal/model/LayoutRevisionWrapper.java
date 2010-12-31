/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link LayoutRevision}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutRevision
 * @generated
 */
public class LayoutRevisionWrapper implements LayoutRevision {
	public LayoutRevisionWrapper(LayoutRevision layoutRevision) {
		_layoutRevision = layoutRevision;
	}

	/**
	* Gets the primary key of this layout revision.
	*
	* @return the primary key of this layout revision
	*/
	public long getPrimaryKey() {
		return _layoutRevision.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout revision
	*
	* @param pk the primary key of this layout revision
	*/
	public void setPrimaryKey(long pk) {
		_layoutRevision.setPrimaryKey(pk);
	}

	/**
	* Gets the layout revision ID of this layout revision.
	*
	* @return the layout revision ID of this layout revision
	*/
	public long getLayoutRevisionId() {
		return _layoutRevision.getLayoutRevisionId();
	}

	/**
	* Sets the layout revision ID of this layout revision.
	*
	* @param layoutRevisionId the layout revision ID of this layout revision
	*/
	public void setLayoutRevisionId(long layoutRevisionId) {
		_layoutRevision.setLayoutRevisionId(layoutRevisionId);
	}

	/**
	* Gets the group ID of this layout revision.
	*
	* @return the group ID of this layout revision
	*/
	public long getGroupId() {
		return _layoutRevision.getGroupId();
	}

	/**
	* Sets the group ID of this layout revision.
	*
	* @param groupId the group ID of this layout revision
	*/
	public void setGroupId(long groupId) {
		_layoutRevision.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this layout revision.
	*
	* @return the company ID of this layout revision
	*/
	public long getCompanyId() {
		return _layoutRevision.getCompanyId();
	}

	/**
	* Sets the company ID of this layout revision.
	*
	* @param companyId the company ID of this layout revision
	*/
	public void setCompanyId(long companyId) {
		_layoutRevision.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this layout revision.
	*
	* @return the user ID of this layout revision
	*/
	public long getUserId() {
		return _layoutRevision.getUserId();
	}

	/**
	* Sets the user ID of this layout revision.
	*
	* @param userId the user ID of this layout revision
	*/
	public void setUserId(long userId) {
		_layoutRevision.setUserId(userId);
	}

	/**
	* Gets the user uuid of this layout revision.
	*
	* @return the user uuid of this layout revision
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout revision.
	*
	* @param userUuid the user uuid of this layout revision
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_layoutRevision.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this layout revision.
	*
	* @return the user name of this layout revision
	*/
	public java.lang.String getUserName() {
		return _layoutRevision.getUserName();
	}

	/**
	* Sets the user name of this layout revision.
	*
	* @param userName the user name of this layout revision
	*/
	public void setUserName(java.lang.String userName) {
		_layoutRevision.setUserName(userName);
	}

	/**
	* Gets the create date of this layout revision.
	*
	* @return the create date of this layout revision
	*/
	public java.util.Date getCreateDate() {
		return _layoutRevision.getCreateDate();
	}

	/**
	* Sets the create date of this layout revision.
	*
	* @param createDate the create date of this layout revision
	*/
	public void setCreateDate(java.util.Date createDate) {
		_layoutRevision.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this layout revision.
	*
	* @return the modified date of this layout revision
	*/
	public java.util.Date getModifiedDate() {
		return _layoutRevision.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout revision.
	*
	* @param modifiedDate the modified date of this layout revision
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutRevision.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the layout set branch ID of this layout revision.
	*
	* @return the layout set branch ID of this layout revision
	*/
	public long getLayoutSetBranchId() {
		return _layoutRevision.getLayoutSetBranchId();
	}

	/**
	* Sets the layout set branch ID of this layout revision.
	*
	* @param layoutSetBranchId the layout set branch ID of this layout revision
	*/
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutRevision.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Gets the parent layout revision ID of this layout revision.
	*
	* @return the parent layout revision ID of this layout revision
	*/
	public long getParentLayoutRevisionId() {
		return _layoutRevision.getParentLayoutRevisionId();
	}

	/**
	* Sets the parent layout revision ID of this layout revision.
	*
	* @param parentLayoutRevisionId the parent layout revision ID of this layout revision
	*/
	public void setParentLayoutRevisionId(long parentLayoutRevisionId) {
		_layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
	}

	/**
	* Gets the head of this layout revision.
	*
	* @return the head of this layout revision
	*/
	public boolean getHead() {
		return _layoutRevision.getHead();
	}

	/**
	* Determines if this layout revision is head.
	*
	* @return <code>true</code> if this layout revision is head; <code>false</code> otherwise
	*/
	public boolean isHead() {
		return _layoutRevision.isHead();
	}

	/**
	* Sets whether this layout revision is head.
	*
	* @param head the head of this layout revision
	*/
	public void setHead(boolean head) {
		_layoutRevision.setHead(head);
	}

	/**
	* Gets the plid of this layout revision.
	*
	* @return the plid of this layout revision
	*/
	public long getPlid() {
		return _layoutRevision.getPlid();
	}

	/**
	* Sets the plid of this layout revision.
	*
	* @param plid the plid of this layout revision
	*/
	public void setPlid(long plid) {
		_layoutRevision.setPlid(plid);
	}

	/**
	* Gets the name of this layout revision.
	*
	* @return the name of this layout revision
	*/
	public java.lang.String getName() {
		return _layoutRevision.getName();
	}

	/**
	* Sets the name of this layout revision.
	*
	* @param name the name of this layout revision
	*/
	public void setName(java.lang.String name) {
		_layoutRevision.setName(name);
	}

	/**
	* Gets the title of this layout revision.
	*
	* @return the title of this layout revision
	*/
	public java.lang.String getTitle() {
		return _layoutRevision.getTitle();
	}

	/**
	* Sets the title of this layout revision.
	*
	* @param title the title of this layout revision
	*/
	public void setTitle(java.lang.String title) {
		_layoutRevision.setTitle(title);
	}

	/**
	* Gets the description of this layout revision.
	*
	* @return the description of this layout revision
	*/
	public java.lang.String getDescription() {
		return _layoutRevision.getDescription();
	}

	/**
	* Sets the description of this layout revision.
	*
	* @param description the description of this layout revision
	*/
	public void setDescription(java.lang.String description) {
		_layoutRevision.setDescription(description);
	}

	/**
	* Gets the type settings of this layout revision.
	*
	* @return the type settings of this layout revision
	*/
	public java.lang.String getTypeSettings() {
		return _layoutRevision.getTypeSettings();
	}

	/**
	* Sets the type settings of this layout revision.
	*
	* @param typeSettings the type settings of this layout revision
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_layoutRevision.setTypeSettings(typeSettings);
	}

	/**
	* Gets the icon image of this layout revision.
	*
	* @return the icon image of this layout revision
	*/
	public boolean getIconImage() {
		return _layoutRevision.getIconImage();
	}

	/**
	* Determines if this layout revision is icon image.
	*
	* @return <code>true</code> if this layout revision is icon image; <code>false</code> otherwise
	*/
	public boolean isIconImage() {
		return _layoutRevision.isIconImage();
	}

	/**
	* Sets whether this layout revision is icon image.
	*
	* @param iconImage the icon image of this layout revision
	*/
	public void setIconImage(boolean iconImage) {
		_layoutRevision.setIconImage(iconImage);
	}

	/**
	* Gets the icon image ID of this layout revision.
	*
	* @return the icon image ID of this layout revision
	*/
	public long getIconImageId() {
		return _layoutRevision.getIconImageId();
	}

	/**
	* Sets the icon image ID of this layout revision.
	*
	* @param iconImageId the icon image ID of this layout revision
	*/
	public void setIconImageId(long iconImageId) {
		_layoutRevision.setIconImageId(iconImageId);
	}

	/**
	* Gets the theme ID of this layout revision.
	*
	* @return the theme ID of this layout revision
	*/
	public java.lang.String getThemeId() {
		return _layoutRevision.getThemeId();
	}

	/**
	* Sets the theme ID of this layout revision.
	*
	* @param themeId the theme ID of this layout revision
	*/
	public void setThemeId(java.lang.String themeId) {
		_layoutRevision.setThemeId(themeId);
	}

	/**
	* Gets the color scheme ID of this layout revision.
	*
	* @return the color scheme ID of this layout revision
	*/
	public java.lang.String getColorSchemeId() {
		return _layoutRevision.getColorSchemeId();
	}

	/**
	* Sets the color scheme ID of this layout revision.
	*
	* @param colorSchemeId the color scheme ID of this layout revision
	*/
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutRevision.setColorSchemeId(colorSchemeId);
	}

	/**
	* Gets the wap theme ID of this layout revision.
	*
	* @return the wap theme ID of this layout revision
	*/
	public java.lang.String getWapThemeId() {
		return _layoutRevision.getWapThemeId();
	}

	/**
	* Sets the wap theme ID of this layout revision.
	*
	* @param wapThemeId the wap theme ID of this layout revision
	*/
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutRevision.setWapThemeId(wapThemeId);
	}

	/**
	* Gets the wap color scheme ID of this layout revision.
	*
	* @return the wap color scheme ID of this layout revision
	*/
	public java.lang.String getWapColorSchemeId() {
		return _layoutRevision.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme ID of this layout revision.
	*
	* @param wapColorSchemeId the wap color scheme ID of this layout revision
	*/
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutRevision.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Gets the css of this layout revision.
	*
	* @return the css of this layout revision
	*/
	public java.lang.String getCss() {
		return _layoutRevision.getCss();
	}

	/**
	* Sets the css of this layout revision.
	*
	* @param css the css of this layout revision
	*/
	public void setCss(java.lang.String css) {
		_layoutRevision.setCss(css);
	}

	/**
	* Gets the status of this layout revision.
	*
	* @return the status of this layout revision
	*/
	public int getStatus() {
		return _layoutRevision.getStatus();
	}

	/**
	* Sets the status of this layout revision.
	*
	* @param status the status of this layout revision
	*/
	public void setStatus(int status) {
		_layoutRevision.setStatus(status);
	}

	/**
	* Gets the status by user ID of this layout revision.
	*
	* @return the status by user ID of this layout revision
	*/
	public long getStatusByUserId() {
		return _layoutRevision.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this layout revision.
	*
	* @param statusByUserId the status by user ID of this layout revision
	*/
	public void setStatusByUserId(long statusByUserId) {
		_layoutRevision.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this layout revision.
	*
	* @return the status by user uuid of this layout revision
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this layout revision.
	*
	* @param statusByUserUuid the status by user uuid of this layout revision
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_layoutRevision.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this layout revision.
	*
	* @return the status by user name of this layout revision
	*/
	public java.lang.String getStatusByUserName() {
		return _layoutRevision.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this layout revision.
	*
	* @param statusByUserName the status by user name of this layout revision
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_layoutRevision.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this layout revision.
	*
	* @return the status date of this layout revision
	*/
	public java.util.Date getStatusDate() {
		return _layoutRevision.getStatusDate();
	}

	/**
	* Sets the status date of this layout revision.
	*
	* @param statusDate the status date of this layout revision
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_layoutRevision.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _layoutRevision.getApproved();
	}

	/**
	* Determines if this layout revision is approved.
	*
	* @return <code>true</code> if this layout revision is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _layoutRevision.isApproved();
	}

	/**
	* Determines if this layout revision is a draft.
	*
	* @return <code>true</code> if this layout revision is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _layoutRevision.isDraft();
	}

	/**
	* Determines if this layout revision is expired.
	*
	* @return <code>true</code> if this layout revision is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _layoutRevision.isExpired();
	}

	/**
	* Determines if this layout revision is pending.
	*
	* @return <code>true</code> if this layout revision is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _layoutRevision.isPending();
	}

	public boolean isNew() {
		return _layoutRevision.isNew();
	}

	public void setNew(boolean n) {
		_layoutRevision.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutRevision.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutRevision.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutRevision.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutRevision.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutRevision.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutRevision.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutRevision.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new LayoutRevisionWrapper((LayoutRevision)_layoutRevision.clone());
	}

	public int compareTo(com.liferay.portal.model.LayoutRevision layoutRevision) {
		return _layoutRevision.compareTo(layoutRevision);
	}

	public int hashCode() {
		return _layoutRevision.hashCode();
	}

	public com.liferay.portal.model.LayoutRevision toEscapedModel() {
		return new LayoutRevisionWrapper(_layoutRevision.toEscapedModel());
	}

	public java.lang.String toString() {
		return _layoutRevision.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutRevision.toXmlString();
	}

	public LayoutRevision getWrappedLayoutRevision() {
		return _layoutRevision;
	}

	private LayoutRevision _layoutRevision;
}