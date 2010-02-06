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

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="WorkflowDefinitionLinkPersistenceTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WorkflowDefinitionLinkPersistenceTest
	extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WorkflowDefinitionLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowDefinitionLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink workflowDefinitionLink = _persistence.create(pk);

		assertNotNull(workflowDefinitionLink);

		assertEquals(workflowDefinitionLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		_persistence.remove(newWorkflowDefinitionLink);

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertNull(existingWorkflowDefinitionLink);
	}

	public void testUpdateNew() throws Exception {
		addWorkflowDefinitionLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink newWorkflowDefinitionLink = _persistence.create(pk);

		newWorkflowDefinitionLink.setGroupId(nextLong());
		newWorkflowDefinitionLink.setCompanyId(nextLong());
		newWorkflowDefinitionLink.setUserId(nextLong());
		newWorkflowDefinitionLink.setUserName(randomString());
		newWorkflowDefinitionLink.setCreateDate(nextDate());
		newWorkflowDefinitionLink.setModifiedDate(nextDate());
		newWorkflowDefinitionLink.setClassNameId(nextLong());
		newWorkflowDefinitionLink.setWorkflowDefinitionName(randomString());
		newWorkflowDefinitionLink.setWorkflowDefinitionVersion(nextInt());

		_persistence.update(newWorkflowDefinitionLink, false);

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionLinkId(),
			newWorkflowDefinitionLink.getWorkflowDefinitionLinkId());
		assertEquals(existingWorkflowDefinitionLink.getGroupId(),
			newWorkflowDefinitionLink.getGroupId());
		assertEquals(existingWorkflowDefinitionLink.getCompanyId(),
			newWorkflowDefinitionLink.getCompanyId());
		assertEquals(existingWorkflowDefinitionLink.getUserId(),
			newWorkflowDefinitionLink.getUserId());
		assertEquals(existingWorkflowDefinitionLink.getUserName(),
			newWorkflowDefinitionLink.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingWorkflowDefinitionLink.getCreateDate()),
			Time.getShortTimestamp(newWorkflowDefinitionLink.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingWorkflowDefinitionLink.getModifiedDate()),
			Time.getShortTimestamp(newWorkflowDefinitionLink.getModifiedDate()));
		assertEquals(existingWorkflowDefinitionLink.getClassNameId(),
			newWorkflowDefinitionLink.getClassNameId());
		assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionName(),
			newWorkflowDefinitionLink.getWorkflowDefinitionName());
		assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionVersion(),
			newWorkflowDefinitionLink.getWorkflowDefinitionVersion());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertEquals(existingWorkflowDefinitionLink, newWorkflowDefinitionLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchWorkflowDefinitionLinkException");
		}
		catch (NoSuchWorkflowDefinitionLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertEquals(existingWorkflowDefinitionLink, newWorkflowDefinitionLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink missingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWorkflowDefinitionLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				WorkflowDefinitionLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"workflowDefinitionLinkId",
				newWorkflowDefinitionLink.getWorkflowDefinitionLinkId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		WorkflowDefinitionLink existingWorkflowDefinitionLink = (WorkflowDefinitionLink)result.get(0);

		assertEquals(existingWorkflowDefinitionLink, newWorkflowDefinitionLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				WorkflowDefinitionLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"workflowDefinitionLinkId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected WorkflowDefinitionLink addWorkflowDefinitionLink()
		throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink workflowDefinitionLink = _persistence.create(pk);

		workflowDefinitionLink.setGroupId(nextLong());
		workflowDefinitionLink.setCompanyId(nextLong());
		workflowDefinitionLink.setUserId(nextLong());
		workflowDefinitionLink.setUserName(randomString());
		workflowDefinitionLink.setCreateDate(nextDate());
		workflowDefinitionLink.setModifiedDate(nextDate());
		workflowDefinitionLink.setClassNameId(nextLong());
		workflowDefinitionLink.setWorkflowDefinitionName(randomString());
		workflowDefinitionLink.setWorkflowDefinitionVersion(nextInt());

		_persistence.update(workflowDefinitionLink, false);

		return workflowDefinitionLink;
	}

	private WorkflowDefinitionLinkPersistence _persistence;
}