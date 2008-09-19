/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * <a href="SearchContainerColumnButtonTag.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerColumnButtonTag
	extends ParamAndPropertyAncestorTagImpl {

	public int doEndTag() {
		try {
			SearchContainerRowTag parentTag =
				(SearchContainerRowTag)findAncestorWithClass(
					this, SearchContainerRowTag.class);

			ResultRow row = parentTag.getRow();

			if (_index <= -1) {
				_index = row.getEntries().size();
			}

			row.addButton(
				_index, getAlign(), getValign(), getColspan(), getName(),
				(String)getHref());

			return EVAL_PAGE;
		}
		finally {
			_align = SearchEntry.DEFAULT_ALIGN;
			_colspan = SearchEntry.DEFAULT_COLSPAN;
			_href = null;
			_index = -1;
			_name = StringPool.BLANK;
			_valign = SearchEntry.DEFAULT_VALIGN;
		}
	}

	public int doStartTag() throws JspException {
		SearchContainerRowTag parentRowTag =
			(SearchContainerRowTag)findAncestorWithClass(
				this, SearchContainerRowTag.class);

		if (parentRowTag == null) {
			throw new JspTagException(
				"Requires liferay-ui:search-container-row");
		}

		if (!parentRowTag.isHeaderNamesAssigned()) {
			SearchContainerTag parentSearchContainerTag =
				(SearchContainerTag)findAncestorWithClass(
					this, SearchContainerTag.class);

			SearchContainer searchContainer =
				parentSearchContainerTag.getSearchContainer();

			List<String> headerNames = searchContainer.getHeaderNames();

			headerNames.add(getName());
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getAlign() {
		return _align;
	}

	public int getColspan() {
		return _colspan;
	}

	public Object getHref() {
		if (Validator.isNotNull(_href) && (_href instanceof PortletURL)) {
			_href = _href.toString();
		}

		return _href;
	}

	public int getIndex() {
		return _index;
	}

	public String getName() {
		return _name;
	}

	public String getValign() {
		return _valign;
	}

	public void setAlign(String align) {
		_align = align;
	}

	public void setColspan(int colspan) {
		_colspan = colspan;
	}

	public void setHref(Object href) {
		_href = href;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValign(String valign) {
		_valign = valign;
	}

	private String _align = SearchEntry.DEFAULT_ALIGN;
	private int _colspan = SearchEntry.DEFAULT_COLSPAN;
	private Object _href;
	private int _index = -1;
	private String _name = StringPool.BLANK;
	private String _valign = SearchEntry.DEFAULT_VALIGN;

}