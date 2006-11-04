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

package com.liferay.portlet.wiki.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.wiki.service.persistence.WikiPagePK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="WikiPageModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiPageModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portlet.wiki.model.WikiPage"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage"), XSS_ALLOW);
	public static boolean XSS_ALLOW_NODEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage.nodeId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TITLE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage.title"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CONTENT = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage.content"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_FORMAT = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.wiki.model.WikiPage.format"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.wiki.model.WikiPageModel"));

	public WikiPageModel() {
	}

	public WikiPagePK getPrimaryKey() {
		return new WikiPagePK(_nodeId, _title, _version);
	}

	public void setPrimaryKey(WikiPagePK pk) {
		setNodeId(pk.nodeId);
		setTitle(pk.title);
		setVersion(pk.version);
	}

	public String getNodeId() {
		return GetterUtil.getString(_nodeId);
	}

	public void setNodeId(String nodeId) {
		if (((nodeId == null) && (_nodeId != null)) ||
				((nodeId != null) && (_nodeId == null)) ||
				((nodeId != null) && (_nodeId != null) &&
				!nodeId.equals(_nodeId))) {
			if (!XSS_ALLOW_NODEID) {
				nodeId = XSSUtil.strip(nodeId);
			}

			_nodeId = nodeId;
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

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		if (version != _version) {
			_version = version;
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

	public String getContent() {
		return GetterUtil.getString(_content);
	}

	public void setContent(String content) {
		if (((content == null) && (_content != null)) ||
				((content != null) && (_content == null)) ||
				((content != null) && (_content != null) &&
				!content.equals(_content))) {
			if (!XSS_ALLOW_CONTENT) {
				content = XSSUtil.strip(content);
			}

			_content = content;
		}
	}

	public String getFormat() {
		return GetterUtil.getString(_format);
	}

	public void setFormat(String format) {
		if (((format == null) && (_format != null)) ||
				((format != null) && (_format == null)) ||
				((format != null) && (_format != null) &&
				!format.equals(_format))) {
			if (!XSS_ALLOW_FORMAT) {
				format = XSSUtil.strip(format);
			}

			_format = format;
		}
	}

	public boolean getHead() {
		return _head;
	}

	public boolean isHead() {
		return _head;
	}

	public void setHead(boolean head) {
		if (head != _head) {
			_head = head;
		}
	}

	public Object clone() {
		WikiPage clone = new WikiPage();
		clone.setNodeId(getNodeId());
		clone.setTitle(getTitle());
		clone.setVersion(getVersion());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setContent(getContent());
		clone.setFormat(getFormat());
		clone.setHead(getHead());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		WikiPage wikiPage = (WikiPage)obj;
		int value = 0;
		value = getNodeId().compareTo(wikiPage.getNodeId());

		if (value != 0) {
			return value;
		}

		value = getTitle().toLowerCase().compareTo(wikiPage.getTitle()
														   .toLowerCase());

		if (value != 0) {
			return value;
		}

		if (getVersion() < wikiPage.getVersion()) {
			value = -1;
		}
		else if (getVersion() > wikiPage.getVersion()) {
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

		WikiPage wikiPage = null;

		try {
			wikiPage = (WikiPage)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		WikiPagePK pk = wikiPage.getPrimaryKey();

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

	private String _nodeId;
	private String _title;
	private double _version;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private String _content;
	private String _format;
	private boolean _head;
}