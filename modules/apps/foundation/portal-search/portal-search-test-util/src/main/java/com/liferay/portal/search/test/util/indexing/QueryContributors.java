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
import com.liferay.portal.kernel.search.generic.MatchQuery;

/**
 * @author André de Oliveira
 */
public class QueryContributors {

	public static QueryContributor mustMatch(String field, String value) {
		return booleanQuery -> QueryContributor.add(
			booleanQuery, new MatchQuery(field, value),
			BooleanClauseOccur.MUST);
	}

	public static QueryContributor mustNotMatch(String field, String value) {
		return booleanQuery -> QueryContributor.add(
			booleanQuery, new MatchQuery(field, value),
			BooleanClauseOccur.MUST_NOT);
	}

}