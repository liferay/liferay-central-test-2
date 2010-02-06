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

import com.liferay.portal.NoSuchBrowserTrackerException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="BrowserTrackerPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class BrowserTrackerPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BrowserTrackerPersistence)PortalBeanLocatorUtil.locate(BrowserTrackerPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		BrowserTracker browserTracker = _persistence.create(pk);

		assertNotNull(browserTracker);

		assertEquals(browserTracker.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		_persistence.remove(newBrowserTracker);

		BrowserTracker existingBrowserTracker = _persistence.fetchByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertNull(existingBrowserTracker);
	}

	public void testUpdateNew() throws Exception {
		addBrowserTracker();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		BrowserTracker newBrowserTracker = _persistence.create(pk);

		newBrowserTracker.setUserId(nextLong());
		newBrowserTracker.setBrowserKey(nextLong());

		_persistence.update(newBrowserTracker, false);

		BrowserTracker existingBrowserTracker = _persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertEquals(existingBrowserTracker.getBrowserTrackerId(),
			newBrowserTracker.getBrowserTrackerId());
		assertEquals(existingBrowserTracker.getUserId(),
			newBrowserTracker.getUserId());
		assertEquals(existingBrowserTracker.getBrowserKey(),
			newBrowserTracker.getBrowserKey());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		BrowserTracker existingBrowserTracker = _persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchBrowserTrackerException");
		}
		catch (NoSuchBrowserTrackerException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		BrowserTracker existingBrowserTracker = _persistence.fetchByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		BrowserTracker missingBrowserTracker = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBrowserTracker);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("browserTrackerId",
				newBrowserTracker.getBrowserTrackerId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		BrowserTracker existingBrowserTracker = (BrowserTracker)result.get(0);

		assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("browserTrackerId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected BrowserTracker addBrowserTracker() throws Exception {
		long pk = nextLong();

		BrowserTracker browserTracker = _persistence.create(pk);

		browserTracker.setUserId(nextLong());
		browserTracker.setBrowserKey(nextLong());

		_persistence.update(browserTracker, false);

		return browserTracker;
	}

	private BrowserTrackerPersistence _persistence;
}