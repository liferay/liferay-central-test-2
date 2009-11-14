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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBThread;

import java.util.List;

/**
 * <a href="MBThreadPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBThreadPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (MBThreadPersistence)PortalBeanLocatorUtil.locate(MBThreadPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		MBThread mbThread = _persistence.create(pk);

		assertNotNull(mbThread);

		assertEquals(mbThread.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		MBThread newMBThread = addMBThread();

		_persistence.remove(newMBThread);

		MBThread existingMBThread = _persistence.fetchByPrimaryKey(newMBThread.getPrimaryKey());

		assertNull(existingMBThread);
	}

	public void testUpdateNew() throws Exception {
		addMBThread();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		MBThread newMBThread = _persistence.create(pk);

		newMBThread.setGroupId(nextLong());
		newMBThread.setCategoryId(nextLong());
		newMBThread.setRootMessageId(nextLong());
		newMBThread.setMessageCount(nextInt());
		newMBThread.setViewCount(nextInt());
		newMBThread.setLastPostByUserId(nextLong());
		newMBThread.setLastPostDate(nextDate());
		newMBThread.setPriority(nextDouble());
		newMBThread.setStatus(nextInt());
		newMBThread.setStatusByUserId(nextLong());
		newMBThread.setStatusByUserName(randomString());
		newMBThread.setStatusDate(nextDate());

		_persistence.update(newMBThread, false);

		MBThread existingMBThread = _persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		assertEquals(existingMBThread.getThreadId(), newMBThread.getThreadId());
		assertEquals(existingMBThread.getGroupId(), newMBThread.getGroupId());
		assertEquals(existingMBThread.getCategoryId(),
			newMBThread.getCategoryId());
		assertEquals(existingMBThread.getRootMessageId(),
			newMBThread.getRootMessageId());
		assertEquals(existingMBThread.getMessageCount(),
			newMBThread.getMessageCount());
		assertEquals(existingMBThread.getViewCount(), newMBThread.getViewCount());
		assertEquals(existingMBThread.getLastPostByUserId(),
			newMBThread.getLastPostByUserId());
		assertEquals(Time.getShortTimestamp(existingMBThread.getLastPostDate()),
			Time.getShortTimestamp(newMBThread.getLastPostDate()));
		assertEquals(existingMBThread.getPriority(), newMBThread.getPriority());
		assertEquals(existingMBThread.getStatus(), newMBThread.getStatus());
		assertEquals(existingMBThread.getStatusByUserId(),
			newMBThread.getStatusByUserId());
		assertEquals(existingMBThread.getStatusByUserName(),
			newMBThread.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(existingMBThread.getStatusDate()),
			Time.getShortTimestamp(newMBThread.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		MBThread newMBThread = addMBThread();

		MBThread existingMBThread = _persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		assertEquals(existingMBThread, newMBThread);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchThreadException");
		}
		catch (NoSuchThreadException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBThread newMBThread = addMBThread();

		MBThread existingMBThread = _persistence.fetchByPrimaryKey(newMBThread.getPrimaryKey());

		assertEquals(existingMBThread, newMBThread);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		MBThread missingMBThread = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingMBThread);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBThread newMBThread = addMBThread();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadId",
				newMBThread.getThreadId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		MBThread existingMBThread = (MBThread)result.get(0);

		assertEquals(existingMBThread, newMBThread);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected MBThread addMBThread() throws Exception {
		long pk = nextLong();

		MBThread mbThread = _persistence.create(pk);

		mbThread.setGroupId(nextLong());
		mbThread.setCategoryId(nextLong());
		mbThread.setRootMessageId(nextLong());
		mbThread.setMessageCount(nextInt());
		mbThread.setViewCount(nextInt());
		mbThread.setLastPostByUserId(nextLong());
		mbThread.setLastPostDate(nextDate());
		mbThread.setPriority(nextDouble());
		mbThread.setStatus(nextInt());
		mbThread.setStatusByUserId(nextLong());
		mbThread.setStatusByUserName(randomString());
		mbThread.setStatusDate(nextDate());

		_persistence.update(mbThread, false);

		return mbThread;
	}

	private MBThreadPersistence _persistence;
}