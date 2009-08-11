/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */


package com.liferay.portlet.calendar.service;

import java.util.Calendar;
import java.util.List;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.calendar.model.CalEvent;

/**
 * <a href="ExternalCalEventService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ganesh Ram
 *
 */

public interface ExternalCalEventService {
	
	public CalEvent addEvent(
			String uuid, long userId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			int remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext,String extCalServerUrl,
			String extCalId,String extCalUserId,String extCalPassword,
			String extCalSessionId)
		throws PortalException, SystemException ;

	public void deleteEvent(
			String eventId,String extCalServerUrl,String extCalId,
			String extCalUserId,String extCalPassword,String extCalSessionId)
		throws PortalException, SystemException ;

	public CalEvent getEvent(
			String eventId,String extCalServerUrl,String extCalId,
			String extCalUserId,String extCalPassword,String extCalSessionId)
		throws SystemException ;

	public List<CalEvent> getEvents(long groupId, Calendar cal, String type,
			String extCalServerUrl,String extCalUserId,String extCalId,
			String extCalPassword,String extCalSessionId)
		throws SystemException;

	public int getEventsCount(
			long groupId, String type,String extCalServerUrl,String extCalId,
			String extCalUserId,String extCalPassword,String extCalSessionId)
		throws SystemException ;
	
	public CalEvent updateEvent(
			long userId, long eventId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int durationHour,
			int durationMinute, boolean allDay, boolean timeZoneSensitive,
			String type, boolean repeating, TZSRecurrence recurrence,
			int remindBy, int firstReminder, int secondReminder,
			ServiceContext serviceContext,String extCalEventId,
			String extCalServerUrl,String extCalId,String extCalUserId,
			String extCalPassword,String extCalSessionId)
		throws PortalException, SystemException;
	
}
