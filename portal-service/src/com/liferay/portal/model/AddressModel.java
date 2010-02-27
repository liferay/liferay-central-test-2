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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="AddressModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Address table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Address
 * @see       com.liferay.portal.model.impl.AddressImpl
 * @see       com.liferay.portal.model.impl.AddressModelImpl
 * @generated
 */
public interface AddressModel extends BaseModel<Address> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getAddressId();

	public void setAddressId(long addressId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getClassName();

	public long getClassNameId();

	public void setClassNameId(long classNameId);

	public long getClassPK();

	public void setClassPK(long classPK);

	public String getStreet1();

	public void setStreet1(String street1);

	public String getStreet2();

	public void setStreet2(String street2);

	public String getStreet3();

	public void setStreet3(String street3);

	public String getCity();

	public void setCity(String city);

	public String getZip();

	public void setZip(String zip);

	public long getRegionId();

	public void setRegionId(long regionId);

	public long getCountryId();

	public void setCountryId(long countryId);

	public int getTypeId();

	public void setTypeId(int typeId);

	public boolean getMailing();

	public boolean isMailing();

	public void setMailing(boolean mailing);

	public boolean getPrimary();

	public boolean isPrimary();

	public void setPrimary(boolean primary);

	public Address toEscapedModel();

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

	public int compareTo(Address address);

	public int hashCode();

	public String toString();

	public String toXmlString();
}