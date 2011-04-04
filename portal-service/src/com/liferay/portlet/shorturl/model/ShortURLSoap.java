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

package com.liferay.portlet.shorturl.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.shorturl.service.http.ShortURLServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.shorturl.service.http.ShortURLServiceSoap
 * @generated
 */
public class ShortURLSoap implements Serializable {
	public static ShortURLSoap toSoapModel(ShortURL model) {
		ShortURLSoap soapModel = new ShortURLSoap();

		soapModel.setShortURLId(model.getShortURLId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setOriginalURL(model.getOriginalURL());
		soapModel.setHash(model.getHash());
		soapModel.setDescriptor(model.getDescriptor());

		return soapModel;
	}

	public static ShortURLSoap[] toSoapModels(ShortURL[] models) {
		ShortURLSoap[] soapModels = new ShortURLSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ShortURLSoap[][] toSoapModels(ShortURL[][] models) {
		ShortURLSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ShortURLSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ShortURLSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ShortURLSoap[] toSoapModels(List<ShortURL> models) {
		List<ShortURLSoap> soapModels = new ArrayList<ShortURLSoap>(models.size());

		for (ShortURL model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ShortURLSoap[soapModels.size()]);
	}

	public ShortURLSoap() {
	}

	public long getPrimaryKey() {
		return _shortURLId;
	}

	public void setPrimaryKey(long pk) {
		setShortURLId(pk);
	}

	public long getShortURLId() {
		return _shortURLId;
	}

	public void setShortURLId(long shortURLId) {
		_shortURLId = shortURLId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getOriginalURL() {
		return _originalURL;
	}

	public void setOriginalURL(String originalURL) {
		_originalURL = originalURL;
	}

	public String getHash() {
		return _hash;
	}

	public void setHash(String hash) {
		_hash = hash;
	}

	public String getDescriptor() {
		return _descriptor;
	}

	public void setDescriptor(String descriptor) {
		_descriptor = descriptor;
	}

	private long _shortURLId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _originalURL;
	private String _hash;
	private String _descriptor;
}