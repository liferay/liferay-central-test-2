/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.calendar.model.CalEvent;

import java.util.List;

/**
 * <a href="CalEventUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventPersistence
 * @see       CalEventPersistenceImpl
 * @generated
 */
public class CalEventUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static CalEvent remove(CalEvent calEvent) throws SystemException {
		return getPersistence().remove(calEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static CalEvent update(CalEvent calEvent, boolean merge)
		throws SystemException {
		return getPersistence().update(calEvent, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.calendar.model.CalEvent calEvent) {
		getPersistence().cacheResult(calEvent);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> calEvents) {
		getPersistence().cacheResult(calEvents);
	}

	public static com.liferay.portlet.calendar.model.CalEvent create(
		long eventId) {
		return getPersistence().create(eventId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent remove(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().remove(eventId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent updateImpl(
		com.liferay.portlet.calendar.model.CalEvent calEvent, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(calEvent, merge);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByPrimaryKey(eventId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent fetchByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(eventId);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByUuid_PrevAndNext(
		long eventId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUuid_PrevAndNext(eventId, uuid, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByCompanyId_PrevAndNext(
		long eventId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(eventId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByGroupId_PrevAndNext(
		long eventId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByGroupId_PrevAndNext(eventId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByRemindBy(
		int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRemindBy(remindBy);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByRemindBy(
		int remindBy, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRemindBy(remindBy, start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByRemindBy(
		int remindBy, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRemindBy(remindBy, start, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByRemindBy_First(
		int remindBy, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByRemindBy_First(remindBy, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByRemindBy_Last(
		int remindBy, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByRemindBy_Last(remindBy, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByRemindBy_PrevAndNext(
		long eventId, int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByRemindBy_PrevAndNext(eventId, remindBy, obc);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, type);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, type, start, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_T_First(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_T_First(groupId, type, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_T_Last(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_T_Last(groupId, type, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByG_T_PrevAndNext(
		long eventId, long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_T_PrevAndNext(eventId, groupId, type, obc);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, repeating);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, repeating, start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_R(groupId, repeating, start, end, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_R_First(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_R_First(groupId, repeating, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent findByG_R_Last(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence().findByG_R_Last(groupId, repeating, obc);
	}

	public static com.liferay.portlet.calendar.model.CalEvent[] findByG_R_PrevAndNext(
		long eventId, long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		return getPersistence()
				   .findByG_R_PrevAndNext(eventId, groupId, repeating, obc);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRemindBy(remindBy);
	}

	public static void removeByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_T(groupId, type);
	}

	public static void removeByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_R(groupId, repeating);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRemindBy(remindBy);
	}

	public static int countByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_T(groupId, type);
	}

	public static int countByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_R(groupId, repeating);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CalEventPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CalEventPersistence)PortalBeanLocatorUtil.locate(CalEventPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(CalEventPersistence persistence) {
		_persistence = persistence;
	}

	private static CalEventPersistence _persistence;
}