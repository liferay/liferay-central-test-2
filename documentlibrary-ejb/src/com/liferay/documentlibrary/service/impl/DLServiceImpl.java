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

package com.liferay.documentlibrary.service.impl;

import com.liferay.documentlibrary.DirectoryNameException;
import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.SourceFileNameException;
import com.liferay.documentlibrary.service.spring.DLLocalServiceUtil;
import com.liferay.documentlibrary.service.spring.DLService;
import com.liferay.documentlibrary.util.DLUtil;
import com.liferay.documentlibrary.util.Indexer;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.jcr.JCRConstants;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.lucene.IndexerException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael Young
 *
 */
public class DLServiceImpl implements DLService {

	public static final String GROUP_NAME = DLServiceImpl.class.getName();

	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static final String VERSION = "_VERSION_";

	public static final double DEFAULT_VERSION = 1.0;

	public void addDirectory(
			String companyId, String repositoryId, String dirName)
		throws PortalException, SystemException {

		if ((dirName == null || dirName.equals("/")) ||
			(dirName.indexOf("\\\\") != -1) ||
			(dirName.indexOf("//") != -1) ||
			(dirName.indexOf(":") != -1) ||
			(dirName.indexOf("*") != -1) ||
			(dirName.indexOf("?") != -1) ||
			(dirName.indexOf("\"") != -1) ||
			(dirName.indexOf("<") != -1) ||
			(dirName.indexOf(">") != -1) ||
			(dirName.indexOf("|") != -1) ||
			(dirName.indexOf("&") != -1) ||
			(dirName.indexOf("[") != -1) ||
			(dirName.indexOf("]") != -1) ||
			(dirName.indexOf("'") != -1)) {

			throw new DirectoryNameException(dirName);
		}

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);

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
			String companyId, String portletId, String groupId,
			String repositoryId, String fileName, byte[] byteArray)
		throws PortalException, SystemException {

		if ((fileName.indexOf("\\\\") != -1) ||
			(fileName.indexOf("//") != -1) ||
			(fileName.indexOf(":") != -1) ||
			(fileName.indexOf("*") != -1) ||
			(fileName.indexOf("?") != -1) ||
			(fileName.indexOf("\"") != -1) ||
			(fileName.indexOf("<") != -1) ||
			(fileName.indexOf(">") != -1) ||
			(fileName.indexOf("|") != -1) ||
			(fileName.indexOf("&") != -1) ||
			(fileName.indexOf("[") != -1) ||
			(fileName.indexOf("]") != -1) ||
			(fileName.indexOf("'") != -1)) {

			throw new FileNameException(fileName);
		}

		boolean validFileExtension = false;

		String[] fileExtensions =
			PropsUtil.getArray(PropsUtil.DL_FILE_EXTENSIONS);

		for (int i = 0; i < fileExtensions.length; i++) {
			if (StringPool.STAR.equals(fileExtensions[i]) ||
				StringUtil.endsWith(fileName, fileExtensions[i])) {

				validFileExtension = true;

				break;
			}
		}

		if (!validFileExtension) {
			throw new FileNameException(fileName);
		}

		long fileMaxSize = GetterUtil.getLong(
			PropsUtil.get(PropsUtil.DL_FILE_MAX_SIZE));

		if ((fileMaxSize > 0) &&
			((byteArray == null) || (byteArray.length > fileMaxSize))) {

			throw new FileSizeException(fileName);
		}

		InputStream is = new BufferedInputStream(
			new ByteArrayInputStream(byteArray));

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);

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
				contentNode.setProperty(JCRConstants.JCR_DATA, is);
				contentNode.setProperty(
					JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

				session.save();

				Version version = contentNode.checkin();

				contentNode.getVersionHistory().addVersionLabel(
					version.getName(), Double.toString(DEFAULT_VERSION), false);

				Indexer.addFile(
					companyId, portletId, groupId, repositoryId, fileName);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
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
			String companyId, String portletId, String repositoryId,
			String dirName)
		throws PortalException, SystemException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);
			Node dirNode = repositoryNode.getNode(dirName);

			_deleteDirectory(companyId, portletId, repositoryId, dirNode);

			dirNode.remove();

			session.save();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchDirectoryException(dirName);
		}
		catch (RepositoryException e) {
			throw new PortalException(e);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void deleteFile(
			String companyId, String portletId, String repositoryId,
			String fileName)
		throws PortalException, SystemException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);

			Indexer.deleteFile(companyId, portletId, repositoryId, fileName);

			fileNode.remove();

			session.save();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
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
			String companyId, String portletId, String repositoryId,
			String fileName, double versionNumber)
		throws PortalException, SystemException {

		String versionLabel = String.valueOf(versionNumber);

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			VersionHistory versionHistory = contentNode.getVersionHistory();

			Version version = versionHistory.getVersionByLabel(versionLabel);

			versionHistory.removeVersion(version.getName());

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

	public byte[] getFile(
			String companyId, String repositoryId, String fileName)
		throws PortalException, SystemException {

		byte[] bytes = null;

		try {
			InputStream is = DLLocalServiceUtil.getFileAsStream(
				companyId, repositoryId, fileName);

			bytes = FileUtil.getBytes(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return bytes;
	}

	public byte[] getFile(
			String companyId, String repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		byte[] bytes = null;

		try {
			InputStream is = DLLocalServiceUtil.getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);

			bytes = FileUtil.getBytes(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return bytes;
	}

	public String[] getFileNames(
			String companyId, String repositoryId, String dirName)
		throws PortalException, SystemException {

		List fileNames = new ArrayList();

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);
			Node dirNode = repositoryNode.getNode(dirName);

			NodeIterator itr = dirNode.getNodes();

			while (itr.hasNext()) {
				Node node = (Node)itr.next();

				if (node.getPrimaryNodeType().getName().equals(
						JCRConstants.NT_FILE)) {

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

		return (String[])fileNames.toArray(new String[0]);
	}

	public long getFileSize(
			String companyId, String repositoryId, String fileName)
		throws PortalException, SystemException {

		long size;

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node contentNode = DLLocalServiceUtil.getFileContentNode(
				session, companyId, repositoryId, fileName, 0);

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

	public void reIndex(String[] ids) throws SystemException {
		try {
			Indexer indexer = new Indexer();

			indexer.reIndex(ids);
		}
		catch (IndexerException ie) {
			throw new SystemException(ie);
		}
	}

	public void updateFile(
			String companyId, String portletId, String groupId,
			String repositoryId, String fileName, double versionNumber,
			String sourceFileName, byte[] byteArray)
		throws PortalException, SystemException {

		String versionLabel = String.valueOf(versionNumber);

		int pos = fileName.lastIndexOf(StringPool.PERIOD);

		if (pos != -1) {
			String fileNameExtension =
				fileName.substring(pos, fileName.length());

			pos = sourceFileName.lastIndexOf(StringPool.PERIOD);

			if (pos == -1) {
				throw new SourceFileNameException(sourceFileName);
			}
			else {
				String sourceFileNameExtension =
					sourceFileName.substring(pos, sourceFileName.length());

				if (!fileNameExtension.equalsIgnoreCase(
						sourceFileNameExtension)) {

					throw new SourceFileNameException(sourceFileName);
				}
			}
		}

		long fileMaxSize = GetterUtil.getLong(
			PropsUtil.get(PropsUtil.DL_FILE_MAX_SIZE));

		if ((fileMaxSize > 0) &&
			((byteArray == null) || (byteArray.length > fileMaxSize))) {

			throw new FileSizeException(fileName);
		}

		InputStream is = new BufferedInputStream(
			new ByteArrayInputStream(byteArray));

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			contentNode.checkout();

			contentNode.setProperty(JCRConstants.JCR_MIME_TYPE, "text/plain");
			contentNode.setProperty(JCRConstants.JCR_DATA, is);
			contentNode.setProperty(
				JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

			session.save();

			Version version = contentNode.checkin();

			contentNode.getVersionHistory().addVersionLabel(
				version.getName(), versionLabel, false);

			Indexer.updateFile(
				companyId, portletId, groupId, repositoryId, fileName);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
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

	private void _deleteDirectory(
			String companyId, String portletId, String repositoryId,
			Node dirNode)
		throws IOException {

		try {
			NodeIterator itr = dirNode.getNodes();

			while (itr.hasNext()) {
				Node node = (Node)itr.next();

				String primaryNodeTypeName =
					node.getPrimaryNodeType().getName();

				if (primaryNodeTypeName.equals(JCRConstants.NT_FOLDER)) {
					_deleteDirectory(companyId, portletId, repositoryId, node);
				}
				else if (primaryNodeTypeName.equals(JCRConstants.NT_FILE)) {
					Indexer.deleteFile(
						companyId, portletId, repositoryId, node.getName());
				}
			}

			Indexer.deleteFile(
				companyId, portletId, repositoryId, dirNode.getName());
		}
		catch (RepositoryException e) {
			_log.error(e);
		}
	}

	private static Log _log = LogFactory.getLog(DLServiceImpl.class);

}