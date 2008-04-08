/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portlet.ActionRequestImpl;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.UploadPortletRequest;
import com.liferay.util.servlet.UploadServletRequest;

import javax.portlet.ActionRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <a href="UploadRequestUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UploadRequestUtil {

	public static UploadPortletRequest getUploadPortletRequest(
		ActionRequest req) {

		ActionRequestImpl actionReq = (ActionRequestImpl)req;

		DynamicServletRequest dynamicReq =
			(DynamicServletRequest)actionReq.getHttpServletRequest();

		HttpServletRequestWrapper reqWrapper =
			(HttpServletRequestWrapper)dynamicReq.getRequest();

		UploadServletRequest uploadReq = getUploadServletRequest(reqWrapper);

		return new UploadPortletRequest(
			uploadReq,
			PortalUtil.getPortletNamespace(actionReq.getPortletName()));
	}

	public static UploadServletRequest getUploadServletRequest(
		HttpServletRequest httpReq) {

		HttpServletRequestWrapper httpReqWrapper = null;

		if (httpReq instanceof HttpServletRequestWrapper) {
			httpReqWrapper = (HttpServletRequestWrapper)httpReq;
		}

		UploadServletRequest uploadReq = null;

		while (uploadReq == null) {

			// Find the underlying UploadServletRequest wrapper. For example,
			// WebSphere wraps all requests with ProtectedServletRequest.

			if (httpReqWrapper instanceof UploadServletRequest) {
				uploadReq = (UploadServletRequest)httpReqWrapper;
			}
			else {
				ServletRequest req = httpReqWrapper.getRequest();

				if (!(req instanceof HttpServletRequestWrapper)) {
					break;
				}
				else {
					httpReqWrapper =
						(HttpServletRequestWrapper)httpReqWrapper.getRequest();
				}
			}
		}

		return uploadReq;
	}

}