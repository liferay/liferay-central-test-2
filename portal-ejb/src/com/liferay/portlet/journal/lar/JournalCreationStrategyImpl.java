/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.GroupUtil;

import java.lang.ref.SoftReference;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalCreationStrategyImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Joel Kozikowski
 *
 */
public class JournalCreationStrategyImpl implements JournalCreationStrategy {

	public String getAuthorUserId(
			String companyId, long groupId, Object journalObj)
		throws Exception {

		User user = getFirstUser(groupId);

		if (user != null) {
			 return user.getUserId();
		}
		else {
			 return null;
		}
	}

	public String getAuthorUserName(
			String companyId, long groupId, Object journalObj)
		throws Exception {

		User user = getFirstUser(groupId);

		if (user != null) {
			 return user.getFullName();
		}
		else {
			 return null;
		}
	}

	public String getApprovalUserName(
			String companyId, long groupId, Object journalObj)
		throws Exception {

		User user = getFirstUser(groupId);

		if (user != null) {
			 return user.getFullName();
		}
		else {
			 return null;
		}
	}

	public String getApprovalUserId(
			String companyId, long groupId, Object journalObj)
		throws Exception {

		User user = getFirstUser(groupId);

		if (user != null) {
			 return user.getUserId();
		}
		else {
			 return null;
		}
	}

	public boolean addCommunityPermissions(
			String companyId, long groupId, Object journalObj)
		throws Exception {

		return true;
	}

	public boolean addGuestPermissions(
			String companyId, long groupId, Object journalObj)
		throws Exception {

		return true;
	}

	protected synchronized User getFirstUser(long groupId) throws Exception {
		User firstUser = null;

		if ((groupId == _lastGroupId) &&
			(_lastUserFound != null) && (_lastUserFound.get() != null)) {

			firstUser = (User)_lastUserFound.get();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Returning cached first user " + firstUser.getUserId() +
						" for group id " + groupId);
			}
		}
		else {
			List userList = GroupUtil.getUsers(groupId, 0, 1);

			if (userList.size() > 0) {
				firstUser = (User)userList.get(0);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Caching first user " + firstUser.getUserId() +
							" for group id " + groupId);
				}

				_lastGroupId = groupId;
				_lastUserFound = new SoftReference(firstUser);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("No users found for group id " + groupId);
				}
			}
		}

		return firstUser;
	}

	private static Log _log =
		LogFactory.getLog(JournalCreationStrategyImpl.class);

	private static long _lastGroupId = 0;
	private static SoftReference _lastUserFound = null;

}
