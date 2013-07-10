/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.persistence.GroupActionableDynamicQuery;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryAssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryActionableDynamicQuery;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderActionableDynamicQuery;
import com.liferay.portlet.dynamicdatamapping.StructureFieldException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.messageboards.model.MBMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Alexander Chow
 */
public class DLFileEntryIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {DLFileEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.DOCUMENT_LIBRARY;

	public DLFileEntryIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		MBMessage message = (MBMessage)obj;

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			message.getClassPK());

		document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
		document.addKeyword(Field.HIDDEN, dlFileEntry.isInHiddenFolder());
		document.addKeyword(Field.RELATED_ENTRY, true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return DLFileEntryPermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		if (searchContext.isIncludeAttachments()) {
			addRelatedClassNames(contextQuery, searchContext);
		}

		contextQuery.addRequiredTerm(
			Field.HIDDEN, searchContext.isIncludeAttachments());

		addSearchClassTypeIds(contextQuery, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			String[] ddmStructureFieldNameParts = StringUtil.split(
				ddmStructureFieldName, StringPool.SLASH);

			DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
				GetterUtil.getLong(ddmStructureFieldNameParts[1]));

			String fieldName = StringUtil.replaceLast(
				ddmStructureFieldNameParts[2],
				StringPool.UNDERLINE.concat(
					LocaleUtil.toLanguageId(searchContext.getLocale())),
				StringPool.BLANK);

			try {
				ddmStructureFieldValue = DDMUtil.getIndexedFieldValue(
					ddmStructureFieldValue, structure.getFieldType(fieldName));
			}
			catch (StructureFieldException sfe) {
			}

			contextQuery.addRequiredTerm(
				ddmStructureFieldName,
				StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);
		}

		long[] folderIds = searchContext.getFolderIds();

		if ((folderIds != null) && (folderIds.length > 0) &&
			(folderIds[0] !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long folderId : folderIds) {
				try {
					DLFolderServiceUtil.getFolder(folderId);
				}
				catch (Exception e) {
					continue;
				}

				folderIdsQuery.addTerm(Field.FOLDER_ID, folderId);
			}

			contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		Set<DDMStructure> ddmStructuresSet = new TreeSet<DDMStructure>();

		long[] groupIds = searchContext.getGroupIds();

		if ((groupIds != null) && (groupIds.length > 0)) {
			List<DLFileEntryType> dlFileEntryTypes =
				DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(groupIds);

			for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
				ddmStructuresSet.addAll(dlFileEntryType.getDDMStructures());
			}
		}

		Group group = GroupLocalServiceUtil.getCompanyGroup(
			searchContext.getCompanyId());

		DDMStructure tikaRawMetadataStructure =
			DDMStructureLocalServiceUtil.fetchStructure(
				group.getGroupId(),
				PortalUtil.getClassNameId(RawMetadataProcessor.class),
				"TikaRawMetadata");

		if (tikaRawMetadataStructure != null) {
			ddmStructuresSet.add(tikaRawMetadataStructure);
		}

		for (DDMStructure ddmStructure : ddmStructuresSet) {
			addSearchDDMStruture(searchQuery, searchContext, ddmStructure);
		}

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, false);
			addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
			addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		}

		addSearchTerm(searchQuery, searchContext, "extension", false);
		addSearchTerm(searchQuery, searchContext, "fileEntryTypeId", false);
		addSearchTerm(searchQuery, searchContext, "path", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addFileEntryTypeAttributes(
			Document document, DLFileVersion dlFileVersion)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
				dlFileVersion.getFileEntryTypeId());

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Fields fields = null;

			try {
				DLFileEntryMetadata fileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
						ddmStructure.getStructureId(),
						dlFileVersion.getFileVersionId());

				fields = StorageEngineUtil.getFields(
					fileEntryMetadata.getDDMStorageId());
			}
			catch (Exception e) {
			}

			if (fields != null) {
				DDMIndexerUtil.addAttributes(document, ddmStructure, fields);
			}
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, dlFileEntry.getFileEntryId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), dlFileEntry.getCompanyId(),
			document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing document " + dlFileEntry);
		}

		boolean indexContent = true;

		InputStream is = null;

		try {
			if (PropsValues.DL_FILE_INDEXING_MAX_SIZE == 0) {
				indexContent = false;
			}
			else if (PropsValues.DL_FILE_INDEXING_MAX_SIZE != -1) {
				if (dlFileEntry.getSize() >
						PropsValues.DL_FILE_INDEXING_MAX_SIZE) {

					indexContent = false;
				}
			}

			if (indexContent) {
				String[] ignoreExtensions = PrefsPropsUtil.getStringArray(
					PropsKeys.DL_FILE_INDEXING_IGNORE_EXTENSIONS,
					StringPool.COMMA);

				if (ArrayUtil.contains(
						ignoreExtensions,
						StringPool.PERIOD + dlFileEntry.getExtension())) {

					indexContent = false;
				}
			}

			if (indexContent) {
				is = dlFileEntry.getFileVersion().getContentStream(false);
			}
		}
		catch (Exception e) {
		}

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		try {
			Document document = getBaseModelDocument(
				PORTLET_ID, dlFileEntry, dlFileVersion);

			if (indexContent) {
				if (is != null) {
					try {
						document.addFile(
							Field.CONTENT, is, dlFileEntry.getTitle());
					}
					catch (IOException ioe) {
						throw new SearchException(
							"Cannot extract text from file" + dlFileEntry);
					}
				}
				else if (_log.isDebugEnabled()) {
					_log.debug(
						"Document " + dlFileEntry +
							" does not have any content");
				}
			}

			document.addKeyword(
				Field.CLASS_TYPE_ID, dlFileEntry.getFileEntryTypeId());
			document.addText(Field.DESCRIPTION, dlFileEntry.getDescription());
			document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
			document.addKeyword(Field.HIDDEN, dlFileEntry.isInHiddenFolder());
			document.addText(
				Field.PROPERTIES, dlFileEntry.getLuceneProperties());
			document.addText(Field.TITLE, dlFileEntry.getTitle());

			document.addKeyword(
				"dataRepositoryId", dlFileEntry.getDataRepositoryId());
			document.addKeyword("downloads", dlFileEntry.getReadCount());
			document.addKeyword("extension", dlFileEntry.getExtension());
			document.addKeyword(
				"fileEntryTypeId", dlFileEntry.getFileEntryTypeId());
			document.addKeyword("path", dlFileEntry.getTitle());
			document.addKeyword("size", dlFileEntry.getSize());

			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
					dlFileVersion.getFileVersionId());

			ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

			addFileEntryTypeAttributes(document, dlFileVersion);

			if (dlFileEntry.isInHiddenFolder()) {
				try {
					Repository repository =
						RepositoryLocalServiceUtil.getRepository(
							dlFileEntry.getRepositoryId());

					String portletId = repository.getPortletId();

					for (Indexer indexer : IndexerRegistryUtil.getIndexers()) {
						if (portletId.equals(indexer.getPortletId())) {
							indexer.addRelatedEntryFields(document, obj);

							break;
						}
					}
				}
				catch (Exception e) {
				}
			}

			if (!dlFileVersion.isInTrash() &&
				dlFileVersion.isInTrashContainer()) {

				DLFolder folder = dlFileVersion.getTrashContainer();

				addTrashFields(
					document, DLFolder.class.getName(), folder.getFolderId(),
					null, null, DLFileEntryAssetRendererFactory.TYPE);

				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_NAME, DLFolder.class.getName());
				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_PK, folder.getFolderId());
				document.addKeyword(
					Field.STATUS, WorkflowConstants.STATUS_IN_TRASH);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Document " + dlFileEntry + " indexed successfully");
			}

			return document;
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		liferayPortletURL.setLifecycle(PortletRequest.ACTION_PHASE);

		try {
			liferayPortletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
		}

		String fileEntryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/document_library/get_file");
		portletURL.setParameter("fileEntryId", fileEntryId);

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		if (!dlFileVersion.isApproved() && !dlFileVersion.isInTrash()) {
			return;
		}

		Document document = getDocument(dlFileEntry);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), dlFileEntry.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			classPK);

		doReindex(dlFileEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		if (ids.length == 1) {
			long companyId = GetterUtil.getLong(ids[0]);

			reindexFolders(companyId);
			reindexRoot(companyId);
		}
		else {
			long companyId = GetterUtil.getLong(ids[0]);
			long groupId = GetterUtil.getLong(ids[2]);
			long dataRepositoryId = GetterUtil.getLong(ids[3]);

			reindexFileEntries(companyId, groupId, dataRepositoryId);
		}
	}

	@Override
	protected void doReindexDDMStructures(List<Long> ddmStructureIds)
		throws Exception {

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getDDMStructureFileEntries(
				ArrayUtil.toLongArray(ddmStructureIds));

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			doReindex(dlFileEntry);
		}
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexFileEntries(
			long companyId, final long groupId, final long dataRepositoryId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new DLFileEntryActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property property = PropertyFactoryUtil.forName("folderId");

				long folderId = DLFolderConstants.getFolderId(
					groupId, dataRepositoryId);

				dynamicQuery.add(property.eq(folderId));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				DLFileEntry dlFileEntry = (DLFileEntry)object;

				Document document = getDocument(dlFileEntry);

				if (document != null) {
					documents.add(document);
				}
			}

		};

		actionableDynamicQuery.setGroupId(groupId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	protected void reindexFolders(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new DLFolderActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws PortalException {
				DLFolder dlFolder = (DLFolder)object;

				String portletId = PortletKeys.DOCUMENT_LIBRARY;
				long groupId = dlFolder.getGroupId();
				long folderId = dlFolder.getFolderId();

				String[] newIds = {
					String.valueOf(companyId), portletId,
					String.valueOf(groupId), String.valueOf(folderId)
				};

				reindex(newIds);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new GroupActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws PortalException {
				Group group = (Group)object;

				String portletId = PortletKeys.DOCUMENT_LIBRARY;
				long groupId = group.getGroupId();
				long folderId = groupId;

				String[] newIds = {
					String.valueOf(companyId), portletId,
					String.valueOf(groupId), String.valueOf(folderId)
				};

				reindex(newIds);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryIndexer.class);

}