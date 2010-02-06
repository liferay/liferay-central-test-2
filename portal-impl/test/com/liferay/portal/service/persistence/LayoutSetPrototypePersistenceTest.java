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

import com.liferay.portal.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="LayoutSetPrototypePersistenceTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LayoutSetPrototypePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutSetPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutSetPrototypePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutSetPrototype layoutSetPrototype = _persistence.create(pk);

		assertNotNull(layoutSetPrototype);

		assertEquals(layoutSetPrototype.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		_persistence.remove(newLayoutSetPrototype);

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.fetchByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertNull(existingLayoutSetPrototype);
	}

	public void testUpdateNew() throws Exception {
		addLayoutSetPrototype();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutSetPrototype newLayoutSetPrototype = _persistence.create(pk);

		newLayoutSetPrototype.setCompanyId(nextLong());
		newLayoutSetPrototype.setName(randomString());
		newLayoutSetPrototype.setDescription(randomString());
		newLayoutSetPrototype.setSettings(randomString());
		newLayoutSetPrototype.setActive(randomBoolean());

		_persistence.update(newLayoutSetPrototype, false);

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.findByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertEquals(existingLayoutSetPrototype.getLayoutSetPrototypeId(),
			newLayoutSetPrototype.getLayoutSetPrototypeId());
		assertEquals(existingLayoutSetPrototype.getCompanyId(),
			newLayoutSetPrototype.getCompanyId());
		assertEquals(existingLayoutSetPrototype.getName(),
			newLayoutSetPrototype.getName());
		assertEquals(existingLayoutSetPrototype.getDescription(),
			newLayoutSetPrototype.getDescription());
		assertEquals(existingLayoutSetPrototype.getSettings(),
			newLayoutSetPrototype.getSettings());
		assertEquals(existingLayoutSetPrototype.getActive(),
			newLayoutSetPrototype.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.findByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchLayoutSetPrototypeException");
		}
		catch (NoSuchLayoutSetPrototypeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.fetchByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutSetPrototype missingLayoutSetPrototype = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutSetPrototype);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetPrototypeId",
				newLayoutSetPrototype.getLayoutSetPrototypeId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		LayoutSetPrototype existingLayoutSetPrototype = (LayoutSetPrototype)result.get(0);

		assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetPrototypeId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected LayoutSetPrototype addLayoutSetPrototype()
		throws Exception {
		long pk = nextLong();

		LayoutSetPrototype layoutSetPrototype = _persistence.create(pk);

		layoutSetPrototype.setCompanyId(nextLong());
		layoutSetPrototype.setName(randomString());
		layoutSetPrototype.setDescription(randomString());
		layoutSetPrototype.setSettings(randomString());
		layoutSetPrototype.setActive(randomBoolean());

		_persistence.update(layoutSetPrototype, false);

		return layoutSetPrototype;
	}

	private LayoutSetPrototypePersistence _persistence;
}