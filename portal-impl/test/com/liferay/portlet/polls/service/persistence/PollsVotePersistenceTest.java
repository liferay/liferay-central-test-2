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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsVote;

import java.util.List;

/**
 * <a href="PollsVotePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PollsVotePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PollsVotePersistence)PortalBeanLocatorUtil.locate(PollsVotePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PollsVote pollsVote = _persistence.create(pk);

		assertNotNull(pollsVote);

		assertEquals(pollsVote.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		_persistence.remove(newPollsVote);

		PollsVote existingPollsVote = _persistence.fetchByPrimaryKey(newPollsVote.getPrimaryKey());

		assertNull(existingPollsVote);
	}

	public void testUpdateNew() throws Exception {
		addPollsVote();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PollsVote newPollsVote = _persistence.create(pk);

		newPollsVote.setUserId(nextLong());
		newPollsVote.setQuestionId(nextLong());
		newPollsVote.setChoiceId(nextLong());
		newPollsVote.setVoteDate(nextDate());

		_persistence.update(newPollsVote, false);

		PollsVote existingPollsVote = _persistence.findByPrimaryKey(newPollsVote.getPrimaryKey());

		assertEquals(existingPollsVote.getVoteId(), newPollsVote.getVoteId());
		assertEquals(existingPollsVote.getUserId(), newPollsVote.getUserId());
		assertEquals(existingPollsVote.getQuestionId(),
			newPollsVote.getQuestionId());
		assertEquals(existingPollsVote.getChoiceId(), newPollsVote.getChoiceId());
		assertEquals(Time.getShortTimestamp(existingPollsVote.getVoteDate()),
			Time.getShortTimestamp(newPollsVote.getVoteDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		PollsVote existingPollsVote = _persistence.findByPrimaryKey(newPollsVote.getPrimaryKey());

		assertEquals(existingPollsVote, newPollsVote);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchVoteException");
		}
		catch (NoSuchVoteException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		PollsVote existingPollsVote = _persistence.fetchByPrimaryKey(newPollsVote.getPrimaryKey());

		assertEquals(existingPollsVote, newPollsVote);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PollsVote missingPollsVote = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPollsVote);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PollsVote newPollsVote = addPollsVote();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsVote.class,
				PollsVote.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("voteId",
				newPollsVote.getVoteId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PollsVote existingPollsVote = (PollsVote)result.get(0);

		assertEquals(existingPollsVote, newPollsVote);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsVote.class,
				PollsVote.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("voteId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected PollsVote addPollsVote() throws Exception {
		long pk = nextLong();

		PollsVote pollsVote = _persistence.create(pk);

		pollsVote.setUserId(nextLong());
		pollsVote.setQuestionId(nextLong());
		pollsVote.setChoiceId(nextLong());
		pollsVote.setVoteDate(nextDate());

		_persistence.update(pollsVote, false);

		return pollsVote;
	}

	private PollsVotePersistence _persistence;
}