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
import java.util.List;

/**
 * <a href="OrganizationSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.OrganizationServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.OrganizationServiceSoap
 *
 */
public class OrganizationSoap implements Serializable {
	public static OrganizationSoap toSoapModel(Organization model) {
		OrganizationSoap soapModel = new OrganizationSoap();
		soapModel.setOrganizationId(model.getOrganizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setParentOrganizationId(model.getParentOrganizationId());
		soapModel.setName(model.getName());
		soapModel.setRecursable(model.getRecursable());
		soapModel.setRegionId(model.getRegionId());
		soapModel.setCountryId(model.getCountryId());
		soapModel.setStatusId(model.getStatusId());
		soapModel.setComments(model.getComments());

		return soapModel;
	}

	public static OrganizationSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			Organization model = (Organization)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (OrganizationSoap[])soapModels.toArray(new OrganizationSoap[0]);
	}

	public OrganizationSoap() {
	}

	public String getPrimaryKey() {
		return _organizationId;
	}

	public void setPrimaryKey(String pk) {
		setOrganizationId(pk);
	}

	public String getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(String organizationId) {
		_organizationId = organizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
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

	public String getRegionId() {
		return _regionId;
	}

	public void setRegionId(String regionId) {
		_regionId = regionId;
	}

	public String getCountryId() {
		return _countryId;
	}

	public void setCountryId(String countryId) {
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

	private String _organizationId;
	private long _companyId;
	private String _parentOrganizationId;
	private String _name;
	private boolean _recursable;
	private String _regionId;
	private String _countryId;
	private int _statusId;
	private String _comments;
}