/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.facet.collector;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Michael C. Han
 */
public class DefaultTermCollector implements TermCollector {

	public DefaultTermCollector(String term, int frequency) {
		_term = term;
		_frequency = frequency;
	}

	@Override
	public int getFrequency() {
		return _frequency;
	}

	@Override
	public String getTerm() {
		return _term;
	}

	@Override
	public String toString() {
		return _term + StringPool.EQUAL + _frequency;
	}

	private final int _frequency;
	private final String _term;

}