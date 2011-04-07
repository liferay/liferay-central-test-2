/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d l entry remote service. This utility wraps {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLEntryService
 * @see com.liferay.portlet.dynamicdatalists.service.base.DDLEntryServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryServiceImpl
 * @generated
 */
public class DDLEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry addEntry(
		long groupId, long ddmStructureId, java.lang.String entryKey,
		boolean autoEntryKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(groupId, ddmStructureId, entryKey, autoEntryKey,
			nameMap, description, serviceContext);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static void deleteEntry(long groupId, java.lang.String entryKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(groupId, entryKey);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry getEntry(
		long groupId, java.lang.String entryKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(groupId, entryKey);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry updateEntry(
		long groupId, long ddmStructureId, java.lang.String entryKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(groupId, ddmStructureId, entryKey, nameMap,
			description, serviceContext);
	}

	public static DDLEntryService getService() {
		if (_service == null) {
			_service = (DDLEntryService)PortalBeanLocatorUtil.locate(DDLEntryService.class.getName());

			ReferenceRegistry.registerReference(DDLEntryServiceUtil.class,
				"_service");
			MethodCache.remove(DDLEntryService.class);
		}

		return _service;
	}

	public void setService(DDLEntryService service) {
		MethodCache.remove(DDLEntryService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDLEntryServiceUtil.class,
			"_service");
		MethodCache.remove(DDLEntryService.class);
	}

	private static DDLEntryService _service;
}