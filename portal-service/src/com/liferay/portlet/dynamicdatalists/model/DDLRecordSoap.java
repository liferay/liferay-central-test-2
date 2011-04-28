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

package com.liferay.portlet.dynamicdatalists.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordSoap implements Serializable {
	public static DDLRecordSoap toSoapModel(DDLRecord model) {
		DDLRecordSoap soapModel = new DDLRecordSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setRecordId(model.getRecordId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setRecordSetId(model.getRecordSetId());

		return soapModel;
	}

	public static DDLRecordSoap[] toSoapModels(DDLRecord[] models) {
		DDLRecordSoap[] soapModels = new DDLRecordSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordSoap[][] toSoapModels(DDLRecord[][] models) {
		DDLRecordSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDLRecordSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDLRecordSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDLRecordSoap[] toSoapModels(List<DDLRecord> models) {
		List<DDLRecordSoap> soapModels = new ArrayList<DDLRecordSoap>(models.size());

		for (DDLRecord model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDLRecordSoap[soapModels.size()]);
	}

	public DDLRecordSoap() {
	}

	public long getPrimaryKey() {
		return _recordId;
	}

	public void setPrimaryKey(long pk) {
		setRecordId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getRecordId() {
		return _recordId;
	}

	public void setRecordId(long recordId) {
		_recordId = recordId;
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

	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
	}

	private String _uuid;
	private long _recordId;
	private long _classNameId;
	private long _classPK;
	private long _recordSetId;
}