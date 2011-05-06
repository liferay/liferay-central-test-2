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

	public void deleteMetadataSets(long fileVersionId)
		throws PortalException, SystemException {

		List<DLDocumentMetadataSet> metadataSets =
			dlDocumentMetadataSetPersistence.findByFileVersionId(fileVersionId);

		for (DLDocumentMetadataSet metadataSet : metadataSets) {
			deleteMetadataSet(metadataSet);
		}
	}

	public DLDocumentMetadataSet getMetadataSet(long metadataSetId)
		throws PortalException, SystemException {

		return dlDocumentMetadataSetPersistence.findByPrimaryKey(metadataSetId);
	}

	public DLDocumentMetadataSet getMetadataSet(
			long ddmStructureId, long fileVersionId)
		throws PortalException, SystemException {

		return dlDocumentMetadataSetPersistence.findByD_F(
			ddmStructureId, fileVersionId);
	}

	public void updateMetadataSets(
			long fileVersionId, long documentTypeId,
			Map<Long, Fields> fieldsMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLDocumentType documentType =
			dlDocumentTypeLocalService.getDocumentType(documentTypeId);

		long companyId = documentType.getCompanyId();

		List<DDMStructure> ddmStructures = documentType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			long ddmStructureId = ddmStructure.getStructureId();

			Fields fields = fieldsMap.get(ddmStructureId);

			if (fields == null) {
				continue;
			}

			try {
				DLDocumentMetadataSet metadataSet =
					dlDocumentMetadataSetPersistence.findByD_F(
						ddmStructureId, fileVersionId);

				StorageEngineUtil.update(
					metadataSet.getClassPK(), fields, serviceContext);
			}
			catch (NoSuchDocumentMetadataSetException nsdmse) {

				// Metadata set

				long metadataSetId = counterLocalService.increment();

				DLDocumentMetadataSet metadataSet =
					dlDocumentMetadataSetPersistence.create(
						metadataSetId);

				metadataSet.setFileVersionId(fileVersionId);
				metadataSet.setDocumentTypeId(documentTypeId);
				metadataSet.setDDMStructureId(ddmStructureId);
				metadataSet.setClassNameId(ddmStructure.getClassNameId());

				long classPK = StorageEngineUtil.create(
					companyId, ddmStructureId, fields, serviceContext);

				metadataSet.setClassPK(classPK);

				dlDocumentMetadataSetPersistence.update(metadataSet, false);

				// Dynamic data mapping structure link

				long classNameId = PortalUtil.getClassNameId(
					DLDocumentMetadataSet.class);

				ddmStructureLinkLocalService.addStructureLink(
					classNameId, metadataSet.getMetadataSetId(), ddmStructureId,
					serviceContext);
			}
		}
	}

	protected void deleteMetadataSet(DLDocumentMetadataSet metadataSet)
		throws PortalException, SystemException {

		// Metadata set

		dlDocumentMetadataSetPersistence.remove(metadataSet);

		// Dynamic data mapping storage

		StorageEngineUtil.deleteByClass(metadataSet.getClassPK());

		// Dynamic data mapping structure link

		ddmStructureLinkLocalService.deleteClassStructureLink(
			metadataSet.getMetadataSetId());
	}

}