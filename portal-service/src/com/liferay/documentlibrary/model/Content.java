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

import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

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

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Content)) {
			return false;
		}

		Content content = (Content)obj;

		if ((_contentId == content._contentId) &&
			(_companyId == content._companyId) &&
			Validator.equals(_portletId, content._portletId) &&
			(_groupId == content._groupId) &&
			(_repositoryId == content._repositoryId) &&
			Validator.equals(_path, content._path) &&
			Validator.equals(_version, content._version) &&
			(_size == content._size)) {

			return true;
		}

		return false;
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

	public int hashCode() {
		return toString().hashCode();
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

	public String toString() {
		return _toString();
	}

	private String _toString() {
		if (_toString == null) {
			StringBundler sb = new StringBundler(19);

			sb.append("{contentId=");
			sb.append(_contentId);
			sb.append(", companyId=");
			sb.append(_companyId);
			sb.append(", portletId=");
			sb.append(_portletId);
			sb.append(", groupId=");
			sb.append(_groupId);
			sb.append(", repositoryId=");
			sb.append(_repositoryId);
			sb.append(", path=");
			sb.append(_path);
			sb.append(", version=");
			sb.append(_version);
			sb.append(", dataType=");
			if (_data instanceof OutputBlob) {
				sb.append("transient");
			}
			else {
				sb.append("persistent");
			}
			sb.append(", size=");
			sb.append(_size);
			sb.append("}");

			_toString = sb.toString();
		}

		return _toString;
	}

	private long _companyId;
	private long _contentId;
	private Blob _data;
	private long _groupId;
	private String _path;
	private String _portletId;
	private long _repositoryId;
	private long _size;
	private String _toString;
	private String _version;

}