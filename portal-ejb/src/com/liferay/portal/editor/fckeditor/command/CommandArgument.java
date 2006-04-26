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

package com.liferay.portal.editor.fckeditor.command;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.ParamUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CommandArgument.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 *
 */
public class CommandArgument {

	public CommandArgument(String command, String type, String currentFolder,
						   String newFolder, ThemeDisplay themeDisplay,
						   HttpServletRequest req) {

		_command = command;
		_type = type;
		_currentFolder = currentFolder;
		_newFolder = newFolder;
		_themeDisplay = themeDisplay;
		_req = req;
	}

	public String getCommand() {
		return _command;
	}

	public String getType() {
		return _type;
	}

	public String getCurrentFolder() {
		return _currentFolder;
	}

	public String getNewFolder() {
		return _newFolder;
	}

	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _req;
	}

	public String getCompanyId() {
		return _themeDisplay.getCompanyId();
	}

	public String getGroupId() {
		return _themeDisplay.getPortletGroupId();
	}

	public String getUserId() {
		return _themeDisplay.getUserId();
	}

	public String getPlid() {
		return _themeDisplay.getPlid();
	}

	private String _command;
	private String _type;
	private String _currentFolder;
	private String _newFolder;
	private ThemeDisplay _themeDisplay;
	private HttpServletRequest _req;

}