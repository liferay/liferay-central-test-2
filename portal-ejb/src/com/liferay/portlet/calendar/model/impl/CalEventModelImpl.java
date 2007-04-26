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

package com.liferay.portlet.calendar.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="CalEventModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>CalEvent</code> table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.calendar.service.model.CalEvent
 * @see com.liferay.portlet.calendar.service.model.CalEventModel
 * @see com.liferay.portlet.calendar.service.model.impl.CalEventImpl
 *
 */
public class CalEventModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "CalEvent";
	public static Object[][] TABLE_COLUMNS = {
			{ "eventId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "startDate", new Integer(Types.TIMESTAMP) },
			{ "endDate", new Integer(Types.TIMESTAMP) },
			{ "durationHour", new Integer(Types.INTEGER) },
			{ "durationMinute", new Integer(Types.INTEGER) },
			{ "allDay", new Integer(Types.BOOLEAN) },
			{ "timeZoneSensitive", new Integer(Types.BOOLEAN) },
			{ "type_", new Integer(Types.VARCHAR) },
			{ "repeating", new Integer(Types.BOOLEAN) },
			{ "recurrence", new Integer(Types.CLOB) },
			{ "remindBy", new Integer(Types.VARCHAR) },
			{ "firstReminder", new Integer(Types.INTEGER) },
			{ "secondReminder", new Integer(Types.INTEGER) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TITLE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent.title"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent.type"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_RECURRENCE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent.recurrence"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_REMINDBY = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.calendar.model.CalEvent.remindBy"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.calendar.model.CalEventModel"));

	public CalEventModelImpl() {
	}

	public long getPrimaryKey() {
		return _eventId;
	}

	public void setPrimaryKey(long pk) {
		setEventId(pk);
	}

	public long getEventId() {
		return _eventId;
	}

	public void setEventId(long eventId) {
		if (eventId != _eventId) {
			_eventId = eventId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
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

	public int getDurationHour() {
		return _durationHour;
	}

	public void setDurationHour(int durationHour) {
		if (durationHour != _durationHour) {
			_durationHour = durationHour;
		}
	}

	public int getDurationMinute() {
		return _durationMinute;
	}

	public void setDurationMinute(int durationMinute) {
		if (durationMinute != _durationMinute) {
			_durationMinute = durationMinute;
		}
	}

	public boolean getAllDay() {
		return _allDay;
	}

	public boolean isAllDay() {
		return _allDay;
	}

	public void setAllDay(boolean allDay) {
		if (allDay != _allDay) {
			_allDay = allDay;
		}
	}

	public boolean getTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	public boolean isTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	public void setTimeZoneSensitive(boolean timeZoneSensitive) {
		if (timeZoneSensitive != _timeZoneSensitive) {
			_timeZoneSensitive = timeZoneSensitive;
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

	public boolean getRepeating() {
		return _repeating;
	}

	public boolean isRepeating() {
		return _repeating;
	}

	public void setRepeating(boolean repeating) {
		if (repeating != _repeating) {
			_repeating = repeating;
		}
	}

	public String getRecurrence() {
		return GetterUtil.getString(_recurrence);
	}

	public void setRecurrence(String recurrence) {
		if (((recurrence == null) && (_recurrence != null)) ||
				((recurrence != null) && (_recurrence == null)) ||
				((recurrence != null) && (_recurrence != null) &&
				!recurrence.equals(_recurrence))) {
			if (!XSS_ALLOW_RECURRENCE) {
				recurrence = XSSUtil.strip(recurrence);
			}

			_recurrence = recurrence;
		}
	}

	public String getRemindBy() {
		return GetterUtil.getString(_remindBy);
	}

	public void setRemindBy(String remindBy) {
		if (((remindBy == null) && (_remindBy != null)) ||
				((remindBy != null) && (_remindBy == null)) ||
				((remindBy != null) && (_remindBy != null) &&
				!remindBy.equals(_remindBy))) {
			if (!XSS_ALLOW_REMINDBY) {
				remindBy = XSSUtil.strip(remindBy);
			}

			_remindBy = remindBy;
		}
	}

	public int getFirstReminder() {
		return _firstReminder;
	}

	public void setFirstReminder(int firstReminder) {
		if (firstReminder != _firstReminder) {
			_firstReminder = firstReminder;
		}
	}

	public int getSecondReminder() {
		return _secondReminder;
	}

	public void setSecondReminder(int secondReminder) {
		if (secondReminder != _secondReminder) {
			_secondReminder = secondReminder;
		}
	}

	public Object clone() {
		CalEventImpl clone = new CalEventImpl();
		clone.setEventId(getEventId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setStartDate(getStartDate());
		clone.setEndDate(getEndDate());
		clone.setDurationHour(getDurationHour());
		clone.setDurationMinute(getDurationMinute());
		clone.setAllDay(getAllDay());
		clone.setTimeZoneSensitive(getTimeZoneSensitive());
		clone.setType(getType());
		clone.setRepeating(getRepeating());
		clone.setRecurrence(getRecurrence());
		clone.setRemindBy(getRemindBy());
		clone.setFirstReminder(getFirstReminder());
		clone.setSecondReminder(getSecondReminder());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		CalEventImpl calEvent = (CalEventImpl)obj;
		int value = 0;
		value = DateUtil.compareTo(getStartDate(), calEvent.getStartDate());

		if (value != 0) {
			return value;
		}

		value = getTitle().toLowerCase().compareTo(calEvent.getTitle()
														   .toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		CalEventImpl calEvent = null;

		try {
			calEvent = (CalEventImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = calEvent.getPrimaryKey();

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

	private long _eventId;
	private long _groupId;
	private String _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private Date _startDate;
	private Date _endDate;
	private int _durationHour;
	private int _durationMinute;
	private boolean _allDay;
	private boolean _timeZoneSensitive;
	private String _type;
	private boolean _repeating;
	private String _recurrence;
	private String _remindBy;
	private int _firstReminder;
	private int _secondReminder;
}