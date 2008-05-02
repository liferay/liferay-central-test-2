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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.compression.CompressionFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="FacebookServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class FacebookServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		try {
			String[] facebookData = getFacebookData(req, res);

			if (facebookData == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), req, res);
			}
			else {
				String appId = facebookData[0];
				String redirect = facebookData[1];

				req.setAttribute(WebKeys.FACEBOOK_APP_ID, appId);
				req.setAttribute(CompressionFilter.SKIP_FILTER, Boolean.TRUE);

				ServletContext ctx = getServletContext();

				RequestDispatcher rd = ctx.getRequestDispatcher(redirect);

				StringServletResponse stringServletRes =
					new StringServletResponse(res);

				rd.forward(req, stringServletRes);

				String fbml = stringServletRes.getString();

				if (req.getAttribute(WebKeys.FACEBOOK_APP_ID) != null) {
					fbml = fixFbml(fbml);
				}

				ServletResponseUtil.write(res, fbml);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, req, res);
		}
	}

	protected String fixFbml(String fbml) {
		fbml = StringUtil.replace(
			fbml,
			new String[] {
				"<nobr>",
				"</nobr>"
			},
			new String[] {
				StringPool.BLANK,
				StringPool.BLANK
			});

		return fbml;
	}

	protected String[] getFacebookData(
		HttpServletRequest req, HttpServletResponse res) {

		String path = GetterUtil.getString(req.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(StringPool.SLASH, 1);

		if (pos == -1) {
			return null;
		}

		String appId = path.substring(1, pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Application ID " + appId);
		}

		if (Validator.isNull(appId)) {
			return null;
		}

		String redirect = path.substring(pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		if (Validator.isNull(redirect)) {
			return null;
		}

		return new String[] {appId, redirect};
	}

	private static Log _log = LogFactory.getLog(FacebookServlet.class);

}