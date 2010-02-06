/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.model.FileModel;
import com.liferay.portal.PortalException;
import com.liferay.portal.cmis.CMISException;
import com.liferay.portal.cmis.CMISUtil;
import com.liferay.portal.cmis.model.CMISConstants;
import com.liferay.portal.cmis.model.CMISObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.ServiceContext;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;

/**
 * <a href="CMISHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class CMISHook extends BaseHook {

	public CMISHook() {
		try {
			String version = CMISUtil.verifyRepository();

			if (_log.isInfoEnabled()) {
				_log.info("CMIS Service is running version " + version);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException {

		Entry repositoryFolderEntry = getRepositoryFolderEntry(
			companyId, repositoryId);

		CMISUtil.createFolder(repositoryFolderEntry, dirName);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException {

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			DEFAULT_VERSION, null, fileEntryId, properties, modifiedDate,
			serviceContext, is);
	}

	public void checkRoot(long companyId) {
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException {

		Entry repositoryFolderEntry = getRepositoryFolderEntry(
			companyId, repositoryId);

		Entry directory = CMISUtil.getFolder(repositoryFolderEntry, dirName);

		if (directory != null) {
			CMISUtil.delete(directory);
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException {

		Entry versioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, fileName, false);

		if (versioningFolderEntry == null) {
			throw new NoSuchFileException();
		}

		CMISUtil.delete(versioningFolderEntry);

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			FileModel.class);

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(companyId);
		fileModel.setFileName(fileName);
		fileModel.setPortletId(portletId);
		fileModel.setRepositoryId(repositoryId);

		indexer.delete(fileModel);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, double versionNumber)
		throws PortalException {

		Entry fileEntry = getVersionedFileEntry(
			companyId, repositoryId, fileName, versionNumber);

		CMISUtil.delete(fileEntry);

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			FileModel.class);

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(companyId);
		fileModel.setFileName(fileName);
		fileModel.setPortletId(portletId);
		fileModel.setRepositoryId(repositoryId);

		indexer.delete(fileModel);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException {

		if (versionNumber == 0) {
			versionNumber = getHeadVersionNumber(
				companyId, repositoryId, fileName);
		}

		Entry fileEntry = getVersionedFileEntry(
			companyId, repositoryId, fileName, versionNumber);

		return CMISUtil.getInputStream(fileEntry);
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		Entry repositoryFolderEntry = getRepositoryFolderEntry(
			companyId, repositoryId);

		List<String> fileNames = CMISUtil.getFolders(repositoryFolderEntry);

		return fileNames.toArray(new String[fileNames.size()]);
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		double versionNumber = getHeadVersionNumber(
			companyId, repositoryId, fileName);

		Entry fileEntry = getVersionedFileEntry(
			companyId, repositoryId, fileName, versionNumber);

		CMISObject cmisObject = fileEntry.getFirstChild(_cmisConstants.OBJECT);

		return cmisObject.getContentStreamLength();
	}

	public double getHeadVersionNumber(
			long companyId, long repositoryId, String dirName)
		throws CMISException, NoSuchFileException {

		Entry versioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, dirName, false);

		if (versioningFolderEntry == null) {
			throw new NoSuchFileException();
		}

		List<String> versionNumbers = CMISUtil.getFolders(
			versioningFolderEntry);

		double headVersionNumber = DEFAULT_VERSION;

		for (int i = 0; i < versionNumbers.size(); i++) {
			double versionNumber = GetterUtil.getDouble(versionNumbers.get(i));

			if (versionNumber > headVersionNumber) {
				headVersionNumber = versionNumber;
			}
		}

		return headVersionNumber;
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException {

		Entry versioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, fileName, true);

		Link link = versioningFolderEntry.getLink(
			_cmisConstants.LINK_DESCENDANTS);

		String url = link.getHref().toString();

		Entry fileEntry = CMISUtil.getEntry(
			url, String.valueOf(versionNumber),
			_cmisConstants.BASE_TYPE_DOCUMENT);

		if (fileEntry == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public void move(String srcDir, String destDir) {
	}

	public void reindex(String[] ids) {
		try {
			long companyId = GetterUtil.getLong(ids[0]);
			String portletId = ids[1];
			long groupId = GetterUtil.getLong(ids[2]);
			long repositoryId = GetterUtil.getLong(ids[3]);

			Entry repositoryFolderEntry = getRepositoryFolderEntry(
				companyId, repositoryId);

			List<String> fileNames = CMISUtil.getFolders(repositoryFolderEntry);

			for (String fileName : fileNames) {
				try {
					Indexer indexer = IndexerRegistryUtil.getIndexer(
						FileModel.class);

					FileModel fileModel = new FileModel();

					fileModel.setCompanyId(companyId);
					fileModel.setFileName(fileName);
					fileModel.setGroupId(groupId);
					fileModel.setPortletId(portletId);
					fileModel.setRepositoryId(repositoryId);

					Document document = indexer.getDocument(fileModel);

					SearchEngineUtil.updateDocument(
						companyId, document.get(Field.UID), document);
				}
				catch (Exception e) {
					_log.error("Reindexing " + fileName, e);
				}
			}
		}
		catch (CMISException cmise) {
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException {

		Entry oldVersioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, fileName, true);
		Entry newVersioningFolderEntry = getVersioningFolderEntry(
			companyId, newRepositoryId, fileName, true);

		List<String> fileNames = CMISUtil.getFolders(oldVersioningFolderEntry);

		for (String curFileName : fileNames) {
			Entry entry = CMISUtil.getDocument(
				oldVersioningFolderEntry, curFileName);

			InputStream is = CMISUtil.getInputStream(entry);

			CMISUtil.createDocument(newVersioningFolderEntry, curFileName, is);
		}

		CMISUtil.delete(oldVersioningFolderEntry);

		Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

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

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException {

		Entry versioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, fileName, true);

		Link link = versioningFolderEntry.getLink(
			_cmisConstants.LINK_DESCENDANTS);

		String url = link.getHref().toString();

		String title = String.valueOf(versionNumber);

		Entry fileEntry = CMISUtil.getEntry(
			url, title, _cmisConstants.BASE_TYPE_DOCUMENT);

		if (fileEntry != null) {
			throw new DuplicateFileException();
		}

		fileEntry = CMISUtil.createDocument(url, title, is);

		Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

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

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String newFileName, boolean reindex)
		throws PortalException {

		Entry oldVersioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, fileName, true);
		Entry newVersioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, newFileName, true);

		List<String> fileNames = CMISUtil.getFolders(oldVersioningFolderEntry);

		for (String curFileName : fileNames) {
			Entry entry = CMISUtil.getDocument(
				oldVersioningFolderEntry, curFileName);

			InputStream is = CMISUtil.getInputStream(entry);

			CMISUtil.createDocument(newVersioningFolderEntry, curFileName, is);
		}

		CMISUtil.delete(oldVersioningFolderEntry);

		if (reindex) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

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

	protected Entry getCompanyFolderEntry(long companyId) throws CMISException {
		String title = String.valueOf(companyId);

		Entry companyFolderEntry = CMISUtil.getFolder(title);

		if (companyFolderEntry == null) {
			companyFolderEntry = CMISUtil.createFolder(title);
		}

		return companyFolderEntry;
	}

	protected Entry getRepositoryFolderEntry(long companyId, long repositoryId)
		throws CMISException {

		Entry companyFolderEntry = getCompanyFolderEntry(companyId);

		Link link = companyFolderEntry.getLink(_cmisConstants.LINK_DESCENDANTS);

		String url = link.getHref().toString();

		String title = String.valueOf(repositoryId);

		Entry repositoryFolderEntry = CMISUtil.getFolder(url, title);

		if (repositoryFolderEntry == null) {
			repositoryFolderEntry = CMISUtil.createFolder(url, title);
		}

		return repositoryFolderEntry;
	}

	protected Entry getVersionedFileEntry(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws CMISException, NoSuchFileException {

		Entry versioningFolderEntry = getVersioningFolderEntry(
			companyId, repositoryId, fileName, false);

		if (versioningFolderEntry == null) {
			throw new NoSuchFileException();
		}

		Link link = versioningFolderEntry.getLink(
			_cmisConstants.LINK_DESCENDANTS);

		String url = link.getHref().toString();

		Entry fileEntry = CMISUtil.getEntry(
			url, String.valueOf(versionNumber),
			_cmisConstants.BASE_TYPE_DOCUMENT);

		if (fileEntry == null) {
			throw new NoSuchFileException();
		}

		return fileEntry;
	}

	protected Entry getVersioningFolderEntry(
			long companyId, long repositoryId, String fileName, boolean create)
		throws CMISException {

		Entry repositoryFolderEntry = getRepositoryFolderEntry(
			companyId, repositoryId);

		Link link = repositoryFolderEntry.getLink(
			_cmisConstants.LINK_DESCENDANTS);

		String url = link.getHref().toString();

		Entry versioningFolderEntry = CMISUtil.getEntry(
			url, fileName, _cmisConstants.BASE_TYPE_FOLDER);

		if (create && (versioningFolderEntry == null)) {
			versioningFolderEntry = CMISUtil.createFolder(url, fileName);
		}

		return versioningFolderEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISHook.class);

	private static CMISConstants _cmisConstants = CMISConstants.getInstance();

}