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

import com.liferay.portal.NoSuchServiceComponentException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="ServiceComponentPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ServiceComponentPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ServiceComponentPersistence)PortalBeanLocatorUtil.locate(ServiceComponentPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ServiceComponent serviceComponent = _persistence.create(pk);

		assertNotNull(serviceComponent);

		assertEquals(serviceComponent.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		_persistence.remove(newServiceComponent);

		ServiceComponent existingServiceComponent = _persistence.fetchByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertNull(existingServiceComponent);
	}

	public void testUpdateNew() throws Exception {
		addServiceComponent();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ServiceComponent newServiceComponent = _persistence.create(pk);

		newServiceComponent.setBuildNamespace(randomString());
		newServiceComponent.setBuildNumber(nextLong());
		newServiceComponent.setBuildDate(nextLong());
		newServiceComponent.setData(randomString());

		_persistence.update(newServiceComponent, false);

		ServiceComponent existingServiceComponent = _persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertEquals(existingServiceComponent.getServiceComponentId(),
			newServiceComponent.getServiceComponentId());
		assertEquals(existingServiceComponent.getBuildNamespace(),
			newServiceComponent.getBuildNamespace());
		assertEquals(existingServiceComponent.getBuildNumber(),
			newServiceComponent.getBuildNumber());
		assertEquals(existingServiceComponent.getBuildDate(),
			newServiceComponent.getBuildDate());
		assertEquals(existingServiceComponent.getData(),
			newServiceComponent.getData());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		ServiceComponent existingServiceComponent = _persistence.findByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertEquals(existingServiceComponent, newServiceComponent);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchServiceComponentException");
		}
		catch (NoSuchServiceComponentException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		ServiceComponent existingServiceComponent = _persistence.fetchByPrimaryKey(newServiceComponent.getPrimaryKey());

		assertEquals(existingServiceComponent, newServiceComponent);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ServiceComponent missingServiceComponent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingServiceComponent);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ServiceComponent newServiceComponent = addServiceComponent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceComponentId",
				newServiceComponent.getServiceComponentId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ServiceComponent existingServiceComponent = (ServiceComponent)result.get(0);

		assertEquals(existingServiceComponent, newServiceComponent);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ServiceComponent.class,
				ServiceComponent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("serviceComponentId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ServiceComponent addServiceComponent() throws Exception {
		long pk = nextLong();

		ServiceComponent serviceComponent = _persistence.create(pk);

		serviceComponent.setBuildNamespace(randomString());
		serviceComponent.setBuildNumber(nextLong());
		serviceComponent.setBuildDate(nextLong());
		serviceComponent.setData(randomString());

		_persistence.update(serviceComponent, false);

		return serviceComponent;
	}

	private ServiceComponentPersistence _persistence;
}