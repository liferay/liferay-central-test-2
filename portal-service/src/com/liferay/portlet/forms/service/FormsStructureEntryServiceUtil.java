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
 * The utility for the forms structure entry remote service. This utility wraps {@link com.liferay.portlet.forms.service.impl.FormsStructureEntryServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryService
 * @see com.liferay.portlet.forms.service.base.FormsStructureEntryServiceBaseImpl
 * @see com.liferay.portlet.forms.service.impl.FormsStructureEntryServiceImpl
 * @generated
 */
public class FormsStructureEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.forms.service.impl.FormsStructureEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.forms.model.FormsStructureEntry addEntry(
		long groupId, java.lang.String formStrucureId,
		boolean autoFormStrucureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(groupId, formStrucureId, autoFormStrucureId, name,
			description, xsd, serviceContext);
	}

	public static void deleteEntry(long groupId,
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(groupId, formStructureId);
	}

	public static com.liferay.portlet.forms.model.FormsStructureEntry fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchByG_F(groupId, formStructureId);
	}

	public static com.liferay.portlet.forms.model.FormsStructureEntry getEntry(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(groupId, formStructureId);
	}

	public static com.liferay.portlet.forms.model.FormsStructureEntry updateEntry(
		long groupId, java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(groupId, formStructureId, name, description,
			xsd, serviceContext);
	}

	public static FormsStructureEntryService getService() {
		if (_service == null) {
			_service = (FormsStructureEntryService)PortalBeanLocatorUtil.locate(FormsStructureEntryService.class.getName());

			ReferenceRegistry.registerReference(FormsStructureEntryServiceUtil.class,
				"_service");
			MethodCache.remove(FormsStructureEntryService.class);
		}

		return _service;
	}

	public void setService(FormsStructureEntryService service) {
		MethodCache.remove(FormsStructureEntryService.class);

		_service = service;

		ReferenceRegistry.registerReference(FormsStructureEntryServiceUtil.class,
			"_service");
		MethodCache.remove(FormsStructureEntryService.class);
	}

	private static FormsStructureEntryService _service;
}