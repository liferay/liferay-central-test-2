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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;

import java.util.List;

/**
 * <a href="DLFileRankPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFileRankPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFileRankPersistence)PortalBeanLocatorUtil.locate(DLFileRankPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFileRank dlFileRank = _persistence.create(pk);

		assertNotNull(dlFileRank);

		assertEquals(dlFileRank.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		_persistence.remove(newDLFileRank);

		DLFileRank existingDLFileRank = _persistence.fetchByPrimaryKey(newDLFileRank.getPrimaryKey());

		assertNull(existingDLFileRank);
	}

	public void testUpdateNew() throws Exception {
		addDLFileRank();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFileRank newDLFileRank = _persistence.create(pk);

		newDLFileRank.setGroupId(nextLong());
		newDLFileRank.setCompanyId(nextLong());
		newDLFileRank.setUserId(nextLong());
		newDLFileRank.setCreateDate(nextDate());
		newDLFileRank.setFolderId(nextLong());
		newDLFileRank.setName(randomString());

		_persistence.update(newDLFileRank, false);

		DLFileRank existingDLFileRank = _persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		assertEquals(existingDLFileRank.getFileRankId(),
			newDLFileRank.getFileRankId());
		assertEquals(existingDLFileRank.getGroupId(), newDLFileRank.getGroupId());
		assertEquals(existingDLFileRank.getCompanyId(),
			newDLFileRank.getCompanyId());
		assertEquals(existingDLFileRank.getUserId(), newDLFileRank.getUserId());
		assertEquals(Time.getShortTimestamp(existingDLFileRank.getCreateDate()),
			Time.getShortTimestamp(newDLFileRank.getCreateDate()));
		assertEquals(existingDLFileRank.getFolderId(),
			newDLFileRank.getFolderId());
		assertEquals(existingDLFileRank.getName(), newDLFileRank.getName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DLFileRank existingDLFileRank = _persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		assertEquals(existingDLFileRank, newDLFileRank);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFileRankException");
		}
		catch (NoSuchFileRankException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DLFileRank existingDLFileRank = _persistence.fetchByPrimaryKey(newDLFileRank.getPrimaryKey());

		assertEquals(existingDLFileRank, newDLFileRank);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFileRank missingDLFileRank = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFileRank);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileRankId",
				newDLFileRank.getFileRankId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFileRank existingDLFileRank = (DLFileRank)result.get(0);

		assertEquals(existingDLFileRank, newDLFileRank);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileRankId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DLFileRank addDLFileRank() throws Exception {
		long pk = nextLong();

		DLFileRank dlFileRank = _persistence.create(pk);

		dlFileRank.setGroupId(nextLong());
		dlFileRank.setCompanyId(nextLong());
		dlFileRank.setUserId(nextLong());
		dlFileRank.setCreateDate(nextDate());
		dlFileRank.setFolderId(nextLong());
		dlFileRank.setName(randomString());

		_persistence.update(dlFileRank, false);

		return dlFileRank;
	}

	private DLFileRankPersistence _persistence;
}