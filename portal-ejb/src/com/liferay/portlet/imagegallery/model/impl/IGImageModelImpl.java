/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="IGImageModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>IGImage</code> table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.imagegallery.service.model.IGImage
 * @see com.liferay.portlet.imagegallery.service.model.IGImageModel
 * @see com.liferay.portlet.imagegallery.service.model.impl.IGImageImpl
 *
 */
public class IGImageModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "IGImage";
	public static Object[][] TABLE_COLUMNS = {
			{ "imageId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "folderId", new Integer(Types.BIGINT) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "height", new Integer(Types.INTEGER) },
			{ "width", new Integer(Types.INTEGER) },
			{ "size_", new Integer(Types.INTEGER) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.imagegallery.model.IGImage"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.imagegallery.model.IGImage.description"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.imagegallery.model.IGImageModel"));

	public IGImageModelImpl() {
	}

	public long getPrimaryKey() {
		return _imageId;
	}

	public void setPrimaryKey(long pk) {
		setImageId(pk);
	}

	public long getImageId() {
		return _imageId;
	}

	public void setImageId(long imageId) {
		if (imageId != _imageId) {
			_imageId = imageId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
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

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		if (folderId != _folderId) {
			_folderId = folderId;
		}
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			if (!XSS_ALLOW_DESCRIPTION) {
				description = XSSUtil.strip(description);
			}

			_description = description;
		}
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		if (height != _height) {
			_height = height;
		}
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		if (width != _width) {
			_width = width;
		}
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		if (size != _size) {
			_size = size;
		}
	}

	public Object clone() {
		IGImageImpl clone = new IGImageImpl();
		clone.setImageId(getImageId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFolderId(getFolderId());
		clone.setDescription(getDescription());
		clone.setHeight(getHeight());
		clone.setWidth(getWidth());
		clone.setSize(getSize());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		IGImageImpl igImage = (IGImageImpl)obj;
		int value = 0;

		if (getImageId() < igImage.getImageId()) {
			value = -1;
		}
		else if (getImageId() > igImage.getImageId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		IGImageImpl igImage = null;

		try {
			igImage = (IGImageImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = igImage.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _imageId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private String _description;
	private int _height;
	private int _width;
	private int _size;
}