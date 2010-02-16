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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.service.base.MBStatsUserLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="MBStatsUserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBStatsUserLocalServiceImpl
	extends MBStatsUserLocalServiceBaseImpl {

	public MBStatsUser addStatsUser(long groupId, long userId)
		throws SystemException {

		long statsUserId = counterLocalService.increment();

		MBStatsUser statsUser = mbStatsUserPersistence.create(statsUserId);

		statsUser.setGroupId(groupId);
		statsUser.setUserId(userId);

		try {
			mbStatsUserPersistence.update(statsUser, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {groupId=" + groupId + ", userId=" +
						userId + "}");
			}

			statsUser = mbStatsUserPersistence.fetchByG_U(
				groupId, userId, false);

			if (statsUser == null) {
				throw se;
			}
		}

		return statsUser;
	}

	public void deleteStatsUsersByGroupId(long groupId)
		throws SystemException {

		mbStatsUserPersistence.removeByGroupId(groupId);
	}

	public void deleteStatsUsersByUserId(long userId) throws SystemException {
		mbStatsUserPersistence.removeByUserId(userId);
	}

	public int getMessageCountByUserId(long userId) throws SystemException {
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(
			MBStatsUser.class, MBStatsUserImpl.TABLE_NAME,
			PortalClassLoaderUtil.getClassLoader());

		query = query.setProjection(
			ProjectionFactoryUtil.sum("messageCount"));

		query = query.add(PropertyFactoryUtil.forName("userId").eq(userId));

		List<Object> results = dynamicQuery(query);

		return (Integer)results.get(0);
	}

	public MBStatsUser getStatsUser(long groupId, long userId)
		throws SystemException {

		MBStatsUser statsUser = mbStatsUserPersistence.fetchByG_U(
			groupId, userId);

		if (statsUser == null) {
			statsUser = mbStatsUserLocalService.addStatsUser(groupId, userId);
		}

		return statsUser;
	}

	public List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws SystemException {

		return mbStatsUserPersistence.findByG_M(groupId, 0, start, end);
	}

	public List<MBStatsUser> getStatsUsersByUserId(long userId)
		throws SystemException {

		return mbStatsUserPersistence.findByUserId(userId);
	}

	public int getStatsUsersByGroupIdCount(long groupId)
		throws SystemException {

		return mbStatsUserPersistence.countByG_M(groupId, 0);
	}

	public MBStatsUser updateStatsUser(long groupId, long userId)
		throws SystemException {

		return updateStatsUser(groupId, userId, null);
	}

	public MBStatsUser updateStatsUser(
			long groupId, long userId, Date lastPostDate)
		throws SystemException {

		int messageCount = mbMessagePersistence.countByG_U(groupId, userId);

		MBStatsUser statsUser = getStatsUser(groupId, userId);

		statsUser.setMessageCount(messageCount);

		if (lastPostDate != null) {
			statsUser.setLastPostDate(lastPostDate);
		}

		mbStatsUserPersistence.update(statsUser, false);

		return statsUser;
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBStatsUserLocalServiceImpl.class);

}