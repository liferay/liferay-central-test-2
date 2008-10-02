/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.wsrp.NoSuchConfiguredProducerException;
import com.liferay.wsrp.model.WSRPConfiguredProducer;

/**
 * <a href="WSRPConfiguredProducerPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WSRPConfiguredProducerPersistenceTest
	extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (WSRPConfiguredProducerPersistence)PortalBeanLocatorUtil.locate(WSRPConfiguredProducerPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WSRPConfiguredProducer wsrpConfiguredProducer = _persistence.create(pk);

		assertNotNull(wsrpConfiguredProducer);

		assertEquals(wsrpConfiguredProducer.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WSRPConfiguredProducer newWSRPConfiguredProducer = addWSRPConfiguredProducer();

		_persistence.remove(newWSRPConfiguredProducer);

		WSRPConfiguredProducer existingWSRPConfiguredProducer = _persistence.fetchByPrimaryKey(newWSRPConfiguredProducer.getPrimaryKey());

		assertNull(existingWSRPConfiguredProducer);
	}

	public void testUpdateNew() throws Exception {
		addWSRPConfiguredProducer();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WSRPConfiguredProducer newWSRPConfiguredProducer = _persistence.create(pk);

		newWSRPConfiguredProducer.setName(randomString());

		_persistence.update(newWSRPConfiguredProducer, false);

		WSRPConfiguredProducer existingWSRPConfiguredProducer = _persistence.findByPrimaryKey(newWSRPConfiguredProducer.getPrimaryKey());

		assertEquals(existingWSRPConfiguredProducer.getConfiguredProducerId(),
			newWSRPConfiguredProducer.getConfiguredProducerId());
		assertEquals(existingWSRPConfiguredProducer.getName(),
			newWSRPConfiguredProducer.getName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WSRPConfiguredProducer newWSRPConfiguredProducer = addWSRPConfiguredProducer();

		WSRPConfiguredProducer existingWSRPConfiguredProducer = _persistence.findByPrimaryKey(newWSRPConfiguredProducer.getPrimaryKey());

		assertEquals(existingWSRPConfiguredProducer, newWSRPConfiguredProducer);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchConfiguredProducerException");
		}
		catch (NoSuchConfiguredProducerException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WSRPConfiguredProducer newWSRPConfiguredProducer = addWSRPConfiguredProducer();

		WSRPConfiguredProducer existingWSRPConfiguredProducer = _persistence.fetchByPrimaryKey(newWSRPConfiguredProducer.getPrimaryKey());

		assertEquals(existingWSRPConfiguredProducer, newWSRPConfiguredProducer);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WSRPConfiguredProducer missingWSRPConfiguredProducer = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWSRPConfiguredProducer);
	}

	protected WSRPConfiguredProducer addWSRPConfiguredProducer()
		throws Exception {
		long pk = nextLong();

		WSRPConfiguredProducer wsrpConfiguredProducer = _persistence.create(pk);

		wsrpConfiguredProducer.setName(randomString());

		_persistence.update(wsrpConfiguredProducer, false);

		return wsrpConfiguredProducer;
	}

	private WSRPConfiguredProducerPersistence _persistence;
}