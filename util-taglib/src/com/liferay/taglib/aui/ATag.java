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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ATag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLang(String lang) {
		_lang = lang;
	}

	public void setTarget(String target) {
		_target = target;
	}

	protected void cleanUp() {
		_cssClass = StringPool.BLANK;
		_href = StringPool.BLANK;
		_id = StringPool.BLANK;
		_label = StringPool.BLANK;
		_lang = StringPool.BLANK;
		_target = StringPool.BLANK;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isTrimNewLines() {
		return _TRIM_NEW_LINES;
	}

	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		if (Validator.isNotNull(_href)) {
			if (_target.equals("_blank") || _target.equals("_new")) {
				jspWriter.write("<span class=\"opens-new-window-accessible\">");
				jspWriter.write(
					LanguageUtil.get(pageContext, "opens-new-window"));
				jspWriter.write("</span>");
			}

			jspWriter.write("</a>");
		}
		else {
			jspWriter.write("</span>");
		}

		return EVAL_PAGE;
	}

	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		String namespace = _getNamespace();

		if (Validator.isNotNull(_href)) {
			jspWriter.write("<a ");

			if (Validator.isNotNull(_cssClass)) {
				jspWriter.write("class=\"");
				jspWriter.write(_cssClass);
				jspWriter.write("\" ");
			}

			jspWriter.write("href=\"");
			jspWriter.write(HtmlUtil.escape(_href));
			jspWriter.write("\" ");

			if (Validator.isNotNull(_id)) {
				jspWriter.write("id=\"");
				jspWriter.write(namespace);
				jspWriter.write(_id);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(_lang)) {
				jspWriter.write("lang=\"");
				jspWriter.write(_lang);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(_target)) {
				jspWriter.write("target=\"");
				jspWriter.write(_target);
				jspWriter.write("\" ");
			}

			writeDynamicAttributes(jspWriter);

			jspWriter.write(">");

			if (Validator.isNotNull(_label)) {
				jspWriter.write(LanguageUtil.get(pageContext, _label));
			}
		}
		else {
			jspWriter.write("<span ");

			if (Validator.isNotNull(_cssClass)) {
				jspWriter.write("class=\"");
				jspWriter.write(_cssClass);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(_id)) {
				jspWriter.write("id=\"");
				jspWriter.write(namespace);
				jspWriter.write(_id);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(_lang)) {
				jspWriter.write("lang=\"");
				jspWriter.write(_lang);
				jspWriter.write("\" ");
			}

			writeDynamicAttributes(jspWriter);

			jspWriter.write(">");
		}

		return EVAL_BODY_INCLUDE;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("aui:a:cssClass", _cssClass);
		request.setAttribute("aui:a:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:a:href", _href);
		request.setAttribute("aui:a:id", _id);
		request.setAttribute("aui:a:label", _label);
		request.setAttribute("aui:a:lang", _lang);
		request.setAttribute("aui:a:target", _target);
	}

	private String _getNamespace() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		String namespace = StringPool.BLANK;

		boolean useNamespace = GetterUtil.getBoolean(
			(String)request.getAttribute("aui:form:useNamespace"), true);

		if ((portletResponse != null) && useNamespace) {
			namespace = portletResponse.getNamespace();
		}

		return namespace;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final boolean _TRIM_NEW_LINES = true;

	private String _cssClass = StringPool.BLANK;
	private String _href = StringPool.BLANK;
	private String _id = StringPool.BLANK;
	private String _label = StringPool.BLANK;
	private String _lang = StringPool.BLANK;
	private String _target = StringPool.BLANK;

}