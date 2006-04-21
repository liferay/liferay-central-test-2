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

package com.liferay.portlet.bookmarks.service.impl;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.FolderNameException;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;
import com.liferay.portlet.bookmarks.service.spring.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.spring.BookmarksFolderLocalService;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BookmarksFolderLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksFolderLocalServiceImpl
	implements BookmarksFolderLocalService {

	public BookmarksFolder addFolder(
			String userId, String plid, String parentFolderId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Folder

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		parentFolderId = getParentFolderId(user.getCompanyId(), parentFolderId);
		Date now = new Date();

		validate(name);

		String folderId = Long.toString(CounterServiceUtil.increment(
			BookmarksFolder.class.getName()));

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

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);

		return folder;
	}

	public void addFolderResources(
			String folderId, boolean addCommunityPermissions,
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
			BookmarksFolder.class.getName(), folder.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void deleteFolder(String folderId)
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
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			folder.getPrimaryKey().toString());

		// Folder

		BookmarksFolderUtil.remove(folder.getFolderId());
	}

	public void deleteFolders(String groupId)
		throws PortalException, SystemException {

		Iterator itr = BookmarksFolderUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			BookmarksFolder folder = (BookmarksFolder)itr.next();

			deleteFolder(folder);
		}
	}

	public BookmarksFolder getFolder(String folderId)
		throws PortalException, SystemException {

		return BookmarksFolderUtil.findByPrimaryKey(folderId);
	}

	public List getFolders(
			String groupId, String parentFolderId, int begin, int end)
		throws SystemException {

		return BookmarksFolderUtil.findByG_P(
			groupId, parentFolderId, begin, end);
	}

	public int getFoldersCount(String groupId, String parentFolderId)
		throws SystemException {

		return BookmarksFolderUtil.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List folderIds, String groupId, String folderId)
		throws SystemException {

		Iterator itr = BookmarksFolderUtil.findByG_P(
			groupId, folderId).iterator();

		while (itr.hasNext()) {
			BookmarksFolder folder = (BookmarksFolder)itr.next();

			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public BookmarksFolder updateFolder(
			String companyId, String folderId, String parentFolderId,
			String name, String description)
		throws PortalException, SystemException {

		parentFolderId = getParentFolderId(companyId, parentFolderId);

		validate(name);

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);

		BookmarksFolderUtil.update(folder);

		return folder;
	}

	protected String getParentFolderId(String companyId, String parentFolderId)
		throws PortalException, SystemException {

		if (!parentFolderId.equals(BookmarksFolder.DEFAULT_PARENT_FOLDER_ID)) {

			// Ensure parent folder exists and belongs to the proper company

			try {
				BookmarksFolder parentFolder =
					BookmarksFolderUtil.findByPrimaryKey(parentFolderId);

				if (!companyId.equals(parentFolder.getCompanyId())) {
					parentFolderId = BookmarksFolder.DEFAULT_PARENT_FOLDER_ID;
				}
			}
			catch (NoSuchFolderException nsfe) {
				parentFolderId = BookmarksFolder.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}
	}

}