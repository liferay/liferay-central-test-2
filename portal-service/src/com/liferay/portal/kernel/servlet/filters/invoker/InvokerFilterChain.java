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

import java.io.IOException;

import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class InvokerFilterChain implements FilterChain {

	public InvokerFilterChain(FilterChain filterChain) {
		_filterChain = filterChain;
	}

	public void addFilter(Filter filter) {
		_filters.add(filter);
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (_filters.isEmpty()) {
			_filterChain.doFilter(servletRequest, servletResponse);
		}
		else {
			Filter filter = _filters.remove();

			filter.doFilter(servletRequest, servletResponse, this);
		}
	}

	private FilterChain _filterChain;
	private Queue<Filter> _filters = new LinkedList<Filter>();

}