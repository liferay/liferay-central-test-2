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

package com.liferay.portlet.forms.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.forms.FormDuplicateStructureElementException;
import com.liferay.portlet.forms.FormDuplicateStructureIdException;
import com.liferay.portlet.forms.FormStructureIdException;
import com.liferay.portlet.forms.FormStructureNameException;
import com.liferay.portlet.forms.FormStructureXsdException;
import com.liferay.portlet.forms.NoSuchFormStructureException;
import com.liferay.portlet.forms.model.FormStructure;
import com.liferay.portlet.forms.service.FormStructureServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class EditFormStructureAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		FormStructure formStructure = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				formStructure = updateFormStructure(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				if (formStructure != null) {
					boolean saveAndContinue = ParamUtil.getBoolean(
						actionRequest, "saveAndContinue");

					if (saveAndContinue) {
						redirect = getSaveAndContinueRedirect(
							portletConfig, actionRequest, formStructure,
							redirect);
					}
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchFormStructureException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.forms.error");
			}
			else if (e instanceof FormDuplicateStructureElementException ||
					 e instanceof FormDuplicateStructureIdException ||
					 e instanceof FormStructureIdException ||
					 e instanceof FormStructureNameException ||
					 e instanceof FormStructureXsdException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			SessionMessages.add(actionRequest, "request_processed");
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getFormStructure(renderRequest);
		}
		catch (NoSuchFormStructureException nsfse) {

			// Let this slide because the user can manually input a structure id
			// for a new structure that does not yet exist.

		}
		catch (Exception e) {
			if (//e instanceof NoSuchFormStructureException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.forms.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.forms.edit_form_structure"));
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			FormStructure structure, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String originalRedirect = ParamUtil.getString(
			actionRequest, "originalRedirect");

		String callback = ParamUtil.getString(actionRequest, "callback");

		PortletURLImpl portletURL = new PortletURLImpl(
			(ActionRequestImpl)actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(actionRequest.getWindowState());

		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("struts_action", "/forms/edit_form_structure");
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter("callback", callback, false);
		portletURL.setParameter(
			"groupId", String.valueOf(structure.getGroupId()), false);
		portletURL.setParameter(
			"formStructureId", structure.getFormStructureId(), false);

		return portletURL.toString();
	}

	protected FormStructure updateFormStructure(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String formStructureId = ParamUtil.getString(
			actionRequest, "formStructureId");

		boolean autoFormStructureId = ParamUtil.getBoolean(
			actionRequest, "autoFormStructureId");

		String xsd = ParamUtil.getString(actionRequest, "xsd");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			FormStructure.class.getName(), actionRequest);

		FormStructure formStructure = null;

		if (cmd.equals(Constants.ADD)) {

			// Add form structure

			formStructure = FormStructureServiceUtil.addFormStructure(
				groupId, formStructureId, autoFormStructureId, name,
				description, xsd, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {

			// Update form structure

			formStructure = FormStructureServiceUtil.updateFormStructure(
				groupId, formStructureId, name, description, xsd,
				serviceContext);
		}

		return formStructure;
	}

}