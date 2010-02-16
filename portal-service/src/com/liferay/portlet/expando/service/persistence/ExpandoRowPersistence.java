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

import com.liferay.portlet.expando.model.ExpandoRow;

/**
 * <a href="ExpandoRowPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRowPersistenceImpl
 * @see       ExpandoRowUtil
 * @generated
 */
public interface ExpandoRowPersistence extends BasePersistence<ExpandoRow> {
	public void cacheResult(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow);

	public void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> expandoRows);

	public com.liferay.portlet.expando.model.ExpandoRow create(long rowId);

	public com.liferay.portlet.expando.model.ExpandoRow remove(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public com.liferay.portlet.expando.model.ExpandoRow updateImpl(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoRow findByPrimaryKey(
		long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public com.liferay.portlet.expando.model.ExpandoRow fetchByPrimaryKey(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoRow findByTableId_First(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public com.liferay.portlet.expando.model.ExpandoRow findByTableId_Last(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public com.liferay.portlet.expando.model.ExpandoRow[] findByTableId_PrevAndNext(
		long rowId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public com.liferay.portlet.expando.model.ExpandoRow findByT_C(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public com.liferay.portlet.expando.model.ExpandoRow fetchByT_C(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.expando.model.ExpandoRow fetchByT_C(
		long tableId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_C(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_C(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}