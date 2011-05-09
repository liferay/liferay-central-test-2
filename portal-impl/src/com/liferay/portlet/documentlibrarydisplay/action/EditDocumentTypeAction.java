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

package com.liferay.portlet.documentlibrarydisplay.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 */
public class EditDocumentTypeAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateDocumentType(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteDocumentType(actionRequest, actionResponse);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchDocumentTypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.document_library.error");
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

		DLDocumentType documentType = null;

		try {
			long documentTypeId = ParamUtil.getLong(
				renderRequest, "documentTypeId");

			if (documentTypeId > 0) {
				documentType = DLDocumentTypeServiceUtil.getDocumentType(
					documentTypeId);

				renderRequest.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_DOCUMENT_TYPE, documentType);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchDocumentTypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest, "portlet.document_library.edit_document_type"));
	}

	protected void deleteDocumentType(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long documentTypeId = ParamUtil.getLong(
			actionRequest, "documentTypeId");

		DLDocumentTypeServiceUtil.deleteDocumentType(documentTypeId);
	}

	protected void updateDocumentType(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long documentTypeId = ParamUtil.getLong(
			actionRequest, "documentTypeId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		List<Long> ddmStructureIdsList = new ArrayList<Long>();

		for (int i = 1; i < 4; i++) {
			long ddmStructureId = ParamUtil.getLong(
				actionRequest, "ddmStructureId" + i);

			if (ddmStructureId > 0) {
				ddmStructureIdsList.add(ddmStructureId);
			}
		}

		long[] ddmStructureIdsArray = ArrayUtil.toArray(
			ddmStructureIdsList.toArray(new Long[ddmStructureIdsList.size()]));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLDocumentType.class.getName(), actionRequest);

		if (documentTypeId <= 0) {

			// Add document type

			DLDocumentTypeServiceUtil.addDocumentType(
				themeDisplay.getScopeGroupId(), name, description,
				ddmStructureIdsArray, serviceContext);
		}
		else {

			// Update document type

			DLDocumentTypeServiceUtil.updateDocumentType(
				documentTypeId, name, description, serviceContext);
		}
	}

}