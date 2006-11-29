/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * <a href="CustomSQLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CustomSQLUtil {

	public static String get(String id) {
		return _instance.get(id);
	}

	public static String replaceAndOperator(String sql, boolean andOperator) {
		return _instance.replaceAndOperator(sql, andOperator);
	}

	public static String replaceIsNull(String sql) {
		return _instance.replaceIsNull(sql);
	}

	public static String removeOrderBy(String sql) {
		return _instance.removeOrderBy(sql);
	}

	public static String replaceOrderBy(String sql, OrderByComparator obc) {
		return _instance.replaceOrderBy(sql, obc);
	}

	private static PortalCustomSQLUtil _instance = new PortalCustomSQLUtil();

}