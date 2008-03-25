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

import com.liferay.portal.NoSuchExpandoColumnException;
import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.model.ExpandoColumn;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * <a href="ExpandoColumnPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoColumnPersistence)BeanLocatorUtil.locate(_TX_IMPL);
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

		newExpandoColumn.setClassNameId(nextLong());
		newExpandoColumn.setName(randomString());
		newExpandoColumn.setType(nextInt());
		newExpandoColumn.setSettings(randomString());

		_persistence.update(newExpandoColumn);

		ExpandoColumn existingExpandoColumn = _persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertEquals(existingExpandoColumn.getColumnId(),
			newExpandoColumn.getColumnId());
		assertEquals(existingExpandoColumn.getClassNameId(),
			newExpandoColumn.getClassNameId());
		assertEquals(existingExpandoColumn.getName(), newExpandoColumn.getName());
		assertEquals(existingExpandoColumn.getType(), newExpandoColumn.getType());
		assertEquals(existingExpandoColumn.getSettings(),
			newExpandoColumn.getSettings());
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

			fail("Missing entity did not throw NoSuchExpandoColumnException");
		}
		catch (NoSuchExpandoColumnException nsee) {
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

	protected ExpandoColumn addExpandoColumn() throws Exception {
		long pk = nextLong();

		ExpandoColumn expandoColumn = _persistence.create(pk);

		expandoColumn.setClassNameId(nextLong());
		expandoColumn.setName(randomString());
		expandoColumn.setType(nextInt());
		expandoColumn.setSettings(randomString());

		_persistence.update(expandoColumn);

		return expandoColumn;
	}

	private static final String _TX_IMPL = ExpandoColumnPersistence.class.getName() +
		".transaction";
	private ExpandoColumnPersistence _persistence;
}