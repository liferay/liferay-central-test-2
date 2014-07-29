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

package com.liferay.portlet.passwordpoliciesadmin.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyImpl;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Daniela Zapata Riesco
 */
public class PasswordPolicyPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "password_policies_admin";

	public PasswordPolicyPortletDataHandler() {
		setDataLevel(DataLevel.PORTAL);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(PasswordPolicy.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "password-policies", true, true, null,
				PasswordPolicy.class.getName()));
		setSupportsDataStrategyCopyAsNew(false);

		XStreamAliasRegistryUtil.register(
			PasswordPolicyImpl.class, "PasswordPolicy");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				PasswordPolicyPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		PasswordPolicyLocalServiceUtil.deleteNondefaultPasswordPolicies(
			portletDataContext.getCompanyId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			getPasswordPolicyActionableDynamicQuery(portletDataContext, true);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(RESOURCE_NAME);

		Element passwordPoliciesElement =
			portletDataContext.getImportDataGroupElement(PasswordPolicy.class);

		List<Element> passwordPolicyElements =
			passwordPoliciesElement.elements();

		for (Element passwordPolicyElement : passwordPolicyElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, passwordPolicyElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			getPasswordPolicyActionableDynamicQuery(portletDataContext, false);

		actionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getPasswordPolicyActionableDynamicQuery(
		final PortletDataContext portletDataContext, final boolean export) {

		ActionableDynamicQuery actionableDynamicQuery =
			PasswordPolicyLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					if (!export) {
						return;
					}

					PasswordPolicy passwordPolicy = (PasswordPolicy)object;

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, passwordPolicy);
				}

			});

		return actionableDynamicQuery;
	}

	protected static final String RESOURCE_NAME =
		"com.liferay.portlet.passwordpoliciesadmin";

}