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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;

import java.util.List;

/**
 * <a href="MBMailingListPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBMailingListPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBMailingListPersistence)PortalBeanLocatorUtil.locate(MBMailingListPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBMailingList mbMailingList = _persistence.create(pk);

		assertNotNull(mbMailingList);

		assertEquals(mbMailingList.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		_persistence.remove(newMBMailingList);

		MBMailingList existingMBMailingList = _persistence.fetchByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertNull(existingMBMailingList);
	}

	public void testUpdateNew() throws Exception {
		addMBMailingList();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBMailingList newMBMailingList = _persistence.create(pk);

		newMBMailingList.setUuid(randomString());
		newMBMailingList.setGroupId(nextLong());
		newMBMailingList.setCompanyId(nextLong());
		newMBMailingList.setUserId(nextLong());
		newMBMailingList.setUserName(randomString());
		newMBMailingList.setCreateDate(nextDate());
		newMBMailingList.setModifiedDate(nextDate());
		newMBMailingList.setCategoryId(nextLong());
		newMBMailingList.setEmailAddress(randomString());
		newMBMailingList.setInProtocol(randomString());
		newMBMailingList.setInServerName(randomString());
		newMBMailingList.setInServerPort(nextInt());
		newMBMailingList.setInUseSSL(randomBoolean());
		newMBMailingList.setInUserName(randomString());
		newMBMailingList.setInPassword(randomString());
		newMBMailingList.setInReadInterval(nextInt());
		newMBMailingList.setOutEmailAddress(randomString());
		newMBMailingList.setOutCustom(randomBoolean());
		newMBMailingList.setOutServerName(randomString());
		newMBMailingList.setOutServerPort(nextInt());
		newMBMailingList.setOutUseSSL(randomBoolean());
		newMBMailingList.setOutUserName(randomString());
		newMBMailingList.setOutPassword(randomString());
		newMBMailingList.setActive(randomBoolean());

		_persistence.update(newMBMailingList, false);

		MBMailingList existingMBMailingList = _persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertEquals(existingMBMailingList.getUuid(), newMBMailingList.getUuid());
		assertEquals(existingMBMailingList.getMailingListId(),
			newMBMailingList.getMailingListId());
		assertEquals(existingMBMailingList.getGroupId(),
			newMBMailingList.getGroupId());
		assertEquals(existingMBMailingList.getCompanyId(),
			newMBMailingList.getCompanyId());
		assertEquals(existingMBMailingList.getUserId(),
			newMBMailingList.getUserId());
		assertEquals(existingMBMailingList.getUserName(),
			newMBMailingList.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingMBMailingList.getCreateDate()),
			Time.getShortTimestamp(newMBMailingList.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingMBMailingList.getModifiedDate()),
			Time.getShortTimestamp(newMBMailingList.getModifiedDate()));
		assertEquals(existingMBMailingList.getCategoryId(),
			newMBMailingList.getCategoryId());
		assertEquals(existingMBMailingList.getEmailAddress(),
			newMBMailingList.getEmailAddress());
		assertEquals(existingMBMailingList.getInProtocol(),
			newMBMailingList.getInProtocol());
		assertEquals(existingMBMailingList.getInServerName(),
			newMBMailingList.getInServerName());
		assertEquals(existingMBMailingList.getInServerPort(),
			newMBMailingList.getInServerPort());
		assertEquals(existingMBMailingList.getInUseSSL(),
			newMBMailingList.getInUseSSL());
		assertEquals(existingMBMailingList.getInUserName(),
			newMBMailingList.getInUserName());
		assertEquals(existingMBMailingList.getInPassword(),
			newMBMailingList.getInPassword());
		assertEquals(existingMBMailingList.getInReadInterval(),
			newMBMailingList.getInReadInterval());
		assertEquals(existingMBMailingList.getOutEmailAddress(),
			newMBMailingList.getOutEmailAddress());
		assertEquals(existingMBMailingList.getOutCustom(),
			newMBMailingList.getOutCustom());
		assertEquals(existingMBMailingList.getOutServerName(),
			newMBMailingList.getOutServerName());
		assertEquals(existingMBMailingList.getOutServerPort(),
			newMBMailingList.getOutServerPort());
		assertEquals(existingMBMailingList.getOutUseSSL(),
			newMBMailingList.getOutUseSSL());
		assertEquals(existingMBMailingList.getOutUserName(),
			newMBMailingList.getOutUserName());
		assertEquals(existingMBMailingList.getOutPassword(),
			newMBMailingList.getOutPassword());
		assertEquals(existingMBMailingList.getActive(),
			newMBMailingList.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		MBMailingList existingMBMailingList = _persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertEquals(existingMBMailingList, newMBMailingList);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchMailingListException");
		}
		catch (NoSuchMailingListException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		MBMailingList existingMBMailingList = _persistence.fetchByPrimaryKey(newMBMailingList.getPrimaryKey());

		assertEquals(existingMBMailingList, newMBMailingList);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBMailingList missingMBMailingList = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBMailingList);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				MBMailingList.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("mailingListId",
				newMBMailingList.getMailingListId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBMailingList existingMBMailingList = (MBMailingList)result.get(0);

		assertEquals(existingMBMailingList, newMBMailingList);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				MBMailingList.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("mailingListId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected MBMailingList addMBMailingList() throws Exception {
		long pk = nextLong();

		MBMailingList mbMailingList = _persistence.create(pk);

		mbMailingList.setUuid(randomString());
		mbMailingList.setGroupId(nextLong());
		mbMailingList.setCompanyId(nextLong());
		mbMailingList.setUserId(nextLong());
		mbMailingList.setUserName(randomString());
		mbMailingList.setCreateDate(nextDate());
		mbMailingList.setModifiedDate(nextDate());
		mbMailingList.setCategoryId(nextLong());
		mbMailingList.setEmailAddress(randomString());
		mbMailingList.setInProtocol(randomString());
		mbMailingList.setInServerName(randomString());
		mbMailingList.setInServerPort(nextInt());
		mbMailingList.setInUseSSL(randomBoolean());
		mbMailingList.setInUserName(randomString());
		mbMailingList.setInPassword(randomString());
		mbMailingList.setInReadInterval(nextInt());
		mbMailingList.setOutEmailAddress(randomString());
		mbMailingList.setOutCustom(randomBoolean());
		mbMailingList.setOutServerName(randomString());
		mbMailingList.setOutServerPort(nextInt());
		mbMailingList.setOutUseSSL(randomBoolean());
		mbMailingList.setOutUserName(randomString());
		mbMailingList.setOutPassword(randomString());
		mbMailingList.setActive(randomBoolean());

		_persistence.update(mbMailingList, false);

		return mbMailingList;
	}

	private MBMailingListPersistence _persistence;
}