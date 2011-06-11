/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.atom;

import com.liferay.portal.atom.AtomPager;
import com.liferay.portal.atom.AtomUtil;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomException;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public class BlogsEntryCollectionAdapter implements
	AtomCollectionAdapter<BlogsEntry> {

	public void deleteEntry(String resourceName) throws AtomException {

		long blogsEntryId = GetterUtil.getLong(resourceName);

		if (blogsEntryId == 0) {
			throw new AtomException(404);
		}

		try {
			BlogsEntryServiceUtil.deleteEntry(blogsEntryId);
		}
		catch (Exception e) {
			throw new AtomException(500, e);
		}
	}

	public String getCollectionName() {
		return "blogs";
	}

	public BlogsEntry getEntry(String resourceName) throws AtomException {

		long blogsEntryId = GetterUtil.getLong(resourceName);

		if (blogsEntryId == 0) {
			throw new AtomException(404);
		}

		BlogsEntry blogsEntry = null;

		try {
			blogsEntry = BlogsEntryServiceUtil.getEntry(blogsEntryId);
		}
		catch (Exception e) {
			throw new AtomException(500, e);
		}

		return blogsEntry;
	}

	public List<String> getEntryAuthors(BlogsEntry blogsEntry) {

		List<String> authors = new ArrayList<String>();

		authors.add(blogsEntry.getUserName());

		return authors;
	}

	public String getEntryContent(BlogsEntry blogsEntry) {
		return blogsEntry.getContent();
	}

	public String getEntryId(BlogsEntry blogsEntry) {
		return String.valueOf(blogsEntry.getEntryId());
	}

	public String getEntryTitle(BlogsEntry blogsEntry) {
		return blogsEntry.getTitle();
	}

	public Date getEntryUpdated(BlogsEntry blogsEntry) {
		return blogsEntry.getModifiedDate();
	}

	public Iterable<BlogsEntry> getFeedEntries(
		AtomRequestContext requestContext)
		throws AtomException {

		List<BlogsEntry> blogsEntries = new ArrayList<BlogsEntry>();

		try {
			long companyId = CompanyThreadLocal.getCompanyId().longValue();

			long groupId = requestContext.getLongParameter("groupId");

			long organizationId =
				requestContext.getLongParameter("organizationId");

			int max = requestContext.getIntParameter(
				"max", SearchContainer.DEFAULT_DELTA);

			int page = requestContext.getIntParameter("page");

			int status = WorkflowConstants.STATUS_APPROVED;

			if (groupId > 0) {

				if (page != 0) {

					int blogsEntriesCount =
						BlogsEntryServiceUtil.getGroupEntriesCount(
							groupId, status);

					AtomPager atomPager =
						new AtomPager(page, blogsEntriesCount, max);

					AtomUtil.saveAtomPagerInRequest(requestContext, atomPager);

					blogsEntries = BlogsEntryServiceUtil.getGroupEntries(
						groupId, status, atomPager.getStart(),
						atomPager.getEnd() + 1);

				}
				else {
					blogsEntries = BlogsEntryServiceUtil.getGroupEntries(
						groupId, status, max);
				}
			}
			else if (organizationId > 0) {
				blogsEntries = BlogsEntryServiceUtil.getOrganizationEntries(
					organizationId, status, max);
			}
			else if (companyId > 0) {
				blogsEntries = BlogsEntryServiceUtil.getCompanyEntries(
					companyId, status, max);
			}
		}
		catch (Exception e) {
			throw new AtomException(500, e);
		}

		return blogsEntries;
	}

	public String getFeedTitle(AtomRequestContext requestContext) {
		String portletTitle = null;

		try {
			portletTitle = AtomUtil.createFeedTitleFromPortletName(
				requestContext, PortletKeys.BLOGS);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return portletTitle;
	}

	public BlogsEntry postEntry(String title, String summary, Date date,
		String content, AtomRequestContext requestContext)
		throws AtomException {

		BlogsEntry blogsEntry = null;

		try {
			long groupId = requestContext.getLongParameter("groupId");

			boolean allowPingbacks = true;
			boolean allowTrackbacks = true;
			String trackbacks[] = new String[0];

			Calendar cal = Calendar.getInstance();

			cal.setTime(date);

			int displayDateMonth = cal.get(Calendar.MONTH);
			int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = cal.get(Calendar.YEAR);
			int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
			int displayDateMinute = cal.get(Calendar.MINUTE);

			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setScopeGroupId(groupId);

			blogsEntry = BlogsEntryServiceUtil.addEntry(
				title, summary, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				allowPingbacks, allowTrackbacks, trackbacks, false, null, null,
				serviceContext);
		}
		catch (Exception e) {
			throw new AtomException(500, e);
		}

		return blogsEntry;
	}

	public void putEntry(BlogsEntry blogsEntry, String title, Date date,
		String summary, String content, AtomRequestContext requestContext)
		throws AtomException {

		try {
			long blogsEntryId = blogsEntry.getEntryId();

			String trackbacks[] = StringUtil.split(blogsEntry.getTrackbacks());

			long smallImageId = blogsEntry.getSmallImageId();

			File smallImageFile = null;

			if (smallImageId != 0) {
				Image smallImage = ImageLocalServiceUtil.getImage(smallImageId);

				if (smallImage != null) {
					byte[] smallImageBytes = smallImage.getTextObj();

					smallImageFile = FileUtil.createTempFile(
						smallImageId + StringPool.PERIOD +
							blogsEntry.getSmallImageType());

					FileUtil.write(smallImageFile, smallImageBytes);
				}
			}

			Calendar cal = Calendar.getInstance();

			cal.setTime(date);

			int displayDateMonth = cal.get(Calendar.MONTH);
			int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = cal.get(Calendar.YEAR);
			int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
			int displayDateMinute = cal.get(Calendar.MINUTE);

			ServiceContext serviceContext = new ServiceContext();

			BlogsEntryServiceUtil.updateEntry(
				blogsEntryId, title, summary, content, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute,
				blogsEntry.getAllowPingbacks(),
				blogsEntry.getAllowTrackbacks(),
				trackbacks,
				blogsEntry.getSmallImage(),
				blogsEntry.getSmallImageURL(),
				smallImageFile,
				serviceContext);

			if (smallImageFile != null) {
				smallImageFile.delete();
			}
		}
		catch (Exception e) {
			throw new AtomException(500, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BlogsEntryCollectionAdapter.class);

}