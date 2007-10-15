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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="BlogsStatsUserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsStatsUserLocalServiceImpl
	extends BlogsStatsUserLocalServiceBaseImpl {

	public void deleteStatsUserByGroupId(long groupId)
		throws SystemException {

		BlogsStatsUserUtil.removeByGroupId(groupId);
	}

	public void deleteStatsUserByUserId(long userId) throws SystemException {
		BlogsStatsUserUtil.removeByUserId(userId);
	}

	public List getCompanyStatsUsers(
			long companyId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return BlogsStatsUserUtil.findByC_E(companyId, 0, begin, end, obc);
	}

	public int getCompanyStatsUsersCount(long companyId)
		throws SystemException {

		return BlogsStatsUserUtil.countByC_E(companyId, 0);
	}

	public List getGroupStatsUsers(
			long groupId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return BlogsStatsUserUtil.findByG_E(groupId, 0, begin, end, obc);
	}

	public int getGroupStatsUsersCount(long groupId) throws SystemException {
		return BlogsStatsUserUtil.countByG_E(groupId, 0);
	}

	public BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException, SystemException {

		BlogsStatsUser statsUser = BlogsStatsUserUtil.fetchByG_U(
			groupId, userId);

		if (statsUser == null) {
			Group group = GroupUtil.findByPrimaryKey(groupId);

			long statsUserId = CounterLocalServiceUtil.increment();

			statsUser = BlogsStatsUserUtil.create(statsUserId);

			statsUser.setCompanyId(group.getCompanyId());
			statsUser.setGroupId(groupId);
			statsUser.setUserId(userId);

			BlogsStatsUserUtil.update(statsUser);
		}

		return statsUser;
	}

	public void updateStatsUser(long groupId, long userId, Date lastPostDate)
		throws PortalException, SystemException {

		int entryCount = BlogsEntryUtil.countByG_U(groupId, userId);

		BlogsStatsUser statsUser = getStatsUser(groupId, userId);

		statsUser.setEntryCount(entryCount);
		statsUser.setLastPostDate(lastPostDate);

		BlogsStatsUserUtil.update(statsUser);
	}

}