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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.DuplicatePageException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.PageTitleException;
import com.liferay.portlet.wiki.PageVersionException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageDisplay;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageDisplayImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.service.base.WikiPageLocalServiceBaseImpl;
import com.liferay.portlet.wiki.social.WikiActivityKeys;
import com.liferay.portlet.wiki.util.Indexer;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.util.MathUtil;
import com.liferay.util.UniqueList;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WikiPageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class WikiPageLocalServiceImpl extends WikiPageLocalServiceBaseImpl {

	public WikiPage addPage(
			long userId, long nodeId, String title, String content,
			String summary, boolean minorEdit, PortletPreferences prefs,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String uuid = null;
		double version = WikiPageImpl.DEFAULT_VERSION;
		String format = WikiPageImpl.DEFAULT_FORMAT;
		boolean head = true;
		String parentTitle = null;
		String redirectTitle = null;
		String[] tagsEntries = null;

		return addPage(
			uuid, userId, nodeId, title, version, content, summary, minorEdit,
			format, head, parentTitle, redirectTitle, tagsEntries, prefs,
			themeDisplay);
	}

	public WikiPage addPage(
			String uuid, long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			boolean head, String parentTitle, String redirectTitle,
			String[] tagsEntries, PortletPreferences prefs,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);
		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		Date now = new Date();

		validate(title, nodeId, content, format);

		long pageId = counterLocalService.increment();

		long resourcePrimKey =
			wikiPageResourceLocalService.getPageResourcePrimKey(nodeId, title);

		WikiPage page = wikiPagePersistence.create(pageId);

		page.setUuid(uuid);
		page.setResourcePrimKey(resourcePrimKey);
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setCreateDate(now);
		page.setModifiedDate(now);
		page.setNodeId(nodeId);
		page.setTitle(title);
		page.setVersion(version);
		page.setContent(content);
		page.setSummary(summary);
		page.setFormat(format);
		page.setHead(head);
		page.setParentTitle(parentTitle);
		page.setRedirectTitle(redirectTitle);

		wikiPagePersistence.update(page, false);

		// Resources

		addPageResources(page.getNode(), page, true, true);

		// Node

		node.setLastPostDate(now);

		wikiNodePersistence.update(node, false);

		// Social

		socialActivityLocalService.addActivity(
			userId, node.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), WikiActivityKeys.ADD_PAGE,
			StringPool.BLANK, 0);

		// Subscriptions

		if (!minorEdit && NotificationThreadLocal.isNotificationEnabled()) {
			notifySubscribers(node, page, prefs, themeDisplay, false);
		}

		// Tags

		updateTagsAsset(userId, page, tagsEntries);

		// Lucene

		try {
			Indexer.addPage(
				page.getCompanyId(), node.getGroupId(), nodeId, title,
				content, tagsEntries);
		}
		catch (SearchException se) {
			_log.error("Indexing " + pageId, se);
		}

		// Cache

		clearReferralsCache(page);
		clearPageCache(page);

		return page;
	}

	public void addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, byte[]>> files)
		throws PortalException, SystemException {

		if (files.size() == 0) {
			return;
		}

		WikiPage page = getPage(nodeId, title);

		long companyId = page.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long groupId = GroupImpl.DEFAULT_PARENT_GROUP_ID;
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = page.getAttachmentsDir();

		try {
			try {
				dlService.addDirectory(companyId, repositoryId, dirName);
			}
			catch (DuplicateDirectoryException dde) {
			}

			for (int i = 0; i < files.size(); i++) {
				ObjectValuePair<String, byte[]> ovp = files.get(i);

				String fileName = ovp.getKey();
				byte[] bytes = ovp.getValue();

				if (Validator.isNull(fileName)) {
					continue;
				}

				try {
					dlService.addFile(
						companyId, portletId, groupId, repositoryId,
						dirName + "/" + fileName, StringPool.BLANK,
						new String[0], bytes);
				}
				catch (DuplicateFileException dfe) {
				}
			}
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

	public void addPageResources(
			long nodeId, String title, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);
		WikiPage page = getPage(nodeId, title);

		addPageResources(
			node, page, addCommunityPermissions, addGuestPermissions);
	}

	public void addPageResources(
			WikiNode node, WikiPage page, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			page.getCompanyId(), node.getGroupId(),	page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addPageResources(
			long nodeId, String title, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);
		WikiPage page = getPage(nodeId, title);

		addPageResources(node, page, communityPermissions, guestPermissions);
	}

	public void addPageResources(
			WikiNode node, WikiPage page, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			page.getCompanyId(), node.getGroupId(),	page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(),
			communityPermissions, guestPermissions);
	}

	public void changeParent(
			long userId, long nodeId, String title, String newParentTitle,
			PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		String originalParentTitle = page.getParentTitle();

		double version = page.getVersion();
		String content = page.getContent();
		String summary = themeDisplay.translate(
			"changed-parent-from-x", originalParentTitle);
		boolean minorEdit = false;
		String format = page.getFormat();
		String redirectTitle = page.getRedirectTitle();
		String[] tagsEntries = null;

		updatePage(
			userId, nodeId, title, version, content, summary, minorEdit,
			format, newParentTitle, redirectTitle, tagsEntries, prefs,
			themeDisplay);

		List<WikiPage> oldPages = wikiPagePersistence.findByN_T_H(
			nodeId, title, false);

		for (WikiPage oldPage : oldPages) {
			oldPage.setParentTitle(originalParentTitle);

			wikiPagePersistence.update(oldPage, false);
		}
	}

	public void deletePage(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (pages.size() > 0) {
			WikiPage page = pages.iterator().next();

			deletePage(page);
		}
	}

	public void deletePage(WikiPage page)
		throws PortalException, SystemException {

		// Children

		List<WikiPage> children = wikiPagePersistence.findByN_P(
			page.getNodeId(), page.getTitle());

		for (WikiPage curPage : children) {
			deletePage(curPage);
		}

		// Lucene

		try {
			Indexer.deletePage(
				page.getCompanyId(), page.getNodeId(), page.getTitle());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + page.getPrimaryKey(), se);
		}

		// Attachments

		long companyId = page.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = page.getAttachmentsDir();

		try {
			dlService.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// Tags

		tagsAssetLocalService.deleteAsset(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			page.getCompanyId(), WikiPage.class.getName(), page.getPageId());

		// Social

		socialActivityLocalService.deleteActivities(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Resources

		resourceLocalService.deleteResource(
			page.getCompanyId(), WikiPage.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, page.getResourcePrimKey());

		// Resource

		wikiPageResourceLocalService.deletePageResource(
			page.getNodeId(), page.getTitle());

		// All versions

		wikiPagePersistence.removeByN_T(page.getNodeId(), page.getTitle());

		// All referrals

		wikiPagePersistence.removeByN_R(page.getNodeId(), page.getTitle());

		// Cache

		clearReferralsCache(page);
	}

	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		if (Validator.isNull(fileName)) {
			return;
		}

		WikiPage page = getPage(nodeId, title);

		long companyId = page.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long repositoryId = CompanyConstants.SYSTEM;

		try {
			dlService.deleteFile(companyId, portletId, repositoryId, fileName);
		}
		catch (NoSuchFileException nsfe) {
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

	public void deletePages(long nodeId)
		throws PortalException, SystemException {

		Iterator<WikiPage> itr = wikiPagePersistence.findByN_H(
			nodeId, true).iterator();

		while (itr.hasNext()) {
			WikiPage page = itr.next();

			deletePage(page);
		}
	}

	public List<WikiPage> getChildren(
			long nodeId, boolean head, String parentTitle)
		throws SystemException {

		return wikiPagePersistence.findByN_H_P(nodeId, head, parentTitle);
	}

	public List<WikiPage> getIncomingLinks(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> links = new UniqueList<WikiPage>();

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			if (isLinkedTo(page, title)) {
				links.add(page);
			}
		}

		List<WikiPage> referrals = wikiPagePersistence.findByN_R(nodeId, title);

		for (WikiPage referral : referrals) {
			for (WikiPage page : pages) {
				if (isLinkedTo(page, referral.getTitle())) {
					links.add(page);
				}
			}
		}

		Collections.sort(links);

		return links;
	}

	public List<WikiPage> getNoAssetPages() throws SystemException {
		return wikiPageFinder.findByNoAssets();
	}

	public List<WikiPage> getOrphans(long nodeId)
		throws PortalException, SystemException {

		List<Map<String, Boolean>> pageTitles =
			new ArrayList<Map<String, Boolean>>();

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			pageTitles.add(WikiCacheUtil.getOutgoingLinks(page));
		}

		Set<WikiPage> notOrphans = new HashSet<WikiPage>();

		for (WikiPage page : pages) {
			for (Map<String, Boolean> pageTitle : pageTitles) {
				if (pageTitle.get(page.getTitle()) != null) {
					notOrphans.add(page);

					break;
				}
			}
		}

		List<WikiPage> orphans = new ArrayList<WikiPage>();

		for (WikiPage page : pages) {
			if (!notOrphans.contains(page)) {
				orphans.add(page);
			}
		}

		Collections.sort(orphans);

		return orphans;
	}

	public List<WikiPage> getOutgoingLinks(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		List<WikiPage> pages = new UniqueList<WikiPage>();

		Map<String, Boolean> links = WikiCacheUtil.getOutgoingLinks(page);

		for (String curTitle : links.keySet()) {
			Boolean exists = links.get(curTitle);

			if (exists) {
				pages.add(getPage(nodeId, curTitle));
			}
			else {
				WikiPageImpl newPage = new WikiPageImpl();

				newPage.setNew(true);
				newPage.setNodeId(nodeId);
				newPage.setTitle(curTitle);

				pages.add(newPage);
			}
		}

		return pages;
	}

	public WikiPage getPage(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (pages.size() > 0) {
			return pages.get(0);
		}
		else {
			throw new NoSuchPageException();
		}
	}

	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPage page = null;

		if (version == 0) {
			page = getPage(nodeId, title);
		}
		else {
			page = wikiPagePersistence.findByN_T_V(nodeId, title, version);
		}

		return page;
	}

	public WikiPageDisplay getPageDisplay(
			long nodeId, String title, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		String formattedContent = WikiUtil.convert(
			page, viewPageURL, editPageURL, attachmentURLPrefix);

		return new WikiPageDisplayImpl(
			page.getUserId(), page.getNodeId(), page.getTitle(),
			page.getVersion(), page.getContent(), formattedContent,
			page.getFormat(), page.getHead(), page.getAttachmentsFiles());
	}

	public List<WikiPage> getPages(long nodeId, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByNodeId(
			nodeId, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(String format) throws SystemException {
		return wikiPagePersistence.findByFormat(format);
	}

	public List<WikiPage> getPages(
			long nodeId, String title, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_T(
			nodeId, title, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, String title, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return wikiPagePersistence.findByN_T(nodeId, title, start, end, obc);
	}

	public List<WikiPage> getPages(
			long nodeId, boolean head, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_H(
			nodeId, head, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, String title, boolean head, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_T_H(
			nodeId, title, head, start, end,
			new PageCreateDateComparator(false));
	}

	public int getPagesCount(long nodeId) throws SystemException {
		return wikiPagePersistence.countByNodeId(nodeId);
	}

	public int getPagesCount(long nodeId, String title)
		throws SystemException {

		return wikiPagePersistence.countByN_T(nodeId, title);
	}

	public int getPagesCount(long nodeId, boolean head)
		throws SystemException {

		return wikiPagePersistence.countByN_H(nodeId, head);
	}

	public int getPagesCount(long nodeId, String title, boolean head)
		throws SystemException {

		return wikiPagePersistence.countByN_T_H(nodeId, title, head);
	}

	public List<WikiPage> getRecentChanges(long nodeId, int start, int end)
		throws SystemException {

		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.findByCreateDate(
			nodeId, cal.getTime(), false, start, end);
	}

	public int getRecentChangesCount(long nodeId) throws SystemException {
		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.countByCreateDate(nodeId, cal.getTime(), false);
	}

	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		movePage(userId, nodeId, title, newTitle, true, prefs, themeDisplay);
	}

	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			boolean strict, PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		validateTitle(newTitle);

		// Check if the new title already exists

		if (isUsedTitle(nodeId, newTitle)) {
			WikiPage page = getPage(nodeId, newTitle);

			// Support moving back to a previously moved title

			if (((page.getVersion() == WikiPageImpl.DEFAULT_VERSION) &&
				 (page.getContent().equals(WikiPageImpl.MOVED))) ||
				!strict) {

				deletePage(nodeId, newTitle);
			}
			else {
				throw new DuplicatePageException(newTitle);
			}
		}

		// All versions

		List<WikiPage> pageVersions = wikiPagePersistence.findByN_T(
			nodeId, title);

		if (pageVersions.size() == 0) {
			return;
		}

		for (WikiPage page : pageVersions) {
			page.setTitle(newTitle);

			wikiPagePersistence.update(page, false);
		}

		// Children

		List<WikiPage> children = wikiPagePersistence.findByN_P(nodeId, title);

		for (WikiPage page : children) {
			page.setParentTitle(newTitle);

			wikiPagePersistence.update(page, false);
		}

		WikiPage page = pageVersions.get(pageVersions.size() - 1);

		// Page resource

		WikiPageResource wikiPageResource =
			wikiPageResourcePersistence.findByPrimaryKey(
				page.getResourcePrimKey());

		wikiPageResource.setTitle(newTitle);

		wikiPageResourcePersistence.update(wikiPageResource, false);

		// Create stub page at the old location

		String uuid = null;
		double version = WikiPageImpl.DEFAULT_VERSION;
		String format = page.getFormat();
		boolean head = true;
		String parentTitle = page.getParentTitle();
		String redirectTitle = page.getTitle();
		String content =
			StringPool.DOUBLE_OPEN_BRACKET + redirectTitle +
				StringPool.DOUBLE_CLOSE_BRACKET;
		String summary = WikiPageImpl.MOVED + " to " + title;
		String[] tagsEntries = null;

		addPage(
			uuid, userId, nodeId, title, version, content, summary, false,
			format, head, parentTitle, redirectTitle, tagsEntries, prefs,
			themeDisplay);

		// Move redirects to point to the page with the new title

		List<WikiPage> redirectedPages = wikiPagePersistence.findByN_R(
			nodeId, title);

		for (WikiPage redirectedPage : redirectedPages) {
			redirectedPage.setRedirectTitle(newTitle);

			wikiPagePersistence.update(redirectedPage, false);
		}

		// Tags

		updateTagsAsset(userId, page, tagsEntries);

		// Lucene

		try {
			Indexer.updatePage(
				page.getCompanyId(), page.getNode().getGroupId(), nodeId,
				newTitle, content, tagsEntries);
		}
		catch (SearchException se) {
			_log.error("Indexing " + newTitle, se);
		}
	}

	public WikiPage revertPage(
			long userId, long nodeId, String title, double version,
			PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		WikiPage oldPage = getPage(nodeId, title, version);

		return updatePage(
			userId, nodeId, title, 0, oldPage.getContent(),
			WikiPageImpl.REVERTED + " to " + version, false,
			oldPage.getFormat(), null, oldPage.getRedirectTitle(), null, prefs,
			themeDisplay);
	}

	public void subscribePage(long userId, long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.addSubscription(
			userId, WikiPage.class.getName(), page.getResourcePrimKey());
	}

	public void unsubscribePage(long userId, long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.deleteSubscription(
			userId, WikiPage.class.getName(), page.getResourcePrimKey());
	}

	public WikiPage updatePage(
			long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle, String[] tagsEntries,
			PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(nodeId, content, format);

		WikiPage page = null;

		try {
			page = getPage(nodeId, title);
		}
		catch (NoSuchPageException nspe) {
			return addPage(
				null, userId, nodeId, title, WikiPageImpl.DEFAULT_VERSION,
				content, summary, minorEdit, format, true, parentTitle,
				redirectTitle, tagsEntries, prefs, themeDisplay);
		}

		double oldVersion = page.getVersion();

		if ((version > 0) && (version != oldVersion)) {
			throw new PageVersionException();
		}

		long resourcePrimKey = page.getResourcePrimKey();

		page.setHead(false);
		page.setModifiedDate(now);

		wikiPagePersistence.update(page, false);

		double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

		long pageId = counterLocalService.increment();

		page = wikiPagePersistence.create(pageId);

		page.setResourcePrimKey(resourcePrimKey);
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setCreateDate(now);
		page.setModifiedDate(now);
		page.setNodeId(nodeId);
		page.setTitle(title);
		page.setVersion(newVersion);
		page.setContent(content);
		page.setSummary(summary);
		page.setFormat(format);
		page.setHead(true);

		if (Validator.isNotNull(parentTitle)) {
			page.setParentTitle(parentTitle);
		}

		if (Validator.isNotNull(redirectTitle)) {
			page.setRedirectTitle(redirectTitle);
		}

		wikiPagePersistence.update(page, false);

		// Node

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		node.setLastPostDate(now);

		wikiNodePersistence.update(node, false);

		// Social

		socialActivityLocalService.addActivity(
			userId, node.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), WikiActivityKeys.UPDATE_PAGE,
			StringPool.BLANK, 0);

		// Subscriptions

		if (!minorEdit && NotificationThreadLocal.isNotificationEnabled()) {
			notifySubscribers(node, page, prefs, themeDisplay, true);
		}

		// Tags

		updateTagsAsset(userId, page, tagsEntries);

		// Lucene

		try {
			Indexer.updatePage(
				node.getCompanyId(), node.getGroupId(), nodeId, title, content,
				tagsEntries);
		}
		catch (SearchException se) {
			_log.error("Indexing " + page.getPrimaryKey(), se);
		}

		// Cache

		clearPageCache(page);

		return page;
	}

	public void updateTagsAsset(
			long userId, WikiPage page, String[] tagsEntries)
		throws PortalException, SystemException {

		tagsAssetLocalService.updateAsset(
			userId, page.getNode().getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), tagsEntries, null, null, null, null,
			ContentTypes.TEXT_HTML, page.getTitle(), null, null, null, 0, 0,
			null, false);
	}

	public void validateTitle(String title) throws PortalException {
		if (Validator.isNotNull(PropsValues.WIKI_PAGE_TITLES_REGEXP)) {
			Pattern pattern = Pattern.compile(
				PropsValues.WIKI_PAGE_TITLES_REGEXP);

			Matcher matcher = pattern.matcher(title);

			if (!matcher.matches()) {
				throw new PageTitleException();
			}
		}
	}

	protected void clearPageCache(WikiPage page) {
		if (!WikiCacheThreadLocal.isClearCache()) {
			return;
		}

		WikiCacheUtil.clearCache(page.getNodeId(), page.getTitle());
	}

	protected void clearReferralsCache(WikiPage page)
		throws PortalException, SystemException {

		if (!WikiCacheThreadLocal.isClearCache()) {
			return;
		}

		List<WikiPage> links = getIncomingLinks(
			page.getNodeId(), page.getTitle());

		for (WikiPage curPage : links) {
			WikiCacheUtil.clearCache(curPage.getNodeId(), curPage.getTitle());
		}
	}

	protected boolean isLinkedTo(WikiPage page, String targetTitle)
		throws PortalException {

		Map<String, Boolean> links = WikiCacheUtil.getOutgoingLinks(page);

		Boolean link = links.get(targetTitle);

		if (link != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isUsedTitle(long nodeId, String title)
		throws SystemException {

		if (getPagesCount(nodeId, title, true) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void notifySubscribers(
			WikiNode node, WikiPage page, PortletPreferences prefs,
			ThemeDisplay themeDisplay, boolean update)
		throws PortalException, SystemException {

		if (prefs == null) {
			long ownerId = node.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.WIKI;
			String defaultPreferences = null;

			prefs = portletPreferencesLocalService.getPreferences(
				node.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		if (!update && WikiUtil.getEmailPageAddedEnabled(prefs)) {
		}
		else if (update && WikiUtil.getEmailPageUpdatedEnabled(prefs)) {
		}
		else {
			return;
		}

		Company company = companyPersistence.findByPrimaryKey(
			page.getCompanyId());

		Group group = groupPersistence.findByPrimaryKey(node.getGroupId());

		User user = userPersistence.findByPrimaryKey(page.getUserId());

		String pageURL = StringPool.BLANK;

		if (themeDisplay != null) {
			String portalURL = PortalUtil.getPortalURL(themeDisplay);
			String layoutURL = PortalUtil.getLayoutURL(themeDisplay);

			pageURL =
				portalURL + layoutURL + "/wiki/" + node.getNodeId() + "/" +
					page.getTitle();
		}

		String portletName = PortalUtil.getPortletTitle(
			PortletKeys.WIKI, user);

		String fromName = WikiUtil.getEmailFromName(prefs);
		String fromAddress = WikiUtil.getEmailFromAddress(prefs);

		String replyToAddress = fromAddress;
		String mailId = WikiUtil.getMailId(
			company.getMx(), page.getNodeId(), page.getPageId());

		fromName = StringUtil.replace(
			fromName,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				user.getEmailAddress(),
				user.getFullName(),
				portletName
			});

		fromAddress = StringUtil.replace(
			fromAddress,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				user.getEmailAddress(),
				user.getFullName(),
				portletName
			});

		String subjectPrefix = null;
		String body = null;
		String signature = null;

		if (update) {
			subjectPrefix = WikiUtil.getEmailPageUpdatedSubjectPrefix(prefs);
			body = WikiUtil.getEmailPageUpdatedBody(prefs);
			signature = WikiUtil.getEmailPageUpdatedSignature(prefs);
		}
		else {
			subjectPrefix = WikiUtil.getEmailPageAddedSubjectPrefix(prefs);
			body = WikiUtil.getEmailPageAddedBody(prefs);
			signature = WikiUtil.getEmailPageAddedSignature(prefs);
		}

		if (Validator.isNotNull(signature)) {
			body +=  "\n--\n" + signature;
		}

		subjectPrefix = StringUtil.replace(
			subjectPrefix,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$NODE_NAME$]",
				"[$PAGE_CONTENT$]",
				"[$PAGE_ID$]",
				"[$PAGE_TITLE$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				fromAddress,
				fromName,
				node.getName(),
				page.getContent(),
				String.valueOf(page.getPageId()),
				page.getTitle(),
				user.getEmailAddress(),
				user.getFullName(),
				company.getVirtualHost(),
				portletName
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$COMMUNITY_NAME$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$NODE_NAME$]",
				"[$PAGE_CONTENT$]",
				"[$PAGE_ID$]",
				"[$PAGE_TITLE$]",
				"[$PAGE_URL$]",
				"[$PAGE_USER_ADDRESS$]",
				"[$PAGE_USER_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]"
			},
			new String[] {
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				group.getName(),
				fromAddress,
				fromName,
				node.getName(),
				page.getContent(),
				String.valueOf(page.getPageId()),
				page.getTitle(),
				pageURL,
				user.getEmailAddress(),
				user.getFullName(),
				company.getVirtualHost(),
				portletName
			});

		String subject = page.getTitle();

		if (subject.indexOf(subjectPrefix) == -1) {
			subject = subjectPrefix + subject;
		}

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("companyId", node.getCompanyId());
		jsonObj.put("userId", node.getUserId());
		jsonObj.put("nodeId", node.getNodeId());
		jsonObj.put("pageResourcePrimKey", page.getResourcePrimKey());
		jsonObj.put("fromName", fromName);
		jsonObj.put("fromAddress", fromAddress);
		jsonObj.put("subject", subject);
		jsonObj.put("body", body);
		jsonObj.put("replyToAddress", replyToAddress);
		jsonObj.put("mailId", mailId);

		MessageBusUtil.sendMessage(DestinationNames.WIKI, jsonObj.toString());
	}

	protected void validate(long nodeId, String content, String format)
		throws PortalException {

		if (!WikiUtil.validate(nodeId, content, format)) {
			throw new PageContentException();
		}
	}

	protected void validate(
			String title, long nodeId, String content, String format)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new PageTitleException();
		}

		validateTitle(title);

		validate(nodeId, content, format);
	}

	private static Log _log = LogFactory.getLog(WikiPageLocalServiceImpl.class);

}