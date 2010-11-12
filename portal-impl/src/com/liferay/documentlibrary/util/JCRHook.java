/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.model.FileModel;
import com.liferay.portal.jcr.JCRConstants;
import com.liferay.portal.jcr.JCRFactory;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import javax.jcr.version.VersionManager;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Young
 * @author Brian Wing Shun Chan
 */
public class JCRHook extends BaseHook {

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (repositoryNode.hasNode(dirName)) {
				throw new DuplicateDirectoryException(dirName);
			}
			else {
				String[] dirNameArray = StringUtil.split(dirName, "/");

				Node dirNode = repositoryNode;

				for (int i = 0; i < dirNameArray.length; i++) {
					if (Validator.isNotNull(dirNameArray[i])) {
						if (dirNode.hasNode(dirNameArray[i])) {
							dirNode = dirNode.getNode(dirNameArray[i]);
						}
						else {
							dirNode = dirNode.addNode(
								dirNameArray[i], JCRConstants.NT_FOLDER);
						}
					}
				}

				session.save();
			}
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (repositoryNode.hasNode(fileName)) {
				throw new DuplicateFileException(fileName);
			}
			else {
				Node fileNode = repositoryNode.addNode(
					fileName, JCRConstants.NT_FILE);

				Node contentNode = fileNode.addNode(
					JCRConstants.JCR_CONTENT, JCRConstants.NT_RESOURCE);

				contentNode.addMixin(JCRConstants.MIX_VERSIONABLE);
				contentNode.setProperty(
					JCRConstants.JCR_MIME_TYPE, "text/plain");

				ValueFactory valueFactory = session.getValueFactory();

				Binary binary = valueFactory.createBinary(is);

				contentNode.setProperty(JCRConstants.JCR_DATA, binary);

				contentNode.setProperty(
					JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

				session.save();

				Version version = versionManager.checkin(contentNode.getPath());

				VersionHistory versionHistory =
					versionManager.getVersionHistory(contentNode.getPath());

				versionHistory.addVersionLabel(
					version.getName(), DEFAULT_VERSION, false);

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					FileModel.class);

				FileModel fileModel = new FileModel();

				fileModel.setAssetCategoryIds(
					serviceContext.getAssetCategoryIds());
				fileModel.setAssetTagNames(serviceContext.getAssetTagNames());
				fileModel.setCompanyId(companyId);
				fileModel.setFileEntryId(fileEntryId);
				fileModel.setFileName(fileName);
				fileModel.setGroupId(groupId);
				fileModel.setModifiedDate(modifiedDate);
				fileModel.setPortletId(portletId);
				fileModel.setProperties(properties);
				fileModel.setRepositoryId(repositoryId);

				indexer.reindex(fileModel);
			}
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void checkRoot(long companyId) throws SystemException {
		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			getRootNode(session, companyId);

			session.save();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node dirNode = repositoryNode.getNode(dirName);

			deleteDirectory(companyId, portletId, repositoryId, dirNode);

			dirNode.remove();

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchDirectoryException(dirName);
		}
		catch (RepositoryException re) {
			String message = GetterUtil.getString(re.getMessage());

			if (message.contains("failed to resolve path")) {
				throw new NoSuchDirectoryException(dirName);
			}
			else {
				throw new PortalException(re);
			}
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		Session session = null;

		// A bug in Jackrabbit requires us to create a dummy node and delete the
		// version tree manually to successfully delete a file

		// Create a dummy node

		try {
			session = JCRFactoryUtil.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			versionManager.checkout(contentNode.getPath());

			contentNode.setProperty(JCRConstants.JCR_MIME_TYPE, "text/plain");
			contentNode.setProperty(JCRConstants.JCR_DATA, "");
			contentNode.setProperty(
				JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

			session.save();

			Version version = versionManager.checkin(contentNode.getPath());

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			versionHistory.addVersionLabel(version.getName(), "0.0", false);
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(fileName);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		// Delete version tree

		try {
			session = JCRFactoryUtil.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			VersionIterator itr = versionHistory.getAllVersions();

			while (itr.hasNext()) {
				Version version = itr.nextVersion();

				if (itr.getPosition() == itr.getSize()) {
					break;
				}
				else {
					if (!StringUtils.equals(
							JCRConstants.JCR_ROOT_VERSION, version.getName())) {

						versionHistory.removeVersion(version.getName());
					}
				}
			}

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(fileName);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		// Delete file

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setCompanyId(companyId);
			fileModel.setFileName(fileName);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			indexer.delete(fileModel);

			fileNode.remove();

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(fileName);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException, SystemException {

		String versionLabel = versionNumber;

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			if (!versionHistory.hasVersionLabel(versionLabel)) {
				throw new NoSuchFileException(
					"{fileName=" + fileName + ", versionLabel=" +
						versionLabel + "}");
			}

			Version version = versionHistory.getVersionByLabel(versionLabel);

			versionHistory.removeVersion(version.getName());

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(
				"{fileName=" + fileName + ", versionLabel=" +
					versionLabel + "}");
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		InputStream is = null;

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node contentNode = getFileContentNode(
				session, companyId, repositoryId, fileName, versionNumber);

			Property property = contentNode.getProperty(JCRConstants.JCR_DATA);

			Value value = property.getValue();

			Binary binary = value.getBinary();

			is = new UnsyncBufferedInputStream(binary.getStream());
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		return is;
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		List<String> fileNames = new ArrayList<String>();

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node dirNode = repositoryNode.getNode(dirName);

			NodeIterator itr = dirNode.getNodes();

			while (itr.hasNext()) {
				Node node = (Node)itr.next();

				NodeType primaryNodeType = node.getPrimaryNodeType();

				String primaryNodeTypeName = primaryNodeType.getName();

				if (primaryNodeTypeName.equals(JCRConstants.NT_FILE)) {
					fileNames.add(dirName + "/" + node.getName());
				}
			}
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchDirectoryException(dirName);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		long size;

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node contentNode = getFileContentNode(
				session, companyId, repositoryId, fileName, StringPool.BLANK);

			size = contentNode.getProperty(JCRConstants.JCR_DATA).getLength();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		return size;
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		try {
			getFileContentNode(
				companyId, repositoryId, fileName, versionNumber);
		}
		catch (NoSuchFileException nsfe) {
			return false;
		}

		return true;
	}

	public void move(String srcDir, String destDir) throws SystemException {
		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			session.move(srcDir, destDir);

			session.save();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void reindex(String[] ids) throws SearchException {
		long companyId = GetterUtil.getLong(ids[0]);
		String portletId = ids[1];
		long groupId = GetterUtil.getLong(ids[2]);
		long repositoryId = GetterUtil.getLong(ids[3]);

		Collection<Document> documents = new ArrayList<Document>();

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			NodeIterator itr = repositoryNode.getNodes();

			while (itr.hasNext()) {
				Node node = (Node)itr.next();

				NodeType primaryNodeType = node.getPrimaryNodeType();

				String primaryNodeTypeName = primaryNodeType.getName();

				if (primaryNodeTypeName.equals(JCRConstants.NT_FILE)) {
					Indexer indexer = IndexerRegistryUtil.getIndexer(
						FileModel.class);

					FileModel fileModel = new FileModel();

					fileModel.setCompanyId(companyId);
					fileModel.setFileName(node.getName());
					fileModel.setGroupId(groupId);
					fileModel.setPortletId(portletId);
					fileModel.setRepositoryId(repositoryId);

					Document document = indexer.getDocument(fileModel);

					if (document == null) {
						continue;
					}

					documents.add(document);
				}
			}
		}
		catch (Exception e1) {
			throw new SearchException(e1);
		}
		finally {
			try {
				if (session != null) {
					session.logout();
				}
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException, SystemException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			Node newRepositoryNode = getFolderNode(rootNode, newRepositoryId);

			if (newRepositoryNode.hasNode(fileName)) {
				throw new DuplicateFileException(fileName);
			}
			else {
				Node newFileNode = newRepositoryNode.addNode(
					fileName, JCRConstants.NT_FILE);

				Node newContentNode = newFileNode.addNode(
					JCRConstants.JCR_CONTENT, JCRConstants.NT_RESOURCE);

				VersionHistory versionHistory =
					versionManager.getVersionHistory(contentNode.getPath());

				String[] versionLabels = versionHistory.getVersionLabels();

				for (int i = (versionLabels.length - 1); i >= 0; i--) {
					Version version = versionHistory.getVersionByLabel(
						versionLabels[i]);

					Node frozenContentNode = version.getNode(
						JCRConstants.JCR_FROZEN_NODE);

					if (i == (versionLabels.length - 1)) {
						newContentNode.addMixin(JCRConstants.MIX_VERSIONABLE);
					}
					else {
						versionManager.checkout(newContentNode.getPath());
					}

					newContentNode.setProperty(
						JCRConstants.JCR_MIME_TYPE, "text/plain");

					copyBinaryProperty(
						frozenContentNode, newContentNode,
						JCRConstants.JCR_DATA);

					newContentNode.setProperty(
						JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

					session.save();

					Version newVersion = versionManager.checkin(
						newContentNode.getPath());

					VersionHistory newVersionHistory =
						versionManager.getVersionHistory(
							newContentNode.getPath());

					newVersionHistory.addVersionLabel(
						newVersion.getName(), versionLabels[i],
						PropsValues.DL_HOOK_JCR_MOVE_VERSION_LABELS);
				}

				fileNode.remove();

				session.save();

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					FileModel.class);

				FileModel fileModel = new FileModel();

				fileModel.setCompanyId(companyId);
				fileModel.setFileName(fileName);
				fileModel.setPortletId(portletId);
				fileModel.setRepositoryId(repositoryId);

				indexer.delete(fileModel);

				fileModel.setRepositoryId(newRepositoryId);
				fileModel.setGroupId(groupId);

				indexer.reindex(fileModel);
			}
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(fileName);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException, SystemException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			Node newFileNode = repositoryNode.addNode(
				newFileName, JCRConstants.NT_FILE);

			Node newContentNode = newFileNode.addNode(
				JCRConstants.JCR_CONTENT, JCRConstants.NT_RESOURCE);

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			String[] versionLabels = versionHistory.getVersionLabels();

			for (int i = (versionLabels.length - 1); i >= 0; i--) {
				Version version = versionHistory.getVersionByLabel(
					versionLabels[i]);

				Node frozenContentNode = version.getNode(
					JCRConstants.JCR_FROZEN_NODE);

				if (i == (versionLabels.length - 1)) {
					newContentNode.addMixin(JCRConstants.MIX_VERSIONABLE);
				}
				else {
					versionManager.checkout(newContentNode.getPath());
				}

				newContentNode.setProperty(
					JCRConstants.JCR_MIME_TYPE, "text/plain");

				copyBinaryProperty(
					frozenContentNode, newContentNode,
					JCRConstants.JCR_DATA);

				newContentNode.setProperty(
					JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

				session.save();

				Version newVersion = versionManager.checkin(
					newContentNode.getPath());

				VersionHistory newVersionHistory =
					versionManager.getVersionHistory(newContentNode.getPath());

				newVersionHistory.addVersionLabel(
					newVersion.getName(), versionLabels[i],
					PropsValues.DL_HOOK_JCR_MOVE_VERSION_LABELS);
			}

			fileNode.remove();

			session.save();

			if (reindex) {
				Indexer indexer = IndexerRegistryUtil.getIndexer(
					FileModel.class);

				FileModel fileModel = new FileModel();

				fileModel.setCompanyId(companyId);
				fileModel.setFileName(fileName);
				fileModel.setPortletId(portletId);
				fileModel.setRepositoryId(repositoryId);

				indexer.delete(fileModel);

				fileModel.setFileName(newFileName);
				fileModel.setGroupId(groupId);

				indexer.reindex(fileModel);
			}
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(fileName);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		String versionLabel = versionNumber;

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			versionManager.checkout(contentNode.getPath());

			contentNode.setProperty(JCRConstants.JCR_MIME_TYPE, "text/plain");

			ValueFactory valueFactory = session.getValueFactory();

			Binary binary = valueFactory.createBinary(is);

			contentNode.setProperty(JCRConstants.JCR_DATA, binary);

			contentNode.setProperty(
				JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

			session.save();

			Version version = versionManager.checkin(contentNode.getPath());

			VersionHistory versionHistory =
					versionManager.getVersionHistory(contentNode.getPath());

			versionHistory.addVersionLabel(
				version.getName(), versionLabel,
				PropsValues.DL_HOOK_JCR_MOVE_VERSION_LABELS);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setAssetCategoryIds(serviceContext.getAssetCategoryIds());
			fileModel.setAssetTagNames(serviceContext.getAssetTagNames());
			fileModel.setCompanyId(companyId);
			fileModel.setFileEntryId(fileEntryId);
			fileModel.setFileName(fileName);
			fileModel.setGroupId(groupId);
			fileModel.setModifiedDate(modifiedDate);
			fileModel.setPortletId(portletId);
			fileModel.setProperties(properties);
			fileModel.setRepositoryId(repositoryId);

			indexer.reindex(fileModel);
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(
				"{fileName=" + fileName + ", versionLabel=" + versionLabel +
					"}");
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	protected void copyBinaryProperty(Node fromNode, Node toNode, String name)
		throws RepositoryException {

		Property property = fromNode.getProperty(name);

		Binary binary = property.getBinary();

		toNode.setProperty(name, binary);
	}

	protected void deleteDirectory(
			long companyId, String portletId, long repositoryId, Node dirNode)
		throws SearchException {

		try {
			NodeIterator itr = dirNode.getNodes();

			FileModel fileModel = new FileModel();

			fileModel.setCompanyId(companyId);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			while (itr.hasNext()) {
				Node node = (Node)itr.next();

				NodeType primaryNodeType = node.getPrimaryNodeType();

				String primaryNodeTypeName = primaryNodeType.getName();

				if (primaryNodeTypeName.equals(JCRConstants.NT_FOLDER)) {
					deleteDirectory(companyId, portletId, repositoryId, node);
				}
				else if (primaryNodeTypeName.equals(JCRConstants.NT_FILE)) {
					fileModel.setFileName(node.getName());

					indexer.delete(fileModel);
				}
			}

			fileModel.setFileName(dirNode.getName());

			indexer.delete(fileModel);
		}
		catch (RepositoryException e) {
			_log.error(e);
		}
	}

	protected Node getFileContentNode(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		Node contentNode = null;

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			contentNode = getFileContentNode(
				session, companyId, repositoryId, fileName, versionNumber);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		return contentNode;
	}

	protected Node getFileContentNode(
			Session session, long companyId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException, SystemException {

		String versionLabel = versionNumber;

		Node contentNode = null;

		try {
			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);
			Node repositoryNode = getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			if (Validator.isNotNull(versionNumber)) {
				VersionHistory versionHistory =
					versionManager.getVersionHistory(contentNode.getPath());

				if (!versionHistory.hasVersionLabel(versionLabel)) {
					throw new NoSuchFileException(
						"{fileName=" + fileName + ", versionLabel=" +
							versionLabel + "}");
				}

				Version version = versionHistory.getVersionByLabel(
					versionLabel);

				contentNode = version.getNode(JCRConstants.JCR_FROZEN_NODE);
			}
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(
				"{fileName=" + fileName + ", versionLabel=" +
					versionLabel + "}");
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}

		return contentNode;
	}

	protected Node getFolderNode(Node node, long name)
		throws RepositoryException {

		return getFolderNode(node, String.valueOf(name));
	}

	protected Node getFolderNode(Node node, String name)
		throws RepositoryException {

		Node folderNode = null;

		if (node.hasNode(name)) {
			folderNode = node.getNode(name);
		}
		else {
			folderNode = node.addNode(name, JCRConstants.NT_FOLDER);
		}

		return folderNode;
	}

	protected Node getRootNode(Session session, long companyId)
		throws RepositoryException {

		Node companyNode = getFolderNode(session.getRootNode(), companyId);

		return getFolderNode(companyNode, JCRFactory.NODE_DOCUMENTLIBRARY);
	}

	private static Log _log = LogFactoryUtil.getLog(JCRHook.class);

}