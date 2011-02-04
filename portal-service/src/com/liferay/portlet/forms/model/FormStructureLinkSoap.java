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

package com.liferay.portlet.forms.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.forms.service.http.FormStructureLinkServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.forms.service.http.FormStructureLinkServiceSoap
 * @generated
 */
public class FormStructureLinkSoap implements Serializable {
	public static FormStructureLinkSoap toSoapModel(FormStructureLink model) {
		FormStructureLinkSoap soapModel = new FormStructureLinkSoap();

		soapModel.setFormStructureLinkId(model.getFormStructureLinkId());
		soapModel.setFormStructureId(model.getFormStructureId());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static FormStructureLinkSoap[] toSoapModels(
		FormStructureLink[] models) {
		FormStructureLinkSoap[] soapModels = new FormStructureLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FormStructureLinkSoap[][] toSoapModels(
		FormStructureLink[][] models) {
		FormStructureLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FormStructureLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FormStructureLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FormStructureLinkSoap[] toSoapModels(
		List<FormStructureLink> models) {
		List<FormStructureLinkSoap> soapModels = new ArrayList<FormStructureLinkSoap>(models.size());

		for (FormStructureLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FormStructureLinkSoap[soapModels.size()]);
	}

	public FormStructureLinkSoap() {
	}

	public long getPrimaryKey() {
		return _formStructureLinkId;
	}

	public void setPrimaryKey(long pk) {
		setFormStructureLinkId(pk);
	}

	public long getFormStructureLinkId() {
		return _formStructureLinkId;
	}

	public void setFormStructureLinkId(long formStructureLinkId) {
		_formStructureLinkId = formStructureLinkId;
	}

	public String getFormStructureId() {
		return _formStructureId;
	}

	public void setFormStructureId(String formStructureId) {
		_formStructureId = formStructureId;
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

	private long _formStructureLinkId;
	private String _formStructureId;
	private String _className;
	private long _classPK;
}