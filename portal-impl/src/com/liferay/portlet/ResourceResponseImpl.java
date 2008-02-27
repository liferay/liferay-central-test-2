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

package com.liferay.portlet;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ResourceResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourceResponseImpl
	extends MimeResponseImpl implements ResourceResponse {

	public PortletURLImpl createPortletURLImpl(
		String portletName, String lifecycle) {

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			throw new IllegalStateException(
				"Unable to create an action URL from a resource response");
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			throw new IllegalStateException(
				"Unable to create a render URL from a resource response");
		}

		return super.createPortletURLImpl(portletName, lifecycle);
	}

	public int getBufferSize() {
		return 0;
	}

	public String getLifecycle() {
		return PortletRequest.RESOURCE_PHASE;
	}

	public void setCharacterEncoding(String charset) {
		_res.setCharacterEncoding(charset);
	}

	public void setLocale(Locale locale) {
		_res.setLocale(locale);
	}

	public void setContentLength(int length) {
		_res.setContentLength(length);
	}

	protected ResourceResponseImpl() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}
	}

	protected void init(
		PortletRequestImpl req, HttpServletResponse res, String portletName,
		long companyId, long plid) {

		super.init(req, res, portletName, companyId, plid);

		_res = res;
	}

	protected void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		super.recycle();

		_res = null;
	}

	private static Log _log = LogFactory.getLog(ResourceResponseImpl.class);

	private HttpServletResponse _res;

}