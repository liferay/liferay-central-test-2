/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="ImageModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ImageModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portal.model.Image"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Image"), XSS_ALLOW);
	public static boolean XSS_ALLOW_IMAGEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Image.imageId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TEXT = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Image.text"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Image.type"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ImageModel"));

	public ImageModel() {
	}

	public String getPrimaryKey() {
		return _imageId;
	}

	public void setPrimaryKey(String pk) {
		setImageId(pk);
	}

	public String getImageId() {
		return GetterUtil.getString(_imageId);
	}

	public void setImageId(String imageId) {
		if (((imageId == null) && (_imageId != null)) ||
				((imageId != null) && (_imageId == null)) ||
				((imageId != null) && (_imageId != null) &&
				!imageId.equals(_imageId))) {
			if (!XSS_ALLOW_IMAGEID) {
				imageId = XSSUtil.strip(imageId);
			}

			_imageId = imageId;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getText() {
		return GetterUtil.getString(_text);
	}

	public void setText(String text) {
		if (((text == null) && (_text != null)) ||
				((text != null) && (_text == null)) ||
				((text != null) && (_text != null) && !text.equals(_text))) {
			if (!XSS_ALLOW_TEXT) {
				text = XSSUtil.strip(text);
			}

			_text = text;
		}
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			if (!XSS_ALLOW_TYPE) {
				type = XSSUtil.strip(type);
			}

			_type = type;
		}
	}

	public Object clone() {
		Image clone = new Image();
		clone.setImageId(getImageId());
		clone.setModifiedDate(getModifiedDate());
		clone.setText(getText());
		clone.setType(getType());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		Image image = (Image)obj;
		int value = 0;
		value = getImageId().compareTo(image.getImageId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Image image = null;

		try {
			image = (Image)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = image.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _imageId;
	private Date _modifiedDate;
	private String _text;
	private String _type;
}