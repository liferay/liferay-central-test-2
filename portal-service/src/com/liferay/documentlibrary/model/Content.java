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

package com.liferay.documentlibrary.model;

import java.io.Serializable;

import java.sql.Blob;

/**
 * @author Shuyang Zhou
 */
public class Content implements Serializable {

	public Content() {
	}

	public Content(
		long contentId, long companyId, String portletId, long groupId,
		long repositoryId, String path, String version, Blob data, long size) {

		_contentId = contentId;
		_companyId = companyId;
		_portletId = portletId;
		_groupId = groupId;
		_repositoryId = repositoryId;
		_path = path;
		_version = version;
		_data = data;
		_size = size;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getContentId() {
		return _contentId;
	}

	public Blob getData() {
		return _data;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getPath() {
		return _path;
	}

	public String getPortletId() {
		return _portletId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public long getSize() {
		return _size;
	}

	public String getVersion() {
		return _version;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setContentId(long contentId) {
		_contentId = contentId;
	}

	public void setData(Blob data) {
		_data = data;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setSize(long size) {
		_size = size;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private long _companyId;
	private long _contentId;
	private Blob _data;
	private long _groupId;
	private String _path;
	private String _portletId;
	private long _repositoryId;
	private long _size;
	private String _version;

}