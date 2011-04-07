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

package com.liferay.portlet.dynamicdatalists.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.dynamicdatalists.NoSuchEntryItemException;
import com.liferay.portlet.dynamicdatalists.model.DDLEntry;
import com.liferay.portlet.dynamicdatalists.model.DDLEntryItem;
import com.liferay.portlet.dynamicdatalists.service.DDLEntryItemLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
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
public class EditEntryItemAction extends PortletAction {

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
			if (e instanceof NoSuchEntryItemException ||
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
			ActionUtil.getEntryItem(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.dynamic_data_lists.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest, "portlet.dynamic_data_lists.edit_entry_item"));
	}

	protected void deleteListItem(ActionRequest actionRequest)
		throws Exception {

		long entryItemId = ParamUtil.getLong(actionRequest, "entryItemId");

		DDLEntryItemLocalServiceUtil.deleteEntryItem(entryItemId);
	}

	protected DDLEntryItem updateListItem(ActionRequest actionRequest)
		throws Exception {

		long entryItemId = ParamUtil.getLong(actionRequest, "entryItemId");

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		DDLEntry entry = DDLEntryLocalServiceUtil.getEntry(entryId);

		DDMStructure ddmStructure = entry.getDDMStructure();

		Set<String> fieldNames = ddmStructure.getFieldNames();

		Fields fields = new Fields();

		for (String name : fieldNames) {
			Field field = new Field();

			field.setName(name);

			String value = ParamUtil.getString(actionRequest, name);

			field.setValue(value);

			fields.put(field);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLEntryItem.class.getName(), actionRequest);

		DDLEntryItem entryItem = null;

		if (entryItemId <= 0) {
			entryItem = DDLEntryItemLocalServiceUtil.addEntryItem(
				entryId, fields, serviceContext);
		}
		else {
			entryItem = DDLEntryItemLocalServiceUtil.updateEntryItem(
				entryItemId, fields, serviceContext);
		}

		return entryItem;
	}

}