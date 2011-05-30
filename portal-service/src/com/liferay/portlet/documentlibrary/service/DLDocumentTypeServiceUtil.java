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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d l document type remote service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLDocumentTypeServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLDocumentTypeService
 * @see com.liferay.portlet.documentlibrary.service.base.DLDocumentTypeServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLDocumentTypeServiceImpl
 * @generated
 */
public class DLDocumentTypeServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLDocumentTypeServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType addDocumentType(
		long groupId, java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addDocumentType(groupId, name, description,
			ddmStructureIds, serviceContext);
	}

	public static void deleteDocumentType(long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDocumentType(documentTypeId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLDocumentType getDocumentType(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDocumentType(documentTypeId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> getDocumentTypes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDocumentTypes(groupId, start, end);
	}

	public static void updateDocumentType(long documentTypeId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateDocumentType(documentTypeId, name, description,
			ddmStructureIds, serviceContext);
	}

	public static DLDocumentTypeService getService() {
		if (_service == null) {
			_service = (DLDocumentTypeService)PortalBeanLocatorUtil.locate(DLDocumentTypeService.class.getName());

			ReferenceRegistry.registerReference(DLDocumentTypeServiceUtil.class,
				"_service");
			MethodCache.remove(DLDocumentTypeService.class);
		}

		return _service;
	}

	public void setService(DLDocumentTypeService service) {
		MethodCache.remove(DLDocumentTypeService.class);

		_service = service;

		ReferenceRegistry.registerReference(DLDocumentTypeServiceUtil.class,
			"_service");
		MethodCache.remove(DLDocumentTypeService.class);
	}

	private static DLDocumentTypeService _service;
}