/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the remote service interface for Phone. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PhoneServiceUtil
 * @see com.liferay.portal.service.base.PhoneServiceBaseImpl
 * @see com.liferay.portal.service.impl.PhoneServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PhoneService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PhoneServiceUtil} to access the phone remote service. Add custom service methods to {@link com.liferay.portal.service.impl.PhoneServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addPhone(String, long,
	String, String, int, boolean, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.Phone addPhone(java.lang.String className,
		long classPK, java.lang.String number, java.lang.String extension,
		long typeId, boolean primary) throws PortalException;

	public com.liferay.portal.model.Phone addPhone(java.lang.String className,
		long classPK, java.lang.String number, java.lang.String extension,
		long typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deletePhone(long phoneId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Phone getPhone(long phoneId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Phone> getPhones(
		java.lang.String className, long classPK) throws PortalException;

	public com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, long typeId,
		boolean primary) throws PortalException;
}