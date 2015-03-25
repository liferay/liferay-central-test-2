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

package com.liferay.portlet.tck.bridge;

import com.liferay.portal.struts.StrutsActionRegistryUtil;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component
public class PortletTCKBridge {

	@Activate
	protected void activate() {
		StrutsActionRegistryUtil.register(_PATH, new PortletTCKStrutsAction());
	}

	@Deactivate
	protected void deactivate() {
		StrutsActionRegistryUtil.unregister(_PATH);
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	private static final String _PATH = "/portal/tck";

}