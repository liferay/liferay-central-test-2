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

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="PortletItemPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletItemPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PortletItem portletItem = _persistence.create(pk);

		assertNotNull(portletItem);

		assertEquals(portletItem.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		_persistence.remove(newPortletItem);

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		assertNull(existingPortletItem);
	}

	public void testUpdateNew() throws Exception {
		addPortletItem();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PortletItem newPortletItem = _persistence.create(pk);

		newPortletItem.setGroupId(nextLong());
		newPortletItem.setCompanyId(nextLong());
		newPortletItem.setUserId(nextLong());
		newPortletItem.setUserName(randomString());
		newPortletItem.setCreateDate(nextDate());
		newPortletItem.setModifiedDate(nextDate());
		newPortletItem.setName(randomString());
		newPortletItem.setPortletId(randomString());
		newPortletItem.setClassNameId(nextLong());

		_persistence.update(newPortletItem, false);

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		assertEquals(existingPortletItem.getPortletItemId(),
			newPortletItem.getPortletItemId());
		assertEquals(existingPortletItem.getGroupId(),
			newPortletItem.getGroupId());
		assertEquals(existingPortletItem.getCompanyId(),
			newPortletItem.getCompanyId());
		assertEquals(existingPortletItem.getUserId(), newPortletItem.getUserId());
		assertEquals(existingPortletItem.getUserName(),
			newPortletItem.getUserName());
		assertEquals(Time.getShortTimestamp(existingPortletItem.getCreateDate()),
			Time.getShortTimestamp(newPortletItem.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingPortletItem.getModifiedDate()),
			Time.getShortTimestamp(newPortletItem.getModifiedDate()));
		assertEquals(existingPortletItem.getName(), newPortletItem.getName());
		assertEquals(existingPortletItem.getPortletId(),
			newPortletItem.getPortletId());
		assertEquals(existingPortletItem.getClassNameId(),
			newPortletItem.getClassNameId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		assertEquals(existingPortletItem, newPortletItem);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPortletItemException");
		}
		catch (NoSuchPortletItemException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		assertEquals(existingPortletItem, newPortletItem);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PortletItem missingPortletItem = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPortletItem);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				newPortletItem.getPortletItemId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PortletItem existingPortletItem = (PortletItem)result.get(0);

		assertEquals(existingPortletItem, newPortletItem);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected PortletItem addPortletItem() throws Exception {
		long pk = nextLong();

		PortletItem portletItem = _persistence.create(pk);

		portletItem.setGroupId(nextLong());
		portletItem.setCompanyId(nextLong());
		portletItem.setUserId(nextLong());
		portletItem.setUserName(randomString());
		portletItem.setCreateDate(nextDate());
		portletItem.setModifiedDate(nextDate());
		portletItem.setName(randomString());
		portletItem.setPortletId(randomString());
		portletItem.setClassNameId(nextLong());

		_persistence.update(portletItem, false);

		return portletItem;
	}

	private PortletItemPersistence _persistence;
}