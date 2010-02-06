/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model;


/**
 * <a href="ImageSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public boolean setNew(boolean n) {
		return _image.setNew(n);
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