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

package com.liferay.portlet.dynamicdatamapping.action;

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
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureEntryException;
import com.liferay.portlet.dynamicdatamapping.StructureEntryDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureEntryDuplicateStructureIdException;
import com.liferay.portlet.dynamicdatamapping.StructureEntryNameException;
import com.liferay.portlet.dynamicdatamapping.StructureEntryStructureIdException;
import com.liferay.portlet.dynamicdatamapping.StructureEntryXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureEntryServiceUtil;

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

		DDMStructureEntry structureEntry = null;

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

				setForward(actionRequest, "portlet.dynamic_data_mapping.error");
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

				return mapping.findForward(
					"portlet.dynamic_data_mapping.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest,
				"portlet.dynamic_data_mapping.edit_structure_entry"));
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			DDMStructureEntry structureEntry, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String callback = ParamUtil.getString(actionRequest, "callback");

		PortletURLImpl portletURL = new PortletURLImpl(
			(ActionRequestImpl)actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(actionRequest.getWindowState());

		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter(
			"struts_action", "/dynamic_data_mapping/edit_structure_entry");
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("callback", callback, false);
		portletURL.setParameter(
			"groupId", String.valueOf(structureEntry.getGroupId()), false);
		portletURL.setParameter(
			"structureId", structureEntry.getStructureId(), false);

		return portletURL.toString();
	}

	protected DDMStructureEntry updateStructureEntry(
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
			DDMStructureEntry.class.getName(), actionRequest);

		DDMStructureEntry structureEntry = null;

		if (cmd.equals(Constants.ADD)) {

			// Add form structure

			structureEntry = DDMStructureEntryServiceUtil.addStructureEntry(
				groupId, structureId, autoStructureId, name,
				description, xsd, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {

			// Update form structure

			structureEntry = DDMStructureEntryServiceUtil.updateStructureEntry(
				groupId, structureId, name, description, xsd, serviceContext);
		}

		return structureEntry;
	}

}