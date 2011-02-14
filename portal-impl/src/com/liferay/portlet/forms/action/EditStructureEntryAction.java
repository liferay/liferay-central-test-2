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
import com.liferay.portlet.forms.NoSuchStructureEntryException;
import com.liferay.portlet.forms.StructureEntryDuplicateElementException;
import com.liferay.portlet.forms.StructureEntryDuplicateStructureIdException;
import com.liferay.portlet.forms.StructureEntryNameException;
import com.liferay.portlet.forms.StructureEntryStructureIdException;
import com.liferay.portlet.forms.StructureEntryXsdException;
import com.liferay.portlet.forms.model.FormsStructureEntry;
import com.liferay.portlet.forms.service.FormsStructureEntryServiceUtil;

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
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class EditStructureEntryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		FormsStructureEntry structureEntry = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				structureEntry = updateStructureEntry(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				if (structureEntry != null) {
					boolean saveAndContinue = ParamUtil.getBoolean(
						actionRequest, "saveAndContinue");

					if (saveAndContinue) {
						redirect = getSaveAndContinueRedirect(
							portletConfig, actionRequest, structureEntry,
							redirect);
					}
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchStructureEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.forms.error");
			}
			else if (e instanceof StructureEntryDuplicateElementException ||
					 e instanceof StructureEntryDuplicateStructureIdException ||
					 e instanceof StructureEntryNameException ||
					 e instanceof StructureEntryStructureIdException ||
					 e instanceof StructureEntryXsdException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
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

		try {
			String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getStructureEntry(renderRequest);
			}
		}
		catch (NoSuchStructureEntryException nssee) {

			// Let this slide because the user can manually input a structure id
			// for a new structure that does not yet exist

		}
		catch (Exception e) {
			if (//e instanceof NoSuchStructureEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.forms.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest, "portlet.forms.edit_structure_entry"));
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			FormsStructureEntry structureEntry, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String callback = ParamUtil.getString(actionRequest, "callback");

		PortletURLImpl portletURL = new PortletURLImpl(
			(ActionRequestImpl)actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(actionRequest.getWindowState());

		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("struts_action", "/forms/edit_structure_entry");
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("callback", callback, false);
		portletURL.setParameter(
			"groupId", String.valueOf(structureEntry.getGroupId()), false);
		portletURL.setParameter(
			"structureId", structureEntry.getStructureId(), false);

		return portletURL.toString();
	}

	protected FormsStructureEntry updateStructureEntry(
			ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String structureId = ParamUtil.getString(actionRequest, "structureId");
		boolean autoStructureId = ParamUtil.getBoolean(
			actionRequest, "autoStructureId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String xsd = ParamUtil.getString(actionRequest, "xsd");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			FormsStructureEntry.class.getName(), actionRequest);

		FormsStructureEntry structureEntry = null;

		if (cmd.equals(Constants.ADD)) {

			// Add form structure

			structureEntry = FormsStructureEntryServiceUtil.addStructureEntry(
				groupId, structureId, autoStructureId, name,
				description, xsd, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {

			// Update form structure

			structureEntry =
				FormsStructureEntryServiceUtil.updateStructureEntry(
					groupId, structureId, name, description, xsd,
					serviceContext);
		}

		return structureEntry;
	}

}