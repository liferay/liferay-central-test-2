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

package com.liferay.portlet.wiki.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiPermission;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="WikiPageAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class WikiPageAssetRenderer extends BaseAssetRenderer {

	public WikiPageAssetRenderer(WikiPage page) {
		_page = page;
	}

	public long getClassPK() {
		return _page.getPageId();
	}

	public String getDiscussionPath() {
		if (PropsValues.WIKI_PAGE_COMMENTS_ENABLED) {
			return "edit_page_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _page.getGroupId();
	}

	public String getSummary() {
		String content = _page.getContent();

		if (_page.getFormat().equals("html")) {
			content = HtmlUtil.stripHtml(content);
		}

		return content;
	}

	public String getTitle() {
		return _page.getTitle();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL editPortletURL = null;

		if (WikiPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_PAGE)) {

			editPortletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.WIKI);

			editPortletURL.setParameter("struts_action", "/wiki/edit_page");
			editPortletURL.setParameter(
				"nodeId", String.valueOf(_page.getNodeId()));
			editPortletURL.setParameter("title", _page.getTitle());
		}

		return editPortletURL;
	}

	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL exportPortletURL = liferayPortletResponse.createActionURL();

		exportPortletURL.setParameter(
			"struts_action", "/asset_publisher/export_wiki_page");
		exportPortletURL.setParameter(
			"nodeId", String.valueOf(_page.getNodeId()));
		exportPortletURL.setParameter("title", _page.getTitle());

		return exportPortletURL;
	}

	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() +
			"/wiki/find_page?pageResourcePrimKey=" + _page.getResourcePrimKey();
	}

	public long getUserId() {
		return _page.getUserId();
	}

	public boolean isConvertible() {
		return true;
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			renderRequest.setAttribute(WebKeys.WIKI_PAGE, _page);

			return "/html/portlet/wiki/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private WikiPage _page;

}