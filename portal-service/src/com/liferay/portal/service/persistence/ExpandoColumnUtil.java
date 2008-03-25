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
 * <a href="ExpandoColumnUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnUtil {
	public static com.liferay.portal.model.ExpandoColumn create(long columnId) {
		return getPersistence().create(columnId);
	}

	public static com.liferay.portal.model.ExpandoColumn remove(long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException {
		return getPersistence().remove(columnId);
	}

	public static com.liferay.portal.model.ExpandoColumn remove(
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(expandoColumn);
	}

	public static com.liferay.portal.model.ExpandoColumn update(
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoColumn);
	}

	public static com.liferay.portal.model.ExpandoColumn update(
		com.liferay.portal.model.ExpandoColumn expandoColumn, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoColumn, merge);
	}

	public static com.liferay.portal.model.ExpandoColumn updateImpl(
		com.liferay.portal.model.ExpandoColumn expandoColumn, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(expandoColumn, merge);
	}

	public static com.liferay.portal.model.ExpandoColumn findByPrimaryKey(
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException {
		return getPersistence().findByPrimaryKey(columnId);
	}

	public static com.liferay.portal.model.ExpandoColumn fetchByPrimaryKey(
		long columnId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(columnId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findByClassNameId(
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findByClassNameId(
		long classNameId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findByClassNameId(
		long classNameId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId, begin, end, obc);
	}

	public static com.liferay.portal.model.ExpandoColumn findByClassNameId_First(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException {
		return getPersistence().findByClassNameId_First(classNameId, obc);
	}

	public static com.liferay.portal.model.ExpandoColumn findByClassNameId_Last(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException {
		return getPersistence().findByClassNameId_Last(classNameId, obc);
	}

	public static com.liferay.portal.model.ExpandoColumn[] findByClassNameId_PrevAndNext(
		long columnId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(columnId, classNameId, obc);
	}

	public static com.liferay.portal.model.ExpandoColumn findByC_N(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException {
		return getPersistence().findByC_N(classNameId, name);
	}

	public static com.liferay.portal.model.ExpandoColumn fetchByC_N(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(classNameId, name);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByClassNameId(classNameId);
	}

	public static void removeByC_N(long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoColumnException {
		getPersistence().removeByC_N(classNameId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByClassNameId(classNameId);
	}

	public static int countByC_N(long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N(classNameId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoColumnPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ExpandoColumnPersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoColumnUtil _getUtil() {
		if (_util == null) {
			_util = (ExpandoColumnUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ExpandoColumnUtil.class.getName();
	private static ExpandoColumnUtil _util;
	private ExpandoColumnPersistence _persistence;
}