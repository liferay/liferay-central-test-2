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

package com.liferay.portal.servlet.filters;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class FilterMapping {

	public static final int REQUEST = 1;
	public static final int INCLUDE = 2;
	public static final int FORWARD = 4;
	public static final int ERROR = 8;

	public FilterMapping(
		String filterName, List<String> urlPatterns, List<String> dispatchers) {

		_filterName = filterName;
		_urlPatterns = urlPatterns;

		for (String dispatcher : dispatchers) {
			if ("REQUEST".equals(dispatcher)) {
				_dispatchers = _dispatchers | REQUEST;
			}
			else if ("INCLUDE".equals(dispatcher)) {
				_dispatchers = _dispatchers | INCLUDE;
			}
			else if ("FORWARD".equals(dispatcher)) {
				_dispatchers = _dispatchers | FORWARD;
			}
			else if ("ERROR".equals(dispatcher)) {
				_dispatchers = _dispatchers | ERROR;
			}
		}

		if (_dispatchers == 0) {
			_dispatchers = _dispatchers | REQUEST;
		}
	}

	public boolean isDispatcherEnabled(int dispatcher) {
		boolean enabled = false;

		switch (dispatcher) {
			case REQUEST: enabled = (_dispatchers & REQUEST) == REQUEST; break;
			case INCLUDE: enabled = (_dispatchers & INCLUDE) == INCLUDE; break;
			case FORWARD: enabled = (_dispatchers & FORWARD) == FORWARD; break;
			case ERROR: enabled = (_dispatchers & ERROR) == ERROR; break;
		}

		return enabled;
	}

	public String getFilterName() {
		return _filterName;
	}

	public boolean matchesURI(String uri) {
		for (String pattern : _urlPatterns) {
			if (pattern.equals(uri)) {
				return true;
			}

			if (pattern.equals("/*")) {
				return true;
			}

			if (pattern.endsWith("/*")) {
				if (pattern.regionMatches(0, uri, 0, pattern.length() - 2)) {
					if (uri.length() == pattern.length() - 2) {
						return true;
					}
					else if ('/' == uri.charAt(pattern.length() - 2)) {
						return true;
					}
				}

				continue;
			}

			if (pattern.startsWith("*.")) {
				int slashIdx = uri.lastIndexOf('/');
				int periodIdx = uri.lastIndexOf('.');

				if ((slashIdx >= 0) && (periodIdx > slashIdx) &&
					(periodIdx != uri.length() - 1) &&
					((uri.length() - periodIdx) == (pattern.length() - 1))) {

					if (pattern.regionMatches(
						2, uri, periodIdx + 1, pattern.length() - 2)) {

						return true;
					}
				}
			}
		}

		return false;
	}

	private int _dispatchers;
	private String _filterName;
	private List<String> _urlPatterns;

}