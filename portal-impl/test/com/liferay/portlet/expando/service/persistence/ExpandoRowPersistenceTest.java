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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;

import java.util.List;

/**
 * <a href="ExpandoRowPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExpandoRowPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoRowPersistence)PortalBeanLocatorUtil.locate(ExpandoRowPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		assertNotNull(expandoRow);

		assertEquals(expandoRow.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		_persistence.remove(newExpandoRow);

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertNull(existingExpandoRow);
	}

	public void testUpdateNew() throws Exception {
		addExpandoRow();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ExpandoRow newExpandoRow = _persistence.create(pk);

		newExpandoRow.setCompanyId(nextLong());
		newExpandoRow.setTableId(nextLong());
		newExpandoRow.setClassPK(nextLong());

		_persistence.update(newExpandoRow, false);

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertEquals(existingExpandoRow.getRowId(), newExpandoRow.getRowId());
		assertEquals(existingExpandoRow.getCompanyId(),
			newExpandoRow.getCompanyId());
		assertEquals(existingExpandoRow.getTableId(), newExpandoRow.getTableId());
		assertEquals(existingExpandoRow.getClassPK(), newExpandoRow.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertEquals(existingExpandoRow, newExpandoRow);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRowException");
		}
		catch (NoSuchRowException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertEquals(existingExpandoRow, newExpandoRow);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ExpandoRow missingExpandoRow = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoRow);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId",
				newExpandoRow.getRowId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ExpandoRow existingExpandoRow = (ExpandoRow)result.get(0);

		assertEquals(existingExpandoRow, newExpandoRow);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ExpandoRow addExpandoRow() throws Exception {
		long pk = nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		expandoRow.setCompanyId(nextLong());
		expandoRow.setTableId(nextLong());
		expandoRow.setClassPK(nextLong());

		_persistence.update(expandoRow, false);

		return expandoRow;
	}

	private ExpandoRowPersistence _persistence;
}