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

package com.liferay.polls.service.persistence;

import com.liferay.polls.exception.NoSuchChoiceException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.impl.PollsChoiceModelImpl;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.PersistenceTestRule;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
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
@RunWith(Arquillian.class)
public class PollsChoicePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<PollsChoice> iterator = _pollsChoices.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsChoice pollsChoice = _persistence.create(pk);

		Assert.assertNotNull(pollsChoice);

		Assert.assertEquals(pollsChoice.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		_persistence.remove(newPollsChoice);

		PollsChoice existingPollsChoice = _persistence.fetchByPrimaryKey(newPollsChoice.getPrimaryKey());

		Assert.assertNull(existingPollsChoice);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPollsChoice();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsChoice newPollsChoice = _persistence.create(pk);

		newPollsChoice.setUuid(RandomTestUtil.randomString());

		newPollsChoice.setGroupId(RandomTestUtil.nextLong());

		newPollsChoice.setCompanyId(RandomTestUtil.nextLong());

		newPollsChoice.setUserId(RandomTestUtil.nextLong());

		newPollsChoice.setUserName(RandomTestUtil.randomString());

		newPollsChoice.setCreateDate(RandomTestUtil.nextDate());

		newPollsChoice.setModifiedDate(RandomTestUtil.nextDate());

		newPollsChoice.setQuestionId(RandomTestUtil.nextLong());

		newPollsChoice.setName(RandomTestUtil.randomString());

		newPollsChoice.setDescription(RandomTestUtil.randomString());

		_pollsChoices.add(_persistence.update(newPollsChoice));

		PollsChoice existingPollsChoice = _persistence.findByPrimaryKey(newPollsChoice.getPrimaryKey());

		Assert.assertEquals(existingPollsChoice.getUuid(),
			newPollsChoice.getUuid());
		Assert.assertEquals(existingPollsChoice.getChoiceId(),
			newPollsChoice.getChoiceId());
		Assert.assertEquals(existingPollsChoice.getGroupId(),
			newPollsChoice.getGroupId());
		Assert.assertEquals(existingPollsChoice.getCompanyId(),
			newPollsChoice.getCompanyId());
		Assert.assertEquals(existingPollsChoice.getUserId(),
			newPollsChoice.getUserId());
		Assert.assertEquals(existingPollsChoice.getUserName(),
			newPollsChoice.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsChoice.getCreateDate()),
			Time.getShortTimestamp(newPollsChoice.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsChoice.getModifiedDate()),
			Time.getShortTimestamp(newPollsChoice.getModifiedDate()));
		Assert.assertEquals(existingPollsChoice.getQuestionId(),
			newPollsChoice.getQuestionId());
		Assert.assertEquals(existingPollsChoice.getName(),
			newPollsChoice.getName());
		Assert.assertEquals(existingPollsChoice.getDescription(),
			newPollsChoice.getDescription());
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
	public void testCountByQ_N() {
		try {
			_persistence.countByQ_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByQ_N(0L, StringPool.NULL);

			_persistence.countByQ_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		PollsChoice existingPollsChoice = _persistence.findByPrimaryKey(newPollsChoice.getPrimaryKey());

		Assert.assertEquals(existingPollsChoice, newPollsChoice);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchChoiceException");
		}
		catch (NoSuchChoiceException nsee) {
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

	protected OrderByComparator<PollsChoice> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PollsChoice", "uuid", true,
			"choiceId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"questionId", true, "name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		PollsChoice existingPollsChoice = _persistence.fetchByPrimaryKey(newPollsChoice.getPrimaryKey());

		Assert.assertEquals(existingPollsChoice, newPollsChoice);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsChoice missingPollsChoice = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPollsChoice);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PollsChoice newPollsChoice1 = addPollsChoice();
		PollsChoice newPollsChoice2 = addPollsChoice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsChoice1.getPrimaryKey());
		primaryKeys.add(newPollsChoice2.getPrimaryKey());

		Map<Serializable, PollsChoice> pollsChoices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, pollsChoices.size());
		Assert.assertEquals(newPollsChoice1,
			pollsChoices.get(newPollsChoice1.getPrimaryKey()));
		Assert.assertEquals(newPollsChoice2,
			pollsChoices.get(newPollsChoice2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PollsChoice> pollsChoices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsChoices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsChoice.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PollsChoice> pollsChoices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsChoices.size());
		Assert.assertEquals(newPollsChoice,
			pollsChoices.get(newPollsChoice.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PollsChoice> pollsChoices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsChoices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsChoice.getPrimaryKey());

		Map<Serializable, PollsChoice> pollsChoices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsChoices.size());
		Assert.assertEquals(newPollsChoice,
			pollsChoices.get(newPollsChoice.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PollsChoiceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PollsChoice pollsChoice = (PollsChoice)object;

					Assert.assertNotNull(pollsChoice);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("choiceId",
				newPollsChoice.getChoiceId()));

		List<PollsChoice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PollsChoice existingPollsChoice = result.get(0);

		Assert.assertEquals(existingPollsChoice, newPollsChoice);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("choiceId",
				RandomTestUtil.nextLong()));

		List<PollsChoice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PollsChoice newPollsChoice = addPollsChoice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("choiceId"));

		Object newChoiceId = newPollsChoice.getChoiceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("choiceId",
				new Object[] { newChoiceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChoiceId = result.get(0);

		Assert.assertEquals(existingChoiceId, newChoiceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsChoice.class,
				PollsChoice.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("choiceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("choiceId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PollsChoice newPollsChoice = addPollsChoice();

		_persistence.clearCache();

		PollsChoiceModelImpl existingPollsChoiceModelImpl = (PollsChoiceModelImpl)_persistence.findByPrimaryKey(newPollsChoice.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingPollsChoiceModelImpl.getUuid(),
				existingPollsChoiceModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingPollsChoiceModelImpl.getGroupId(),
			existingPollsChoiceModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingPollsChoiceModelImpl.getQuestionId(),
			existingPollsChoiceModelImpl.getOriginalQuestionId());
		Assert.assertTrue(Validator.equals(
				existingPollsChoiceModelImpl.getName(),
				existingPollsChoiceModelImpl.getOriginalName()));
	}

	protected PollsChoice addPollsChoice() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsChoice pollsChoice = _persistence.create(pk);

		pollsChoice.setUuid(RandomTestUtil.randomString());

		pollsChoice.setGroupId(RandomTestUtil.nextLong());

		pollsChoice.setCompanyId(RandomTestUtil.nextLong());

		pollsChoice.setUserId(RandomTestUtil.nextLong());

		pollsChoice.setUserName(RandomTestUtil.randomString());

		pollsChoice.setCreateDate(RandomTestUtil.nextDate());

		pollsChoice.setModifiedDate(RandomTestUtil.nextDate());

		pollsChoice.setQuestionId(RandomTestUtil.nextLong());

		pollsChoice.setName(RandomTestUtil.randomString());

		pollsChoice.setDescription(RandomTestUtil.randomString());

		_pollsChoices.add(_persistence.update(pollsChoice));

		return pollsChoice;
	}

	private List<PollsChoice> _pollsChoices = new ArrayList<PollsChoice>();
	private PollsChoicePersistence _persistence = PollsChoiceUtil.getPersistence();
}