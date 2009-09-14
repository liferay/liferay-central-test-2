/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.upgrade.v5_3_0.util;

import com.liferay.portal.upgrade.util.Table;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ShoppingCategoryView.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class ShoppingCategoryView extends Table {

	public static long getCategoryId(String[] values) {
		return Long.parseLong(values[0]);
	}

	public static long getGroupId(String[] values) {
		return Long.parseLong(values[1]);
	}

	public ShoppingCategoryView(String tableName) {
		super(tableName);

		List<Object[]> columns = new ArrayList<Object[]>();

		columns.add(new Object[] {"categoryId", Types.BIGINT});
		columns.add(new Object[] {"groupId", Types.BIGINT});

		setColumns(columns.toArray(new Object[0][]));
	}

	public String getSelectSQL() throws Exception {
		return _SELECT_SQL;
	}

	private static final String _SELECT_SQL =
		"SELECT ShoppingCategory.categoryId, ShoppingCategory.groupId " +
		"FROM ShoppingCategory";

}