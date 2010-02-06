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

import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="OrganizationModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Organization_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Organization
 * @see       com.liferay.portal.model.impl.OrganizationImpl
 * @see       com.liferay.portal.model.impl.OrganizationModelImpl
 * @generated
 */
public interface OrganizationModel extends BaseModel<Organization> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getOrganizationId();

	public void setOrganizationId(long organizationId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getParentOrganizationId();

	public void setParentOrganizationId(long parentOrganizationId);

	public long getLeftOrganizationId();

	public void setLeftOrganizationId(long leftOrganizationId);

	public long getRightOrganizationId();

	public void setRightOrganizationId(long rightOrganizationId);

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

	public boolean getRecursable();

	public boolean isRecursable();

	public void setRecursable(boolean recursable);

	public long getRegionId();

	public void setRegionId(long regionId);

	public long getCountryId();

	public void setCountryId(long countryId);

	public int getStatusId();

	public void setStatusId(int statusId);

	public String getComments();

	public void setComments(String comments);

	public Organization toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(Organization organization);

	public int hashCode();

	public String toString();

	public String toXmlString();
}