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

package com.liferay.portlet.blogs.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="BlogsEntryAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class BlogsEntryAssetRenderer extends BaseAssetRenderer {

	public BlogsEntryAssetRenderer(BlogsEntry entry) {
		_entry = entry;
	}

	public long getClassPK() {
		return _entry.getEntryId();
	}

	public String getDiscussionPath() {
		if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
			return "edit_entry_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _entry.getGroupId();
	}

	public String getSummary() {
		return HtmlUtil.stripHtml(_entry.getContent());
	}

	public String getTitle() {
		return _entry.getTitle();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL editPortletURL = null;

		if (BlogsPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			editPortletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.BLOGS);

			editPortletURL.setParameter("struts_action", "/blogs/edit_entry");
			editPortletURL.setParameter(
				"entryId", String.valueOf(_entry.getEntryId()));
		}

		return editPortletURL;
	}

	public String getUrlTitle() {
		return _entry.getUrlTitle();
	}

	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
			"/blogs/find_entry?noSuchEntryRedirect=" +
				HttpUtil.encodeURL(noSuchEntryRedirect) + "&entryId=" +
					_entry.getEntryId();
	}

	public long getUserId() {
		return _entry.getUserId();
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			renderRequest.setAttribute(WebKeys.BLOGS_ENTRY, _entry);

			return "/html/portlet/blogs/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private BlogsEntry _entry;

}