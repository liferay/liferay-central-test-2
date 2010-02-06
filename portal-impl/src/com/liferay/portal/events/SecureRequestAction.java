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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="SecureRequestAction.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This action ensures that all requests are secure. Extend this and override
 * the <code>isRequiresSecure</code> method to programmatically decide when a
 * request requires HTTPS.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SecureRequestAction extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			if (request.isSecure()) {
				return;
			}

			if (!isRequiresSecure(request)) {
				return;
			}

			if (response.isCommitted()) {
				return;
			}

			String redirect = getRedirect(request);

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect " + redirect);
			}

			if (redirect != null) {
				response.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected String getRedirect(HttpServletRequest request) {
		String unsecureCompleteURL = PortalUtil.getCurrentCompleteURL(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Unsecure URL " + unsecureCompleteURL);
		}

		String secureCompleteURL = StringUtil.replaceFirst(
			unsecureCompleteURL, Http.HTTP_WITH_SLASH, Http.HTTPS_WITH_SLASH);

		if (_log.isDebugEnabled()) {
			_log.debug("Secure URL " + secureCompleteURL);
		}

		if (unsecureCompleteURL.equals(secureCompleteURL)) {
			return null;
		}
		else {
			return secureCompleteURL;
		}
	}

	protected boolean isRequiresSecure(HttpServletRequest request) {
		return _REQUIRES_SECURE;
	}

	private static final boolean _REQUIRES_SECURE = true;

	private static Log _log = LogFactoryUtil.getLog(SecureRequestAction.class);

}