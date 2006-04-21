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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.spring.BlogsCategoryServiceUtil;
import com.liferay.portlet.blogs.service.spring.BlogsEntryServiceUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ActionUtil {

	public static void getCategory(ActionRequest req) throws Exception {
		HttpServletRequest httpReq =
			((ActionRequestImpl)req).getHttpServletRequest();

		getCategory(httpReq);
	}

	public static void getCategory(RenderRequest req) throws Exception {
		HttpServletRequest httpReq =
			((RenderRequestImpl)req).getHttpServletRequest();

		getCategory(httpReq);
	}

	public static void getCategory(HttpServletRequest req) throws Exception {
		String categoryId = ParamUtil.getString(req, "categoryId");

		BlogsCategory category = null;

		if (Validator.isNotNull(categoryId) &&
			!categoryId.equals(BlogsCategory.DEFAULT_PARENT_CATEGORY_ID)) {

			category = BlogsCategoryServiceUtil.getCategory(categoryId);
		}

		req.setAttribute(WebKeys.BLOGS_CATEGORY, category);
	}

	public static void getEntry(ActionRequest req) throws Exception {
		HttpServletRequest httpReq =
			((ActionRequestImpl)req).getHttpServletRequest();

		getEntry(httpReq);
	}

	public static void getEntry(RenderRequest req) throws Exception {
		HttpServletRequest httpReq =
			((RenderRequestImpl)req).getHttpServletRequest();

		getEntry(httpReq);
	}

	public static void getEntry(HttpServletRequest req) throws Exception {
		String entryId = ParamUtil.getString(req, "entryId");

		BlogsEntry entry = null;

		if (Validator.isNotNull(entryId)) {
			entry = BlogsEntryServiceUtil.getEntry(entryId);
		}

		req.setAttribute(WebKeys.BLOGS_ENTRY, entry);
	}

}