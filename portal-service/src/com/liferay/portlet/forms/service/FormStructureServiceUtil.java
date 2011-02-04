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

package com.liferay.portlet.forms.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the form structure remote service. This utility wraps {@link com.liferay.portlet.forms.service.impl.FormStructureServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormStructureService
 * @see com.liferay.portlet.forms.service.base.FormStructureServiceBaseImpl
 * @see com.liferay.portlet.forms.service.impl.FormStructureServiceImpl
 * @generated
 */
public class FormStructureServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.forms.service.impl.FormStructureServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.forms.model.FormStructure addFormStructure(
		long groupId, java.lang.String formStructureId,
		boolean autoFormStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFormStructure(groupId, formStructureId,
			autoFormStructureId, name, description, xsd, serviceContext);
	}

	public static void deleteFormStructure(long groupId,
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFormStructure(groupId, formStructureId);
	}

	public static com.liferay.portlet.forms.model.FormStructure fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchByG_F(groupId, formStructureId);
	}

	public static com.liferay.portlet.forms.model.FormStructure getFormStructure(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFormStructure(groupId, formStructureId);
	}

	public static com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		long groupId, java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFormStructure(groupId, formStructureId, name,
			description, xsd, serviceContext);
	}

	public static FormStructureService getService() {
		if (_service == null) {
			_service = (FormStructureService)PortalBeanLocatorUtil.locate(FormStructureService.class.getName());

			ReferenceRegistry.registerReference(FormStructureServiceUtil.class,
				"_service");
			MethodCache.remove(FormStructureService.class);
		}

		return _service;
	}

	public void setService(FormStructureService service) {
		MethodCache.remove(FormStructureService.class);

		_service = service;

		ReferenceRegistry.registerReference(FormStructureServiceUtil.class,
			"_service");
		MethodCache.remove(FormStructureService.class);
	}

	private static FormStructureService _service;
}