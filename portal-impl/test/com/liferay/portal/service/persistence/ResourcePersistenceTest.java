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

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.Resource;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="ResourcePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ResourcePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourcePersistence)PortalBeanLocatorUtil.locate(ResourcePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Resource resource = _persistence.create(pk);

		assertNotNull(resource);

		assertEquals(resource.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Resource newResource = addResource();

		_persistence.remove(newResource);

		Resource existingResource = _persistence.fetchByPrimaryKey(newResource.getPrimaryKey());

		assertNull(existingResource);
	}

	public void testUpdateNew() throws Exception {
		addResource();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Resource newResource = _persistence.create(pk);

		newResource.setCodeId(nextLong());
		newResource.setPrimKey(randomString());

		_persistence.update(newResource, false);

		Resource existingResource = _persistence.findByPrimaryKey(newResource.getPrimaryKey());

		assertEquals(existingResource.getResourceId(),
			newResource.getResourceId());
		assertEquals(existingResource.getCodeId(), newResource.getCodeId());
		assertEquals(existingResource.getPrimKey(), newResource.getPrimKey());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Resource newResource = addResource();

		Resource existingResource = _persistence.findByPrimaryKey(newResource.getPrimaryKey());

		assertEquals(existingResource, newResource);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchResourceException");
		}
		catch (NoSuchResourceException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Resource newResource = addResource();

		Resource existingResource = _persistence.fetchByPrimaryKey(newResource.getPrimaryKey());

		assertEquals(existingResource, newResource);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Resource missingResource = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResource);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Resource newResource = addResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Resource.class,
				Resource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceId",
				newResource.getResourceId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Resource existingResource = (Resource)result.get(0);

		assertEquals(existingResource, newResource);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Resource.class,
				Resource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Resource addResource() throws Exception {
		long pk = nextLong();

		Resource resource = _persistence.create(pk);

		resource.setCodeId(nextLong());
		resource.setPrimKey(randomString());

		_persistence.update(resource, false);

		return resource;
	}

	private ResourcePersistence _persistence;
}