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
 * <a href="ExpandoValueUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueUtil {
	public static com.liferay.portal.model.ExpandoValue create(long valueId) {
		return getPersistence().create(valueId);
	}

	public static com.liferay.portal.model.ExpandoValue remove(long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().remove(valueId);
	}

	public static com.liferay.portal.model.ExpandoValue remove(
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(expandoValue);
	}

	/**
	 * @deprecated Use <code>update(ExpandoValue expandoValue, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.ExpandoValue update(
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoValue);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoValue the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoValue is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.ExpandoValue update(
		com.liferay.portal.model.ExpandoValue expandoValue, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoValue, merge);
	}

	public static com.liferay.portal.model.ExpandoValue updateImpl(
		com.liferay.portal.model.ExpandoValue expandoValue, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(expandoValue, merge);
	}

	public static com.liferay.portal.model.ExpandoValue findByPrimaryKey(
		long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().findByPrimaryKey(valueId);
	}

	public static com.liferay.portal.model.ExpandoValue fetchByPrimaryKey(
		long valueId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(valueId);
	}

	public static com.liferay.portal.model.ExpandoValue findByC_C(
		long classPK, long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().findByC_C(classPK, columnId);
	}

	public static com.liferay.portal.model.ExpandoValue fetchByC_C(
		long classPK, long columnId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classPK, columnId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findByClassPK(
		long classPK) throws com.liferay.portal.SystemException {
		return getPersistence().findByClassPK(classPK);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findByClassPK(
		long classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassPK(classPK, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findByClassPK(
		long classPK, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassPK(classPK, begin, end, obc);
	}

	public static com.liferay.portal.model.ExpandoValue findByClassPK_First(
		long classPK, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().findByClassPK_First(classPK, obc);
	}

	public static com.liferay.portal.model.ExpandoValue findByClassPK_Last(
		long classPK, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().findByClassPK_Last(classPK, obc);
	}

	public static com.liferay.portal.model.ExpandoValue[] findByClassPK_PrevAndNext(
		long valueId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().findByClassPK_PrevAndNext(valueId, classPK, obc);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findByColumnId(
		long columnId) throws com.liferay.portal.SystemException {
		return getPersistence().findByColumnId(columnId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findByColumnId(
		long columnId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByColumnId(columnId, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findByColumnId(
		long columnId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByColumnId(columnId, begin, end, obc);
	}

	public static com.liferay.portal.model.ExpandoValue findByColumnId_First(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().findByColumnId_First(columnId, obc);
	}

	public static com.liferay.portal.model.ExpandoValue findByColumnId_Last(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence().findByColumnId_Last(columnId, obc);
	}

	public static com.liferay.portal.model.ExpandoValue[] findByColumnId_PrevAndNext(
		long valueId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		return getPersistence()
				   .findByColumnId_PrevAndNext(valueId, columnId, obc);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByC_C(long classPK, long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException {
		getPersistence().removeByC_C(classPK, columnId);
	}

	public static void removeByClassPK(long classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByClassPK(classPK);
	}

	public static void removeByColumnId(long columnId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByColumnId(columnId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(long classPK, long columnId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classPK, columnId);
	}

	public static int countByClassPK(long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByClassPK(classPK);
	}

	public static int countByColumnId(long columnId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByColumnId(columnId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoValuePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ExpandoValuePersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoValueUtil _getUtil() {
		if (_util == null) {
			_util = (ExpandoValueUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ExpandoValueUtil.class.getName();
	private static ExpandoValueUtil _util;
	private ExpandoValuePersistence _persistence;
}