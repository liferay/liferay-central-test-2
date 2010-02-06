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

package com.liferay.portal.service;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.TestPropsValues;

/**
 * <a href="ResourceLocalServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ResourceLocalServiceTest extends BaseServiceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		Group group = GroupLocalServiceUtil.getGroup(
			TestPropsValues.COMPANY_ID, GroupConstants.GUEST);

		_layout = LayoutLocalServiceUtil.getLayout(TestPropsValues.LAYOUT_PLID);

		_userIds = UserLocalServiceUtil.getGroupUserIds(group.getGroupId());
	}

	public void testAddResourcesConcurrently() throws Exception {
		int threadCount = 5;

		DoAsUserThread[] threads = new DoAsUserThread[threadCount];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new AddResources(_userIds[i]);
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
				" threads added resources successfully",
			successCount == threadCount);
	}

	private Layout _layout;
	private long[] _userIds;

	private class AddResources extends DoAsUserThread {

		public AddResources(long userId) {
			super(userId);
		}

		public boolean isSuccess() {
			return true;
		}

		protected void doRun() throws Exception {
			try {
				ResourceLocalServiceUtil.getResource(
					_layout.getCompanyId(), Layout.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(_layout.getPlid()));
			}
			catch (NoSuchResourceException nsre) {
				boolean addCommunityPermission = true;
				boolean addGuestPermission = true;

				if (_layout.isPrivateLayout()) {
					addGuestPermission = false;
				}

				ResourceLocalServiceUtil.addResources(
					_layout.getCompanyId(), _layout.getGroupId(), 0,
					Layout.class.getName(), _layout.getPlid(), false,
					addCommunityPermission, addGuestPermission);
			}
		}

	}

}