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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.BaseRepositoryImpl;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.cmis.model.CMISFileEntry;
import com.liferay.portal.repository.cmis.model.CMISFileVersion;
import com.liferay.portal.repository.cmis.model.CMISFolder;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;

import java.io.InputStream;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.client.runtime.ObjectIdImpl;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
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
		throws SystemException {

		try {
			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, folderId);

			if (hasTitle(cmisFolder, title)) {
				throw new DuplicateFileException(title);
			}

			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put(PropertyIds.NAME, title);
			properties.put(
				PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());

			String mimeType = MimeTypesUtil.getContentType(
				(String)serviceContext.getAttribute("sourceFileName"));

			ContentStream contentStream = new ContentStreamImpl(
				title, BigInteger.valueOf(size), mimeType, is);

			return toFileEntry(
				cmisFolder.createDocument(properties, contentStream, null));
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws SystemException {

		try {
			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, parentFolderId);

			if (hasTitle(cmisFolder, title)) {
				throw new DuplicateFolderNameException(title);
			}

			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put(PropertyIds.NAME, title);
			properties.put(
				PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_FOLDER.value());

			return toFolder(cmisFolder.createFolder(properties));
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public Folder copyFolder(
		long sourceFolderId, long parentFolderId, String title,
		String description, ServiceContext serviceContext) {

		// TODO

		return null;
	}

	public void deleteAll() {
		try {
			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder rootCMISFolder =
				session.getRootFolder();

			rootCMISFolder.deleteTree(true, UnfileObject.DELETE, true);
		}
		catch (Exception e) {
			UnicodeProperties typeSettingsProperties =
				getTypeSettingsProperties();

			String repositoryId = typeSettingsProperties.getProperty(
				REPOSITORY_ID);

			_log.error(
				"Unexpected error deleting CMIS repository " + repositoryId, e);
		}
	}

	public void deleteFileEntry(long fileEntryId) throws SystemException {
		try {
			Session session = getSession();

			FileEntry fileEntry = getFileEntry(session, fileEntryId);

			Document document = (Document)fileEntry.getModel();

			document.deleteAllVersions();
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void deleteFolder(long folderId) throws SystemException {
		try {
			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, folderId);

			cmisFolder.delete(true);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		try {
			List<FileEntry> fileEntries = new ArrayList<FileEntry>();

			Iterator<CmisObject> itr = getIterator(
				folderId, true, start, end, obc);

			boolean documentsSection = false;

			while (itr.hasNext()) {
				CmisObject cmisObject = itr.next();

				if (cmisObject instanceof Document) {
					FileEntry fileEntry = toFileEntry(cmisObject);

					fileEntries.add(fileEntry);

					documentsSection = true;
				}
				else if (documentsSection) {
					break;
				}
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
			throw new RepositoryException(e);
		}
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		try {
			Iterator<CmisObject> itr = getIterator(folderId, true);

			boolean documentsSection = false;

			while (itr.hasNext()) {
				CmisObject cmisObject = itr.next();

				if (cmisObject instanceof Document) {
					String cmisObjectName = cmisObject.getName();

					if (cmisObjectName.equals(title)) {
						return toFileEntry(cmisObject);
					}

					documentsSection = true;
				}
				else if (documentsSection) {
					break;
				}
			}
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
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

			ObjectId objectId = new ObjectIdImpl(uuid);

			return toFileEntry(session.getObject(objectId));
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
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
			throw new RepositoryException(e);
		}
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		try {
			Iterator<CmisObject> itr = getIterator(parentFolderId, false);

			boolean foldersSection = false;

			while (itr.hasNext()) {
				CmisObject cmisObject = itr.next();

				if (cmisObject instanceof
						org.apache.chemistry.opencmis.client.api.Folder) {

					String cmisObjectName = cmisObject.getName();

					if (cmisObjectName.equals(title)) {
						return toFolder(cmisObject);
					}

					foldersSection = true;
				}
				else if (foldersSection) {
					break;
				}
			}
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}

		throw new NoSuchFolderException(
			"No CMIS file entry with {parentFolderId=" + parentFolderId +
				", title=" + title + "}");
	}

	public List<Folder> getFolders(long parentFolderId, int start, int end)
		throws SystemException {

		try {
			List<Folder> folders = new ArrayList<Folder>();

			Iterator<CmisObject> itr = getIterator(
				parentFolderId, false, start, end, null);

			boolean foldersSection = false;

			while (itr.hasNext()) {
				CmisObject cmisObject = itr.next();

				if (cmisObject instanceof
						org.apache.chemistry.opencmis.client.api.Folder) {

					Folder folder = toFolder(cmisObject);

					folders.add(folder);

					foldersSection = true;
				}
				else if (foldersSection) {
					break;
				}
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
				Iterator<CmisObject> itr = getIterator(
					folderId, true, start, end, null);

				boolean documentsSection = false;
				boolean foldersSection = false;

				while (itr.hasNext()) {
					CmisObject cmisObject = itr.next();

					if (cmisObject instanceof Document) {
						FileEntry fileEntry = toFileEntry(cmisObject);

						foldersAndFileEntriesAndFileShortcuts.add(fileEntry);

						documentsSection = true;
					}
					else if (cmisObject instanceof
								org.apache.chemistry.opencmis.client.api.
									Folder) {

						Folder folder = toFolder(cmisObject);

						foldersAndFileEntriesAndFileShortcuts.add(folder);

						foldersSection = true;
					}
					else if (documentsSection && foldersSection) {
						break;
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
			Session session = getSession();

			List<Long> subfolderIds = new ArrayList<Long>();

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, folderId);

			int depth = 1;

			if (recurse) {
				depth = 10;
			}

			List<Tree<FileableCmisObject>> fileableCmisObjects =
				cmisFolder.getFolderTree(depth);

			getSubfolderIds(subfolderIds, fileableCmisObjects);

			return subfolderIds;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void initRepository() throws RepositoryException {
		try {
			Session session = getSession();

			session.getRepositoryInfo();
		}
		catch (IllegalArgumentException iae) {
			throw new RepositoryException(
			"Unable to initialize CMIS session", iae);
		}
	}

	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return null;
	}

	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		return null;
	}

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		return null;
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		return null;
	}

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return null;
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return null;
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return null;
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

	}

	public FileEntry toFileEntry(CmisObject cmisObject)
		throws PortalException, SystemException {

		if (cmisObject instanceof Document) {
			return new CMISFileEntry(
				this, getRepositoryId(),
				toRepositoryEntryId(cmisObject.getId()), (Document)cmisObject);
		}
		else {
			throw new NoSuchFileEntryException(
				"CMIS object is not a file entry " + cmisObject);
		}
	}

	public FileVersion toFileVersion(CmisObject object)
		throws PortalException, SystemException {

		if (object instanceof Document) {
			return new CMISFileVersion(
				this, getRepositoryId(), toRepositoryEntryId(object.getId()),
				(Document)object);
		}
		else {
			throw new NoSuchFileVersionException(
				"CMIS object is not a file version " + object);
		}
	}

	public Folder toFolder(CmisObject cmisObject)
		throws PortalException, SystemException {

		if (cmisObject instanceof
				org.apache.chemistry.opencmis.client.api.Folder) {

			return new CMISFolder(
				this, getRepositoryId(),
				toRepositoryEntryId(cmisObject.getId()),
				(org.apache.chemistry.opencmis.client.api.Folder)cmisObject);
		}
		else {
			throw new NoSuchFolderException(
				"CMIS object is not a folder " + cmisObject);
		}
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

		// TODO

		return null;
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// TODO

		return null;
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

		return toFileEntry(session.getObject(objectId));
	}

	protected FileVersion getFileVersion(Session session, long fileVersionId)
		throws PortalException, SystemException {

		ObjectId objectId = toFileVersionId(fileVersionId);

		return toFileVersion(session.getObject(objectId));
	}

	protected Folder getFolder(Session session, long folderId)
		throws PortalException, SystemException {

		ObjectId objectId = toFolderId(session, folderId);

		return toFolder(session.getObject(objectId));
	}

	protected Iterator<CmisObject> getIterator(
			long folderId, boolean fileEntriesFirst)
		throws PortalException, SystemException {

		Session session = getSession();

		org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
			getCmisFolder(session, folderId);

		OperationContext operationContext = getOperationContext(
			fileEntriesFirst);

		ItemIterable<CmisObject> cmisObjects = cmisFolder.getChildren(
			operationContext);

		return cmisObjects.iterator();
	}

	protected Iterator<CmisObject> getIterator(
			long folderId, boolean fileEntriesFirst, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		// TODO support obc

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			return getIterator(folderId, fileEntriesFirst);
		}

		Session session = getSession();

		org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
			getCmisFolder(session, folderId);

		OperationContext operationContext = getOperationContext(
			fileEntriesFirst);

		operationContext.setMaxItemsPerPage(end - start);

		ItemIterable<CmisObject> cmisObjects = cmisFolder.getChildren(
			operationContext);

		ItemIterable<CmisObject> cmisObject = cmisObjects.skipTo(start);

		cmisObject = cmisObject.getPage();

		return cmisObject.iterator();
	}

	protected OperationContext getOperationContext(boolean fileEntriesFirst) {
		OperationContext operationContext = new OperationContextImpl();

		if (fileEntriesFirst) {
			operationContext.setOrderBy("cmis:objectTypeId ASC,cmis:name ASC");
		}
		else {
			operationContext.setOrderBy("cmis:objectTypeId DESC,cmis:name ASC");
		}

		return operationContext;
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

		Map<String, String> parameter = new HashMap<String, String>();

		parameter.put(SessionParameter.USER, login);
		parameter.put(SessionParameter.PASSWORD, password);
		parameter.put(SessionParameter.LOCALE_ISO3166_COUNTRY,
			LocaleUtil.getDefault().getCountry());
		parameter.put(SessionParameter.LOCALE_ISO639_LANGUAGE,
			LocaleUtil.getDefault().getLanguage());

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		if (typeSettingsProperties.containsKey(ATOMPUB_URL)) {
			parameter.put(
				SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

			putParameter(parameter, SessionParameter.ATOMPUB_URL, ATOMPUB_URL);
			putParameter(
				parameter, SessionParameter.REPOSITORY_ID, REPOSITORY_ID);
		}
		else {
			parameter.put(
				SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());

			putParameter(
				parameter, SessionParameter.REPOSITORY_ID, REPOSITORY_ID);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_ACL_SERVICE,
				WEBSERVICES_ACL_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_DISCOVERY_SERVICE,
				WEBSERVICES_DISCOVERY_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_MULTIFILING_SERVICE,
				WEBSERVICES_MULTIFILING_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_NAVIGATION_SERVICE,
				WEBSERVICES_NAVIGATION_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_OBJECT_SERVICE,
				WEBSERVICES_OBJECT_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_POLICY_SERVICE,
				WEBSERVICES_POLICY_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE,
				WEBSERVICES_RELATIONSHIP_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_REPOSITORY_SERVICE,
				WEBSERVICES_REPOSITORY_SERVICE);
			putParameter(
				parameter, SessionParameter.WEBSERVICES_VERSIONING_SERVICE,
				WEBSERVICES_VERSIONING_SERVICE);
		}

		return _sessionFactory.createSession(parameter);
	}

	protected void getSubfolderIds(
			List<Long> subfolderIds,
			List<Tree<FileableCmisObject>> fileableCmisObjects)
		throws SystemException {

		for (Tree<FileableCmisObject> tree : fileableCmisObjects) {
			subfolderIds.add(toRepositoryEntryId(tree.getItem().getId()));

			getSubfolderIds(subfolderIds, tree.getChildren());
		}
	}

	protected boolean hasTitle(
		org.apache.chemistry.opencmis.client.api.Folder cmisFolder,
		String title) {

		OperationContext operationContext = getOperationContext(true);

		cmisFolder.getChildren(operationContext);

		ItemIterable<CmisObject> cmisObjects = cmisFolder.getChildren(
			operationContext);

		Iterator<CmisObject> itr = cmisObjects.iterator();

		boolean documentsSection = false;
		boolean foldersSection = false;

		while (itr.hasNext()) {
			CmisObject cmisObject = itr.next();

			String cmisObjectName = cmisObject.getName();

			if (cmisObject instanceof Document) {
				if (cmisObjectName.equals(title)) {
					return true;
				}

				documentsSection = true;
			}
			else if (cmisObject instanceof
						org.apache.chemistry.opencmis.client.api.Folder) {

				if (cmisObjectName.equals(title)) {
					return true;
				}

				foldersSection = true;
			}
			else if (documentsSection && foldersSection) {
				break;
			}
		}

		return false;
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

	protected ObjectId toFolderId(Session session, long folderId)
		throws PortalException, SystemException {

		ObjectId objectId = null;

		DLFolder dlFolder = DLFolderUtil.fetchByPrimaryKey(folderId);

		if (dlFolder != null) {
			if (!dlFolder.isMountPoint()) {
				throw new RepositoryException(
					"CMIS repository should not be used for folder ID " +
						folderId);
			}

			objectId = new ObjectIdImpl(
				session.getRepositoryInfo().getRootFolderId());
		}
		else {
			RepositoryEntry repositoryEntry =
				RepositoryEntryUtil.fetchByPrimaryKey(folderId);

			if (repositoryEntry == null) {
				throw new NoSuchFolderException(
					"No CMIS folder with {folderId=" + folderId + "}");
			}

			objectId = new ObjectIdImpl(repositoryEntry.getMappedId());
		}

		return objectId;
	}

	private SessionFactory _sessionFactory = SessionFactoryImpl.newInstance();

	private static Log _log = LogFactoryUtil.getLog(CMISRepository.class);

}