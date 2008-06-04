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
 * This class provides static methods for the
 * <code>com.liferay.portlet.blogs.service.BlogsEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.blogs.service.BlogsEntryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.blogs.service.BlogsEntryLocalService
 * @see com.liferay.portlet.blogs.service.BlogsEntryLocalServiceFactory
 *
 */
public class BlogsEntryLocalServiceUtil {
	public static com.liferay.portlet.blogs.model.BlogsEntry addBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.addBlogsEntry(blogsEntry);
	}

	public static void deleteBlogsEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.deleteBlogsEntry(entryId);
	}

	public static void deleteBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getBlogsEntry(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		long userId, long plid, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean draft, boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.addEntry(userId, plid, title, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, draft, allowTrackbacks, trackbacks, tagsEntries,
			addCommunityPermissions, addGuestPermissions, themeDisplay);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String uuid, long userId, long plid, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean draft, boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String[] tagsEntries, boolean addCommunityPermissions,
		boolean addGuestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.addEntry(uuid, userId, plid, title,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, draft, allowTrackbacks,
			trackbacks, tagsEntries, addCommunityPermissions,
			addGuestPermissions, themeDisplay);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		long userId, long plid, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean draft, boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String[] tagsEntries,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.addEntry(userId, plid, title, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, draft, allowTrackbacks, trackbacks, tagsEntries,
			communityPermissions, guestPermissions, themeDisplay);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String uuid, long userId, long plid, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean draft, boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String[] tagsEntries,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.addEntry(uuid, userId, plid, title,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, draft, allowTrackbacks,
			trackbacks, tagsEntries, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions,
			themeDisplay);
	}

	public static void addEntryResources(long entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.addEntryResources(entryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.addEntryResources(entry,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addEntryResources(long entryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.addEntryResources(entryId, communityPermissions,
			guestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.addEntryResources(entry, communityPermissions,
			guestPermissions);
	}

	public static void deleteEntries(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.deleteEntries(groupId);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.deleteEntry(entryId);
	}

	public static void deleteEntry(
		com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.deleteEntry(entry);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getCompanyEntries(companyId, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, boolean draft, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getCompanyEntries(companyId, draft,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, boolean draft, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getCompanyEntries(companyId, draft,
			start, end, obc);
	}

	public static int getCompanyEntriesCount(long companyId)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getCompanyEntriesCount(companyId);
	}

	public static int getCompanyEntriesCount(long companyId, boolean draft)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getCompanyEntriesCount(companyId, draft);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getEntry(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getEntry(groupId, urlTitle);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupEntries(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, boolean draft, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupEntries(groupId, draft, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, boolean draft, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupEntries(groupId, draft, start,
			end, obc);
	}

	public static int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupEntriesCount(groupId);
	}

	public static int getGroupEntriesCount(long groupId, boolean draft)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupEntriesCount(groupId, draft);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, boolean draft, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			draft, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, boolean draft, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			draft, start, end, obc);
	}

	public static int getGroupUserEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupUserEntriesCount(groupId, userId);
	}

	public static int getGroupUserEntriesCount(long groupId, long userId,
		boolean draft) throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getGroupUserEntriesCount(groupId, userId,
			draft);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getNoAssetEntries()
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getNoAssetEntries();
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, boolean draft, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getOrganizationEntries(organizationId,
			draft, start, end);
	}

	public static int getOrganizationEntriesCount(long organizationId,
		boolean draft) throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getOrganizationEntriesCount(organizationId,
			draft);
	}

	public static java.lang.String getUrlTitle(long entryId,
		java.lang.String title) {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.getUrlTitle(entryId, title);
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.reIndex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords, int start, int end)
		throws com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.search(companyId, groupId, userId,
			keywords, start, end);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean draft, boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String[] tagsEntries,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		return blogsEntryLocalService.updateEntry(userId, entryId, title,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, draft, allowTrackbacks,
			trackbacks, tagsEntries, themeDisplay);
	}

	public static void updateTagsAsset(long userId,
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] tagsEntries)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

		blogsEntryLocalService.updateTagsAsset(userId, entry, tagsEntries);
	}
}