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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.base.BlogsEntryServiceBaseImpl;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.portlet.blogs.util.comparator.EntryDisplayDateComparator;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BlogsEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsEntryServiceImpl extends BlogsEntryServiceBaseImpl {

	public BlogsEntry addEntry(
			String title, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean draft, boolean allowTrackbacks,
			String[] trackbacks, ServiceContext serviceContext)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), serviceContext.getPlid(), PortletKeys.BLOGS,
			ActionKeys.ADD_ENTRY);

		return blogsEntryLocalService.addEntry(
			getUserId(), title, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, draft,
			allowTrackbacks, trackbacks, serviceContext);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		blogsEntryLocalService.deleteEntry(entryId);
	}

	public List<BlogsEntry> getCompanyEntries(long companyId, int max)
		throws PortalException, SystemException {

		return getCompanyEntries(companyId, max, false);
	}

	public List<BlogsEntry> getPublishedCompanyEntries(long companyId, int max)
		throws PortalException, SystemException {

		return getCompanyEntries(companyId, max, true);
	}

	private List<BlogsEntry> getCompanyEntries(long companyId, int max, boolean onlyPublished)
		throws PortalException, SystemException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((entries.size() < max) && listNotExhausted) {
			List<BlogsEntry> entryList;

			if (onlyPublished) {
				entryList =	blogsEntryLocalService.getPublishedCompanyEntries(
						companyId, false, lastIntervalStart,
						lastIntervalStart + max, new EntryDisplayDateComparator());
			}
			else {
				entryList =	blogsEntryLocalService.getCompanyEntries(
					companyId, false, lastIntervalStart,
					lastIntervalStart + max, new EntryDisplayDateComparator());
			}

			Iterator<BlogsEntry> itr = entryList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (entryList.size() == max);

			while (itr.hasNext() && (entries.size() < max)) {
				BlogsEntry entry = itr.next();

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	public String getCompanyEntriesRSS(
			long companyId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getCompanyEntriesRSS(
			companyId, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay, false);
	}

	public String getPublishedCompanyEntriesRSS(
			long companyId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getCompanyEntriesRSS(
			companyId, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay, true);
	}

	private String getCompanyEntriesRSS(
			long companyId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay, boolean onlyPublished)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();
		String description = name;
		List<BlogsEntry> blogsEntries;
		if (onlyPublished) blogsEntries = getPublishedCompanyEntries(companyId, max);
		else blogsEntries = getCompanyEntries(companyId, max);

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return blogsEntryLocalService.getEntry(entryId);
	}

	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryLocalService.getEntry(groupId, urlTitle);

		BlogsEntryPermission.check(
			getPermissionChecker(), entry.getEntryId(), ActionKeys.VIEW);

		return entry;
	}

	public List<BlogsEntry> getGroupEntries(long groupId, int max)
		throws PortalException, SystemException {

		return getGroupEntries(groupId, max, false);
	}

	public List<BlogsEntry> getPublishedGroupEntries(long groupId, int max)
		throws PortalException, SystemException {

		return getGroupEntries(groupId, max, true);
	}

	private List<BlogsEntry> getGroupEntries(long groupId, int max, boolean onlyPublished)
		throws PortalException, SystemException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((entries.size() < max) && listNotExhausted) {
			List<BlogsEntry> entryList;

			if (onlyPublished) {
				entryList = blogsEntryLocalService.getPublishedGroupEntries(
						groupId, false, lastIntervalStart,
						lastIntervalStart + max);
			}
			else {
				entryList = blogsEntryLocalService.getGroupEntries(
					groupId, false, lastIntervalStart,
					lastIntervalStart + max);
			}

			Iterator<BlogsEntry> itr = entryList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (entryList.size() == max);

			while (itr.hasNext() && (entries.size() < max)) {
				BlogsEntry entry = itr.next();

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	public String getGroupEntriesRSS(
			long groupId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getGroupEntriesRSS(
			groupId, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay, false);
	}

	public String getPublishedGroupEntriesRSS(
			long groupId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getGroupEntriesRSS(
			groupId, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay, true);
	}

	private String getGroupEntriesRSS(
			long groupId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay, boolean onlyPublished)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String name = group.getDescriptiveName();
		String description = name;
		List<BlogsEntry> blogsEntries;
		if (onlyPublished) blogsEntries = getPublishedGroupEntries(groupId, max);
		else blogsEntries = getGroupEntries(groupId, max);

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	public List<BlogsEntry> getOrganizationEntries(long organizationId, int max)
		throws PortalException, SystemException {

		return getOrganizationEntries(organizationId, max, false);
	}

	public List<BlogsEntry> getPublishedOrganizationEntries(long organizationId, int max)
		throws PortalException, SystemException {

	return getOrganizationEntries(organizationId, max, true);
}

	private List<BlogsEntry> getOrganizationEntries(long organizationId, int max, boolean onlyPublished)
		throws PortalException, SystemException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((entries.size() < max) && listNotExhausted) {
			List<BlogsEntry> entryList;

			if (onlyPublished) {
				entryList = blogsEntryFinder.findPublishedByOrganizationId(
						organizationId, false, lastIntervalStart,
						lastIntervalStart + max);
			}
			else {
				entryList = blogsEntryFinder.findByOrganizationId(
					organizationId, false, lastIntervalStart,
					lastIntervalStart + max);
			}

			Iterator<BlogsEntry> itr = entryList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (entryList.size() == max);

			while (itr.hasNext() && (entries.size() < max)) {
				BlogsEntry entry = itr.next();

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	public String getOrganizationEntriesRSS(
			long organizationId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getOrganizationEntriesRSS(organizationId, max, type, version,
				displayStyle, feedURL, entryURL, themeDisplay, false);
	}

	public String getPublishedOrganizationEntriesRSS(
			long organizationId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getOrganizationEntriesRSS(organizationId, max, type, version,
				displayStyle, feedURL, entryURL, themeDisplay, true);
	}

	private String getOrganizationEntriesRSS(
			long organizationId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay, boolean onlyPublished)
		throws PortalException, SystemException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		String name = organization.getName();
		String description = name;
		List<BlogsEntry> blogsEntries;
		if (onlyPublished)  blogsEntries = getPublishedOrganizationEntries(organizationId, max);
		else blogsEntries = getOrganizationEntries(organizationId, max);

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	public BlogsEntry updateEntry(
			long entryId, String title, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean draft, boolean allowTrackbacks,
			String[] trackbacks, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return blogsEntryLocalService.updateEntry(
			getUserId(), entryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			draft, allowTrackbacks, trackbacks, serviceContext);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			List<BlogsEntry> blogsEntries, ThemeDisplay themeDisplay)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(description);

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		for (BlogsEntry entry : blogsEntries) {
			String author = PortalUtil.getUserName(
				entry.getUserId(), entry.getUserName());

			String link = entryURL;

			if (link.endsWith("/blogs/rss")) {
				link =
					link.substring(0, link.length() - 3) + entry.getUrlTitle();
			}
			else {
				if (!link.endsWith("?")) {
					link += "&";
				}

				link += "entryId=" + entry.getEntryId();
			}

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
				value = StringUtil.shorten(
					HtmlUtil.extractText(entry.getContent()),
					_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
			}
			else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = StringUtil.replace(
					entry.getContent(),
					new String[] {
						"href=\"/",
						"src=\"/"
					},
					new String[] {
						"href=\"" + themeDisplay.getURLPortal() + "/",
						"src=\"" + themeDisplay.getURLPortal() + "/"
					}
				);
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(entry.getTitle());
			syndEntry.setLink(link);
			syndEntry.setPublishedDate(entry.getCreateDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.DEFAULT_ENTRY_TYPE);
			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
		}

	private static final int _RSS_ABSTRACT_LENGTH = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.BLOGS_RSS_ABSTRACT_LENGTH));

}
