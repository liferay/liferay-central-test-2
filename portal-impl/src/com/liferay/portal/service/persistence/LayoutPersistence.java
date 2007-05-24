/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="LayoutPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface LayoutPersistence {
	public com.liferay.portal.model.Layout create(long plid);

	public com.liferay.portal.model.Layout remove(long plid)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout remove(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByPrimaryKey(long plid)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout fetchByPrimaryKey(long plid)
		throws com.liferay.portal.SystemException;

	public java.util.List findByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public java.util.List findByG_P(long groupId, boolean privateLayout,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List findByG_P(long groupId, boolean privateLayout,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_First(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout findByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout[] findByG_P_PrevAndNext(long plid,
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout findByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException;

	public java.util.List findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout findByG_P_P_First(long groupId,
		boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout findByG_P_P_Last(long groupId,
		boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout[] findByG_P_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout findByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List findAll() throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException;

	public void removeByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchLayoutException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException;

	public int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException;

	public int countByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}