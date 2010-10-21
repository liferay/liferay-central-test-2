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
 * This class is a wrapper for {@link Revision}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Revision
 * @generated
 */
public class RevisionWrapper implements Revision {
	public RevisionWrapper(Revision revision) {
		_revision = revision;
	}

	/**
	* Gets the primary key of this revision.
	*
	* @return the primary key of this revision
	*/
	public long getPrimaryKey() {
		return _revision.getPrimaryKey();
	}

	/**
	* Sets the primary key of this revision
	*
	* @param pk the primary key of this revision
	*/
	public void setPrimaryKey(long pk) {
		_revision.setPrimaryKey(pk);
	}

	/**
	* Gets the revision id of this revision.
	*
	* @return the revision id of this revision
	*/
	public long getRevisionId() {
		return _revision.getRevisionId();
	}

	/**
	* Sets the revision id of this revision.
	*
	* @param revisionId the revision id of this revision
	*/
	public void setRevisionId(long revisionId) {
		_revision.setRevisionId(revisionId);
	}

	/**
	* Gets the group id of this revision.
	*
	* @return the group id of this revision
	*/
	public long getGroupId() {
		return _revision.getGroupId();
	}

	/**
	* Sets the group id of this revision.
	*
	* @param groupId the group id of this revision
	*/
	public void setGroupId(long groupId) {
		_revision.setGroupId(groupId);
	}

	/**
	* Gets the company id of this revision.
	*
	* @return the company id of this revision
	*/
	public long getCompanyId() {
		return _revision.getCompanyId();
	}

	/**
	* Sets the company id of this revision.
	*
	* @param companyId the company id of this revision
	*/
	public void setCompanyId(long companyId) {
		_revision.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this revision.
	*
	* @return the user id of this revision
	*/
	public long getUserId() {
		return _revision.getUserId();
	}

	/**
	* Sets the user id of this revision.
	*
	* @param userId the user id of this revision
	*/
	public void setUserId(long userId) {
		_revision.setUserId(userId);
	}

	/**
	* Gets the user uuid of this revision.
	*
	* @return the user uuid of this revision
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revision.getUserUuid();
	}

	/**
	* Sets the user uuid of this revision.
	*
	* @param userUuid the user uuid of this revision
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_revision.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this revision.
	*
	* @return the user name of this revision
	*/
	public java.lang.String getUserName() {
		return _revision.getUserName();
	}

	/**
	* Sets the user name of this revision.
	*
	* @param userName the user name of this revision
	*/
	public void setUserName(java.lang.String userName) {
		_revision.setUserName(userName);
	}

	/**
	* Gets the create date of this revision.
	*
	* @return the create date of this revision
	*/
	public java.util.Date getCreateDate() {
		return _revision.getCreateDate();
	}

	/**
	* Sets the create date of this revision.
	*
	* @param createDate the create date of this revision
	*/
	public void setCreateDate(java.util.Date createDate) {
		_revision.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this revision.
	*
	* @return the modified date of this revision
	*/
	public java.util.Date getModifiedDate() {
		return _revision.getModifiedDate();
	}

	/**
	* Sets the modified date of this revision.
	*
	* @param modifiedDate the modified date of this revision
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_revision.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the branch id of this revision.
	*
	* @return the branch id of this revision
	*/
	public long getBranchId() {
		return _revision.getBranchId();
	}

	/**
	* Sets the branch id of this revision.
	*
	* @param branchId the branch id of this revision
	*/
	public void setBranchId(long branchId) {
		_revision.setBranchId(branchId);
	}

	/**
	* Gets the plid of this revision.
	*
	* @return the plid of this revision
	*/
	public long getPlid() {
		return _revision.getPlid();
	}

	/**
	* Sets the plid of this revision.
	*
	* @param plid the plid of this revision
	*/
	public void setPlid(long plid) {
		_revision.setPlid(plid);
	}

	/**
	* Gets the parent revision id of this revision.
	*
	* @return the parent revision id of this revision
	*/
	public long getParentRevisionId() {
		return _revision.getParentRevisionId();
	}

	/**
	* Sets the parent revision id of this revision.
	*
	* @param parentRevisionId the parent revision id of this revision
	*/
	public void setParentRevisionId(long parentRevisionId) {
		_revision.setParentRevisionId(parentRevisionId);
	}

	/**
	* Gets the head of this revision.
	*
	* @return the head of this revision
	*/
	public boolean getHead() {
		return _revision.getHead();
	}

	/**
	* Determines if this revision is head.
	*
	* @return <code>true</code> if this revision is head; <code>false</code> otherwise
	*/
	public boolean isHead() {
		return _revision.isHead();
	}

	/**
	* Sets whether this revision is head.
	*
	* @param head the head of this revision
	*/
	public void setHead(boolean head) {
		_revision.setHead(head);
	}

	/**
	* Gets the name of this revision.
	*
	* @return the name of this revision
	*/
	public java.lang.String getName() {
		return _revision.getName();
	}

	/**
	* Sets the name of this revision.
	*
	* @param name the name of this revision
	*/
	public void setName(java.lang.String name) {
		_revision.setName(name);
	}

	/**
	* Gets the title of this revision.
	*
	* @return the title of this revision
	*/
	public java.lang.String getTitle() {
		return _revision.getTitle();
	}

	/**
	* Sets the title of this revision.
	*
	* @param title the title of this revision
	*/
	public void setTitle(java.lang.String title) {
		_revision.setTitle(title);
	}

	/**
	* Gets the description of this revision.
	*
	* @return the description of this revision
	*/
	public java.lang.String getDescription() {
		return _revision.getDescription();
	}

	/**
	* Sets the description of this revision.
	*
	* @param description the description of this revision
	*/
	public void setDescription(java.lang.String description) {
		_revision.setDescription(description);
	}

	/**
	* Gets the type settings of this revision.
	*
	* @return the type settings of this revision
	*/
	public java.lang.String getTypeSettings() {
		return _revision.getTypeSettings();
	}

	/**
	* Sets the type settings of this revision.
	*
	* @param typeSettings the type settings of this revision
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_revision.setTypeSettings(typeSettings);
	}

	/**
	* Gets the icon image of this revision.
	*
	* @return the icon image of this revision
	*/
	public boolean getIconImage() {
		return _revision.getIconImage();
	}

	/**
	* Determines if this revision is icon image.
	*
	* @return <code>true</code> if this revision is icon image; <code>false</code> otherwise
	*/
	public boolean isIconImage() {
		return _revision.isIconImage();
	}

	/**
	* Sets whether this revision is icon image.
	*
	* @param iconImage the icon image of this revision
	*/
	public void setIconImage(boolean iconImage) {
		_revision.setIconImage(iconImage);
	}

	/**
	* Gets the icon image id of this revision.
	*
	* @return the icon image id of this revision
	*/
	public long getIconImageId() {
		return _revision.getIconImageId();
	}

	/**
	* Sets the icon image id of this revision.
	*
	* @param iconImageId the icon image id of this revision
	*/
	public void setIconImageId(long iconImageId) {
		_revision.setIconImageId(iconImageId);
	}

	/**
	* Gets the theme id of this revision.
	*
	* @return the theme id of this revision
	*/
	public java.lang.String getThemeId() {
		return _revision.getThemeId();
	}

	/**
	* Sets the theme id of this revision.
	*
	* @param themeId the theme id of this revision
	*/
	public void setThemeId(java.lang.String themeId) {
		_revision.setThemeId(themeId);
	}

	/**
	* Gets the color scheme id of this revision.
	*
	* @return the color scheme id of this revision
	*/
	public java.lang.String getColorSchemeId() {
		return _revision.getColorSchemeId();
	}

	/**
	* Sets the color scheme id of this revision.
	*
	* @param colorSchemeId the color scheme id of this revision
	*/
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_revision.setColorSchemeId(colorSchemeId);
	}

	/**
	* Gets the wap theme id of this revision.
	*
	* @return the wap theme id of this revision
	*/
	public java.lang.String getWapThemeId() {
		return _revision.getWapThemeId();
	}

	/**
	* Sets the wap theme id of this revision.
	*
	* @param wapThemeId the wap theme id of this revision
	*/
	public void setWapThemeId(java.lang.String wapThemeId) {
		_revision.setWapThemeId(wapThemeId);
	}

	/**
	* Gets the wap color scheme id of this revision.
	*
	* @return the wap color scheme id of this revision
	*/
	public java.lang.String getWapColorSchemeId() {
		return _revision.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme id of this revision.
	*
	* @param wapColorSchemeId the wap color scheme id of this revision
	*/
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_revision.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Gets the css of this revision.
	*
	* @return the css of this revision
	*/
	public java.lang.String getCss() {
		return _revision.getCss();
	}

	/**
	* Sets the css of this revision.
	*
	* @param css the css of this revision
	*/
	public void setCss(java.lang.String css) {
		_revision.setCss(css);
	}

	/**
	* Gets the status of this revision.
	*
	* @return the status of this revision
	*/
	public int getStatus() {
		return _revision.getStatus();
	}

	/**
	* Sets the status of this revision.
	*
	* @param status the status of this revision
	*/
	public void setStatus(int status) {
		_revision.setStatus(status);
	}

	/**
	* Gets the status by user id of this revision.
	*
	* @return the status by user id of this revision
	*/
	public long getStatusByUserId() {
		return _revision.getStatusByUserId();
	}

	/**
	* Sets the status by user id of this revision.
	*
	* @param statusByUserId the status by user id of this revision
	*/
	public void setStatusByUserId(long statusByUserId) {
		_revision.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this revision.
	*
	* @return the status by user uuid of this revision
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revision.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this revision.
	*
	* @param statusByUserUuid the status by user uuid of this revision
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_revision.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this revision.
	*
	* @return the status by user name of this revision
	*/
	public java.lang.String getStatusByUserName() {
		return _revision.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this revision.
	*
	* @param statusByUserName the status by user name of this revision
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_revision.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this revision.
	*
	* @return the status date of this revision
	*/
	public java.util.Date getStatusDate() {
		return _revision.getStatusDate();
	}

	/**
	* Sets the status date of this revision.
	*
	* @param statusDate the status date of this revision
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_revision.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _revision.getApproved();
	}

	/**
	* Determines if this revision is approved.
	*
	* @return <code>true</code> if this revision is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _revision.isApproved();
	}

	/**
	* Determines if this revision is a draft.
	*
	* @return <code>true</code> if this revision is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _revision.isDraft();
	}

	/**
	* Determines if this revision is expired.
	*
	* @return <code>true</code> if this revision is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _revision.isExpired();
	}

	/**
	* Determines if this revision is pending.
	*
	* @return <code>true</code> if this revision is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _revision.isPending();
	}

	public boolean isNew() {
		return _revision.isNew();
	}

	public void setNew(boolean n) {
		_revision.setNew(n);
	}

	public boolean isCachedModel() {
		return _revision.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_revision.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _revision.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_revision.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _revision.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _revision.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_revision.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _revision.clone();
	}

	public int compareTo(com.liferay.portal.model.Revision revision) {
		return _revision.compareTo(revision);
	}

	public int hashCode() {
		return _revision.hashCode();
	}

	public com.liferay.portal.model.Revision toEscapedModel() {
		return _revision.toEscapedModel();
	}

	public java.lang.String toString() {
		return _revision.toString();
	}

	public java.lang.String toXmlString() {
		return _revision.toXmlString();
	}

	public Revision getWrappedRevision() {
		return _revision;
	}

	private Revision _revision;
}