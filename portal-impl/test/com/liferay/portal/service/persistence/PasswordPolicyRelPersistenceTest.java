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

import com.liferay.portal.NoSuchPasswordPolicyRelException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="PasswordPolicyRelPersistenceTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyRelPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PasswordPolicyRelPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyRelPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PasswordPolicyRel passwordPolicyRel = _persistence.create(pk);

		assertNotNull(passwordPolicyRel);

		assertEquals(passwordPolicyRel.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		_persistence.remove(newPasswordPolicyRel);

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.fetchByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertNull(existingPasswordPolicyRel);
	}

	public void testUpdateNew() throws Exception {
		addPasswordPolicyRel();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PasswordPolicyRel newPasswordPolicyRel = _persistence.create(pk);

		newPasswordPolicyRel.setPasswordPolicyId(nextLong());
		newPasswordPolicyRel.setClassNameId(nextLong());
		newPasswordPolicyRel.setClassPK(nextLong());

		_persistence.update(newPasswordPolicyRel, false);

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertEquals(existingPasswordPolicyRel.getPasswordPolicyRelId(),
			newPasswordPolicyRel.getPasswordPolicyRelId());
		assertEquals(existingPasswordPolicyRel.getPasswordPolicyId(),
			newPasswordPolicyRel.getPasswordPolicyId());
		assertEquals(existingPasswordPolicyRel.getClassNameId(),
			newPasswordPolicyRel.getClassNameId());
		assertEquals(existingPasswordPolicyRel.getClassPK(),
			newPasswordPolicyRel.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.findByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchPasswordPolicyRelException");
		}
		catch (NoSuchPasswordPolicyRelException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		PasswordPolicyRel existingPasswordPolicyRel = _persistence.fetchByPrimaryKey(newPasswordPolicyRel.getPrimaryKey());

		assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PasswordPolicyRel missingPasswordPolicyRel = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPasswordPolicyRel);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PasswordPolicyRel newPasswordPolicyRel = addPasswordPolicyRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyRelId",
				newPasswordPolicyRel.getPasswordPolicyRelId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PasswordPolicyRel existingPasswordPolicyRel = (PasswordPolicyRel)result.get(0);

		assertEquals(existingPasswordPolicyRel, newPasswordPolicyRel);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicyRel.class,
				PasswordPolicyRel.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyRelId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected PasswordPolicyRel addPasswordPolicyRel()
		throws Exception {
		long pk = nextLong();

		PasswordPolicyRel passwordPolicyRel = _persistence.create(pk);

		passwordPolicyRel.setPasswordPolicyId(nextLong());
		passwordPolicyRel.setClassNameId(nextLong());
		passwordPolicyRel.setClassPK(nextLong());

		_persistence.update(passwordPolicyRel, false);

		return passwordPolicyRel;
	}

	private PasswordPolicyRelPersistence _persistence;
}