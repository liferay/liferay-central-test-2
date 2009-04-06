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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchMembershipInvitationException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.MembershipInvitation;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * <a href="MembershipInvitationPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MembershipInvitationPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (MembershipInvitationPersistence)PortalBeanLocatorUtil.locate(MembershipInvitationPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MembershipInvitation membershipInvitation = _persistence.create(pk);

		assertNotNull(membershipInvitation);

		assertEquals(membershipInvitation.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MembershipInvitation newMembershipInvitation = addMembershipInvitation();

		_persistence.remove(newMembershipInvitation);

		MembershipInvitation existingMembershipInvitation = _persistence.fetchByPrimaryKey(newMembershipInvitation.getPrimaryKey());

		assertNull(existingMembershipInvitation);
	}

	public void testUpdateNew() throws Exception {
		addMembershipInvitation();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MembershipInvitation newMembershipInvitation = _persistence.create(pk);

		newMembershipInvitation.setCompanyId(nextLong());
		newMembershipInvitation.setUserId(nextLong());
		newMembershipInvitation.setCreateDate(nextDate());
		newMembershipInvitation.setAcceptedDate(nextDate());
		newMembershipInvitation.setDeclinedDate(nextDate());
		newMembershipInvitation.setInvitedUserId(nextLong());
		newMembershipInvitation.setGroupId(nextLong());
		newMembershipInvitation.setKey(randomString());

		_persistence.update(newMembershipInvitation, false);

		MembershipInvitation existingMembershipInvitation = _persistence.findByPrimaryKey(newMembershipInvitation.getPrimaryKey());

		assertEquals(existingMembershipInvitation.getMembershipRequestId(),
			newMembershipInvitation.getMembershipRequestId());
		assertEquals(existingMembershipInvitation.getCompanyId(),
			newMembershipInvitation.getCompanyId());
		assertEquals(existingMembershipInvitation.getUserId(),
			newMembershipInvitation.getUserId());
		assertEquals(Time.getShortTimestamp(
				existingMembershipInvitation.getCreateDate()),
			Time.getShortTimestamp(newMembershipInvitation.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingMembershipInvitation.getAcceptedDate()),
			Time.getShortTimestamp(newMembershipInvitation.getAcceptedDate()));
		assertEquals(Time.getShortTimestamp(
				existingMembershipInvitation.getDeclinedDate()),
			Time.getShortTimestamp(newMembershipInvitation.getDeclinedDate()));
		assertEquals(existingMembershipInvitation.getInvitedUserId(),
			newMembershipInvitation.getInvitedUserId());
		assertEquals(existingMembershipInvitation.getGroupId(),
			newMembershipInvitation.getGroupId());
		assertEquals(existingMembershipInvitation.getKey(),
			newMembershipInvitation.getKey());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MembershipInvitation newMembershipInvitation = addMembershipInvitation();

		MembershipInvitation existingMembershipInvitation = _persistence.findByPrimaryKey(newMembershipInvitation.getPrimaryKey());

		assertEquals(existingMembershipInvitation, newMembershipInvitation);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchMembershipInvitationException");
		}
		catch (NoSuchMembershipInvitationException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MembershipInvitation newMembershipInvitation = addMembershipInvitation();

		MembershipInvitation existingMembershipInvitation = _persistence.fetchByPrimaryKey(newMembershipInvitation.getPrimaryKey());

		assertEquals(existingMembershipInvitation, newMembershipInvitation);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MembershipInvitation missingMembershipInvitation = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMembershipInvitation);
	}

	protected MembershipInvitation addMembershipInvitation()
		throws Exception {
		long pk = nextLong();

		MembershipInvitation membershipInvitation = _persistence.create(pk);

		membershipInvitation.setCompanyId(nextLong());
		membershipInvitation.setUserId(nextLong());
		membershipInvitation.setCreateDate(nextDate());
		membershipInvitation.setAcceptedDate(nextDate());
		membershipInvitation.setDeclinedDate(nextDate());
		membershipInvitation.setInvitedUserId(nextLong());
		membershipInvitation.setGroupId(nextLong());
		membershipInvitation.setKey(randomString());

		_persistence.update(membershipInvitation, false);

		return membershipInvitation;
	}

	private MembershipInvitationPersistence _persistence;
}