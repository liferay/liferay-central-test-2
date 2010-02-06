/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import java.util.List;

/**
 * <a href="OrganizationSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.OrganizationServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.OrganizationServiceSoap
 * @generated
 */
public class OrganizationSoap implements Serializable {
	public static OrganizationSoap toSoapModel(Organization model) {
		OrganizationSoap soapModel = new OrganizationSoap();

		soapModel.setOrganizationId(model.getOrganizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setParentOrganizationId(model.getParentOrganizationId());
		soapModel.setLeftOrganizationId(model.getLeftOrganizationId());
		soapModel.setRightOrganizationId(model.getRightOrganizationId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setRecursable(model.getRecursable());
		soapModel.setRegionId(model.getRegionId());
		soapModel.setCountryId(model.getCountryId());
		soapModel.setStatusId(model.getStatusId());
		soapModel.setComments(model.getComments());

		return soapModel;
	}

	public static OrganizationSoap[] toSoapModels(Organization[] models) {
		OrganizationSoap[] soapModels = new OrganizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OrganizationSoap[][] toSoapModels(Organization[][] models) {
		OrganizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OrganizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OrganizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OrganizationSoap[] toSoapModels(List<Organization> models) {
		List<OrganizationSoap> soapModels = new ArrayList<OrganizationSoap>(models.size());

		for (Organization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OrganizationSoap[soapModels.size()]);
	}

	public OrganizationSoap() {
	}

	public long getPrimaryKey() {
		return _organizationId;
	}

	public void setPrimaryKey(long pk) {
		setOrganizationId(pk);
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	public long getLeftOrganizationId() {
		return _leftOrganizationId;
	}

	public void setLeftOrganizationId(long leftOrganizationId) {
		_leftOrganizationId = leftOrganizationId;
	}

	public long getRightOrganizationId() {
		return _rightOrganizationId;
	}

	public void setRightOrganizationId(long rightOrganizationId) {
		_rightOrganizationId = rightOrganizationId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public boolean getRecursable() {
		return _recursable;
	}

	public boolean isRecursable() {
		return _recursable;
	}

	public void setRecursable(boolean recursable) {
		_recursable = recursable;
	}

	public long getRegionId() {
		return _regionId;
	}

	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	public long getCountryId() {
		return _countryId;
	}

	public void setCountryId(long countryId) {
		_countryId = countryId;
	}

	public int getStatusId() {
		return _statusId;
	}

	public void setStatusId(int statusId) {
		_statusId = statusId;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	private long _organizationId;
	private long _companyId;
	private long _parentOrganizationId;
	private long _leftOrganizationId;
	private long _rightOrganizationId;
	private String _name;
	private String _type;
	private boolean _recursable;
	private long _regionId;
	private long _countryId;
	private int _statusId;
	private String _comments;
}