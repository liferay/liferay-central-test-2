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

package com.liferay.portlet.journal.model;

/**
 * <p>
 * This class is a wrapper for {@link JournalFeed}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeed
 * @generated
 */
public class JournalFeedWrapper implements JournalFeed {
	public JournalFeedWrapper(JournalFeed journalFeed) {
		_journalFeed = journalFeed;
	}

	/**
	* Gets the primary key of this journal feed.
	*
	* @return the primary key of this journal feed
	*/
	public long getPrimaryKey() {
		return _journalFeed.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal feed
	*
	* @param pk the primary key of this journal feed
	*/
	public void setPrimaryKey(long pk) {
		_journalFeed.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this journal feed.
	*
	* @return the uuid of this journal feed
	*/
	public java.lang.String getUuid() {
		return _journalFeed.getUuid();
	}

	/**
	* Sets the uuid of this journal feed.
	*
	* @param uuid the uuid of this journal feed
	*/
	public void setUuid(java.lang.String uuid) {
		_journalFeed.setUuid(uuid);
	}

	/**
	* Gets the ID of this journal feed.
	*
	* @return the ID of this journal feed
	*/
	public long getId() {
		return _journalFeed.getId();
	}

	/**
	* Sets the ID of this journal feed.
	*
	* @param id the ID of this journal feed
	*/
	public void setId(long id) {
		_journalFeed.setId(id);
	}

	/**
	* Gets the group ID of this journal feed.
	*
	* @return the group ID of this journal feed
	*/
	public long getGroupId() {
		return _journalFeed.getGroupId();
	}

	/**
	* Sets the group ID of this journal feed.
	*
	* @param groupId the group ID of this journal feed
	*/
	public void setGroupId(long groupId) {
		_journalFeed.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this journal feed.
	*
	* @return the company ID of this journal feed
	*/
	public long getCompanyId() {
		return _journalFeed.getCompanyId();
	}

	/**
	* Sets the company ID of this journal feed.
	*
	* @param companyId the company ID of this journal feed
	*/
	public void setCompanyId(long companyId) {
		_journalFeed.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this journal feed.
	*
	* @return the user ID of this journal feed
	*/
	public long getUserId() {
		return _journalFeed.getUserId();
	}

	/**
	* Sets the user ID of this journal feed.
	*
	* @param userId the user ID of this journal feed
	*/
	public void setUserId(long userId) {
		_journalFeed.setUserId(userId);
	}

	/**
	* Gets the user uuid of this journal feed.
	*
	* @return the user uuid of this journal feed
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFeed.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal feed.
	*
	* @param userUuid the user uuid of this journal feed
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_journalFeed.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this journal feed.
	*
	* @return the user name of this journal feed
	*/
	public java.lang.String getUserName() {
		return _journalFeed.getUserName();
	}

	/**
	* Sets the user name of this journal feed.
	*
	* @param userName the user name of this journal feed
	*/
	public void setUserName(java.lang.String userName) {
		_journalFeed.setUserName(userName);
	}

	/**
	* Gets the create date of this journal feed.
	*
	* @return the create date of this journal feed
	*/
	public java.util.Date getCreateDate() {
		return _journalFeed.getCreateDate();
	}

	/**
	* Sets the create date of this journal feed.
	*
	* @param createDate the create date of this journal feed
	*/
	public void setCreateDate(java.util.Date createDate) {
		_journalFeed.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this journal feed.
	*
	* @return the modified date of this journal feed
	*/
	public java.util.Date getModifiedDate() {
		return _journalFeed.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal feed.
	*
	* @param modifiedDate the modified date of this journal feed
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalFeed.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the feed ID of this journal feed.
	*
	* @return the feed ID of this journal feed
	*/
	public java.lang.String getFeedId() {
		return _journalFeed.getFeedId();
	}

	/**
	* Sets the feed ID of this journal feed.
	*
	* @param feedId the feed ID of this journal feed
	*/
	public void setFeedId(java.lang.String feedId) {
		_journalFeed.setFeedId(feedId);
	}

	/**
	* Gets the name of this journal feed.
	*
	* @return the name of this journal feed
	*/
	public java.lang.String getName() {
		return _journalFeed.getName();
	}

	/**
	* Sets the name of this journal feed.
	*
	* @param name the name of this journal feed
	*/
	public void setName(java.lang.String name) {
		_journalFeed.setName(name);
	}

	/**
	* Gets the description of this journal feed.
	*
	* @return the description of this journal feed
	*/
	public java.lang.String getDescription() {
		return _journalFeed.getDescription();
	}

	/**
	* Sets the description of this journal feed.
	*
	* @param description the description of this journal feed
	*/
	public void setDescription(java.lang.String description) {
		_journalFeed.setDescription(description);
	}

	/**
	* Gets the type of this journal feed.
	*
	* @return the type of this journal feed
	*/
	public java.lang.String getType() {
		return _journalFeed.getType();
	}

	/**
	* Sets the type of this journal feed.
	*
	* @param type the type of this journal feed
	*/
	public void setType(java.lang.String type) {
		_journalFeed.setType(type);
	}

	/**
	* Gets the structure ID of this journal feed.
	*
	* @return the structure ID of this journal feed
	*/
	public java.lang.String getStructureId() {
		return _journalFeed.getStructureId();
	}

	/**
	* Sets the structure ID of this journal feed.
	*
	* @param structureId the structure ID of this journal feed
	*/
	public void setStructureId(java.lang.String structureId) {
		_journalFeed.setStructureId(structureId);
	}

	/**
	* Gets the template ID of this journal feed.
	*
	* @return the template ID of this journal feed
	*/
	public java.lang.String getTemplateId() {
		return _journalFeed.getTemplateId();
	}

	/**
	* Sets the template ID of this journal feed.
	*
	* @param templateId the template ID of this journal feed
	*/
	public void setTemplateId(java.lang.String templateId) {
		_journalFeed.setTemplateId(templateId);
	}

	/**
	* Gets the renderer template ID of this journal feed.
	*
	* @return the renderer template ID of this journal feed
	*/
	public java.lang.String getRendererTemplateId() {
		return _journalFeed.getRendererTemplateId();
	}

	/**
	* Sets the renderer template ID of this journal feed.
	*
	* @param rendererTemplateId the renderer template ID of this journal feed
	*/
	public void setRendererTemplateId(java.lang.String rendererTemplateId) {
		_journalFeed.setRendererTemplateId(rendererTemplateId);
	}

	/**
	* Gets the delta of this journal feed.
	*
	* @return the delta of this journal feed
	*/
	public int getDelta() {
		return _journalFeed.getDelta();
	}

	/**
	* Sets the delta of this journal feed.
	*
	* @param delta the delta of this journal feed
	*/
	public void setDelta(int delta) {
		_journalFeed.setDelta(delta);
	}

	/**
	* Gets the order by col of this journal feed.
	*
	* @return the order by col of this journal feed
	*/
	public java.lang.String getOrderByCol() {
		return _journalFeed.getOrderByCol();
	}

	/**
	* Sets the order by col of this journal feed.
	*
	* @param orderByCol the order by col of this journal feed
	*/
	public void setOrderByCol(java.lang.String orderByCol) {
		_journalFeed.setOrderByCol(orderByCol);
	}

	/**
	* Gets the order by type of this journal feed.
	*
	* @return the order by type of this journal feed
	*/
	public java.lang.String getOrderByType() {
		return _journalFeed.getOrderByType();
	}

	/**
	* Sets the order by type of this journal feed.
	*
	* @param orderByType the order by type of this journal feed
	*/
	public void setOrderByType(java.lang.String orderByType) {
		_journalFeed.setOrderByType(orderByType);
	}

	/**
	* Gets the target layout friendly url of this journal feed.
	*
	* @return the target layout friendly url of this journal feed
	*/
	public java.lang.String getTargetLayoutFriendlyUrl() {
		return _journalFeed.getTargetLayoutFriendlyUrl();
	}

	/**
	* Sets the target layout friendly url of this journal feed.
	*
	* @param targetLayoutFriendlyUrl the target layout friendly url of this journal feed
	*/
	public void setTargetLayoutFriendlyUrl(
		java.lang.String targetLayoutFriendlyUrl) {
		_journalFeed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
	}

	/**
	* Gets the target portlet ID of this journal feed.
	*
	* @return the target portlet ID of this journal feed
	*/
	public java.lang.String getTargetPortletId() {
		return _journalFeed.getTargetPortletId();
	}

	/**
	* Sets the target portlet ID of this journal feed.
	*
	* @param targetPortletId the target portlet ID of this journal feed
	*/
	public void setTargetPortletId(java.lang.String targetPortletId) {
		_journalFeed.setTargetPortletId(targetPortletId);
	}

	/**
	* Gets the content field of this journal feed.
	*
	* @return the content field of this journal feed
	*/
	public java.lang.String getContentField() {
		return _journalFeed.getContentField();
	}

	/**
	* Sets the content field of this journal feed.
	*
	* @param contentField the content field of this journal feed
	*/
	public void setContentField(java.lang.String contentField) {
		_journalFeed.setContentField(contentField);
	}

	/**
	* Gets the feed type of this journal feed.
	*
	* @return the feed type of this journal feed
	*/
	public java.lang.String getFeedType() {
		return _journalFeed.getFeedType();
	}

	/**
	* Sets the feed type of this journal feed.
	*
	* @param feedType the feed type of this journal feed
	*/
	public void setFeedType(java.lang.String feedType) {
		_journalFeed.setFeedType(feedType);
	}

	/**
	* Gets the feed version of this journal feed.
	*
	* @return the feed version of this journal feed
	*/
	public double getFeedVersion() {
		return _journalFeed.getFeedVersion();
	}

	/**
	* Sets the feed version of this journal feed.
	*
	* @param feedVersion the feed version of this journal feed
	*/
	public void setFeedVersion(double feedVersion) {
		_journalFeed.setFeedVersion(feedVersion);
	}

	public boolean isNew() {
		return _journalFeed.isNew();
	}

	public void setNew(boolean n) {
		_journalFeed.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalFeed.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalFeed.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalFeed.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalFeed.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalFeed.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalFeed.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalFeed.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new JournalFeedWrapper((JournalFeed)_journalFeed.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalFeed journalFeed) {
		return _journalFeed.compareTo(journalFeed);
	}

	public int hashCode() {
		return _journalFeed.hashCode();
	}

	public com.liferay.portlet.journal.model.JournalFeed toEscapedModel() {
		return new JournalFeedWrapper(_journalFeed.toEscapedModel());
	}

	public java.lang.String toString() {
		return _journalFeed.toString();
	}

	public java.lang.String toXmlString() {
		return _journalFeed.toXmlString();
	}

	public JournalFeed getWrappedJournalFeed() {
		return _journalFeed;
	}

	private JournalFeed _journalFeed;
}