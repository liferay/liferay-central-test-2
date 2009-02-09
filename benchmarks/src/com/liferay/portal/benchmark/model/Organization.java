/*
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.benchmark.model;

/**
 * <a href="Organization.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class Organization {
	public Organization(long organizationId, long companyId, String name,
						String type, boolean recursable) {
		_organizationId = organizationId;
		_companyId = companyId;
		_name = name;
		_type = type;
		_recursable = recursable;
		_statusId = OrganizationStatus.FULL;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public boolean isRecursable() {
		return _recursable;
	}

	public long getRegionId() {
		return _regionId;
	}

	public long getCountryId() {
		return _countryId;
	}

	public OrganizationStatus getStatusId() {
		return _statusId;
	}

	public String getComments() {
		return _comments;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setRecursable(boolean recursable) {
		_recursable = recursable;
	}

	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	public void setCountryId(long countryId) {
		_countryId = countryId;
	}

	public void setStatusId(OrganizationStatus statusId) {
		_statusId = statusId;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	private long _organizationId;
	private long _companyId;
	private long _parentOrganizationId;
	private String _name;
	private String _type;
	private boolean _recursable;
	private long _regionId;
	private long _countryId;
	private OrganizationStatus _statusId;
	private String _comments;
}
