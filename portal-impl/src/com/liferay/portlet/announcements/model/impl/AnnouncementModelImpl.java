/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.announcements.model.Announcement;

import com.liferay.util.Html;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="AnnouncementModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Announcement</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.model.Announcement
 * @see com.liferay.portlet.announcements.service.model.AnnouncementModel
 * @see com.liferay.portlet.announcements.service.model.impl.AnnouncementImpl
 *
 */
public class AnnouncementModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "Announcement";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "announcementId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) },
			

			{ "title", new Integer(Types.VARCHAR) },
			

			{ "content", new Integer(Types.VARCHAR) },
			

			{ "url", new Integer(Types.VARCHAR) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "displayDate", new Integer(Types.TIMESTAMP) },
			

			{ "expirationDate", new Integer(Types.TIMESTAMP) },
			

			{ "priority", new Integer(Types.INTEGER) },
			

			{ "alert", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table Announcement (uuid_ VARCHAR(75) null,announcementId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,title VARCHAR(75) null,content STRING null,url STRING null,type_ VARCHAR(75) null,displayDate DATE null,expirationDate DATE null,priority INTEGER,alert BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table Announcement";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.announcements.model.Announcement"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.announcements.model.Announcement"));

	public AnnouncementModelImpl() {
	}

	public long getPrimaryKey() {
		return _announcementId;
	}

	public void setPrimaryKey(long pk) {
		setAnnouncementId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_announcementId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		if ((uuid != null) && (uuid != _uuid)) {
			_uuid = uuid;
		}
	}

	public long getAnnouncementId() {
		return _announcementId;
	}

	public void setAnnouncementId(long announcementId) {
		if (announcementId != _announcementId) {
			_announcementId = announcementId;
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

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (classNameId != _classNameId) {
			_classNameId = classNameId;
		}
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (classPK != _classPK) {
			_classPK = classPK;
		}
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			_title = title;
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
			_content = content;
		}
	}

	public String getUrl() {
		return GetterUtil.getString(_url);
	}

	public void setUrl(String url) {
		if (((url == null) && (_url != null)) ||
				((url != null) && (_url == null)) ||
				((url != null) && (_url != null) && !url.equals(_url))) {
			_url = url;
		}
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			_type = type;
		}
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		if (((displayDate == null) && (_displayDate != null)) ||
				((displayDate != null) && (_displayDate == null)) ||
				((displayDate != null) && (_displayDate != null) &&
				!displayDate.equals(_displayDate))) {
			_displayDate = displayDate;
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

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		if (priority != _priority) {
			_priority = priority;
		}
	}

	public boolean getAlert() {
		return _alert;
	}

	public boolean isAlert() {
		return _alert;
	}

	public void setAlert(boolean alert) {
		if (alert != _alert) {
			_alert = alert;
		}
	}

	public Announcement toEscapedModel() {
		if (isEscapedModel()) {
			return (Announcement)this;
		}
		else {
			Announcement model = new AnnouncementImpl();

			model.setEscapedModel(true);

			model.setUuid(Html.escape(getUuid()));
			model.setAnnouncementId(getAnnouncementId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(Html.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());
			model.setTitle(Html.escape(getTitle()));
			model.setContent(Html.escape(getContent()));
			model.setUrl(Html.escape(getUrl()));
			model.setType(Html.escape(getType()));
			model.setDisplayDate(getDisplayDate());
			model.setExpirationDate(getExpirationDate());
			model.setPriority(getPriority());
			model.setAlert(getAlert());

			model = (Announcement)Proxy.newProxyInstance(Announcement.class.getClassLoader(),
					new Class[] { Announcement.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		AnnouncementImpl clone = new AnnouncementImpl();

		clone.setUuid(getUuid());
		clone.setAnnouncementId(getAnnouncementId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setTitle(getTitle());
		clone.setContent(getContent());
		clone.setUrl(getUrl());
		clone.setType(getType());
		clone.setDisplayDate(getDisplayDate());
		clone.setExpirationDate(getExpirationDate());
		clone.setPriority(getPriority());
		clone.setAlert(getAlert());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		AnnouncementImpl announcement = (AnnouncementImpl)obj;

		int value = 0;

		if (getPriority() < announcement.getPriority()) {
			value = -1;
		}
		else if (getPriority() > announcement.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getModifiedDate(),
				announcement.getModifiedDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		AnnouncementImpl announcement = null;

		try {
			announcement = (AnnouncementImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = announcement.getPrimaryKey();

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

	private String _uuid;
	private long _announcementId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _title;
	private String _content;
	private String _url;
	private String _type;
	private Date _displayDate;
	private Date _expirationDate;
	private int _priority;
	private boolean _alert;
}