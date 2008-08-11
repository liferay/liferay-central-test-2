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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.messageboards.NoSuchMailingException;
import com.liferay.portlet.messageboards.model.MBMailing;

/**
 * <a href="MBMailingPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMailingPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (MBMailingPersistence)PortalBeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBMailing mbMailing = _persistence.create(pk);

		assertNotNull(mbMailing);

		assertEquals(mbMailing.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBMailing newMBMailing = addMBMailing();

		_persistence.remove(newMBMailing);

		MBMailing existingMBMailing = _persistence.fetchByPrimaryKey(newMBMailing.getPrimaryKey());

		assertNull(existingMBMailing);
	}

	public void testUpdateNew() throws Exception {
		addMBMailing();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBMailing newMBMailing = _persistence.create(pk);

		newMBMailing.setUuid(randomString());
		newMBMailing.setGroupId(nextLong());
		newMBMailing.setCompanyId(nextLong());
		newMBMailing.setUserId(nextLong());
		newMBMailing.setUserName(randomString());
		newMBMailing.setCreateDate(nextDate());
		newMBMailing.setModifiedDate(nextDate());
		newMBMailing.setCategoryId(nextLong());
		newMBMailing.setMailingListAddress(randomString());
		newMBMailing.setMailAddress(randomString());
		newMBMailing.setMailInProtocol(randomString());
		newMBMailing.setMailInServerName(randomString());
		newMBMailing.setMailInUseSSL(randomBoolean());
		newMBMailing.setMailInServerPort(nextInt());
		newMBMailing.setMailInUserName(randomString());
		newMBMailing.setMailInPassword(randomString());
		newMBMailing.setMailInReadInterval(nextInt());
		newMBMailing.setMailOutConfigured(randomBoolean());
		newMBMailing.setMailOutServerName(randomString());
		newMBMailing.setMailOutUseSSL(randomBoolean());
		newMBMailing.setMailOutServerPort(nextInt());
		newMBMailing.setMailOutUserName(randomString());
		newMBMailing.setMailOutPassword(randomString());
		newMBMailing.setActive(randomBoolean());

		_persistence.update(newMBMailing, false);

		MBMailing existingMBMailing = _persistence.findByPrimaryKey(newMBMailing.getPrimaryKey());

		assertEquals(existingMBMailing.getUuid(), newMBMailing.getUuid());
		assertEquals(existingMBMailing.getMailingId(),
			newMBMailing.getMailingId());
		assertEquals(existingMBMailing.getGroupId(), newMBMailing.getGroupId());
		assertEquals(existingMBMailing.getCompanyId(),
			newMBMailing.getCompanyId());
		assertEquals(existingMBMailing.getUserId(), newMBMailing.getUserId());
		assertEquals(existingMBMailing.getUserName(), newMBMailing.getUserName());
		assertEquals(existingMBMailing.getCreateDate(),
			newMBMailing.getCreateDate());
		assertEquals(existingMBMailing.getModifiedDate(),
			newMBMailing.getModifiedDate());
		assertEquals(existingMBMailing.getCategoryId(),
			newMBMailing.getCategoryId());
		assertEquals(existingMBMailing.getMailingListAddress(),
			newMBMailing.getMailingListAddress());
		assertEquals(existingMBMailing.getMailAddress(),
			newMBMailing.getMailAddress());
		assertEquals(existingMBMailing.getMailInProtocol(),
			newMBMailing.getMailInProtocol());
		assertEquals(existingMBMailing.getMailInServerName(),
			newMBMailing.getMailInServerName());
		assertEquals(existingMBMailing.getMailInUseSSL(),
			newMBMailing.getMailInUseSSL());
		assertEquals(existingMBMailing.getMailInServerPort(),
			newMBMailing.getMailInServerPort());
		assertEquals(existingMBMailing.getMailInUserName(),
			newMBMailing.getMailInUserName());
		assertEquals(existingMBMailing.getMailInPassword(),
			newMBMailing.getMailInPassword());
		assertEquals(existingMBMailing.getMailInReadInterval(),
			newMBMailing.getMailInReadInterval());
		assertEquals(existingMBMailing.getMailOutConfigured(),
			newMBMailing.getMailOutConfigured());
		assertEquals(existingMBMailing.getMailOutServerName(),
			newMBMailing.getMailOutServerName());
		assertEquals(existingMBMailing.getMailOutUseSSL(),
			newMBMailing.getMailOutUseSSL());
		assertEquals(existingMBMailing.getMailOutServerPort(),
			newMBMailing.getMailOutServerPort());
		assertEquals(existingMBMailing.getMailOutUserName(),
			newMBMailing.getMailOutUserName());
		assertEquals(existingMBMailing.getMailOutPassword(),
			newMBMailing.getMailOutPassword());
		assertEquals(existingMBMailing.getActive(), newMBMailing.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBMailing newMBMailing = addMBMailing();

		MBMailing existingMBMailing = _persistence.findByPrimaryKey(newMBMailing.getPrimaryKey());

		assertEquals(existingMBMailing, newMBMailing);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchMailingException");
		}
		catch (NoSuchMailingException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBMailing newMBMailing = addMBMailing();

		MBMailing existingMBMailing = _persistence.fetchByPrimaryKey(newMBMailing.getPrimaryKey());

		assertEquals(existingMBMailing, newMBMailing);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBMailing missingMBMailing = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBMailing);
	}

	protected MBMailing addMBMailing() throws Exception {
		long pk = nextLong();

		MBMailing mbMailing = _persistence.create(pk);

		mbMailing.setUuid(randomString());
		mbMailing.setGroupId(nextLong());
		mbMailing.setCompanyId(nextLong());
		mbMailing.setUserId(nextLong());
		mbMailing.setUserName(randomString());
		mbMailing.setCreateDate(nextDate());
		mbMailing.setModifiedDate(nextDate());
		mbMailing.setCategoryId(nextLong());
		mbMailing.setMailingListAddress(randomString());
		mbMailing.setMailAddress(randomString());
		mbMailing.setMailInProtocol(randomString());
		mbMailing.setMailInServerName(randomString());
		mbMailing.setMailInUseSSL(randomBoolean());
		mbMailing.setMailInServerPort(nextInt());
		mbMailing.setMailInUserName(randomString());
		mbMailing.setMailInPassword(randomString());
		mbMailing.setMailInReadInterval(nextInt());
		mbMailing.setMailOutConfigured(randomBoolean());
		mbMailing.setMailOutServerName(randomString());
		mbMailing.setMailOutUseSSL(randomBoolean());
		mbMailing.setMailOutServerPort(nextInt());
		mbMailing.setMailOutUserName(randomString());
		mbMailing.setMailOutPassword(randomString());
		mbMailing.setActive(randomBoolean());

		_persistence.update(mbMailing, false);

		return mbMailing;
	}

	private static final String _TX_IMPL = MBMailingPersistence.class.getName() +
		".transaction";
	private MBMailingPersistence _persistence;
}