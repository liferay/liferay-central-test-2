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

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="IconTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class IconTag extends IncludeTag {

	public int doEndTag() throws JspException {
		int value = super.doEndTag();

		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.removeAttribute("liferay-ui:icon:cssClass");
			request.removeAttribute("liferay-ui:icon:image");
			request.removeAttribute("liferay-ui:icon:imageHover");
			request.removeAttribute("liferay-ui:icon:label");
			request.removeAttribute("liferay-ui:icon:lang");
			request.removeAttribute("liferay-ui:icon:message");
			request.removeAttribute("liferay-ui:icon:method");
			request.removeAttribute("liferay-ui:icon:src");
			request.removeAttribute("liferay-ui:icon:srcHover");
			request.removeAttribute("liferay-ui:icon:target");
			request.removeAttribute("liferay-ui:icon:toolTip");
			request.removeAttribute("liferay-ui:icon:url");

			return value;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_cssClass = null;
				_image = null;
				_imageHover = null;
				_label = false;
				_lang = null;
				_message = null;
				_method = null;
				_src = null;
				_srcHover = null;
				_target = null;
				_toolTip = false;
				_url = null;
			}
		}
	}

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:icon:cssClass", _cssClass);
		request.setAttribute("liferay-ui:icon:image", _image);
		request.setAttribute("liferay-ui:icon:imageHover", _imageHover);
		request.setAttribute("liferay-ui:icon:label", String.valueOf(_label));
		request.setAttribute("liferay-ui:icon:lang", _lang);
		request.setAttribute("liferay-ui:icon:message", _message);
		request.setAttribute("liferay-ui:icon:method", _method);
		request.setAttribute("liferay-ui:icon:src", _src);
		request.setAttribute("liferay-ui:icon:srcHover", _srcHover);
		request.setAttribute("liferay-ui:icon:target", _target);
		request.setAttribute(
			"liferay-ui:icon:toolTip", String.valueOf(_toolTip));
		request.setAttribute("liferay-ui:icon:url", _url);

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setImageHover(String imageHover) {
		_imageHover = imageHover;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setLang(String lang) {
		_lang = lang;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setSrc(String src) {
		_src = src;
	}

	public void setSrcHover(String srcHover) {
		_srcHover = srcHover;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setToolTip(boolean toolTip) {
		_toolTip = toolTip;
	}

	public void setUrl(String url) {
		_url = url;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/icon/page.jsp";

	private String _cssClass;
	private String _image;
	private String _imageHover;
	private boolean _label;
	private String _lang;
	private String _message;
	private String _method;
	private String _src;
	private String _srcHover;
	private String _target = "_self";
	private boolean _toolTip = true;
	private String _url;

}