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

import com.liferay.portal.model.Resource;

/**
 * <a href="ResourcePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePersistenceImpl
 * @see       ResourceUtil
 * @generated
 */
public interface ResourcePersistence extends BasePersistence<Resource> {
	public void cacheResult(com.liferay.portal.model.Resource resource);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Resource> resources);

	public com.liferay.portal.model.Resource create(long resourceId);

	public com.liferay.portal.model.Resource remove(long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource updateImpl(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource findByPrimaryKey(long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource fetchByPrimaryKey(long resourceId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource findByCodeId_First(long codeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource findByCodeId_Last(long codeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource[] findByCodeId_PrevAndNext(
		long resourceId, long codeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource findByC_P(long codeId,
		java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCodeId(long codeId)
		throws com.liferay.portal.SystemException;

	public void removeByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCodeId(long codeId)
		throws com.liferay.portal.SystemException;

	public int countByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}