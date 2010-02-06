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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.announcements.NoSuchDeliveryException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;

import java.util.List;

/**
 * <a href="AnnouncementsDeliveryPersistenceTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsDeliveryPersistenceTest
	extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementsDeliveryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery announcementsDelivery = _persistence.create(pk);

		assertNotNull(announcementsDelivery);

		assertEquals(announcementsDelivery.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		_persistence.remove(newAnnouncementsDelivery);

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.fetchByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertNull(existingAnnouncementsDelivery);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementsDelivery();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery newAnnouncementsDelivery = _persistence.create(pk);

		newAnnouncementsDelivery.setCompanyId(nextLong());
		newAnnouncementsDelivery.setUserId(nextLong());
		newAnnouncementsDelivery.setType(randomString());
		newAnnouncementsDelivery.setEmail(randomBoolean());
		newAnnouncementsDelivery.setSms(randomBoolean());
		newAnnouncementsDelivery.setWebsite(randomBoolean());

		_persistence.update(newAnnouncementsDelivery, false);

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.findByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertEquals(existingAnnouncementsDelivery.getDeliveryId(),
			newAnnouncementsDelivery.getDeliveryId());
		assertEquals(existingAnnouncementsDelivery.getCompanyId(),
			newAnnouncementsDelivery.getCompanyId());
		assertEquals(existingAnnouncementsDelivery.getUserId(),
			newAnnouncementsDelivery.getUserId());
		assertEquals(existingAnnouncementsDelivery.getType(),
			newAnnouncementsDelivery.getType());
		assertEquals(existingAnnouncementsDelivery.getEmail(),
			newAnnouncementsDelivery.getEmail());
		assertEquals(existingAnnouncementsDelivery.getSms(),
			newAnnouncementsDelivery.getSms());
		assertEquals(existingAnnouncementsDelivery.getWebsite(),
			newAnnouncementsDelivery.getWebsite());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.findByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertEquals(existingAnnouncementsDelivery, newAnnouncementsDelivery);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchDeliveryException");
		}
		catch (NoSuchDeliveryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.fetchByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertEquals(existingAnnouncementsDelivery, newAnnouncementsDelivery);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery missingAnnouncementsDelivery = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementsDelivery);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsDelivery.class,
				AnnouncementsDelivery.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deliveryId",
				newAnnouncementsDelivery.getDeliveryId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AnnouncementsDelivery existingAnnouncementsDelivery = (AnnouncementsDelivery)result.get(0);

		assertEquals(existingAnnouncementsDelivery, newAnnouncementsDelivery);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsDelivery.class,
				AnnouncementsDelivery.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deliveryId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected AnnouncementsDelivery addAnnouncementsDelivery()
		throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery announcementsDelivery = _persistence.create(pk);

		announcementsDelivery.setCompanyId(nextLong());
		announcementsDelivery.setUserId(nextLong());
		announcementsDelivery.setType(randomString());
		announcementsDelivery.setEmail(randomBoolean());
		announcementsDelivery.setSms(randomBoolean());
		announcementsDelivery.setWebsite(randomBoolean());

		_persistence.update(announcementsDelivery, false);

		return announcementsDelivery;
	}

	private AnnouncementsDeliveryPersistence _persistence;
}