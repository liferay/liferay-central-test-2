/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="FlashTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FlashTag extends IncludeTag {

	public static void doTag(
			String align, String allowScriptAccess, String base, String bgcolor,
			String devicefont, String flashvars, String height, String id,
			String loop, String menu, String movie, String play, String quality,
			String salign, String scale, String swliveconnect, String version,
			String width, String wmode, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		doTag(
			_PAGE, align, allowScriptAccess, base, bgcolor, devicefont,
			flashvars, height, id, loop, menu, movie, play, quality, salign,
			scale, swliveconnect, version, width, wmode, servletContext,
			request, response);
	}

	public static void doTag(
			String page, String align, String allowScriptAccess, String base,
			String bgcolor, String devicefont, String flashvars, String height,
			String id, String loop, String menu, String movie, String play,
			String quality, String salign, String scale, String swliveconnect,
			String version, String width, String wmode,
			ServletContext servletContext, HttpServletRequest reqquest,
			HttpServletResponse response)
		throws Exception {

		reqquest.setAttribute("liferay-ui:flash:align", align);
		reqquest.setAttribute(
			"liferay-ui:flash:allowScriptAccess", allowScriptAccess);
		reqquest.setAttribute("liferay-ui:flash:base", base);
		reqquest.setAttribute("liferay-ui:flash:bgcolor", bgcolor);
		reqquest.setAttribute("liferay-ui:flash:devicefont", devicefont);
		reqquest.setAttribute("liferay-ui:flash:flashvars", flashvars);
		reqquest.setAttribute("liferay-ui:flash:height", height);
		reqquest.setAttribute("liferay-ui:flash:id", id);
		reqquest.setAttribute("liferay-ui:flash:loop", loop);
		reqquest.setAttribute("liferay-ui:flash:menu", menu);
		reqquest.setAttribute("liferay-ui:flash:movie", movie);
		reqquest.setAttribute("liferay-ui:flash:play", play);
		reqquest.setAttribute("liferay-ui:flash:quality", quality);
		reqquest.setAttribute("liferay-ui:flash:salign", salign);
		reqquest.setAttribute("liferay-ui:flash:scale", scale);
		reqquest.setAttribute("liferay-ui:flash:swliveconnect", swliveconnect);
		reqquest.setAttribute("liferay-ui:flash:version", version);
		reqquest.setAttribute("liferay-ui:flash:width", width);
		reqquest.setAttribute("liferay-ui:flash:wmode", wmode);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(reqquest, response);
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			doTag(
				getPage(), _align, _allowScriptAccess, _base, _bgcolor,
				_devicefont, _flashvars, _height, _id, _loop, _menu, _movie,
				_play, _quality, _salign, _scale, _swliveconnect, _version,
				_width, _wmode, servletContext, request, stringResponse);

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setAlign(String align) {
		if (Validator.isNotNull(align)) {
			_align = align;
		}
	}

	public void setAllowScriptAccess(String allowScriptAccess) {
		if (Validator.isNotNull(allowScriptAccess)) {
			_allowScriptAccess = allowScriptAccess;
		}
	}

	public void setBase(String base) {
		if (Validator.isNotNull(base)) {
			_base = base;
		}
	}

	public void setBgcolor(String bgcolor) {
		if (Validator.isNotNull(bgcolor)) {
			_bgcolor = bgcolor;
		}
	}

	public void setDevicefont(String devicefont) {
		if (Validator.isNotNull(devicefont)) {
			_devicefont = devicefont;
		}
	}

	public void setFlashvars(String flashvars) {
		if (Validator.isNotNull(flashvars)) {
			_flashvars = flashvars;
		}
	}

	public void setHeight(String height) {
		if (Validator.isNotNull(height)) {
			_height = height;
		}
	}

	public void setId(String id) {
		if (Validator.isNotNull(id)) {
			_id = id;
		}
	}

	public void setLoop(String loop) {
		if (Validator.isNotNull(loop)) {
			_loop = loop;
		}
	}

	public void setMenu(String menu) {
		if (Validator.isNotNull(menu)) {
			_menu = menu;
		}
	}

	public void setMovie(String movie) {
		_movie = movie;
	}

	public void setPlay(String play) {
		if (Validator.isNotNull(play)) {
			_play = play;
		}
	}

	public void setQuality(String quality) {
		if (Validator.isNotNull(quality)) {
			_quality = quality;
		}
	}

	public void setSalign(String salign) {
		if (Validator.isNotNull(salign)) {
			_salign = salign;
		}
	}

	public void setScale(String scale) {
		if (Validator.isNotNull(scale)) {
			_scale = scale;
		}
	}

	public void setSwliveconnect(String swliveconnect) {
		if (Validator.isNotNull(swliveconnect)) {
			_swliveconnect = swliveconnect;
		}
	}

	public void setVersion(String version) {
		if (Validator.isNotNull(version)) {
			_version = version;
		}
	}

	public void setWidth(String width) {
		if (Validator.isNotNull(width)) {
			_width = width;
		}
	}

	public void setWmode(String wmode) {
		if (Validator.isNotNull(wmode)) {
			_wmode = wmode;
		}
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/flash/page.jsp";

	private String _align = "left";
	private String _allowScriptAccess = "sameDomain";
	private String _base = StringPool.PERIOD;
	private String _bgcolor = "#FFFFFF";
	private String _devicefont = Boolean.TRUE.toString();
	private String _flashvars = StringPool.BLANK;
	private String _height = "500";
	private String _id = StringPool.BLANK;
	private String _loop = Boolean.TRUE.toString();
	private String _menu = Boolean.FALSE.toString();
	private String _movie = StringPool.BLANK;
	private String _play = Boolean.FALSE.toString();
	private String _quality = "best";
	private String _salign = StringPool.BLANK;
	private String _scale = "showall";
	private String _swliveconnect = Boolean.FALSE.toString();
	private String _version = "7";
	private String _width = "100%";
	private String _wmode = "opaque";

}