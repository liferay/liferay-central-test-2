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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class ViewOrganizationAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					renderRequest);

			String organizationView = ParamUtil.getString(
				renderRequest, "organizationView");

			if (Validator.isNotNull(organizationView)) {
				preferences.setValue(
					PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS,
					"organization-view",	organizationView);
			}
			else {
				organizationView = preferences.getValue(
					PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS,
					"organization-view",
					PropsValues.ORGANIZATIONS_VIEWS_DEFAULT);
			}

			if (!ArrayUtil.contains(
					PropsValues.ORGANIZATIONS_VIEWS, organizationView)) {

				organizationView = PropsValues.ORGANIZATIONS_VIEWS_DEFAULT;

				preferences.setValue(
					PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS,
					"organization-view", organizationView);
			}

			renderRequest.setAttribute("tabs1", "organizations");

			return mapping.findForward(
				"portlet.enterprise_admin.view");
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}
	}

}