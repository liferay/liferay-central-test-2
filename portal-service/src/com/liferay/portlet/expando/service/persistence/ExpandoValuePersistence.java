/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.expando.model.ExpandoValue;

/**
 * <a href="ExpandoValuePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoValuePersistenceImpl
 * @see       ExpandoValueUtil
 * @generated
 */
public interface ExpandoValuePersistence extends BasePersistence<ExpandoValue> {
	public void cacheResult(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue);

	public void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> expandoValues);

	public com.liferay.portlet.expando.model.ExpandoValue create(long valueId);

	public com.liferay.portlet.expando.model.ExpandoValue remove(long valueId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByPrimaryKey(
		long valueId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue fetchByPrimaryKey(
		long valueId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByTableId_First(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByTableId_Last(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByTableId_PrevAndNext(
		long valueId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByColumnId_First(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByColumnId_Last(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByColumnId_PrevAndNext(
		long valueId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByRowId_First(
		long rowId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByRowId_Last(
		long rowId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByRowId_PrevAndNext(
		long valueId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_First(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_Last(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_PrevAndNext(
		long valueId, long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_First(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_Last(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_CPK_PrevAndNext(
		long valueId, long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_R_First(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_R_Last(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_R_PrevAndNext(
		long valueId, long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByC_C_PrevAndNext(
		long valueId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_First(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_Last(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_D_PrevAndNext(
		long valueId, long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByColumnId(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByRowId(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_C(long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_R(long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_R(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_C_C(long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchValueException;

	public void removeByT_C_D(long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByColumnId(long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByRowId(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_C(long tableId, long columnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_R(long tableId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_R(long columnId, long rowId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_C_C(long tableId, long columnId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_C_D(long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}