/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.repository.cmis;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepositoryImpl;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.cmis.model.CMISFileEntry;
import com.liferay.portal.repository.cmis.model.CMISFileVersion;
import com.liferay.portal.repository.cmis.model.CMISFolder;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portal.service.persistence.RepositoryUtil;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryModifiedDateComparator;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryReadCountComparator;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryTitleComparator;

import java.io.InputStream;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.ObjectIdImpl;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.exceptions.CmisPermissionDeniedException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

/**
 * CMIS does not provide vendor neutral support for workflow, metadata, tags,
 * categories, etc. They will be ignored in this implementation.
 *
 * @author Alexander Chow
 * @see    <a href="http://wiki.oasis-open.org/cmis/Candidate%20v2%20topics">
 *         Candidate v2 topics</a>
 * @see    <a href="http://wiki.oasis-open.org/cmis/Mixin_Proposal">Mixin /
 *         Aspect Support</a>
 * @see    <a
 *         href="http://www.oasis-open.org/committees/document.php?document_id=39631">
 *         CMIS Type Mutability proposal</a>
 */
public class CMISRepository extends BaseRepositoryImpl {

	public static final String ATOMPUB_URL = "ATOMPUB_URL";

	public static final String CONFIGURATION_ATOMPUB = "ATOMPUB";

	public static final String CONFIGURATION_WEBSERVICES = "WEBSERVICES";

	public static final String REPOSITORY_ID = "REPOSITORY_ID";

	public static final String WEBSERVICES_ACL_SERVICE =
		"WEBSERVICES_ACL_SERVICE";

	public static final String WEBSERVICES_DISCOVERY_SERVICE =
		"WEBSERVICES_DISCOVERY_SERVICE";

	public static final String WEBSERVICES_MULTIFILING_SERVICE =
		"WEBSERVICES_MULTIFILING_SERVICE";

	public static final String WEBSERVICES_NAVIGATION_SERVICE =
		"WEBSERVICES_NAVIGATION_SERVICE";

	public static final String WEBSERVICES_OBJECT_SERVICE =
		"WEBSERVICES_OBJECT_SERVICE";

	public static final String WEBSERVICES_POLICY_SERVICE =
		"WEBSERVICES_POLICY_SERVICE";

	public static final String WEBSERVICES_RELATIONSHIP_SERVICE =
		"WEBSERVICES_RELATIONSHIP_SERVICE";

	public static final String WEBSERVICES_REPOSITORY_SERVICE =
		"WEBSERVICES_REPOSITORY_SERVICE";

	public static final String WEBSERVICES_VERSIONING_SERVICE =
		"WEBSERVICES_VERSIONING_SERVICE";

	public static final String[] SUPPORTED_CONFIGURATIONS = {
		CONFIGURATION_ATOMPUB, CONFIGURATION_WEBSERVICES
	};

	public static final String[][] SUPPORTED_PARAMETERS = {
		new String[] {
			ATOMPUB_URL, REPOSITORY_ID
		},
		new String[] {
			REPOSITORY_ID, WEBSERVICES_ACL_SERVICE,
			WEBSERVICES_DISCOVERY_SERVICE, WEBSERVICES_MULTIFILING_SERVICE,
			WEBSERVICES_NAVIGATION_SERVICE, WEBSERVICES_OBJECT_SERVICE,
			WEBSERVICES_POLICY_SERVICE, WEBSERVICES_RELATIONSHIP_SERVICE,
			WEBSERVICES_REPOSITORY_SERVICE, WEBSERVICES_VERSIONING_SERVICE
		}
	};

	public FileEntry addFileEntry(
			long folderId, String title, String description, String changeLog,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			if (hasTitle(session, folderId, title)) {
				throw new DuplicateFileException(title);
			}

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, folderId);

			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put(PropertyIds.NAME, title);
			properties.put(
				PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());

			String contentType = (String)serviceContext.getAttribute(
				"contentType");

			ContentStream contentStream = new ContentStreamImpl(
				title, BigInteger.valueOf(size), contentType, is);

			return toFileEntry(
				cmisFolder.createDocument(properties, contentStream, null));
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			if (hasTitle(session, parentFolderId, title)) {
				throw new DuplicateFolderNameException(title);
			}

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, parentFolderId);

			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put(PropertyIds.NAME, title);
			properties.put(
				PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_FOLDER.value());

			return toFolder(cmisFolder.createFolder(properties));
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public Folder copyFolder(
			long sourceFolderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// TODO

		return null;
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			FileEntry fileEntry = getFileEntry(session, fileEntryId);

			Document document = (Document)fileEntry.getModel();

			document.deleteAllVersions();
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, folderId);

			cmisFolder.deleteTree(true, UnfileObject.DELETE, false);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		try {
			Session session = getSession();

			List<String> objectIds = getObjectIds(
				session, folderId, true, start, end, obc);

			List<FileEntry> fileEntries = new ArrayList<FileEntry>(objectIds.size());

			for (String objectId : objectIds) {
				Document document = (Document)session.getObject(
					new ObjectIdImpl(objectId));

				fileEntries.add(toFileEntry(document));
			}

			return fileEntries;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		List<Object> fileEntriesAndFileShortcuts = new ArrayList<Object>();

		for (long folderId : folderIds) {
			List<FileEntry> fileEntries = getFileEntries(
				folderId, start, end, null);

			fileEntriesAndFileShortcuts.addAll(fileEntries);
		}

		List<DLFileShortcut> dlFileShortcuts =
			dlAppHelperLocalService.getFileShortcuts(
				getGroupId(), folderIds, status);

		fileEntriesAndFileShortcuts.addAll(dlFileShortcuts);

		return fileEntriesAndFileShortcuts;
	}

	public int getFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		int count = 0;

		for (long folderId : folderIds) {
			count += getFileEntriesCount(folderId);
		}

		count +=
			dlAppHelperLocalService.getFileShortcutsCount(
				getGroupId(), folderIds, status);

		return count;
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return getCount(folderId, true);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			return getFileEntry(session, fileEntryId);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			String objectId = getObjectId(session, folderId, true, title);

			if (objectId != null) {
				CmisObject cmisObject = session.getObject(
					new ObjectIdImpl(objectId));

				Document document = (Document)cmisObject;

				return toFileEntry(document);
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}

		throw new NoSuchFileEntryException(
			"No CMIS file entry with {folderId=" + folderId + ", title=" +
				title + "}");
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			RepositoryEntry repositoryEntry = RepositoryEntryUtil.findByUUID_G(
				uuid, getGroupId());

			ObjectId objectId = new ObjectIdImpl(repositoryEntry.getMappedId());

			return toFileEntry((Document)session.getObject(objectId));
		}
		catch (NoSuchRepositoryEntryException nsree) {
			throw new NoSuchFileEntryException(nsree);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			return getFileVersion(session, fileVersionId);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			return getFolder(session, folderId);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			String objectId = getObjectId(
				session, parentFolderId, false, title);

			if (objectId != null) {
				CmisObject cmisObject = session.getObject(
					new ObjectIdImpl(objectId));

				return toFolder(
					(org.apache.chemistry.opencmis.client.api.Folder)
						cmisObject);
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}

		throw new NoSuchFolderException(
			"No CMIS file entry with {parentFolderId=" + parentFolderId +
				", title=" + title + "}");
	}

	public List<Folder> getFolders(long parentFolderId, int start, int end)
		throws SystemException {

		try {
			Session session = getSession();

			List<String> objectIds = getObjectIds(
				session, parentFolderId, false, start, end, null);

			List<Folder> folders = new ArrayList<Folder>(objectIds.size());

			for (String objectId : objectIds) {
				CmisObject cmisObject = session.getObject(
					new ObjectIdImpl(objectId));

				Folder folder = toFolder(
					(org.apache.chemistry.opencmis.client.api.Folder)
						cmisObject);

				folders.add(folder);
			}

			return folders;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		try {
			List<Object> foldersAndFileEntriesAndFileShortcuts =
				new ArrayList<Object>();

			for (long folderId : folderIds) {
				Iterator<CmisObject> itr = getIterator(folderId, start, end);

				while (itr.hasNext()) {
					CmisObject cmisObject = itr.next();

					Object folderFileEntryOrFileShortcut =
						toFolderFileEntryOrFileShortcut(cmisObject);

					if (folderFileEntryOrFileShortcut != null) {
						foldersAndFileEntriesAndFileShortcuts.add(
							folderFileEntryOrFileShortcut);
					}
				}
			}

			return foldersAndFileEntriesAndFileShortcuts;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			List<Long> folderIds, int status)
		throws SystemException {

		int count = getFoldersFileEntriesCount(folderIds, status);

		count +=
			dlAppHelperLocalService.getFileShortcutsCount(
				getGroupId(), folderIds, status);

		return count;
	}

	public int getFoldersCount(long parentFolderId) throws SystemException {
		return getCount(parentFolderId, false);
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		try {
			int count = 0;

			for (long folderId : folderIds) {
				count += getFileEntriesCount(folderId);
				count += getFoldersCount(folderId);
			}

			return count;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws SystemException {

		try {
			List<Long> subfolderIds = new ArrayList<Long>();

			List<Folder> subFolders = getFolders(
				folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			getSubfolderIds(subfolderIds, subFolders, recurse);

			return subfolderIds;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void initRepository() throws PortalException, SystemException {
		try {
			Session session = getSession();

			session.getRepositoryInfo();
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(
				"Unable to initialize CMIS session", e);
		}
	}

	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		// TODO

		return null;
	}

	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		// TODO

		return null;
	}

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		// TODO

		return null;
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		// TODO

		return null;
	}

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			ObjectId versionSeriesId = toFileEntryId(fileEntryId);
			ObjectId newFolderObjectId = toFolderId(session, newFolderId);

			Document document = (Document)session.getObject(versionSeriesId);

			String oldFolderObjectId = document.getParents().get(0).getId();

			if (oldFolderObjectId.equals(newFolderObjectId.toString())) {
				return toFileEntry(document);
			}

			document = (Document)document.move(
				new ObjectIdImpl(oldFolderObjectId), newFolderObjectId);

			ObjectId newObjectId = new ObjectIdImpl(
				document.getVersionSeriesId());

			if (!versionSeriesId.toString().equals(newObjectId.toString())) {
				document = (Document)session.getObject(newObjectId);

				updateMappedId(fileEntryId, document.getVersionSeriesId());
			}

			FileEntry fileEntry = toFileEntry(document);

			document = null;

			return fileEntry;
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		// TODO

		return null;
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		// TODO

		return null;
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Document document = null;

		try {
			Session session = getSession();

			ObjectId versionSeriesId = toFileEntryId(fileEntryId);

			document = (Document)session.getObject(versionSeriesId);

			Document oldVersion = null;

			List<Document> documentVersions = document.getAllVersions();

			for (Document currentVersion : documentVersions) {
				String currentVersionLabel = currentVersion.getVersionLabel();

				if (Validator.isNull(currentVersionLabel)) {
					currentVersionLabel = DLFileEntryConstants.DEFAULT_VERSION;
				}

				if (currentVersionLabel.equals(version)) {
					oldVersion = currentVersion;

					break;
				}
			}

			String changeLog = "Reverted to " + version;
			String title = oldVersion.getName();
			ContentStream contentStream = oldVersion.getContentStream();

			String oldTitle = document.getName();

			ObjectId pwcId = document.checkOut();

			if (!pwcId.toString().equals(versionSeriesId.toString())) {
				document = (Document)session.getObject(pwcId);
			}

			Map<String, Object> properties = new HashMap<String, Object>();

			if (Validator.isNotNull(title) && !title.equals(oldTitle)) {
				properties.put(PropertyIds.NAME, title);
			}

			ObjectId newObjectId = document.checkIn(
				true, properties, contentStream, changeLog);

			if (!versionSeriesId.toString().equals(newObjectId.toString())) {
				document = (Document)session.getObject(newObjectId);

				updateMappedId(fileEntryId, document.getVersionSeriesId());
			}

			toFileEntry(document);

			document = null;
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
		finally {
			if (document != null) {
				document.cancelCheckOut();
			}
		}
	}

	public FileEntry toFileEntry(Document document) throws SystemException {
		Object[] ids = getRepositoryEntryIds(document.getVersionSeriesId());

		long fileEntryId = (Long)ids[0];
		String uuid = (String)ids[1];

		return new CMISFileEntry(this, uuid, fileEntryId, document);
	}

	public FileVersion toFileVersion(Document version) throws SystemException {
		Object[] ids = getRepositoryEntryIds(version.getId());

		long fileVersionId = (Long)ids[0];

		return new CMISFileVersion(this, fileVersionId, version);
	}

	public Folder toFolder(
			org.apache.chemistry.opencmis.client.api.Folder cmisFolder)
		throws SystemException {

		Object[] ids = getRepositoryEntryIds(cmisFolder.getId());

		long folderId = (Long)ids[0];
		String uuid = (String)ids[1];

		return new CMISFolder(this, uuid, folderId, cmisFolder);
	}

	public void unlockFileEntry(long fileEntryId) throws SystemException {

		// TODO

	}

	public void unlockFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		// TODO

	}

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, SystemException {

		// TODO

	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Document document = null;

		try {
			Session session = getSession();

			ObjectId versionSeriesId = toFileEntryId(fileEntryId);

			document = (Document)session.getObject(versionSeriesId);

			ObjectId pwcId = document.checkOut();

			if (!pwcId.toString().equals(versionSeriesId.toString())) {
				document = (Document)session.getObject(pwcId);
			}

			String oldTitle = document.getName();

			Map<String, Object> properties = new HashMap<String, Object>();

			ContentStream contentStream = null;

			if (Validator.isNotNull(title) && !title.equals(oldTitle)) {
				properties.put(PropertyIds.NAME, title);
			}

			if (is != null) {
				String contentType = (String)serviceContext.getAttribute(
					"contentType");

				contentStream = new ContentStreamImpl(
					sourceFileName, BigInteger.valueOf(size), contentType, is);
			}

			ObjectId newObjectId = document.checkIn(
				majorVersion, properties, contentStream, changeLog);

			if (!versionSeriesId.toString().equals(newObjectId.toString())) {
				document = (Document)session.getObject(newObjectId);

				updateMappedId(fileEntryId, document.getVersionSeriesId());
			}

			FileEntry fileEntry = toFileEntry(document);

			document = null;

			return fileEntry;
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
		finally {
			if (document != null) {
				document.cancelCheckOut();
			}
		}
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			Session session = getSession();

			ObjectId objectId = toFolderId(session, folderId);

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				(org.apache.chemistry.opencmis.client.api.Folder)
					session.getObject(objectId);

			String oldTitle = cmisFolder.getName();

			Map<String, Object> properties = new HashMap<String, Object>();

			if (Validator.isNotNull(title) && !title.equals(oldTitle)) {
				properties.put(PropertyIds.NAME, title);
			}

			ObjectId newObjectId = cmisFolder.updateProperties(
				properties, true);

			if (!objectId.toString().equals(newObjectId.toString())) {
				cmisFolder =
					(org.apache.chemistry.opencmis.client.api.Folder)
						session.getObject(newObjectId);

				updateMappedId(folderId, newObjectId.toString());
			}

			return toFolder(cmisFolder);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		// TODO

		return false;
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		// TODO

		return false;
	}

	protected org.apache.chemistry.opencmis.client.api.Folder getCmisFolder(
			Session session, long folderId)
		throws PortalException, SystemException {

		Folder folder = getFolder(session, folderId);

		org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
			(org.apache.chemistry.opencmis.client.api.Folder)folder.getModel();

		return cmisFolder;
	}

	protected int getCount(long folderId, boolean fileEntries)
		throws SystemException {

		try {
			Session session = getSession();

			ObjectId objectId = toFolderId(session, folderId);

			StringBundler sb = new StringBundler(5);

			sb.append("SELECT cmis:objectId FROM ");

			if (fileEntries) {
				sb.append("cmis:document ");
			}
			else {
				sb.append("cmis:folder ");
			}

			sb.append("WHERE IN_FOLDER('");
			sb.append(objectId.getId());
			sb.append("')");

			ItemIterable<QueryResult> queryResults = session.query(
				sb.toString(), false);

			int count = (int)queryResults.getTotalNumItems();

			if (count < 0) {
				count = 0;

				Iterator<QueryResult> itr = queryResults.iterator();

				while (itr.hasNext()) {
					itr.next();

					count++;
				}
			}

			return count;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	protected FileEntry getFileEntry(Session session, long fileEntryId)
		throws PortalException, SystemException {

		ObjectId objectId = toFileEntryId(fileEntryId);

		return toFileEntry((Document)session.getObject(objectId));
	}

	protected FileVersion getFileVersion(Session session, long fileVersionId)
		throws PortalException, SystemException {

		ObjectId objectId = toFileVersionId(fileVersionId);

		return toFileVersion((Document)session.getObject(objectId));
	}

	protected Folder getFolder(Session session, long folderId)
		throws PortalException, SystemException {

		ObjectId objectId = toFolderId(session, folderId);

		CmisObject cmisObject = session.getObject(objectId);

		return (Folder)toFolderFileEntryOrFileShortcut(cmisObject);
	}

	protected Iterator<CmisObject> getIterator(
			long folderId, int start, int end)
		throws PortalException, SystemException {

		Session session = getSession();

		org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
			getCmisFolder(session, folderId);

		OperationContext operationContext = new OperationContextImpl();

		operationContext.setOrderBy("cmis:name ASC");

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			ItemIterable<CmisObject> cmisObjects = cmisFolder.getChildren(
				operationContext);

			return cmisObjects.iterator();
		}
		else {
			operationContext.setMaxItemsPerPage(end - start);

			ItemIterable<CmisObject> cmisObjects = cmisFolder.getChildren(
				operationContext);

			ItemIterable<CmisObject> cmisObject = cmisObjects.skipTo(start);

			cmisObject = cmisObject.getPage();

			return cmisObject.iterator();
		}
	}

	protected String getObjectId(
			Session session, long folderId, boolean fileEntry, String name)
		throws SystemException {

		try {
			ObjectId objectId = toFolderId(session, folderId);

			StringBundler sb = new StringBundler(7);

			sb.append("SELECT cmis:objectId FROM ");

			if (fileEntry) {
				sb.append("cmis:document ");
			}
			else {
				sb.append("cmis:folder ");
			}

			sb.append("WHERE cmis:name = '");
			sb.append(name);
			sb.append("' AND IN_FOLDER('");
			sb.append(objectId.getId());
			sb.append("')");

			ItemIterable<QueryResult> queryResults = session.query(
				sb.toString(), false);

			Iterator<QueryResult> itr = queryResults.iterator();

			if (itr.hasNext()) {
				QueryResult queryResult = itr.next();

				PropertyData<String> propertyData = queryResult.getPropertyById(
					PropertyIds.OBJECT_ID);

				List<String> values = propertyData.getValues();

				return values.get(0);
			}

			return null;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	protected List<String> getObjectIds(
			Session session, long folderId, boolean fileEntries, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		try {
			ObjectId objectId = toFolderId(session, folderId);

			StringBundler sb = new StringBundler(6);

			sb.append("SELECT cmis:objectId FROM ");

			if (fileEntries) {
				sb.append("cmis:document ");
			}
			else {
				sb.append("cmis:folder ");
			}

			sb.append("WHERE IN_FOLDER('");
			sb.append(objectId.getId());
			sb.append("') ORDER BY ");

			if (obc != null) {
				if (obc instanceof FileEntryModifiedDateComparator) {
					if (obc.isAscending()) {
						sb.append("cmis:lastModificationDate ASC");
					}
					else {
						sb.append("cmis:lastModificationDate DESC");
					}
				}
				else if (obc instanceof FileEntryReadCountComparator) {

					// TODO

				}
				else if (obc instanceof FileEntryTitleComparator) {
					if (!obc.isAscending()) {
						sb.append("cmis:name DESC");
					}
				}
			}

			if (sb.index() == 5) {
				sb.append("cmis:name ASC");
			}

			ItemIterable<QueryResult> queryResults = session.query(
				sb.toString(), false);

			List<String> objectIds = new ArrayList<String>();

			Iterator<QueryResult> itr = queryResults.iterator();

			while (itr.hasNext()) {
				QueryResult queryResult = itr.next();

				PropertyData<String> propertyData = queryResult.getPropertyById(
					PropertyIds.OBJECT_ID);

				List<String> values = propertyData.getValues();

				objectIds.add(values.get(0));
			}

			return ListUtil.subList(objectIds, start, end);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	protected Session getSession() throws RepositoryException {
		String login = PrincipalThreadLocal.getName();
		String password = PrincipalThreadLocal.getPassword();

		try {
			String authType = CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()).getAuthType();

			if (!authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				User user = UserLocalServiceUtil.getUser(
					GetterUtil.getLong(login));

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					login = user.getEmailAddress();
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					login = user.getScreenName();
				}
			}
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put(SessionParameter.USER, login);
		parameters.put(SessionParameter.PASSWORD, password);
		parameters.put(SessionParameter.LOCALE_ISO3166_COUNTRY,
			LocaleUtil.getDefault().getCountry());
		parameters.put(SessionParameter.LOCALE_ISO639_LANGUAGE,
			LocaleUtil.getDefault().getLanguage());

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		if (typeSettingsProperties.containsKey(ATOMPUB_URL)) {
			parameters.put(
				SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

			putParameter(parameters, SessionParameter.ATOMPUB_URL, ATOMPUB_URL);
		}
		else {
			parameters.put(
				SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());

			putParameter(
				parameters, SessionParameter.WEBSERVICES_ACL_SERVICE,
				WEBSERVICES_ACL_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_DISCOVERY_SERVICE,
				WEBSERVICES_DISCOVERY_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_MULTIFILING_SERVICE,
				WEBSERVICES_MULTIFILING_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_NAVIGATION_SERVICE,
				WEBSERVICES_NAVIGATION_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_OBJECT_SERVICE,
				WEBSERVICES_OBJECT_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_POLICY_SERVICE,
				WEBSERVICES_POLICY_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE,
				WEBSERVICES_RELATIONSHIP_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_REPOSITORY_SERVICE,
				WEBSERVICES_REPOSITORY_SERVICE);
			putParameter(
				parameters, SessionParameter.WEBSERVICES_VERSIONING_SERVICE,
				WEBSERVICES_VERSIONING_SERVICE);
		}

		if (!typeSettingsProperties.containsKey(REPOSITORY_ID)) {
			org.apache.chemistry.opencmis.client.api.Repository cmisRepository =
				_sessionFactory.getRepositories(parameters).get(0);

			typeSettingsProperties.setProperty(
				REPOSITORY_ID, cmisRepository.getId());

			try {
				Repository repository = RepositoryUtil.findByPrimaryKey(
					getRepositoryId());

				repository.setTypeSettingsProperties(typeSettingsProperties);

				RepositoryUtil.update(repository, false);
			}
			catch (Exception e) {
				throw new RepositoryException(e);
			}
		}

		putParameter(
			parameters, SessionParameter.REPOSITORY_ID, REPOSITORY_ID);

		return _sessionFactory.createSession(parameters);
	}

	protected void getSubfolderIds(
			List<Long> subfolderIds, List<Folder> subfolders, boolean recurse)
		throws SystemException {

		for (Folder subfolder : subfolders) {
			long subfolderId = subfolder.getFolderId();

			subfolderIds.add(subfolderId);

			if (recurse) {
				List<Folder> subSubFolders = getFolders(
					subfolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				getSubfolderIds(subfolderIds, subSubFolders, recurse);
			}
		}
	}

	protected boolean hasTitle(Session session, long folderId, String title)
		throws SystemException {

		String objectId = getObjectId(session, folderId, true, title);

		if (objectId == null) {
			objectId = getObjectId(session, folderId, false, title);
		}

		if (objectId == null) {
			return false;
		}
		else {
			return true;
		}
	}

	protected void processException(Exception e) throws PortalException {
		if (e instanceof CmisRuntimeException &&
			e.getMessage().contains("authorized")) {

			throw new PrincipalException(e);
		}
		else if (e instanceof CmisPermissionDeniedException) {
			throw new PrincipalException(e);
		}
	}

	protected void putParameter(
		Map<String, String> parameters, String chemistryKey,
		String typeSettingsKey) {

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		String value = typeSettingsProperties.getProperty(typeSettingsKey);

		parameters.put(chemistryKey, value);
	}

	protected ObjectId toFileEntryId(long fileEntryId)
		throws PortalException, SystemException {

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(
			fileEntryId);

		if (repositoryEntry == null) {
			throw new NoSuchFileEntryException(
				"No CMIS file entry with {fileEntryId=" + fileEntryId + "}");
		}

		return new ObjectIdImpl(repositoryEntry.getMappedId());
	}

	protected ObjectId toFileVersionId(long fileVersionId)
		throws PortalException, SystemException {

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(
			fileVersionId);

		if (repositoryEntry == null) {
			throw new NoSuchFileEntryException(
				"No CMIS file version with {fileVersionId=" + fileVersionId +
					"}");
		}

		return new ObjectIdImpl(repositoryEntry.getMappedId());
	}

	protected Object toFolderFileEntryOrFileShortcut(CmisObject cmisObject)
		throws SystemException {

		if (cmisObject instanceof Document) {
			FileEntry fileEntry = toFileEntry((Document)cmisObject);

			return fileEntry;
		}
		else if (cmisObject instanceof
					org.apache.chemistry.opencmis.client.api.Folder) {

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				(org.apache.chemistry.opencmis.client.api.Folder)cmisObject;

			Folder folder = toFolder(cmisFolder);

			return folder;
		}
		else {
			return null;
		}
	}

	protected ObjectId toFolderId(Session session, long folderId)
		throws PortalException, SystemException {

		RepositoryEntry repositoryEntry =
			RepositoryEntryUtil.fetchByPrimaryKey(folderId);

		if (repositoryEntry == null) {
			DLFolder dlFolder = DLFolderUtil.fetchByPrimaryKey(folderId);

			if (dlFolder == null) {
				throw new NoSuchFolderException(
					"No CMIS folder with {folderId=" + folderId + "}");
			}
			else if (!dlFolder.isMountPoint()) {
				throw new RepositoryException(
					"CMIS repository should not be used for folder ID " +
						folderId);
			}

			String rootFolderId = session.getRepositoryInfo().getRootFolderId();

			repositoryEntry = RepositoryEntryUtil.fetchByR_M(
				getRepositoryId(), rootFolderId);

			if (repositoryEntry == null) {
				long repositoryEntryId = counterLocalService.increment();

				repositoryEntry = RepositoryEntryUtil.create(repositoryEntryId);

				repositoryEntry.setGroupId(getGroupId());
				repositoryEntry.setRepositoryId(getRepositoryId());
				repositoryEntry.setMappedId(rootFolderId);

				RepositoryEntryUtil.update(repositoryEntry, false);
			}
		}

		return new ObjectIdImpl(repositoryEntry.getMappedId());
	}

	protected void updateMappedId(long repositoryEntryId, String mappedId)
		throws NoSuchRepositoryEntryException, SystemException {

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.findByPrimaryKey(
			repositoryEntryId);

		if (!repositoryEntry.getMappedId().equals(mappedId)) {
			repositoryEntry.setMappedId(mappedId);

			RepositoryEntryUtil.update(repositoryEntry, false);
		}
	}

	private SessionFactory _sessionFactory = SessionFactoryImpl.newInstance();

}