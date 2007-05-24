/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.sample.php.portlet;

import com.liferay.util.servlet.StringServletResponse;

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletResponse;

import org.apache.portals.bridges.common.ScriptPostProcess;

/**
 * <a href="PHPServletResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PHPServletResponse extends StringServletResponse {

	public PHPServletResponse(
		HttpServletResponse httpRes, String phpFileParam,
		PortletURL actionURL) {
		super(httpRes);

		_phpFileParam = phpFileParam;
		_actionURL = actionURL;
	}

	public void rewriteAndFlush() throws IOException {
		String result = getString();

		if (getContentType().startsWith("text/")) {
			result = rewriteURLs(result);
		}

		getWriter().write(result.toCharArray());
	}

	private String rewriteURLs(String page) {
		ScriptPostProcess processor = new ScriptPostProcess();

		processor.setInitalPage(new StringBuffer(page));
		processor.postProcessPage(_actionURL, _phpFileParam);
		return processor.getFinalizedPage();
	}

	private String _phpFileParam;
	private PortletURL _actionURL;

}