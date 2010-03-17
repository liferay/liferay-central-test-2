/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.wiki.model.WikiPage;

import java.util.List;

/**
 * <a href="WikiPageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPagePersistence
 * @see       WikiPagePersistenceImpl
 * @generated
 */
public class WikiPageUtil {
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
	public static WikiPage remove(WikiPage wikiPage) throws SystemException {
		return getPersistence().remove(wikiPage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static WikiPage update(WikiPage wikiPage, boolean merge)
		throws SystemException {
		return getPersistence().update(wikiPage, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.wiki.model.WikiPage wikiPage) {
		getPersistence().cacheResult(wikiPage);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiPage> wikiPages) {
		getPersistence().cacheResult(wikiPages);
	}

	public static com.liferay.portlet.wiki.model.WikiPage create(long pageId) {
		return getPersistence().create(pageId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage remove(long pageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().remove(pageId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateImpl(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(wikiPage, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByPrimaryKey(
		long pageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByPrimaryKey(pageId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByPrimaryKey(
		long pageId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(pageId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByUuid_PrevAndNext(
		long pageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByUuid_PrevAndNext(pageId, uuid, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByNodeId(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByNodeId(nodeId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByNodeId(nodeId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_First(
		long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByNodeId_First(nodeId, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_Last(
		long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByNodeId_Last(nodeId, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByNodeId_PrevAndNext(
		long pageId, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByNodeId_PrevAndNext(pageId, nodeId, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFormat(format);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFormat(format, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFormat(format, start, end, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByFormat_First(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByFormat_First(format, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByFormat_Last(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByFormat_Last(format, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByFormat_PrevAndNext(
		long pageId, java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByFormat_PrevAndNext(pageId, format, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T(nodeId, title, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_T(nodeId, title, start, end, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_First(
		long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_First(nodeId, title, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_Last(
		long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_Last(nodeId, title, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_PrevAndNext(
		long pageId, long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_PrevAndNext(pageId, nodeId, title,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H(nodeId, head);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H(nodeId, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H(nodeId, head, start, end, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_First(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_First(nodeId, head, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_Last(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_Last(nodeId, head, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_PrevAndNext(
		long pageId, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_PrevAndNext(pageId, nodeId, head,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_P(nodeId, parentTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_P(nodeId, parentTitle, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_P(nodeId, parentTitle, start, end, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_P_First(
		long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_P_First(nodeId, parentTitle, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_P_Last(
		long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_P_Last(nodeId, parentTitle, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_P_PrevAndNext(
		long pageId, long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_P_PrevAndNext(pageId, nodeId, parentTitle,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_R(nodeId, redirectTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_R(nodeId, redirectTitle, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_R(nodeId, redirectTitle, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_R_First(
		long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_R_First(nodeId, redirectTitle, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_R_Last(
		long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_R_Last(nodeId, redirectTitle, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_R_PrevAndNext(
		long pageId, long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_R_PrevAndNext(pageId, nodeId, redirectTitle,
			orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_V(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_V(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByN_T_V(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(
		long nodeId, java.lang.String title, double version,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByN_T_V(nodeId, title, version, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_T_H(nodeId, title, head, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_First(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_First(nodeId, title, head, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_Last(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_Last(nodeId, title, head, orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_H_PrevAndNext(
		long pageId, long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_PrevAndNext(pageId, nodeId, title, head,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H_P(nodeId, head, parentTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_First(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_First(nodeId, head, parentTitle,
			orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_Last(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_Last(nodeId, head, parentTitle,
			orderByComparator);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_P_PrevAndNext(
		long pageId, long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_PrevAndNext(pageId, nodeId, head, parentTitle,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByNodeId(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByNodeId(nodeId);
	}

	public static void removeByFormat(java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFormat(format);
	}

	public static void removeByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_T(nodeId, title);
	}

	public static void removeByN_H(long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_H(nodeId, head);
	}

	public static void removeByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_P(nodeId, parentTitle);
	}

	public static void removeByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_R(nodeId, redirectTitle);
	}

	public static void removeByN_T_V(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		getPersistence().removeByN_T_V(nodeId, title, version);
	}

	public static void removeByN_T_H(long nodeId, java.lang.String title,
		boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_T_H(nodeId, title, head);
	}

	public static void removeByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_H_P(nodeId, head, parentTitle);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByNodeId(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByNodeId(nodeId);
	}

	public static int countByFormat(java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFormat(format);
	}

	public static int countByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_T(nodeId, title);
	}

	public static int countByN_H(long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_H(nodeId, head);
	}

	public static int countByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_P(nodeId, parentTitle);
	}

	public static int countByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_R(nodeId, redirectTitle);
	}

	public static int countByN_T_V(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_T_V(nodeId, title, version);
	}

	public static int countByN_T_H(long nodeId, java.lang.String title,
		boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_T_H(nodeId, title, head);
	}

	public static int countByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_H_P(nodeId, head, parentTitle);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static WikiPagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WikiPagePersistence)PortalBeanLocatorUtil.locate(WikiPagePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(WikiPagePersistence persistence) {
		_persistence = persistence;
	}

	private static WikiPagePersistence _persistence;
}