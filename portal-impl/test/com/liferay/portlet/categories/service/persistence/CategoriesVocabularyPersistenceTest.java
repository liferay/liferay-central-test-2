/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.categories.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.categories.NoSuchVocabularyException;
import com.liferay.portlet.categories.model.CategoriesVocabulary;

/**
 * <a href="CategoriesVocabularyPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoriesVocabularyPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (CategoriesVocabularyPersistence)PortalBeanLocatorUtil.locate(CategoriesVocabularyPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		CategoriesVocabulary categoriesVocabulary = _persistence.create(pk);

		assertNotNull(categoriesVocabulary);

		assertEquals(categoriesVocabulary.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		CategoriesVocabulary newCategoriesVocabulary = addCategoriesVocabulary();

		_persistence.remove(newCategoriesVocabulary);

		CategoriesVocabulary existingCategoriesVocabulary = _persistence.fetchByPrimaryKey(newCategoriesVocabulary.getPrimaryKey());

		assertNull(existingCategoriesVocabulary);
	}

	public void testUpdateNew() throws Exception {
		addCategoriesVocabulary();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		CategoriesVocabulary newCategoriesVocabulary = _persistence.create(pk);

		newCategoriesVocabulary.setGroupId(nextLong());
		newCategoriesVocabulary.setCompanyId(nextLong());
		newCategoriesVocabulary.setUserId(nextLong());
		newCategoriesVocabulary.setUserName(randomString());
		newCategoriesVocabulary.setCreateDate(nextDate());
		newCategoriesVocabulary.setModifiedDate(nextDate());
		newCategoriesVocabulary.setName(randomString());
		newCategoriesVocabulary.setDescription(randomString());

		_persistence.update(newCategoriesVocabulary, false);

		CategoriesVocabulary existingCategoriesVocabulary = _persistence.findByPrimaryKey(newCategoriesVocabulary.getPrimaryKey());

		assertEquals(existingCategoriesVocabulary.getVocabularyId(),
			newCategoriesVocabulary.getVocabularyId());
		assertEquals(existingCategoriesVocabulary.getGroupId(),
			newCategoriesVocabulary.getGroupId());
		assertEquals(existingCategoriesVocabulary.getCompanyId(),
			newCategoriesVocabulary.getCompanyId());
		assertEquals(existingCategoriesVocabulary.getUserId(),
			newCategoriesVocabulary.getUserId());
		assertEquals(existingCategoriesVocabulary.getUserName(),
			newCategoriesVocabulary.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingCategoriesVocabulary.getCreateDate()),
			Time.getShortTimestamp(newCategoriesVocabulary.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingCategoriesVocabulary.getModifiedDate()),
			Time.getShortTimestamp(newCategoriesVocabulary.getModifiedDate()));
		assertEquals(existingCategoriesVocabulary.getName(),
			newCategoriesVocabulary.getName());
		assertEquals(existingCategoriesVocabulary.getDescription(),
			newCategoriesVocabulary.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		CategoriesVocabulary newCategoriesVocabulary = addCategoriesVocabulary();

		CategoriesVocabulary existingCategoriesVocabulary = _persistence.findByPrimaryKey(newCategoriesVocabulary.getPrimaryKey());

		assertEquals(existingCategoriesVocabulary, newCategoriesVocabulary);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchVocabularyException");
		}
		catch (NoSuchVocabularyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		CategoriesVocabulary newCategoriesVocabulary = addCategoriesVocabulary();

		CategoriesVocabulary existingCategoriesVocabulary = _persistence.fetchByPrimaryKey(newCategoriesVocabulary.getPrimaryKey());

		assertEquals(existingCategoriesVocabulary, newCategoriesVocabulary);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		CategoriesVocabulary missingCategoriesVocabulary = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingCategoriesVocabulary);
	}

	protected CategoriesVocabulary addCategoriesVocabulary()
		throws Exception {
		long pk = nextLong();

		CategoriesVocabulary categoriesVocabulary = _persistence.create(pk);

		categoriesVocabulary.setGroupId(nextLong());
		categoriesVocabulary.setCompanyId(nextLong());
		categoriesVocabulary.setUserId(nextLong());
		categoriesVocabulary.setUserName(randomString());
		categoriesVocabulary.setCreateDate(nextDate());
		categoriesVocabulary.setModifiedDate(nextDate());
		categoriesVocabulary.setName(randomString());
		categoriesVocabulary.setDescription(randomString());

		_persistence.update(categoriesVocabulary, false);

		return categoriesVocabulary;
	}

	private CategoriesVocabularyPersistence _persistence;
}