/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryFinder;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;
import com.liferay.util.Validator;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BookmarksEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksEntryLocalServiceImpl
	implements BookmarksEntryLocalService {

	public BookmarksEntry addEntry(
			String userId, String folderId, String name, String url,
			String comments, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addEntry(
			userId, folderId, name, url, comments,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public BookmarksEntry addEntry(
			String userId, String folderId, String name, String url,
			String comments, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addEntry(
			userId, folderId, name, url, comments, null, null,
			communityPermissions, guestPermissions);
	}

	public BookmarksEntry addEntry(
			String userId, String folderId, String name, String url,
			String comments, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Entry

		User user = UserUtil.findByPrimaryKey(userId);
		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		if (Validator.isNull(name)) {
			name = url;
		}

		Date now = new Date();

		validate(url);

		String entryId = String.valueOf(CounterLocalServiceUtil.increment(
			BookmarksEntry.class.getName()));

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

		return entry;
	}

	public void addEntryResources(
			String folderId, String entryId, boolean addCommunityPermissions,
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
			BookmarksEntry.class.getName(), entry.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			String folderId, String entryId, String[] communityPermissions,
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
			BookmarksEntry.class.getName(), entry.getPrimaryKey().toString(),
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(String folderId)
		throws PortalException, SystemException {

		Iterator itr = BookmarksEntryUtil.findByFolderId(folderId).iterator();

		while (itr.hasNext()) {
			BookmarksEntry entry = (BookmarksEntry)itr.next();

			deleteEntry(entry);
		}
	}

	public void deleteEntry(String entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryUtil.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(BookmarksEntry entry)
		throws PortalException, SystemException {

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			entry.getCompanyId(), BookmarksEntry.class.getName(),
			ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
			entry.getPrimaryKey().toString());

		// Entry

		BookmarksEntryUtil.remove(entry.getEntryId());
	}

	public List getEntries(String folderId, int begin, int end)
		throws SystemException {

		return BookmarksEntryUtil.findByFolderId(folderId, begin, end);
	}

	public int getEntriesCount(String folderId) throws SystemException {
		return BookmarksEntryUtil.countByFolderId(folderId);
	}

	public BookmarksEntry getEntry(String entryId)
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

	public List getGroupEntries(
			long groupId, String userId, int begin, int end)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return BookmarksEntryFinder.findByGroupId(groupId, begin, end);
		}
		else {
			return BookmarksEntryFinder.findByG_U(groupId, userId, begin, end);
		}
	}

	public int getGroupEntriesCount(long groupId) throws SystemException {
		return BookmarksEntryFinder.countByGroupId(groupId);
	}

	public int getGroupEntriesCount(long groupId, String userId)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return BookmarksEntryFinder.countByGroupId(groupId);
		}
		else {
			return BookmarksEntryFinder.countByG_U(groupId, userId);
		}
	}

	public BookmarksEntry openEntry(String entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryUtil.findByPrimaryKey(entryId);

		entry.setVisits(entry.getVisits() + 1);

		BookmarksEntryUtil.update(entry);

		return entry;
	}

	public BookmarksEntry updateEntry(
			String companyId, String entryId, String folderId, String name,
			String url, String comments)
		throws PortalException, SystemException {

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

		return entry;
	}

	protected BookmarksFolder getFolder(BookmarksEntry entry, String folderId)
		throws PortalException, SystemException {

		if (!entry.getFolderId().equals(folderId)) {
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