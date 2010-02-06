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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.wiki.model.WikiNode;

/**
 * <a href="WikiNodePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNodePersistenceImpl
 * @see       WikiNodeUtil
 * @generated
 */
public interface WikiNodePersistence extends BasePersistence<WikiNode> {
	public void cacheResult(com.liferay.portlet.wiki.model.WikiNode wikiNode);

	public void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiNode> wikiNodes);

	public com.liferay.portlet.wiki.model.WikiNode create(long nodeId);

	public com.liferay.portlet.wiki.model.WikiNode remove(long nodeId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode updateImpl(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiNode findByPrimaryKey(long nodeId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode fetchByPrimaryKey(
		long nodeId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiNode findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode[] findByUuid_PrevAndNext(
		long nodeId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiNode findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode[] findByGroupId_PrevAndNext(
		long nodeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiNode findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode[] findByCompanyId_PrevAndNext(
		long nodeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public com.liferay.portlet.wiki.model.WikiNode fetchByG_N(long groupId,
		java.lang.String name) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiNode fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}