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

package com.liferay.portal.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SessionTreeJSClicks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class SessionTreeJSClickAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(request, Constants.CMD);

			String treeId = ParamUtil.getString(request, "treeId");

			long groupId = ParamUtil.getLong(request, "groupId");

			long layoutId = ParamUtil.getLong(request, "layoutId");

			boolean privateLayout = ParamUtil.getBoolean(
				request, "privateLayout");

			boolean recursive = ParamUtil.getBoolean(request, "recursive");

			if (cmd.equals("collapseLayout")) {
				String treeNamespace = _EXPAND_NAMESPACE.concat(treeId);

				SessionTreeJSClicks.removeLayoutNodes(
					request, treeNamespace, layoutId, privateLayout, recursive);
			}
			else if (cmd.equals("expandLayout")) {
				String treeNamespace = _EXPAND_NAMESPACE.concat(treeId);

				SessionTreeJSClicks.addLayoutNodes(
					request, treeNamespace, layoutId, privateLayout, recursive);
			}
			else if (cmd.equals("checkLayout")) {
				String treeNamespace = _CHECK_NAMESPACE.concat(treeId);

				SessionTreeJSClicks.addLayoutNodes(
					request, treeNamespace, layoutId, privateLayout, recursive);
			}
			else if (cmd.equals("uncheckLayout")) {
				String treeNamespace = _CHECK_NAMESPACE.concat(treeId);

				SessionTreeJSClicks.removeLayoutNodes(
					request, treeNamespace, layoutId, privateLayout, recursive);

				if (layoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
					Layout layout = LayoutLocalServiceUtil.getLayout(
						groupId, privateLayout, layoutId);

					for (Layout parentLayout : layout.getAncestors()) {
						SessionTreeJSClicks.removeNode(
							request, treeNamespace,
							String.valueOf(parentLayout.getLayoutId()));
					}
				}
			}
			else if (cmd.equals("expand")) {
				String[] nodeIds = StringUtil.split(
					ParamUtil.getString(request, "nodeIds"));

				SessionTreeJSClicks.addNodes(request, treeId, nodeIds);
			}
			else if (cmd.equals("collapse")) {
				SessionTreeJSClicks.removeNodes(request, treeId);
			}
			else {
				String nodeId = ParamUtil.getString(request, "nodeId");
				boolean openNode = ParamUtil.getBoolean(request, "openNode");

				if (openNode) {
					SessionTreeJSClicks.addNode(request, treeId, nodeId);
				}
				else {
					SessionTreeJSClicks.removeNode(request, treeId, nodeId);
				}
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	private final String _CHECK_NAMESPACE = "CHECK_NAMESPACE_";

	private final String _EXPAND_NAMESPACE = "EXPAND_NAMESPACE_";

}