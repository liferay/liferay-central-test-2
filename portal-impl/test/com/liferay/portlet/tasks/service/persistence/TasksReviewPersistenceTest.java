/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.tasks.NoSuchReviewException;
import com.liferay.portlet.tasks.model.TasksReview;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class TasksReviewPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (TasksReviewPersistence)PortalBeanLocatorUtil.locate(TasksReviewPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		TasksReview tasksReview = _persistence.create(pk);

		assertNotNull(tasksReview);

		assertEquals(tasksReview.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		TasksReview newTasksReview = addTasksReview();

		_persistence.remove(newTasksReview);

		TasksReview existingTasksReview = _persistence.fetchByPrimaryKey(newTasksReview.getPrimaryKey());

		assertNull(existingTasksReview);
	}

	public void testUpdateNew() throws Exception {
		addTasksReview();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		TasksReview newTasksReview = _persistence.create(pk);

		newTasksReview.setGroupId(nextLong());
		newTasksReview.setCompanyId(nextLong());
		newTasksReview.setUserId(nextLong());
		newTasksReview.setUserName(randomString());
		newTasksReview.setCreateDate(nextDate());
		newTasksReview.setModifiedDate(nextDate());
		newTasksReview.setProposalId(nextLong());
		newTasksReview.setAssignedByUserId(nextLong());
		newTasksReview.setAssignedByUserName(randomString());
		newTasksReview.setStage(nextInt());
		newTasksReview.setCompleted(randomBoolean());
		newTasksReview.setRejected(randomBoolean());

		_persistence.update(newTasksReview, false);

		TasksReview existingTasksReview = _persistence.findByPrimaryKey(newTasksReview.getPrimaryKey());

		assertEquals(existingTasksReview.getReviewId(),
			newTasksReview.getReviewId());
		assertEquals(existingTasksReview.getGroupId(),
			newTasksReview.getGroupId());
		assertEquals(existingTasksReview.getCompanyId(),
			newTasksReview.getCompanyId());
		assertEquals(existingTasksReview.getUserId(), newTasksReview.getUserId());
		assertEquals(existingTasksReview.getUserName(),
			newTasksReview.getUserName());
		assertEquals(Time.getShortTimestamp(existingTasksReview.getCreateDate()),
			Time.getShortTimestamp(newTasksReview.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingTasksReview.getModifiedDate()),
			Time.getShortTimestamp(newTasksReview.getModifiedDate()));
		assertEquals(existingTasksReview.getProposalId(),
			newTasksReview.getProposalId());
		assertEquals(existingTasksReview.getAssignedByUserId(),
			newTasksReview.getAssignedByUserId());
		assertEquals(existingTasksReview.getAssignedByUserName(),
			newTasksReview.getAssignedByUserName());
		assertEquals(existingTasksReview.getStage(), newTasksReview.getStage());
		assertEquals(existingTasksReview.getCompleted(),
			newTasksReview.getCompleted());
		assertEquals(existingTasksReview.getRejected(),
			newTasksReview.getRejected());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		TasksReview newTasksReview = addTasksReview();

		TasksReview existingTasksReview = _persistence.findByPrimaryKey(newTasksReview.getPrimaryKey());

		assertEquals(existingTasksReview, newTasksReview);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchReviewException");
		}
		catch (NoSuchReviewException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		TasksReview newTasksReview = addTasksReview();

		TasksReview existingTasksReview = _persistence.fetchByPrimaryKey(newTasksReview.getPrimaryKey());

		assertEquals(existingTasksReview, newTasksReview);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		TasksReview missingTasksReview = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingTasksReview);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		TasksReview newTasksReview = addTasksReview();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TasksReview.class,
				TasksReview.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("reviewId",
				newTasksReview.getReviewId()));

		List<TasksReview> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		TasksReview existingTasksReview = result.get(0);

		assertEquals(existingTasksReview, newTasksReview);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TasksReview.class,
				TasksReview.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("reviewId", nextLong()));

		List<TasksReview> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected TasksReview addTasksReview() throws Exception {
		long pk = nextLong();

		TasksReview tasksReview = _persistence.create(pk);

		tasksReview.setGroupId(nextLong());
		tasksReview.setCompanyId(nextLong());
		tasksReview.setUserId(nextLong());
		tasksReview.setUserName(randomString());
		tasksReview.setCreateDate(nextDate());
		tasksReview.setModifiedDate(nextDate());
		tasksReview.setProposalId(nextLong());
		tasksReview.setAssignedByUserId(nextLong());
		tasksReview.setAssignedByUserName(randomString());
		tasksReview.setStage(nextInt());
		tasksReview.setCompleted(randomBoolean());
		tasksReview.setRejected(randomBoolean());

		_persistence.update(tasksReview, false);

		return tasksReview;
	}

	private TasksReviewPersistence _persistence;
}