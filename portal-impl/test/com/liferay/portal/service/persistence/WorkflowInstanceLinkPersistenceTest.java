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

import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="WorkflowInstanceLinkPersistenceTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WorkflowInstanceLinkPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WorkflowInstanceLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WorkflowInstanceLink workflowInstanceLink = _persistence.create(pk);

		assertNotNull(workflowInstanceLink);

		assertEquals(workflowInstanceLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		_persistence.remove(newWorkflowInstanceLink);

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		assertNull(existingWorkflowInstanceLink);
	}

	public void testUpdateNew() throws Exception {
		addWorkflowInstanceLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WorkflowInstanceLink newWorkflowInstanceLink = _persistence.create(pk);

		newWorkflowInstanceLink.setGroupId(nextLong());
		newWorkflowInstanceLink.setCompanyId(nextLong());
		newWorkflowInstanceLink.setUserId(nextLong());
		newWorkflowInstanceLink.setUserName(randomString());
		newWorkflowInstanceLink.setCreateDate(nextDate());
		newWorkflowInstanceLink.setModifiedDate(nextDate());
		newWorkflowInstanceLink.setClassNameId(nextLong());
		newWorkflowInstanceLink.setClassPK(nextLong());
		newWorkflowInstanceLink.setWorkflowInstanceId(nextLong());

		_persistence.update(newWorkflowInstanceLink, false);

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.findByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		assertEquals(existingWorkflowInstanceLink.getWorkflowInstanceLinkId(),
			newWorkflowInstanceLink.getWorkflowInstanceLinkId());
		assertEquals(existingWorkflowInstanceLink.getGroupId(),
			newWorkflowInstanceLink.getGroupId());
		assertEquals(existingWorkflowInstanceLink.getCompanyId(),
			newWorkflowInstanceLink.getCompanyId());
		assertEquals(existingWorkflowInstanceLink.getUserId(),
			newWorkflowInstanceLink.getUserId());
		assertEquals(existingWorkflowInstanceLink.getUserName(),
			newWorkflowInstanceLink.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingWorkflowInstanceLink.getCreateDate()),
			Time.getShortTimestamp(newWorkflowInstanceLink.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingWorkflowInstanceLink.getModifiedDate()),
			Time.getShortTimestamp(newWorkflowInstanceLink.getModifiedDate()));
		assertEquals(existingWorkflowInstanceLink.getClassNameId(),
			newWorkflowInstanceLink.getClassNameId());
		assertEquals(existingWorkflowInstanceLink.getClassPK(),
			newWorkflowInstanceLink.getClassPK());
		assertEquals(existingWorkflowInstanceLink.getWorkflowInstanceId(),
			newWorkflowInstanceLink.getWorkflowInstanceId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.findByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		assertEquals(existingWorkflowInstanceLink, newWorkflowInstanceLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchWorkflowInstanceLinkException");
		}
		catch (NoSuchWorkflowInstanceLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		assertEquals(existingWorkflowInstanceLink, newWorkflowInstanceLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WorkflowInstanceLink missingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWorkflowInstanceLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("workflowInstanceLinkId",
				newWorkflowInstanceLink.getWorkflowInstanceLinkId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		WorkflowInstanceLink existingWorkflowInstanceLink = (WorkflowInstanceLink)result.get(0);

		assertEquals(existingWorkflowInstanceLink, newWorkflowInstanceLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("workflowInstanceLinkId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected WorkflowInstanceLink addWorkflowInstanceLink()
		throws Exception {
		long pk = nextLong();

		WorkflowInstanceLink workflowInstanceLink = _persistence.create(pk);

		workflowInstanceLink.setGroupId(nextLong());
		workflowInstanceLink.setCompanyId(nextLong());
		workflowInstanceLink.setUserId(nextLong());
		workflowInstanceLink.setUserName(randomString());
		workflowInstanceLink.setCreateDate(nextDate());
		workflowInstanceLink.setModifiedDate(nextDate());
		workflowInstanceLink.setClassNameId(nextLong());
		workflowInstanceLink.setClassPK(nextLong());
		workflowInstanceLink.setWorkflowInstanceId(nextLong());

		_persistence.update(workflowInstanceLink, false);

		return workflowInstanceLink;
	}

	private WorkflowInstanceLinkPersistence _persistence;
}