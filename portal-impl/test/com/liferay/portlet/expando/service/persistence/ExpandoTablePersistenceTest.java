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

import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoTable;

import java.util.List;

/**
 * <a href="ExpandoTablePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExpandoTablePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoTablePersistence)PortalBeanLocatorUtil.locate(ExpandoTablePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ExpandoTable expandoTable = _persistence.create(pk);

		assertNotNull(expandoTable);

		assertEquals(expandoTable.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		_persistence.remove(newExpandoTable);

		ExpandoTable existingExpandoTable = _persistence.fetchByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertNull(existingExpandoTable);
	}

	public void testUpdateNew() throws Exception {
		addExpandoTable();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ExpandoTable newExpandoTable = _persistence.create(pk);

		newExpandoTable.setCompanyId(nextLong());
		newExpandoTable.setClassNameId(nextLong());
		newExpandoTable.setName(randomString());

		_persistence.update(newExpandoTable, false);

		ExpandoTable existingExpandoTable = _persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertEquals(existingExpandoTable.getTableId(),
			newExpandoTable.getTableId());
		assertEquals(existingExpandoTable.getCompanyId(),
			newExpandoTable.getCompanyId());
		assertEquals(existingExpandoTable.getClassNameId(),
			newExpandoTable.getClassNameId());
		assertEquals(existingExpandoTable.getName(), newExpandoTable.getName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		ExpandoTable existingExpandoTable = _persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertEquals(existingExpandoTable, newExpandoTable);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTableException");
		}
		catch (NoSuchTableException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		ExpandoTable existingExpandoTable = _persistence.fetchByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertEquals(existingExpandoTable, newExpandoTable);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ExpandoTable missingExpandoTable = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoTable);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tableId",
				newExpandoTable.getTableId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ExpandoTable existingExpandoTable = (ExpandoTable)result.get(0);

		assertEquals(existingExpandoTable, newExpandoTable);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tableId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ExpandoTable addExpandoTable() throws Exception {
		long pk = nextLong();

		ExpandoTable expandoTable = _persistence.create(pk);

		expandoTable.setCompanyId(nextLong());
		expandoTable.setClassNameId(nextLong());
		expandoTable.setName(randomString());

		_persistence.update(expandoTable, false);

		return expandoTable;
	}

	private ExpandoTablePersistence _persistence;
}