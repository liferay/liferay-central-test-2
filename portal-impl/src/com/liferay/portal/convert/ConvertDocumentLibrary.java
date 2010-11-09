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

package com.liferay.portal.convert;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.util.Hook;
import com.liferay.documentlibrary.util.HookFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * @author Minhchau Dang
 * @author Alexander Chow
 */
public class ConvertDocumentLibrary extends ConvertProcess {

	public String getDescription() {
		return "migrate-documents-from-one-repository-to-another";
	}

	public String getParameterDescription() {
		return "please-select-a-new-repository-hook";
	}

	public String[] getParameterNames() {
		StringBundler sb = new StringBundler(_HOOKS.length * 2 + 2);

		sb.append(PropsKeys.DL_HOOK_IMPL);
		sb.append(StringPool.EQUAL);

		for (String hook : _HOOKS) {
			if (!hook.equals(PropsValues.DL_HOOK_IMPL)) {
				sb.append(hook);
				sb.append(StringPool.SEMICOLON);
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

		PropsValues.DL_HOOK_IMPL = targetHookClassName;
	}

	protected void migrateDL() throws Exception {
		int count = DLAppLocalServiceUtil.getDLFileEntriesCount();
		int pages = count / Indexer.DEFAULT_INTERVAL;

		MaintenanceUtil.appendStatus(
			"Migrating " + count + " document library files");

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			List<DLFileEntry> dlFileEntries =
				DLAppLocalServiceUtil.getDLFileEntries(start, end);

			String portletId = PortletKeys.DOCUMENT_LIBRARY;

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				long companyId = dlFileEntry.getCompanyId();
				long groupId = dlFileEntry.getGroupId();
				long repositoryId = dlFileEntry.getRepositoryId();

				migrateDLFileEntry(
					companyId, portletId, groupId, repositoryId, dlFileEntry);
			}
		}
	}

	protected void migrateDLFileEntry(
			long companyId, String portletId, long groupId, long repositoryId,
			DLFileEntry fileEntry)
		throws Exception {

		String fileName = fileEntry.getName();
		long fileEntryId = fileEntry.getFileEntryId();
		String properties = fileEntry.getLuceneProperties();

		List<DLFileVersion> dlFileVersions =
			DLAppLocalServiceUtil.getFileVersions(
				groupId, repositoryId, fileName, WorkflowConstants.STATUS_ANY);

		if (dlFileVersions.isEmpty()) {
			String versionNumber = Hook.DEFAULT_VERSION;
			Date modifiedDate = fileEntry.getModifiedDate();

			migrateFile(
				companyId, portletId, groupId, repositoryId, fileName,
				versionNumber, fileEntryId, properties, modifiedDate);

			return;
		}

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			String versionNumber = dlFileVersion.getVersion();
			Date modifiedDate = dlFileVersion.getCreateDate();

			migrateFile(
				companyId, portletId, groupId, repositoryId, fileName,
				versionNumber, fileEntryId, properties, modifiedDate);
		}
	}

	protected void migrateFile(
		long companyId, String portletId, long groupId, long repositoryId,
		String fileName, String versionNumber, long fileEntryId,
		String properties, Date modifiedDate) {

		try {
			InputStream is = _sourceHook.getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);

			if (versionNumber.equals(Hook.DEFAULT_VERSION)) {
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
	protected void migrateFiles(
			long companyId, String dirName, String[] fileNames)
		throws Exception {

		String portletId = CompanyConstants.SYSTEM_STRING;
		long groupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
		long repositoryId = CompanyConstants.SYSTEM;
		String versionNumber = Hook.DEFAULT_VERSION;
		long fileEntryId = 0;
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
				companyId, portletId, groupId, repositoryId, fileName,
				versionNumber, fileEntryId, properties, modifiedDate);
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

	protected void migratePortlets() throws Exception {
		migrateDL();
		migrateMB();
		migrateWiki();
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

	private static final String[] _HOOKS = new String[] {
		"com.liferay.documentlibrary.util.AdvancedFileSystemHook",
		"com.liferay.documentlibrary.util.CMISHook",
		"com.liferay.documentlibrary.util.FileSystemHook",
		"com.liferay.documentlibrary.util.JCRHook",
		"com.liferay.documentlibrary.util.S3Hook"
	};

	private static Log _log = LogFactoryUtil.getLog(
		ConvertDocumentLibrary.class);

	private ServiceContext _serviceContext = new ServiceContext();
	private Hook _sourceHook;
	private Hook _targetHook;

}