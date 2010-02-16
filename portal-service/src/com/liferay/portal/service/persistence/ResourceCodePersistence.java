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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ResourceCode;

/**
 * <a href="ResourceCodePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCodePersistenceImpl
 * @see       ResourceCodeUtil
 * @generated
 */
public interface ResourceCodePersistence extends BasePersistence<ResourceCode> {
	public void cacheResult(com.liferay.portal.model.ResourceCode resourceCode);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceCode> resourceCodes);

	public com.liferay.portal.model.ResourceCode create(long codeId);

	public com.liferay.portal.model.ResourceCode remove(long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode updateImpl(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode findByPrimaryKey(long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode fetchByPrimaryKey(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode[] findByCompanyId_PrevAndNext(
		long codeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode findByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode findByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode[] findByName_PrevAndNext(
		long codeId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode findByC_N_S(long companyId,
		java.lang.String name, int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode fetchByC_N_S(long companyId,
		java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode fetchByC_N_S(long companyId,
		java.lang.String name, int scope, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_N_S(long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_N_S(long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}