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

import com.liferay.portlet.announcements.NoSuchAnnouncementException;
import com.liferay.portlet.announcements.model.Announcement;

/**
 * <a href="AnnouncementPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementPersistence)BeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Announcement announcement = _persistence.create(pk);

		assertNotNull(announcement);

		assertEquals(announcement.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Announcement newAnnouncement = addAnnouncement();

		_persistence.remove(newAnnouncement);

		Announcement existingAnnouncement = _persistence.fetchByPrimaryKey(newAnnouncement.getPrimaryKey());

		assertNull(existingAnnouncement);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncement();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Announcement newAnnouncement = _persistence.create(pk);

		newAnnouncement.setUuid(randomString());
		newAnnouncement.setCompanyId(nextLong());
		newAnnouncement.setUserId(nextLong());
		newAnnouncement.setUserName(randomString());
		newAnnouncement.setCreateDate(nextDate());
		newAnnouncement.setModifiedDate(nextDate());
		newAnnouncement.setClassNameId(nextLong());
		newAnnouncement.setClassPK(nextLong());
		newAnnouncement.setTitle(randomString());
		newAnnouncement.setContent(randomString());
		newAnnouncement.setUrl(randomString());
		newAnnouncement.setType(randomString());
		newAnnouncement.setDisplayDate(nextDate());
		newAnnouncement.setExpirationDate(nextDate());
		newAnnouncement.setPriority(nextInt());
		newAnnouncement.setAlert(randomBoolean());

		_persistence.update(newAnnouncement);

		Announcement existingAnnouncement = _persistence.findByPrimaryKey(newAnnouncement.getPrimaryKey());

		assertEquals(existingAnnouncement.getUuid(), newAnnouncement.getUuid());
		assertEquals(existingAnnouncement.getAnnouncementId(),
			newAnnouncement.getAnnouncementId());
		assertEquals(existingAnnouncement.getCompanyId(),
			newAnnouncement.getCompanyId());
		assertEquals(existingAnnouncement.getUserId(),
			newAnnouncement.getUserId());
		assertEquals(existingAnnouncement.getUserName(),
			newAnnouncement.getUserName());
		assertEquals(existingAnnouncement.getCreateDate(),
			newAnnouncement.getCreateDate());
		assertEquals(existingAnnouncement.getModifiedDate(),
			newAnnouncement.getModifiedDate());
		assertEquals(existingAnnouncement.getClassNameId(),
			newAnnouncement.getClassNameId());
		assertEquals(existingAnnouncement.getClassPK(),
			newAnnouncement.getClassPK());
		assertEquals(existingAnnouncement.getTitle(), newAnnouncement.getTitle());
		assertEquals(existingAnnouncement.getContent(),
			newAnnouncement.getContent());
		assertEquals(existingAnnouncement.getUrl(), newAnnouncement.getUrl());
		assertEquals(existingAnnouncement.getType(), newAnnouncement.getType());
		assertEquals(existingAnnouncement.getDisplayDate(),
			newAnnouncement.getDisplayDate());
		assertEquals(existingAnnouncement.getExpirationDate(),
			newAnnouncement.getExpirationDate());
		assertEquals(existingAnnouncement.getPriority(),
			newAnnouncement.getPriority());
		assertEquals(existingAnnouncement.getAlert(), newAnnouncement.getAlert());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Announcement newAnnouncement = addAnnouncement();

		Announcement existingAnnouncement = _persistence.findByPrimaryKey(newAnnouncement.getPrimaryKey());

		assertEquals(existingAnnouncement, newAnnouncement);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchAnnouncementException");
		}
		catch (NoSuchAnnouncementException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Announcement newAnnouncement = addAnnouncement();

		Announcement existingAnnouncement = _persistence.fetchByPrimaryKey(newAnnouncement.getPrimaryKey());

		assertEquals(existingAnnouncement, newAnnouncement);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Announcement missingAnnouncement = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncement);
	}

	protected Announcement addAnnouncement() throws Exception {
		long pk = nextLong();

		Announcement announcement = _persistence.create(pk);

		announcement.setUuid(randomString());
		announcement.setCompanyId(nextLong());
		announcement.setUserId(nextLong());
		announcement.setUserName(randomString());
		announcement.setCreateDate(nextDate());
		announcement.setModifiedDate(nextDate());
		announcement.setClassNameId(nextLong());
		announcement.setClassPK(nextLong());
		announcement.setTitle(randomString());
		announcement.setContent(randomString());
		announcement.setUrl(randomString());
		announcement.setType(randomString());
		announcement.setDisplayDate(nextDate());
		announcement.setExpirationDate(nextDate());
		announcement.setPriority(nextInt());
		announcement.setAlert(randomBoolean());

		_persistence.update(announcement);

		return announcement;
	}

	private static final String _TX_IMPL = AnnouncementPersistence.class.getName() +
		".transaction";
	private AnnouncementPersistence _persistence;
}