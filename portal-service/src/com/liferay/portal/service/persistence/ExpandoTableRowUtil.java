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
 * <a href="ExpandoTableRowUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableRowUtil {
	public static com.liferay.portal.model.ExpandoTableRow create(long rowId) {
		return getPersistence().create(rowId);
	}

	public static com.liferay.portal.model.ExpandoTableRow remove(long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().remove(rowId);
	}

	public static com.liferay.portal.model.ExpandoTableRow remove(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(expandoTableRow);
	}

	public static com.liferay.portal.model.ExpandoTableRow update(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoTableRow);
	}

	public static com.liferay.portal.model.ExpandoTableRow update(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoTableRow, merge);
	}

	public static com.liferay.portal.model.ExpandoTableRow updateImpl(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(expandoTableRow, merge);
	}

	public static com.liferay.portal.model.ExpandoTableRow findByPrimaryKey(
		long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().findByPrimaryKey(rowId);
	}

	public static com.liferay.portal.model.ExpandoTableRow fetchByPrimaryKey(
		long rowId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(rowId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findByTableId(
		long tableId) throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findByTableId(
		long tableId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findByTableId(
		long tableId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, begin, end, obc);
	}

	public static com.liferay.portal.model.ExpandoTableRow findByTableId_First(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().findByTableId_First(tableId, obc);
	}

	public static com.liferay.portal.model.ExpandoTableRow findByTableId_Last(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().findByTableId_Last(tableId, obc);
	}

	public static com.liferay.portal.model.ExpandoTableRow[] findByTableId_PrevAndNext(
		long rowId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().findByTableId_PrevAndNext(rowId, tableId, obc);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByTableId(tableId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByTableId(tableId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().getExpandoValues(pk);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().getExpandoValues(pk, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		return getPersistence().getExpandoValues(pk, begin, end, obc);
	}

	public static int getExpandoValuesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getExpandoValuesSize(pk);
	}

	public static boolean containsExpandoValue(long pk, long expandoValuePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsExpandoValue(pk, expandoValuePK);
	}

	public static boolean containsExpandoValues(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsExpandoValues(pk);
	}

	public static void addExpandoValue(long pk, long expandoValuePK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().addExpandoValue(pk, expandoValuePK);
	}

	public static void addExpandoValue(long pk,
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().addExpandoValue(pk, expandoValue);
	}

	public static void addExpandoValues(long pk, long[] expandoValuePKs)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().addExpandoValues(pk, expandoValuePKs);
	}

	public static void addExpandoValues(long pk,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().addExpandoValues(pk, expandoValues);
	}

	public static void clearExpandoValues(long pk)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().clearExpandoValues(pk);
	}

	public static void removeExpandoValue(long pk, long expandoValuePK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().removeExpandoValue(pk, expandoValuePK);
	}

	public static void removeExpandoValue(long pk,
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().removeExpandoValue(pk, expandoValue);
	}

	public static void removeExpandoValues(long pk, long[] expandoValuePKs)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().removeExpandoValues(pk, expandoValuePKs);
	}

	public static void removeExpandoValues(long pk,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().removeExpandoValues(pk, expandoValues);
	}

	public static void setExpandoValues(long pk, long[] expandoValuePKs)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().setExpandoValues(pk, expandoValuePKs);
	}

	public static void setExpandoValues(long pk,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException {
		getPersistence().setExpandoValues(pk, expandoValues);
	}

	public static ExpandoTableRowPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ExpandoTableRowPersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoTableRowUtil _getUtil() {
		if (_util == null) {
			_util = (ExpandoTableRowUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ExpandoTableRowUtil.class.getName();
	private static ExpandoTableRowUtil _util;
	private ExpandoTableRowPersistence _persistence;
}