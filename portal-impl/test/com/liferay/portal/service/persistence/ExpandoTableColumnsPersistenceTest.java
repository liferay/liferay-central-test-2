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

import com.liferay.portal.NoSuchExpandoTableColumnsException;
import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.model.ExpandoTableColumns;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * <a href="ExpandoTableColumnsPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableColumnsPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoTableColumnsPersistence)BeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		ExpandoTableColumnsPK pk = new ExpandoTableColumnsPK(nextLong(),
				nextLong());

		ExpandoTableColumns expandoTableColumns = _persistence.create(pk);

		assertNotNull(expandoTableColumns);

		assertEquals(expandoTableColumns.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoTableColumns newExpandoTableColumns = addExpandoTableColumns();

		_persistence.remove(newExpandoTableColumns);

		ExpandoTableColumns existingExpandoTableColumns = _persistence.fetchByPrimaryKey(newExpandoTableColumns.getPrimaryKey());

		assertNull(existingExpandoTableColumns);
	}

	public void testUpdateNew() throws Exception {
		addExpandoTableColumns();
	}

	public void testUpdateExisting() throws Exception {
		ExpandoTableColumnsPK pk = new ExpandoTableColumnsPK(nextLong(),
				nextLong());

		ExpandoTableColumns newExpandoTableColumns = _persistence.create(pk);

		_persistence.update(newExpandoTableColumns);

		ExpandoTableColumns existingExpandoTableColumns = _persistence.findByPrimaryKey(newExpandoTableColumns.getPrimaryKey());

		assertEquals(existingExpandoTableColumns.getTableId(),
			newExpandoTableColumns.getTableId());
		assertEquals(existingExpandoTableColumns.getColumnId(),
			newExpandoTableColumns.getColumnId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoTableColumns newExpandoTableColumns = addExpandoTableColumns();

		ExpandoTableColumns existingExpandoTableColumns = _persistence.findByPrimaryKey(newExpandoTableColumns.getPrimaryKey());

		assertEquals(existingExpandoTableColumns, newExpandoTableColumns);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		ExpandoTableColumnsPK pk = new ExpandoTableColumnsPK(nextLong(),
				nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchExpandoTableColumnsException");
		}
		catch (NoSuchExpandoTableColumnsException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoTableColumns newExpandoTableColumns = addExpandoTableColumns();

		ExpandoTableColumns existingExpandoTableColumns = _persistence.fetchByPrimaryKey(newExpandoTableColumns.getPrimaryKey());

		assertEquals(existingExpandoTableColumns, newExpandoTableColumns);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		ExpandoTableColumnsPK pk = new ExpandoTableColumnsPK(nextLong(),
				nextLong());

		ExpandoTableColumns missingExpandoTableColumns = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoTableColumns);
	}

	protected ExpandoTableColumns addExpandoTableColumns()
		throws Exception {
		ExpandoTableColumnsPK pk = new ExpandoTableColumnsPK(nextLong(),
				nextLong());

		ExpandoTableColumns expandoTableColumns = _persistence.create(pk);

		_persistence.update(expandoTableColumns);

		return expandoTableColumns;
	}

	private static final String _TX_IMPL = ExpandoTableColumnsPersistence.class.getName() +
		".transaction";
	private ExpandoTableColumnsPersistence _persistence;
}