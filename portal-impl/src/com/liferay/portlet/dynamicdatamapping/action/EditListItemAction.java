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
import com.liferay.portlet.dynamicdatamapping.NoSuchListItemException;
import com.liferay.portlet.dynamicdatamapping.model.DDMList;
import com.liferay.portlet.dynamicdatamapping.model.DDMListItem;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMListItemLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMListLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Marcellus Tavares
 */
public class EditListItemAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateListItem(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteListItem(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchListItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.dynamic_data_mapping.error");
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
				ActionUtil.getListItem(renderRequest);
			}
		}
		catch (NoSuchListItemException nslie) {

			// Let this slide because the user can manually input a list item
			// key for a new list that does not yet exist

		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
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
				renderRequest, "portlet.dynamic_data_mapping.edit_list_item"));
	}

	protected void deleteListItem(ActionRequest actionRequest)
		throws Exception {

		long listItemId = ParamUtil.getLong(actionRequest, "listItemId");

		DDMListItemLocalServiceUtil.deleteListItem(listItemId);
	}

	protected DDMListItem updateListItem(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long listId = ParamUtil.getLong(actionRequest, "listId");
		long listItemId = ParamUtil.getLong(actionRequest, "listItemId");

		DDMList list = DDMListLocalServiceUtil.getList(listId);

		DDMStructure structure = list.getStructure();

		Set<String> fieldNames = structure.getFieldNames();

		Fields fields = new Fields();

		for (String name : fieldNames) {
			Field field = new Field();

			field.setName(name);
			field.setValue(ParamUtil.getString(actionRequest, name));

			fields.put(field);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMListItem.class.getName(), actionRequest);

		DDMListItem listItem = null;

		if (cmd.equals(Constants.ADD)) {
			listItem = DDMListItemLocalServiceUtil.addListItem(
				listId, fields, serviceContext);
		}
		else {
			listItem = DDMListItemLocalServiceUtil.updateListItem(
				listItemId, fields, serviceContext);
		}

		return listItem;
	}

}