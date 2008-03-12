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

import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.TestPropsValues;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBMessageWorkerThread.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class MBMessageWorkerThread extends Thread {

	public MBMessageWorkerThread(long categoryId, String subject) {
		_categoryId = categoryId;
		_subject = subject;
	}

	public void run() {
		String body = "This is a test... hear me roar.";
		List files = new ArrayList();
		boolean anonymous = false;
		double priority = 0.0;
		String[] tagsEntries = null;
		PortletPreferences prefs = null;
		ThemeDisplay themeDisplay = null;
		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		try {
			_setup();

			MBMessageServiceUtil.addMessage(
				_categoryId, _subject, body, files, anonymous, priority,
				tagsEntries, prefs, addCommunityPermissions,
				addGuestPermissions, themeDisplay);

			_success = true;
		}
		catch (Exception e) {
			_log.error("Failed to add message for " + _subject, e);
		}
		finally {
			_tearDown();
		}
	}

	public boolean getSuccess() {
		return _success;
	}

	private void _setup() throws Exception {
		PrincipalThreadLocal.setName(String.valueOf(TestPropsValues.USER_ID));

		User user = UserLocalServiceUtil.getUserById(TestPropsValues.USER_ID);

		_permissionChecker = PermissionCheckerFactory.create(user, true);

		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	private void _tearDown() {
		try {
			if (_permissionChecker != null) {
				PermissionCheckerFactory.recycle(_permissionChecker);
			}
		}
		catch (Exception e) {
		}
	}

	private long _categoryId;
	private String _subject;
	private boolean _success = false;
	private PermissionCheckerImpl _permissionChecker = null;

	private static Log _log = LogFactory.getLog(MBMessageWorkerThread.class);

}