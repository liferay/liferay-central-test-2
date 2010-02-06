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

package com.liferay.portlet.blogs.service;


/**
 * <a href="BlogsStatsUserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BlogsStatsUserLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUserLocalService
 * @generated
 */
public class BlogsStatsUserLocalServiceWrapper
	implements BlogsStatsUserLocalService {
	public BlogsStatsUserLocalServiceWrapper(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {
		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser addBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.addBlogsStatsUser(blogsStatsUser);
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser createBlogsStatsUser(
		long statsUserId) {
		return _blogsStatsUserLocalService.createBlogsStatsUser(statsUserId);
	}

	public void deleteBlogsStatsUser(long statsUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_blogsStatsUserLocalService.deleteBlogsStatsUser(statsUserId);
	}

	public void deleteBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser)
		throws com.liferay.portal.SystemException {
		_blogsStatsUserLocalService.deleteBlogsStatsUser(blogsStatsUser);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser getBlogsStatsUser(
		long statsUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getBlogsStatsUser(statsUserId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getBlogsStatsUsers(
		int start, int end) throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getBlogsStatsUsers(start, end);
	}

	public int getBlogsStatsUsersCount()
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getBlogsStatsUsersCount();
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser updateBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.updateBlogsStatsUser(blogsStatsUser);
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser updateBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser,
		boolean merge) throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.updateBlogsStatsUser(blogsStatsUser,
			merge);
	}

	public void deleteStatsUserByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		_blogsStatsUserLocalService.deleteStatsUserByGroupId(groupId);
	}

	public void deleteStatsUserByUserId(long userId)
		throws com.liferay.portal.SystemException {
		_blogsStatsUserLocalService.deleteStatsUserByUserId(userId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getCompanyStatsUsers(companyId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getCompanyStatsUsers(companyId,
			start, end, obc);
	}

	public int getCompanyStatsUsersCount(long companyId)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getCompanyStatsUsersCount(companyId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getGroupStatsUsers(groupId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getGroupStatsUsers(groupId, start,
			end, obc);
	}

	public int getGroupStatsUsersCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getGroupStatsUsersCount(groupId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getOrganizationStatsUsers(organizationId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getOrganizationStatsUsers(organizationId,
			start, end, obc);
	}

	public int getOrganizationStatsUsersCount(long organizationId)
		throws com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getOrganizationStatsUsersCount(organizationId);
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser getStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsStatsUserLocalService.getStatsUser(groupId, userId);
	}

	public void updateStatsUser(long groupId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_blogsStatsUserLocalService.updateStatsUser(groupId, userId);
	}

	public void updateStatsUser(long groupId, long userId,
		java.util.Date displayDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_blogsStatsUserLocalService.updateStatsUser(groupId, userId, displayDate);
	}

	public BlogsStatsUserLocalService getWrappedBlogsStatsUserLocalService() {
		return _blogsStatsUserLocalService;
	}

	private BlogsStatsUserLocalService _blogsStatsUserLocalService;
}