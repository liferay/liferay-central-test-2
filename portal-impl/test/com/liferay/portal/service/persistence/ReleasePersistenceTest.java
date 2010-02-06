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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchReleaseException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Release;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="ReleasePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReleasePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ReleasePersistence)PortalBeanLocatorUtil.locate(ReleasePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Release release = _persistence.create(pk);

		assertNotNull(release);

		assertEquals(release.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Release newRelease = addRelease();

		_persistence.remove(newRelease);

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		assertNull(existingRelease);
	}

	public void testUpdateNew() throws Exception {
		addRelease();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Release newRelease = _persistence.create(pk);

		newRelease.setCreateDate(nextDate());
		newRelease.setModifiedDate(nextDate());
		newRelease.setServletContextName(randomString());
		newRelease.setBuildNumber(nextInt());
		newRelease.setBuildDate(nextDate());
		newRelease.setVerified(randomBoolean());
		newRelease.setTestString(randomString());

		_persistence.update(newRelease, false);

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		assertEquals(existingRelease.getReleaseId(), newRelease.getReleaseId());
		assertEquals(Time.getShortTimestamp(existingRelease.getCreateDate()),
			Time.getShortTimestamp(newRelease.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingRelease.getModifiedDate()),
			Time.getShortTimestamp(newRelease.getModifiedDate()));
		assertEquals(existingRelease.getServletContextName(),
			newRelease.getServletContextName());
		assertEquals(existingRelease.getBuildNumber(),
			newRelease.getBuildNumber());
		assertEquals(Time.getShortTimestamp(existingRelease.getBuildDate()),
			Time.getShortTimestamp(newRelease.getBuildDate()));
		assertEquals(existingRelease.getVerified(), newRelease.getVerified());
		assertEquals(existingRelease.getTestString(), newRelease.getTestString());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		assertEquals(existingRelease, newRelease);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchReleaseException");
		}
		catch (NoSuchReleaseException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		assertEquals(existingRelease, newRelease);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Release missingRelease = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingRelease);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Release newRelease = addRelease();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId",
				newRelease.getReleaseId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Release existingRelease = (Release)result.get(0);

		assertEquals(existingRelease, newRelease);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Release addRelease() throws Exception {
		long pk = nextLong();

		Release release = _persistence.create(pk);

		release.setCreateDate(nextDate());
		release.setModifiedDate(nextDate());
		release.setServletContextName(randomString());
		release.setBuildNumber(nextInt());
		release.setBuildDate(nextDate());
		release.setVerified(randomBoolean());
		release.setTestString(randomString());

		_persistence.update(release, false);

		return release;
	}

	private ReleasePersistence _persistence;
}