/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.language.LanguageUtil;
import com.liferay.portlet.LiferayWindowState;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.service.spring.BlogsCategoryLocalServiceUtil;
import com.liferay.util.StringPool;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.jsp.PageContext;

/**
 * <a href="BlogsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsUtil {

	public static String getBreadcrumbs(
			String categoryId, PageContext pageContext, RenderRequest req,
			RenderResponse res)
		throws Exception {

		BlogsCategory category = null;

		try {
			category = BlogsCategoryLocalServiceUtil.getCategory(categoryId);
		}
		catch (Exception e) {
		}

		return getBreadcrumbs(category, pageContext, req, res);
	}

	public static String getBreadcrumbs(
			BlogsCategory category, PageContext pageContext, RenderRequest req,
			RenderResponse res)
		throws Exception {

		PortletURL categoriesURL = res.createRenderURL();

		WindowState windowState = req.getWindowState();

		if (windowState.equals(LiferayWindowState.POP_UP)) {
			categoriesURL.setWindowState(LiferayWindowState.POP_UP);

			categoriesURL.setParameter(
				"struts_action", "/blogs/select_category");
		}
		else {
			categoriesURL.setWindowState(WindowState.MAXIMIZED);

			categoriesURL.setParameter("struts_action", "/blogs/view");
			categoriesURL.setParameter("tabs1", "categories");
		}

		String categoriesLink =
			"<a href=\"" + categoriesURL.toString() + "\">" +
				LanguageUtil.get(pageContext, "categories") + "</a>";

		if (category == null) {
			return categoriesLink;
		}

		String breadcrumbs = StringPool.BLANK;

		if (category != null) {
			for (int i = 0;; i++) {
				PortletURL portletURL = res.createRenderURL();

				if (windowState.equals(LiferayWindowState.POP_UP)) {
					portletURL.setWindowState(LiferayWindowState.POP_UP);

					portletURL.setParameter(
						"struts_action", "/blogs/select_category");
				}
				else {
					portletURL.setWindowState(WindowState.MAXIMIZED);

					portletURL.setParameter("struts_action", "/blogs/view");
					portletURL.setParameter("tabs1", "categories");
					portletURL.setParameter(
						"categoryId", category.getCategoryId());
				}

				String categoryLink =
					"<a href=\"" + portletURL.toString() + "\">" +
						category.getName() + "</a>";

				if (i == 0) {
					breadcrumbs = categoryLink;
				}
				else {
					breadcrumbs = categoryLink + " &raquo; " + breadcrumbs;
				}

				if (category.isRoot()) {
					break;
				}

				category = BlogsCategoryLocalServiceUtil.getCategory(
					category.getParentCategoryId());
			}
		}

		breadcrumbs = categoriesLink + " &raquo; " + breadcrumbs;

		return breadcrumbs;
	}

}