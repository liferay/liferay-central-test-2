/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.communities.action;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.util.CommunitiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="UpdatePageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ming-Gih Lam
 */
public class UpdatePageAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long plid = ParamUtil.getLong(request, "plid");

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long layoutId = ParamUtil.getLong(request, "layoutId");
		long parentLayoutId = ParamUtil.getLong(request, "parentLayoutId");

		Layout layout = null;

		if (plid > 0) {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		else if (layoutId > 0) {
			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);
		}
		else if (parentLayoutId > 0) {
			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, parentLayoutId);
		}

		if (layout != null) {
			if (!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.UPDATE)) {

				return null;
			}
		}
		else {
			if (!GroupPermissionUtil.contains(
					permissionChecker, groupId, ActionKeys.MANAGE_LAYOUTS)) {

				return null;
			}
		}

		String cmd = ParamUtil.getString(request, Constants.CMD);

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		if (cmd.equals("add")) {
			String[] array = addPage(themeDisplay, request, response);

			jsonObj.put("layoutId", array[0]);
			jsonObj.put("url", array[1]);
		}
		else if (cmd.equals("delete")) {
			CommunitiesUtil.deleteLayout(request, response);
		}
		else if (cmd.equals("display_order")) {
			updateDisplayOrder(request);
		}
		else if (cmd.equals("name")) {
			updateName(request);
		}
		else if (cmd.equals("parent_layout_id")) {
			updateParentLayoutId(request);
		}
		else if (cmd.equals("priority")) {
			updatePriority(request);
		}

		return jsonObj.toString();
	}

	protected String[] addPage(
			ThemeDisplay themeDisplay, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String doAsUserId = ParamUtil.getString(request, "doAsUserId");
		String doAsUserLanguageId = ParamUtil.getString(
			request, "doAsUserLanguageId");

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(request, "parentLayoutId");
		String name = ParamUtil.getString(request, "name", "New Page");
		String title = StringPool.BLANK;
		String description = StringPool.BLANK;
		String type = LayoutConstants.TYPE_PORTLET;
		boolean hidden = false;
		String friendlyURL = StringPool.BLANK;
		long layoutPrototypeId = ParamUtil.getLong(
			request, "layoutPrototypeId");

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = null;

		if (layoutPrototypeId > 0) {
			LayoutPrototype layoutPrototype =
				LayoutPrototypeServiceUtil.getLayoutPrototype(
					layoutPrototypeId);

			Layout layoutPrototypeLayout = layoutPrototype.getLayout();

			layout = LayoutServiceUtil.addLayout(
				groupId, privateLayout, parentLayoutId, name, title,
				description, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
				serviceContext);

			LayoutServiceUtil.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layoutPrototypeLayout.getTypeSettings());

			ActionUtil.copyPreferences(request, layout, layoutPrototypeLayout);
		}
		else {
			layout = LayoutServiceUtil.addLayout(
				groupId, privateLayout, parentLayoutId, name, title,
				description, type, hidden, friendlyURL, serviceContext);
		}

		String[] eventClasses = StringUtil.split(
			PropsUtil.get(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
				new Filter(layout.getType())));

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE, eventClasses, request,
			response);

		String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);

		if (Validator.isNotNull(doAsUserId)) {
			layoutURL = HttpUtil.addParameter(
				layoutURL, "doAsUserId", themeDisplay.getDoAsUserId());
		}

		if (Validator.isNotNull(doAsUserLanguageId)) {
			layoutURL = HttpUtil.addParameter(
				layoutURL, "doAsUserLanguageId",
				themeDisplay.getDoAsUserLanguageId());
		}

		return new String[] {String.valueOf(layout.getLayoutId()), layoutURL};
	}

	protected void updateDisplayOrder(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(request, "parentLayoutId");
		long[] layoutIds = StringUtil.split(
			ParamUtil.getString(request, "layoutIds"), 0L);

		LayoutServiceUtil.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds);
	}

	protected void updateName(HttpServletRequest request) throws Exception {
		long plid = ParamUtil.getLong(request, "plid");

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long layoutId = ParamUtil.getLong(request, "layoutId");
		String name = ParamUtil.getString(request, "name");
		String languageId = ParamUtil.getString(request, "languageId");

		if (plid <= 0) {
			LayoutServiceUtil.updateName(
				groupId, privateLayout, layoutId, name, languageId);
		}
		else {
			LayoutServiceUtil.updateName(plid, name, languageId);
		}
	}

	protected void updateParentLayoutId(HttpServletRequest request)
		throws Exception {

		long plid = ParamUtil.getLong(request, "plid");

		long parentPlid = ParamUtil.getLong(request, "parentPlid");

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long layoutId = ParamUtil.getLong(request, "layoutId");
		long parentLayoutId = ParamUtil.getLong(
			request, "parentLayoutId",
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (plid <= 0) {
			LayoutServiceUtil.updateParentLayoutId(
				groupId, privateLayout, layoutId, parentLayoutId);
		}
		else {
			LayoutServiceUtil.updateParentLayoutId(plid, parentPlid);
		}
	}

	protected void updatePriority(HttpServletRequest request) throws Exception {
		long plid = ParamUtil.getLong(request, "plid");

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long layoutId = ParamUtil.getLong(request, "layoutId");
		int priority = ParamUtil.getInteger(request, "priority");

		if (plid <= 0) {
			LayoutServiceUtil.updatePriority(
				groupId, privateLayout, layoutId, priority);
		}
		else {
			LayoutServiceUtil.updatePriority(plid, priority);
		}
	}

}