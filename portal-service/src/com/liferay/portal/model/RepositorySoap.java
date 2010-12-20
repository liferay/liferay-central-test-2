/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.RepositoryServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.RepositoryServiceSoap
 * @generated
 */
public class RepositorySoap implements Serializable {
	public static RepositorySoap toSoapModel(Repository model) {
		RepositorySoap soapModel = new RepositorySoap();

		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setPortletKey(model.getPortletKey());
		soapModel.setMappedFolderId(model.getMappedFolderId());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());

		return soapModel;
	}

	public static RepositorySoap[] toSoapModels(Repository[] models) {
		RepositorySoap[] soapModels = new RepositorySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RepositorySoap[][] toSoapModels(Repository[][] models) {
		RepositorySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RepositorySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RepositorySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RepositorySoap[] toSoapModels(List<Repository> models) {
		List<RepositorySoap> soapModels = new ArrayList<RepositorySoap>(models.size());

		for (Repository model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RepositorySoap[soapModels.size()]);
	}

	public RepositorySoap() {
	}

	public long getPrimaryKey() {
		return _repositoryId;
	}

	public void setPrimaryKey(long pk) {
		setRepositoryId(pk);
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getPortletKey() {
		return _portletKey;
	}

	public void setPortletKey(String portletKey) {
		_portletKey = portletKey;
	}

	public long getMappedFolderId() {
		return _mappedFolderId;
	}

	public void setMappedFolderId(long mappedFolderId) {
		_mappedFolderId = mappedFolderId;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	private long _repositoryId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _groupId;
	private String _name;
	private String _description;
	private String _portletKey;
	private long _mappedFolderId;
	private int _type;
	private String _typeSettings;
}