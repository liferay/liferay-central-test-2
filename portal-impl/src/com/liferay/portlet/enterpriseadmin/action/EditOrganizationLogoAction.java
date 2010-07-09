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

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.servlet.UploadException;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditOrganizationLogoAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateLogo(actionRequest);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof ImageTypeException ||
				e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.enterprise_admin.error");
			}
			else if (e instanceof UploadException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward(getForward(
			renderRequest, "portlet.enterprise_admin.edit_organization_logo"));
	}

	protected void updateLogo(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		long groupId = ParamUtil.getLong(uploadRequest, "groupId");

		File file = uploadRequest.getFile("fileName");
		byte[] bytes = FileUtil.getBytes(file);

		if ((bytes == null) || (bytes.length == 0)) {
			throw new UploadException();
		}

		LayoutSetServiceUtil.updateLogo(groupId, true, true, file);
		LayoutSetServiceUtil.updateLogo(groupId, false, true, file);
	}

}