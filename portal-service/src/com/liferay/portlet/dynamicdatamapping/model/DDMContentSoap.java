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
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DDMContentSoap implements Serializable {
	public static DDMContentSoap toSoapModel(DDMContent model) {
		DDMContentSoap soapModel = new DDMContentSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setContentId(model.getContentId());

		return soapModel;
	}

	public static DDMContentSoap[] toSoapModels(DDMContent[] models) {
		DDMContentSoap[] soapModels = new DDMContentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMContentSoap[][] toSoapModels(DDMContent[][] models) {
		DDMContentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMContentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMContentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMContentSoap[] toSoapModels(List<DDMContent> models) {
		List<DDMContentSoap> soapModels = new ArrayList<DDMContentSoap>(models.size());

		for (DDMContent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMContentSoap[soapModels.size()]);
	}

	public DDMContentSoap() {
	}

	public long getPrimaryKey() {
		return _contentId;
	}

	public void setPrimaryKey(long pk) {
		setContentId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getContentId() {
		return _contentId;
	}

	public void setContentId(long contentId) {
		_contentId = contentId;
	}

	private String _uuid;
	private long _contentId;
}