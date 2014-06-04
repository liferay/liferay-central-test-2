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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiUtil;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Farache
 * @author Julio Camarero
 */
public class CompareVersionsAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getNode(renderRequest);
			ActionUtil.getPage(renderRequest);

			compareVersions(renderRequest, renderResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPageException) {
				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward("portlet.wiki.compare_versions");
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		String htmlDiffResult = getHtmlDiffResult(
			resourceRequest, resourceResponse);

		resourceRequest.setAttribute(WebKeys.DIFF_HTML_RESULTS, htmlDiffResult);

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/taglib/ui/diff_version_comparator/diff_html.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void compareVersions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		long nodeId = ParamUtil.getLong(renderRequest, "nodeId");
		String title = ParamUtil.getString(renderRequest, "title");
		double sourceVersion = ParamUtil.getDouble(
			renderRequest, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(
			renderRequest, "targetVersion");

		String htmlDiffResult = getHtmlDiffResult(
			renderRequest, renderResponse);

		renderRequest.setAttribute(WebKeys.DIFF_HTML_RESULTS, htmlDiffResult);
		renderRequest.setAttribute(WebKeys.SOURCE_VERSION, sourceVersion);
		renderRequest.setAttribute(WebKeys.TARGET_VERSION, targetVersion);
		renderRequest.setAttribute(WebKeys.TITLE, title);
		renderRequest.setAttribute(WebKeys.WIKI_NODE_ID, nodeId);
	}

	protected String getHtmlDiffResult(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(portletRequest);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(liferayPortletRequest, "nodeId");
		String title = ParamUtil.getString(liferayPortletRequest, "title");
		double sourceVersion = ParamUtil.getDouble(
			liferayPortletRequest, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(
			liferayPortletRequest, "targetVersion");

		WikiPage sourcePage = WikiPageServiceUtil.getPage(
			nodeId, title, sourceVersion);
		WikiPage targetPage = WikiPageServiceUtil.getPage(
			nodeId, title, targetVersion);

		PortletURL viewPageURL = liferayPortletResponse.createRenderURL();

		viewPageURL.setParameter("struts_action", "/wiki/view");

		WikiNode sourceNode = sourcePage.getNode();

		viewPageURL.setParameter("nodeName", sourceNode.getName());

		PortletURL editPageURL = liferayPortletResponse.createRenderURL();

		editPageURL.setParameter("struts_action", "/wiki/edit_page");
		editPageURL.setParameter("nodeId", String.valueOf(nodeId));
		editPageURL.setParameter("title", title);

		String attachmentURLPrefix = WikiUtil.getAttachmentURLPrefix(
			themeDisplay.getPathMain(), themeDisplay.getPlid(), nodeId, title);

		return WikiUtil.diffHtml(
			sourcePage, targetPage, viewPageURL, editPageURL,
			attachmentURLPrefix);
	}

}