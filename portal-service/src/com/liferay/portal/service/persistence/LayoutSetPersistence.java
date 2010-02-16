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

import com.liferay.portal.model.LayoutSet;

/**
 * <a href="LayoutSetPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPersistenceImpl
 * @see       LayoutSetUtil
 * @generated
 */
public interface LayoutSetPersistence extends BasePersistence<LayoutSet> {
	public void cacheResult(com.liferay.portal.model.LayoutSet layoutSet);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutSet> layoutSets);

	public com.liferay.portal.model.LayoutSet create(long layoutSetId);

	public com.liferay.portal.model.LayoutSet remove(long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet updateImpl(
		com.liferay.portal.model.LayoutSet layoutSet, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet findByPrimaryKey(long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet fetchByPrimaryKey(
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet[] findByGroupId_PrevAndNext(
		long layoutSetId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet fetchByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet findByG_P(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet fetchByG_P(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSet fetchByG_P(long groupId,
		boolean privateLayout, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSet> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSet> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSet> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}