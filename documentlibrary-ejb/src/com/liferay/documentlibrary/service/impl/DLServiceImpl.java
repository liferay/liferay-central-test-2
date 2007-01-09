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

package com.liferay.documentlibrary.service.impl;

import com.liferay.documentlibrary.DirectoryNameException;
import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.documentlibrary.service.DLService;
import com.liferay.documentlibrary.util.DLUtil;
import com.liferay.documentlibrary.util.Indexer;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.jcr.JCRConstants;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	public static final long FILE_MAX_SIZE = GetterUtil.getLong(
		PropsUtil.get(PropsUtil.DL_FILE_MAX_SIZE));

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
		String companyId, String portletId, long groupId,
		String repositoryId, String fileName, File file)
	throws PortalException, SystemException {

		if ((FILE_MAX_SIZE > 0) &&
			((file == null) || (file.length() > FILE_MAX_SIZE))) {
			throw new FileSizeException(fileName);
		}

		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException(fileName);
		}

		DLLocalServiceUtil.addFile(
			companyId, portletId, groupId, repositoryId, fileName, is);
	}

	public void addFile(
		String companyId, String portletId, long groupId,
		String repositoryId, String fileName, byte[] byteArray)
		throws PortalException, SystemException {

		if ((FILE_MAX_SIZE > 0) &&
			((byteArray == null) || (byteArray.length > FILE_MAX_SIZE))) {
			throw new FileSizeException(fileName);
		}

		InputStream is =
			new BufferedInputStream(new ByteArrayInputStream(byteArray));

		DLLocalServiceUtil.addFile(
			companyId, portletId, groupId, repositoryId, fileName, is);
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

			Node contentNode = DLUtil.getFileContentNode(
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
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	public void updateFile(
		String companyId, String portletId, long groupId,
		String repositoryId, String fileName, double versionNumber,
		String sourceFileName, File file)
	throws PortalException, SystemException {

		if ((FILE_MAX_SIZE > 0) &&
			((file == null) || (file.length() > FILE_MAX_SIZE))) {
			throw new FileSizeException(fileName);
		}

		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException(fileName);
		}

		DLLocalServiceUtil.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, is);
	}

	public void updateFile(
		String companyId, String portletId, long groupId,
		String repositoryId, String fileName, double versionNumber,
		String sourceFileName, byte[] byteArray)
		throws PortalException, SystemException {

		if ((FILE_MAX_SIZE > 0) &&
			((byteArray == null) || (byteArray.length > FILE_MAX_SIZE))) {
			throw new FileSizeException(fileName);
		}

		InputStream is =
			new BufferedInputStream(new ByteArrayInputStream(byteArray));

		DLLocalServiceUtil.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, is);
	}

	public void updateFile(
			String companyId, String portletId, long groupId,
			String repositoryId, String newRepositoryId, String fileName)
		throws PortalException, SystemException {

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			Node newRepositoryNode = DLUtil.getFolderNode(
				rootNode, newRepositoryId);

			if (newRepositoryNode.hasNode(fileName)) {
				throw new DuplicateFileException(fileName);
			}
			else {
				Node newFileNode = newRepositoryNode.addNode(
					fileName, JCRConstants.NT_FILE);

				Node newContentNode = newFileNode.addNode(
					JCRConstants.JCR_CONTENT, JCRConstants.NT_RESOURCE);

				VersionHistory versionHistory = contentNode.getVersionHistory();

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
						newContentNode.checkout();
					}

					newContentNode.setProperty(
						JCRConstants.JCR_MIME_TYPE, "text/plain");
					newContentNode.setProperty(
						JCRConstants.JCR_DATA,
						frozenContentNode.getProperty(
							JCRConstants.JCR_DATA).getStream());
					newContentNode.setProperty(
						JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

					session.save();

					Version newVersion = newContentNode.checkin();

					newContentNode.getVersionHistory().addVersionLabel(
						newVersion.getName(), versionLabels[i], false);
				}

				fileNode.remove();

				session.save();

				try {
					Indexer.deleteFile(
						companyId, portletId, repositoryId, fileName);
				}
				catch (IOException ioe) {
				}

				Indexer.addFile(
					companyId, portletId, new Long(groupId), newRepositoryId,
					fileName);
			}
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