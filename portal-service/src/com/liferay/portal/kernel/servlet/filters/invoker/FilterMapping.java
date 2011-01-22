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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.util.CharPool;

import java.util.List;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class FilterMapping {

	public FilterMapping(
		String filterName, List<String> urlPatterns, List<String> dispatchers) {

		_filterName = filterName;
		_urlPatterns = urlPatterns;

		for (String dispatcher : dispatchers) {
			if (dispatcher.equals("ERROR")) {
				_dispatcherError = true;
			}
			else if (dispatcher.equals("FORWARD")) {
				_dispatcherForward = true;
			}
			else if (dispatcher.equals("INCLUDE")) {
				_dispatcherInclude = true;
			}
			else if (dispatcher.equals("REQUEST")) {
				_dispatcherRequest = true;
			}
		}

		if (!_dispatcherError && !_dispatcherForward && !_dispatcherInclude &&
			!_dispatcherRequest) {

			_dispatcherRequest = true;
		}
	}

	public String getFilterName() {
		return _filterName;
	}

	public boolean isMatch(Dispatcher dispatcher, String uri) {
		if (!isMatch(dispatcher)) {
			return false;
		}

		for (String urlPattern : _urlPatterns) {
			if (isMatch(uri, urlPattern)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isMatch(Dispatcher dispatcher) {
		if (((dispatcher == Dispatcher.ERROR) && _dispatcherError) ||
			((dispatcher == Dispatcher.FORWARD) && _dispatcherForward) ||
			((dispatcher == Dispatcher.INCLUDE) && _dispatcherInclude) ||
			((dispatcher == Dispatcher.REQUEST) && _dispatcherRequest)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isMatch(String uri, String urlPattern) {
		if (urlPattern.equals(uri)) {
			return true;
		}

		if (urlPattern.equals(_SLASH_STAR)) {
			return true;
		}

		if (urlPattern.endsWith(_SLASH_STAR)) {
			if (urlPattern.regionMatches(0, uri, 0, urlPattern.length() - 2)) {
				if (uri.length() == (urlPattern.length() - 2)) {
					return true;
				}
				else if (CharPool.SLASH ==
							uri.charAt(urlPattern.length() - 2)) {

					return true;
				}
			}
		}
		else if (urlPattern.startsWith(_STAR_PERIOD)) {
			int slashPos = uri.lastIndexOf(CharPool.SLASH);
			int periodPos = uri.lastIndexOf(CharPool.PERIOD);

			if ((slashPos >= 0) && (periodPos > slashPos) &&
				(periodPos != (uri.length() - 1)) &&
				((uri.length() - periodPos) == (urlPattern.length() - 1))) {

				if (urlPattern.regionMatches(
						2, uri, periodPos + 1, urlPattern.length() - 2)) {

					return true;
				}
			}
		}

		return false;
	}

	private static final String _SLASH_STAR = "/*";
	private static final String _STAR_PERIOD = "*.";

	private boolean _dispatcherError;
	private boolean _dispatcherForward;
	private boolean _dispatcherInclude;
	private boolean _dispatcherRequest;
	private String _filterName;
	private List<String> _urlPatterns;

}