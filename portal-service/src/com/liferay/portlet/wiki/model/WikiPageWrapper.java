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

package com.liferay.portlet.wiki.model;

/**
 * <p>
 * This class is a wrapper for {@link WikiPage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPage
 * @generated
 */
public class WikiPageWrapper implements WikiPage {
	public WikiPageWrapper(WikiPage wikiPage) {
		_wikiPage = wikiPage;
	}

	public long getPrimaryKey() {
		return _wikiPage.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_wikiPage.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _wikiPage.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_wikiPage.setUuid(uuid);
	}

	public long getPageId() {
		return _wikiPage.getPageId();
	}

	public void setPageId(long pageId) {
		_wikiPage.setPageId(pageId);
	}

	public long getResourcePrimKey() {
		return _wikiPage.getResourcePrimKey();
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPage.setResourcePrimKey(resourcePrimKey);
	}

	public long getGroupId() {
		return _wikiPage.getGroupId();
	}

	public void setGroupId(long groupId) {
		_wikiPage.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _wikiPage.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_wikiPage.setCompanyId(companyId);
	}

	public long getUserId() {
		return _wikiPage.getUserId();
	}

	public void setUserId(long userId) {
		_wikiPage.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_wikiPage.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _wikiPage.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_wikiPage.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _wikiPage.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_wikiPage.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _wikiPage.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_wikiPage.setModifiedDate(modifiedDate);
	}

	public long getNodeId() {
		return _wikiPage.getNodeId();
	}

	public void setNodeId(long nodeId) {
		_wikiPage.setNodeId(nodeId);
	}

	public java.lang.String getTitle() {
		return _wikiPage.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_wikiPage.setTitle(title);
	}

	public double getVersion() {
		return _wikiPage.getVersion();
	}

	public void setVersion(double version) {
		_wikiPage.setVersion(version);
	}

	public boolean getMinorEdit() {
		return _wikiPage.getMinorEdit();
	}

	public boolean isMinorEdit() {
		return _wikiPage.isMinorEdit();
	}

	public void setMinorEdit(boolean minorEdit) {
		_wikiPage.setMinorEdit(minorEdit);
	}

	public java.lang.String getContent() {
		return _wikiPage.getContent();
	}

	public void setContent(java.lang.String content) {
		_wikiPage.setContent(content);
	}

	public java.lang.String getSummary() {
		return _wikiPage.getSummary();
	}

	public void setSummary(java.lang.String summary) {
		_wikiPage.setSummary(summary);
	}

	public java.lang.String getFormat() {
		return _wikiPage.getFormat();
	}

	public void setFormat(java.lang.String format) {
		_wikiPage.setFormat(format);
	}

	public boolean getHead() {
		return _wikiPage.getHead();
	}

	public boolean isHead() {
		return _wikiPage.isHead();
	}

	public void setHead(boolean head) {
		_wikiPage.setHead(head);
	}

	public java.lang.String getParentTitle() {
		return _wikiPage.getParentTitle();
	}

	public void setParentTitle(java.lang.String parentTitle) {
		_wikiPage.setParentTitle(parentTitle);
	}

	public java.lang.String getRedirectTitle() {
		return _wikiPage.getRedirectTitle();
	}

	public void setRedirectTitle(java.lang.String redirectTitle) {
		_wikiPage.setRedirectTitle(redirectTitle);
	}

	public int getStatus() {
		return _wikiPage.getStatus();
	}

	public void setStatus(int status) {
		_wikiPage.setStatus(status);
	}

	public long getStatusByUserId() {
		return _wikiPage.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_wikiPage.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_wikiPage.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _wikiPage.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_wikiPage.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _wikiPage.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_wikiPage.setStatusDate(statusDate);
	}

	public boolean isApproved() {
		return _wikiPage.isApproved();
	}

	public boolean isDraft() {
		return _wikiPage.isDraft();
	}

	public boolean isExpired() {
		return _wikiPage.isExpired();
	}

	public boolean isPending() {
		return _wikiPage.isPending();
	}

	public com.liferay.portlet.wiki.model.WikiPage toEscapedModel() {
		return _wikiPage.toEscapedModel();
	}

	public boolean isNew() {
		return _wikiPage.isNew();
	}

	public void setNew(boolean n) {
		_wikiPage.setNew(n);
	}

	public boolean isCachedModel() {
		return _wikiPage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_wikiPage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _wikiPage.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_wikiPage.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiPage.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiPage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiPage.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _wikiPage.clone();
	}

	public int compareTo(com.liferay.portlet.wiki.model.WikiPage wikiPage) {
		return _wikiPage.compareTo(wikiPage);
	}

	public int hashCode() {
		return _wikiPage.hashCode();
	}

	public java.lang.String toString() {
		return _wikiPage.toString();
	}

	public java.lang.String toXmlString() {
		return _wikiPage.toXmlString();
	}

	public java.lang.String getAttachmentsDir() {
		return _wikiPage.getAttachmentsDir();
	}

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getAttachmentsFiles();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildPages() {
		return _wikiPage.getChildPages();
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode() {
		return _wikiPage.getNode();
	}

	public com.liferay.portlet.wiki.model.WikiPage getParentPage() {
		return _wikiPage.getParentPage();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getParentPages() {
		return _wikiPage.getParentPages();
	}

	public com.liferay.portlet.wiki.model.WikiPage getRedirectPage() {
		return _wikiPage.getRedirectPage();
	}

	public void setAttachmentsDir(java.lang.String attachmentsDir) {
		_wikiPage.setAttachmentsDir(attachmentsDir);
	}

	public WikiPage getWrappedWikiPage() {
		return _wikiPage;
	}

	private WikiPage _wikiPage;
}