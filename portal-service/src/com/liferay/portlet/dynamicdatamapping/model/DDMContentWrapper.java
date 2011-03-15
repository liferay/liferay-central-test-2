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

package com.liferay.portlet.dynamicdatamapping.model;

/**
 * <p>
 * This class is a wrapper for {@link DDMContent}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMContent
 * @generated
 */
public class DDMContentWrapper implements DDMContent {
	public DDMContentWrapper(DDMContent ddmContent) {
		_ddmContent = ddmContent;
	}

	/**
	* Gets the primary key of this d d m content.
	*
	* @return the primary key of this d d m content
	*/
	public long getPrimaryKey() {
		return _ddmContent.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m content
	*
	* @param pk the primary key of this d d m content
	*/
	public void setPrimaryKey(long pk) {
		_ddmContent.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m content.
	*
	* @return the uuid of this d d m content
	*/
	public java.lang.String getUuid() {
		return _ddmContent.getUuid();
	}

	/**
	* Sets the uuid of this d d m content.
	*
	* @param uuid the uuid of this d d m content
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmContent.setUuid(uuid);
	}

	/**
	* Gets the content ID of this d d m content.
	*
	* @return the content ID of this d d m content
	*/
	public long getContentId() {
		return _ddmContent.getContentId();
	}

	/**
	* Sets the content ID of this d d m content.
	*
	* @param contentId the content ID of this d d m content
	*/
	public void setContentId(long contentId) {
		_ddmContent.setContentId(contentId);
	}

	public boolean isNew() {
		return _ddmContent.isNew();
	}

	public void setNew(boolean n) {
		_ddmContent.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmContent.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmContent.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmContent.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmContent.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmContent.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmContent.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmContent.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMContentWrapper((DDMContent)_ddmContent.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMContent ddmContent) {
		return _ddmContent.compareTo(ddmContent);
	}

	public int hashCode() {
		return _ddmContent.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMContent toEscapedModel() {
		return new DDMContentWrapper(_ddmContent.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmContent.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmContent.toXmlString();
	}

	public DDMContent getWrappedDDMContent() {
		return _ddmContent;
	}

	private DDMContent _ddmContent;
}