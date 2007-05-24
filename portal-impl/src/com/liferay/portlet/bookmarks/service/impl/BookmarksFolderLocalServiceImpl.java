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
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.FolderNameException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.base.BookmarksFolderLocalServiceBaseImpl;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BookmarksFolderLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BookmarksFolderLocalServiceImpl
	extends BookmarksFolderLocalServiceBaseImpl {

	public BookmarksFolder addFolder(
			long userId, long plid, long parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			userId, plid, parentFolderId, name, description,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public BookmarksFolder addFolder(
			long userId, long plid, long parentFolderId, String name,
			String description, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addFolder(
			userId, plid, parentFolderId, name, description, null, null,
			communityPermissions, guestPermissions);
	}

	public BookmarksFolder addFolder(
			long userId, long plid, long parentFolderId, String name,
			String description, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Folder

		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(name);

		long folderId = CounterLocalServiceUtil.increment();

		BookmarksFolder folder = BookmarksFolderUtil.create(folderId);

		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setCreateDate(now);
		folder.setModifiedDate(now);
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		BookmarksFolderUtil.update(folder);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addFolderResources(
				folder, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addFolderResources(folder, communityPermissions, guestPermissions);
		}

		return folder;
	}

	public void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			BookmarksFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			BookmarksFolder.class.getName(), folder.getFolderId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			BookmarksFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			BookmarksFolder.class.getName(), folder.getFolderId(),
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public void deleteFolder(BookmarksFolder folder)
		throws PortalException, SystemException {

		// Folders

		Iterator itr = BookmarksFolderUtil.findByG_P(
			folder.getGroupId(), folder.getFolderId()).iterator();

		while (itr.hasNext()) {
			BookmarksFolder curFolder = (BookmarksFolder)itr.next();

			deleteFolder(curFolder);
		}

		// Entries

		BookmarksEntryLocalServiceUtil.deleteEntries(folder.getFolderId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			folder.getCompanyId(), BookmarksFolder.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, folder.getFolderId());

		// Folder

		BookmarksFolderUtil.remove(folder.getFolderId());
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		Iterator itr = BookmarksFolderUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			BookmarksFolder folder = (BookmarksFolder)itr.next();

			deleteFolder(folder);
		}
	}

	public BookmarksFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return BookmarksFolderUtil.findByPrimaryKey(folderId);
	}

	public List getFolders(
			long groupId, long parentFolderId, int begin, int end)
		throws SystemException {

		return BookmarksFolderUtil.findByG_P(
			groupId, parentFolderId, begin, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return BookmarksFolderUtil.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List folderIds, long groupId, long folderId)
		throws SystemException {

		Iterator itr = BookmarksFolderUtil.findByG_P(
			groupId, folderId).iterator();

		while (itr.hasNext()) {
			BookmarksFolder folder = (BookmarksFolder)itr.next();

			folderIds.add(new Long(folder.getFolderId()));

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public BookmarksFolder updateFolder(
			long folderId, long parentFolderId, String name,
			String description, boolean mergeWithParentFolder)
		throws PortalException, SystemException {

		// Folder

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		long oldFolderId = folder.getParentFolderId();
		parentFolderId = getParentFolderId(folder, parentFolderId);

		validate(name);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		BookmarksFolderUtil.update(folder);

		// Merge folders

		if (mergeWithParentFolder && (oldFolderId != parentFolderId) &&
			(parentFolderId != BookmarksFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {

			mergeFolders(folder, parentFolderId);
		}

		return folder;
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId != BookmarksFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
			BookmarksFolder parentFolder =
				BookmarksFolderUtil.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(groupId !=parentFolder.getGroupId())) {

				parentFolderId = BookmarksFolderImpl.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected long getParentFolderId(
			BookmarksFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == BookmarksFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			BookmarksFolder parentFolder =
				BookmarksFolderUtil.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(folder.getGroupId() != parentFolder.getGroupId())) {

				return folder.getParentFolderId();
			}

			List subfolderIds = new ArrayList();

			getSubfolderIds(
				subfolderIds, folder.getGroupId(), folder.getFolderId());

			if (subfolderIds.contains(new Long(parentFolderId))) {
				return folder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected void mergeFolders(BookmarksFolder fromFolder, long toFolderId)
		throws PortalException, SystemException {

		Iterator itr = BookmarksFolderUtil.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId()).iterator();

		while (itr.hasNext()) {
			BookmarksFolder folder = (BookmarksFolder)itr.next();

			mergeFolders(folder, toFolderId);
		}

		itr = BookmarksEntryUtil.findByFolderId(
			fromFolder.getFolderId()).iterator();

		while (itr.hasNext()) {

			// Entry

			BookmarksEntry entry = (BookmarksEntry)itr.next();

			entry.setFolderId(toFolderId);

			BookmarksEntryUtil.update(entry);
		}

		BookmarksFolderUtil.remove(fromFolder.getFolderId());
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}
	}

}