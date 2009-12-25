/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.DiffResult;
import com.liferay.portal.kernel.util.DiffUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiUtil;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="CompareVersionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Julio Camarero
 */
public class CompareVersionsAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getNode(renderRequest);
			ActionUtil.getPage(renderRequest);

			compareVersions(renderRequest, renderResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPageException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward("portlet.wiki.compare_versions");
	}

	protected void compareVersions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(renderRequest, "nodeId");

		String title = ParamUtil.getString(renderRequest, "title");

		double sourceVersion = ParamUtil.getDouble(
			renderRequest, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(
			renderRequest, "targetVersion");
		String type = ParamUtil.getString(renderRequest, "type", "escape");

		WikiPage sourcePage = WikiPageServiceUtil.getPage(
			nodeId, title, sourceVersion);
		WikiPage targetPage = WikiPageServiceUtil.getPage(
			nodeId, title, targetVersion);

		if (type.equals("html")) {
			WikiNode sourceNode = sourcePage.getNode();

			PortletURL viewPageURL = renderResponse.createRenderURL();

			viewPageURL.setParameter("struts_action", "/wiki/view");
			viewPageURL.setParameter("nodeName", sourceNode.getName());

			PortletURL editPageURL = renderResponse.createRenderURL();

			editPageURL.setParameter("struts_action", "/wiki/edit_page");
			editPageURL.setParameter("nodeId", String.valueOf(nodeId));
			editPageURL.setParameter("title", title);

			String attachmentURLPrefix =
				themeDisplay.getPathMain() + "/wiki/get_page_attachment?" +
					"p_l_id=" + themeDisplay.getPlid() + "&nodeId=" + nodeId +
						"&title=" + HttpUtil.encodeURL(title) + "&fileName=";

			String htmlDiffResult = WikiUtil.diffHtml(
				sourcePage, targetPage, viewPageURL, editPageURL,
				attachmentURLPrefix);

			renderRequest.setAttribute(
				WebKeys.DIFF_HTML_RESULTS, htmlDiffResult);
		}
		else {
			String sourceContent = sourcePage.getContent();
			String targetContent = targetPage.getContent();

			sourceContent = WikiUtil.processContent(sourceContent);
			targetContent = WikiUtil.processContent(targetContent);

			if (type.equals("escape")) {
				sourceContent = HtmlUtil.escape(sourceContent);
				targetContent = HtmlUtil.escape(targetContent);
			}
			else if (type.equals("strip")) {
				sourceContent = HtmlUtil.extractText(sourceContent);
				targetContent = HtmlUtil.extractText(targetContent);
			}

			List<DiffResult>[] diffResults = DiffUtil.diff(
				new UnsyncStringReader(sourceContent),
				new UnsyncStringReader(targetContent));

			renderRequest.setAttribute(WebKeys.DIFF_RESULTS, diffResults);
		}

		renderRequest.setAttribute(WebKeys.WIKI_NODE_ID, nodeId);
		renderRequest.setAttribute(WebKeys.TITLE, title);
		renderRequest.setAttribute(WebKeys.SOURCE_VERSION, sourceVersion);
		renderRequest.setAttribute(WebKeys.TARGET_VERSION, targetVersion);
	}

}