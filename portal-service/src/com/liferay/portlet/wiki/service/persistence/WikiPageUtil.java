/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.wiki.model.WikiPage;

import java.util.List;

/**
 * The persistence utility for the wiki page service. This utility wraps {@link WikiPagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPagePersistence
 * @see WikiPagePersistenceImpl
 * @generated
 */
public class WikiPageUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(WikiPage wikiPage) {
		getPersistence().clearCache(wikiPage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WikiPage> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WikiPage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WikiPage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static WikiPage update(WikiPage wikiPage, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(wikiPage, merge, serviceContext);
	}

	/**
	* Caches the wiki page in the entity cache if it is enabled.
	*
	* @param wikiPage the wiki page to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.wiki.model.WikiPage wikiPage) {
		getPersistence().cacheResult(wikiPage);
	}

	/**
	* Caches the wiki pages in the entity cache if it is enabled.
	*
	* @param wikiPages the wiki pages to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiPage> wikiPages) {
		getPersistence().cacheResult(wikiPages);
	}

	/**
	* Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	*
	* @param pageId the primary key for the new wiki page
	* @return the new wiki page
	*/
	public static com.liferay.portlet.wiki.model.WikiPage create(long pageId) {
		return getPersistence().create(pageId);
	}

	/**
	* Removes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pageId the primary key of the wiki page to remove
	* @return the wiki page that was removed
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
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

	/**
	* Finds the wiki page with the primary key or throws a {@link com.liferay.portlet.wiki.NoSuchPageException} if it could not be found.
	*
	* @param pageId the primary key of the wiki page to find
	* @return the wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByPrimaryKey(
		long pageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByPrimaryKey(pageId);
	}

	/**
	* Finds the wiki page with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param pageId the primary key of the wiki page to find
	* @return the wiki page, or <code>null</code> if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage fetchByPrimaryKey(
		long pageId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(pageId);
	}

	/**
	* Finds all the wiki pages where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the wiki pages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByUuid_PrevAndNext(
		long pageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByUuid_PrevAndNext(pageId, uuid, orderByComparator);
	}

	/**
	* Finds the wiki page where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.wiki.NoSuchPageException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Finds the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Finds the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63;.
	*
	* @param nodeId the node ID to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByNodeId(nodeId);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByNodeId(nodeId, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByNodeId(nodeId, start, end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_First(
		long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByNodeId_First(nodeId, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_Last(
		long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByNodeId_Last(nodeId, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByNodeId_PrevAndNext(
		long pageId, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByNodeId_PrevAndNext(pageId, nodeId, orderByComparator);
	}

	/**
	* Finds all the wiki pages where format = &#63;.
	*
	* @param format the format to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFormat(format);
	}

	/**
	* Finds a range of all the wiki pages where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param format the format to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFormat(format, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param format the format to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFormat(format, start, end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param format the format to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByFormat_First(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByFormat_First(format, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param format the format to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByFormat_Last(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByFormat_Last(format, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where format = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param format the format to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByFormat_PrevAndNext(
		long pageId, java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByFormat_PrevAndNext(pageId, format, orderByComparator);
	}

	/**
	* Finds all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByR_N(
		long resourcePrimKey, long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_N(resourcePrimKey, nodeId);
	}

	/**
	* Finds a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByR_N(
		long resourcePrimKey, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_N(resourcePrimKey, nodeId, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByR_N(
		long resourcePrimKey, long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_N(resourcePrimKey, nodeId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByR_N_First(
		long resourcePrimKey, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByR_N_First(resourcePrimKey, nodeId, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByR_N_Last(
		long resourcePrimKey, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByR_N_Last(resourcePrimKey, nodeId, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByR_N_PrevAndNext(
		long pageId, long resourcePrimKey, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByR_N_PrevAndNext(pageId, resourcePrimKey, nodeId,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T(nodeId, title);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T(nodeId, title, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_T(nodeId, title, start, end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_First(
		long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_First(nodeId, title, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_Last(
		long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_Last(nodeId, title, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_PrevAndNext(
		long pageId, long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_PrevAndNext(pageId, nodeId, title,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H(nodeId, head);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H(nodeId, head, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H(nodeId, head, start, end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_First(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_First(nodeId, head, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_Last(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_Last(nodeId, head, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_PrevAndNext(
		long pageId, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_PrevAndNext(pageId, nodeId, head,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_P(nodeId, parentTitle);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_P(nodeId, parentTitle, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_P(nodeId, parentTitle, start, end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_P_First(
		long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_P_First(nodeId, parentTitle, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_P_Last(
		long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_P_Last(nodeId, parentTitle, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_P_PrevAndNext(
		long pageId, long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_P_PrevAndNext(pageId, nodeId, parentTitle,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_R(nodeId, redirectTitle);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_R(nodeId, redirectTitle, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_R(nodeId, redirectTitle, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_R_First(
		long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_R_First(nodeId, redirectTitle, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_R_Last(
		long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_R_Last(nodeId, redirectTitle, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_R_PrevAndNext(
		long pageId, long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_R_PrevAndNext(pageId, nodeId, redirectTitle,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_S(
		long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_S(nodeId, status);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_S(
		long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_S(nodeId, status, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_S(
		long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_S(nodeId, status, start, end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_S_First(
		long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_S_First(nodeId, status, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_S_Last(
		long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_S_Last(nodeId, status, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_S_PrevAndNext(
		long pageId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_S_PrevAndNext(pageId, nodeId, status,
			orderByComparator);
	}

	/**
	* Finds the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.wiki.NoSuchPageException} if it could not be found.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param version the version to search with
	* @return the matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByR_N_V(
		long resourcePrimKey, long nodeId, double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Finds the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param version the version to search with
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage fetchByR_N_V(
		long resourcePrimKey, long nodeId, double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Finds the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param version the version to search with
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage fetchByR_N_V(
		long resourcePrimKey, long nodeId, double version,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByR_N_V(resourcePrimKey, nodeId, version,
			retrieveFromCache);
	}

	/**
	* Finds all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByR_N_S(
		long resourcePrimKey, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_N_S(resourcePrimKey, nodeId, status);
	}

	/**
	* Finds a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByR_N_S(
		long resourcePrimKey, long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_N_S(resourcePrimKey, nodeId, status, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByR_N_S(
		long resourcePrimKey, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_N_S(resourcePrimKey, nodeId, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByR_N_S_First(
		long resourcePrimKey, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByR_N_S_First(resourcePrimKey, nodeId, status,
			orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByR_N_S_Last(
		long resourcePrimKey, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByR_N_S_Last(resourcePrimKey, nodeId, status,
			orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByR_N_S_PrevAndNext(
		long pageId, long resourcePrimKey, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByR_N_S_PrevAndNext(pageId, resourcePrimKey, nodeId,
			status, orderByComparator);
	}

	/**
	* Finds all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByU_N_S(
		long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_N_S(userId, nodeId, status);
	}

	/**
	* Finds a range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByU_N_S(
		long userId, long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_N_S(userId, nodeId, status, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByU_N_S(
		long userId, long nodeId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_N_S(userId, nodeId, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByU_N_S_First(
		long userId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByU_N_S_First(userId, nodeId, status, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByU_N_S_Last(
		long userId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByU_N_S_Last(userId, nodeId, status, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByU_N_S_PrevAndNext(
		long pageId, long userId, long nodeId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByU_N_S_PrevAndNext(pageId, userId, nodeId, status,
			orderByComparator);
	}

	/**
	* Finds the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or throws a {@link com.liferay.portlet.wiki.NoSuchPageException} if it could not be found.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param version the version to search with
	* @return the matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_V(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_V(nodeId, title, version);
	}

	/**
	* Finds the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param version the version to search with
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByN_T_V(nodeId, title, version);
	}

	/**
	* Finds the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param version the version to search with
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(
		long nodeId, java.lang.String title, double version,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByN_T_V(nodeId, title, version, retrieveFromCache);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_T_H(nodeId, title, head, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_First(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_First(nodeId, title, head, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_Last(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_Last(nodeId, title, head, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_H_PrevAndNext(
		long pageId, long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_PrevAndNext(pageId, nodeId, title, head,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_S(
		long nodeId, java.lang.String title, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T_S(nodeId, title, status);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_S(
		long nodeId, java.lang.String title, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_T_S(nodeId, title, status, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_S(
		long nodeId, java.lang.String title, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_T_S(nodeId, title, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_S_First(
		long nodeId, java.lang.String title, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_S_First(nodeId, title, status, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_S_Last(
		long nodeId, java.lang.String title, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_S_Last(nodeId, title, status, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_S_PrevAndNext(
		long pageId, long nodeId, java.lang.String title, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_S_PrevAndNext(pageId, nodeId, title, status,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H_P(nodeId, head, parentTitle);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_First(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_First(nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_Last(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_Last(nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_P_PrevAndNext(
		long pageId, long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_PrevAndNext(pageId, nodeId, head, parentTitle,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_S(
		long nodeId, boolean head, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H_S(nodeId, head, status);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_S(
		long nodeId, boolean head, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H_S(nodeId, head, status, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_S(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H_S(nodeId, head, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_S_First(
		long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_S_First(nodeId, head, status, orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_S_Last(
		long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_S_Last(nodeId, head, status, orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_S_PrevAndNext(
		long pageId, long nodeId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_S_PrevAndNext(pageId, nodeId, head, status,
			orderByComparator);
	}

	/**
	* Finds all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @return the matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P_S(
		long nodeId, boolean head, java.lang.String parentTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_H_P_S(nodeId, head, parentTitle, status);
	}

	/**
	* Finds a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P_S(
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H_P_S(nodeId, head, parentTitle, status, start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P_S(
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_H_P_S(nodeId, head, parentTitle, status, start,
			end, orderByComparator);
	}

	/**
	* Finds the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_S_First(
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_S_First(nodeId, head, parentTitle, status,
			orderByComparator);
	}

	/**
	* Finds the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_S_Last(
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_S_Last(nodeId, head, parentTitle, status,
			orderByComparator);
	}

	/**
	* Finds the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pageId the primary key of the current wiki page
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next wiki page
	* @throws com.liferay.portlet.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_P_S_PrevAndNext(
		long pageId, long nodeId, boolean head, java.lang.String parentTitle,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_S_PrevAndNext(pageId, nodeId, head,
			parentTitle, status, orderByComparator);
	}

	/**
	* Finds all the wiki pages.
	*
	* @return the wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the wiki pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @return the range of wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the wiki pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of wiki pages to return
	* @param end the upper bound of the range of wiki pages to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the wiki pages where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the wiki page where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByNodeId(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByNodeId(nodeId);
	}

	/**
	* Removes all the wiki pages where format = &#63; from the database.
	*
	* @param format the format to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFormat(java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFormat(format);
	}

	/**
	* Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_N(long resourcePrimKey, long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_N(resourcePrimKey, nodeId);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and title = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_T(nodeId, title);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_H(long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_H(nodeId, head);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and parentTitle = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_P(nodeId, parentTitle);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and redirectTitle = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_R(nodeId, redirectTitle);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_S(long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_S(nodeId, status);
	}

	/**
	* Removes the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param version the version to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_N_V(long resourcePrimKey, long nodeId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		getPersistence().removeByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_N_S(long resourcePrimKey, long nodeId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_N_S(resourcePrimKey, nodeId, status);
	}

	/**
	* Removes all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_N_S(long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_N_S(userId, nodeId, status);
	}

	/**
	* Removes the wiki page where nodeId = &#63; and title = &#63; and version = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param version the version to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_T_V(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		getPersistence().removeByN_T_V(nodeId, title, version);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_T_H(long nodeId, java.lang.String title,
		boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_T_H(nodeId, title, head);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_T_S(long nodeId, java.lang.String title,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_T_S(nodeId, title, status);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_H_P(nodeId, head, parentTitle);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_H_S(long nodeId, boolean head, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_H_S(nodeId, head, status);
	}

	/**
	* Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63; from the database.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_H_P_S(nodeId, head, parentTitle, status);
	}

	/**
	* Removes all the wiki pages from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the wiki pages where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the wiki pages where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63;.
	*
	* @param nodeId the node ID to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByNodeId(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByNodeId(nodeId);
	}

	/**
	* Counts all the wiki pages where format = &#63;.
	*
	* @param format the format to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFormat(java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFormat(format);
	}

	/**
	* Counts all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_N(long resourcePrimKey, long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_N(resourcePrimKey, nodeId);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and title = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_T(nodeId, title);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and head = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_H(long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_H(nodeId, head);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param parentTitle the parent title to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_P(nodeId, parentTitle);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param redirectTitle the redirect title to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_R(nodeId, redirectTitle);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_S(long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_S(nodeId, status);
	}

	/**
	* Counts all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param version the version to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_N_V(long resourcePrimKey, long nodeId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_N_V(resourcePrimKey, nodeId, version);
	}

	/**
	* Counts all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_N_S(long resourcePrimKey, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_N_S(resourcePrimKey, nodeId, status);
	}

	/**
	* Counts all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	*
	* @param userId the user ID to search with
	* @param nodeId the node ID to search with
	* @param status the status to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_N_S(long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_N_S(userId, nodeId, status);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and title = &#63; and version = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param version the version to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_T_V(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_T_V(nodeId, title, version);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param head the head to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_T_H(long nodeId, java.lang.String title,
		boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_T_H(nodeId, title, head);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param title the title to search with
	* @param status the status to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_T_S(long nodeId, java.lang.String title,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_T_S(nodeId, title, status);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_H_P(nodeId, head, parentTitle);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param status the status to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_H_S(long nodeId, boolean head, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_H_S(nodeId, head, status);
	}

	/**
	* Counts all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	*
	* @param nodeId the node ID to search with
	* @param head the head to search with
	* @param parentTitle the parent title to search with
	* @param status the status to search with
	* @return the number of matching wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_H_P_S(long nodeId, boolean head,
		java.lang.String parentTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_H_P_S(nodeId, head, parentTitle, status);
	}

	/**
	* Counts all the wiki pages.
	*
	* @return the number of wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static WikiPagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WikiPagePersistence)PortalBeanLocatorUtil.locate(WikiPagePersistence.class.getName());

			ReferenceRegistry.registerReference(WikiPageUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(WikiPagePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(WikiPageUtil.class, "_persistence");
	}

	private static WikiPagePersistence _persistence;
}