/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.messaging.async.aop.config;

import com.liferay.portal.messaging.async.aop.AsyncAdvice;

import java.util.Collections;
import java.util.Map;

/**
 * <a href="AsyncAdviceConfigurator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class AsyncAdviceConfigurator {

	public void afterPropertiesSet() {
		if (_asyncAdvice == null) {
			throw new IllegalArgumentException("AsyncAdvice is null");
		}
		if (_defaultDestination == null) {
			throw new IllegalArgumentException(
				"Default destination name is null");
		}
		if (_destinations == null) {
			_destinations = Collections.emptyMap();
		}
		_asyncAdvice.setDefaultDestination(_defaultDestination);
		_asyncAdvice.setDestinations(_destinations);
	}

	public void setAsyncAdvice(AsyncAdvice asyncAdvice) {
		_asyncAdvice = asyncAdvice;
	}

	public void setDefaultDestination(String defaultDestination) {
		_defaultDestination = defaultDestination;
	}

	public void setDestinations(Map<Class<?>, String> destinations) {
		_destinations = destinations;
	}

	private AsyncAdvice _asyncAdvice;

	private String _defaultDestination;

	private Map<Class<?>, String> _destinations;

}