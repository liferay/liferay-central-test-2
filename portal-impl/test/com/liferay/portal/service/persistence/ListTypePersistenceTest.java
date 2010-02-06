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

import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ListType;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="ListTypePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ListTypePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ListTypePersistence)PortalBeanLocatorUtil.locate(ListTypePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		int pk = nextInt();

		ListType listType = _persistence.create(pk);

		assertNotNull(listType);

		assertEquals(listType.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ListType newListType = addListType();

		_persistence.remove(newListType);

		ListType existingListType = _persistence.fetchByPrimaryKey(newListType.getPrimaryKey());

		assertNull(existingListType);
	}

	public void testUpdateNew() throws Exception {
		addListType();
	}

	public void testUpdateExisting() throws Exception {
		int pk = nextInt();

		ListType newListType = _persistence.create(pk);

		newListType.setName(randomString());
		newListType.setType(randomString());

		_persistence.update(newListType, false);

		ListType existingListType = _persistence.findByPrimaryKey(newListType.getPrimaryKey());

		assertEquals(existingListType.getListTypeId(),
			newListType.getListTypeId());
		assertEquals(existingListType.getName(), newListType.getName());
		assertEquals(existingListType.getType(), newListType.getType());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ListType newListType = addListType();

		ListType existingListType = _persistence.findByPrimaryKey(newListType.getPrimaryKey());

		assertEquals(existingListType, newListType);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		int pk = nextInt();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchListTypeException");
		}
		catch (NoSuchListTypeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ListType newListType = addListType();

		ListType existingListType = _persistence.fetchByPrimaryKey(newListType.getPrimaryKey());

		assertEquals(existingListType, newListType);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		int pk = nextInt();

		ListType missingListType = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingListType);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ListType newListType = addListType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				ListType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listTypeId",
				newListType.getListTypeId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ListType existingListType = (ListType)result.get(0);

		assertEquals(existingListType, newListType);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				ListType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listTypeId", nextInt()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ListType addListType() throws Exception {
		int pk = nextInt();

		ListType listType = _persistence.create(pk);

		listType.setName(randomString());
		listType.setType(randomString());

		_persistence.update(listType, false);

		return listType;
	}

	private ListTypePersistence _persistence;
}