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
 * The utility for the d d m structure entry link remote service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLinkServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryLinkService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureEntryLinkServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLinkServiceImpl
 * @generated
 */
public class DDMStructureEntryLinkServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLinkServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink addStructureEntryLink(
		java.lang.String structureId, java.lang.String className, long classPK,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructureEntryLink(structureId, className, classPK,
			serviceContext);
	}

	public static void deleteStructureEntryLink(long groupId,
		java.lang.String structureId, long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteStructureEntryLink(groupId, structureId, structureEntryLinkId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getStructureEntryLink(
		long groupId, java.lang.String structureId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getStructureEntryLink(groupId, structureId, className,
			classPK);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateStructureEntryLink(
		long structureEntryLinkId, java.lang.String structureId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructureEntryLink(structureEntryLinkId, structureId,
			groupId, className, classPK);
	}

	public static DDMStructureEntryLinkService getService() {
		if (_service == null) {
			_service = (DDMStructureEntryLinkService)PortalBeanLocatorUtil.locate(DDMStructureEntryLinkService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureEntryLinkServiceUtil.class,
				"_service");
			MethodCache.remove(DDMStructureEntryLinkService.class);
		}

		return _service;
	}

	public void setService(DDMStructureEntryLinkService service) {
		MethodCache.remove(DDMStructureEntryLinkService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMStructureEntryLinkServiceUtil.class,
			"_service");
		MethodCache.remove(DDMStructureEntryLinkService.class);
	}

	private static DDMStructureEntryLinkService _service;
}