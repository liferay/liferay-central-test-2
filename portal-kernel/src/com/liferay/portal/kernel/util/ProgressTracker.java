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

package com.liferay.portal.kernel.util;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * <a href="ProgressTracker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class ProgressTracker {

	public static final String PERCENT =
		ProgressTracker.class.getName() + "_PERCENT";

	public ProgressTracker(PortletRequest req, String progressId) {
		_req = req;
		_progressId = progressId;
	}

	public void start() {
		updateProgress(1);
	}

	public void updateProgress(int percentage) {
		PortletSession ses = _req.getPortletSession(true);

		ses.setAttribute(
			PERCENT + _progressId, new Integer(percentage),
			PortletSession.APPLICATION_SCOPE);
	}

	public void finish() {
		PortletSession ses = _req.getPortletSession(true);

		ses.removeAttribute(
			PERCENT + _progressId, PortletSession.APPLICATION_SCOPE);
	}

	private PortletRequest _req;
	private String _progressId;

}