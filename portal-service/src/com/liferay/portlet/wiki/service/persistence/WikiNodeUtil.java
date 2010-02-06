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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.wiki.model.WikiNode;

import java.util.List;

/**
 * <a href="WikiNodeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNodePersistence
 * @see       WikiNodePersistenceImpl
 * @generated
 */
public class WikiNodeUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static WikiNode remove(WikiNode wikiNode) throws SystemException {
		return getPersistence().remove(wikiNode);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static WikiNode update(WikiNode wikiNode, boolean merge)
		throws SystemException {
		return getPersistence().update(wikiNode, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.wiki.model.WikiNode wikiNode) {
		getPersistence().cacheResult(wikiNode);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiNode> wikiNodes) {
		getPersistence().cacheResult(wikiNodes);
	}

	public static com.liferay.portlet.wiki.model.WikiNode create(long nodeId) {
		return getPersistence().create(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode remove(long nodeId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().remove(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode updateImpl(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(wikiNode, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByPrimaryKey(
		long nodeId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByPrimaryKey(nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode fetchByPrimaryKey(
		long nodeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode[] findByUuid_PrevAndNext(
		long nodeId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByUuid_PrevAndNext(nodeId, uuid, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode[] findByGroupId_PrevAndNext(
		long nodeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByGroupId_PrevAndNext(nodeId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode[] findByCompanyId_PrevAndNext(
		long nodeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(nodeId, companyId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiNode findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		return getPersistence().findByG_N(groupId, name);
	}

	public static com.liferay.portlet.wiki.model.WikiNode fetchByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_N(groupId, name);
	}

	public static com.liferay.portlet.wiki.model.WikiNode fetchByG_N(
		long groupId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiNode> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchNodeException {
		getPersistence().removeByG_N(groupId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_N(groupId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static WikiNodePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WikiNodePersistence)PortalBeanLocatorUtil.locate(WikiNodePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(WikiNodePersistence persistence) {
		_persistence = persistence;
	}

	private static WikiNodePersistence _persistence;
}