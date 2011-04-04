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

package com.liferay.portlet.shorturl.model;

/**
 * <p>
 * This class is a wrapper for {@link ShortURL}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShortURL
 * @generated
 */
public class ShortURLWrapper implements ShortURL {
	public ShortURLWrapper(ShortURL shortURL) {
		_shortURL = shortURL;
	}

	public Class<?> getModelClass() {
		return ShortURL.class;
	}

	public String getModelClassName() {
		return ShortURL.class.getName();
	}

	/**
	* Gets the primary key of this short u r l.
	*
	* @return the primary key of this short u r l
	*/
	public long getPrimaryKey() {
		return _shortURL.getPrimaryKey();
	}

	/**
	* Sets the primary key of this short u r l
	*
	* @param pk the primary key of this short u r l
	*/
	public void setPrimaryKey(long pk) {
		_shortURL.setPrimaryKey(pk);
	}

	/**
	* Gets the short u r l ID of this short u r l.
	*
	* @return the short u r l ID of this short u r l
	*/
	public long getShortURLId() {
		return _shortURL.getShortURLId();
	}

	/**
	* Sets the short u r l ID of this short u r l.
	*
	* @param shortURLId the short u r l ID of this short u r l
	*/
	public void setShortURLId(long shortURLId) {
		_shortURL.setShortURLId(shortURLId);
	}

	/**
	* Gets the create date of this short u r l.
	*
	* @return the create date of this short u r l
	*/
	public java.util.Date getCreateDate() {
		return _shortURL.getCreateDate();
	}

	/**
	* Sets the create date of this short u r l.
	*
	* @param createDate the create date of this short u r l
	*/
	public void setCreateDate(java.util.Date createDate) {
		_shortURL.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this short u r l.
	*
	* @return the modified date of this short u r l
	*/
	public java.util.Date getModifiedDate() {
		return _shortURL.getModifiedDate();
	}

	/**
	* Sets the modified date of this short u r l.
	*
	* @param modifiedDate the modified date of this short u r l
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_shortURL.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the original u r l of this short u r l.
	*
	* @return the original u r l of this short u r l
	*/
	public java.lang.String getOriginalURL() {
		return _shortURL.getOriginalURL();
	}

	/**
	* Sets the original u r l of this short u r l.
	*
	* @param originalURL the original u r l of this short u r l
	*/
	public void setOriginalURL(java.lang.String originalURL) {
		_shortURL.setOriginalURL(originalURL);
	}

	/**
	* Gets the hash of this short u r l.
	*
	* @return the hash of this short u r l
	*/
	public java.lang.String getHash() {
		return _shortURL.getHash();
	}

	/**
	* Sets the hash of this short u r l.
	*
	* @param hash the hash of this short u r l
	*/
	public void setHash(java.lang.String hash) {
		_shortURL.setHash(hash);
	}

	/**
	* Gets the descriptor of this short u r l.
	*
	* @return the descriptor of this short u r l
	*/
	public java.lang.String getDescriptor() {
		return _shortURL.getDescriptor();
	}

	/**
	* Sets the descriptor of this short u r l.
	*
	* @param descriptor the descriptor of this short u r l
	*/
	public void setDescriptor(java.lang.String descriptor) {
		_shortURL.setDescriptor(descriptor);
	}

	public boolean isNew() {
		return _shortURL.isNew();
	}

	public void setNew(boolean n) {
		_shortURL.setNew(n);
	}

	public boolean isCachedModel() {
		return _shortURL.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shortURL.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shortURL.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shortURL.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shortURL.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shortURL.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shortURL.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new ShortURLWrapper((ShortURL)_shortURL.clone());
	}

	public int compareTo(com.liferay.portlet.shorturl.model.ShortURL shortURL) {
		return _shortURL.compareTo(shortURL);
	}

	public int hashCode() {
		return _shortURL.hashCode();
	}

	public com.liferay.portlet.shorturl.model.ShortURL toEscapedModel() {
		return new ShortURLWrapper(_shortURL.toEscapedModel());
	}

	public java.lang.String toString() {
		return _shortURL.toString();
	}

	public java.lang.String toXmlString() {
		return _shortURL.toXmlString();
	}

	public java.lang.String getURL() {
		return _shortURL.getURL();
	}

	public ShortURL getWrappedShortURL() {
		return _shortURL;
	}

	public void resetOriginalValues() {
		_shortURL.resetOriginalValues();
	}

	private ShortURL _shortURL;
}