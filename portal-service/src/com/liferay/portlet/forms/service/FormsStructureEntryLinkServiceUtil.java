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
 * The utility for the forms structure entry link remote service. This utility wraps {@link com.liferay.portlet.forms.service.impl.FormsStructureEntryLinkServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryLinkService
 * @see com.liferay.portlet.forms.service.base.FormsStructureEntryLinkServiceBaseImpl
 * @see com.liferay.portlet.forms.service.impl.FormsStructureEntryLinkServiceImpl
 * @generated
 */
public class FormsStructureEntryLinkServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.forms.service.impl.FormsStructureEntryLinkServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static FormsStructureEntryLinkService getService() {
		if (_service == null) {
			_service = (FormsStructureEntryLinkService)PortalBeanLocatorUtil.locate(FormsStructureEntryLinkService.class.getName());

			ReferenceRegistry.registerReference(FormsStructureEntryLinkServiceUtil.class,
				"_service");
			MethodCache.remove(FormsStructureEntryLinkService.class);
		}

		return _service;
	}

	public void setService(FormsStructureEntryLinkService service) {
		MethodCache.remove(FormsStructureEntryLinkService.class);

		_service = service;

		ReferenceRegistry.registerReference(FormsStructureEntryLinkServiceUtil.class,
			"_service");
		MethodCache.remove(FormsStructureEntryLinkService.class);
	}

	private static FormsStructureEntryLinkService _service;
}