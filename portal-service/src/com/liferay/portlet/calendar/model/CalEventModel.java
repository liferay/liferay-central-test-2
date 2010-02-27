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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="CalEventModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the CalEvent table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEvent
 * @see       com.liferay.portlet.calendar.model.impl.CalEventImpl
 * @see       com.liferay.portlet.calendar.model.impl.CalEventModelImpl
 * @generated
 */
public interface CalEventModel extends BaseModel<CalEvent> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public String getUuid();

	public void setUuid(String uuid);

	public long getEventId();

	public void setEventId(long eventId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getTitle();

	public void setTitle(String title);

	public String getDescription();

	public void setDescription(String description);

	public Date getStartDate();

	public void setStartDate(Date startDate);

	public Date getEndDate();

	public void setEndDate(Date endDate);

	public int getDurationHour();

	public void setDurationHour(int durationHour);

	public int getDurationMinute();

	public void setDurationMinute(int durationMinute);

	public boolean getAllDay();

	public boolean isAllDay();

	public void setAllDay(boolean allDay);

	public boolean getTimeZoneSensitive();

	public boolean isTimeZoneSensitive();

	public void setTimeZoneSensitive(boolean timeZoneSensitive);

	public String getType();

	public void setType(String type);

	public boolean getRepeating();

	public boolean isRepeating();

	public void setRepeating(boolean repeating);

	public String getRecurrence();

	public void setRecurrence(String recurrence);

	public int getRemindBy();

	public void setRemindBy(int remindBy);

	public int getFirstReminder();

	public void setFirstReminder(int firstReminder);

	public int getSecondReminder();

	public void setSecondReminder(int secondReminder);

	public CalEvent toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(CalEvent calEvent);

	public int hashCode();

	public String toString();

	public String toXmlString();
}