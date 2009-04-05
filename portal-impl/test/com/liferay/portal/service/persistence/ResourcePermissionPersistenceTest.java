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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * <a href="ResourcePermissionPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePermissionPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourcePermissionPersistence)PortalBeanLocatorUtil.locate(ResourcePermissionPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ResourcePermission resourcePermission = _persistence.create(pk);

		assertNotNull(resourcePermission);

		assertEquals(resourcePermission.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		_persistence.remove(newResourcePermission);

		ResourcePermission existingResourcePermission = _persistence.fetchByPrimaryKey(newResourcePermission.getPrimaryKey());

		assertNull(existingResourcePermission);
	}

	public void testUpdateNew() throws Exception {
		addResourcePermission();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ResourcePermission newResourcePermission = _persistence.create(pk);

		newResourcePermission.setResourceId(nextLong());
		newResourcePermission.setRoleId(nextLong());
		newResourcePermission.setActionIds(nextLong());

		_persistence.update(newResourcePermission, false);

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		assertEquals(existingResourcePermission.getResourcePermissionId(),
			newResourcePermission.getResourcePermissionId());
		assertEquals(existingResourcePermission.getResourceId(),
			newResourcePermission.getResourceId());
		assertEquals(existingResourcePermission.getRoleId(),
			newResourcePermission.getRoleId());
		assertEquals(existingResourcePermission.getActionIds(),
			newResourcePermission.getActionIds());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		ResourcePermission existingResourcePermission = _persistence.findByPrimaryKey(newResourcePermission.getPrimaryKey());

		assertEquals(existingResourcePermission, newResourcePermission);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchResourcePermissionException");
		}
		catch (NoSuchResourcePermissionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourcePermission newResourcePermission = addResourcePermission();

		ResourcePermission existingResourcePermission = _persistence.fetchByPrimaryKey(newResourcePermission.getPrimaryKey());

		assertEquals(existingResourcePermission, newResourcePermission);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ResourcePermission missingResourcePermission = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourcePermission);
	}

	protected ResourcePermission addResourcePermission()
		throws Exception {
		long pk = nextLong();

		ResourcePermission resourcePermission = _persistence.create(pk);

		resourcePermission.setResourceId(nextLong());
		resourcePermission.setRoleId(nextLong());
		resourcePermission.setActionIds(nextLong());

		_persistence.update(resourcePermission, false);

		return resourcePermission;
	}

	private ResourcePermissionPersistence _persistence;
}