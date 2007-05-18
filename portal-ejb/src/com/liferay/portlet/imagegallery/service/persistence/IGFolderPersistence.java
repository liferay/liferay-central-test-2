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

package com.liferay.portlet.imagegallery.service.persistence;

/**
 * <a href="IGFolderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface IGFolderPersistence {
	public com.liferay.portlet.imagegallery.model.IGFolder create(long folderId);

	public com.liferay.portlet.imagegallery.model.IGFolder remove(long folderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder remove(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder update(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder update(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByPrimaryKey(
		long folderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder fetchByPrimaryKey(
		long folderId) throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public java.util.List findByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByG_P(long groupId, long parentFolderId,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List findByG_P(long groupId, long parentFolderId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.imagegallery.NoSuchFolderException;

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

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}