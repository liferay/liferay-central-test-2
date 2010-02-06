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

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;

import java.util.List;

/**
 * <a href="ExpandoColumnPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ExpandoColumn expandoColumn = _persistence.create(pk);

		assertNotNull(expandoColumn);

		assertEquals(expandoColumn.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		_persistence.remove(newExpandoColumn);

		ExpandoColumn existingExpandoColumn = _persistence.fetchByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertNull(existingExpandoColumn);
	}

	public void testUpdateNew() throws Exception {
		addExpandoColumn();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ExpandoColumn newExpandoColumn = _persistence.create(pk);

		newExpandoColumn.setCompanyId(nextLong());
		newExpandoColumn.setTableId(nextLong());
		newExpandoColumn.setName(randomString());
		newExpandoColumn.setType(nextInt());
		newExpandoColumn.setDefaultData(randomString());
		newExpandoColumn.setTypeSettings(randomString());

		_persistence.update(newExpandoColumn, false);

		ExpandoColumn existingExpandoColumn = _persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertEquals(existingExpandoColumn.getColumnId(),
			newExpandoColumn.getColumnId());
		assertEquals(existingExpandoColumn.getCompanyId(),
			newExpandoColumn.getCompanyId());
		assertEquals(existingExpandoColumn.getTableId(),
			newExpandoColumn.getTableId());
		assertEquals(existingExpandoColumn.getName(), newExpandoColumn.getName());
		assertEquals(existingExpandoColumn.getType(), newExpandoColumn.getType());
		assertEquals(existingExpandoColumn.getDefaultData(),
			newExpandoColumn.getDefaultData());
		assertEquals(existingExpandoColumn.getTypeSettings(),
			newExpandoColumn.getTypeSettings());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		ExpandoColumn existingExpandoColumn = _persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchColumnException");
		}
		catch (NoSuchColumnException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		ExpandoColumn existingExpandoColumn = _persistence.fetchByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ExpandoColumn missingExpandoColumn = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoColumn);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("columnId",
				newExpandoColumn.getColumnId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ExpandoColumn existingExpandoColumn = (ExpandoColumn)result.get(0);

		assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("columnId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ExpandoColumn addExpandoColumn() throws Exception {
		long pk = nextLong();

		ExpandoColumn expandoColumn = _persistence.create(pk);

		expandoColumn.setCompanyId(nextLong());
		expandoColumn.setTableId(nextLong());
		expandoColumn.setName(randomString());
		expandoColumn.setType(nextInt());
		expandoColumn.setDefaultData(randomString());
		expandoColumn.setTypeSettings(randomString());

		_persistence.update(expandoColumn, false);

		return expandoColumn;
	}

	private ExpandoColumnPersistence _persistence;
}