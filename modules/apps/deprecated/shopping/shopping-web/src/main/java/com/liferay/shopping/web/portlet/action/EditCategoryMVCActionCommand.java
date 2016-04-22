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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.CategoryNameException;
import com.liferay.shopping.exception.NoSuchCategoryException;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.service.ShoppingCategoryServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"mvc.command.name=/shopping/edit_category",
		"mvc.command.name=/shopping/select_category"
	},
	service = MVCActionCommand.class
)
public class EditCategoryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		ShoppingCategoryServiceUtil.deleteCategory(categoryId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCategory(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCategory(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCategoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CategoryNameException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		boolean mergeWithParentCategory = ParamUtil.getBoolean(
			actionRequest, "mergeWithParentCategory");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ShoppingCategory.class.getName(), actionRequest);

		if (categoryId <= 0) {

			// Add category

			ShoppingCategoryServiceUtil.addCategory(
				parentCategoryId, name, description, serviceContext);
		}
		else {

			// Update category

			ShoppingCategoryServiceUtil.updateCategory(
				categoryId, parentCategoryId, name, description,
				mergeWithParentCategory, serviceContext);
		}
	}

}