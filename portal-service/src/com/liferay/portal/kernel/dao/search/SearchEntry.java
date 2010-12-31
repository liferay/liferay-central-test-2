/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.search;

import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class SearchEntry implements Cloneable {

	public static final String DEFAULT_ALIGN = "left";

	public static final String DEFAULT_VALIGN = "middle";

	public static final int DEFAULT_COLSPAN = 1;

	public SearchEntry() {
		this(DEFAULT_ALIGN, DEFAULT_VALIGN, DEFAULT_COLSPAN);
	}

	public SearchEntry(String align, String valign, int colspan) {
		_align = align;
		_valign = valign;
		_colspan = colspan;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public String getAlign() {
		return _align;
	}

	public void setAlign(String align) {
		_align = align;
	}

	public String getValign() {
		return _valign;
	}

	public void setValign(String valign) {
		_valign = valign;
	}

	public int getColspan() {
		return _colspan;
	}

	public void setColspan(int colspan) {
		_colspan = colspan;
	}

	public abstract void print(PageContext pageContext) throws Exception;

	private int _index;
	private String _align;
	private String _valign;
	private int _colspan;

}