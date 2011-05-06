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

package com.liferay.portal.kernel.search;

/**
 * @author Raymond Augé
 */
public class TermRangeQueryFactoryUtil {

	public static TermRangeQuery create(
		String field, String lowerTerm, String upperTerm, boolean includeLower,
		boolean includeUpper) {

		return getTermRangeQueryFactory().create(
			field, lowerTerm, upperTerm, includeLower, includeUpper);
	}

	public static TermRangeQueryFactory getTermRangeQueryFactory() {
		return _termRangeQueryFactory;
	}

	public void setTermRangeQueryFactory(
		TermRangeQueryFactory termRangeQueryFactory) {

		_termRangeQueryFactory = termRangeQueryFactory;
	}

	private static TermRangeQueryFactory _termRangeQueryFactory;

}