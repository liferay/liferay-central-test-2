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
 * <a href="ExpandoTableRowPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ExpandoTableRowPersistence {
	public com.liferay.portal.model.ExpandoTableRow create(long rowId);

	public com.liferay.portal.model.ExpandoTableRow remove(long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public com.liferay.portal.model.ExpandoTableRow remove(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ExpandoTableRow expandoTableRow, boolean merge)</code>.
	 */
	public com.liferay.portal.model.ExpandoTableRow update(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoTableRow the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoTableRow is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.ExpandoTableRow update(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoTableRow updateImpl(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoTableRow findByPrimaryKey(long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public com.liferay.portal.model.ExpandoTableRow fetchByPrimaryKey(
		long rowId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findByTableId(
		long tableId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findByTableId(
		long tableId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findByTableId(
		long tableId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoTableRow findByTableId_First(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public com.liferay.portal.model.ExpandoTableRow findByTableId_Last(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public com.liferay.portal.model.ExpandoTableRow[] findByTableId_PrevAndNext(
		long rowId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoTableRow> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByTableId(long tableId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByTableId(long tableId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> getExpandoValues(
		long pk, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public int getExpandoValuesSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsExpandoValue(long pk, long expandoValuePK)
		throws com.liferay.portal.SystemException;

	public boolean containsExpandoValues(long pk)
		throws com.liferay.portal.SystemException;

	public void addExpandoValue(long pk, long expandoValuePK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void addExpandoValue(long pk,
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void addExpandoValues(long pk, long[] expandoValuePKs)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void addExpandoValues(long pk,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void clearExpandoValues(long pk)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void removeExpandoValue(long pk, long expandoValuePK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void removeExpandoValue(long pk,
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void removeExpandoValues(long pk, long[] expandoValuePKs)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void removeExpandoValues(long pk,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void setExpandoValues(long pk, long[] expandoValuePKs)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;

	public void setExpandoValues(long pk,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException,
			com.liferay.portal.NoSuchExpandoTableRowException;
}