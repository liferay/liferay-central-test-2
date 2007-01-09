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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryAndShortcutFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPK;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.MathUtil;
import com.liferay.util.Validator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLFileEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryLocalServiceImpl implements DLFileEntryLocalService {

	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description, String extraSettings, File file,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {
	
		return addFileEntry(
			userId, folderId, name, title, description, extraSettings,
			file, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}
	
	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description, String extraSettings, byte[] byteArray,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFileEntry(
			userId, folderId, name, title, description, extraSettings,
			byteArray, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}
	
	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description, String extraSettings, File file,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {
		
		return addFileEntry(
			userId, folderId, name, title, description, extraSettings, file, 
			null, null, communityPermissions, guestPermissions);
	}

	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description, String extraSettings, byte[] byteArray,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addFileEntry(
			userId, folderId, name, title, description, extraSettings,
			byteArray, null, null, communityPermissions, guestPermissions);
	}

	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description,	String extraSettings, File file,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		if ((file == null) || (file.length() == 0)) {
			throw new FileSizeException();
		}
		
		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
		
		return addFileEntry(
			userId, folderId, name, title, description, extraSettings, is, 
			file.length(), addCommunityPermissions, addGuestPermissions, 
			communityPermissions, guestPermissions);
	}
	
	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description,	String extraSettings, byte[] byteArray,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		if ((byteArray == null) || (byteArray.length == 0)) {
			throw new FileSizeException();
		}
		
		InputStream is = 
			new BufferedInputStream(new ByteArrayInputStream(byteArray));
		
		return addFileEntry(
			userId, folderId, name, title, description, extraSettings, is, 
			byteArray.length, addCommunityPermissions, addGuestPermissions, 
			communityPermissions, guestPermissions);
	}
	
	public DLFileEntry addFileEntry(
			String userId, String folderId, String name, String title,
			String description,	String extraSettings, InputStream is, long size,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// File entry

		User user = UserUtil.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);
		Date now = new Date();

		if (Validator.isNull(title)) {
			title = name;
		}

		name = getName(name);

		if (is == null) {
			throw new FileSizeException();
		}

		if (DLLocalServiceUtil.hasFileContentNode(
				user.getCompanyId(), folderId, name, 0)) {

			throw new DuplicateFileException(name);
		}

		DLFileEntry fileEntry = DLFileEntryUtil.create(
			new DLFileEntryPK(folderId, name));

		fileEntry.setCompanyId(user.getCompanyId());
		fileEntry.setUserId(user.getUserId());
		fileEntry.setUserName(user.getFullName());
		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setCreateDate(now);
		fileEntry.setModifiedDate(now);
		fileEntry.setTitle(title);
		fileEntry.setDescription(description);
		fileEntry.setVersion(DLFileEntryImpl.DEFAULT_VERSION);
		fileEntry.setSize((int)size);
		fileEntry.setReadCount(DLFileEntryImpl.DEFAULT_READ_COUNT);
		fileEntry.setExtraSettings(extraSettings);

		DLFileEntryUtil.update(fileEntry);

		DLLocalServiceUtil.addFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			folder.getGroupId(), folderId, name, is);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addFileEntryResources(
				folder, fileEntry, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addFileEntryResources(
				folder, fileEntry, communityPermissions, guestPermissions);
		}

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

	public void addFileEntryResources(
			String folderId, String name, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);
		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(
			new DLFileEntryPK(folderId, name));

		addFileEntryResources(
			folder, fileEntry, communityPermissions, guestPermissions);
	}

	public void addFileEntryResources(
			DLFolder folder, DLFileEntry fileEntry,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			fileEntry.getCompanyId(), folder.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getPrimaryKey().toString(), communityPermissions,
			guestPermissions);
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
		catch (NoSuchFileException nsfe) {
			_log.warn(nsfe);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// File ranks

		DLFileRankLocalServiceUtil.deleteFileRanks(
			fileEntry.getFolderId(), fileEntry.getName());

		// File shortcuts

		DLFileShortcutLocalServiceUtil.deleteFileShortcuts(
			fileEntry.getFolderId(), fileEntry.getName());

		// File versions

		Iterator itr = DLFileVersionUtil.findByF_N(
			fileEntry.getFolderId(), fileEntry.getName()).iterator();

		while (itr.hasNext()) {
			DLFileVersion fileVersion = (DLFileVersion)itr.next();

			DLFileVersionUtil.remove(fileVersion.getPrimaryKey());
		}

		// Ratings

		RatingsStatsLocalServiceUtil.deleteStats(
			DLFileEntry.class.getName(), fileEntry.getPrimaryKey().toString());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			fileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
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

		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(
			new DLFileEntryPK(folderId, name));

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

		return DLFileEntryUtil.findByPrimaryKey(
			new DLFileEntryPK(folderId, name));
	}

	public List getFileEntries(String folderId) throws SystemException {
		return DLFileEntryUtil.findByFolderId(folderId);
	}

	public List getFileEntries(String folderId, int begin, int end)
		throws SystemException {

		return DLFileEntryUtil.findByFolderId(folderId, begin, end);
	}

	public List getFileEntriesAndShortcuts(String folderId, int begin, int end)
		throws SystemException {

		List folderIds = new ArrayList();

		folderIds.add(folderId);

		return DLFileEntryAndShortcutFinder.findByFolderIds(
			folderIds, begin, end);
	}

	public List getFileEntriesAndShortcuts(List folderIds, int begin, int end)
		throws SystemException {

		return DLFileEntryAndShortcutFinder.findByFolderIds(
			folderIds, begin, end);
	}

	public int getFileEntriesAndShortcutsCount(String folderId)
		throws SystemException {

		List folderIds = new ArrayList();

		folderIds.add(folderId);

		return DLFileEntryAndShortcutFinder.countByFolderIds(folderIds);
	}

	public int getFileEntriesAndShortcutsCount(List folderIds)
		throws SystemException {

		return DLFileEntryAndShortcutFinder.countByFolderIds(folderIds);
	}

	public int getFileEntriesCount(String folderId) throws SystemException {
		return DLFileEntryUtil.countByFolderId(folderId);
	}

	public int getFoldersFileEntriesCount(List folderIds)
		throws SystemException {

		return DLFileEntryFinder.countByFolderIds(folderIds);
	}

	public List getGroupFileEntries(long groupId, int begin, int end)
		throws SystemException {

		return DLFileEntryFinder.findByGroupId(groupId, begin, end);
	}

	public List getGroupFileEntries(
			long groupId, String userId, int begin, int end)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return DLFileEntryFinder.findByGroupId(groupId, begin, end);
		}
		else {
			return DLFileEntryFinder.findByG_U(groupId, userId, begin, end);
		}
	}

	public int getGroupFileEntriesCount(long groupId) throws SystemException {
		return DLFileEntryFinder.countByGroupId(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, String userId)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return DLFileEntryFinder.countByGroupId(groupId);
		}
		else {
			return DLFileEntryFinder.countByG_U(groupId, userId);
		}
	}

	public DLFileEntry updateFileEntry(
			String userId, String folderId, String newFolderId, String name,
			String sourceFileName, String title, String description,
			String extraSettings, File file)
		throws PortalException, SystemException {
		if ((file == null) || (file.length() == 0)) {
			throw new FileSizeException();
		}
		
		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
		
		return updateFileEntry(
			userId, folderId, newFolderId, name, sourceFileName, title, 
			description, extraSettings, is, file.length());
	}
	
	public DLFileEntry updateFileEntry(
			String userId, String folderId, String newFolderId, String name,
			String sourceFileName, String title, String description,
			String extraSettings, byte[] byteArray)
		throws PortalException, SystemException {

		if ((byteArray == null) || (byteArray.length == 0)) {
			throw new FileSizeException();
		}
		
		InputStream is = 
			new BufferedInputStream(new ByteArrayInputStream(byteArray));
		
		return updateFileEntry(
			userId, folderId, newFolderId, name, sourceFileName, title, 
			description, extraSettings, is, byteArray.length);
	}
	
	public DLFileEntry updateFileEntry(
			String userId, String folderId, String newFolderId, String name,
			String sourceFileName, String title, String description,
			String extraSettings, InputStream is, long size)
		throws PortalException, SystemException {

		// File entry

		User user = UserUtil.findByPrimaryKey(userId);
		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		if (Validator.isNull(title)) {
			title = name;
		}

		DLFileEntry fileEntry = DLFileEntryUtil.findByPrimaryKey(
			new DLFileEntryPK(folderId, name));

		fileEntry.setTitle(title);
		fileEntry.setDescription(description);
		fileEntry.setExtraSettings(extraSettings);

		DLFileEntryUtil.update(fileEntry);

		// Move file entry

		if (Validator.isNotNull(newFolderId) && !folderId.equals(newFolderId)) {
			DLFolder newFolder = DLFolderUtil.findByPrimaryKey(newFolderId);

			if (folder.getGroupId() != newFolder.getGroupId()) {
				throw new NoSuchFolderException();
			}

			if (DLLocalServiceUtil.hasFileContentNode(
					user.getCompanyId(), newFolderId, name, 0)) {

				throw new DuplicateFileException(name);
			}

			DLFileEntry newFileEntry = DLFileEntryUtil.create(
				new DLFileEntryPK(newFolderId, name));

			newFileEntry.setCompanyId(fileEntry.getCompanyId());
			newFileEntry.setUserId(fileEntry.getUserId());
			newFileEntry.setUserName(fileEntry.getUserName());
			newFileEntry.setVersionUserId(fileEntry.getVersionUserId());
			newFileEntry.setVersionUserName(fileEntry.getVersionUserName());
			newFileEntry.setCreateDate(fileEntry.getCreateDate());
			newFileEntry.setModifiedDate(fileEntry.getModifiedDate());
			newFileEntry.setTitle(fileEntry.getTitle());
			newFileEntry.setDescription(fileEntry.getDescription());
			newFileEntry.setVersion(fileEntry.getVersion());
			newFileEntry.setSize(fileEntry.getSize());
			newFileEntry.setReadCount(fileEntry.getReadCount());
			newFileEntry.setExtraSettings(extraSettings);

			DLFileEntryUtil.update(newFileEntry);

			DLFileEntryUtil.remove(fileEntry);

			fileEntry = newFileEntry;

			Iterator itr = DLFileVersionUtil.findByF_N(
				folderId, name).iterator();

			while (itr.hasNext()) {
				DLFileVersion fileVersion = (DLFileVersion)itr.next();

				DLFileVersion newFileVersion = DLFileVersionUtil.create(
					new DLFileVersionPK(
						newFolderId, name, fileVersion.getVersion()));

				newFileVersion.setCompanyId(fileVersion.getCompanyId());
				newFileVersion.setUserId(fileVersion.getUserId());
				newFileVersion.setUserName(fileVersion.getUserName());
				newFileVersion.setCreateDate(fileVersion.getCreateDate());
				newFileVersion.setSize(fileVersion.getSize());

				DLFileVersionUtil.update(newFileVersion);

				DLFileVersionUtil.remove(fileVersion);
			}

			DLFileShortcutLocalServiceUtil.updateFileShortcuts(
				folderId, name, newFolderId, name);

			try {
				DLServiceUtil.updateFile(
					user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					folder.getGroupId(), folderId, newFolderId, name);
			}
			catch (RemoteException re) {
				throw new SystemException(re);
			}

			folderId = newFolderId;
			folder = newFolder;
		}

		// File version

		if (is == null) {
			return fileEntry;
		}

		double oldVersion = fileEntry.getVersion();
		double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

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
		fileEntry.setSize((int)size);

		DLFileEntryUtil.update(fileEntry);

		// File

		DLLocalServiceUtil.updateFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			folder.getGroupId(), folderId, name, newVersion, sourceFileName,
			is);

		// Folder

		folder.setLastPostDate(fileEntry.getModifiedDate());

		DLFolderUtil.update(folder);

		return fileEntry;
	}

	protected String getFolderId(String companyId, String folderId)
		throws PortalException, SystemException {

		if (!folderId.equals(DLFolderImpl.DEFAULT_PARENT_FOLDER_ID)) {

			// Ensure folder exists and belongs to the proper company

			try {
				DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

				if (!companyId.equals(folder.getCompanyId())) {
					folderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;
				}
			}
			catch (NoSuchFolderException nsfe) {
				folderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	protected String getName(String name) throws SystemException {
		String extension = StringPool.BLANK;

		int pos = name.lastIndexOf(StringPool.PERIOD);

		if (pos != -1) {
			extension = name.substring(pos + 1, name.length()).toLowerCase();
		}

		name = String.valueOf(CounterLocalServiceUtil.increment(
			DLFileEntry.class.getName()));

		if (Validator.isNotNull(extension)) {
			name = "DLFE-" + name + StringPool.PERIOD + extension;
		}

		return name;
	}

	private static Log _log =
		LogFactory.getLog(DLFileEntryLocalServiceImpl.class);

}