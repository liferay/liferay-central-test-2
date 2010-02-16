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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="BlogsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link BlogsEntryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryLocalService
 * @generated
 */
public class BlogsEntryLocalServiceUtil {
	public static com.liferay.portlet.blogs.model.BlogsEntry addBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addBlogsEntry(blogsEntry);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry createBlogsEntry(
		long entryId) {
		return getService().createBlogsEntry(entryId);
	}

	public static void deleteBlogsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteBlogsEntry(entryId);
	}

	public static void deleteBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteBlogsEntry(blogsEntry);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBlogsEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getBlogsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBlogsEntries(start, end);
	}

	public static int getBlogsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBlogsEntriesCount();
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateBlogsEntry(blogsEntry);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateBlogsEntry(blogsEntry, merge);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String uuid, long userId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(uuid, userId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowTrackbacks, trackbacks, serviceContext);
	}

	public static void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entry, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public static void addEntryResources(long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEntryResources(long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entryId, communityPermissions, guestPermissions);
	}

	public static void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntries(groupId);
	}

	public static void deleteEntry(
		com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entry);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntries(companyId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntries(companyId, status, start, end, obc);
	}

	public static int getCompanyEntriesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntriesCount(companyId, status);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] getEntriesPrevAndNext(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesPrevAndNext(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(groupId, urlTitle);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupEntries(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupEntries(groupId, status, start, end, obc);
	}

	public static int getGroupEntriesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupEntriesCount(groupId, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupUserEntries(groupId, userId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupUserEntries(groupId, userId, status, start, end, obc);
	}

	public static int getGroupUserEntriesCount(long groupId, long userId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUserEntriesCount(groupId, userId, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getNoAssetEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNoAssetEntries();
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getOrganizationEntries(organizationId, status, start, end);
	}

	public static int getOrganizationEntriesCount(long organizationId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationEntriesCount(organizationId, status);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateAsset(userId, entry, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, entryId, title, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowTrackbacks, trackbacks, serviceContext);
	}

	public static void updateEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateEntryResources(entry, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateWorkflowStatus(
		long userId, long entryId, java.lang.String[] trackbacks,
		boolean pingOldTrackbaks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateWorkflowStatus(userId, entryId, trackbacks,
			pingOldTrackbaks, serviceContext);
	}

	public static BlogsEntryLocalService getService() {
		if (_service == null) {
			_service = (BlogsEntryLocalService)PortalBeanLocatorUtil.locate(BlogsEntryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(BlogsEntryLocalService service) {
		_service = service;
	}

	private static BlogsEntryLocalService _service;
}