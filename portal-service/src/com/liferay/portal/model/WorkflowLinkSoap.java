/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * <a href="WorkflowLinkSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.WorkflowLinkServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.WorkflowLinkServiceSoap
 * @generated
 */
public class WorkflowLinkSoap implements Serializable {
	public static WorkflowLinkSoap toSoapModel(WorkflowLink model) {
		WorkflowLinkSoap soapModel = new WorkflowLinkSoap();

		soapModel.setWorkflowLinkId(model.getWorkflowLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setDefinitionName(model.getDefinitionName());
		soapModel.setDefinitionVersion(model.getDefinitionVersion());

		return soapModel;
	}

	public static WorkflowLinkSoap[] toSoapModels(WorkflowLink[] models) {
		WorkflowLinkSoap[] soapModels = new WorkflowLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WorkflowLinkSoap[][] toSoapModels(WorkflowLink[][] models) {
		WorkflowLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WorkflowLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WorkflowLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WorkflowLinkSoap[] toSoapModels(List<WorkflowLink> models) {
		List<WorkflowLinkSoap> soapModels = new ArrayList<WorkflowLinkSoap>(models.size());

		for (WorkflowLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WorkflowLinkSoap[soapModels.size()]);
	}

	public WorkflowLinkSoap() {
	}

	public long getPrimaryKey() {
		return _workflowLinkId;
	}

	public void setPrimaryKey(long pk) {
		setWorkflowLinkId(pk);
	}

	public long getWorkflowLinkId() {
		return _workflowLinkId;
	}

	public void setWorkflowLinkId(long workflowLinkId) {
		_workflowLinkId = workflowLinkId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getDefinitionName() {
		return _definitionName;
	}

	public void setDefinitionName(String definitionName) {
		_definitionName = definitionName;
	}

	public int getDefinitionVersion() {
		return _definitionVersion;
	}

	public void setDefinitionVersion(int definitionVersion) {
		_definitionVersion = definitionVersion;
	}

	private long _workflowLinkId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _modifiedDate;
	private long _classNameId;
	private String _definitionName;
	private int _definitionVersion;
}