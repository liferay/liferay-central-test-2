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
 * This class is a wrapper for {@link WikiPageResource}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageResource
 * @generated
 */
public class WikiPageResourceWrapper implements WikiPageResource {
	public WikiPageResourceWrapper(WikiPageResource wikiPageResource) {
		_wikiPageResource = wikiPageResource;
	}

	public long getPrimaryKey() {
		return _wikiPageResource.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_wikiPageResource.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _wikiPageResource.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_wikiPageResource.setUuid(uuid);
	}

	public long getResourcePrimKey() {
		return _wikiPageResource.getResourcePrimKey();
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPageResource.setResourcePrimKey(resourcePrimKey);
	}

	public long getNodeId() {
		return _wikiPageResource.getNodeId();
	}

	public void setNodeId(long nodeId) {
		_wikiPageResource.setNodeId(nodeId);
	}

	public java.lang.String getTitle() {
		return _wikiPageResource.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_wikiPageResource.setTitle(title);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource toEscapedModel() {
		return _wikiPageResource.toEscapedModel();
	}

	public boolean isNew() {
		return _wikiPageResource.isNew();
	}

	public void setNew(boolean n) {
		_wikiPageResource.setNew(n);
	}

	public boolean isCachedModel() {
		return _wikiPageResource.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_wikiPageResource.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _wikiPageResource.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_wikiPageResource.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiPageResource.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiPageResource.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiPageResource.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _wikiPageResource.clone();
	}

	public int compareTo(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource) {
		return _wikiPageResource.compareTo(wikiPageResource);
	}

	public int hashCode() {
		return _wikiPageResource.hashCode();
	}

	public java.lang.String toString() {
		return _wikiPageResource.toString();
	}

	public java.lang.String toXmlString() {
		return _wikiPageResource.toXmlString();
	}

	public WikiPageResource getWrappedWikiPageResource() {
		return _wikiPageResource;
	}

	private WikiPageResource _wikiPageResource;
}