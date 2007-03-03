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
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.io.IOException;
import java.io.OutputStreamWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SoftwareCatalogServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class SoftwareCatalogServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		synchronized (SoftwareCatalogServlet.class) {
			super.init(config);

			ServletContext ctx = getServletContext();

			_companyId = ctx.getInitParameter("company_id");

			_rootPath = GetterUtil.getString(
				ctx.getInitParameter("root_path"), StringPool.SLASH);

			if (_rootPath.equals(StringPool.SLASH)) {
				_rootPath = StringPool.BLANK;
			}

			_imagePath = _rootPath + "/image";

		}
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		if (!PortalInstances.matches()) {
			return;
		}

		res.setContentType("text/xml; charset=UTF-8");

		OutputStreamWriter out = new OutputStreamWriter(res.getOutputStream());

		try {
			long groupId = _getGroupId(req);

			String baseImageURL = _getBaseImageURL(req);

			Date oldestDate = _getOldestDate(req);

			int maxNumOfVersions =
				GetterUtil.getInteger(req.getParameter("maxNumOfVersions"));

			Properties repoSettings = _getRepoSettings(req);

			String repositoryXML =
				SCProductEntryLocalServiceUtil.getRepositoryXML(
					groupId, baseImageURL, oldestDate, maxNumOfVersions,
					repoSettings);

			if (!res.isCommitted()) {
				out.write(repositoryXML);
			}
		}
		catch (NoSuchGroupException e) {
			res.sendError(404);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
			res.sendError(500);
		}
		finally {
			out.flush();
			out.close();
		}
	}

	private long _getGroupId(HttpServletRequest req)
		throws SystemException, PortalException {

		long groupId = -1;

		String path = req.getPathInfo();

		try {
			groupId = Long.parseLong(path.substring(1));
		}
		catch (NumberFormatException e) {
		}

		if (groupId == -1) {
			groupId = GetterUtil.getLong(req.getParameter("groupId"), -1);
		}

		if (groupId == -1) {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				_companyId, path);

			groupId = group.getGroupId();
		}

		return groupId;
	}

	private String _getBaseImageURL(HttpServletRequest req) {
		String host = PortalUtil.getHost(req);

		String portalURL = PortalUtil.getPortalURL(
			host, req.getServerPort(), req.isSecure());

		return portalURL + _imagePath + "/software_catalog";
	}

	private Properties _getRepoSettings(HttpServletRequest req) {

		Properties repoSettings = new Properties();

		String prefix = "setting_";

		Enumeration enu = req.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (name.startsWith(prefix)) {
				String settingName =
					name.substring(prefix.length(), name.length());

				if (Validator.isNotNull(req.getParameter(name))) {
					repoSettings.setProperty(
						settingName , req.getParameter(name));
				}
			}
		}

		return repoSettings;
	}

	private Date _getOldestDate(HttpServletRequest req) {
		Date oldestDate = null;

		oldestDate =
			GetterUtil.getDate(req.getParameter("oldestDate"), df, null);

		if (oldestDate == null) {
			int daysOld = GetterUtil.getInteger(req.getParameter("maxAge"), -1);

			if (daysOld != -1) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, (0 - daysOld));
				oldestDate = cal.getTime();
			}

		}

		return oldestDate;
	}

	private static Log _log = LogFactory.getLog(SoftwareCatalogServlet.class);

	private String _companyId;
	private String _rootPath;
	private String _imagePath;

	private DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

}