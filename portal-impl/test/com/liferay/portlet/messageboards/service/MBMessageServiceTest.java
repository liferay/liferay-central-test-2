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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="MBMessageServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class MBMessageServiceTest extends BaseServiceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		String name = "Test Category";
		String description = "This is a test category.";
		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean mailingListActive = false;

		Group group = GroupLocalServiceUtil.getGroup(
			TestPropsValues.COMPANY_ID, GroupConstants.GUEST);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCommunityPermissions(
			new String[] {ActionKeys.ADD_MESSAGE, ActionKeys.VIEW});
		serviceContext.setGuestPermissions(
			new String[] {ActionKeys.ADD_MESSAGE, ActionKeys.VIEW});
		serviceContext.setScopeGroupId(group.getGroupId());

		_category = MBCategoryServiceUtil.addCategory(
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, name, description,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, serviceContext);

		_userIds = UserLocalServiceUtil.getGroupUserIds(group.getGroupId());
	}

	public void tearDown() throws Exception {
		if (_category != null) {
			MBCategoryServiceUtil.deleteCategory(
				_category.getGroupId(), _category.getCategoryId());
		}

		super.tearDown();
	}

	public void testAddMessagesConcurrently() throws Exception {
		int threadCount = 5;

		DoAsUserThread[] threads = new DoAsUserThread[threadCount];

		for (int i = 0; i < threads.length; i++) {
			String subject = "Test Message " + i;

			threads[i] = new AddMessageThread(_userIds[i], subject);
		}

		for (DoAsUserThread thread : threads) {
			thread.start();
		}

		for (DoAsUserThread thread : threads) {
			thread.join();
		}

		int successCount = 0;

		for (DoAsUserThread thread : threads) {
			if (thread.isSuccess()) {
				successCount++;
			}
		}

		assertTrue(
			"Only " + successCount + " out of " + threadCount +
				" threads added messages successfully",
			successCount == threadCount);
	}

	private MBCategory _category;
	private long[] _userIds;

	private class AddMessageThread extends DoAsUserThread {

		public AddMessageThread(long userId, String subject) {
			super(userId);

			_subject = subject;
		}

		public boolean isSuccess() {
			return true;
		}

		protected void doRun() throws Exception {
			String body = "This is a test message.";
			List<ObjectValuePair<String, byte[]>> files =
				new ArrayList<ObjectValuePair<String, byte[]>>();
			boolean anonymous = false;
			double priority = 0.0;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			MBMessageServiceUtil.addMessage(
				_category.getGroupId(), _category.getCategoryId(), _subject,
				body, files, anonymous, priority, serviceContext);
		}

		private String _subject;

	}

}