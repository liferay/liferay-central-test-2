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

import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;

import java.util.List;

/**
 * <a href="DLFileVersionPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFileVersionPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFileVersionPersistence)PortalBeanLocatorUtil.locate(DLFileVersionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFileVersion dlFileVersion = _persistence.create(pk);

		assertNotNull(dlFileVersion);

		assertEquals(dlFileVersion.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFileVersion newDLFileVersion = addDLFileVersion();

		_persistence.remove(newDLFileVersion);

		DLFileVersion existingDLFileVersion = _persistence.fetchByPrimaryKey(newDLFileVersion.getPrimaryKey());

		assertNull(existingDLFileVersion);
	}

	public void testUpdateNew() throws Exception {
		addDLFileVersion();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFileVersion newDLFileVersion = _persistence.create(pk);

		newDLFileVersion.setGroupId(nextLong());
		newDLFileVersion.setCompanyId(nextLong());
		newDLFileVersion.setUserId(nextLong());
		newDLFileVersion.setUserName(randomString());
		newDLFileVersion.setCreateDate(nextDate());
		newDLFileVersion.setFolderId(nextLong());
		newDLFileVersion.setName(randomString());
		newDLFileVersion.setDescription(randomString());
		newDLFileVersion.setVersion(randomString());
		newDLFileVersion.setSize(nextInt());
		newDLFileVersion.setStatus(nextInt());
		newDLFileVersion.setStatusByUserId(nextLong());
		newDLFileVersion.setStatusByUserName(randomString());
		newDLFileVersion.setStatusDate(nextDate());

		_persistence.update(newDLFileVersion, false);

		DLFileVersion existingDLFileVersion = _persistence.findByPrimaryKey(newDLFileVersion.getPrimaryKey());

		assertEquals(existingDLFileVersion.getFileVersionId(),
			newDLFileVersion.getFileVersionId());
		assertEquals(existingDLFileVersion.getGroupId(),
			newDLFileVersion.getGroupId());
		assertEquals(existingDLFileVersion.getCompanyId(),
			newDLFileVersion.getCompanyId());
		assertEquals(existingDLFileVersion.getUserId(),
			newDLFileVersion.getUserId());
		assertEquals(existingDLFileVersion.getUserName(),
			newDLFileVersion.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingDLFileVersion.getCreateDate()),
			Time.getShortTimestamp(newDLFileVersion.getCreateDate()));
		assertEquals(existingDLFileVersion.getFolderId(),
			newDLFileVersion.getFolderId());
		assertEquals(existingDLFileVersion.getName(), newDLFileVersion.getName());
		assertEquals(existingDLFileVersion.getDescription(),
			newDLFileVersion.getDescription());
		assertEquals(existingDLFileVersion.getVersion(),
			newDLFileVersion.getVersion());
		assertEquals(existingDLFileVersion.getSize(), newDLFileVersion.getSize());
		assertEquals(existingDLFileVersion.getStatus(),
			newDLFileVersion.getStatus());
		assertEquals(existingDLFileVersion.getStatusByUserId(),
			newDLFileVersion.getStatusByUserId());
		assertEquals(existingDLFileVersion.getStatusByUserName(),
			newDLFileVersion.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(
				existingDLFileVersion.getStatusDate()),
			Time.getShortTimestamp(newDLFileVersion.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileVersion newDLFileVersion = addDLFileVersion();

		DLFileVersion existingDLFileVersion = _persistence.findByPrimaryKey(newDLFileVersion.getPrimaryKey());

		assertEquals(existingDLFileVersion, newDLFileVersion);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFileVersionException");
		}
		catch (NoSuchFileVersionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileVersion newDLFileVersion = addDLFileVersion();

		DLFileVersion existingDLFileVersion = _persistence.fetchByPrimaryKey(newDLFileVersion.getPrimaryKey());

		assertEquals(existingDLFileVersion, newDLFileVersion);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFileVersion missingDLFileVersion = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFileVersion);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileVersion newDLFileVersion = addDLFileVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileVersion.class,
				DLFileVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileVersionId",
				newDLFileVersion.getFileVersionId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFileVersion existingDLFileVersion = (DLFileVersion)result.get(0);

		assertEquals(existingDLFileVersion, newDLFileVersion);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileVersion.class,
				DLFileVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileVersionId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DLFileVersion addDLFileVersion() throws Exception {
		long pk = nextLong();

		DLFileVersion dlFileVersion = _persistence.create(pk);

		dlFileVersion.setGroupId(nextLong());
		dlFileVersion.setCompanyId(nextLong());
		dlFileVersion.setUserId(nextLong());
		dlFileVersion.setUserName(randomString());
		dlFileVersion.setCreateDate(nextDate());
		dlFileVersion.setFolderId(nextLong());
		dlFileVersion.setName(randomString());
		dlFileVersion.setDescription(randomString());
		dlFileVersion.setVersion(randomString());
		dlFileVersion.setSize(nextInt());
		dlFileVersion.setStatus(nextInt());
		dlFileVersion.setStatusByUserId(nextLong());
		dlFileVersion.setStatusByUserName(randomString());
		dlFileVersion.setStatusDate(nextDate());

		_persistence.update(dlFileVersion, false);

		return dlFileVersion;
	}

	private DLFileVersionPersistence _persistence;
}