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
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public interface DDMTemplateManager {

	public static final String TEMPLATE_MODE_CREATE = "create";

	public static final String TEMPLATE_TYPE_DISPLAY = "display";

	public static final String TEMPLATE_VERSION_DEFAULT = "1.0";

	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String templateKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException;

	public DDMTemplate fetchTemplate(
		long groupId, long classNameId, String templateKey);

	public Class<?> getDDMTemplateModelClass();

	public DDMTemplate getTemplate(long templateId) throws PortalException;

	public List<DDMTemplate> getTemplates(
		long[] groupIds, long classNameId, long classPK);

	public boolean hasPermission(
			PermissionChecker permissionChecker, long groupId, long templateId,
			String portletId, String actionId)
		throws PortalException;

}