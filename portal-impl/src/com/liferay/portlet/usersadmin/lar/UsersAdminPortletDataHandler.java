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

package com.liferay.portlet.usersadmin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.service.OrganizationLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 * @author David Gonzalez
 */
public class UsersAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "users_admin";

	public UsersAdminPortletDataHandler() {
		setDataLevel(DataLevel.PORTAL);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(Organization.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "organizations", true, true, null,
				Organization.class.getName()));
		setSupportsDataStrategyCopyAsNew(false);

		XStreamAliasRegistryUtil.register(
			OrganizationImpl.class, "Organization");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getOrganizations(
				portletDataContext.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID);

		for (Organization organization : organizations) {
			OrganizationLocalServiceUtil.deleteOrganization(organization);
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortalPermissions();

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			OrganizationLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortalPermissions();

		Element organizationsElement =
			portletDataContext.getImportDataGroupElement(Organization.class);

		List<Element> organizationElements = organizationsElement.elements();

		for (Element organizationElement : organizationElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, organizationElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			OrganizationLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performCount();
	}

}