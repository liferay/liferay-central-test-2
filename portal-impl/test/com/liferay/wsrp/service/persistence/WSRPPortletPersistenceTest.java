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

import com.liferay.wsrp.NoSuchPortletException;
import com.liferay.wsrp.model.WSRPPortlet;

/**
 * <a href="WSRPPortletPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WSRPPortletPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (WSRPPortletPersistence)PortalBeanLocatorUtil.locate(WSRPPortletPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WSRPPortlet wsrpPortlet = _persistence.create(pk);

		assertNotNull(wsrpPortlet);

		assertEquals(wsrpPortlet.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WSRPPortlet newWSRPPortlet = addWSRPPortlet();

		_persistence.remove(newWSRPPortlet);

		WSRPPortlet existingWSRPPortlet = _persistence.fetchByPrimaryKey(newWSRPPortlet.getPrimaryKey());

		assertNull(existingWSRPPortlet);
	}

	public void testUpdateNew() throws Exception {
		addWSRPPortlet();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WSRPPortlet newWSRPPortlet = _persistence.create(pk);

		newWSRPPortlet.setName(randomString());
		newWSRPPortlet.setChannelName(randomString());
		newWSRPPortlet.setTitle(randomString());
		newWSRPPortlet.setShortTitle(randomString());
		newWSRPPortlet.setDisplayName(randomString());
		newWSRPPortlet.setKeywords(randomString());
		newWSRPPortlet.setStatus(nextInt());
		newWSRPPortlet.setProducerEntityId(randomString());
		newWSRPPortlet.setConsumerId(randomString());
		newWSRPPortlet.setPortletHandle(randomString());
		newWSRPPortlet.setMimeTypes(randomString());

		_persistence.update(newWSRPPortlet, false);

		WSRPPortlet existingWSRPPortlet = _persistence.findByPrimaryKey(newWSRPPortlet.getPrimaryKey());

		assertEquals(existingWSRPPortlet.getPortletId(),
			newWSRPPortlet.getPortletId());
		assertEquals(existingWSRPPortlet.getName(), newWSRPPortlet.getName());
		assertEquals(existingWSRPPortlet.getChannelName(),
			newWSRPPortlet.getChannelName());
		assertEquals(existingWSRPPortlet.getTitle(), newWSRPPortlet.getTitle());
		assertEquals(existingWSRPPortlet.getShortTitle(),
			newWSRPPortlet.getShortTitle());
		assertEquals(existingWSRPPortlet.getDisplayName(),
			newWSRPPortlet.getDisplayName());
		assertEquals(existingWSRPPortlet.getKeywords(),
			newWSRPPortlet.getKeywords());
		assertEquals(existingWSRPPortlet.getStatus(), newWSRPPortlet.getStatus());
		assertEquals(existingWSRPPortlet.getProducerEntityId(),
			newWSRPPortlet.getProducerEntityId());
		assertEquals(existingWSRPPortlet.getConsumerId(),
			newWSRPPortlet.getConsumerId());
		assertEquals(existingWSRPPortlet.getPortletHandle(),
			newWSRPPortlet.getPortletHandle());
		assertEquals(existingWSRPPortlet.getMimeTypes(),
			newWSRPPortlet.getMimeTypes());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WSRPPortlet newWSRPPortlet = addWSRPPortlet();

		WSRPPortlet existingWSRPPortlet = _persistence.findByPrimaryKey(newWSRPPortlet.getPrimaryKey());

		assertEquals(existingWSRPPortlet, newWSRPPortlet);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPortletException");
		}
		catch (NoSuchPortletException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WSRPPortlet newWSRPPortlet = addWSRPPortlet();

		WSRPPortlet existingWSRPPortlet = _persistence.fetchByPrimaryKey(newWSRPPortlet.getPrimaryKey());

		assertEquals(existingWSRPPortlet, newWSRPPortlet);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WSRPPortlet missingWSRPPortlet = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWSRPPortlet);
	}

	protected WSRPPortlet addWSRPPortlet() throws Exception {
		long pk = nextLong();

		WSRPPortlet wsrpPortlet = _persistence.create(pk);

		wsrpPortlet.setName(randomString());
		wsrpPortlet.setChannelName(randomString());
		wsrpPortlet.setTitle(randomString());
		wsrpPortlet.setShortTitle(randomString());
		wsrpPortlet.setDisplayName(randomString());
		wsrpPortlet.setKeywords(randomString());
		wsrpPortlet.setStatus(nextInt());
		wsrpPortlet.setProducerEntityId(randomString());
		wsrpPortlet.setConsumerId(randomString());
		wsrpPortlet.setPortletHandle(randomString());
		wsrpPortlet.setMimeTypes(randomString());

		_persistence.update(wsrpPortlet, false);

		return wsrpPortlet;
	}

	private WSRPPortletPersistence _persistence;
}