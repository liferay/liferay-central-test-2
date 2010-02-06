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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.model.JournalArticleResource;

import java.util.List;

/**
 * <a href="JournalArticleResourcePersistenceTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JournalArticleResourcePersistenceTest
	extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalArticleResourcePersistence)PortalBeanLocatorUtil.locate(JournalArticleResourcePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalArticleResource journalArticleResource = _persistence.create(pk);

		assertNotNull(journalArticleResource);

		assertEquals(journalArticleResource.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalArticleResource newJournalArticleResource = addJournalArticleResource();

		_persistence.remove(newJournalArticleResource);

		JournalArticleResource existingJournalArticleResource = _persistence.fetchByPrimaryKey(newJournalArticleResource.getPrimaryKey());

		assertNull(existingJournalArticleResource);
	}

	public void testUpdateNew() throws Exception {
		addJournalArticleResource();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalArticleResource newJournalArticleResource = _persistence.create(pk);

		newJournalArticleResource.setGroupId(nextLong());
		newJournalArticleResource.setArticleId(randomString());

		_persistence.update(newJournalArticleResource, false);

		JournalArticleResource existingJournalArticleResource = _persistence.findByPrimaryKey(newJournalArticleResource.getPrimaryKey());

		assertEquals(existingJournalArticleResource.getResourcePrimKey(),
			newJournalArticleResource.getResourcePrimKey());
		assertEquals(existingJournalArticleResource.getGroupId(),
			newJournalArticleResource.getGroupId());
		assertEquals(existingJournalArticleResource.getArticleId(),
			newJournalArticleResource.getArticleId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticleResource newJournalArticleResource = addJournalArticleResource();

		JournalArticleResource existingJournalArticleResource = _persistence.findByPrimaryKey(newJournalArticleResource.getPrimaryKey());

		assertEquals(existingJournalArticleResource, newJournalArticleResource);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchArticleResourceException");
		}
		catch (NoSuchArticleResourceException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticleResource newJournalArticleResource = addJournalArticleResource();

		JournalArticleResource existingJournalArticleResource = _persistence.fetchByPrimaryKey(newJournalArticleResource.getPrimaryKey());

		assertEquals(existingJournalArticleResource, newJournalArticleResource);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalArticleResource missingJournalArticleResource = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalArticleResource);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticleResource newJournalArticleResource = addJournalArticleResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleResource.class,
				JournalArticleResource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePrimKey",
				newJournalArticleResource.getResourcePrimKey()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalArticleResource existingJournalArticleResource = (JournalArticleResource)result.get(0);

		assertEquals(existingJournalArticleResource, newJournalArticleResource);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleResource.class,
				JournalArticleResource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePrimKey",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected JournalArticleResource addJournalArticleResource()
		throws Exception {
		long pk = nextLong();

		JournalArticleResource journalArticleResource = _persistence.create(pk);

		journalArticleResource.setGroupId(nextLong());
		journalArticleResource.setArticleId(randomString());

		_persistence.update(journalArticleResource, false);

		return journalArticleResource;
	}

	private JournalArticleResourcePersistence _persistence;
}