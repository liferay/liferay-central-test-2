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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.io.IOException;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="FriendlyURLServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class FriendlyURLServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		synchronized (FriendlyURLServlet.class) {
			super.init(config);

			ServletContext ctx = getServletContext();

			_companyId = ctx.getInitParameter("company_id");
			_private = GetterUtil.getBoolean(
				config.getInitParameter("private"));
			_user = GetterUtil.getBoolean(
				config.getInitParameter("user"), false);
		}
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		if (!PortalInstances.matches()) {
			return;
		}

		ServletContext ctx = getServletContext();

		// Do not set the entire full main path. See LEP-456.

		//String mainPath = (String)ctx.getAttribute(WebKeys.MAIN_PATH);
		String mainPath = MainServlet.DEFAULT_MAIN_PATH;

		String friendlyURLPath = null;

		if (_private) {
			if (_user) {
				friendlyURLPath = (String)ctx.getAttribute(
					WebKeys.FRIENDLY_URL_PRIVATE_USER_PATH);
			}
			else {
				friendlyURLPath = (String)ctx.getAttribute(
					WebKeys.FRIENDLY_URL_PRIVATE_GROUP_PATH);
			}
		}
		else {
			friendlyURLPath = (String)ctx.getAttribute(
				WebKeys.FRIENDLY_URL_PUBLIC_PATH);
		}

		String redirect = mainPath;

		try {
			redirect = getRedirect(
				req.getPathInfo(), mainPath, req.getParameterMap());

			LastPath lastPath = new LastPath(
				friendlyURLPath, req.getPathInfo(), req.getParameterMap());

			req.setAttribute(WebKeys.LAST_PATH, lastPath);
		}
		catch (NoSuchLayoutException nsle) {
			_log.warn(nsle);

			redirect = PropsUtil.get(
				PropsUtil.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		if (Validator.isNull(redirect)) {
			redirect = mainPath;
		}

		if (redirect.startsWith(StringPool.SLASH)) {
			RequestDispatcher rd = ctx.getRequestDispatcher(redirect);

			if (rd != null) {
				rd.forward(req, res);
			}
		}
		else {
			res.sendRedirect(redirect);
		}
	}

	protected String getCompanyId() {
		return _companyId;
	}

	protected String getRedirect(String path, String mainPath, Map params)
		throws Exception {

		if (Validator.isNull(path)) {
			return mainPath;
		}

		if (!path.startsWith(StringPool.SLASH)) {
			return mainPath;
		}

		// Group friendly URL

		String friendlyURL = null;

		int pos = path.indexOf(StringPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = path.substring(0, pos);
		}
		else {
			if (path.length() > 1) {
				friendlyURL = path.substring(0, path.length());
			}
		}

		if (Validator.isNull(friendlyURL)) {
			return mainPath;
		}

		String ownerId = null;

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getFriendlyURLGroup(
				_companyId, friendlyURL);
		}
		catch (NoSuchGroupException nsge) {
		}

		if (group == null) {
			try {
				if (_private && _user) {
					String screenName = friendlyURL.substring(1);

					User user = UserLocalServiceUtil.getUserByScreenName(
						screenName);

					group = user.getGroup();
				}
				else {
					long groupId = GetterUtil.getLong(friendlyURL.substring(1));

					group = GroupLocalServiceUtil.getGroup(groupId);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
		}

		if (group == null) {
			return mainPath;
		}

		if (_private) {
			ownerId = LayoutImpl.PRIVATE + group.getGroupId();
		}
		else {
			ownerId = LayoutImpl.PUBLIC + group.getGroupId();
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != path.length())) {
			friendlyURL = path.substring(pos, path.length());
		}

		return PortalUtil.getLayoutActualURL(
			ownerId, mainPath, friendlyURL, params);
	}

	private static Log _log = LogFactory.getLog(FriendlyURLServlet.class);

	private String _companyId;
	private boolean _private;
	private boolean _user;

}