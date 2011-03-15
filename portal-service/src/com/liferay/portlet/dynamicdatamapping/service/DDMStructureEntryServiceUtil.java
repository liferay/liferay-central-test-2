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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d m structure entry remote service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureEntryServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryServiceImpl
 * @generated
 */
public class DDMStructureEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry addStructureEntry(
		long groupId, java.lang.String structureId, boolean autoStrucureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructureEntry(groupId, structureId, autoStrucureId,
			name, description, xsd, serviceContext);
	}

	public static void deleteStructureEntry(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureEntry(groupId, structureId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry getStructureEntry(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntry(groupId, structureId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry updateStructureEntry(
		long groupId, java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructureEntry(groupId, structureId, name,
			description, xsd, serviceContext);
	}

	public static DDMStructureEntryService getService() {
		if (_service == null) {
			_service = (DDMStructureEntryService)PortalBeanLocatorUtil.locate(DDMStructureEntryService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureEntryServiceUtil.class,
				"_service");
			MethodCache.remove(DDMStructureEntryService.class);
		}

		return _service;
	}

	public void setService(DDMStructureEntryService service) {
		MethodCache.remove(DDMStructureEntryService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMStructureEntryServiceUtil.class,
			"_service");
		MethodCache.remove(DDMStructureEntryService.class);
	}

	private static DDMStructureEntryService _service;
}