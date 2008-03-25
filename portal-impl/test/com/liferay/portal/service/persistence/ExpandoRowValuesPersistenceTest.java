/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchExpandoRowValuesException;
import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.model.ExpandoRowValues;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * <a href="ExpandoRowValuesPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoRowValuesPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoRowValuesPersistence)BeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		ExpandoRowValuesPK pk = new ExpandoRowValuesPK(nextLong(), nextLong());

		ExpandoRowValues expandoRowValues = _persistence.create(pk);

		assertNotNull(expandoRowValues);

		assertEquals(expandoRowValues.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoRowValues newExpandoRowValues = addExpandoRowValues();

		_persistence.remove(newExpandoRowValues);

		ExpandoRowValues existingExpandoRowValues = _persistence.fetchByPrimaryKey(newExpandoRowValues.getPrimaryKey());

		assertNull(existingExpandoRowValues);
	}

	public void testUpdateNew() throws Exception {
		addExpandoRowValues();
	}

	public void testUpdateExisting() throws Exception {
		ExpandoRowValuesPK pk = new ExpandoRowValuesPK(nextLong(), nextLong());

		ExpandoRowValues newExpandoRowValues = _persistence.create(pk);

		_persistence.update(newExpandoRowValues);

		ExpandoRowValues existingExpandoRowValues = _persistence.findByPrimaryKey(newExpandoRowValues.getPrimaryKey());

		assertEquals(existingExpandoRowValues.getRowId(),
			newExpandoRowValues.getRowId());
		assertEquals(existingExpandoRowValues.getValueId(),
			newExpandoRowValues.getValueId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoRowValues newExpandoRowValues = addExpandoRowValues();

		ExpandoRowValues existingExpandoRowValues = _persistence.findByPrimaryKey(newExpandoRowValues.getPrimaryKey());

		assertEquals(existingExpandoRowValues, newExpandoRowValues);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		ExpandoRowValuesPK pk = new ExpandoRowValuesPK(nextLong(), nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchExpandoRowValuesException");
		}
		catch (NoSuchExpandoRowValuesException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoRowValues newExpandoRowValues = addExpandoRowValues();

		ExpandoRowValues existingExpandoRowValues = _persistence.fetchByPrimaryKey(newExpandoRowValues.getPrimaryKey());

		assertEquals(existingExpandoRowValues, newExpandoRowValues);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		ExpandoRowValuesPK pk = new ExpandoRowValuesPK(nextLong(), nextLong());

		ExpandoRowValues missingExpandoRowValues = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoRowValues);
	}

	protected ExpandoRowValues addExpandoRowValues() throws Exception {
		ExpandoRowValuesPK pk = new ExpandoRowValuesPK(nextLong(), nextLong());

		ExpandoRowValues expandoRowValues = _persistence.create(pk);

		_persistence.update(expandoRowValues);

		return expandoRowValues;
	}

	private static final String _TX_IMPL = ExpandoRowValuesPersistence.class.getName() +
		".transaction";
	private ExpandoRowValuesPersistence _persistence;
}