/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import javax.sql.DataSource;

/**
 * <a href="MappingSqlQueryFactoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MappingSqlQueryFactoryUtil {

	public static MappingSqlQuery getMappingSqlQuery(
		DataSource dataSource, String sql, int[] types, RowMapper rowMapper) {

		return getMappingSqlQueryFactory().getMappingSqlQuery(
			dataSource, sql, types, rowMapper);
	}

	public static MappingSqlQueryFactory getMappingSqlQueryFactory() {
		return _getUtil()._sqlUpdateFactory;
	}

	public void setMappingSqlQueryFactory(
		MappingSqlQueryFactory sqlUpdateFactory) {

		_sqlUpdateFactory = sqlUpdateFactory;
	}

	private static MappingSqlQueryFactoryUtil _getUtil() {
		if (_util == null) {
			_util = (MappingSqlQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				_UTIL);
		}

		return _util;
	}

	private static final String _UTIL =
		MappingSqlQueryFactoryUtil.class.getName();

	private static MappingSqlQueryFactoryUtil _util;

	private MappingSqlQueryFactory _sqlUpdateFactory;

}