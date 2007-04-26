/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.service.base.MBStatsUserLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="MBStatsUserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBStatsUserLocalServiceImpl
	extends MBStatsUserLocalServiceBaseImpl {

	public void deleteStatsUserByGroupId(long groupId)
		throws SystemException {

		MBStatsUserUtil.removeByGroupId(groupId);
	}

	public void deleteStatsUserByUserId(long userId) throws SystemException {
		MBStatsUserUtil.removeByUserId(userId);
	}

	public MBStatsUser getStatsUser(long groupId, long userId)
		throws PortalException, SystemException {

		MBStatsUserPK statsUserPK = new MBStatsUserPK(groupId, userId);

		MBStatsUser statsUser = null;

		statsUser = MBStatsUserUtil.fetchByPrimaryKey(statsUserPK);

		if (statsUser == null) {
			statsUser = MBStatsUserUtil.create(statsUserPK);

			MBStatsUserUtil.update(statsUser);
		}

		return statsUser;
	}

	public List getStatsUsers(long groupId, int begin, int end)
		throws SystemException {

		return MBStatsUserUtil.findByG_M(groupId, 0, begin, end);
	}

	public int getStatsUsersCount(long groupId) throws SystemException {
		return MBStatsUserUtil.countByG_M(groupId, 0);
	}

	public void updateStatsUser(long groupId, long userId)
		throws PortalException, SystemException {

		update(groupId, userId);
		update(GroupImpl.DEFAULT_PARENT_GROUP_ID, userId);
	}

	protected void update(long groupId, long userId)
		throws PortalException, SystemException {

		MBStatsUserPK statsUserPK = new MBStatsUserPK(groupId, userId);

		MBStatsUser statsUser = MBStatsUserUtil.fetchByPrimaryKey(statsUserPK);

		if (statsUser == null) {
			statsUser = MBStatsUserUtil.create(statsUserPK);
		}

		statsUser.setLastPostDate(new Date());
		statsUser.setMessageCount(statsUser.getMessageCount() + 1);

		MBStatsUserUtil.update(statsUser);
	}

}