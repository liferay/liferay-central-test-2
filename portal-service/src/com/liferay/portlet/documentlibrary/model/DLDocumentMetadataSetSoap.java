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

package com.liferay.portlet.documentlibrary.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DLDocumentMetadataSetSoap implements Serializable {
	public static DLDocumentMetadataSetSoap toSoapModel(
		DLDocumentMetadataSet model) {
		DLDocumentMetadataSetSoap soapModel = new DLDocumentMetadataSetSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setMetadataSetId(model.getMetadataSetId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setDocumentTypeId(model.getDocumentTypeId());
		soapModel.setDDMStructureId(model.getDDMStructureId());
		soapModel.setFileVersionId(model.getFileVersionId());

		return soapModel;
	}

	public static DLDocumentMetadataSetSoap[] toSoapModels(
		DLDocumentMetadataSet[] models) {
		DLDocumentMetadataSetSoap[] soapModels = new DLDocumentMetadataSetSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLDocumentMetadataSetSoap[][] toSoapModels(
		DLDocumentMetadataSet[][] models) {
		DLDocumentMetadataSetSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLDocumentMetadataSetSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLDocumentMetadataSetSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLDocumentMetadataSetSoap[] toSoapModels(
		List<DLDocumentMetadataSet> models) {
		List<DLDocumentMetadataSetSoap> soapModels = new ArrayList<DLDocumentMetadataSetSoap>(models.size());

		for (DLDocumentMetadataSet model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLDocumentMetadataSetSoap[soapModels.size()]);
	}

	public DLDocumentMetadataSetSoap() {
	}

	public long getPrimaryKey() {
		return _metadataSetId;
	}

	public void setPrimaryKey(long pk) {
		setMetadataSetId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getMetadataSetId() {
		return _metadataSetId;
	}

	public void setMetadataSetId(long metadataSetId) {
		_metadataSetId = metadataSetId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getDocumentTypeId() {
		return _documentTypeId;
	}

	public void setDocumentTypeId(long documentTypeId) {
		_documentTypeId = documentTypeId;
	}

	public long getDDMStructureId() {
		return _DDMStructureId;
	}

	public void setDDMStructureId(long DDMStructureId) {
		_DDMStructureId = DDMStructureId;
	}

	public long getFileVersionId() {
		return _fileVersionId;
	}

	public void setFileVersionId(long fileVersionId) {
		_fileVersionId = fileVersionId;
	}

	private String _uuid;
	private long _metadataSetId;
	private long _classNameId;
	private long _classPK;
	private long _documentTypeId;
	private long _DDMStructureId;
	private long _fileVersionId;
}