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

import com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;
import com.liferay.portlet.announcements.model.AnnouncementFlag;

/**
 * <a href="AnnouncementFlagPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementFlagPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementFlagPersistence)BeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementFlag announcementFlag = _persistence.create(pk);

		assertNotNull(announcementFlag);

		assertEquals(announcementFlag.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementFlag newAnnouncementFlag = addAnnouncementFlag();

		_persistence.remove(newAnnouncementFlag);

		AnnouncementFlag existingAnnouncementFlag = _persistence.fetchByPrimaryKey(newAnnouncementFlag.getPrimaryKey());

		assertNull(existingAnnouncementFlag);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementFlag();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementFlag newAnnouncementFlag = _persistence.create(pk);

		newAnnouncementFlag.setUserId(nextLong());
		newAnnouncementFlag.setEntryId(nextLong());
		newAnnouncementFlag.setFlag(nextInt());
		newAnnouncementFlag.setFlagDate(nextDate());

		_persistence.update(newAnnouncementFlag);

		AnnouncementFlag existingAnnouncementFlag = _persistence.findByPrimaryKey(newAnnouncementFlag.getPrimaryKey());

		assertEquals(existingAnnouncementFlag.getFlagId(),
			newAnnouncementFlag.getFlagId());
		assertEquals(existingAnnouncementFlag.getUserId(),
			newAnnouncementFlag.getUserId());
		assertEquals(existingAnnouncementFlag.getEntryId(),
			newAnnouncementFlag.getEntryId());
		assertEquals(existingAnnouncementFlag.getFlag(),
			newAnnouncementFlag.getFlag());
		assertEquals(existingAnnouncementFlag.getFlagDate(),
			newAnnouncementFlag.getFlagDate());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementFlag newAnnouncementFlag = addAnnouncementFlag();

		AnnouncementFlag existingAnnouncementFlag = _persistence.findByPrimaryKey(newAnnouncementFlag.getPrimaryKey());

		assertEquals(existingAnnouncementFlag, newAnnouncementFlag);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchAnnouncementFlagException");
		}
		catch (NoSuchAnnouncementFlagException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementFlag newAnnouncementFlag = addAnnouncementFlag();

		AnnouncementFlag existingAnnouncementFlag = _persistence.fetchByPrimaryKey(newAnnouncementFlag.getPrimaryKey());

		assertEquals(existingAnnouncementFlag, newAnnouncementFlag);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementFlag missingAnnouncementFlag = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementFlag);
	}

	protected AnnouncementFlag addAnnouncementFlag() throws Exception {
		long pk = nextLong();

		AnnouncementFlag announcementFlag = _persistence.create(pk);

		announcementFlag.setUserId(nextLong());
		announcementFlag.setEntryId(nextLong());
		announcementFlag.setFlag(nextInt());
		announcementFlag.setFlagDate(nextDate());

		_persistence.update(announcementFlag);

		return announcementFlag;
	}

	private static final String _TX_IMPL = AnnouncementFlagPersistence.class.getName() +
		".transaction";
	private AnnouncementFlagPersistence _persistence;
}