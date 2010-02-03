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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * <a href="DB2Dialect.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shepherd Ching
 * @author Jian Cao
 */
public class DB2Dialect extends org.hibernate.dialect.DB2Dialect {

	public String getLimitString(String sql, boolean hasOffset) {
		if (!sql.startsWith("(")) {
			return super.getLimitString(sql, hasOffset);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("select cursor1.* from (");
		sb.append("select rownumber() over() as rownumber_, cursor2.* from (");
		sb.append(sql);
		sb.append(") as cursor2) as cursor1 where rownumber_");

		if (hasOffset) {
			sb.append(" between ? + 1 and ?");
		}
		else {
			sb.append(" <= ?");
		}

		return sb.toString();
	}

}