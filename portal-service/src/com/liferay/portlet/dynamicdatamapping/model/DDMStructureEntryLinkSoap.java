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

package com.liferay.portlet.dynamicdatamapping.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.dynamicdatamapping.service.http.DDMStructureEntryLinkServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.dynamicdatamapping.service.http.DDMStructureEntryLinkServiceSoap
 * @generated
 */
public class DDMStructureEntryLinkSoap implements Serializable {
	public static DDMStructureEntryLinkSoap toSoapModel(
		DDMStructureEntryLink model) {
		DDMStructureEntryLinkSoap soapModel = new DDMStructureEntryLinkSoap();

		soapModel.setStructureEntryLinkId(model.getStructureEntryLinkId());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static DDMStructureEntryLinkSoap[] toSoapModels(
		DDMStructureEntryLink[] models) {
		DDMStructureEntryLinkSoap[] soapModels = new DDMStructureEntryLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureEntryLinkSoap[][] toSoapModels(
		DDMStructureEntryLink[][] models) {
		DDMStructureEntryLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMStructureEntryLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMStructureEntryLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMStructureEntryLinkSoap[] toSoapModels(
		List<DDMStructureEntryLink> models) {
		List<DDMStructureEntryLinkSoap> soapModels = new ArrayList<DDMStructureEntryLinkSoap>(models.size());

		for (DDMStructureEntryLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMStructureEntryLinkSoap[soapModels.size()]);
	}

	public DDMStructureEntryLinkSoap() {
	}

	public long getPrimaryKey() {
		return _structureEntryLinkId;
	}

	public void setPrimaryKey(long pk) {
		setStructureEntryLinkId(pk);
	}

	public long getStructureEntryLinkId() {
		return _structureEntryLinkId;
	}

	public void setStructureEntryLinkId(long structureEntryLinkId) {
		_structureEntryLinkId = structureEntryLinkId;
	}

	public String getStructureId() {
		return _structureId;
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private long _structureEntryLinkId;
	private String _structureId;
	private String _className;
	private long _classPK;
}