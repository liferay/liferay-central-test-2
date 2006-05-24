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

package com.liferay.portal.cms.servlet;

import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.shared.util.StackTraceUtil;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalInstances;
import com.liferay.util.ExtPropertiesLoader;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import java.io.IOException;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CMSServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Jorge Ferrer
 * @author  Raymond Auge
 *
 */
public class CMSServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		synchronized (CMSServlet.class) {
			super.init(config);

			ServletContext ctx = getServletContext();

			_companyId = ctx.getInitParameter("company_id");

			String redirectsConf = config.getInitParameter("redirects_conf");

			if (redirectsConf != null) {
				_redirectProperties = ExtPropertiesLoader.getInstance(
					redirectsConf).getProperties();
			}

			_redirectsEnabled = GetterUtil.getBoolean(
				config.getInitParameter("redirects_enabled"));
		}
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		if (!PortalInstances.matches()) {
			return;
		}

		String path = GetterUtil.getString(req.getPathInfo());

		if (path.endsWith(StringPool.SLASH)) {
			path = path.substring(0, path.length() - 1);
		}

		if (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1, path.length());
		}

		if ((_redirectProperties != null) && _redirectsEnabled) {
			String redirect = _redirectProperties.getProperty(path);

			if (Validator.isNotNull(redirect)) {
				res.sendRedirect(redirect);

				return;
			}
		}

		String languageId = LanguageUtil.getLanguageId(req);

		// Root path should point to the portal's root path and should not be
		// hard coded like this. However, since the CMSServlet is deprecated and
		// only exists for backwards compatibility, no effort will be made to
		// fix this unless the issue is brought up by the community. This only
		// affects users who are not deploying the portal on the root path and
		// have the CMSServlet deployed on a separate server.

		String rootPath = StringPool.BLANK;

		String content = getContent(path, languageId, rootPath);

		if (Validator.isNotNull(content)) {
			_log.debug("Content found for " + path);

			String mimeType = ParamUtil.getString(
				req, "mimeType", Constants.TEXT_HTML);

			res.setContentType(mimeType);

			ServletOutputStream out = res.getOutputStream();

			try {
				if (!res.isCommitted()) {
					out.print(content);
				}
			}
			catch (Exception e) {
				_log.warn(StackTraceUtil.getStackTrace(e));
			}
			finally {
				out.flush();
				out.close();
			}
		}
		else {
			_log.debug("Content NOT found for " + path);
		}
	}

	protected String getContent(
		String articleId, String languageId, String rootPath) {

		return CMSServletUtil.getContent(
			_companyId, articleId, languageId, rootPath);
	}

	private static Log _log = LogFactory.getLog(CMSServlet.class);

	private String _companyId;
	private Properties _redirectProperties;
	private boolean _redirectsEnabled;

}