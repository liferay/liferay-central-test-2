/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.journal.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class JournalFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFolder> {

	public static final String[] CLASS_NAMES = {JournalFolder.class.getName()};

	@Override
	public void deleteStagedModel(JournalFolder folder) throws PortalException {
		_journalFolderLocalService.deleteFolder(folder);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFolder folder = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (folder != null) {
			deleteStagedModel(folder);
		}
	}

	@Override
	public JournalFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFolderLocalService.fetchJournalFolderByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFolderLocalService.getJournalFoldersByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFolder>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(JournalFolder folder) {
		return folder.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		if (folder.getParentFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, folder.getParentFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element folderElement = portletDataContext.getExportDataElement(folder);

		portletDataContext.addClassedModel(
			folderElement, ExportImportPathUtil.getModelPath(folder), folder);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder);

		JournalFolder importedFolder = null;

		long groupId = portletDataContext.getScopeGroupId();

		if (portletDataContext.isDataStrategyMirror()) {
			JournalFolder existingFolder = fetchStagedModelByUuidAndGroupId(
				folder.getUuid(), groupId);

			if (existingFolder == null) {
				serviceContext.setUuid(folder.getUuid());

				importedFolder = _journalFolderLocalService.addFolder(
					userId, groupId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}
			else {
				importedFolder = _journalFolderLocalService.updateFolder(
					userId, serviceContext.getScopeGroupId(),
					existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			importedFolder = _journalFolderLocalService.addFolder(
				userId, groupId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(folder, importedFolder);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		JournalFolder existingFolder = fetchStagedModelByUuidAndGroupId(
			folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) || !existingFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingFolder.getTrashHandler();

		if (trashHandler.isRestorable(existingFolder.getFolderId())) {
			trashHandler.restoreTrashEntry(
				userId, existingFolder.getFolderId());
		}
	}

	@Reference
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private JournalFolderLocalService _journalFolderLocalService;

}