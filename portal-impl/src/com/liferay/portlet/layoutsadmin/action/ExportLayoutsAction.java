/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.sites.action.ActionUtil;

import java.util.ArrayList;
import java.util.Date;
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
 * @author Raymond Augé
 */
public class ExportLayoutsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long[] layoutIds = getLayoutIds(
				groupId, privateLayout,
				ParamUtil.getString(actionRequest, "layoutIds"));
			String fileName = ParamUtil.getString(
				actionRequest, "exportFileName");

			DateRange dateRange = ExportImportHelperUtil.getDateRange(
				actionRequest, groupId, privateLayout, 0, null);

			Date startDate = dateRange.getStartDate();
			Date endDate = dateRange.getEndDate();

			if (Validator.isNotNull(cmd)) {
				LayoutServiceUtil.exportLayoutsAsFileInBackground(
					fileName, groupId, privateLayout, layoutIds,
					actionRequest.getParameterMap(), startDate, endDate,
					fileName);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				if (startDate != null) {
					actionResponse.setRenderParameter(
						"startDate", String.valueOf(startDate.getTime()));
				}

				if (endDate != null) {
					actionResponse.setRenderParameter(
						"endDate", String.valueOf(endDate.getTime()));
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			SessionErrors.add(actionRequest, e.getClass());

			String pagesRedirect = ParamUtil.getString(
				actionRequest, "pagesRedirect");

			sendRedirect(actionRequest, actionResponse, pagesRedirect);
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.export_layouts"));
	}

	protected void addLayoutIds(
			List<Long> layoutIds, long groupId, boolean privateLayout,
			long layoutId)
		throws Exception {

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, layoutId);

		for (Layout layout : layouts) {
			layoutIds.add(layout.getLayoutId());

			addLayoutIds(
				layoutIds, layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());
		}
	}

	protected long[] getLayoutIds(
			long groupId, boolean privateLayout, String layoutIdsJSON)
		throws Exception {

		if (Validator.isNull(layoutIdsJSON)) {
			return new long[0];
		}

		List<Long> layoutIds = new ArrayList<Long>();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(layoutIdsJSON);

		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long layoutId = jsonObject.getLong("layoutId");

			if (layoutId > 0) {
				layoutIds.add(layoutId);
			}

			if (jsonObject.getBoolean("includeChildren")) {
				addLayoutIds(layoutIds, groupId, privateLayout, layoutId);
			}
		}

		return ArrayUtil.toArray(layoutIds.toArray(new Long[layoutIds.size()]));
	}

	private static Log _log = LogFactoryUtil.getLog(ExportLayoutsAction.class);

}