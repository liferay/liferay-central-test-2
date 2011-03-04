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

package com.liferay.portal.rest;

import java.util.regex.Pattern;

public class PathMacro {

	public int getIndex() {
		return _index;
	}

	public String getName() {
		return _name;
	}

	public String getOffsetLeft() {
		return _offsetLeft;
	}

	public String getOffsetRight() {
		return _offsetRight;
	}

	public Pattern getRegexPattern() {
		return _regexPattern;
	}

	public void setIndex(int _ndx) {
		this._index = _ndx;
	}

	public void setName(String name) {
		this._name = name;
	}

	public void setOffsetLeft(String left) {
		this._offsetLeft = left;
	}

	public void setRegexPattern(Pattern pattern) {
		this._regexPattern = pattern;
	}

	public void setRight(String right) {
		this._offsetRight = right;
	}

	private int _index;

	private String _name;

	private String _offsetLeft;

	private String _offsetRight;

	private Pattern _regexPattern;

}
