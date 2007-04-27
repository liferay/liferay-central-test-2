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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="DataTrackerSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.DataTrackerServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.DataTrackerServiceSoap
 *
 */
public class DataTrackerSoap implements Serializable {
	public static DataTrackerSoap toSoapModel(DataTracker model) {
		DataTrackerSoap soapModel = new DataTrackerSoap();
		soapModel.setDataTrackerId(model.getDataTrackerId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreatedOn(model.getCreatedOn());
		soapModel.setCreatedByUserId(model.getCreatedByUserId());
		soapModel.setCreatedByUserName(model.getCreatedByUserName());
		soapModel.setUpdatedOn(model.getUpdatedOn());
		soapModel.setUpdatedBy(model.getUpdatedBy());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setActive(model.getActive());

		return soapModel;
	}

	public static DataTrackerSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			DataTracker model = (DataTracker)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (DataTrackerSoap[])soapModels.toArray(new DataTrackerSoap[0]);
	}

	public DataTrackerSoap() {
	}

	public String getPrimaryKey() {
		return _dataTrackerId;
	}

	public void setPrimaryKey(String pk) {
		setDataTrackerId(pk);
	}

	public String getDataTrackerId() {
		return _dataTrackerId;
	}

	public void setDataTrackerId(String dataTrackerId) {
		_dataTrackerId = dataTrackerId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreatedOn() {
		return _createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		_createdOn = createdOn;
	}

	public long getCreatedByUserId() {
		return _createdByUserId;
	}

	public void setCreatedByUserId(long createdByUserId) {
		_createdByUserId = createdByUserId;
	}

	public String getCreatedByUserName() {
		return _createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		_createdByUserName = createdByUserName;
	}

	public Date getUpdatedOn() {
		return _updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		_updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return _updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		_updatedBy = updatedBy;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getClassPK() {
		return _classPK;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private String _dataTrackerId;
	private long _companyId;
	private Date _createdOn;
	private long _createdByUserId;
	private String _createdByUserName;
	private Date _updatedOn;
	private String _updatedBy;
	private String _className;
	private String _classPK;
	private boolean _active;
}