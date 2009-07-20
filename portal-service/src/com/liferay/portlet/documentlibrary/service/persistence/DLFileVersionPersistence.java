/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

public interface DLFileVersionPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion);

	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> dlFileVersions);

	public void clearCache();

	public com.liferay.portlet.documentlibrary.model.DLFileVersion create(
		long fileVersionId);

	public com.liferay.portlet.documentlibrary.model.DLFileVersion remove(
		long fileVersionId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion remove(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(DLFileVersion dlFileVersion, boolean merge)</code>.
	 */
	public com.liferay.portlet.documentlibrary.model.DLFileVersion update(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        dlFileVersion the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when dlFileVersion is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.documentlibrary.model.DLFileVersion update(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByPrimaryKey(
		long fileVersionId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByPrimaryKey(
		long fileVersionId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByF_N(
		long folderId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByF_N(
		long folderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByF_N(
		long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_N_First(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_N_Last(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion[] findByF_N_PrevAndNext(
		long fileVersionId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_N_V(
		long folderId, java.lang.String name, double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByF_N_V(
		long folderId, java.lang.String name, double version)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByF_N_V(
		long folderId, java.lang.String name, double version,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByF_N(long folderId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public void removeByF_N_V(long folderId, java.lang.String name,
		double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByF_N(long folderId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countByF_N_V(long folderId, java.lang.String name, double version)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}