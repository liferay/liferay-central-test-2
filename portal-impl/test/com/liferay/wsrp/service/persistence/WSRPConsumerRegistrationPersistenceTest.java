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

package com.liferay.wsrp.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.wsrp.NoSuchConsumerRegistrationException;
import com.liferay.wsrp.model.WSRPConsumerRegistration;

/**
 * <a href="WSRPConsumerRegistrationPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WSRPConsumerRegistrationPersistenceTest
	extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (WSRPConsumerRegistrationPersistence)PortalBeanLocatorUtil.locate(WSRPConsumerRegistrationPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WSRPConsumerRegistration wsrpConsumerRegistration = _persistence.create(pk);

		assertNotNull(wsrpConsumerRegistration);

		assertEquals(wsrpConsumerRegistration.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WSRPConsumerRegistration newWSRPConsumerRegistration = addWSRPConsumerRegistration();

		_persistence.remove(newWSRPConsumerRegistration);

		WSRPConsumerRegistration existingWSRPConsumerRegistration = _persistence.fetchByPrimaryKey(newWSRPConsumerRegistration.getPrimaryKey());

		assertNull(existingWSRPConsumerRegistration);
	}

	public void testUpdateNew() throws Exception {
		addWSRPConsumerRegistration();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WSRPConsumerRegistration newWSRPConsumerRegistration = _persistence.create(pk);

		newWSRPConsumerRegistration.setConsumerName(randomString());
		newWSRPConsumerRegistration.setStatus(randomBoolean());
		newWSRPConsumerRegistration.setRegistrationHandle(randomString());
		newWSRPConsumerRegistration.setRegistrationData(randomString());
		newWSRPConsumerRegistration.setLifetimeTerminationTime(randomString());
		newWSRPConsumerRegistration.setProducerKey(randomString());

		_persistence.update(newWSRPConsumerRegistration, false);

		WSRPConsumerRegistration existingWSRPConsumerRegistration = _persistence.findByPrimaryKey(newWSRPConsumerRegistration.getPrimaryKey());

		assertEquals(existingWSRPConsumerRegistration.getConsumerRegistrationId(),
			newWSRPConsumerRegistration.getConsumerRegistrationId());
		assertEquals(existingWSRPConsumerRegistration.getConsumerName(),
			newWSRPConsumerRegistration.getConsumerName());
		assertEquals(existingWSRPConsumerRegistration.getStatus(),
			newWSRPConsumerRegistration.getStatus());
		assertEquals(existingWSRPConsumerRegistration.getRegistrationHandle(),
			newWSRPConsumerRegistration.getRegistrationHandle());
		assertEquals(existingWSRPConsumerRegistration.getRegistrationData(),
			newWSRPConsumerRegistration.getRegistrationData());
		assertEquals(existingWSRPConsumerRegistration.getLifetimeTerminationTime(),
			newWSRPConsumerRegistration.getLifetimeTerminationTime());
		assertEquals(existingWSRPConsumerRegistration.getProducerKey(),
			newWSRPConsumerRegistration.getProducerKey());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WSRPConsumerRegistration newWSRPConsumerRegistration = addWSRPConsumerRegistration();

		WSRPConsumerRegistration existingWSRPConsumerRegistration = _persistence.findByPrimaryKey(newWSRPConsumerRegistration.getPrimaryKey());

		assertEquals(existingWSRPConsumerRegistration,
			newWSRPConsumerRegistration);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchConsumerRegistrationException");
		}
		catch (NoSuchConsumerRegistrationException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WSRPConsumerRegistration newWSRPConsumerRegistration = addWSRPConsumerRegistration();

		WSRPConsumerRegistration existingWSRPConsumerRegistration = _persistence.fetchByPrimaryKey(newWSRPConsumerRegistration.getPrimaryKey());

		assertEquals(existingWSRPConsumerRegistration,
			newWSRPConsumerRegistration);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WSRPConsumerRegistration missingWSRPConsumerRegistration = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWSRPConsumerRegistration);
	}

	protected WSRPConsumerRegistration addWSRPConsumerRegistration()
		throws Exception {
		long pk = nextLong();

		WSRPConsumerRegistration wsrpConsumerRegistration = _persistence.create(pk);

		wsrpConsumerRegistration.setConsumerName(randomString());
		wsrpConsumerRegistration.setStatus(randomBoolean());
		wsrpConsumerRegistration.setRegistrationHandle(randomString());
		wsrpConsumerRegistration.setRegistrationData(randomString());
		wsrpConsumerRegistration.setLifetimeTerminationTime(randomString());
		wsrpConsumerRegistration.setProducerKey(randomString());

		_persistence.update(wsrpConsumerRegistration, false);

		return wsrpConsumerRegistration;
	}

	private WSRPConsumerRegistrationPersistence _persistence;
}