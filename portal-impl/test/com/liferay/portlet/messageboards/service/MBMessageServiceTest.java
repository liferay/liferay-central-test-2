/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="MBMessageServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class MBMessageServiceTest extends BaseServiceTestCase {

	public void testAddMessagesConcurrently() throws Exception {
		int threadCount = 50;

		DoAsUserThread[] threads = new DoAsUserThread[threadCount];

		for (int i = 0; i < threads.length; i++) {
			String subject = "Test Message " + i;

			threads[i] = new AddMessageThread(subject);
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

	protected void setUp() throws Exception {
		super.setUp();

		String name = "Test Category";
		String description = "This is a test category.";

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		_category = MBCategoryServiceUtil.addCategory(
			TestPropsValues.LAYOUT_PLID,
			MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	protected void tearDown() throws Exception {
		if (_category != null) {
			MBCategoryServiceUtil.deleteCategory(_category.getCategoryId());
		}

		super.tearDown();
	}

	private MBCategory _category;

	private class AddMessageThread extends DoAsUserThread {

		public AddMessageThread(String subject) {
			super(TestPropsValues.USER_ID);

			_subject = subject;
		}

		public boolean isSuccess() {
			return true;
		}

		protected void doRun() throws Exception {
			String body = "This is a test message.";
			List files = new ArrayList();
			boolean anonymous = false;
			double priority = 0.0;
			String[] tagsEntries = null;
			PortletPreferences prefs = null;

			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			ThemeDisplay themeDisplay = null;

			MBMessageServiceUtil.addMessage(
				_category.getCategoryId(), _subject, body, files, anonymous,
				priority, tagsEntries, prefs, addCommunityPermissions,
				addGuestPermissions, themeDisplay);
		}

		private String _subject;

	}

}