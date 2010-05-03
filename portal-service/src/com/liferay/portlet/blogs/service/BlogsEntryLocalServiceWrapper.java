/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.blogs.service;


/**
 * <a href="BlogsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BlogsEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryLocalService
 * @generated
 */
public class BlogsEntryLocalServiceWrapper implements BlogsEntryLocalService {
	public BlogsEntryLocalServiceWrapper(
		BlogsEntryLocalService blogsEntryLocalService) {
		_blogsEntryLocalService = blogsEntryLocalService;
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.addBlogsEntry(blogsEntry);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry createBlogsEntry(
		long entryId) {
		return _blogsEntryLocalService.createBlogsEntry(entryId);
	}

	public void deleteBlogsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteBlogsEntry(entryId);
	}

	public void deleteBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntry(entryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntryByUuidAndGroupId(uuid,
			groupId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getBlogsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntries(start, end);
	}

	public int getBlogsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntriesCount();
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateBlogsEntry(blogsEntry, merge);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String uuid, long userId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.addEntry(uuid, userId, title, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			serviceContext);
	}

	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entry,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entry, communityPermissions,
			guestPermissions);
	}

	public void addEntryResources(long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entryId,
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteEntries(groupId);
	}

	public void deleteEntry(com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteEntry(entry);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getCompanyEntries(companyId, status,
			start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getCompanyEntries(companyId, status,
			start, end, obc);
	}

	public int getCompanyEntriesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getCompanyEntriesCount(companyId, status);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry[] getEntriesPrevAndNext(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getEntriesPrevAndNext(entryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getEntry(entryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getEntry(groupId, urlTitle);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupEntries(groupId, status, start,
			end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupEntries(groupId, status, start,
			end, obc);
	}

	public int getGroupEntriesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId, status);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupsEntries(companyId, groupId,
			status, start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			status, start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			status, start, end, obc);
	}

	public int getGroupUserEntriesCount(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupUserEntriesCount(groupId,
			userId, status);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getNoAssetEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getNoAssetEntries();
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getOrganizationEntries(organizationId,
			status, start, end);
	}

	public int getOrganizationEntriesCount(long organizationId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getOrganizationEntriesCount(organizationId,
			status);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.updateAsset(userId, entry, assetCategoryIds,
			assetTagNames);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, serviceContext);
	}

	public void updateEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.updateEntryResources(entry,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateStatus(
		long userId, long entryId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateStatus(userId, entryId, status,
			serviceContext);
	}

	public BlogsEntryLocalService getWrappedBlogsEntryLocalService() {
		return _blogsEntryLocalService;
	}

	private BlogsEntryLocalService _blogsEntryLocalService;
}