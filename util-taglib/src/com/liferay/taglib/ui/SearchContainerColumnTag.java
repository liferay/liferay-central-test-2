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

import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

/**
 * <a href="SearchContainerColumnTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public abstract class SearchContainerColumnTag
	extends ParamAndPropertyAncestorTagImpl {

	public String getAlign() {
		return _align;
	}

	public int getColspan() {
		return _colspan;
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

	public void setIndex(int index) {
		_index = index;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValign(String valign) {
		_valign = valign;
	}

	protected String _align = SearchEntry.DEFAULT_ALIGN;
	protected int _colspan = SearchEntry.DEFAULT_COLSPAN;
	protected int _index = -1;
	protected String _name = StringPool.BLANK;
	protected String _valign = SearchEntry.DEFAULT_VALIGN;

}