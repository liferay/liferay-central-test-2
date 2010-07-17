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

package com.liferay.portlet.journal.model;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticleResource}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResource
 * @generated
 */
public class JournalArticleResourceWrapper implements JournalArticleResource {
	public JournalArticleResourceWrapper(
		JournalArticleResource journalArticleResource) {
		_journalArticleResource = journalArticleResource;
	}

	public long getPrimaryKey() {
		return _journalArticleResource.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_journalArticleResource.setPrimaryKey(pk);
	}

	public long getResourcePrimKey() {
		return _journalArticleResource.getResourcePrimKey();
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_journalArticleResource.setResourcePrimKey(resourcePrimKey);
	}

	public long getGroupId() {
		return _journalArticleResource.getGroupId();
	}

	public void setGroupId(long groupId) {
		_journalArticleResource.setGroupId(groupId);
	}

	public java.lang.String getArticleId() {
		return _journalArticleResource.getArticleId();
	}

	public void setArticleId(java.lang.String articleId) {
		_journalArticleResource.setArticleId(articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticleResource toEscapedModel() {
		return _journalArticleResource.toEscapedModel();
	}

	public boolean isNew() {
		return _journalArticleResource.isNew();
	}

	public void setNew(boolean n) {
		_journalArticleResource.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalArticleResource.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalArticleResource.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalArticleResource.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalArticleResource.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticleResource.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticleResource.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalArticleResource.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalArticleResource.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource) {
		return _journalArticleResource.compareTo(journalArticleResource);
	}

	public int hashCode() {
		return _journalArticleResource.hashCode();
	}

	public java.lang.String toString() {
		return _journalArticleResource.toString();
	}

	public java.lang.String toXmlString() {
		return _journalArticleResource.toXmlString();
	}

	public JournalArticleResource getWrappedJournalArticleResource() {
		return _journalArticleResource;
	}

	private JournalArticleResource _journalArticleResource;
}