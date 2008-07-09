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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="DynamicQueryFactoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DynamicQueryFactoryUtil {

	public static DynamicQuery forClass(Class clazz) {
		return getDynamicQueryFactory().forClass(clazz);
	}

	public static DynamicQuery forClass(Class clazz, String alias) {
		return getDynamicQueryFactory().forClass(clazz, alias);
	}

	public static DynamicQueryFactory getDynamicQueryFactory() {
		return _getUtil()._dynamicQueryFactory;
	}

	public void setDynamicQueryFactory(
		DynamicQueryFactory dynamicQueryFactory) {

		_dynamicQueryFactory = dynamicQueryFactory;
	}

	private static DynamicQueryFactoryUtil _getUtil() {
		if (_util == null) {
			_util = (DynamicQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = DynamicQueryFactoryUtil.class.getName();

	private static DynamicQueryFactoryUtil _util;

	private DynamicQueryFactory _dynamicQueryFactory;

}