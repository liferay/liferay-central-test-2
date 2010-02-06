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

package com.liferay.portlet.documentlibrary.asset;

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
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="DLFileEntryAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class DLFileEntryAssetRenderer extends BaseAssetRenderer {

	public DLFileEntryAssetRenderer(DLFileEntry entry) {
		_entry = entry;
	}

	public long getClassPK() {
		return _entry.getFileEntryId();
	}

	public String getDiscussionPath() {
		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			return "edit_file_entry_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _entry.getGroupId();
	}

	public String getSummary() {
		return HtmlUtil.stripHtml(_entry.getDescription());
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

		if (DLPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_DOCUMENT)) {

			editPortletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.DOCUMENT_LIBRARY);

			editPortletURL.setParameter(
				"struts_action", "/document_library/edit_file_entry");
			editPortletURL.setParameter(
				"folderId", String.valueOf(_entry.getFolderId()));
 			editPortletURL.setParameter(
				"name", String.valueOf(_entry.getName()));
		}

		return editPortletURL;
	}

	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL exportPortletURL = liferayPortletResponse.createActionURL();

		exportPortletURL.setParameter(
			"struts_action", "/asset_publisher/get_file");
		exportPortletURL.setParameter(
			"groupId", String.valueOf(_entry.getGroupId()));
		exportPortletURL.setParameter(
			"folderId", String.valueOf(_entry.getFolderId()));
		exportPortletURL.setParameter(
			"title", String.valueOf(_entry.getTitle()));

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
			"/document_library/get_file?p_l_id=" + themeDisplay.getPlid() +
				"&folderId=" + _entry.getFolderId() + "&title=" +
					HttpUtil.encodeURL(_entry.getTitle());
	}

	public long getUserId() {
		return _entry.getUserId();
	}

	public boolean isConvertible() {
		return true;
	}

	public boolean isPrintable() {
		return false;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, _entry);

			return "/html/portlet/document_library/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private DLFileEntry _entry;

}