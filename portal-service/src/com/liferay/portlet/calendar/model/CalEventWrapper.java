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

package com.liferay.portlet.calendar.model;

/**
 * <p>
 * This class is a wrapper for {@link CalEvent}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEvent
 * @generated
 */
public class CalEventWrapper implements CalEvent {
	public CalEventWrapper(CalEvent calEvent) {
		_calEvent = calEvent;
	}

	public long getPrimaryKey() {
		return _calEvent.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_calEvent.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _calEvent.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_calEvent.setUuid(uuid);
	}

	public long getEventId() {
		return _calEvent.getEventId();
	}

	public void setEventId(long eventId) {
		_calEvent.setEventId(eventId);
	}

	public long getGroupId() {
		return _calEvent.getGroupId();
	}

	public void setGroupId(long groupId) {
		_calEvent.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _calEvent.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_calEvent.setCompanyId(companyId);
	}

	public long getUserId() {
		return _calEvent.getUserId();
	}

	public void setUserId(long userId) {
		_calEvent.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _calEvent.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_calEvent.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _calEvent.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_calEvent.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _calEvent.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_calEvent.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _calEvent.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_calEvent.setModifiedDate(modifiedDate);
	}

	public java.lang.String getTitle() {
		return _calEvent.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_calEvent.setTitle(title);
	}

	public java.lang.String getDescription() {
		return _calEvent.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_calEvent.setDescription(description);
	}

	public java.util.Date getStartDate() {
		return _calEvent.getStartDate();
	}

	public void setStartDate(java.util.Date startDate) {
		_calEvent.setStartDate(startDate);
	}

	public java.util.Date getEndDate() {
		return _calEvent.getEndDate();
	}

	public void setEndDate(java.util.Date endDate) {
		_calEvent.setEndDate(endDate);
	}

	public int getDurationHour() {
		return _calEvent.getDurationHour();
	}

	public void setDurationHour(int durationHour) {
		_calEvent.setDurationHour(durationHour);
	}

	public int getDurationMinute() {
		return _calEvent.getDurationMinute();
	}

	public void setDurationMinute(int durationMinute) {
		_calEvent.setDurationMinute(durationMinute);
	}

	public boolean getAllDay() {
		return _calEvent.getAllDay();
	}

	public boolean isAllDay() {
		return _calEvent.isAllDay();
	}

	public void setAllDay(boolean allDay) {
		_calEvent.setAllDay(allDay);
	}

	public boolean getTimeZoneSensitive() {
		return _calEvent.getTimeZoneSensitive();
	}

	public boolean isTimeZoneSensitive() {
		return _calEvent.isTimeZoneSensitive();
	}

	public void setTimeZoneSensitive(boolean timeZoneSensitive) {
		_calEvent.setTimeZoneSensitive(timeZoneSensitive);
	}

	public java.lang.String getType() {
		return _calEvent.getType();
	}

	public void setType(java.lang.String type) {
		_calEvent.setType(type);
	}

	public boolean getRepeating() {
		return _calEvent.getRepeating();
	}

	public boolean isRepeating() {
		return _calEvent.isRepeating();
	}

	public void setRepeating(boolean repeating) {
		_calEvent.setRepeating(repeating);
	}

	public java.lang.String getRecurrence() {
		return _calEvent.getRecurrence();
	}

	public void setRecurrence(java.lang.String recurrence) {
		_calEvent.setRecurrence(recurrence);
	}

	public int getRemindBy() {
		return _calEvent.getRemindBy();
	}

	public void setRemindBy(int remindBy) {
		_calEvent.setRemindBy(remindBy);
	}

	public int getFirstReminder() {
		return _calEvent.getFirstReminder();
	}

	public void setFirstReminder(int firstReminder) {
		_calEvent.setFirstReminder(firstReminder);
	}

	public int getSecondReminder() {
		return _calEvent.getSecondReminder();
	}

	public void setSecondReminder(int secondReminder) {
		_calEvent.setSecondReminder(secondReminder);
	}

	public com.liferay.portlet.calendar.model.CalEvent toEscapedModel() {
		return _calEvent.toEscapedModel();
	}

	public boolean isNew() {
		return _calEvent.isNew();
	}

	public void setNew(boolean n) {
		_calEvent.setNew(n);
	}

	public boolean isCachedModel() {
		return _calEvent.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_calEvent.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _calEvent.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_calEvent.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _calEvent.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _calEvent.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_calEvent.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _calEvent.clone();
	}

	public int compareTo(com.liferay.portlet.calendar.model.CalEvent calEvent) {
		return _calEvent.compareTo(calEvent);
	}

	public int hashCode() {
		return _calEvent.hashCode();
	}

	public java.lang.String toString() {
		return _calEvent.toString();
	}

	public java.lang.String toXmlString() {
		return _calEvent.toXmlString();
	}

	public com.liferay.portal.kernel.cal.TZSRecurrence getRecurrenceObj() {
		return _calEvent.getRecurrenceObj();
	}

	public void setRecurrenceObj(
		com.liferay.portal.kernel.cal.TZSRecurrence recurrenceObj) {
		_calEvent.setRecurrenceObj(recurrenceObj);
	}

	public CalEvent getWrappedCalEvent() {
		return _calEvent;
	}

	private CalEvent _calEvent;
}