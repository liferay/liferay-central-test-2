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
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.FolderNameException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.base.BookmarksFolderLocalServiceBaseImpl;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.bookmarks.util.Indexer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="BookmarksFolderLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class BookmarksFolderLocalServiceImpl
	extends BookmarksFolderLocalServiceBaseImpl {

	public BookmarksFolder addFolder(
			String uuid, long userId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(name);

		long folderId = counterLocalService.increment();

		BookmarksFolder folder = bookmarksFolderPersistence.create(folderId);

		folder.setUuid(uuid);
		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setCreateDate(now);
		folder.setModifiedDate(now);
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		bookmarksFolderPersistence.update(folder, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFolderResources(
				folder, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFolderResources(
				folder, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return folder;
	}

	public void addFolderResources(
			BookmarksFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			BookmarksFolder.class.getName(), folder.getFolderId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			BookmarksFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			BookmarksFolder.class.getName(), folder.getFolderId(),
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void deleteFolder(BookmarksFolder folder)
		throws PortalException, SystemException {

		// Folder

		bookmarksFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder.getCompanyId(), BookmarksFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, folder.getFolderId());

		// Entries

		bookmarksEntryLocalService.deleteEntries(
			folder.getGroupId(), folder.getFolderId());

		// Folders

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (BookmarksFolder curFolder : folders) {
			deleteFolder(curFolder);
		}

		// Expando

		expandoValueLocalService.deleteValues(
			BookmarksFolder.class.getName(), folder.getFolderId());
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		deleteFolder(folder);
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			groupId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (BookmarksFolder folder : folders) {
			deleteFolder(folder);
		}
	}

	public BookmarksFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return bookmarksFolderPersistence.findByPrimaryKey(folderId);
	}

	public List<BookmarksFolder> getFolders(long groupId)
		throws SystemException {

		return bookmarksFolderPersistence.findByGroupId(groupId);
	}

	public List<BookmarksFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return bookmarksFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<BookmarksFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return bookmarksFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return bookmarksFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			groupId, folderId);

		for (BookmarksFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			reIndexFolders(companyId);

			reIndexRoot(companyId);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public Hits search(
			long companyId, long groupId, long userId, long[] folderIds,
			String keywords, int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				if (group.isLayout()) {
					contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);

					groupId = group.getParentGroupId();
				}

				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if ((folderIds != null) && (folderIds.length > 0)) {
				BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create();

				for (long folderId : folderIds) {
					if (userId > 0) {
						try {
							PermissionChecker permissionChecker =
								PermissionThreadLocal.getPermissionChecker();

							BookmarksFolderPermission.check(
								permissionChecker, groupId, folderId,
								ActionKeys.VIEW);
						}
						catch (Exception e) {
							continue;
						}
					}

					TermQuery termQuery = TermQueryFactoryUtil.create(
						"folderId", folderId);

					folderIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
				}

				contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords, true);
				searchQuery.addTerm(Field.URL, keywords);
				searchQuery.addTerm(Field.COMMENTS, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, BookmarksEntry.class.getName(),
				fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public BookmarksFolder updateFolder(
			long folderId, long parentFolderId, String name,
			String description, boolean mergeWithParentFolder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		// Merge folders

		if (mergeWithParentFolder && (folderId != parentFolderId)) {
			mergeFolders(folder, parentFolderId);

			return folder;
		}

		// Folder

		validate(name);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		bookmarksFolderPersistence.update(folder, false);

		return folder;
	}

	protected long getParentFolderId(
			BookmarksFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			BookmarksFolder parentFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(folder.getGroupId() != parentFolder.getGroupId())) {

				return folder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, folder.getGroupId(), folder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return folder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			BookmarksFolder parentFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId =
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void mergeFolders(BookmarksFolder fromFolder, long toFolderId)
		throws PortalException, SystemException {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
				fromFolder.getGroupId(), fromFolder.getFolderId());

		for (BookmarksFolder folder : folders) {
			mergeFolders(folder, toFolderId);
		}

		List<BookmarksEntry> entries = bookmarksEntryPersistence.findByG_F(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (BookmarksEntry entry : entries) {
			entry.setFolderId(toFolderId);

			bookmarksEntryPersistence.update(entry, false);

			bookmarksEntryLocalService.reIndex(entry);
		}

		deleteFolder(fromFolder);
	}

	protected void reIndexEntries(
			long groupId, long folderId, int entryStart, int entryEnd)
		throws SystemException {

		List<BookmarksEntry> entries = bookmarksEntryPersistence.findByG_F(
			groupId, folderId, entryStart, entryEnd);

		for (BookmarksEntry entry : entries) {
			bookmarksEntryLocalService.reIndex(entry);
		}
	}

	protected void reIndexFolders(long companyId) throws SystemException {
		int folderCount = bookmarksFolderPersistence.countByCompanyId(
			companyId);

		int folderPages = folderCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= folderPages; i++) {
			int folderStart = (i * Indexer.DEFAULT_INTERVAL);
			int folderEnd = folderStart + Indexer.DEFAULT_INTERVAL;

			reIndexFolders(companyId, folderStart, folderEnd);
		}
	}

	protected void reIndexFolders(
			long companyId, int folderStart, int folderEnd)
		throws SystemException {

		List<BookmarksFolder> folders =
			bookmarksFolderPersistence.findByCompanyId(
				companyId, folderStart, folderEnd);

		for (BookmarksFolder folder : folders) {
			long groupId = folder.getGroupId();
			long folderId = folder.getFolderId();

			int entryCount = bookmarksEntryPersistence.countByG_F(
				groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reIndexEntries(groupId, folderId, entryStart, entryEnd);
			}
		}
	}

	protected void reIndexRoot(long companyId) throws SystemException {
		int groupCount = groupPersistence.countByCompanyId(companyId);

		int groupPages = groupCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= groupPages; i++) {
			int groupStart = (i * Indexer.DEFAULT_INTERVAL);
			int groupEnd = groupStart + Indexer.DEFAULT_INTERVAL;

			reIndexRoot(companyId, groupStart, groupEnd);
		}
	}

	protected void reIndexRoot(long companyId, int groupStart, int groupEnd)
		throws SystemException {

		List<Group> groups = groupPersistence.findByCompanyId(
			companyId, groupStart, groupEnd);

		for (Group group : groups) {
			long groupId = group.getGroupId();
			long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			int entryCount = bookmarksEntryPersistence.countByG_F(
				groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reIndexEntries(groupId, folderId, entryStart, entryEnd);
			}
		}
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new FolderNameException();
		}
	}

}