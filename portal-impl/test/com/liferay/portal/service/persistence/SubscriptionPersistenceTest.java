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

import com.liferay.portal.NoSuchSubscriptionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="SubscriptionPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SubscriptionPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SubscriptionPersistence)PortalBeanLocatorUtil.locate(SubscriptionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Subscription subscription = _persistence.create(pk);

		assertNotNull(subscription);

		assertEquals(subscription.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Subscription newSubscription = addSubscription();

		_persistence.remove(newSubscription);

		Subscription existingSubscription = _persistence.fetchByPrimaryKey(newSubscription.getPrimaryKey());

		assertNull(existingSubscription);
	}

	public void testUpdateNew() throws Exception {
		addSubscription();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Subscription newSubscription = _persistence.create(pk);

		newSubscription.setCompanyId(nextLong());
		newSubscription.setUserId(nextLong());
		newSubscription.setUserName(randomString());
		newSubscription.setCreateDate(nextDate());
		newSubscription.setModifiedDate(nextDate());
		newSubscription.setClassNameId(nextLong());
		newSubscription.setClassPK(nextLong());
		newSubscription.setFrequency(randomString());

		_persistence.update(newSubscription, false);

		Subscription existingSubscription = _persistence.findByPrimaryKey(newSubscription.getPrimaryKey());

		assertEquals(existingSubscription.getSubscriptionId(),
			newSubscription.getSubscriptionId());
		assertEquals(existingSubscription.getCompanyId(),
			newSubscription.getCompanyId());
		assertEquals(existingSubscription.getUserId(),
			newSubscription.getUserId());
		assertEquals(existingSubscription.getUserName(),
			newSubscription.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingSubscription.getCreateDate()),
			Time.getShortTimestamp(newSubscription.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingSubscription.getModifiedDate()),
			Time.getShortTimestamp(newSubscription.getModifiedDate()));
		assertEquals(existingSubscription.getClassNameId(),
			newSubscription.getClassNameId());
		assertEquals(existingSubscription.getClassPK(),
			newSubscription.getClassPK());
		assertEquals(existingSubscription.getFrequency(),
			newSubscription.getFrequency());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Subscription newSubscription = addSubscription();

		Subscription existingSubscription = _persistence.findByPrimaryKey(newSubscription.getPrimaryKey());

		assertEquals(existingSubscription, newSubscription);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchSubscriptionException");
		}
		catch (NoSuchSubscriptionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Subscription newSubscription = addSubscription();

		Subscription existingSubscription = _persistence.fetchByPrimaryKey(newSubscription.getPrimaryKey());

		assertEquals(existingSubscription, newSubscription);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Subscription missingSubscription = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSubscription);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Subscription newSubscription = addSubscription();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Subscription.class,
				Subscription.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("subscriptionId",
				newSubscription.getSubscriptionId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Subscription existingSubscription = (Subscription)result.get(0);

		assertEquals(existingSubscription, newSubscription);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Subscription.class,
				Subscription.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("subscriptionId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Subscription addSubscription() throws Exception {
		long pk = nextLong();

		Subscription subscription = _persistence.create(pk);

		subscription.setCompanyId(nextLong());
		subscription.setUserId(nextLong());
		subscription.setUserName(randomString());
		subscription.setCreateDate(nextDate());
		subscription.setModifiedDate(nextDate());
		subscription.setClassNameId(nextLong());
		subscription.setClassPK(nextLong());
		subscription.setFrequency(randomString());

		_persistence.update(subscription, false);

		return subscription;
	}

	private SubscriptionPersistence _persistence;
}