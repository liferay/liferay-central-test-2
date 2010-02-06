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

import com.liferay.portlet.expando.model.ExpandoTable;

/**
 * <a href="ExpandoTablePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoTablePersistenceImpl
 * @see       ExpandoTableUtil
 * @generated
 */
public interface ExpandoTablePersistence extends BasePersistence<ExpandoTable> {
	public void cacheResult(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable);

	public void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoTable> expandoTables);

	public com.liferay.portlet.expando.model.ExpandoTable create(long tableId);

	public com.liferay.portlet.expando.model.ExpandoTable remove(long tableId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public com.liferay.portlet.expando.model.ExpandoTable updateImpl(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoTable findByPrimaryKey(
		long tableId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public com.liferay.portlet.expando.model.ExpandoTable fetchByPrimaryKey(
		long tableId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoTable findByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public com.liferay.portlet.expando.model.ExpandoTable findByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public com.liferay.portlet.expando.model.ExpandoTable[] findByC_C_PrevAndNext(
		long tableId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public com.liferay.portlet.expando.model.ExpandoTable findByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public void removeByC_C_N(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchTableException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public int countByC_C_N(long companyId, long classNameId,
		java.lang.String name) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}