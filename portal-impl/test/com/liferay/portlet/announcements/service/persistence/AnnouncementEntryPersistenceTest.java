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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.announcements.NoSuchAnnouncementEntryException;
import com.liferay.portlet.announcements.model.AnnouncementEntry;

/**
 * <a href="AnnouncementEntryPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementEntryPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementEntryPersistence)BeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementEntry announcementEntry = _persistence.create(pk);

		assertNotNull(announcementEntry);

		assertEquals(announcementEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementEntry newAnnouncementEntry = addAnnouncementEntry();

		_persistence.remove(newAnnouncementEntry);

		AnnouncementEntry existingAnnouncementEntry = _persistence.fetchByPrimaryKey(newAnnouncementEntry.getPrimaryKey());

		assertNull(existingAnnouncementEntry);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementEntry newAnnouncementEntry = _persistence.create(pk);

		newAnnouncementEntry.setUuid(randomString());
		newAnnouncementEntry.setCompanyId(nextLong());
		newAnnouncementEntry.setUserId(nextLong());
		newAnnouncementEntry.setUserName(randomString());
		newAnnouncementEntry.setCreateDate(nextDate());
		newAnnouncementEntry.setModifiedDate(nextDate());
		newAnnouncementEntry.setClassNameId(nextLong());
		newAnnouncementEntry.setClassPK(nextLong());
		newAnnouncementEntry.setTitle(randomString());
		newAnnouncementEntry.setContent(randomString());
		newAnnouncementEntry.setUrl(randomString());
		newAnnouncementEntry.setType(randomString());
		newAnnouncementEntry.setDisplayDate(nextDate());
		newAnnouncementEntry.setExpirationDate(nextDate());
		newAnnouncementEntry.setPriority(nextInt());
		newAnnouncementEntry.setAlert(randomBoolean());

		_persistence.update(newAnnouncementEntry);

		AnnouncementEntry existingAnnouncementEntry = _persistence.findByPrimaryKey(newAnnouncementEntry.getPrimaryKey());

		assertEquals(existingAnnouncementEntry.getUuid(),
			newAnnouncementEntry.getUuid());
		assertEquals(existingAnnouncementEntry.getEntryId(),
			newAnnouncementEntry.getEntryId());
		assertEquals(existingAnnouncementEntry.getCompanyId(),
			newAnnouncementEntry.getCompanyId());
		assertEquals(existingAnnouncementEntry.getUserId(),
			newAnnouncementEntry.getUserId());
		assertEquals(existingAnnouncementEntry.getUserName(),
			newAnnouncementEntry.getUserName());
		assertEquals(existingAnnouncementEntry.getCreateDate(),
			newAnnouncementEntry.getCreateDate());
		assertEquals(existingAnnouncementEntry.getModifiedDate(),
			newAnnouncementEntry.getModifiedDate());
		assertEquals(existingAnnouncementEntry.getClassNameId(),
			newAnnouncementEntry.getClassNameId());
		assertEquals(existingAnnouncementEntry.getClassPK(),
			newAnnouncementEntry.getClassPK());
		assertEquals(existingAnnouncementEntry.getTitle(),
			newAnnouncementEntry.getTitle());
		assertEquals(existingAnnouncementEntry.getContent(),
			newAnnouncementEntry.getContent());
		assertEquals(existingAnnouncementEntry.getUrl(),
			newAnnouncementEntry.getUrl());
		assertEquals(existingAnnouncementEntry.getType(),
			newAnnouncementEntry.getType());
		assertEquals(existingAnnouncementEntry.getDisplayDate(),
			newAnnouncementEntry.getDisplayDate());
		assertEquals(existingAnnouncementEntry.getExpirationDate(),
			newAnnouncementEntry.getExpirationDate());
		assertEquals(existingAnnouncementEntry.getPriority(),
			newAnnouncementEntry.getPriority());
		assertEquals(existingAnnouncementEntry.getAlert(),
			newAnnouncementEntry.getAlert());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementEntry newAnnouncementEntry = addAnnouncementEntry();

		AnnouncementEntry existingAnnouncementEntry = _persistence.findByPrimaryKey(newAnnouncementEntry.getPrimaryKey());

		assertEquals(existingAnnouncementEntry, newAnnouncementEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchAnnouncementEntryException");
		}
		catch (NoSuchAnnouncementEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementEntry newAnnouncementEntry = addAnnouncementEntry();

		AnnouncementEntry existingAnnouncementEntry = _persistence.fetchByPrimaryKey(newAnnouncementEntry.getPrimaryKey());

		assertEquals(existingAnnouncementEntry, newAnnouncementEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementEntry missingAnnouncementEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementEntry);
	}

	protected AnnouncementEntry addAnnouncementEntry()
		throws Exception {
		long pk = nextLong();

		AnnouncementEntry announcementEntry = _persistence.create(pk);

		announcementEntry.setUuid(randomString());
		announcementEntry.setCompanyId(nextLong());
		announcementEntry.setUserId(nextLong());
		announcementEntry.setUserName(randomString());
		announcementEntry.setCreateDate(nextDate());
		announcementEntry.setModifiedDate(nextDate());
		announcementEntry.setClassNameId(nextLong());
		announcementEntry.setClassPK(nextLong());
		announcementEntry.setTitle(randomString());
		announcementEntry.setContent(randomString());
		announcementEntry.setUrl(randomString());
		announcementEntry.setType(randomString());
		announcementEntry.setDisplayDate(nextDate());
		announcementEntry.setExpirationDate(nextDate());
		announcementEntry.setPriority(nextInt());
		announcementEntry.setAlert(randomBoolean());

		_persistence.update(announcementEntry);

		return announcementEntry;
	}

	private static final String _TX_IMPL = AnnouncementEntryPersistence.class.getName() +
		".transaction";
	private AnnouncementEntryPersistence _persistence;
}