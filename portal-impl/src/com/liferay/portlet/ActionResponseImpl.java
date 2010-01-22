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

package com.liferay.portlet;

import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

/**
 * <a href="ActionResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ActionResponseImpl
	extends StateAwareResponseImpl implements ActionResponse {

	public String getLifecycle() {
		return PortletRequest.ACTION_PHASE;
	}

	public void sendRedirect(String location) {
		if ((location == null) ||
			(!location.startsWith("/") && (location.indexOf("://") == -1) &&
				(!location.startsWith("wsrp_rewrite?")))) {

			throw new IllegalArgumentException(
				location + " is not a valid redirect");
		}

		// This is needed because app servers will try to prepend a host if
		// they see an invalid URL

		if (location.startsWith("wsrp_rewrite?")) {
			location = "http://wsrp-rewrite-holder?" + location;
		}

		if (isCalledSetRenderParameter()) {
			throw new IllegalStateException(
				"Set render parameter has already been called");
		}

		setRedirectLocation(location);
	}

	public void sendRedirect(String location, String renderUrlParamName) {
	}

}