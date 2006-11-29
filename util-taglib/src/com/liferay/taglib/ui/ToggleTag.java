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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.SessionClicks;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.PwdGenerator;
import com.liferay.util.servlet.StringServletResponse;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="ToggleTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ToggleTag extends IncludeTag {

	public static void doTag(
			String id, String onImage, String offImage, boolean defaultOn,
			String stateVar, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res)
		throws IOException, ServletException {

		doTag(_PAGE, id, onImage, offImage, defaultOn, stateVar, ctx, req, res);
	}

	public static void doTag(
			String page, String id, String onImage, String offImage,
			boolean defaultOn, String stateVar, ServletContext ctx,
			HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		String defaultStateValue = defaultOn ? StringPool.BLANK : "none";
		String defaultImage = defaultOn ? onImage : offImage;

		String clickValue = SessionClicks.get(req, id, null);

		if (defaultOn) {
			if ((clickValue != null) && (clickValue.equals("none"))) {
				defaultStateValue = "none";
				defaultImage = offImage;
			}
			else {
				defaultStateValue = "";
				defaultImage = onImage;
			}
		}
		else {
			if ((clickValue == null) || (clickValue.equals("none"))) {
				defaultStateValue = "none";
				defaultImage = offImage;
			}
			else {
				defaultStateValue = "";
				defaultImage = onImage;
			}
		}

		if (stateVar == null) {
			stateVar = PwdGenerator.getPassword(PwdGenerator.KEY3, 8);
		}

		req.setAttribute("liferay-ui:toggle:id", id);
		req.setAttribute("liferay-ui:toggle:onImage", onImage);
		req.setAttribute("liferay-ui:toggle:offImage", offImage);
		req.setAttribute("liferay-ui:toggle:stateVar", stateVar);
		req.setAttribute(
			"liferay-ui:toggle:defaultStateValue", defaultStateValue);
		req.setAttribute("liferay-ui:toggle:defaultImage", defaultImage);

		RequestDispatcher rd = ctx.getRequestDispatcher(page);

		rd.include(req, res);
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext ctx = getServletContext();
			HttpServletRequest req = getServletRequest();
			StringServletResponse res = getServletResponse();

			doTag(
				_id, _onImage, _offImage, _defaultOn, _stateVar, ctx, req, res);

			pageContext.getOut().print(res.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setId(String id) {
		_id = id;
	}

	public void setOnImage(String onImage) {
		_onImage = onImage;
	}

	public void setOffImage(String offImage) {
		_offImage = offImage;
	}

	public void setDefaultOn(boolean defaultOn) {
		_defaultOn = defaultOn;
	}

	public void setStateVar(String stateVar) {
		_stateVar = stateVar;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/toggle/page.jsp";

	private String _id;
	private String _onImage;
	private String _offImage;
	private boolean _defaultOn;
	private String _stateVar;

}