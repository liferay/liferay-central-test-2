/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.model.impl.PollsVoteModelImpl;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PollsVotePersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PollsVote> iterator = _pollsVotes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote pollsVote = _persistence.create(pk);

		Assert.assertNotNull(pollsVote);

		Assert.assertEquals(pollsVote.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		_persistence.remove(newPollsVote);

		PollsVote existingPollsVote = _persistence.fetchByPrimaryKey(newPollsVote.getPrimaryKey());

		Assert.assertNull(existingPollsVote);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPollsVote();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote newPollsVote = _persistence.create(pk);

		newPollsVote.setUuid(RandomTestUtil.randomString());

		newPollsVote.setGroupId(RandomTestUtil.nextLong());

		newPollsVote.setCompanyId(RandomTestUtil.nextLong());

		newPollsVote.setUserId(RandomTestUtil.nextLong());

		newPollsVote.setUserName(RandomTestUtil.randomString());

		newPollsVote.setCreateDate(RandomTestUtil.nextDate());

		newPollsVote.setModifiedDate(RandomTestUtil.nextDate());

		newPollsVote.setQuestionId(RandomTestUtil.nextLong());

		newPollsVote.setChoiceId(RandomTestUtil.nextLong());

		newPollsVote.setVoteDate(RandomTestUtil.nextDate());

		_pollsVotes.add(_persistence.update(newPollsVote));

		PollsVote existingPollsVote = _persistence.findByPrimaryKey(newPollsVote.getPrimaryKey());

		Assert.assertEquals(existingPollsVote.getUuid(), newPollsVote.getUuid());
		Assert.assertEquals(existingPollsVote.getVoteId(),
			newPollsVote.getVoteId());
		Assert.assertEquals(existingPollsVote.getGroupId(),
			newPollsVote.getGroupId());
		Assert.assertEquals(existingPollsVote.getCompanyId(),
			newPollsVote.getCompanyId());
		Assert.assertEquals(existingPollsVote.getUserId(),
			newPollsVote.getUserId());
		Assert.assertEquals(existingPollsVote.getUserName(),
			newPollsVote.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsVote.getCreateDate()),
			Time.getShortTimestamp(newPollsVote.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsVote.getModifiedDate()),
			Time.getShortTimestamp(newPollsVote.getModifiedDate()));
		Assert.assertEquals(existingPollsVote.getQuestionId(),
			newPollsVote.getQuestionId());
		Assert.assertEquals(existingPollsVote.getChoiceId(),
			newPollsVote.getChoiceId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsVote.getVoteDate()),
			Time.getShortTimestamp(newPollsVote.getVoteDate()));
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByQuestionId() {
		try {
			_persistence.countByQuestionId(RandomTestUtil.nextLong());

			_persistence.countByQuestionId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByChoiceId() {
		try {
			_persistence.countByChoiceId(RandomTestUtil.nextLong());

			_persistence.countByChoiceId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByQ_U() {
		try {
			_persistence.countByQ_U(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByQ_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		PollsVote existingPollsVote = _persistence.findByPrimaryKey(newPollsVote.getPrimaryKey());

		Assert.assertEquals(existingPollsVote, newPollsVote);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchVoteException");
		}
		catch (NoSuchVoteException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<PollsVote> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PollsVote", "uuid", true,
			"voteId", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"questionId", true, "choiceId", true, "voteDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		PollsVote existingPollsVote = _persistence.fetchByPrimaryKey(newPollsVote.getPrimaryKey());

		Assert.assertEquals(existingPollsVote, newPollsVote);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote missingPollsVote = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPollsVote);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PollsVote newPollsVote1 = addPollsVote();
		PollsVote newPollsVote2 = addPollsVote();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsVote1.getPrimaryKey());
		primaryKeys.add(newPollsVote2.getPrimaryKey());

		Map<Serializable, PollsVote> pollsVotes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, pollsVotes.size());
		Assert.assertEquals(newPollsVote1,
			pollsVotes.get(newPollsVote1.getPrimaryKey()));
		Assert.assertEquals(newPollsVote2,
			pollsVotes.get(newPollsVote2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PollsVote> pollsVotes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsVotes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PollsVote newPollsVote = addPollsVote();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsVote.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PollsVote> pollsVotes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsVotes.size());
		Assert.assertEquals(newPollsVote,
			pollsVotes.get(newPollsVote.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PollsVote> pollsVotes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsVotes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PollsVote newPollsVote = addPollsVote();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsVote.getPrimaryKey());

		Map<Serializable, PollsVote> pollsVotes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsVotes.size());
		Assert.assertEquals(newPollsVote,
			pollsVotes.get(newPollsVote.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PollsVoteLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PollsVote pollsVote = (PollsVote)object;

					Assert.assertNotNull(pollsVote);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PollsVote newPollsVote = addPollsVote();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsVote.class,
				PollsVote.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("voteId",
				newPollsVote.getVoteId()));

		List<PollsVote> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PollsVote existingPollsVote = result.get(0);

		Assert.assertEquals(existingPollsVote, newPollsVote);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsVote.class,
				PollsVote.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("voteId",
				RandomTestUtil.nextLong()));

		List<PollsVote> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PollsVote newPollsVote = addPollsVote();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsVote.class,
				PollsVote.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("voteId"));

		Object newVoteId = newPollsVote.getVoteId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("voteId",
				new Object[] { newVoteId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVoteId = result.get(0);

		Assert.assertEquals(existingVoteId, newVoteId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsVote.class,
				PollsVote.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("voteId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("voteId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PollsVote newPollsVote = addPollsVote();

		_persistence.clearCache();

		PollsVoteModelImpl existingPollsVoteModelImpl = (PollsVoteModelImpl)_persistence.findByPrimaryKey(newPollsVote.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingPollsVoteModelImpl.getUuid(),
				existingPollsVoteModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingPollsVoteModelImpl.getGroupId(),
			existingPollsVoteModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingPollsVoteModelImpl.getQuestionId(),
			existingPollsVoteModelImpl.getOriginalQuestionId());
		Assert.assertEquals(existingPollsVoteModelImpl.getUserId(),
			existingPollsVoteModelImpl.getOriginalUserId());
	}

	protected PollsVote addPollsVote() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote pollsVote = _persistence.create(pk);

		pollsVote.setUuid(RandomTestUtil.randomString());

		pollsVote.setGroupId(RandomTestUtil.nextLong());

		pollsVote.setCompanyId(RandomTestUtil.nextLong());

		pollsVote.setUserId(RandomTestUtil.nextLong());

		pollsVote.setUserName(RandomTestUtil.randomString());

		pollsVote.setCreateDate(RandomTestUtil.nextDate());

		pollsVote.setModifiedDate(RandomTestUtil.nextDate());

		pollsVote.setQuestionId(RandomTestUtil.nextLong());

		pollsVote.setChoiceId(RandomTestUtil.nextLong());

		pollsVote.setVoteDate(RandomTestUtil.nextDate());

		_pollsVotes.add(_persistence.update(pollsVote));

		return pollsVote;
	}

	private static Log _log = LogFactoryUtil.getLog(PollsVotePersistenceTest.class);
	private List<PollsVote> _pollsVotes = new ArrayList<PollsVote>();
	private ModelListener<PollsVote>[] _modelListeners;
	private PollsVotePersistence _persistence = PollsVoteUtil.getPersistence();
}