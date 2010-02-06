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

import com.liferay.portal.NoSuchUserTrackerException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="UserTrackerPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UserTrackerPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserTrackerPersistence)PortalBeanLocatorUtil.locate(UserTrackerPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		UserTracker userTracker = _persistence.create(pk);

		assertNotNull(userTracker);

		assertEquals(userTracker.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		_persistence.remove(newUserTracker);

		UserTracker existingUserTracker = _persistence.fetchByPrimaryKey(newUserTracker.getPrimaryKey());

		assertNull(existingUserTracker);
	}

	public void testUpdateNew() throws Exception {
		addUserTracker();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		UserTracker newUserTracker = _persistence.create(pk);

		newUserTracker.setCompanyId(nextLong());
		newUserTracker.setUserId(nextLong());
		newUserTracker.setModifiedDate(nextDate());
		newUserTracker.setSessionId(randomString());
		newUserTracker.setRemoteAddr(randomString());
		newUserTracker.setRemoteHost(randomString());
		newUserTracker.setUserAgent(randomString());

		_persistence.update(newUserTracker, false);

		UserTracker existingUserTracker = _persistence.findByPrimaryKey(newUserTracker.getPrimaryKey());

		assertEquals(existingUserTracker.getUserTrackerId(),
			newUserTracker.getUserTrackerId());
		assertEquals(existingUserTracker.getCompanyId(),
			newUserTracker.getCompanyId());
		assertEquals(existingUserTracker.getUserId(), newUserTracker.getUserId());
		assertEquals(Time.getShortTimestamp(
				existingUserTracker.getModifiedDate()),
			Time.getShortTimestamp(newUserTracker.getModifiedDate()));
		assertEquals(existingUserTracker.getSessionId(),
			newUserTracker.getSessionId());
		assertEquals(existingUserTracker.getRemoteAddr(),
			newUserTracker.getRemoteAddr());
		assertEquals(existingUserTracker.getRemoteHost(),
			newUserTracker.getRemoteHost());
		assertEquals(existingUserTracker.getUserAgent(),
			newUserTracker.getUserAgent());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		UserTracker existingUserTracker = _persistence.findByPrimaryKey(newUserTracker.getPrimaryKey());

		assertEquals(existingUserTracker, newUserTracker);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchUserTrackerException");
		}
		catch (NoSuchUserTrackerException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		UserTracker existingUserTracker = _persistence.fetchByPrimaryKey(newUserTracker.getPrimaryKey());

		assertEquals(existingUserTracker, newUserTracker);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		UserTracker missingUserTracker = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUserTracker);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserTracker newUserTracker = addUserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerId",
				newUserTracker.getUserTrackerId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		UserTracker existingUserTracker = (UserTracker)result.get(0);

		assertEquals(existingUserTracker, newUserTracker);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected UserTracker addUserTracker() throws Exception {
		long pk = nextLong();

		UserTracker userTracker = _persistence.create(pk);

		userTracker.setCompanyId(nextLong());
		userTracker.setUserId(nextLong());
		userTracker.setModifiedDate(nextDate());
		userTracker.setSessionId(randomString());
		userTracker.setRemoteAddr(randomString());
		userTracker.setRemoteHost(randomString());
		userTracker.setUserAgent(randomString());

		_persistence.update(userTracker, false);

		return userTracker;
	}

	private UserTrackerPersistence _persistence;
}