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

package com.liferay.portal.kernel.search;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SortFactoryImpl implements SortFactory {

	public Sort create(String fieldName, boolean reverse) {
		return new Sort(fieldName, reverse);
	}

	public Sort create(String fieldName, int type, boolean reverse) {
		return new Sort(fieldName, type, reverse);
	}

	public Sort[] getDefaultSorts() {
		return _DEFAULT_SORTS;
	}

	public Sort[] toArray(List<Sort> sorts) {
		if ((sorts == null) || sorts.isEmpty()) {
			return new Sort[0];
		}

		Sort[] sortsArray = new Sort[sorts.size()];

		for (int i = 0; i < sorts.size(); i++) {
			sortsArray[i] = (Sort)sorts.get(i);
		}

		return sortsArray;
	}

	private static final Sort[] _DEFAULT_SORTS = new Sort[] {
		new Sort(null, Sort.SCORE_TYPE, false),
		new Sort(Field.MODIFIED, Sort.LONG_TYPE, true)
	};

}