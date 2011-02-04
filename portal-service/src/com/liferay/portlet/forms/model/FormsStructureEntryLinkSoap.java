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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.forms.service.http.FormsStructureEntryLinkServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.forms.service.http.FormsStructureEntryLinkServiceSoap
 * @generated
 */
public class FormsStructureEntryLinkSoap implements Serializable {
	public static FormsStructureEntryLinkSoap toSoapModel(
		FormsStructureEntryLink model) {
		FormsStructureEntryLinkSoap soapModel = new FormsStructureEntryLinkSoap();

		soapModel.setFormStructureLinkId(model.getFormStructureLinkId());
		soapModel.setFormStructureId(model.getFormStructureId());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static FormsStructureEntryLinkSoap[] toSoapModels(
		FormsStructureEntryLink[] models) {
		FormsStructureEntryLinkSoap[] soapModels = new FormsStructureEntryLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FormsStructureEntryLinkSoap[][] toSoapModels(
		FormsStructureEntryLink[][] models) {
		FormsStructureEntryLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FormsStructureEntryLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FormsStructureEntryLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FormsStructureEntryLinkSoap[] toSoapModels(
		List<FormsStructureEntryLink> models) {
		List<FormsStructureEntryLinkSoap> soapModels = new ArrayList<FormsStructureEntryLinkSoap>(models.size());

		for (FormsStructureEntryLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FormsStructureEntryLinkSoap[soapModels.size()]);
	}

	public FormsStructureEntryLinkSoap() {
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