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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.announcements.NoSuchEntryException;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;

import java.util.List;

/**
 * <a href="AnnouncementsEntryPersistenceTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsEntryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementsEntryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementsEntry announcementsEntry = _persistence.create(pk);

		assertNotNull(announcementsEntry);

		assertEquals(announcementsEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		_persistence.remove(newAnnouncementsEntry);

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.fetchByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertNull(existingAnnouncementsEntry);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementsEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementsEntry newAnnouncementsEntry = _persistence.create(pk);

		newAnnouncementsEntry.setUuid(randomString());
		newAnnouncementsEntry.setCompanyId(nextLong());
		newAnnouncementsEntry.setUserId(nextLong());
		newAnnouncementsEntry.setUserName(randomString());
		newAnnouncementsEntry.setCreateDate(nextDate());
		newAnnouncementsEntry.setModifiedDate(nextDate());
		newAnnouncementsEntry.setClassNameId(nextLong());
		newAnnouncementsEntry.setClassPK(nextLong());
		newAnnouncementsEntry.setTitle(randomString());
		newAnnouncementsEntry.setContent(randomString());
		newAnnouncementsEntry.setUrl(randomString());
		newAnnouncementsEntry.setType(randomString());
		newAnnouncementsEntry.setDisplayDate(nextDate());
		newAnnouncementsEntry.setExpirationDate(nextDate());
		newAnnouncementsEntry.setPriority(nextInt());
		newAnnouncementsEntry.setAlert(randomBoolean());

		_persistence.update(newAnnouncementsEntry, false);

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.findByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertEquals(existingAnnouncementsEntry.getUuid(),
			newAnnouncementsEntry.getUuid());
		assertEquals(existingAnnouncementsEntry.getEntryId(),
			newAnnouncementsEntry.getEntryId());
		assertEquals(existingAnnouncementsEntry.getCompanyId(),
			newAnnouncementsEntry.getCompanyId());
		assertEquals(existingAnnouncementsEntry.getUserId(),
			newAnnouncementsEntry.getUserId());
		assertEquals(existingAnnouncementsEntry.getUserName(),
			newAnnouncementsEntry.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getCreateDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getModifiedDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getModifiedDate()));
		assertEquals(existingAnnouncementsEntry.getClassNameId(),
			newAnnouncementsEntry.getClassNameId());
		assertEquals(existingAnnouncementsEntry.getClassPK(),
			newAnnouncementsEntry.getClassPK());
		assertEquals(existingAnnouncementsEntry.getTitle(),
			newAnnouncementsEntry.getTitle());
		assertEquals(existingAnnouncementsEntry.getContent(),
			newAnnouncementsEntry.getContent());
		assertEquals(existingAnnouncementsEntry.getUrl(),
			newAnnouncementsEntry.getUrl());
		assertEquals(existingAnnouncementsEntry.getType(),
			newAnnouncementsEntry.getType());
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getDisplayDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getDisplayDate()));
		assertEquals(Time.getShortTimestamp(
				existingAnnouncementsEntry.getExpirationDate()),
			Time.getShortTimestamp(newAnnouncementsEntry.getExpirationDate()));
		assertEquals(existingAnnouncementsEntry.getPriority(),
			newAnnouncementsEntry.getPriority());
		assertEquals(existingAnnouncementsEntry.getAlert(),
			newAnnouncementsEntry.getAlert());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.findByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertEquals(existingAnnouncementsEntry, newAnnouncementsEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		AnnouncementsEntry existingAnnouncementsEntry = _persistence.fetchByPrimaryKey(newAnnouncementsEntry.getPrimaryKey());

		assertEquals(existingAnnouncementsEntry, newAnnouncementsEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementsEntry missingAnnouncementsEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementsEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AnnouncementsEntry newAnnouncementsEntry = addAnnouncementsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsEntry.class,
				AnnouncementsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newAnnouncementsEntry.getEntryId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AnnouncementsEntry existingAnnouncementsEntry = (AnnouncementsEntry)result.get(0);

		assertEquals(existingAnnouncementsEntry, newAnnouncementsEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsEntry.class,
				AnnouncementsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected AnnouncementsEntry addAnnouncementsEntry()
		throws Exception {
		long pk = nextLong();

		AnnouncementsEntry announcementsEntry = _persistence.create(pk);

		announcementsEntry.setUuid(randomString());
		announcementsEntry.setCompanyId(nextLong());
		announcementsEntry.setUserId(nextLong());
		announcementsEntry.setUserName(randomString());
		announcementsEntry.setCreateDate(nextDate());
		announcementsEntry.setModifiedDate(nextDate());
		announcementsEntry.setClassNameId(nextLong());
		announcementsEntry.setClassPK(nextLong());
		announcementsEntry.setTitle(randomString());
		announcementsEntry.setContent(randomString());
		announcementsEntry.setUrl(randomString());
		announcementsEntry.setType(randomString());
		announcementsEntry.setDisplayDate(nextDate());
		announcementsEntry.setExpirationDate(nextDate());
		announcementsEntry.setPriority(nextInt());
		announcementsEntry.setAlert(randomBoolean());

		_persistence.update(announcementsEntry, false);

		return announcementsEntry;
	}

	private AnnouncementsEntryPersistence _persistence;
}