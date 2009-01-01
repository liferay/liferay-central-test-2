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

import com.liferay.wsrp.NoSuchProducerException;
import com.liferay.wsrp.model.WSRPProducer;

/**
 * <a href="WSRPProducerPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WSRPProducerPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (WSRPProducerPersistence)PortalBeanLocatorUtil.locate(WSRPProducerPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WSRPProducer wsrpProducer = _persistence.create(pk);

		assertNotNull(wsrpProducer);

		assertEquals(wsrpProducer.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		_persistence.remove(newWSRPProducer);

		WSRPProducer existingWSRPProducer = _persistence.fetchByPrimaryKey(newWSRPProducer.getPrimaryKey());

		assertNull(existingWSRPProducer);
	}

	public void testUpdateNew() throws Exception {
		addWSRPProducer();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WSRPProducer newWSRPProducer = _persistence.create(pk);

		newWSRPProducer.setPortalId(randomString());
		newWSRPProducer.setStatus(randomBoolean());
		newWSRPProducer.setNamespace(randomString());
		newWSRPProducer.setInstanceName(randomString());
		newWSRPProducer.setRequiresRegistration(randomBoolean());
		newWSRPProducer.setSupportsInbandRegistration(randomBoolean());
		newWSRPProducer.setVersion(randomString());
		newWSRPProducer.setOfferedPortlets(randomString());
		newWSRPProducer.setProducerProfileMap(randomString());
		newWSRPProducer.setRegistrationProperties(randomString());
		newWSRPProducer.setRegistrationValidatorClass(randomString());

		_persistence.update(newWSRPProducer, false);

		WSRPProducer existingWSRPProducer = _persistence.findByPrimaryKey(newWSRPProducer.getPrimaryKey());

		assertEquals(existingWSRPProducer.getProducerId(),
			newWSRPProducer.getProducerId());
		assertEquals(existingWSRPProducer.getPortalId(),
			newWSRPProducer.getPortalId());
		assertEquals(existingWSRPProducer.getStatus(),
			newWSRPProducer.getStatus());
		assertEquals(existingWSRPProducer.getNamespace(),
			newWSRPProducer.getNamespace());
		assertEquals(existingWSRPProducer.getInstanceName(),
			newWSRPProducer.getInstanceName());
		assertEquals(existingWSRPProducer.getRequiresRegistration(),
			newWSRPProducer.getRequiresRegistration());
		assertEquals(existingWSRPProducer.getSupportsInbandRegistration(),
			newWSRPProducer.getSupportsInbandRegistration());
		assertEquals(existingWSRPProducer.getVersion(),
			newWSRPProducer.getVersion());
		assertEquals(existingWSRPProducer.getOfferedPortlets(),
			newWSRPProducer.getOfferedPortlets());
		assertEquals(existingWSRPProducer.getProducerProfileMap(),
			newWSRPProducer.getProducerProfileMap());
		assertEquals(existingWSRPProducer.getRegistrationProperties(),
			newWSRPProducer.getRegistrationProperties());
		assertEquals(existingWSRPProducer.getRegistrationValidatorClass(),
			newWSRPProducer.getRegistrationValidatorClass());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		WSRPProducer existingWSRPProducer = _persistence.findByPrimaryKey(newWSRPProducer.getPrimaryKey());

		assertEquals(existingWSRPProducer, newWSRPProducer);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchProducerException");
		}
		catch (NoSuchProducerException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		WSRPProducer existingWSRPProducer = _persistence.fetchByPrimaryKey(newWSRPProducer.getPrimaryKey());

		assertEquals(existingWSRPProducer, newWSRPProducer);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WSRPProducer missingWSRPProducer = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWSRPProducer);
	}

	protected WSRPProducer addWSRPProducer() throws Exception {
		long pk = nextLong();

		WSRPProducer wsrpProducer = _persistence.create(pk);

		wsrpProducer.setPortalId(randomString());
		wsrpProducer.setStatus(randomBoolean());
		wsrpProducer.setNamespace(randomString());
		wsrpProducer.setInstanceName(randomString());
		wsrpProducer.setRequiresRegistration(randomBoolean());
		wsrpProducer.setSupportsInbandRegistration(randomBoolean());
		wsrpProducer.setVersion(randomString());
		wsrpProducer.setOfferedPortlets(randomString());
		wsrpProducer.setProducerProfileMap(randomString());
		wsrpProducer.setRegistrationProperties(randomString());
		wsrpProducer.setRegistrationValidatorClass(randomString());

		_persistence.update(wsrpProducer, false);

		return wsrpProducer;
	}

	private WSRPProducerPersistence _persistence;
}