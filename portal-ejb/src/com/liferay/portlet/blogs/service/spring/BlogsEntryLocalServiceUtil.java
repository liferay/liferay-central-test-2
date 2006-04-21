/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.blogs.service.spring;

/**
 * <a href="BlogsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsEntryLocalServiceUtil {
	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String userId, java.lang.String plid,
		java.lang.String categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.addEntry(userId, plid, categoryId,
				title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addEntryResources(java.lang.String entryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();
			blogsEntryLocalService.addEntryResources(entryId,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();
			blogsEntryLocalService.addEntryResources(entry,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteEntries(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();
			blogsEntryLocalService.deleteEntries(groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteEntry(java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();
			blogsEntryLocalService.deleteEntry(entryId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteEntry(
		com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();
			blogsEntryLocalService.deleteEntry(entry);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getCategoriesEntriesCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.getCategoriesEntriesCount(categoryIds);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getEntries(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.getEntries(categoryId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getEntriesCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.getEntriesCount(categoryId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.getEntry(entryId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getGroupEntries(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.getGroupEntries(groupId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getGroupEntriesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.getGroupEntriesCount(groupId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.lang.String getGroupEntriesRSS(
		java.lang.String groupId, int begin, int end, double version,
		java.lang.String url)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.getGroupEntriesRSS(groupId, begin,
				end, version, url);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();
			blogsEntryLocalService.reIndex(ids);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.util.lucene.Hits search(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String userId, java.lang.String[] categoryIds,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.search(companyId, groupId, userId,
				categoryIds, keywords);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		java.lang.String userId, java.lang.String entryId,
		java.lang.String categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsEntryLocalService blogsEntryLocalService = BlogsEntryLocalServiceFactory.getService();

			return blogsEntryLocalService.updateEntry(userId, entryId,
				categoryId, title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}