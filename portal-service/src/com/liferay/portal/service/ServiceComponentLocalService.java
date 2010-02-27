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

package com.liferay.portal.service;

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="ServiceComponentLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portal.service.impl.ServiceComponentLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ServiceComponentLocalService {
	public com.liferay.portal.model.ServiceComponent addServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent createServiceComponent(
		long serviceComponentId);

	public void deleteServiceComponent(long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.ServiceComponent getServiceComponent(
		long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.ServiceComponent> getServiceComponents(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getServiceComponentsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent updateServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent updateServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void destroyServiceComponent(
		javax.servlet.ServletContext servletContext,
		java.lang.ClassLoader classLoader)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent initServiceComponent(
		javax.servlet.ServletContext servletContext,
		java.lang.ClassLoader classLoader, java.lang.String buildNamespace,
		long buildNumber, long buildDate, boolean buildAutoUpgrade)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void upgradeDB(java.lang.ClassLoader classLoader,
		java.lang.String buildNamespace, long buildNumber,
		boolean buildAutoUpgrade,
		com.liferay.portal.model.ServiceComponent previousServiceComponent,
		java.lang.String tablesSQL, java.lang.String sequencesSQL,
		java.lang.String indexesSQL) throws java.lang.Exception;

	public void verifyDB()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}