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

package com.liferay.portlet.tags.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="TagsAssetModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "TagsAsset";
	public static Object[][] TABLE_COLUMNS = {
			{ "assetId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "userId", new Integer(Types.VARCHAR) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.VARCHAR) },
			{ "modifiedDate", new Integer(Types.VARCHAR) },
			{ "className", new Integer(Types.VARCHAR) },
			{ "classPK", new Integer(Types.VARCHAR) },
			{ "startDate", new Integer(Types.VARCHAR) },
			{ "endDate", new Integer(Types.VARCHAR) },
			{ "publishDate", new Integer(Types.VARCHAR) },
			{ "expirationDate", new Integer(Types.VARCHAR) },
			{ "mimeType", new Integer(Types.VARCHAR) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "url", new Integer(Types.VARCHAR) },
			{ "height", new Integer(Types.INTEGER) },
			{ "width", new Integer(Types.INTEGER) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset"), XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_MIMETYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.mimeType"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TITLE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.title"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_URL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsAsset.url"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.tags.model.TagsAssetModel"));

	public TagsAssetModelImpl() {
	}

	public long getPrimaryKey() {
		return _assetId;
	}

	public void setPrimaryKey(long pk) {
		setAssetId(pk);
	}

	public long getAssetId() {
		return _assetId;
	}

	public void setAssetId(long assetId) {
		if (assetId != _assetId) {
			_assetId = assetId;
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
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

	public String getClassName() {
		return GetterUtil.getString(_className);
	}

	public void setClassName(String className) {
		if (((className == null) && (_className != null)) ||
				((className != null) && (_className == null)) ||
				((className != null) && (_className != null) &&
				!className.equals(_className))) {
			if (!XSS_ALLOW_CLASSNAME) {
				className = XSSUtil.strip(className);
			}

			_className = className;
		}
	}

	public String getClassPK() {
		return GetterUtil.getString(_classPK);
	}

	public void setClassPK(String classPK) {
		if (((classPK == null) && (_classPK != null)) ||
				((classPK != null) && (_classPK == null)) ||
				((classPK != null) && (_classPK != null) &&
				!classPK.equals(_classPK))) {
			if (!XSS_ALLOW_CLASSPK) {
				classPK = XSSUtil.strip(classPK);
			}

			_classPK = classPK;
		}
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		if (((startDate == null) && (_startDate != null)) ||
				((startDate != null) && (_startDate == null)) ||
				((startDate != null) && (_startDate != null) &&
				!startDate.equals(_startDate))) {
			_startDate = startDate;
		}
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		if (((endDate == null) && (_endDate != null)) ||
				((endDate != null) && (_endDate == null)) ||
				((endDate != null) && (_endDate != null) &&
				!endDate.equals(_endDate))) {
			_endDate = endDate;
		}
	}

	public Date getPublishDate() {
		return _publishDate;
	}

	public void setPublishDate(Date publishDate) {
		if (((publishDate == null) && (_publishDate != null)) ||
				((publishDate != null) && (_publishDate == null)) ||
				((publishDate != null) && (_publishDate != null) &&
				!publishDate.equals(_publishDate))) {
			_publishDate = publishDate;
		}
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		if (((expirationDate == null) && (_expirationDate != null)) ||
				((expirationDate != null) && (_expirationDate == null)) ||
				((expirationDate != null) && (_expirationDate != null) &&
				!expirationDate.equals(_expirationDate))) {
			_expirationDate = expirationDate;
		}
	}

	public String getMimeType() {
		return GetterUtil.getString(_mimeType);
	}

	public void setMimeType(String mimeType) {
		if (((mimeType == null) && (_mimeType != null)) ||
				((mimeType != null) && (_mimeType == null)) ||
				((mimeType != null) && (_mimeType != null) &&
				!mimeType.equals(_mimeType))) {
			if (!XSS_ALLOW_MIMETYPE) {
				mimeType = XSSUtil.strip(mimeType);
			}

			_mimeType = mimeType;
		}
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			if (!XSS_ALLOW_TITLE) {
				title = XSSUtil.strip(title);
			}

			_title = title;
		}
	}

	public String getUrl() {
		return GetterUtil.getString(_url);
	}

	public void setUrl(String url) {
		if (((url == null) && (_url != null)) ||
				((url != null) && (_url == null)) ||
				((url != null) && (_url != null) && !url.equals(_url))) {
			if (!XSS_ALLOW_URL) {
				url = XSSUtil.strip(url);
			}

			_url = url;
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

	public Object clone() {
		TagsAssetImpl clone = new TagsAssetImpl();
		clone.setAssetId(getAssetId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());
		clone.setStartDate(getStartDate());
		clone.setEndDate(getEndDate());
		clone.setPublishDate(getPublishDate());
		clone.setExpirationDate(getExpirationDate());
		clone.setMimeType(getMimeType());
		clone.setTitle(getTitle());
		clone.setUrl(getUrl());
		clone.setHeight(getHeight());
		clone.setWidth(getWidth());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		TagsAssetImpl tagsAsset = (TagsAssetImpl)obj;
		long pk = tagsAsset.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		TagsAssetImpl tagsAsset = null;

		try {
			tagsAsset = (TagsAssetImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = tagsAsset.getPrimaryKey();

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

	private long _assetId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _className;
	private String _classPK;
	private Date _startDate;
	private Date _endDate;
	private Date _publishDate;
	private Date _expirationDate;
	private String _mimeType;
	private String _title;
	private String _url;
	private int _height;
	private int _width;
}