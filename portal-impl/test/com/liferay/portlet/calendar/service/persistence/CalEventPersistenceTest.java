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
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.calendar.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;

import java.util.List;

/**
 * <a href="CalEventPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CalEventPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (CalEventPersistence)PortalBeanLocatorUtil.locate(CalEventPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		CalEvent calEvent = _persistence.create(pk);

		assertNotNull(calEvent);

		assertEquals(calEvent.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		_persistence.remove(newCalEvent);

		CalEvent existingCalEvent = _persistence.fetchByPrimaryKey(newCalEvent.getPrimaryKey());

		assertNull(existingCalEvent);
	}

	public void testUpdateNew() throws Exception {
		addCalEvent();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		CalEvent newCalEvent = _persistence.create(pk);

		newCalEvent.setUuid(randomString());
		newCalEvent.setGroupId(nextLong());
		newCalEvent.setCompanyId(nextLong());
		newCalEvent.setUserId(nextLong());
		newCalEvent.setUserName(randomString());
		newCalEvent.setCreateDate(nextDate());
		newCalEvent.setModifiedDate(nextDate());
		newCalEvent.setTitle(randomString());
		newCalEvent.setDescription(randomString());
		newCalEvent.setStartDate(nextDate());
		newCalEvent.setEndDate(nextDate());
		newCalEvent.setDurationHour(nextInt());
		newCalEvent.setDurationMinute(nextInt());
		newCalEvent.setAllDay(randomBoolean());
		newCalEvent.setTimeZoneSensitive(randomBoolean());
		newCalEvent.setType(randomString());
		newCalEvent.setRepeating(randomBoolean());
		newCalEvent.setRecurrence(randomString());
		newCalEvent.setRemindBy(nextInt());
		newCalEvent.setFirstReminder(nextInt());
		newCalEvent.setSecondReminder(nextInt());

		_persistence.update(newCalEvent, false);

		CalEvent existingCalEvent = _persistence.findByPrimaryKey(newCalEvent.getPrimaryKey());

		assertEquals(existingCalEvent.getUuid(), newCalEvent.getUuid());
		assertEquals(existingCalEvent.getEventId(), newCalEvent.getEventId());
		assertEquals(existingCalEvent.getGroupId(), newCalEvent.getGroupId());
		assertEquals(existingCalEvent.getCompanyId(), newCalEvent.getCompanyId());
		assertEquals(existingCalEvent.getUserId(), newCalEvent.getUserId());
		assertEquals(existingCalEvent.getUserName(), newCalEvent.getUserName());
		assertEquals(Time.getShortTimestamp(existingCalEvent.getCreateDate()),
			Time.getShortTimestamp(newCalEvent.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingCalEvent.getModifiedDate()),
			Time.getShortTimestamp(newCalEvent.getModifiedDate()));
		assertEquals(existingCalEvent.getTitle(), newCalEvent.getTitle());
		assertEquals(existingCalEvent.getDescription(),
			newCalEvent.getDescription());
		assertEquals(Time.getShortTimestamp(existingCalEvent.getStartDate()),
			Time.getShortTimestamp(newCalEvent.getStartDate()));
		assertEquals(Time.getShortTimestamp(existingCalEvent.getEndDate()),
			Time.getShortTimestamp(newCalEvent.getEndDate()));
		assertEquals(existingCalEvent.getDurationHour(),
			newCalEvent.getDurationHour());
		assertEquals(existingCalEvent.getDurationMinute(),
			newCalEvent.getDurationMinute());
		assertEquals(existingCalEvent.getAllDay(), newCalEvent.getAllDay());
		assertEquals(existingCalEvent.getTimeZoneSensitive(),
			newCalEvent.getTimeZoneSensitive());
		assertEquals(existingCalEvent.getType(), newCalEvent.getType());
		assertEquals(existingCalEvent.getRepeating(), newCalEvent.getRepeating());
		assertEquals(existingCalEvent.getRecurrence(),
			newCalEvent.getRecurrence());
		assertEquals(existingCalEvent.getRemindBy(), newCalEvent.getRemindBy());
		assertEquals(existingCalEvent.getFirstReminder(),
			newCalEvent.getFirstReminder());
		assertEquals(existingCalEvent.getSecondReminder(),
			newCalEvent.getSecondReminder());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		CalEvent existingCalEvent = _persistence.findByPrimaryKey(newCalEvent.getPrimaryKey());

		assertEquals(existingCalEvent, newCalEvent);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEventException");
		}
		catch (NoSuchEventException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		CalEvent existingCalEvent = _persistence.fetchByPrimaryKey(newCalEvent.getPrimaryKey());

		assertEquals(existingCalEvent, newCalEvent);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		CalEvent missingCalEvent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingCalEvent);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CalEvent newCalEvent = addCalEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				CalEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("eventId",
				newCalEvent.getEventId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		CalEvent existingCalEvent = (CalEvent)result.get(0);

		assertEquals(existingCalEvent, newCalEvent);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				CalEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("eventId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected CalEvent addCalEvent() throws Exception {
		long pk = nextLong();

		CalEvent calEvent = _persistence.create(pk);

		calEvent.setUuid(randomString());
		calEvent.setGroupId(nextLong());
		calEvent.setCompanyId(nextLong());
		calEvent.setUserId(nextLong());
		calEvent.setUserName(randomString());
		calEvent.setCreateDate(nextDate());
		calEvent.setModifiedDate(nextDate());
		calEvent.setTitle(randomString());
		calEvent.setDescription(randomString());
		calEvent.setStartDate(nextDate());
		calEvent.setEndDate(nextDate());
		calEvent.setDurationHour(nextInt());
		calEvent.setDurationMinute(nextInt());
		calEvent.setAllDay(randomBoolean());
		calEvent.setTimeZoneSensitive(randomBoolean());
		calEvent.setType(randomString());
		calEvent.setRepeating(randomBoolean());
		calEvent.setRecurrence(randomString());
		calEvent.setRemindBy(nextInt());
		calEvent.setFirstReminder(nextInt());
		calEvent.setSecondReminder(nextInt());

		_persistence.update(calEvent, false);

		return calEvent;
	}

	private CalEventPersistence _persistence;
}