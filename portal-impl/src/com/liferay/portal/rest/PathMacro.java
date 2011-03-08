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

	public Pattern getPattern() {
		return _pattern;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOffsetLeft(String offsetLeft) {
		_offsetLeft = offsetLeft;
	}

	public void setOffsetRight(String offsetRight) {
		_offsetRight = offsetRight;
	}

	public void setPattern(Pattern pattern) {
		_pattern = pattern;
	}

	private int _index;
	private String _name;
	private String _offsetLeft;
	private String _offsetRight;
	private Pattern _pattern;

}