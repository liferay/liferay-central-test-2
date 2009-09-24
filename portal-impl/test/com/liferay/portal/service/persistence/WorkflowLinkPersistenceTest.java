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

import com.liferay.portal.NoSuchWorkflowLinkException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.WorkflowLink;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

/**
 * <a href="WorkflowLinkPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WorkflowLinkPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WorkflowLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowLinkPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WorkflowLink workflowLink = _persistence.create(pk);

		assertNotNull(workflowLink);

		assertEquals(workflowLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WorkflowLink newWorkflowLink = addWorkflowLink();

		_persistence.remove(newWorkflowLink);

		WorkflowLink existingWorkflowLink = _persistence.fetchByPrimaryKey(newWorkflowLink.getPrimaryKey());

		assertNull(existingWorkflowLink);
	}

	public void testUpdateNew() throws Exception {
		addWorkflowLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WorkflowLink newWorkflowLink = _persistence.create(pk);

		newWorkflowLink.setGroupId(nextLong());
		newWorkflowLink.setCompanyId(nextLong());
		newWorkflowLink.setUserId(nextLong());
		newWorkflowLink.setUserName(randomString());
		newWorkflowLink.setModifiedDate(nextDate());
		newWorkflowLink.setClassNameId(nextLong());
		newWorkflowLink.setDefinitionName(randomString());

		_persistence.update(newWorkflowLink, false);

		WorkflowLink existingWorkflowLink = _persistence.findByPrimaryKey(newWorkflowLink.getPrimaryKey());

		assertEquals(existingWorkflowLink.getWorkflowLinkId(),
			newWorkflowLink.getWorkflowLinkId());
		assertEquals(existingWorkflowLink.getGroupId(),
			newWorkflowLink.getGroupId());
		assertEquals(existingWorkflowLink.getCompanyId(),
			newWorkflowLink.getCompanyId());
		assertEquals(existingWorkflowLink.getUserId(),
			newWorkflowLink.getUserId());
		assertEquals(existingWorkflowLink.getUserName(),
			newWorkflowLink.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingWorkflowLink.getModifiedDate()),
			Time.getShortTimestamp(newWorkflowLink.getModifiedDate()));
		assertEquals(existingWorkflowLink.getClassNameId(),
			newWorkflowLink.getClassNameId());
		assertEquals(existingWorkflowLink.getDefinitionName(),
			newWorkflowLink.getDefinitionName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowLink newWorkflowLink = addWorkflowLink();

		WorkflowLink existingWorkflowLink = _persistence.findByPrimaryKey(newWorkflowLink.getPrimaryKey());

		assertEquals(existingWorkflowLink, newWorkflowLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchWorkflowLinkException");
		}
		catch (NoSuchWorkflowLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowLink newWorkflowLink = addWorkflowLink();

		WorkflowLink existingWorkflowLink = _persistence.fetchByPrimaryKey(newWorkflowLink.getPrimaryKey());

		assertEquals(existingWorkflowLink, newWorkflowLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WorkflowLink missingWorkflowLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWorkflowLink);
	}

	protected WorkflowLink addWorkflowLink() throws Exception {
		long pk = nextLong();

		WorkflowLink workflowLink = _persistence.create(pk);

		workflowLink.setGroupId(nextLong());
		workflowLink.setCompanyId(nextLong());
		workflowLink.setUserId(nextLong());
		workflowLink.setUserName(randomString());
		workflowLink.setModifiedDate(nextDate());
		workflowLink.setClassNameId(nextLong());
		workflowLink.setDefinitionName(randomString());

		_persistence.update(workflowLink, false);

		return workflowLink;
	}

	private WorkflowLinkPersistence _persistence;
}