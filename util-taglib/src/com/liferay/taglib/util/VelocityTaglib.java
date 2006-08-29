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

package com.liferay.taglib.util;

import com.liferay.taglib.portletext.IconBackTag;
import com.liferay.taglib.portletext.IconCloseTag;
import com.liferay.taglib.portletext.IconConfigurationTag;
import com.liferay.taglib.portletext.IconEditGuestTag;
import com.liferay.taglib.portletext.IconEditTag;
import com.liferay.taglib.portletext.IconHelpTag;
import com.liferay.taglib.portletext.IconMaximizeTag;
import com.liferay.taglib.portletext.IconMinimizeTag;
import com.liferay.taglib.portletext.IconPrintTag;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.theme.MetaTagsTag;
import com.liferay.taglib.ui.JournalContentSearchTag;
import com.liferay.taglib.ui.PngImageTag;
import com.liferay.taglib.ui.SearchTag;
import com.liferay.taglib.ui.ToggleTag;
import com.liferay.util.servlet.StringServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * <a href="VelocityTaglib.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class VelocityTaglib {

	public VelocityTaglib(ServletContext ctx, HttpServletRequest req,
						  StringServletResponse res, PageContext pageContext) {

		_ctx = ctx;
		_req = req;
		_res = res;
	}

	public String iconBack() throws Exception {
		_res.recycle();

		IconBackTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconClose() throws Exception {
		_res.recycle();

		IconCloseTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconConfiguration() throws Exception {
		_res.recycle();

		IconConfigurationTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconEdit() throws Exception {
		_res.recycle();

		IconEditTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconEditGuest() throws Exception {
		_res.recycle();

		IconEditGuestTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconHelp() throws Exception {
		_res.recycle();

		IconHelpTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconMaximize() throws Exception {
		_res.recycle();

		IconMaximizeTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconMinimize() throws Exception {
		_res.recycle();

		IconMinimizeTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String iconPrint() throws Exception {
		_res.recycle();

		IconPrintTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String include(String page) throws Exception {
		_res.recycle();

		RequestDispatcher rd = _ctx.getRequestDispatcher(page);

		rd.include(_req, _res);

		return _res.getString();
	}

	public String journalContentSearch() throws Exception {
		_res.recycle();

		JournalContentSearchTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String metaTags() throws Exception {
		_res.recycle();

		MetaTagsTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String pngImage(String image, String height, String width)
		throws Exception {

		_res.recycle();

		PngImageTag.doTag(image, height, width, _ctx, _req, _res);

		return _res.getString();
	}

	public String runtime(String portletName)
		throws Exception {

		_res.recycle();

		RuntimeTag.doTag(portletName, null, _ctx, _req, _res);

		return _res.getString();
	}

	public String search() throws Exception {
		_res.recycle();

		SearchTag.doTag(_ctx, _req, _res);

		return _res.getString();
	}

	public String toggle(
			String id, String onImage, String offImage, boolean defaultOn)
		throws Exception {

		_res.recycle();

		ToggleTag.doTag(
			id, onImage, offImage, defaultOn, null, _ctx, _req, _res);

		return _res.getString();
	}

	private ServletContext _ctx;
	private HttpServletRequest _req;
	private StringServletResponse _res;

}