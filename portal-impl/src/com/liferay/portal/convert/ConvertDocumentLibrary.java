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

package com.liferay.portal.convert;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.util.Hook;
import com.liferay.documentlibrary.util.HookFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.imagegallery.util.Indexer;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * <a href="ConvertDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
 * @author Minhchau Dang
 * @author Alexander Chow
 */
public class ConvertDocumentLibrary extends ConvertProcess {

	public String getDescription() {
		return "migrate-documents-from-one-repository-to-another";
	}

	public String getParameterDescription() {
		return "please-select-the-new-repository-hook";
	}

	public String[] getParameterNames() {
		StringBuilder sb = new StringBuilder();

		sb.append(PropsKeys.DL_HOOK_IMPL + StringPool.EQUAL);

		for (String hook : _HOOKS) {
			if (!hook.equals(PropsValues.DL_HOOK_IMPL)) {
				sb.append(hook + StringPool.SEMICOLON);
			}
		}

		return new String[] {sb.toString()};
	}

	public boolean isEnabled() {
		return true;
	}

	protected void doConvert() throws Exception {
		_sourceHook = HookFactory.getInstance();

		String[] values = getParameterValues();

		String targetHookClassName = values[0];

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		_targetHook = (Hook)classLoader.loadClass(
			targetHookClassName).newInstance();

		migratePortlets();

		HookFactory.setInstance(_targetHook);

		MaintenanceUtil.appendStatus(
			"Please set " + PropsKeys.DL_HOOK_IMPL +
				" in your portal-ext.properties to use " + targetHookClassName);
	}

	protected void migratePortlets() throws Exception {
		migrateDL();
		migrateMB();
		migrateWiki();
	}

	protected void migrateFiles(
			long companyId, String dirName, String[] fileNames)
		throws Exception {

		String portletId = CompanyConstants.SYSTEM_STRING;
		long groupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
		long repositoryId = CompanyConstants.SYSTEM;
		long fileEntryId = 0;
		double versionNumber = Hook.DEFAULT_VERSION;
		String properties = StringPool.BLANK;
		Date modifiedDate = new Date();

		try {
			_targetHook.addDirectory(companyId, repositoryId, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		for (String fileName : fileNames) {
			if (fileName.startsWith(StringPool.SLASH)) {
				fileName = fileName.substring(1);
			}

			migrateFile(
				companyId, portletId, groupId, repositoryId, fileEntryId,
				fileName, versionNumber, properties, modifiedDate);
		}
	}

	protected void migrateDL() throws Exception {
		int count = DLFileEntryLocalServiceUtil.getDLFileEntriesCount();
		int pages = count / Indexer.DEFAULT_INTERVAL;

		MaintenanceUtil.appendStatus(
			"Migrating " + count + " document library files");

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			List<DLFileEntry> dlFileEntries =
				DLFileEntryLocalServiceUtil.getDLFileEntries(start, end);

			String portletId = PortletKeys.DOCUMENT_LIBRARY;

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				long companyId = dlFileEntry.getCompanyId();
				long groupId = dlFileEntry.getGroupId();
				long folderId = dlFileEntry.getFolderId();
				long repositoryId = DLFileEntryLocalServiceUtil.getRepositoryId(
					groupId, folderId);

				migrateDLFileEntry(
					companyId, portletId, groupId, repositoryId, dlFileEntry);
			}
		}
	}

	protected void migrateDLFileEntry(
			long companyId, String portletId, long groupId, long repositoryId,
			DLFileEntry fileEntry)
		throws Exception {

		long fileEntryId = fileEntry.getFileEntryId();
		String fileName = fileEntry.getName();
		String properties = fileEntry.getLuceneProperties();

		List<DLFileVersion> dlFileVersions =
			DLFileVersionLocalServiceUtil.getFileVersions(
				groupId, repositoryId, fileName, StatusConstants.ANY);

		if (dlFileVersions.isEmpty()) {
			double versionNumber = Hook.DEFAULT_VERSION;
			Date modifiedDate = fileEntry.getModifiedDate();

			migrateFile(
				companyId, portletId, groupId, repositoryId, fileEntryId,
				fileName, versionNumber, properties, modifiedDate);

			return;
		}

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			double versionNumber = dlFileVersion.getVersion();
			Date modifiedDate = dlFileVersion.getCreateDate();

			migrateFile(
				companyId, portletId, groupId, repositoryId, fileEntryId,
				fileName, versionNumber, properties, modifiedDate);
		}
	}

	protected void migrateMB() throws Exception {
		int count = MBMessageLocalServiceUtil.getMBMessagesCount();
		int pages = count / Indexer.DEFAULT_INTERVAL;

		MaintenanceUtil.appendStatus(
			"Migrating message boards attachments in " + count + " messages");

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			List<MBMessage> messages =
				MBMessageLocalServiceUtil.getMBMessages(start, end);

			for (MBMessage message : messages) {
				migrateFiles(
					message.getCompanyId(), message.getAttachmentsDir(),
					message.getAttachmentsFiles());
			}
		}
	}

	protected void migrateFile(
		long companyId, String portletId, long groupId, long repositoryId,
		long fileEntryId, String fileName, double versionNumber,
		String properties, Date modifiedDate) {

		try {
			InputStream is = _sourceHook.getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);

			if (versionNumber == Hook.DEFAULT_VERSION) {
				_targetHook.addFile(
					companyId, portletId, groupId, repositoryId, fileName,
					fileEntryId, properties, modifiedDate, _serviceContext,
					is);
			}
			else {
				_targetHook.updateFile(
					companyId, portletId, groupId, repositoryId, fileName,
					versionNumber, fileName, fileEntryId, properties,
					modifiedDate, _serviceContext, is);
			}
		}
		catch (Exception e) {
			_log.error("Migration failed for " + fileName, e);
		}
	}

	protected void migrateWiki() throws Exception {
		int count = WikiPageLocalServiceUtil.getWikiPagesCount();
		int pages = count / Indexer.DEFAULT_INTERVAL;

		MaintenanceUtil.appendStatus(
			"Migrating wiki page attachments in " + count + " pages");

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			List<WikiPage> wikiPages =
				WikiPageLocalServiceUtil.getWikiPages(start, end);

			for (WikiPage wikiPage : wikiPages) {
				if (!wikiPage.isHead()) {
					continue;
				}

				migrateFiles(
					wikiPage.getCompanyId(), wikiPage.getAttachmentsDir(),
					wikiPage.getAttachmentsFiles());
			}
		}
	}

	private Hook _sourceHook;
	private Hook _targetHook;

	private static ServiceContext _serviceContext = new ServiceContext();

	private static final String[] _HOOKS = new String[] {
		"com.liferay.documentlibrary.util.AdvancedFileSystemHook",
		"com.liferay.documentlibrary.util.CMISHook",
		"com.liferay.documentlibrary.util.FileSystemHook",
		"com.liferay.documentlibrary.util.JCRHook",
		"com.liferay.documentlibrary.util.S3Hook"
	};

	private static Log _log =
		LogFactoryUtil.getLog(ConvertDocumentLibrary.class);

}