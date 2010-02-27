/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class ActionUtil {

	public static WikiNode getFirstVisibleNode(RenderRequest renderRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WikiNode node = null;

		int nodesCount = WikiNodeLocalServiceUtil.getNodesCount(
			themeDisplay.getScopeGroupId());

		if (nodesCount == 0) {
			String nodeName = PropsUtil.get(PropsKeys.WIKI_INITIAL_NODE_NAME);

			Layout layout = themeDisplay.getLayout();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				WikiNode.class.getName(), renderRequest);

			serviceContext.setAddCommunityPermissions(true);

			if (layout.isPublicLayout()) {
				serviceContext.setAddGuestPermissions(true);
			}
			else {
				serviceContext.setAddGuestPermissions(false);
			}

			node = WikiNodeLocalServiceUtil.addNode(
				themeDisplay.getUserId(), nodeName, StringPool.BLANK,
				serviceContext);
		}
		else {
			List<WikiNode> nodes = WikiUtil.getNodes(renderRequest);

			if (nodes.size() == 0) {
				throw new PrincipalException();
			}

			node = nodes.get(0);
		}

		renderRequest.setAttribute(WebKeys.WIKI_NODE, node);

		return node;
	}

	public static WikiNode getNode(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		return getNode(request);
	}

	public static WikiNode getNode(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		return getNode(request);
	}

	public static WikiNode getNode(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(request, "nodeId");
		String nodeName = ParamUtil.getString(request, "nodeName");

		WikiNode node = null;

		if (nodeId > 0) {
			node = WikiNodeServiceUtil.getNode(nodeId);
		}
		else if (Validator.isNotNull(nodeName)) {
			node = WikiNodeServiceUtil.getNode(
				themeDisplay.getScopeGroupId(), nodeName);
		}

		request.setAttribute(WebKeys.WIKI_NODE, node);

		return node;
	}

	public static void getPage(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getPage(request);
	}

	public static void getPage(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getPage(request);
	}

	public static void getPage(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(request, "nodeId");
		String title = ParamUtil.getString(request, "title");
		double version = ParamUtil.getDouble(request, "version");

		if (nodeId == 0) {
			WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

			if (node != null) {
				nodeId = node.getNodeId();
			}
		}

		if (Validator.isNull(title)) {
			title = WikiPageConstants.FRONT_PAGE;
		}

		WikiPage page = null;

		try {
			page = WikiPageServiceUtil.getPage(nodeId, title, version);
		}
		catch (NoSuchPageException nspe) {
			if (title.equals(WikiPageConstants.FRONT_PAGE) && (version == 0)) {
				long userId = PortalUtil.getUserId(request);

				if (userId == 0) {
					long companyId = PortalUtil.getCompanyId(request);

					userId = UserLocalServiceUtil.getDefaultUserId(companyId);
				}

				ServiceContext serviceContext = new ServiceContext();

				Layout layout = themeDisplay.getLayout();

				serviceContext.setAddCommunityPermissions(true);

				if (layout.isPublicLayout()) {
					serviceContext.setAddGuestPermissions(true);
				}
				else {
					serviceContext.setAddGuestPermissions(false);
				}

				page = WikiPageLocalServiceUtil.addPage(
					userId, nodeId, title, null, WikiPageConstants.NEW, true,
					serviceContext);
			}
			else {
				throw nspe;
			}
		}

		request.setAttribute(WebKeys.WIKI_PAGE, page);
	}

}