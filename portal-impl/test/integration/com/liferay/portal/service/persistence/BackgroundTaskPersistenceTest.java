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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchBackgroundTaskException;
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
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.test.RandomTestUtil;

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
public class BackgroundTaskPersistenceTest {
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
		Iterator<BackgroundTask> iterator = _backgroundTasks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BackgroundTask backgroundTask = _persistence.create(pk);

		Assert.assertNotNull(backgroundTask);

		Assert.assertEquals(backgroundTask.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BackgroundTask newBackgroundTask = addBackgroundTask();

		_persistence.remove(newBackgroundTask);

		BackgroundTask existingBackgroundTask = _persistence.fetchByPrimaryKey(newBackgroundTask.getPrimaryKey());

		Assert.assertNull(existingBackgroundTask);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBackgroundTask();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BackgroundTask newBackgroundTask = _persistence.create(pk);

		newBackgroundTask.setMvccVersion(RandomTestUtil.nextLong());

		newBackgroundTask.setGroupId(RandomTestUtil.nextLong());

		newBackgroundTask.setCompanyId(RandomTestUtil.nextLong());

		newBackgroundTask.setUserId(RandomTestUtil.nextLong());

		newBackgroundTask.setUserName(RandomTestUtil.randomString());

		newBackgroundTask.setCreateDate(RandomTestUtil.nextDate());

		newBackgroundTask.setModifiedDate(RandomTestUtil.nextDate());

		newBackgroundTask.setName(RandomTestUtil.randomString());

		newBackgroundTask.setServletContextNames(RandomTestUtil.randomString());

		newBackgroundTask.setTaskExecutorClassName(RandomTestUtil.randomString());

		newBackgroundTask.setTaskContext(RandomTestUtil.randomString());

		newBackgroundTask.setCompleted(RandomTestUtil.randomBoolean());

		newBackgroundTask.setCompletionDate(RandomTestUtil.nextDate());

		newBackgroundTask.setStatus(RandomTestUtil.nextInt());

		newBackgroundTask.setStatusMessage(RandomTestUtil.randomString());

		_backgroundTasks.add(_persistence.update(newBackgroundTask));

		BackgroundTask existingBackgroundTask = _persistence.findByPrimaryKey(newBackgroundTask.getPrimaryKey());

		Assert.assertEquals(existingBackgroundTask.getMvccVersion(),
			newBackgroundTask.getMvccVersion());
		Assert.assertEquals(existingBackgroundTask.getBackgroundTaskId(),
			newBackgroundTask.getBackgroundTaskId());
		Assert.assertEquals(existingBackgroundTask.getGroupId(),
			newBackgroundTask.getGroupId());
		Assert.assertEquals(existingBackgroundTask.getCompanyId(),
			newBackgroundTask.getCompanyId());
		Assert.assertEquals(existingBackgroundTask.getUserId(),
			newBackgroundTask.getUserId());
		Assert.assertEquals(existingBackgroundTask.getUserName(),
			newBackgroundTask.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBackgroundTask.getCreateDate()),
			Time.getShortTimestamp(newBackgroundTask.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingBackgroundTask.getModifiedDate()),
			Time.getShortTimestamp(newBackgroundTask.getModifiedDate()));
		Assert.assertEquals(existingBackgroundTask.getName(),
			newBackgroundTask.getName());
		Assert.assertEquals(existingBackgroundTask.getServletContextNames(),
			newBackgroundTask.getServletContextNames());
		Assert.assertEquals(existingBackgroundTask.getTaskExecutorClassName(),
			newBackgroundTask.getTaskExecutorClassName());
		Assert.assertEquals(existingBackgroundTask.getTaskContext(),
			newBackgroundTask.getTaskContext());
		Assert.assertEquals(existingBackgroundTask.getCompleted(),
			newBackgroundTask.getCompleted());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBackgroundTask.getCompletionDate()),
			Time.getShortTimestamp(newBackgroundTask.getCompletionDate()));
		Assert.assertEquals(existingBackgroundTask.getStatus(),
			newBackgroundTask.getStatus());
		Assert.assertEquals(existingBackgroundTask.getStatusMessage(),
			newBackgroundTask.getStatusMessage());
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByStatus() {
		try {
			_persistence.countByStatus(RandomTestUtil.nextInt());

			_persistence.countByStatus(0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T() {
		try {
			_persistence.countByG_T(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_T(0L, StringPool.NULL);

			_persistence.countByG_T(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_TArrayable() {
		try {
			_persistence.countByG_T(RandomTestUtil.nextLong(),
				new String[] {
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.NULL, null, null
				});
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_S() {
		try {
			_persistence.countByT_S(StringPool.BLANK, RandomTestUtil.nextInt());

			_persistence.countByT_S(StringPool.NULL, 0);

			_persistence.countByT_S((String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_SArrayable() {
		try {
			_persistence.countByT_S(new String[] {
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.NULL, null, null
				}, RandomTestUtil.nextInt());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_T() {
		try {
			_persistence.countByG_N_T(RandomTestUtil.nextLong(),
				StringPool.BLANK, StringPool.BLANK);

			_persistence.countByG_N_T(0L, StringPool.NULL, StringPool.NULL);

			_persistence.countByG_N_T(0L, (String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T_C() {
		try {
			_persistence.countByG_T_C(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.randomBoolean());

			_persistence.countByG_T_C(0L, StringPool.NULL,
				RandomTestUtil.randomBoolean());

			_persistence.countByG_T_C(0L, (String)null,
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T_CArrayable() {
		try {
			_persistence.countByG_T_C(RandomTestUtil.nextLong(),
				new String[] {
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.NULL, null, null
				}, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T_S() {
		try {
			_persistence.countByG_T_S(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt());

			_persistence.countByG_T_S(0L, StringPool.NULL, 0);

			_persistence.countByG_T_S(0L, (String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T_SArrayable() {
		try {
			_persistence.countByG_T_S(RandomTestUtil.nextLong(),
				new String[] {
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.NULL, null, null
				}, RandomTestUtil.nextInt());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_T_C() {
		try {
			_persistence.countByG_N_T_C(RandomTestUtil.nextLong(),
				StringPool.BLANK, StringPool.BLANK,
				RandomTestUtil.randomBoolean());

			_persistence.countByG_N_T_C(0L, StringPool.NULL, StringPool.NULL,
				RandomTestUtil.randomBoolean());

			_persistence.countByG_N_T_C(0L, (String)null, (String)null,
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BackgroundTask newBackgroundTask = addBackgroundTask();

		BackgroundTask existingBackgroundTask = _persistence.findByPrimaryKey(newBackgroundTask.getPrimaryKey());

		Assert.assertEquals(existingBackgroundTask, newBackgroundTask);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchBackgroundTaskException");
		}
		catch (NoSuchBackgroundTaskException nsee) {
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

	protected OrderByComparator<BackgroundTask> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("BackgroundTask",
			"mvccVersion", true, "backgroundTaskId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "name", true, "servletContextNames",
			true, "taskExecutorClassName", true, "taskContext", true,
			"completed", true, "completionDate", true, "status", true,
			"statusMessage", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BackgroundTask newBackgroundTask = addBackgroundTask();

		BackgroundTask existingBackgroundTask = _persistence.fetchByPrimaryKey(newBackgroundTask.getPrimaryKey());

		Assert.assertEquals(existingBackgroundTask, newBackgroundTask);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BackgroundTask missingBackgroundTask = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBackgroundTask);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		BackgroundTask newBackgroundTask1 = addBackgroundTask();
		BackgroundTask newBackgroundTask2 = addBackgroundTask();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBackgroundTask1.getPrimaryKey());
		primaryKeys.add(newBackgroundTask2.getPrimaryKey());

		Map<Serializable, BackgroundTask> backgroundTasks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, backgroundTasks.size());
		Assert.assertEquals(newBackgroundTask1,
			backgroundTasks.get(newBackgroundTask1.getPrimaryKey()));
		Assert.assertEquals(newBackgroundTask2,
			backgroundTasks.get(newBackgroundTask2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BackgroundTask> backgroundTasks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(backgroundTasks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		BackgroundTask newBackgroundTask = addBackgroundTask();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBackgroundTask.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BackgroundTask> backgroundTasks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, backgroundTasks.size());
		Assert.assertEquals(newBackgroundTask,
			backgroundTasks.get(newBackgroundTask.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BackgroundTask> backgroundTasks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(backgroundTasks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		BackgroundTask newBackgroundTask = addBackgroundTask();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBackgroundTask.getPrimaryKey());

		Map<Serializable, BackgroundTask> backgroundTasks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, backgroundTasks.size());
		Assert.assertEquals(newBackgroundTask,
			backgroundTasks.get(newBackgroundTask.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = BackgroundTaskLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					BackgroundTask backgroundTask = (BackgroundTask)object;

					Assert.assertNotNull(backgroundTask);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BackgroundTask newBackgroundTask = addBackgroundTask();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BackgroundTask.class,
				BackgroundTask.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("backgroundTaskId",
				newBackgroundTask.getBackgroundTaskId()));

		List<BackgroundTask> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BackgroundTask existingBackgroundTask = result.get(0);

		Assert.assertEquals(existingBackgroundTask, newBackgroundTask);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BackgroundTask.class,
				BackgroundTask.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("backgroundTaskId",
				RandomTestUtil.nextLong()));

		List<BackgroundTask> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BackgroundTask newBackgroundTask = addBackgroundTask();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BackgroundTask.class,
				BackgroundTask.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"backgroundTaskId"));

		Object newBackgroundTaskId = newBackgroundTask.getBackgroundTaskId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("backgroundTaskId",
				new Object[] { newBackgroundTaskId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBackgroundTaskId = result.get(0);

		Assert.assertEquals(existingBackgroundTaskId, newBackgroundTaskId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BackgroundTask.class,
				BackgroundTask.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"backgroundTaskId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("backgroundTaskId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected BackgroundTask addBackgroundTask() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BackgroundTask backgroundTask = _persistence.create(pk);

		backgroundTask.setMvccVersion(RandomTestUtil.nextLong());

		backgroundTask.setGroupId(RandomTestUtil.nextLong());

		backgroundTask.setCompanyId(RandomTestUtil.nextLong());

		backgroundTask.setUserId(RandomTestUtil.nextLong());

		backgroundTask.setUserName(RandomTestUtil.randomString());

		backgroundTask.setCreateDate(RandomTestUtil.nextDate());

		backgroundTask.setModifiedDate(RandomTestUtil.nextDate());

		backgroundTask.setName(RandomTestUtil.randomString());

		backgroundTask.setServletContextNames(RandomTestUtil.randomString());

		backgroundTask.setTaskExecutorClassName(RandomTestUtil.randomString());

		backgroundTask.setTaskContext(RandomTestUtil.randomString());

		backgroundTask.setCompleted(RandomTestUtil.randomBoolean());

		backgroundTask.setCompletionDate(RandomTestUtil.nextDate());

		backgroundTask.setStatus(RandomTestUtil.nextInt());

		backgroundTask.setStatusMessage(RandomTestUtil.randomString());

		_backgroundTasks.add(_persistence.update(backgroundTask));

		return backgroundTask;
	}

	private static Log _log = LogFactoryUtil.getLog(BackgroundTaskPersistenceTest.class);
	private List<BackgroundTask> _backgroundTasks = new ArrayList<BackgroundTask>();
	private ModelListener<BackgroundTask>[] _modelListeners;
	private BackgroundTaskPersistence _persistence = BackgroundTaskUtil.getPersistence();
}