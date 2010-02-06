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

import com.liferay.portal.model.Layout;

/**
 * <a href="LayoutPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutPersistenceImpl
 * @see       LayoutUtil
 * @generated
 */
public interface LayoutPersistence extends BasePersistence<Layout> {
	public void cacheResult(com.liferay.portal.model.Layout layout);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Layout> layouts);

	public com.liferay.portal.model.Layout create(long plid);

	public com.liferay.portal.model.Layout remove(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout updateImpl(
		com.liferay.portal.model.Layout layout, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByPrimaryKey(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByPrimaryKey(long plid)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout[] findByGroupId_PrevAndNext(
		long plid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout[] findByCompanyId_PrevAndNext(
		long plid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByDLFolderId(long dlFolderId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByDLFolderId(long dlFolderId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByDLFolderId(long dlFolderId,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByIconImageId(long iconImageId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByIconImageId(
		long iconImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_First(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout[] findByG_P_PrevAndNext(long plid,
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_P_First(long groupId,
		boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_P_Last(long groupId,
		boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout[] findByG_P_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_T_First(long groupId,
		boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_T_Last(long groupId,
		boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout[] findByG_P_T_PrevAndNext(
		long plid, long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByDLFolderId(long dlFolderId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public void removeByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException;

	public void removeByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.SystemException;

	public void removeByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type) throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByDLFolderId(long dlFolderId)
		throws com.liferay.portal.SystemException;

	public int countByIconImageId(long iconImageId)
		throws com.liferay.portal.SystemException;

	public int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException;

	public int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException;

	public int countByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL) throws com.liferay.portal.SystemException;

	public int countByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}