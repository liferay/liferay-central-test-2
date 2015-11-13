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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for DDLRecord. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordServiceUtil
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=ddl", "json.web.service.context.path=DDLRecord"}, service = DDLRecordService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDLRecordService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordServiceUtil} to access the d d l record remote service. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteRecord(long recordId) throws PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecord deleteRecordLocale(
		long recordId, java.util.Locale locale,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
		long recordId) throws PortalException;

	public void revertRecord(long recordId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, String,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public void revertRecordVersion(long recordId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}