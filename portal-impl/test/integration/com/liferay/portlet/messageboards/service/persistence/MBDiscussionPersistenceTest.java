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

package com.liferay.portlet.messageboards.service.persistence;

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
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.PersistenceTestRule;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionModelImpl;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

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
public class MBDiscussionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<MBDiscussion> iterator = _mbDiscussions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBDiscussion mbDiscussion = _persistence.create(pk);

		Assert.assertNotNull(mbDiscussion);

		Assert.assertEquals(mbDiscussion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		_persistence.remove(newMBDiscussion);

		MBDiscussion existingMBDiscussion = _persistence.fetchByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertNull(existingMBDiscussion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBDiscussion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBDiscussion newMBDiscussion = _persistence.create(pk);

		newMBDiscussion.setUuid(RandomTestUtil.randomString());

		newMBDiscussion.setGroupId(RandomTestUtil.nextLong());

		newMBDiscussion.setCompanyId(RandomTestUtil.nextLong());

		newMBDiscussion.setUserId(RandomTestUtil.nextLong());

		newMBDiscussion.setUserName(RandomTestUtil.randomString());

		newMBDiscussion.setCreateDate(RandomTestUtil.nextDate());

		newMBDiscussion.setModifiedDate(RandomTestUtil.nextDate());

		newMBDiscussion.setClassNameId(RandomTestUtil.nextLong());

		newMBDiscussion.setClassPK(RandomTestUtil.nextLong());

		newMBDiscussion.setThreadId(RandomTestUtil.nextLong());

		_mbDiscussions.add(_persistence.update(newMBDiscussion));

		MBDiscussion existingMBDiscussion = _persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertEquals(existingMBDiscussion.getUuid(),
			newMBDiscussion.getUuid());
		Assert.assertEquals(existingMBDiscussion.getDiscussionId(),
			newMBDiscussion.getDiscussionId());
		Assert.assertEquals(existingMBDiscussion.getGroupId(),
			newMBDiscussion.getGroupId());
		Assert.assertEquals(existingMBDiscussion.getCompanyId(),
			newMBDiscussion.getCompanyId());
		Assert.assertEquals(existingMBDiscussion.getUserId(),
			newMBDiscussion.getUserId());
		Assert.assertEquals(existingMBDiscussion.getUserName(),
			newMBDiscussion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBDiscussion.getCreateDate()),
			Time.getShortTimestamp(newMBDiscussion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBDiscussion.getModifiedDate()),
			Time.getShortTimestamp(newMBDiscussion.getModifiedDate()));
		Assert.assertEquals(existingMBDiscussion.getClassNameId(),
			newMBDiscussion.getClassNameId());
		Assert.assertEquals(existingMBDiscussion.getClassPK(),
			newMBDiscussion.getClassPK());
		Assert.assertEquals(existingMBDiscussion.getThreadId(),
			newMBDiscussion.getThreadId());
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
	public void testCountByClassNameId() {
		try {
			_persistence.countByClassNameId(RandomTestUtil.nextLong());

			_persistence.countByClassNameId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByThreadId() {
		try {
			_persistence.countByThreadId(RandomTestUtil.nextLong());

			_persistence.countByThreadId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		MBDiscussion existingMBDiscussion = _persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchDiscussionException");
		}
		catch (NoSuchDiscussionException nsee) {
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

	protected OrderByComparator<MBDiscussion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MBDiscussion", "uuid",
			true, "discussionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"threadId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		MBDiscussion existingMBDiscussion = _persistence.fetchByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBDiscussion missingMBDiscussion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBDiscussion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MBDiscussion newMBDiscussion1 = addMBDiscussion();
		MBDiscussion newMBDiscussion2 = addMBDiscussion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBDiscussion1.getPrimaryKey());
		primaryKeys.add(newMBDiscussion2.getPrimaryKey());

		Map<Serializable, MBDiscussion> mbDiscussions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mbDiscussions.size());
		Assert.assertEquals(newMBDiscussion1,
			mbDiscussions.get(newMBDiscussion1.getPrimaryKey()));
		Assert.assertEquals(newMBDiscussion2,
			mbDiscussions.get(newMBDiscussion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MBDiscussion> mbDiscussions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbDiscussions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBDiscussion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MBDiscussion> mbDiscussions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbDiscussions.size());
		Assert.assertEquals(newMBDiscussion,
			mbDiscussions.get(newMBDiscussion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MBDiscussion> mbDiscussions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbDiscussions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBDiscussion.getPrimaryKey());

		Map<Serializable, MBDiscussion> mbDiscussions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbDiscussions.size());
		Assert.assertEquals(newMBDiscussion,
			mbDiscussions.get(newMBDiscussion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MBDiscussionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MBDiscussion mbDiscussion = (MBDiscussion)object;

					Assert.assertNotNull(mbDiscussion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("discussionId",
				newMBDiscussion.getDiscussionId()));

		List<MBDiscussion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBDiscussion existingMBDiscussion = result.get(0);

		Assert.assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("discussionId",
				RandomTestUtil.nextLong()));

		List<MBDiscussion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"discussionId"));

		Object newDiscussionId = newMBDiscussion.getDiscussionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("discussionId",
				new Object[] { newDiscussionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDiscussionId = result.get(0);

		Assert.assertEquals(existingDiscussionId, newDiscussionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"discussionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("discussionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBDiscussion newMBDiscussion = addMBDiscussion();

		_persistence.clearCache();

		MBDiscussionModelImpl existingMBDiscussionModelImpl = (MBDiscussionModelImpl)_persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMBDiscussionModelImpl.getUuid(),
				existingMBDiscussionModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMBDiscussionModelImpl.getGroupId(),
			existingMBDiscussionModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingMBDiscussionModelImpl.getThreadId(),
			existingMBDiscussionModelImpl.getOriginalThreadId());

		Assert.assertEquals(existingMBDiscussionModelImpl.getClassNameId(),
			existingMBDiscussionModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingMBDiscussionModelImpl.getClassPK(),
			existingMBDiscussionModelImpl.getOriginalClassPK());
	}

	protected MBDiscussion addMBDiscussion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBDiscussion mbDiscussion = _persistence.create(pk);

		mbDiscussion.setUuid(RandomTestUtil.randomString());

		mbDiscussion.setGroupId(RandomTestUtil.nextLong());

		mbDiscussion.setCompanyId(RandomTestUtil.nextLong());

		mbDiscussion.setUserId(RandomTestUtil.nextLong());

		mbDiscussion.setUserName(RandomTestUtil.randomString());

		mbDiscussion.setCreateDate(RandomTestUtil.nextDate());

		mbDiscussion.setModifiedDate(RandomTestUtil.nextDate());

		mbDiscussion.setClassNameId(RandomTestUtil.nextLong());

		mbDiscussion.setClassPK(RandomTestUtil.nextLong());

		mbDiscussion.setThreadId(RandomTestUtil.nextLong());

		_mbDiscussions.add(_persistence.update(mbDiscussion));

		return mbDiscussion;
	}

	private List<MBDiscussion> _mbDiscussions = new ArrayList<MBDiscussion>();
	private MBDiscussionPersistence _persistence = MBDiscussionUtil.getPersistence();
}