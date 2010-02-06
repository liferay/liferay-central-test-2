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

import com.liferay.portal.NoSuchEmailAddressException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="EmailAddressPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EmailAddressPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (EmailAddressPersistence)PortalBeanLocatorUtil.locate(EmailAddressPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		EmailAddress emailAddress = _persistence.create(pk);

		assertNotNull(emailAddress);

		assertEquals(emailAddress.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		_persistence.remove(newEmailAddress);

		EmailAddress existingEmailAddress = _persistence.fetchByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertNull(existingEmailAddress);
	}

	public void testUpdateNew() throws Exception {
		addEmailAddress();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		EmailAddress newEmailAddress = _persistence.create(pk);

		newEmailAddress.setCompanyId(nextLong());
		newEmailAddress.setUserId(nextLong());
		newEmailAddress.setUserName(randomString());
		newEmailAddress.setCreateDate(nextDate());
		newEmailAddress.setModifiedDate(nextDate());
		newEmailAddress.setClassNameId(nextLong());
		newEmailAddress.setClassPK(nextLong());
		newEmailAddress.setAddress(randomString());
		newEmailAddress.setTypeId(nextInt());
		newEmailAddress.setPrimary(randomBoolean());

		_persistence.update(newEmailAddress, false);

		EmailAddress existingEmailAddress = _persistence.findByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertEquals(existingEmailAddress.getEmailAddressId(),
			newEmailAddress.getEmailAddressId());
		assertEquals(existingEmailAddress.getCompanyId(),
			newEmailAddress.getCompanyId());
		assertEquals(existingEmailAddress.getUserId(),
			newEmailAddress.getUserId());
		assertEquals(existingEmailAddress.getUserName(),
			newEmailAddress.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingEmailAddress.getCreateDate()),
			Time.getShortTimestamp(newEmailAddress.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingEmailAddress.getModifiedDate()),
			Time.getShortTimestamp(newEmailAddress.getModifiedDate()));
		assertEquals(existingEmailAddress.getClassNameId(),
			newEmailAddress.getClassNameId());
		assertEquals(existingEmailAddress.getClassPK(),
			newEmailAddress.getClassPK());
		assertEquals(existingEmailAddress.getAddress(),
			newEmailAddress.getAddress());
		assertEquals(existingEmailAddress.getTypeId(),
			newEmailAddress.getTypeId());
		assertEquals(existingEmailAddress.getPrimary(),
			newEmailAddress.getPrimary());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		EmailAddress existingEmailAddress = _persistence.findByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertEquals(existingEmailAddress, newEmailAddress);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEmailAddressException");
		}
		catch (NoSuchEmailAddressException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		EmailAddress existingEmailAddress = _persistence.fetchByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertEquals(existingEmailAddress, newEmailAddress);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		EmailAddress missingEmailAddress = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingEmailAddress);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("emailAddressId",
				newEmailAddress.getEmailAddressId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		EmailAddress existingEmailAddress = (EmailAddress)result.get(0);

		assertEquals(existingEmailAddress, newEmailAddress);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("emailAddressId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected EmailAddress addEmailAddress() throws Exception {
		long pk = nextLong();

		EmailAddress emailAddress = _persistence.create(pk);

		emailAddress.setCompanyId(nextLong());
		emailAddress.setUserId(nextLong());
		emailAddress.setUserName(randomString());
		emailAddress.setCreateDate(nextDate());
		emailAddress.setModifiedDate(nextDate());
		emailAddress.setClassNameId(nextLong());
		emailAddress.setClassPK(nextLong());
		emailAddress.setAddress(randomString());
		emailAddress.setTypeId(nextInt());
		emailAddress.setPrimary(randomBoolean());

		_persistence.update(emailAddress, false);

		return emailAddress;
	}

	private EmailAddressPersistence _persistence;
}