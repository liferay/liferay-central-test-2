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
 * <a href="ExpandoValuePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ExpandoValuePersistence {
	public com.liferay.portal.model.ExpandoValue create(long valueId);

	public com.liferay.portal.model.ExpandoValue remove(long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public com.liferay.portal.model.ExpandoValue remove(
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoValue update(
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoValue update(
		com.liferay.portal.model.ExpandoValue expandoValue, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoValue updateImpl(
		com.liferay.portal.model.ExpandoValue expandoValue, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoValue findByPrimaryKey(long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public com.liferay.portal.model.ExpandoValue fetchByPrimaryKey(long valueId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoValue findByC_C(long classPK,
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public com.liferay.portal.model.ExpandoValue fetchByC_C(long classPK,
		long columnId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findByClassPK(
		long classPK) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findByClassPK(
		long classPK, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findByClassPK(
		long classPK, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoValue findByClassPK_First(
		long classPK, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public com.liferay.portal.model.ExpandoValue findByClassPK_Last(
		long classPK, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public com.liferay.portal.model.ExpandoValue[] findByClassPK_PrevAndNext(
		long valueId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findByColumnId(
		long columnId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findByColumnId(
		long columnId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findByColumnId(
		long columnId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ExpandoValue findByColumnId_First(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public com.liferay.portal.model.ExpandoValue findByColumnId_Last(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public com.liferay.portal.model.ExpandoValue[] findByColumnId_PrevAndNext(
		long valueId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ExpandoValue> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long classPK, long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchExpandoValueException;

	public void removeByClassPK(long classPK)
		throws com.liferay.portal.SystemException;

	public void removeByColumnId(long columnId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByC_C(long classPK, long columnId)
		throws com.liferay.portal.SystemException;

	public int countByClassPK(long classPK)
		throws com.liferay.portal.SystemException;

	public int countByColumnId(long columnId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}