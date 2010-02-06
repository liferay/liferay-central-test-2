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
 * <a href="BlogsEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BlogsEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryService
 * @generated
 */
public class BlogsEntryServiceWrapper implements BlogsEntryService {
	public BlogsEntryServiceWrapper(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String title, java.lang.String content, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, boolean allowTrackbacks,
		java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.addEntry(title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowTrackbacks, trackbacks, serviceContext);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_blogsEntryService.deleteEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int status, int max)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getCompanyEntries(companyId, status, max);
	}

	public java.lang.String getCompanyEntriesRSS(long companyId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getCompanyEntriesRSS(companyId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getEntry(entryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getEntry(groupId, urlTitle);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int max)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getGroupEntries(groupId, status, max);
	}

	public java.lang.String getGroupEntriesRSS(long groupId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getGroupEntriesRSS(groupId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, int status, int max)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getOrganizationEntries(organizationId,
			status, max);
	}

	public java.lang.String getOrganizationEntriesRSS(long organizationId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.getOrganizationEntriesRSS(organizationId,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowTrackbacks,
		java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _blogsEntryService.updateEntry(entryId, title, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowTrackbacks, trackbacks, serviceContext);
	}

	public BlogsEntryService getWrappedBlogsEntryService() {
		return _blogsEntryService;
	}

	private BlogsEntryService _blogsEntryService;
}