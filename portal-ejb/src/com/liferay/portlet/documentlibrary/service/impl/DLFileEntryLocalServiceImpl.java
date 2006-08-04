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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.documentlibrary.service.spring.DLLocalServiceUtil;
import com.liferay.documentlibrary.service.spring.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPK;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.spring.DLFileRankLocalServiceUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.MathUtil;
import com.liferay.util.Validator;

import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFileEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryLocalServiceImpl implements DLFileEntryLocalService {

	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description,	byte[] byteArray,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		// File entry

		User user = UserUtil.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);
		Date now = new Date();

		if (Validator.isNull(title)) {
			title = name;
		}

		try {
			DLServiceUtil.addFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				folder.getGroupId(), folderId, name, byteArray);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		DLFileEntry fileEntry = DLFileEntryUtil.create(pk);

		fileEntry.setCompanyId(user.getCompanyId());
		fileEntry.setUserId(user.getUserId());
		fileEntry.setUserName(user.getFullName());
		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setCreateDate(now);
		fileEntry.setModifiedDate(now);
		fileEntry.setTitle(title);
		fileEntry.setDescription(description);
		fileEntry.setVersion(DLFileEntry.DEFAULT_VERSION);
		fileEntry.setSize(byteArray.length);
		fileEntry.setReadCount(DLFileEntry.DEFAULT_READ_COUNT);

		DLFileEntryUtil.update(fileEntry);

		// Resources

		addFileEntryResources(
			folder, fileEntry, addCommunityPermissions, addGuestPermissions);

		// Folder

		folder.setLastPostDate(fileEntry.getModifiedDate());

		DLFolderUtil.update(folder);

		return fileEntry;
	}

	public void addFileEntryResources(
			String folderId, String name, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);
		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(
			new DLFileEntryPK(folderId, name));

		addFileEntryResources(
			folder, fileEntry, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
			DLFolder folder, DLFileEntry fileEntry,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			fileEntry.getCompanyId(), folder.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getPrimaryKey().toString(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void deleteFileEntries(String folderId)
		throws PortalException, SystemException {

		Iterator itr = DLFileEntryUtil.findByFolderId(folderId).iterator();

		while (itr.hasNext()) {
			DLFileEntry fileEntry = (DLFileEntry)itr.next();

			deleteFileEntry(fileEntry);
		}
	}

	public void deleteFileEntry(String folderId, String name)
		throws PortalException, SystemException {

		deleteFileEntry(folderId, name, -1);
	}

	public void deleteFileEntry(String folderId, String name, double version)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(
			new DLFileEntryPK(folderId, name));

		if (version > 0) {
			try {
				DLServiceUtil.deleteFile(
					fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					fileEntry.getFolderId(), fileEntry.getName(), version);
			}
			catch (RemoteException re) {
				throw new SystemException(re);
			}

			DLFileVersionUtil.remove(
				new DLFileVersionPK(folderId, name, version));
		}
		else {
			deleteFileEntry(fileEntry);
		}
	}

	public void deleteFileEntry(DLFileEntry fileEntry)
		throws PortalException, SystemException {

		// File

		try {
			DLServiceUtil.deleteFile(
				fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				fileEntry.getFolderId(), fileEntry.getName());
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// File ranks

		DLFileRankLocalServiceUtil.deleteFileRanks(
			fileEntry.getFolderId(), fileEntry.getName());

		// File versions

		Iterator itr = DLFileVersionUtil.findByF_N(
			fileEntry.getFolderId(), fileEntry.getName()).iterator();

		while (itr.hasNext()) {
			DLFileVersion fileVersion = (DLFileVersion)itr.next();

			DLFileVersionUtil.remove(fileVersion.getPrimaryKey());
		}

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			fileEntry.getCompanyId(), DLFileEntry.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			fileEntry.getPrimaryKey().toString());

		// File entry

		DLFileEntryUtil.remove(fileEntry.getPrimaryKey());
	}

	public InputStream getFileAsStream(
			String companyId, String userId, String folderId, String name)
		throws PortalException, SystemException {

		return getFileAsStream(companyId, userId, folderId, name, -1);
	}

	public InputStream getFileAsStream(
			String companyId, String userId, String folderId, String name,
			double version)
		throws PortalException, SystemException {

		if (Validator.isNotNull(userId)) {
			DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

			DLFileRankLocalServiceUtil.updateFileRank(
				folder.getGroupId(), companyId, userId, folderId, name);
		}

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(pk);

		fileEntry.setReadCount(fileEntry.getReadCount() + 1);

		DLFileEntryUtil.update(fileEntry);

		if (version > 0 && (fileEntry.getVersion() != version)) {
			return DLLocalServiceUtil.getFileAsStream(
				companyId, folderId, name, version);
		}
		else {
			return DLLocalServiceUtil.getFileAsStream(
				companyId, folderId, name);
		}
	}

	public DLFileEntry getFileEntry(String folderId, String name)
		throws PortalException, SystemException {

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(pk);

		return fileEntry;
	}

	public List getFileEntries(String folderId) throws SystemException {
		return DLFileEntryUtil.findByFolderId(folderId);
	}

	public List getFileEntries(String folderId, int begin, int end)
		throws SystemException {

		return DLFileEntryUtil.findByFolderId(folderId, begin, end);
	}

	public int getFileEntriesCount(String folderId) throws SystemException {
		return DLFileEntryUtil.countByFolderId(folderId);
	}

	public int getFoldersFileEntriesCount(List folderIds)
		throws SystemException {

		return DLFileEntryFinder.countByFolderIds(folderIds);
	}

	public DLFileEntry updateFileEntry(
			String folderId, String name, String title, String description)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			title = name;
		}

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(pk);

		fileEntry.setTitle(title);
		fileEntry.setDescription(description);

		DLFileEntryUtil.update(fileEntry);

		return fileEntry;
	}

	public DLFileEntry updateFileEntry(
			String userId, String folderId, String name, String sourceFileName,
			byte[] byteArray)
		throws PortalException, SystemException {

		// File entry

		User user = UserUtil.findByPrimaryKey(userId);
		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		DLFileEntryPK pk = new DLFileEntryPK(folderId, name);

		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(pk);

		double oldVersion = fileEntry.getVersion();
		double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

		// File

		try {
			DLServiceUtil.updateFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				folder.getGroupId(), folderId, name, newVersion, sourceFileName,
				byteArray);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// File version

		DLFileVersion fileVersion = DLFileVersionUtil.create(
			new DLFileVersionPK(folderId, name, oldVersion));

		String versionUserId = GetterUtil.getString(
			fileEntry.getVersionUserId(), fileEntry.getUserId());

		String versionUserName = GetterUtil.getString(
			fileEntry.getVersionUserName(), fileEntry.getUserName());

		fileVersion.setCompanyId(fileEntry.getCompanyId());
		fileVersion.setUserId(versionUserId);
		fileVersion.setUserName(versionUserName);
		fileVersion.setCreateDate(fileEntry.getModifiedDate());
		fileVersion.setSize(fileEntry.getSize());

		DLFileVersionUtil.update(fileVersion);

		// File entry

		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setModifiedDate(new Date());
		fileEntry.setVersion(newVersion);
		fileEntry.setSize(byteArray.length);

		DLFileEntryUtil.update(fileEntry);

		// Folder

		folder.setLastPostDate(fileEntry.getModifiedDate());

		DLFolderUtil.update(folder);

		return fileEntry;
	}

	protected String getFolderId(String companyId, String folderId)
		throws PortalException, SystemException {

		if (!folderId.equals(DLFolder.DEFAULT_PARENT_FOLDER_ID)) {

			// Ensure folder exists and belongs to the proper company

			try {
				DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

				if (!companyId.equals(folder.getCompanyId())) {
					folderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;
				}
			}
			catch (NoSuchFolderException nsfe) {
				folderId = DLFolder.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

}