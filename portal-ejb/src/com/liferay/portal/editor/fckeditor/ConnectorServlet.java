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

package com.liferay.portal.editor.fckeditor;

import com.liferay.portal.editor.fckeditor.command.Command;
import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.command.CommandFactory;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ParamUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ConnectorServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 *
 */
public class ConnectorServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		ThemeDisplay themeDisplay = null;

		try {
			HttpSession ses = req.getSession();

			// The servlet accepts commands sent in the format:
			// connector?Command=&Type=&CurrentFolder=. The servlet then
			// executes the command and returns the results to the client in XML
			// format.

			String command = req.getParameter("Command");
			String type = req.getParameter("Type");
			String currentFolder = req.getParameter("CurrentFolder");
			String newFolder = ParamUtil.getString(req, "NewFolderName");

			// Populate a barebones ThemeDisplay that is used by
			// PageCommandReceiver

			themeDisplay = ThemeDisplayFactory.create();

			req.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

			Company company = PortalUtil.getCompany(req);
			User user = PortalUtil.getUser(req);
			String plid = ParamUtil.getString(req, "p_l_id");
			String groupId = PortalUtil.getPortletGroupId(plid);
			String mainPath = (String)ses.getAttribute(WebKeys.MAIN_PATH);
			String friendlyURLPrivatePath = (String)ses.getAttribute(
				WebKeys.FRIENDLY_URL_PRIVATE_PATH);
			String friendlyURLPublicPath = (String)ses.getAttribute(
				WebKeys.FRIENDLY_URL_PUBLIC_PATH);

			themeDisplay.setCompany(company);
			themeDisplay.setUser(user);
			themeDisplay.setPlid(plid);
			themeDisplay.setPortletGroupId(groupId);
			themeDisplay.setPathMain(mainPath);
			themeDisplay.setPathFriendlyURLPrivate(friendlyURLPrivatePath);
			themeDisplay.setPathFriendlyURLPublic(friendlyURLPublicPath);

			// Set the user principal that is used by the business tier

			if (user != null) {
				PrincipalThreadLocal.setName(user.getUserId());

				PermissionChecker permissionChecker =
					PermissionCheckerFactory.create(user, true);

				PermissionThreadLocal.setPermissionChecker(
					permissionChecker);
			}

			CommandArgument arg = new CommandArgument(
				command, type, currentFolder, newFolder, themeDisplay, req);

			Command commandObj = CommandFactory.getCommand(command);

			commandObj.execute(arg, req, res);
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			try {
				ThemeDisplayFactory.recycle(themeDisplay);
			}
			catch (Exception e) {
			}
		}
	}

	private static Log _log = LogFactory.getLog(ConnectorServlet.class);

}