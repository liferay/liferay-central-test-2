/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.base.BookmarksEntryLocalServiceBaseImpl;
import com.liferay.portlet.bookmarks.util.BookmarksIndexer;
import com.liferay.portlet.bookmarks.util.comparator.EntryModifiedDateComparator;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BookmarksEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class BookmarksEntryLocalServiceImpl
	extends BookmarksEntryLocalServiceBaseImpl {

	public BookmarksEntry addEntry(
			String uuid, long userId, long groupId, long folderId, String name,
			String url, String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(name)) {
			name = url;
		}

		Date now = new Date();

		validate(url);

		long entryId = counterLocalService.increment();

		BookmarksEntry entry = bookmarksEntryPersistence.create(entryId);

		entry.setUuid(uuid);
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setFolderId(folderId);
		entry.setName(name);
		entry.setUrl(url);
		entry.setComments(comments);
		entry.setExpandoBridgeAttributes(serviceContext);

		bookmarksEntryPersistence.update(entry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addEntryResources(
				entry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEntryResources(
				entry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Indexer

		reIndex(entry);

		return entry;
	}

	public void addEntryResources(
			BookmarksEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BookmarksEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			BookmarksEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BookmarksEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public void addEntryResources(
			long entryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			long entryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		Iterator<BookmarksEntry> itr = bookmarksEntryPersistence.findByG_F(
			groupId, folderId).iterator();

		while (itr.hasNext()) {
			BookmarksEntry entry = itr.next();

			deleteEntry(entry);
		}
	}

	public void deleteEntry(BookmarksEntry entry)
		throws PortalException, SystemException {

		// Entry

		bookmarksEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), BookmarksEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Asset

		assetEntryLocalService.deleteEntry(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Expando

		expandoValueLocalService.deleteValues(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Indexer

		try {
			BookmarksIndexer.deleteEntry(
				entry.getCompanyId(), entry.getEntryId());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + entry.getEntryId(), se);
		}
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public List<BookmarksEntry> getEntries(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return bookmarksEntryPersistence.findByG_F(
			groupId, folderId, start, end);
	}

	public List<BookmarksEntry> getEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return bookmarksEntryPersistence.findByG_F(
			groupId, folderId, start, end, orderByComparator);
	}

	public int getEntriesCount(
			long groupId, long folderId)
		throws SystemException {

		return bookmarksEntryPersistence.countByG_F(groupId, folderId);
	}

	public BookmarksEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return bookmarksEntryPersistence.findByPrimaryKey(entryId);
	}

	public int getFoldersEntriesCount(long groupId, List<Long> folderIds)
		throws SystemException {

		return bookmarksEntryFinder.countByG_F(groupId, folderIds);
	}

	public List<BookmarksEntry> getGroupEntries(
			long groupId, int start, int end)
		throws SystemException {

		return bookmarksEntryPersistence.findByGroupId(
			groupId, start, end, new EntryModifiedDateComparator());
	}

	public List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		OrderByComparator orderByComparator = new EntryModifiedDateComparator();

		if (userId <= 0) {
			return bookmarksEntryPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}
		else {
			return bookmarksEntryPersistence.findByG_U(
				groupId, userId, start, end, orderByComparator);
		}
	}

	public int getGroupEntriesCount(long groupId) throws SystemException {
		return bookmarksEntryPersistence.countByGroupId(groupId);
	}

	public int getGroupEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return bookmarksEntryPersistence.countByGroupId(groupId);
		}
		else {
			return bookmarksEntryPersistence.countByG_U(groupId, userId);
		}
	}

	public List<BookmarksEntry> getNoAssetEntries() throws SystemException {
		return bookmarksEntryFinder.findByNoAssets();
	}

	public BookmarksEntry openEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		entry.setVisits(entry.getVisits() + 1);

		bookmarksEntryPersistence.update(entry, false);

		assetEntryLocalService.incrementViewCounter(
			BookmarksEntry.class.getName(), entryId);

		return entry;
	}

	public void reIndex(BookmarksEntry entry) throws SystemException {
		long companyId = entry.getCompanyId();
		long groupId = entry.getGroupId();
		long folderId = entry.getFolderId();
		long entryId = entry.getEntryId();
		String name = entry.getName();
		String url = entry.getUrl();
		String comments = entry.getComments();
		Date modifiedDate = entry.getModifiedDate();

		String[] assetTagNames = assetTagLocalService.getTagNames(
			BookmarksEntry.class.getName(), entryId);

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		try {
			BookmarksIndexer.updateEntry(
				companyId, groupId, folderId, entryId, name, url, comments,
				modifiedDate, assetTagNames, expandoBridge);
		}
		catch (SearchException se) {
			_log.error("Reindexing " + entryId, se);
		}
	}

	public void reIndex(long entryId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		BookmarksEntry entry = bookmarksEntryPersistence.fetchByPrimaryKey(
			entryId);

		if (entry == null) {
			return;
		}

		reIndex(entry);
	}

	public void updateAsset(
			long userId, BookmarksEntry entry, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		assetEntryLocalService.updateEntry(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(),
			entry.getEntryId(), assetCategoryIds, assetTagNames, true, null,
			null, null, null, ContentTypes.TEXT_PLAIN, entry.getName(),
			entry.getComments(), null, entry.getUrl(), 0, 0, null, false);
	}

	public BookmarksEntry updateEntry(
			long userId, long entryId, long groupId, long folderId, String name,
			String url, String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		if (Validator.isNull(name)) {
			name = url;
		}

		validate(url);

		entry.setModifiedDate(new Date());
		entry.setFolderId(folderId);
		entry.setName(name);
		entry.setUrl(url);
		entry.setComments(comments);
		entry.setExpandoBridgeAttributes(serviceContext);

		bookmarksEntryPersistence.update(entry, false);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Indexer

		reIndex(entry);

		return entry;
	}

	protected long getFolder(BookmarksEntry entry, long folderId)
		throws SystemException {

		if ((entry.getFolderId() != folderId) &&
			(folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			BookmarksFolder newFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(folderId);

			if ((newFolder == null) ||
				(entry.getGroupId() != newFolder.getGroupId())) {

				folderId = entry.getFolderId();
			}
		}

		return folderId;
	}

	protected void validate(String url) throws PortalException {
		if (Validator.isNull(url)) {
			throw new EntryURLException();
		}
		else {
			try {
				new URL(url);
			}
			catch (MalformedURLException murle) {
				throw new EntryURLException();
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(BookmarksEntryLocalServiceImpl.class);

}