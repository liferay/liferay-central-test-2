/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.TextFormatter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * <a href="SelectTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SelectTag extends IncludeTag implements DynamicAttributes {

	public int doEndTag() throws JspException {
		try{
			PortalIncludeUtil.include(pageContext, getEndPage());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_bean = null;
				_cssClass = null;
				_dynamicAttributes.clear();
				_endPage = null;
				_first = false;
				_helpMessage = null;
				_inlineField = false;
				_inlineLabel = null;
				_id = null;
				_label = null;
				_last = false;
				_listType = null;
				_name = null;
				_prefix = null;
				_showEmptyOption = false;
				_startPage = null;
				_suffix = null;
			}
		}
	}

	public int doStartTag() throws JspException {
		try{
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			if (_bean == null) {
				_bean = pageContext.getAttribute("aui:model-context:bean");
			}

			if (Validator.isNull(_id)) {
				_id = _name;
			}

			if (_label == null) {
				_label = TextFormatter.format(_name, TextFormatter.K);
			}

			request.setAttribute("aui:select:bean", _bean);
			request.setAttribute("aui:select:cssClass", _cssClass);
			request.setAttribute(
				"aui:select:disabled", String.valueOf(_disabled));
			request.setAttribute(
				"aui:select:dynamicAttributes", _dynamicAttributes);
			request.setAttribute("aui:select:first", String.valueOf(_first));
			request.setAttribute("aui:select:helpMessage", _helpMessage);
			request.setAttribute(
				"aui:select:inlineField", String.valueOf(_inlineField));
			request.setAttribute("aui:select:inlineLabel", _inlineLabel);
			request.setAttribute("aui:select:id", _id);
			request.setAttribute("aui:select:label", _label);
			request.setAttribute("aui:select:last", String.valueOf(_last));
			request.setAttribute("aui:select:listType", _listType);
			request.setAttribute("aui:select:name", _name);
			request.setAttribute("aui:select:prefix", _prefix);
			request.setAttribute(
				"aui:select:showEmptyOption", String.valueOf(_showEmptyOption));
			request.setAttribute("aui:select:suffix", _suffix);

			PortalIncludeUtil.include(pageContext, getStartPage());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	public String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setInlineField(boolean inlineField) {
		_inlineField = inlineField;
	}

	public void setInlineLabel(String inlineLabel) {
		_inlineLabel = inlineLabel;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	public void setListType(String listType) {
		_listType = listType;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPrefix(String prefix) {
		_prefix = prefix;
	}

	public void setShowEmptyOption(boolean showEmptyOption) {
		_showEmptyOption = showEmptyOption;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setSuffix(String suffix) {
		_suffix = suffix;
	}

	private static final String _END_PAGE = "/html/taglib/aui/select/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/select/start.jsp";

	private Object _bean;
	private String _cssClass;
	private boolean _disabled;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _endPage;
	private boolean _first;
	private String _helpMessage;
	private String _id;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _label;
	private boolean _last;
	private String _listType;
	private String _name;
	private String _prefix;
	private boolean _showEmptyOption;
	private String _startPage;
	private String _suffix;

}