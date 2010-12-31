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
 * @author Brian Wing Shun Chan
 */
public class TermQueryFactoryUtil {

	public static TermQuery create(String field, long value) {
		return getTermQueryFactory().create(field, value);
	}

	public static TermQuery create(String field, String value) {
		return getTermQueryFactory().create(field, value);
	}

	public static TermQueryFactory getTermQueryFactory() {
		return _termQueryFactory;
	}

	public void setTermQueryFactory(
		TermQueryFactory termQueryFactory) {

		_termQueryFactory = termQueryFactory;
	}

	private static TermQueryFactory _termQueryFactory;

}