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
 * This class is a wrapper for {@link Image}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Image
 * @generated
 */
public class ImageWrapper implements Image {
	public ImageWrapper(Image image) {
		_image = image;
	}

	public long getPrimaryKey() {
		return _image.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_image.setPrimaryKey(pk);
	}

	public long getImageId() {
		return _image.getImageId();
	}

	public void setImageId(long imageId) {
		_image.setImageId(imageId);
	}

	public java.util.Date getModifiedDate() {
		return _image.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_image.setModifiedDate(modifiedDate);
	}

	public java.lang.String getText() {
		return _image.getText();
	}

	public void setText(java.lang.String text) {
		_image.setText(text);
	}

	public java.lang.String getType() {
		return _image.getType();
	}

	public void setType(java.lang.String type) {
		_image.setType(type);
	}

	public int getHeight() {
		return _image.getHeight();
	}

	public void setHeight(int height) {
		_image.setHeight(height);
	}

	public int getWidth() {
		return _image.getWidth();
	}

	public void setWidth(int width) {
		_image.setWidth(width);
	}

	public int getSize() {
		return _image.getSize();
	}

	public void setSize(int size) {
		_image.setSize(size);
	}

	public com.liferay.portal.model.Image toEscapedModel() {
		return _image.toEscapedModel();
	}

	public boolean isNew() {
		return _image.isNew();
	}

	public void setNew(boolean n) {
		_image.setNew(n);
	}

	public boolean isCachedModel() {
		return _image.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_image.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _image.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_image.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _image.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _image.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_image.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _image.clone();
	}

	public int compareTo(com.liferay.portal.model.Image image) {
		return _image.compareTo(image);
	}

	public int hashCode() {
		return _image.hashCode();
	}

	public java.lang.String toString() {
		return _image.toString();
	}

	public java.lang.String toXmlString() {
		return _image.toXmlString();
	}

	public byte[] getTextObj() {
		return _image.getTextObj();
	}

	public void setTextObj(byte[] textObj) {
		_image.setTextObj(textObj);
	}

	public Image getWrappedImage() {
		return _image;
	}

	private Image _image;
}