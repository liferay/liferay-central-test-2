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

import com.liferay.portlet.categories.NoSuchPropertyException;
import com.liferay.portlet.categories.model.CategoriesProperty;

/**
 * <a href="CategoriesPropertyPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoriesPropertyPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (CategoriesPropertyPersistence)PortalBeanLocatorUtil.locate(CategoriesPropertyPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		CategoriesProperty categoriesProperty = _persistence.create(pk);

		assertNotNull(categoriesProperty);

		assertEquals(categoriesProperty.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		CategoriesProperty newCategoriesProperty = addCategoriesProperty();

		_persistence.remove(newCategoriesProperty);

		CategoriesProperty existingCategoriesProperty = _persistence.fetchByPrimaryKey(newCategoriesProperty.getPrimaryKey());

		assertNull(existingCategoriesProperty);
	}

	public void testUpdateNew() throws Exception {
		addCategoriesProperty();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		CategoriesProperty newCategoriesProperty = _persistence.create(pk);

		newCategoriesProperty.setCompanyId(nextLong());
		newCategoriesProperty.setUserId(nextLong());
		newCategoriesProperty.setUserName(randomString());
		newCategoriesProperty.setCreateDate(nextDate());
		newCategoriesProperty.setModifiedDate(nextDate());
		newCategoriesProperty.setEntryId(nextLong());
		newCategoriesProperty.setKey(randomString());
		newCategoriesProperty.setValue(randomString());

		_persistence.update(newCategoriesProperty, false);

		CategoriesProperty existingCategoriesProperty = _persistence.findByPrimaryKey(newCategoriesProperty.getPrimaryKey());

		assertEquals(existingCategoriesProperty.getPropertyId(),
			newCategoriesProperty.getPropertyId());
		assertEquals(existingCategoriesProperty.getCompanyId(),
			newCategoriesProperty.getCompanyId());
		assertEquals(existingCategoriesProperty.getUserId(),
			newCategoriesProperty.getUserId());
		assertEquals(existingCategoriesProperty.getUserName(),
			newCategoriesProperty.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingCategoriesProperty.getCreateDate()),
			Time.getShortTimestamp(newCategoriesProperty.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingCategoriesProperty.getModifiedDate()),
			Time.getShortTimestamp(newCategoriesProperty.getModifiedDate()));
		assertEquals(existingCategoriesProperty.getEntryId(),
			newCategoriesProperty.getEntryId());
		assertEquals(existingCategoriesProperty.getKey(),
			newCategoriesProperty.getKey());
		assertEquals(existingCategoriesProperty.getValue(),
			newCategoriesProperty.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		CategoriesProperty newCategoriesProperty = addCategoriesProperty();

		CategoriesProperty existingCategoriesProperty = _persistence.findByPrimaryKey(newCategoriesProperty.getPrimaryKey());

		assertEquals(existingCategoriesProperty, newCategoriesProperty);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPropertyException");
		}
		catch (NoSuchPropertyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		CategoriesProperty newCategoriesProperty = addCategoriesProperty();

		CategoriesProperty existingCategoriesProperty = _persistence.fetchByPrimaryKey(newCategoriesProperty.getPrimaryKey());

		assertEquals(existingCategoriesProperty, newCategoriesProperty);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		CategoriesProperty missingCategoriesProperty = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingCategoriesProperty);
	}

	protected CategoriesProperty addCategoriesProperty()
		throws Exception {
		long pk = nextLong();

		CategoriesProperty categoriesProperty = _persistence.create(pk);

		categoriesProperty.setCompanyId(nextLong());
		categoriesProperty.setUserId(nextLong());
		categoriesProperty.setUserName(randomString());
		categoriesProperty.setCreateDate(nextDate());
		categoriesProperty.setModifiedDate(nextDate());
		categoriesProperty.setEntryId(nextLong());
		categoriesProperty.setKey(randomString());
		categoriesProperty.setValue(randomString());

		_persistence.update(categoriesProperty, false);

		return categoriesProperty;
	}

	private CategoriesPropertyPersistence _persistence;
}