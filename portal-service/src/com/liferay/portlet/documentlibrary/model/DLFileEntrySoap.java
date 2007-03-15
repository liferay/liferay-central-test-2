/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.documentlibrary.service.http.DLFileEntryServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.http.DLFileEntryServiceSoap
 *
 */
public class DLFileEntrySoap implements Serializable {
	public static DLFileEntrySoap toSoapModel(DLFileEntry model) {
		DLFileEntrySoap soapModel = new DLFileEntrySoap();
		soapModel.setFolderId(model.getFolderId());
		soapModel.setName(model.getName());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setVersionUserId(model.getVersionUserId());
		soapModel.setVersionUserName(model.getVersionUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setVersion(model.getVersion());
		soapModel.setSize(model.getSize());
		soapModel.setReadCount(model.getReadCount());
		soapModel.setExtraSettings(model.getExtraSettings());

		return soapModel;
	}

	public static DLFileEntrySoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			DLFileEntry model = (DLFileEntry)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (DLFileEntrySoap[])soapModels.toArray(new DLFileEntrySoap[0]);
	}

	public DLFileEntrySoap() {
	}

	public DLFileEntryPK getPrimaryKey() {
		return new DLFileEntryPK(_folderId, _name);
	}

	public void setPrimaryKey(DLFileEntryPK pk) {
		setFolderId(pk.folderId);
		setName(pk.name);
	}

	public String getFolderId() {
		return _folderId;
	}

	public void setFolderId(String folderId) {
		_folderId = folderId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(String companyId) {
		_companyId = companyId;
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public String getVersionUserId() {
		return _versionUserId;
	}

	public void setVersionUserId(String versionUserId) {
		_versionUserId = versionUserId;
	}

	public String getVersionUserName() {
		return _versionUserName;
	}

	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
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

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
	}

	public int getReadCount() {
		return _readCount;
	}

	public void setReadCount(int readCount) {
		_readCount = readCount;
	}

	public String getExtraSettings() {
		return _extraSettings;
	}

	public void setExtraSettings(String extraSettings) {
		_extraSettings = extraSettings;
	}

	private String _folderId;
	private String _name;
	private String _companyId;
	private String _userId;
	private String _userName;
	private String _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private double _version;
	private int _size;
	private int _readCount;
	private String _extraSettings;
}