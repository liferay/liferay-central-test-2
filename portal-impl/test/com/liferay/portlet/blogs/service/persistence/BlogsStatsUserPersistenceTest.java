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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;

import java.util.List;

/**
 * <a href="BlogsStatsUserPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class BlogsStatsUserPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BlogsStatsUserPersistence)PortalBeanLocatorUtil.locate(BlogsStatsUserPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		assertNotNull(blogsStatsUser);

		assertEquals(blogsStatsUser.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		_persistence.remove(newBlogsStatsUser);

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertNull(existingBlogsStatsUser);
	}

	public void testUpdateNew() throws Exception {
		addBlogsStatsUser();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		BlogsStatsUser newBlogsStatsUser = _persistence.create(pk);

		newBlogsStatsUser.setGroupId(nextLong());
		newBlogsStatsUser.setCompanyId(nextLong());
		newBlogsStatsUser.setUserId(nextLong());
		newBlogsStatsUser.setEntryCount(nextInt());
		newBlogsStatsUser.setLastPostDate(nextDate());
		newBlogsStatsUser.setRatingsTotalEntries(nextInt());
		newBlogsStatsUser.setRatingsTotalScore(nextDouble());
		newBlogsStatsUser.setRatingsAverageScore(nextDouble());

		_persistence.update(newBlogsStatsUser, false);

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertEquals(existingBlogsStatsUser.getStatsUserId(),
			newBlogsStatsUser.getStatsUserId());
		assertEquals(existingBlogsStatsUser.getGroupId(),
			newBlogsStatsUser.getGroupId());
		assertEquals(existingBlogsStatsUser.getCompanyId(),
			newBlogsStatsUser.getCompanyId());
		assertEquals(existingBlogsStatsUser.getUserId(),
			newBlogsStatsUser.getUserId());
		assertEquals(existingBlogsStatsUser.getEntryCount(),
			newBlogsStatsUser.getEntryCount());
		assertEquals(Time.getShortTimestamp(
				existingBlogsStatsUser.getLastPostDate()),
			Time.getShortTimestamp(newBlogsStatsUser.getLastPostDate()));
		assertEquals(existingBlogsStatsUser.getRatingsTotalEntries(),
			newBlogsStatsUser.getRatingsTotalEntries());
		assertEquals(existingBlogsStatsUser.getRatingsTotalScore(),
			newBlogsStatsUser.getRatingsTotalScore());
		assertEquals(existingBlogsStatsUser.getRatingsAverageScore(),
			newBlogsStatsUser.getRatingsAverageScore());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStatsUserException");
		}
		catch (NoSuchStatsUserException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		BlogsStatsUser missingBlogsStatsUser = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBlogsStatsUser);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				newBlogsStatsUser.getStatsUserId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		BlogsStatsUser existingBlogsStatsUser = (BlogsStatsUser)result.get(0);

		assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				BlogsStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected BlogsStatsUser addBlogsStatsUser() throws Exception {
		long pk = nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		blogsStatsUser.setGroupId(nextLong());
		blogsStatsUser.setCompanyId(nextLong());
		blogsStatsUser.setUserId(nextLong());
		blogsStatsUser.setEntryCount(nextInt());
		blogsStatsUser.setLastPostDate(nextDate());
		blogsStatsUser.setRatingsTotalEntries(nextInt());
		blogsStatsUser.setRatingsTotalScore(nextDouble());
		blogsStatsUser.setRatingsAverageScore(nextDouble());

		_persistence.update(blogsStatsUser, false);

		return blogsStatsUser;
	}

	private BlogsStatsUserPersistence _persistence;
}