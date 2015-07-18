/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMTemplateManagerUtil {

	public static DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String templateKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException {

		return _ddmTemplateManager.addTemplate(
			userId, groupId, classNameId, classPK, resourceClassNameId,
			templateKey, nameMap, descriptionMap, type, mode, language, script,
			cacheable, smallImage, smallImageURL, smallImageFile,
			serviceContext);
	}

	public static DDMTemplate fetchTemplate(
		long groupId, long classNameId, String templateKey) {

		return _ddmTemplateManager.fetchTemplate(
			groupId, classNameId, templateKey);
	}

	public static Class<?> getDDMTemplateModelClass() {
		return _ddmTemplateManager.getDDMTemplateModelClass();
	}

	public static DDMTemplate getTemplate(long templateId)
		throws PortalException {

		return _ddmTemplateManager.getTemplate(templateId);
	}

	public static List<DDMTemplate> getTemplates(
		long[] groupIds, long classNameId, long classPK) {

		return _ddmTemplateManager.getTemplates(groupIds, classNameId, classPK);
	}

	public static boolean hasPermission(
			PermissionChecker permissionChecker, long groupId, long templateId,
			String portletId, String actionId)
		throws PortalException {

		return _ddmTemplateManager.hasPermission(
			permissionChecker, groupId, templateId, portletId, actionId);
	}

	private static final DDMTemplateManager _ddmTemplateManager =
		ProxyFactory.newServiceTrackedInstance(DDMTemplateManager.class);

}