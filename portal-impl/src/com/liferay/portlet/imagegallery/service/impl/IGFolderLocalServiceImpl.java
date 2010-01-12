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

package com.liferay.portlet.imagegallery.service.impl;

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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.imagegallery.DuplicateFolderNameException;
import com.liferay.portlet.imagegallery.FolderNameException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.base.IGFolderLocalServiceBaseImpl;
import com.liferay.portlet.imagegallery.service.permission.IGFolderPermission;
import com.liferay.portlet.imagegallery.util.Indexer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="IGFolderLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class IGFolderLocalServiceImpl extends IGFolderLocalServiceBaseImpl {

	public IGFolder addFolder(
			String uuid, long userId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		IGFolder folder = igFolderPersistence.create(folderId);

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

		igFolderPersistence.update(folder, false);

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
			IGFolder folder, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getFolderId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			IGFolder folder, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			folder.getCompanyId(), folder.getGroupId(), folder.getUserId(),
			IGFolder.class.getName(), folder.getFolderId(),
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(
			long folderId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(
			folder, addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
			long folderId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(folder, communityPermissions, guestPermissions);
	}

	public void deleteFolder(IGFolder folder)
		throws PortalException, SystemException {

		// Folder

		igFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder.getCompanyId(), IGFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, folder.getFolderId());

		// Folders

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (IGFolder curFolder : folders) {
			deleteFolder(curFolder);
		}

		// Images

		igImageLocalService.deleteImages(
			folder.getGroupId(), folder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			IGFolder.class.getName(), folder.getFolderId());
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(folder);
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			groupId, IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (IGFolder folder : folders) {
			deleteFolder(folder);
		}
	}

	public IGFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return igFolderPersistence.findByPrimaryKey(folderId);
	}

	public IGFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return igFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	public List<IGFolder> getFolders(long groupId) throws SystemException {
		return igFolderPersistence.findByGroupId(groupId);
	}

	public List<IGFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return igFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<IGFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return igFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return igFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			groupId, folderId);

		for (IGFolder folder : folders) {
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

							IGFolderPermission.check(
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
				searchQuery.addTerm(Field.DESCRIPTION, keywords);
				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords, true);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, IGImage.class.getName(), fullQuery,
				start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public IGFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		IGFolder folder = igFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		// Merge folders

		if (mergeWithParentFolder && (folderId != parentFolderId)) {
			mergeFolders(folder, parentFolderId);

			return folder;
		}

		// Folder

		validate(
			folder.getFolderId(), folder.getGroupId(), parentFolderId, name);

		folder.setModifiedDate(new Date());
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		igFolderPersistence.update(folder, false);

		return folder;
	}

	protected long getParentFolderId(IGFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			IGFolder parentFolder = igFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

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

		if (parentFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			IGFolder parentFolder = igFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId = IGFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void mergeFolders(IGFolder fromFolder, long toFolderId)
		throws PortalException, SystemException {

		List<IGFolder> folders = igFolderPersistence.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (IGFolder folder : folders) {
			mergeFolders(folder, toFolderId);
		}

		List<IGImage> images = igImagePersistence.findByG_F(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (IGImage image : images) {
			image.setFolderId(toFolderId);

			igImagePersistence.update(image, false);

			igImageLocalService.reIndex(image);
		}

		deleteFolder(fromFolder);
	}

	protected void reIndexFolders(long companyId) throws SystemException {
		int folderCount = igFolderPersistence.countByCompanyId(companyId);

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

		List<IGFolder> folders = igFolderPersistence.findByCompanyId(
			companyId, folderStart, folderEnd);

		for (IGFolder folder : folders) {
			long groupId = folder.getGroupId();
			long folderId = folder.getFolderId();

			int entryCount = igImagePersistence.countByG_F(groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reIndexImages(groupId, folderId, entryStart, entryEnd);
			}
		}
	}

	protected void reIndexImages(
			long groupId, long folderId, int entryStart, int entryEnd)
		throws SystemException {

		List<IGImage> images = igImagePersistence.findByG_F(
			groupId, folderId, entryStart, entryEnd);

		for (IGImage image : images) {
			igImageLocalService.reIndex(image);
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
			long folderId = IGFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			int entryCount = igImagePersistence.countByG_F(
				groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int j = 0; j <= entryPages; j++) {
				int entryStart = (j * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reIndexImages(groupId, folderId, entryStart, entryEnd);
			}
		}
	}

	protected void validate(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}

		IGFolder folder = igFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((folder != null) && (folder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException();
		}

		if (name.indexOf(StringPool.PERIOD) != -1) {
			String nameWithExtension = name;

			name = FileUtil.stripExtension(nameWithExtension);

			List<IGImage> images = igImagePersistence.findByG_F_N(
				groupId, parentFolderId, name);

			for (IGImage image : images) {
				if (nameWithExtension.equals(image.getNameWithExtension())) {
					throw new DuplicateFolderNameException();
				}
			}
		}
	}

	protected void validate(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validate(folderId, groupId, parentFolderId, name);
	}

}