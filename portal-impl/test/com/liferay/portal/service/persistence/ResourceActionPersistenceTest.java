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

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="ResourceActionPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ResourceActionPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourceActionPersistence)PortalBeanLocatorUtil.locate(ResourceActionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		assertNotNull(resourceAction);

		assertEquals(resourceAction.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		_persistence.remove(newResourceAction);

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		assertNull(existingResourceAction);
	}

	public void testUpdateNew() throws Exception {
		addResourceAction();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ResourceAction newResourceAction = _persistence.create(pk);

		newResourceAction.setName(randomString());
		newResourceAction.setActionId(randomString());
		newResourceAction.setBitwiseValue(nextLong());

		_persistence.update(newResourceAction, false);

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		assertEquals(existingResourceAction.getResourceActionId(),
			newResourceAction.getResourceActionId());
		assertEquals(existingResourceAction.getName(),
			newResourceAction.getName());
		assertEquals(existingResourceAction.getActionId(),
			newResourceAction.getActionId());
		assertEquals(existingResourceAction.getBitwiseValue(),
			newResourceAction.getBitwiseValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		assertEquals(existingResourceAction, newResourceAction);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchResourceActionException");
		}
		catch (NoSuchResourceActionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		assertEquals(existingResourceAction, newResourceAction);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ResourceAction missingResourceAction = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourceAction);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				newResourceAction.getResourceActionId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ResourceAction existingResourceAction = (ResourceAction)result.get(0);

		assertEquals(existingResourceAction, newResourceAction);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ResourceAction addResourceAction() throws Exception {
		long pk = nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		resourceAction.setName(randomString());
		resourceAction.setActionId(randomString());
		resourceAction.setBitwiseValue(nextLong());

		_persistence.update(resourceAction, false);

		return resourceAction;
	}

	private ResourceActionPersistence _persistence;
}