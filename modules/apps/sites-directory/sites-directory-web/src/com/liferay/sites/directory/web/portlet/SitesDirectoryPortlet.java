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

package com.liferay.sites.directory.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.sites.directory.web.upgrade.SitesDirectoryWebUpgrade;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.sites.directory.web",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true;",
		"com.liferay.portlet.css-class-wrapper=portlet-sites-directory",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.icon=/icons/navigation.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.single-page-application=false",
		"com.liferay.portlet.struts-path=sites_directory",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Sites Directory",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class SitesDirectoryPortlet extends MVCPortlet {

	@Reference(unbind = "-")
	protected void setSitesDirectoryWebUpgrade(
		SitesDirectoryWebUpgrade sitesDirectoryWebUpgrade) {
	}

}