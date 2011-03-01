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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the social request interpreter local service. This utility wraps {@link com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestInterpreterLocalService
 * @see com.liferay.portlet.social.service.base.SocialRequestInterpreterLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl
 * @generated
 */
public class SocialRequestInterpreterLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public static java.lang.String getIdentifier() {
		return getService().getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public static void setIdentifier(java.lang.String identifier) {
		getService().setIdentifier(identifier);
	}

	public static void addRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		getService().addRequestInterpreter(requestInterpreter);
	}

	public static void deleteRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		getService().deleteRequestInterpreter(requestInterpreter);
	}

	public static com.liferay.portlet.social.model.SocialRequestFeedEntry interpret(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return getService().interpret(request, themeDisplay);
	}

	public static void processConfirmation(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		getService().processConfirmation(request, themeDisplay);
	}

	public static void processRejection(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		getService().processRejection(request, themeDisplay);
	}

	public static SocialRequestInterpreterLocalService getService() {
		if (_service == null) {
			_service = (SocialRequestInterpreterLocalService)PortalBeanLocatorUtil.locate(SocialRequestInterpreterLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialRequestInterpreterLocalServiceUtil.class,
				"_service");
			MethodCache.remove(SocialRequestInterpreterLocalService.class);
		}

		return _service;
	}

	public void setService(SocialRequestInterpreterLocalService service) {
		MethodCache.remove(SocialRequestInterpreterLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(SocialRequestInterpreterLocalServiceUtil.class,
			"_service");
		MethodCache.remove(SocialRequestInterpreterLocalService.class);
	}

	private static SocialRequestInterpreterLocalService _service;
}