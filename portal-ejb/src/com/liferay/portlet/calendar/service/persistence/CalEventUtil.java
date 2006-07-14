/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="CalEventUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CalEventUtil {
	public static final String CLASS_NAME = CalEventUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.calendar.model.CalEvent"));

	public static com.liferay.portlet.calendar.model.CalEvent create(
		java.lang.String eventId) {
		return getPersistence().create(eventId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent remove(
		java.lang.String eventId)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(eventId));
		}

		com.liferay.portlet.calendar.model.CalEvent calEvent = getPersistence()
																   .remove(eventId);

		if (listener != null) {
			listener.onAfterRemove(calEvent);
		}

		return calEvent;
	}

	public static com.liferay.portlet.calendar.model.CalEvent update(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = calEvent.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(calEvent);
			}
			else {
				listener.onBeforeUpdate(calEvent);
			}
		}

		calEvent = getPersistence().update(calEvent);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(calEvent);
			}
			else {
				listener.onAfterUpdate(calEvent);
			}
		}

		return calEvent;
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByPrimaryKey(
		java.lang.String eventId)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(eventId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByPrimaryKey(
		java.lang.String eventId, boolean throwNoSuchObjectException)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(eventId,
			throwNoSuchObjectException);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByGroupId_PrevAndNext(
		java.lang.String eventId, java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(eventId, groupId, obc);
	}

	public static java.util.List findByG_T(java.lang.String groupId,
		java.lang.String type) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_T(groupId, type);
	}

	public static java.util.List findByG_T(java.lang.String groupId,
		java.lang.String type, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_T(groupId, type, begin, end);
	}

	public static java.util.List findByG_T(java.lang.String groupId,
		java.lang.String type, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_T(groupId, type, begin, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_T_First(
		java.lang.String groupId, java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_T_First(groupId, type, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_T_Last(
		java.lang.String groupId, java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_T_Last(groupId, type, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByG_T_PrevAndNext(
		java.lang.String eventId, java.lang.String groupId,
		java.lang.String type,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_T_PrevAndNext(eventId, groupId, type,
			obc);
	}

	public static java.util.List findByG_R(java.lang.String groupId,
		boolean repeating) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_R(groupId, repeating);
	}

	public static java.util.List findByG_R(java.lang.String groupId,
		boolean repeating, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_R(groupId, repeating, begin, end);
	}

	public static java.util.List findByG_R(java.lang.String groupId,
		boolean repeating, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_R(groupId, repeating, begin, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_R_First(
		java.lang.String groupId, boolean repeating,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_R_First(groupId, repeating, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_R_Last(
		java.lang.String groupId, boolean repeating,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_R_Last(groupId, repeating, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByG_R_PrevAndNext(
		java.lang.String eventId, java.lang.String groupId, boolean repeating,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.calendar.NoSuchEventException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_R_PrevAndNext(eventId, groupId,
			repeating, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByG_T(java.lang.String groupId,
		java.lang.String type) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_T(groupId, type);
	}

	public static void removeByG_R(java.lang.String groupId, boolean repeating)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_R(groupId, repeating);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_T(java.lang.String groupId, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_T(groupId, type);
	}

	public static int countByG_R(java.lang.String groupId, boolean repeating)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_R(groupId, repeating);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static CalEventPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		CalEventUtil util = (CalEventUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(CalEventPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(CalEventUtil.class);
	private CalEventPersistence _persistence;
}