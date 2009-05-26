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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutPrototypeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * <a href="LayoutPrototypePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutPrototypePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutPrototypePersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutPrototype layoutPrototype = _persistence.create(pk);

		assertNotNull(layoutPrototype);

		assertEquals(layoutPrototype.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		_persistence.remove(newLayoutPrototype);

		LayoutPrototype existingLayoutPrototype = _persistence.fetchByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertNull(existingLayoutPrototype);
	}

	public void testUpdateNew() throws Exception {
		addLayoutPrototype();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutPrototype newLayoutPrototype = _persistence.create(pk);

		newLayoutPrototype.setCompanyId(nextLong());
		newLayoutPrototype.setName(randomString());
		newLayoutPrototype.setTitle(randomString());
		newLayoutPrototype.setDescription(randomString());
		newLayoutPrototype.setSettings(randomString());
		newLayoutPrototype.setActive(randomBoolean());

		_persistence.update(newLayoutPrototype, false);

		LayoutPrototype existingLayoutPrototype = _persistence.findByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertEquals(existingLayoutPrototype.getLayoutPrototypeId(),
			newLayoutPrototype.getLayoutPrototypeId());
		assertEquals(existingLayoutPrototype.getCompanyId(),
			newLayoutPrototype.getCompanyId());
		assertEquals(existingLayoutPrototype.getName(),
			newLayoutPrototype.getName());
		assertEquals(existingLayoutPrototype.getTitle(),
			newLayoutPrototype.getTitle());
		assertEquals(existingLayoutPrototype.getDescription(),
			newLayoutPrototype.getDescription());
		assertEquals(existingLayoutPrototype.getSettings(),
			newLayoutPrototype.getSettings());
		assertEquals(existingLayoutPrototype.getActive(),
			newLayoutPrototype.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		LayoutPrototype existingLayoutPrototype = _persistence.findByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertEquals(existingLayoutPrototype, newLayoutPrototype);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLayoutPrototypeException");
		}
		catch (NoSuchLayoutPrototypeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		LayoutPrototype existingLayoutPrototype = _persistence.fetchByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertEquals(existingLayoutPrototype, newLayoutPrototype);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutPrototype missingLayoutPrototype = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutPrototype);
	}

	protected LayoutPrototype addLayoutPrototype() throws Exception {
		long pk = nextLong();

		LayoutPrototype layoutPrototype = _persistence.create(pk);

		layoutPrototype.setCompanyId(nextLong());
		layoutPrototype.setName(randomString());
		layoutPrototype.setTitle(randomString());
		layoutPrototype.setDescription(randomString());
		layoutPrototype.setSettings(randomString());
		layoutPrototype.setActive(randomBoolean());

		_persistence.update(layoutPrototype, false);

		return layoutPrototype;
	}

	private LayoutPrototypePersistence _persistence;
}