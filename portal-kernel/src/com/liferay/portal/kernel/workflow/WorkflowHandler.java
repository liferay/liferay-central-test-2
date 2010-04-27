/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="WorkflowHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Macerllus Tavares
 * @author Juan Fernández
 */
public interface WorkflowHandler {

	public static final String TYPE_CONTENT = "content";

	public static final String TYPE_DOCUMENT = "document";

	public static final String TYPE_UNKNOWN = "unknown";

	public String getClassName();

	public String getIconPath(LiferayPortletRequest liferayPortletRequest);

	public String getTitle(long classPK);

	public String getType();

	public PortletURL getURLEdit(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse);

	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK,
			Object model, Map<String, Serializable> contextVariables)
		throws PortalException, SystemException;

	public Object updateStatus(
			long companyId, long groupId, long userId, long classPK, int status)
		throws PortalException, SystemException;

}