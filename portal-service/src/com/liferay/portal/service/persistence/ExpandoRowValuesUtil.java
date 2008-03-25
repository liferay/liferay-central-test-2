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

package com.liferay.portal.service.persistence;

/**
 * <a href="ExpandoRowValuesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoRowValuesUtil {
	public static com.liferay.portal.model.ExpandoRowValues create(
		com.liferay.portal.service.persistence.ExpandoRowValuesPK expandoRowValuesPK) {
		return getPersistence().create(expandoRowValuesPK);
	}

	public static com.liferay.portal.model.ExpandoRowValues remove(
		com.liferay.portal.service.persistence.ExpandoRowValuesPK expandoRowValuesPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoRowValuesException {
		return getPersistence().remove(expandoRowValuesPK);
	}

	public static com.liferay.portal.model.ExpandoRowValues remove(
		com.liferay.portal.model.ExpandoRowValues expandoRowValues)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(expandoRowValues);
	}

	public static com.liferay.portal.model.ExpandoRowValues update(
		com.liferay.portal.model.ExpandoRowValues expandoRowValues)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoRowValues);
	}

	public static com.liferay.portal.model.ExpandoRowValues update(
		com.liferay.portal.model.ExpandoRowValues expandoRowValues,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoRowValues, merge);
	}

	public static com.liferay.portal.model.ExpandoRowValues updateImpl(
		com.liferay.portal.model.ExpandoRowValues expandoRowValues,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(expandoRowValues, merge);
	}

	public static com.liferay.portal.model.ExpandoRowValues findByPrimaryKey(
		com.liferay.portal.service.persistence.ExpandoRowValuesPK expandoRowValuesPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoRowValuesException {
		return getPersistence().findByPrimaryKey(expandoRowValuesPK);
	}

	public static com.liferay.portal.model.ExpandoRowValues fetchByPrimaryKey(
		com.liferay.portal.service.persistence.ExpandoRowValuesPK expandoRowValuesPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(expandoRowValuesPK);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoRowValues> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoRowValues> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoRowValues> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ExpandoRowValues> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoRowValues> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoRowValuesPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ExpandoRowValuesPersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoRowValuesUtil _getUtil() {
		if (_util == null) {
			_util = (ExpandoRowValuesUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ExpandoRowValuesUtil.class.getName();
	private static ExpandoRowValuesUtil _util;
	private ExpandoRowValuesPersistence _persistence;
}