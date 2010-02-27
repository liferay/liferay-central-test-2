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


/**
 * <a href="Company.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Company table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.CompanyImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyModel
 * @see       com.liferay.portal.model.impl.CompanyImpl
 * @see       com.liferay.portal.model.impl.CompanyModelImpl
 * @generated
 */
public interface Company extends CompanyModel {
	public int compareTo(com.liferay.portal.model.Company company);

	public com.liferay.portal.model.Account getAccount();

	public java.lang.String getAdminName();

	public java.lang.String getAuthType()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.User getDefaultUser();

	public java.lang.String getDefaultWebId();

	public java.lang.String getEmailAddress();

	public com.liferay.portal.model.Group getGroup();

	public java.security.Key getKeyObj();

	public java.util.Locale getLocale();

	public java.lang.String getName();

	public java.lang.String getShardName();

	public java.lang.String getShortName();

	public java.util.TimeZone getTimeZone();

	public boolean hasCompanyMx(java.lang.String emailAddress);

	public boolean isAutoLogin()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isCommunityLogo()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isSendPassword()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangers()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangersVerify()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangersWithMx()
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setKey(java.lang.String key);

	public void setKeyObj(java.security.Key keyObj);
}