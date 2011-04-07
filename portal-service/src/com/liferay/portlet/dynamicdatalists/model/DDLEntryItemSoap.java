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
public class DDLEntryItemSoap implements Serializable {
	public static DDLEntryItemSoap toSoapModel(DDLEntryItem model) {
		DDLEntryItemSoap soapModel = new DDLEntryItemSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setEntryItemId(model.getEntryItemId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setEntryId(model.getEntryId());

		return soapModel;
	}

	public static DDLEntryItemSoap[] toSoapModels(DDLEntryItem[] models) {
		DDLEntryItemSoap[] soapModels = new DDLEntryItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDLEntryItemSoap[][] toSoapModels(DDLEntryItem[][] models) {
		DDLEntryItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDLEntryItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDLEntryItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDLEntryItemSoap[] toSoapModels(List<DDLEntryItem> models) {
		List<DDLEntryItemSoap> soapModels = new ArrayList<DDLEntryItemSoap>(models.size());

		for (DDLEntryItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDLEntryItemSoap[soapModels.size()]);
	}

	public DDLEntryItemSoap() {
	}

	public long getPrimaryKey() {
		return _entryItemId;
	}

	public void setPrimaryKey(long pk) {
		setEntryItemId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getEntryItemId() {
		return _entryItemId;
	}

	public void setEntryItemId(long entryItemId) {
		_entryItemId = entryItemId;
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

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	private String _uuid;
	private long _entryItemId;
	private long _classNameId;
	private long _classPK;
	private long _entryId;
}