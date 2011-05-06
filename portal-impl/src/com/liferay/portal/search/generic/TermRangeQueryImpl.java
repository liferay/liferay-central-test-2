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

package com.liferay.portal.search.generic;

import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Raymond Augé
 */
public class TermRangeQueryImpl extends BaseQueryImpl
	implements TermRangeQuery {

	public TermRangeQueryImpl(
		String field, String lowerTerm, String upperTerm, boolean includeLower,
		boolean includeUpper) {

		_field = field;
		_lowerTerm = lowerTerm;
		_upperTerm = upperTerm;
		_includeLower = includeLower;
		_includeUpper = includeUpper;
	}

	public String getField() {
		return _field;
	}

	public String getLowerTerm() {
		return _lowerTerm;
	}

	public String getUpperTerm() {
		return _upperTerm;
	}

	public Object getWrappedQuery() {
		return this;
	}

	public boolean includeLower() {
		return _includeLower;
	}

	public boolean includeUpper() {
		return _includeUpper;
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append(getField());
		sb.append(':');

		sb.append(includeLower() ? '[' : '{');
		sb.append(getLowerTerm() != null ? getLowerTerm() : '*');
		sb.append(" TO ");
		sb.append(getUpperTerm() != null ? getUpperTerm() : '*');
		sb.append(includeUpper() ? ']' : '}');

		return sb.toString();
	}

	private String _field;
	private boolean _includeLower;
	private boolean _includeUpper;
	private String _lowerTerm;
	private String _upperTerm;

}