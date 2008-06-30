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

package com.liferay.portal.util;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ConcurrentHashSet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserTrackerLocalServiceUtil;
import com.liferay.portal.service.persistence.UserTrackerUtil;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LiveUsers.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Brian Wing Shun Chan
 *
 */
public class LiveUsers {

	public static void deleteGroup(long groupId) {
		_instance._deleteGroup(groupId);
	}

	public static Set<Long> getGroupUsers(long groupId) {
		return _instance._getGroupUsers(_instance._getLiveUsers(), groupId);
	}

	public static int getGroupUsersCount(long groupId) {
		return getGroupUsers(groupId).size();
	}

	public static Map<String, UserTracker> getSessionUsers() {
		return _instance._getSessionUsers();
	}

	public static int getSessionUsersCount() {
		return getSessionUsers().size();
	}

	public static UserTracker getUserTracker(String sesId) {
		return _instance._getUserTracker(sesId);
	}

	public static void joinGroup(long userId, long groupId) {
		_instance._joinGroup(userId, groupId);
	}

	public static void joinGroup(long[] userIds, long groupId) {
		_instance._joinGroup(userIds, groupId);
	}

	public static void leaveGroup(long userId, long groupId) {
		_instance._leaveGroup(userId, groupId);
	}

	public static void leaveGroup(long[] userIds, long groupId) {
		_instance._leaveGroup(userIds, groupId);
	}

	public static void signIn(HttpServletRequest request)
		throws SystemException {

		_instance._signIn(request);
	}

	public static void signOut(String sessionId, long userId)
		throws SystemException {

		_instance._signOut(sessionId, userId);
	}

	private LiveUsers() {
	}

	private void _addUserTracker(long userId, UserTracker userTracker) {
		List<UserTracker> userTrackers = _getUserTrackers(userId);

		if (userTrackers != null) {
			userTrackers.add(userTracker);
		}
		else {
			userTrackers = new ArrayList<UserTracker>();

			userTrackers.add(userTracker);

			Map<Long, List<UserTracker>> userTrackersMap =
				_getUserTrackersMap();

			userTrackersMap.put(userId, userTrackers);
		}
	}

	private void _deleteGroup(long groupId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers();

		liveUsers.remove(groupId);
	}

	private Set<Long> _getGroupUsers(
		Map<Long, Set<Long>> liveUsers, long groupId) {

		Set<Long> groupUsers = liveUsers.get(groupId);

		if (groupUsers == null) {
			groupUsers = new ConcurrentHashSet<Long>();

			liveUsers.put(groupId, groupUsers);
		}

		return groupUsers;
	}

	private Map<Long, Set<Long>> _getLiveUsers() {
		String companyIdString = String.valueOf(
			CompanyThreadLocal.getCompanyId());

		Map<Long, Set<Long>> liveUsers = (Map<Long, Set<Long>>)WebAppPool.get(
			companyIdString, WebKeys.LIVE_USERS);

		if (liveUsers == null) {
			liveUsers = new ConcurrentHashMap<Long, Set<Long>>();

			WebAppPool.put(companyIdString, WebKeys.LIVE_USERS, liveUsers);
		}

		return liveUsers;
	}

	private Map<String, UserTracker> _getSessionUsers() {
		String companyIdString = String.valueOf(
			CompanyThreadLocal.getCompanyId());

		Map<String, UserTracker> sessionUsers =
			(Map<String, UserTracker>)WebAppPool.get(
				companyIdString, WebKeys.LIVE_SESSION_USERS);

		if (sessionUsers == null) {
			sessionUsers = new ConcurrentHashMap<String, UserTracker>();

			WebAppPool.put(
				companyIdString, WebKeys.LIVE_SESSION_USERS, sessionUsers);
		}

		return sessionUsers;
	}

	private UserTracker _getUserTracker(String sessionId) {
		Map<String, UserTracker> sessionUsers = _getSessionUsers();

		return sessionUsers.get(sessionId);
	}

	private List<UserTracker> _getUserTrackers(long userId) {
		Map<Long, List<UserTracker>> userTrackersMap = _getUserTrackersMap();

		return userTrackersMap.get(userId);
	}

	private Map<Long, List<UserTracker>> _getUserTrackersMap() {
		String companyIdString = String.valueOf(
			CompanyThreadLocal.getCompanyId());

		Map<Long, List<UserTracker>> userTrackersMap =
			(Map<Long, List<UserTracker>>)WebAppPool.get(
				companyIdString, WebKeys.LIVE_USER_TRACKERS);

		if (userTrackersMap == null) {
			userTrackersMap = new ConcurrentHashMap<Long, List<UserTracker>>();

			WebAppPool.put(
				companyIdString, WebKeys.LIVE_USER_TRACKERS, userTrackersMap);
		}

		return userTrackersMap;
	}

	private void _joinGroup(long userId, long groupId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers();

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		if (_getUserTrackers(userId) != null) {
			groupUsers.add(userId);
		}
	}

	private void _joinGroup(long[] userIds, long groupId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers();

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		for (long userId : userIds) {
			if (_getUserTrackers(userId) != null) {
				groupUsers.add(userId);
			}
		}
	}

	private void _leaveGroup(long userId, long groupId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers();

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		groupUsers.remove(userId);
	}

	private void _leaveGroup(long[] userIds, long groupId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers();

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		for (long userId : userIds) {
			groupUsers.remove(userId);
		}
	}

	private void _removeUserTracker(long userId, UserTracker userTracker) {
		List<UserTracker> userTrackers = _getUserTrackers(userId);

		if (userTrackers != null) {
			String sessionId = userTracker.getSessionId();

			Iterator<UserTracker> itr = userTrackers.iterator();

			while (itr.hasNext()) {
				UserTracker curUserTracker = itr.next();

				if (sessionId.equals(curUserTracker.getSessionId())) {
					itr.remove();
				}
			}

			if (userTrackers.size() == 0) {
				Map<Long, List<UserTracker>> userTrackersMap =
					_getUserTrackersMap();

				userTrackersMap.remove(userId);
			}
		}
	}

	private void _signIn(HttpServletRequest request) throws SystemException {
		HttpSession session = request.getSession();

		long companyId = CompanyThreadLocal.getCompanyId();
		long userId = GetterUtil.getLong(request.getRemoteUser());

		_updateGroupStatus(userId, true);

		Map<String, UserTracker> sessionUsers = _getSessionUsers();

		List<UserTracker> userTrackers = _getUserTrackers(userId);

		if (!PropsValues.AUTH_SIMULTANEOUS_LOGINS) {
			if (userTrackers != null) {
				for (UserTracker userTracker : userTrackers) {

					// Disable old login

					userTracker.getHttpSession().setAttribute(
						WebKeys.STALE_SESSION, Boolean.TRUE);
				}
			}
		}

		UserTracker userTracker = sessionUsers.get(session.getId());

		if ((userTracker == null) &&
			(PropsValues.SESSION_TRACKER_MEMORY_ENABLED)) {

			userTracker = UserTrackerUtil.create(0);

			userTracker.setCompanyId(companyId);
			userTracker.setUserId(userId);
			userTracker.setModifiedDate(new Date());
			userTracker.setHttpSession(session);
			userTracker.setRemoteAddr(request.getRemoteAddr());
			userTracker.setRemoteHost(request.getRemoteHost());
			userTracker.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));

			sessionUsers.put(session.getId(), userTracker);

			_addUserTracker(userId, userTracker);
		}
	}

	private void _signOut(String sessionId, long userId)
		throws SystemException {

		List<UserTracker> userTrackers = _getUserTrackers(userId);

		if ((userTrackers == null) || (userTrackers.size() <= 1)) {
			_updateGroupStatus(userId, false);
		}

		Map<String, UserTracker> sessionUsers = _getSessionUsers();

		UserTracker userTracker = sessionUsers.remove(sessionId);

		if (userTracker != null) {
			try {
				UserTrackerLocalServiceUtil.addUserTracker(
					userTracker.getCompanyId(), userTracker.getUserId(),
					userTracker.getModifiedDate(), sessionId,
					userTracker.getRemoteAddr(), userTracker.getRemoteHost(),
					userTracker.getUserAgent(), userTracker.getPaths());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			_removeUserTracker(userId, userTracker);
		}
	}

	private Map<Long, Set<Long>> _updateGroupStatus(
			long userId, boolean signedIn)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		Map<Long, Set<Long>> liveUsers = _getLiveUsers();

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("usersGroups", userId);

		List<Group> communities = GroupLocalServiceUtil.search(
			companyId, null, null, groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (Group community : communities) {
			Set<Long> groupUsers = _getGroupUsers(
				liveUsers, community.getGroupId());

			if (signedIn) {
				groupUsers.add(userId);
			}
			else {
				groupUsers.remove(userId);
			}
		}

		return liveUsers;
	}

	private static Log _log = LogFactory.getLog(LiveUsers.class);

	private static LiveUsers _instance = new LiveUsers();

}