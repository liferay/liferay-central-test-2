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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

/**
 * <a href="DLFileShortcutPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcutPersistenceImpl
 * @see       DLFileShortcutUtil
 * @generated
 */
public interface DLFileShortcutPersistence extends BasePersistence<DLFileShortcut> {
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut);

	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> dlFileShortcuts);

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut create(
		long fileShortcutId);

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut remove(
		long fileShortcutId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByPrimaryKey(
		long fileShortcutId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByUuid_PrevAndNext(
		long fileShortcutId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_PrevAndNext(
		long fileShortcutId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_First(
		long groupId, long toFolderId, java.lang.String toName,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_Last(
		long groupId, long toFolderId, java.lang.String toName,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_TF_TN_PrevAndNext(
		long fileShortcutId, long groupId, long toFolderId,
		java.lang.String toName,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_S_First(
		long groupId, long toFolderId, java.lang.String toName, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_S_Last(
		long groupId, long toFolderId, java.lang.String toName, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_TF_TN_S_PrevAndNext(
		long fileShortcutId, long groupId, long toFolderId,
		java.lang.String toName, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.SystemException;

	public void removeByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.SystemException;

	public void removeByG_TF_TN(long groupId, long toFolderId,
		java.lang.String toName) throws com.liferay.portal.SystemException;

	public void removeByG_TF_TN_S(long groupId, long toFolderId,
		java.lang.String toName, int status)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.SystemException;

	public int countByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.SystemException;

	public int countByG_TF_TN(long groupId, long toFolderId,
		java.lang.String toName) throws com.liferay.portal.SystemException;

	public int countByG_TF_TN_S(long groupId, long toFolderId,
		java.lang.String toName, int status)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}