/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SitemapUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SitemapServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 *
 */
public class SitemapServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		synchronized (SitemapServlet.class) {
			super.init(config);

			ServletContext ctx = getServletContext();

			_companyId = ctx.getInitParameter("company_id");

			String rootPath = GetterUtil.getString(
				ctx.getInitParameter("root_path"), StringPool.SLASH);

			if (rootPath.equals(StringPool.SLASH)) {
				rootPath = StringPool.BLANK;
			}

			_mainPath = rootPath + MainServlet.DEFAULT_MAIN_PATH;

		}
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		if (!PortalInstances.matches()) {
			return;
		}

		res.setContentType("text/xml; charset=utf-8");
		OutputStreamWriter out = new OutputStreamWriter(res.getOutputStream());

		try {

			String hostName = PortalUtil.getHost(req);
			String ownerId = req.getParameter("ownerId");

			LayoutSet layoutSet = null;
			if (ownerId != null) {
				layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);
			} else {
				layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					_companyId, hostName);
			}

			if (Validator.isNull(hostName)) {
				hostName = PortalUtil.getHost(req);
			}
			String portalURL = PortalUtil.getPortalURL(
				hostName, req.getServerPort(), req.isSecure());

			String sitemap = SitemapUtil.getSitemap(
				layoutSet.getOwnerId(), portalURL + _mainPath);

			if (!res.isCommitted()) {
				out.write(sitemap);
			}
		}
		catch (NoSuchLayoutSetException e) {
			res.sendError(404);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(StackTraceUtil.getStackTrace(e));
			}
		}
		finally {
			out.flush();
			out.close();
		}
	}

	protected String getCompanyId() {
		return _companyId;
	}

	private static Log _log = LogFactory.getLog(SitemapServlet.class);

	private String _companyId;
	private String _mainPath;
}
