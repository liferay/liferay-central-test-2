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

package com.liferay.portlet.xslcontent.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class ConfigurationActionImpl extends BaseConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateUrls(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/xsl_content/configuration.jsp";
	}

	protected void validateUrls(ActionRequest actionRequest) {
		String xmlUrl = getParameter(actionRequest, "xmlUrl");
		String xslUrl = getParameter(actionRequest, "xslUrl");

		if (xmlUrl.startsWith("file:/")) {
			SessionErrors.add(actionRequest, "xmlUrl");
		}
		else if (xslUrl.startsWith("file:/")) {
			SessionErrors.add(actionRequest, "xslUrl");
		}
	}

}