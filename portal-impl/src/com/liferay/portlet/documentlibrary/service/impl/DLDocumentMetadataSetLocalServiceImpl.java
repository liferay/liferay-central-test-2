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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.service.base.DLDocumentMetadataSetLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class DLDocumentMetadataSetLocalServiceImpl
	extends DLDocumentMetadataSetLocalServiceBaseImpl {

	public void deleteDocumentMetadataSets(long fileVersionId)
		throws PortalException, SystemException {

		List<DLDocumentMetadataSet> documentMetadataSets =
			dlDocumentMetadataSetPersistence.findByFileVersionId(fileVersionId);

		for (DLDocumentMetadataSet documentMetadataSet : documentMetadataSets) {
			deleteDocumentMetadataSet(documentMetadataSet);
		}
	}

	public DLDocumentMetadataSet getDocumentMetadataSet(
			long documentMetadataSetId)
		throws PortalException, SystemException {

		return dlDocumentMetadataSetPersistence.findByPrimaryKey(
			documentMetadataSetId);
	}

	public DLDocumentMetadataSet getDocumentMetadataSet(
			long ddmStructureId, long fileVersionId)
		throws PortalException, SystemException {

		return dlDocumentMetadataSetPersistence.findByD_F(
			ddmStructureId, fileVersionId);
	}

	public void updateDocumentMetadataSets(
			long companyId, List<DDMStructure> ddmStructures,
			long documentTypeId, long fileVersionId,
			Map<String, Fields> fieldsMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		for (DDMStructure ddmStructure : ddmStructures) {
			Fields fields = fieldsMap.get(ddmStructure.getStructureKey());

			if (fields != null) {
				updateDocumentMetadataSet(
					companyId, ddmStructure, documentTypeId, fileVersionId,
					fields, serviceContext);
			}
		}
	}

	public void updateDocumentMetadataSets(
			long documentTypeId, long fileVersionId,
			Map<String, Fields> fieldsMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLDocumentType documentType =
			dlDocumentTypeLocalService.getDocumentType(documentTypeId);

		List<DDMStructure> ddmStructures = documentType.getDDMStructures();

		updateDocumentMetadataSets(
			documentType.getCompanyId(), ddmStructures, documentTypeId,
			fileVersionId, fieldsMap, serviceContext);
	}

	protected void deleteDocumentMetadataSet(
			DLDocumentMetadataSet documentMetadataSet)
		throws PortalException, SystemException {

		// Document metadata set

		dlDocumentMetadataSetPersistence.remove(documentMetadataSet);

		// Dynamic data mapping storage

		StorageEngineUtil.deleteByClass(documentMetadataSet.getClassPK());

		// Dynamic data mapping structure link

		ddmStructureLinkLocalService.deleteClassStructureLink(
			documentMetadataSet.getDocumentMetadataSetId());
	}

	protected void updateDocumentMetadataSet(
			long companyId, DDMStructure ddmStructure, long documentTypeId,
			long fileVersionId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException, SystemException {

		try {
			DLDocumentMetadataSet metadataSet =
				dlDocumentMetadataSetPersistence.findByD_F(
					ddmStructure.getStructureId(), fileVersionId);

			StorageEngineUtil.update(
				metadataSet.getClassPK(), fields, serviceContext);
		}
		catch (NoSuchDocumentMetadataSetException nsdmse) {

			// Document metadata set

			long documentMetadataSetId = counterLocalService.increment();

			DLDocumentMetadataSet documentMetadataSet =
				dlDocumentMetadataSetPersistence.create(
					documentMetadataSetId);

			documentMetadataSet.setClassNameId(
				ddmStructure.getClassNameId());

			long classPK = StorageEngineUtil.create(
				companyId, ddmStructure.getStructureId(), fields,
				serviceContext);

			documentMetadataSet.setClassPK(classPK);

			documentMetadataSet.setDDMStructureId(
				ddmStructure.getStructureId());
			documentMetadataSet.setDocumentTypeId(documentTypeId);
			documentMetadataSet.setFileVersionId(fileVersionId);

			dlDocumentMetadataSetPersistence.update(
				documentMetadataSet, false);

			// Dynamic data mapping structure link

			long classNameId = PortalUtil.getClassNameId(
				DLDocumentMetadataSet.class);

			ddmStructureLinkLocalService.addStructureLink(
				classNameId, documentMetadataSet.getDocumentMetadataSetId(),
				ddmStructure.getStructureId(), serviceContext);
		}
	}

}