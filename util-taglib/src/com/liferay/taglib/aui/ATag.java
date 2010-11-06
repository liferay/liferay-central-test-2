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

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletResponse;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * @author Shuyang Zhou
 */
public class ATag extends BodyTagSupport implements DynamicAttributes {

	public int doEndTag() throws JspException {
		JspWriter jspWriter = pageContext.getOut();

		try {
			if (Validator.isNotNull(_href)) {
				if (_target.equals("_blank") || _target.equals("_new")) {
					jspWriter.append(
						"<span class=\"opens-new-window-accessible\">");
					jspWriter.append(LanguageUtil.format(
						pageContext, "opens-new-window", (Object[]) null,
						true));
					jspWriter.append("</span>");
				}
				jspWriter.append("</a>");
			}
			else {
				jspWriter.append("</span>");
			}
		}
		catch (IOException ioe) {
			throw new JspException(ioe);
		}

		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		ServletRequest request = pageContext.getRequest();

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
		String namespace = StringPool.BLANK;
		boolean useNamespace = GetterUtil.getBoolean(
			(String)request.getAttribute("aui:form:useNamespace"), true);

		if ((portletResponse != null) && useNamespace) {
			namespace = portletResponse.getNamespace();
		}

		JspWriter jspWriter = pageContext.getOut();

		try {
			if (Validator.isNotNull(_href)) {
				jspWriter.append("<a ");

				if (Validator.isNotNull(_cssClass)) {
					jspWriter.append("class=\"");
					jspWriter.append(_cssClass);
					jspWriter.append("\" ");
				}

				jspWriter.append("href=\"");
				jspWriter.append(HtmlUtil.escape(_href));
				jspWriter.append("\" ");

				if (Validator.isNotNull(_id)) {
					jspWriter.append("id=\"");
					jspWriter.append(namespace);
					jspWriter.append(_id);
					jspWriter.append("\" ");
				}

				if (Validator.isNotNull(_lang)) {
					jspWriter.append("lang=\"");
					jspWriter.append(_lang);
					jspWriter.append("\" ");
				}

				if (Validator.isNotNull(_target)) {
					jspWriter.append("target=\"");
					jspWriter.append(_target);
					jspWriter.append("\" ");
				}

				_insertDynamicAttributes(jspWriter);

				jspWriter.append(">");

				if (Validator.isNotNull(_label)) {
					jspWriter.append(LanguageUtil.format(
						pageContext, _label, (Object[]) null, true));
				}
			}
			else {
				jspWriter.append("<span ");
				if (Validator.isNotNull(_cssClass)) {
					jspWriter.append("class=\"");
					jspWriter.append(_cssClass);
					jspWriter.append("\" ");
				}
				if (Validator.isNotNull(_id)) {
					jspWriter.append("id=\"");
					jspWriter.append(namespace);
					jspWriter.append(_id);
					jspWriter.append("\" ");
				}
				if (Validator.isNotNull(_lang)) {
					jspWriter.append("lang=\"");
					jspWriter.append(_lang);
					jspWriter.append("\" ");
				}

				_insertDynamicAttributes(jspWriter);

				jspWriter.append(">");
			}
		}
		catch (IOException ioe) {
			throw new JspException(ioe);
		}

		return EVAL_BODY_INCLUDE;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
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

	private void _insertDynamicAttributes(JspWriter jspWriter)
		throws IOException {
		if ((_dynamicAttributes == null) || _dynamicAttributes.isEmpty()) {
			return;
		}

		for (Map.Entry<String, Object> entry : _dynamicAttributes.entrySet()) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());

			if (!key.equals("class")) {
				jspWriter.append(key);
				jspWriter.append("=\"");
				jspWriter.append(value);
				jspWriter.append("\" ");
			}
		}

	}

	private String _cssClass = StringPool.BLANK;
	private Map<String, Object> _dynamicAttributes =
			new HashMap<String, Object>();
	private String _href = StringPool.BLANK;
	private String _id = StringPool.BLANK;
	private String _label = StringPool.BLANK;
	private String _lang = StringPool.BLANK;
	private String _target = StringPool.BLANK;

}