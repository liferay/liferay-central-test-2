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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;

/**
 * @author André de Oliveira
 */
public interface QueryContributor {

	public static void add(
		BooleanQuery booleanQuery, Query query,
		BooleanClauseOccur booleanClauseOccur) {

		try {
			booleanQuery.add(query, booleanClauseOccur);
		}
		catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
	}

	public void contribute(BooleanQuery booleanQuery);

}