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

import com.liferay.portlet.announcements.model.AnnouncementFlag;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="AnnouncementFlagModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>AnnouncementFlag</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.model.AnnouncementFlag
 * @see com.liferay.portlet.announcements.service.model.AnnouncementFlagModel
 * @see com.liferay.portlet.announcements.service.model.impl.AnnouncementFlagImpl
 *
 */
public class AnnouncementFlagModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "AnnouncementFlag";
	public static final Object[][] TABLE_COLUMNS = {
			{ "announcementFlagId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "announcementId", new Integer(Types.BIGINT) },
			

			{ "flag", new Integer(Types.INTEGER) },
			

			{ "flagDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table AnnouncementFlag (announcementFlagId LONG not null primary key,userId LONG,announcementId LONG,flag INTEGER,flagDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table AnnouncementFlag";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.announcements.model.AnnouncementFlag"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.announcements.model.AnnouncementFlag"));

	public AnnouncementFlagModelImpl() {
	}

	public long getPrimaryKey() {
		return _announcementFlagId;
	}

	public void setPrimaryKey(long pk) {
		setAnnouncementFlagId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_announcementFlagId);
	}

	public long getAnnouncementFlagId() {
		return _announcementFlagId;
	}

	public void setAnnouncementFlagId(long announcementFlagId) {
		if (announcementFlagId != _announcementFlagId) {
			_announcementFlagId = announcementFlagId;
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

	public long getAnnouncementId() {
		return _announcementId;
	}

	public void setAnnouncementId(long announcementId) {
		if (announcementId != _announcementId) {
			_announcementId = announcementId;
		}
	}

	public int getFlag() {
		return _flag;
	}

	public void setFlag(int flag) {
		if (flag != _flag) {
			_flag = flag;
		}
	}

	public Date getFlagDate() {
		return _flagDate;
	}

	public void setFlagDate(Date flagDate) {
		if (((flagDate == null) && (_flagDate != null)) ||
				((flagDate != null) && (_flagDate == null)) ||
				((flagDate != null) && (_flagDate != null) &&
				!flagDate.equals(_flagDate))) {
			_flagDate = flagDate;
		}
	}

	public AnnouncementFlag toEscapedModel() {
		if (isEscapedModel()) {
			return (AnnouncementFlag)this;
		}
		else {
			AnnouncementFlag model = new AnnouncementFlagImpl();

			model.setEscapedModel(true);

			model.setAnnouncementFlagId(getAnnouncementFlagId());
			model.setUserId(getUserId());
			model.setAnnouncementId(getAnnouncementId());
			model.setFlag(getFlag());
			model.setFlagDate(getFlagDate());

			model = (AnnouncementFlag)Proxy.newProxyInstance(AnnouncementFlag.class.getClassLoader(),
					new Class[] { AnnouncementFlag.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		AnnouncementFlagImpl clone = new AnnouncementFlagImpl();

		clone.setAnnouncementFlagId(getAnnouncementFlagId());
		clone.setUserId(getUserId());
		clone.setAnnouncementId(getAnnouncementId());
		clone.setFlag(getFlag());
		clone.setFlagDate(getFlagDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		AnnouncementFlagImpl announcementFlag = (AnnouncementFlagImpl)obj;

		int value = 0;

		if (getUserId() < announcementFlag.getUserId()) {
			value = -1;
		}
		else if (getUserId() > announcementFlag.getUserId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getFlagDate(), announcementFlag.getFlagDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		AnnouncementFlagImpl announcementFlag = null;

		try {
			announcementFlag = (AnnouncementFlagImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = announcementFlag.getPrimaryKey();

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

	private long _announcementFlagId;
	private long _userId;
	private long _announcementId;
	private int _flag;
	private Date _flagDate;
}