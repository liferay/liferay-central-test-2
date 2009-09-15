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

package com.liferay.portlet.imagegallery.asset;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.permission.IGPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * <a href="IGImageAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class IGImageAssetRenderer extends BaseAssetRenderer {

	public IGImageAssetRenderer(IGImage image) {
		_image = image;
	}

	public long getClassPK() {
		return _image.getImageId();
	}

	public long getGroupId() {
		return _image.getGroupId();
	}

	public String getSummary() {
		return HtmlUtil.stripHtml(_image.getDescription());
	}

	public String getTitle() {
		return _image.getName();
	}

	public PortletURL getURLEdit(PortletRequest portletRequest) {
		PortletRequestImpl portletRequestImpl =
			(PortletRequestImpl)portletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)portletRequestImpl.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL editPortletURL = null;

		if (IGPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_IMAGE)) {

			editPortletURL = new PortletURLImpl(
				portletRequestImpl, PortletKeys.IMAGE_GALLERY,
				themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

			editPortletURL.setParameter(
				"struts_action", "/image_gallery/edit_image");
			editPortletURL.setParameter(
				"folderId", String.valueOf(_image.getFolderId()));
 			editPortletURL.setParameter(
				 "imageId", String.valueOf(_image.getImageId()));
		}

		return editPortletURL;
	}

	public String getURLViewInContext(
			PortletRequest portletRequest, String noSuchEntryRedirect)
		throws Exception {

		PortletRequestImpl portletRequestImpl =
			(PortletRequestImpl)portletRequest;

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL viewPortletURL = new PortletURLImpl(
			portletRequestImpl, PortletKeys.IMAGE_GALLERY,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		viewPortletURL.setWindowState(WindowState.MAXIMIZED);

		viewPortletURL.setParameter("struts_action", "/image_gallery/view");
 		viewPortletURL.setParameter(
			"folderId", String.valueOf(_image.getFolderId()));

		return viewPortletURL.toString();
	}

	public long getUserId() {
		return _image.getUserId();
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String template) {

		if (template.equals(TEMPLATE_FULL_CONTENT) ||
			template.equals(TEMPLATE_ABSTRACT)) {

			renderRequest.setAttribute(WebKeys.IMAGE_GALLERY_IMAGE, _image);

			return "/html/portlet/image_gallery/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private IGImage _image;

}