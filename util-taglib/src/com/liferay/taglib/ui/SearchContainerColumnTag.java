/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 */
public abstract class SearchContainerColumnTag
	extends ParamAndPropertyAncestorTagImpl {

	public String getAlign() {
		return align;
	}

	public int getColspan() {
		return colspan;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getValign() {
		return valign;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValign(String valign) {
		this.valign = valign;
	}

	protected String align = SearchEntry.DEFAULT_ALIGN;
	protected int colspan = SearchEntry.DEFAULT_COLSPAN;
	protected int index = -1;
	protected String name = StringPool.BLANK;
	protected String valign = SearchEntry.DEFAULT_VALIGN;

}