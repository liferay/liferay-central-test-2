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
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.List;

import javax.portlet.PortletURL;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;


/**
 * <a href="SearchContainerColumnButtonTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerColumnButtonTag
    extends ParamAndPropertyAncestorTagImpl {

	public int doStartTag() throws JspException {
		SearchContainerRowTag parent = (SearchContainerRowTag)
			findAncestorWithClass(this, SearchContainerRowTag.class);

		if (parent == null) {
			throw new JspTagException(
				"Requires the liferay-ui:search-container-row tag");
		}

		if (!parent.isHeaderNamesAssigned()) {
			SearchContainerTag parentParent = (SearchContainerTag)
				findAncestorWithClass(this, SearchContainerTag.class);

			List<String> headerNames = parentParent.getSearchContainer()
				.getHeaderNames();

			headerNames.add(getName());
		}

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		SearchContainerRowTag parent = (SearchContainerRowTag)
			findAncestorWithClass(this, SearchContainerRowTag.class);

		try {
			ResultRow row = parent.getRow();

			if (_index <= -1) {
				_index = row.getEntries().size();
			}

			row.addButton(
				_index, getAlign(), getValign(), getColspan(), getName(),
				(String)getHref());

			return EVAL_PAGE;
		}
		finally {
			_align = null;
			_colspan = -1;
			_index = -1;
			_href = null;
			_name = null;
			_valign = null;
		}
	}

   public String getAlign() {
		if (Validator.isNull(_align)) {
			return SearchEntry.DEFAULT_ALIGN;
		}
		else {
			return _align;
		}
    }

    public void setAlign(String align) {
    	_align = align;
    }

    public int getColspan() {
		if (_colspan >= -1) {
			return SearchEntry.DEFAULT_COLSPAN;
		}
		else {
			return _colspan;
		}
    }

    public void setColspan(int colspan) {
    	_colspan = colspan;
    }

    public Object getHref() {
		if (Validator.isNotNull(_href) && (_href instanceof PortletURL)) {
			_href = _href.toString();
		}

    	return _href;
    }

    public void setHref(Object href) {
    	_href = href;
    }

    public int getIndex() {
    	return _index;
    }

    public void setIndex(int index) {
    	_index = index;
    }

    public String getName() {
		if (Validator.isNull(_name)) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
    }

    public void setName(String name) {
    	_name = name;
    }

    public String getValign() {
		if (Validator.isNull(_valign)) {
			return SearchEntry.DEFAULT_VALIGN;
		}
		else {
			return _valign;
		}
    }

    public void setValign(String valign) {
    	_valign = valign;
    }

	private String _align;
	private int _colspan = -1;
	private Object _href;
	private int _index = -1;
	private String _name;
	private String _valign;

}
