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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.cmis.CMISException;
import com.liferay.portal.cmis.CMISUtil;
import com.liferay.portal.cmis.model.CMISConstants;
import com.liferay.portal.cmis.model.CMISObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.service.ServiceContext;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;

/**
 * <a href="CMISHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class CMISHook extends BaseHook {

	public CMISHook() {
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Entry repositoryFolder = getRepositoryFolder(companyId, repositoryId);

		CMISUtil.createFolder(repositoryFolder, dirName);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			DEFAULT_VERSION, null, fileEntryId, properties, modifiedDate,
			serviceContext, is);
	}

	public void checkRoot(long companyId) throws SystemException {
		// None
	}

	public void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Entry repositoryFolder = getRepositoryFolder(companyId, repositoryId);

		Entry directory = CMISUtil.getFolder(repositoryFolder, dirName);

		if (directory != null) {
			CMISUtil.delete(directory);
		}
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException {

		Entry versioningFolder =
			getVersioningFolder(companyId, repositoryId, fileName, false);

		if (versioningFolder == null) {
			throw new NoSuchFileException();
		}

		CMISUtil.delete(versioningFolder);

		Indexer.deleteFile(companyId, portletId, repositoryId, fileName);
	}

	public void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, double versionNumber)
		throws PortalException, SystemException {

		Entry fileEntry =
			getVersionedFile(companyId, repositoryId, fileName, versionNumber);

		CMISUtil.delete(fileEntry);

		Indexer.deleteFile(companyId, portletId, repositoryId, fileName);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		if (versionNumber == 0) {
			versionNumber = getHeadVersionNumber(
				companyId, repositoryId, fileName);
		}

		Entry fileEntry =
			getVersionedFile(companyId, repositoryId, fileName, versionNumber);

		return CMISUtil.getInputStream(fileEntry);
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Entry repositoryFolder = getRepositoryFolder(companyId, repositoryId);

		List<String> list = CMISUtil.getFolderList(repositoryFolder);

		return list.toArray(new String[0]);
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		double versionNumber =
			getHeadVersionNumber(companyId, repositoryId, fileName);

		Entry fileEntry =
			getVersionedFile(companyId, repositoryId, fileName, versionNumber);

		CMISObject cmisObject = fileEntry.getFirstChild(_constants.OBJECT);

		return cmisObject.getContentStreamLength();
	}

	public double getHeadVersionNumber(
			long companyId, long repositoryId, String dirName)
		throws CMISException, NoSuchFileException {

		Entry versioningFolder =
			getVersioningFolder(companyId, repositoryId, dirName, false);

		if (versioningFolder == null) {
			throw new NoSuchFileException();
		}

		List<String> list = CMISUtil.getFolderList(versioningFolder);

		List<Double> versions = new ArrayList<Double>();

		for (String entry : list) {
			versions.add(Double.parseDouble(entry));
		}

		versions = ListUtil.sort(versions);

		return versions.get(versions.size() - 1);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		String versionString = Double.toString(versionNumber);

		Entry versioningFolder =
			getVersioningFolder(companyId, repositoryId, fileName);

		Link link = versioningFolder.getLink(_constants.LINK_DESCENDANTS);

		String descendantsUrl = link.getHref().toString();

		Entry fileEntry = CMISUtil.getEntry(
			descendantsUrl, versionString, _constants.BASE_TYPE_DOCUMENT);

		if (fileEntry == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public void move(String srcDir, String destDir) throws SystemException {
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			long companyId = GetterUtil.getLong(ids[0]);
			String portletId = ids[1];
			long groupId = GetterUtil.getLong(ids[2]);
			long repositoryId = GetterUtil.getLong(ids[3]);

			Entry repositoryFolder =
				getRepositoryFolder(companyId, repositoryId);

			List<String> list = CMISUtil.getFolderList(repositoryFolder);

			for (String fileName : list) {
				try {
					Document doc = Indexer.getFileDocument(
						companyId, portletId, groupId, repositoryId, fileName);

					SearchEngineUtil.updateDocument(
						companyId, doc.get(Field.UID), doc);
				}
				catch (Exception e) {
					_log.error("Reindexing " + fileName, e);
				}
			}
		}
		catch (CMISException ce) {

		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		String versionString = Double.toString(versionNumber);

		Entry versioningFolder =
			getVersioningFolder(companyId, repositoryId, fileName);

		Link link = versioningFolder.getLink(_constants.LINK_DESCENDANTS);

		String descendantsUrl = link.getHref().toString();

		Entry fileEntry = CMISUtil.getEntry(
			descendantsUrl, versionString, _constants.BASE_TYPE_DOCUMENT);

		if (fileEntry != null) {
			throw new DuplicateFileException();
		}

		fileEntry = CMISUtil.createDocument(descendantsUrl, versionString, is);

		if (sourceFileName == null) {
			Indexer.addFile(
				companyId, portletId, groupId, repositoryId, fileName,
				fileEntryId, properties, modifiedDate,
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}
		else {
			Indexer.updateFile(
				companyId, portletId, groupId, repositoryId, fileName,
				fileEntryId, properties, modifiedDate,
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName, long fileEntryId)
		throws PortalException, SystemException {

		try {
			Entry versioningFolder =
				getVersioningFolder(companyId, repositoryId, fileName);
			Entry newVersioningFolder =
				getVersioningFolder(companyId, newRepositoryId, fileName);

			List<String> folderList = CMISUtil.getFolderList(versioningFolder);

			for (String title : folderList) {
				Entry document = CMISUtil.getDocument(versioningFolder, title);

				InputStream is = CMISUtil.getInputStream(document);

				CMISUtil.createDocument(newVersioningFolder, title, is);
			}

			CMISUtil.delete(versioningFolder);

			try {
				Indexer.deleteFile(
					companyId, portletId, repositoryId, fileName);
			}
			catch (SearchException se) {
			}

			Indexer.addFile(
				companyId, portletId, groupId, newRepositoryId, fileName);
		}
		catch (SearchException se) {
			throw new SystemException();
		}
	}

	protected Entry getCompanyFolder(long companyId) throws CMISException {
		String name = Long.toString(companyId);

		Entry companyFolder = CMISUtil.getFolder(name);

		if (companyFolder == null) {
			companyFolder = CMISUtil.createFolder(name);
		}

		return companyFolder;
	}

	protected Entry getRepositoryFolder(long companyId, long repositoryId)
		throws CMISException {

		String name = Long.toString(repositoryId);

		Entry companyFolder = getCompanyFolder(companyId);

		Link link = companyFolder.getLink(_constants.LINK_DESCENDANTS);

		String descendantsUrl = link.getHref().toString();

		Entry repositoryFolder = CMISUtil.getFolder(descendantsUrl, name);

		if (repositoryFolder == null) {
			repositoryFolder = CMISUtil.createFolder(descendantsUrl, name);
		}

		return repositoryFolder;
	}

	protected Entry getVersioningFolder(
			long companyId, long repositoryId, String fileName)
		throws CMISException {

		return getVersioningFolder(companyId, repositoryId, fileName, true);
	}

	protected Entry getVersioningFolder(
			long companyId, long repositoryId, String fileName, boolean create)
		throws CMISException {

		Entry repositoryFolder = getRepositoryFolder(companyId, repositoryId);

		Link link = repositoryFolder.getLink(_constants.LINK_DESCENDANTS);

		String descendantsUrl = link.getHref().toString();

		Entry versioningFolder = CMISUtil.getEntry(
			descendantsUrl, fileName, _constants.BASE_TYPE_FOLDER);

		if (create && (versioningFolder == null)) {
			versioningFolder = CMISUtil.createFolder(descendantsUrl, fileName);
		}

		return versioningFolder;
	}

	protected Entry getVersionedFile(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws CMISException, NoSuchFileException {

		String versionString = Double.toString(versionNumber);

		Entry versioningFolder =
			getVersioningFolder(companyId, repositoryId, fileName, false);

		if (versioningFolder == null) {
			throw new NoSuchFileException();
		}

		Link link = versioningFolder.getLink(_constants.LINK_DESCENDANTS);

		String descendantsUrl = link.getHref().toString();

		Entry fileEntry = CMISUtil.getEntry(
			descendantsUrl, versionString, _constants.BASE_TYPE_DOCUMENT);

		if (fileEntry == null) {
			throw new NoSuchFileException();
		}

		return fileEntry;
	}

	private static final CMISConstants _constants = CMISUtil.getConstants();

	private static Log _log = LogFactoryUtil.getLog(CMISHook.class);

}