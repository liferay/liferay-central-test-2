/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.base.BookmarksEntryLocalServiceBaseImpl;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryFinder;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;

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
 *
 */
public class BookmarksEntryLocalServiceImpl
	extends BookmarksEntryLocalServiceBaseImpl {

	public BookmarksEntry addEntry(
			long userId, long folderId, String name, String url,
			String comments, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addEntry(
			userId, folderId, name, url, comments, tagsEntries,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public BookmarksEntry addEntry(
			long userId, long folderId, String name, String url,
			String comments, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addEntry(
			userId, folderId, name, url, comments, tagsEntries, null, null,
			communityPermissions, guestPermissions);
	}

	public BookmarksEntry addEntry(
			long userId, long folderId, String name, String url,
			String comments, String[] tagsEntries,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Entry

		User user = UserUtil.findByPrimaryKey(userId);
		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		if (Validator.isNull(name)) {
			name = url;
		}

		Date now = new Date();

		validate(url);

		long entryId = CounterLocalServiceUtil.increment();

		BookmarksEntry entry = BookmarksEntryUtil.create(entryId);

		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setFolderId(folderId);
		entry.setName(name);
		entry.setUrl(url);
		entry.setComments(comments);

		BookmarksEntryUtil.update(entry);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addEntryResources(
				folder, entry, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addEntryResources(
				folder, entry, communityPermissions, guestPermissions);
		}

		// Tags

		updateTagsAsset(entry, tagsEntries);

		return entry;
	}

	public void addEntryResources(
			long folderId, long entryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);
		BookmarksEntry entry = BookmarksEntryUtil.findByPrimaryKey(entryId);

		addEntryResources(
			folder, entry, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			BookmarksFolder folder, BookmarksEntry entry,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			entry.getCompanyId(), folder.getGroupId(), entry.getUserId(),
			BookmarksEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			long folderId, long entryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);
		BookmarksEntry entry = BookmarksEntryUtil.findByPrimaryKey(entryId);

		addEntryResources(
			folder, entry, communityPermissions, guestPermissions);
	}

	public void addEntryResources(
			BookmarksFolder folder, BookmarksEntry entry,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			entry.getCompanyId(), folder.getGroupId(), entry.getUserId(),
			BookmarksEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long folderId)
		throws PortalException, SystemException {

		Iterator itr = BookmarksEntryUtil.findByFolderId(folderId).iterator();

		while (itr.hasNext()) {
			BookmarksEntry entry = (BookmarksEntry)itr.next();

			deleteEntry(entry);
		}
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryUtil.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(BookmarksEntry entry)
		throws PortalException, SystemException {

		// Tags

		TagsAssetLocalServiceUtil.deleteAsset(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			entry.getCompanyId(), BookmarksEntry.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry

		BookmarksEntryUtil.remove(entry.getEntryId());
	}

	public List getEntries(long folderId, int begin, int end)
		throws SystemException {

		return BookmarksEntryUtil.findByFolderId(folderId, begin, end);
	}

	public List getEntries(
			long folderId, int begin, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return BookmarksEntryUtil.findByFolderId(
			folderId, begin, end, orderByComparator);
	}

	public int getEntriesCount(long folderId) throws SystemException {
		return BookmarksEntryUtil.countByFolderId(folderId);
	}

	public BookmarksEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return BookmarksEntryUtil.findByPrimaryKey(entryId);
	}

	public int getFoldersEntriesCount(List folderIds)
		throws SystemException {

		return BookmarksEntryFinder.countByFolderIds(folderIds);
	}

	public List getGroupEntries(long groupId, int begin, int end)
		throws SystemException {

		return BookmarksEntryFinder.findByGroupId(groupId, begin, end);
	}

	public List getGroupEntries(long groupId, long userId, int begin, int end)
		throws SystemException {

		if (userId <= 0) {
			return BookmarksEntryFinder.findByGroupId(groupId, begin, end);
		}
		else {
			return BookmarksEntryFinder.findByG_U(groupId, userId, begin, end);
		}
	}

	public int getGroupEntriesCount(long groupId) throws SystemException {
		return BookmarksEntryFinder.countByGroupId(groupId);
	}

	public int getGroupEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return BookmarksEntryFinder.countByGroupId(groupId);
		}
		else {
			return BookmarksEntryFinder.countByG_U(groupId, userId);
		}
	}

	public BookmarksEntry openEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryUtil.findByPrimaryKey(entryId);

		entry.setVisits(entry.getVisits() + 1);

		BookmarksEntryUtil.update(entry);

		return entry;
	}

	public BookmarksEntry updateEntry(
			long entryId, long folderId, String name, String url,
			String comments, String[] tagsEntries)
		throws PortalException, SystemException {

		// Entry

		BookmarksEntry entry = BookmarksEntryUtil.findByPrimaryKey(entryId);

		BookmarksFolder folder = getFolder(entry, folderId);

		if (Validator.isNull(name)) {
			name = url;
		}

		validate(url);

		entry.setModifiedDate(new Date());
		entry.setFolderId(folder.getFolderId());
		entry.setName(name);
		entry.setUrl(url);
		entry.setComments(comments);

		BookmarksEntryUtil.update(entry);

		// Tags

		updateTagsAsset(entry, tagsEntries);

		return entry;
	}

	public void updateTagsAsset(BookmarksEntry entry, String[] tagsEntries)
		throws PortalException, SystemException {

		TagsAssetLocalServiceUtil.updateAsset(
			entry.getUserId(), BookmarksEntry.class.getName(),
			entry.getEntryId(), tagsEntries, null, null, null, null,
			ContentTypes.TEXT_PLAIN, entry.getName(), entry.getComments(),
			entry.getComments(), entry.getUrl(), 0, 0);
	}

	protected BookmarksFolder getFolder(BookmarksEntry entry, long folderId)
		throws PortalException, SystemException {

		if (entry.getFolderId() != folderId) {
			BookmarksFolder oldFolder = BookmarksFolderUtil.findByPrimaryKey(
				entry.getFolderId());

			BookmarksFolder newFolder = BookmarksFolderUtil.fetchByPrimaryKey(
				folderId);

			if ((newFolder == null) ||
				(oldFolder.getGroupId() != newFolder.getGroupId())) {

				folderId = entry.getFolderId();
			}
		}

		return BookmarksFolderUtil.findByPrimaryKey(folderId);
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

}