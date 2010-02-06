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

import com.liferay.portlet.expando.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;

import java.util.List;

/**
 * <a href="ExpandoValuePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExpandoValuePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoValuePersistence)PortalBeanLocatorUtil.locate(ExpandoValuePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ExpandoValue expandoValue = _persistence.create(pk);

		assertNotNull(expandoValue);

		assertEquals(expandoValue.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		_persistence.remove(newExpandoValue);

		ExpandoValue existingExpandoValue = _persistence.fetchByPrimaryKey(newExpandoValue.getPrimaryKey());

		assertNull(existingExpandoValue);
	}

	public void testUpdateNew() throws Exception {
		addExpandoValue();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ExpandoValue newExpandoValue = _persistence.create(pk);

		newExpandoValue.setCompanyId(nextLong());
		newExpandoValue.setTableId(nextLong());
		newExpandoValue.setColumnId(nextLong());
		newExpandoValue.setRowId(nextLong());
		newExpandoValue.setClassNameId(nextLong());
		newExpandoValue.setClassPK(nextLong());
		newExpandoValue.setData(randomString());

		_persistence.update(newExpandoValue, false);

		ExpandoValue existingExpandoValue = _persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		assertEquals(existingExpandoValue.getValueId(),
			newExpandoValue.getValueId());
		assertEquals(existingExpandoValue.getCompanyId(),
			newExpandoValue.getCompanyId());
		assertEquals(existingExpandoValue.getTableId(),
			newExpandoValue.getTableId());
		assertEquals(existingExpandoValue.getColumnId(),
			newExpandoValue.getColumnId());
		assertEquals(existingExpandoValue.getRowId(), newExpandoValue.getRowId());
		assertEquals(existingExpandoValue.getClassNameId(),
			newExpandoValue.getClassNameId());
		assertEquals(existingExpandoValue.getClassPK(),
			newExpandoValue.getClassPK());
		assertEquals(existingExpandoValue.getData(), newExpandoValue.getData());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		ExpandoValue existingExpandoValue = _persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		assertEquals(existingExpandoValue, newExpandoValue);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchValueException");
		}
		catch (NoSuchValueException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		ExpandoValue existingExpandoValue = _persistence.fetchByPrimaryKey(newExpandoValue.getPrimaryKey());

		assertEquals(existingExpandoValue, newExpandoValue);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ExpandoValue missingExpandoValue = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoValue);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("valueId",
				newExpandoValue.getValueId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ExpandoValue existingExpandoValue = (ExpandoValue)result.get(0);

		assertEquals(existingExpandoValue, newExpandoValue);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("valueId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ExpandoValue addExpandoValue() throws Exception {
		long pk = nextLong();

		ExpandoValue expandoValue = _persistence.create(pk);

		expandoValue.setCompanyId(nextLong());
		expandoValue.setTableId(nextLong());
		expandoValue.setColumnId(nextLong());
		expandoValue.setRowId(nextLong());
		expandoValue.setClassNameId(nextLong());
		expandoValue.setClassPK(nextLong());
		expandoValue.setData(randomString());

		_persistence.update(expandoValue, false);

		return expandoValue;
	}

	private ExpandoValuePersistence _persistence;
}