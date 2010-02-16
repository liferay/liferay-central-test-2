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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.imagegallery.model.IGFolder;

/**
 * <a href="IGFolderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolderPersistenceImpl
 * @see       IGFolderUtil
 * @generated
 */
public interface IGFolderPersistence extends BasePersistence<IGFolder> {
	public void cacheResult(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder);

	public void cacheResult(
		java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> igFolders);

	public com.liferay.portlet.imagegallery.model.IGFolder create(long folderId);

	public com.liferay.portlet.imagegallery.model.IGFolder remove(long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder updateImpl(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder fetchByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByUuid_PrevAndNext(
		long folderId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByCompanyId_PrevAndNext(
		long folderId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder[] findByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder findByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public com.liferay.portlet.imagegallery.model.IGFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.imagegallery.model.IGFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}