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

package com.liferay.portlet.documentlibrary.service.persistence;

/**
 * <a href="DLFolderPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface DLFolderPersistence {
	public com.liferay.portlet.documentlibrary.model.DLFolder create(
		java.lang.String folderId);

	public com.liferay.portlet.documentlibrary.model.DLFolder remove(
		java.lang.String folderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder remove(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder update(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder update(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByPrimaryKey(
		java.lang.String folderId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByPrimaryKey(
		java.lang.String folderId) throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByGroupId_PrevAndNext(
		java.lang.String folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByCompanyId(long companyId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByCompanyId(long companyId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByCompanyId_PrevAndNext(
		java.lang.String folderId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public java.util.List findByG_P(long groupId,
		java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByG_P(long groupId,
		java.lang.String parentFolderId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByG_P(long groupId,
		java.lang.String parentFolderId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_First(
		long groupId, java.lang.String parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_Last(
		long groupId, java.lang.String parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByG_P_PrevAndNext(
		java.lang.String folderId, long groupId,
		java.lang.String parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder findByP_N(
		java.lang.String parentFolderId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByP_N(
		java.lang.String parentFolderId, java.lang.String name)
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

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByG_P(long groupId, java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException;

	public void removeByP_N(java.lang.String parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByG_P(long groupId, java.lang.String parentFolderId)
		throws com.liferay.portal.SystemException;

	public int countByP_N(java.lang.String parentFolderId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}