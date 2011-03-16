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
 * The utility for the d d m structure link remote service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLinkServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLinkServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLinkServiceImpl
 * @generated
 */
public class DDMStructureLinkServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLinkServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink addStructureLink(
		java.lang.String structureKey, java.lang.String className,
		long classPK, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructureLink(structureKey, className, classPK,
			serviceContext);
	}

	public static void deleteStructureLink(long groupId,
		java.lang.String structureKey, long structureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureLink(groupId, structureKey, structureLinkId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink getStructureLink(
		long groupId, java.lang.String structureKey,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getStructureLink(groupId, structureKey, className, classPK);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink updateStructureLink(
		long structureLinkId, java.lang.String structureKey, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructureLink(structureLinkId, structureKey, groupId,
			className, classPK);
	}

	public static DDMStructureLinkService getService() {
		if (_service == null) {
			_service = (DDMStructureLinkService)PortalBeanLocatorUtil.locate(DDMStructureLinkService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureLinkServiceUtil.class,
				"_service");
			MethodCache.remove(DDMStructureLinkService.class);
		}

		return _service;
	}

	public void setService(DDMStructureLinkService service) {
		MethodCache.remove(DDMStructureLinkService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMStructureLinkServiceUtil.class,
			"_service");
		MethodCache.remove(DDMStructureLinkService.class);
	}

	private static DDMStructureLinkService _service;
}