/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.wiki.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.wiki.model.WikiNode;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the wiki node service. This utility wraps {@link com.liferay.wiki.service.persistence.impl.WikiNodePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodePersistence
 * @see com.liferay.wiki.service.persistence.impl.WikiNodePersistenceImpl
 * @generated
 */
@ProviderType
public class WikiNodeUtil {
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
	public static void clearCache(WikiNode wikiNode) {
		getPersistence().clearCache(wikiNode);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WikiNode> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WikiNode> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WikiNode> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WikiNode> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static WikiNode update(WikiNode wikiNode) {
		return getPersistence().update(wikiNode);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static WikiNode update(WikiNode wikiNode,
		ServiceContext serviceContext) {
		return getPersistence().update(wikiNode, serviceContext);
	}

	/**
	* Returns all the wiki nodes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the wiki nodes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where uuid = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] findByUuid_PrevAndNext(
		long nodeId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByUuid_PrevAndNext(nodeId, uuid, orderByComparator);
	}

	/**
	* Removes all the wiki nodes where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of wiki nodes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wiki nodes
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the wiki node where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.wiki.NoSuchNodeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the wiki node where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the wiki node where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the wiki node where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the wiki node that was removed
	*/
	public static com.liferay.wiki.model.WikiNode removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of wiki nodes where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching wiki nodes
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the wiki nodes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the wiki nodes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] findByUuid_C_PrevAndNext(
		long nodeId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(nodeId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the wiki nodes where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of wiki nodes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching wiki nodes
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the wiki nodes where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the wiki nodes where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where groupId = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] findByGroupId_PrevAndNext(
		long nodeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(nodeId, groupId, orderByComparator);
	}

	/**
	* Returns all the wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching wiki nodes that the user has permission to view
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> filterFindByGroupId(
		long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes that the user has permission to view
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes that the user has permission to view
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set of wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] filterFindByGroupId_PrevAndNext(
		long nodeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(nodeId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the wiki nodes where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of wiki nodes where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching wiki nodes
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of wiki nodes that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching wiki nodes that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the wiki nodes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the wiki nodes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where companyId = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] findByCompanyId_PrevAndNext(
		long nodeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(nodeId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the wiki nodes where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of wiki nodes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching wiki nodes
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the wiki node where groupId = &#63; and name = &#63; or throws a {@link com.liferay.wiki.NoSuchNodeException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the wiki node where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the wiki node where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the wiki node where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the wiki node that was removed
	*/
	public static com.liferay.wiki.model.WikiNode removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of wiki nodes where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching wiki nodes
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the wiki nodes where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByG_S(
		long groupId, int status) {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	* Returns a range of all the wiki nodes where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByG_S(
		long groupId, int status, int start, int end) {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByG_S_Last(long groupId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] findByG_S_PrevAndNext(
		long nodeId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByG_S_PrevAndNext(nodeId, groupId, status,
			orderByComparator);
	}

	/**
	* Returns all the wiki nodes that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching wiki nodes that the user has permission to view
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> filterFindByG_S(
		long groupId, int status) {
		return getPersistence().filterFindByG_S(groupId, status);
	}

	/**
	* Returns a range of all the wiki nodes that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes that the user has permission to view
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> filterFindByG_S(
		long groupId, int status, int start, int end) {
		return getPersistence().filterFindByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes that the user has permission to view
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> filterFindByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .filterFindByG_S(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set of wiki nodes that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] filterFindByG_S_PrevAndNext(
		long nodeId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .filterFindByG_S_PrevAndNext(nodeId, groupId, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki nodes where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public static void removeByG_S(long groupId, int status) {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	* Returns the number of wiki nodes where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching wiki nodes
	*/
	public static int countByG_S(long groupId, int status) {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	* Returns the number of wiki nodes that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching wiki nodes that the user has permission to view
	*/
	public static int filterCountByG_S(long groupId, int status) {
		return getPersistence().filterCountByG_S(groupId, status);
	}

	/**
	* Returns all the wiki nodes where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByC_S(
		long companyId, int status) {
		return getPersistence().findByC_S(companyId, status);
	}

	/**
	* Returns a range of all the wiki nodes where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByC_S(
		long companyId, int status, int start, int end) {
		return getPersistence().findByC_S(companyId, status, start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .findByC_S(companyId, status, start, end, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first wiki node in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last wiki node in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wiki node, or <code>null</code> if a matching wiki node could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the wiki nodes before and after the current wiki node in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param nodeId the primary key of the current wiki node
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode[] findByC_S_PrevAndNext(
		long nodeId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence()
				   .findByC_S_PrevAndNext(nodeId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the wiki nodes where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_S(long companyId, int status) {
		getPersistence().removeByC_S(companyId, status);
	}

	/**
	* Returns the number of wiki nodes where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching wiki nodes
	*/
	public static int countByC_S(long companyId, int status) {
		return getPersistence().countByC_S(companyId, status);
	}

	/**
	* Caches the wiki node in the entity cache if it is enabled.
	*
	* @param wikiNode the wiki node
	*/
	public static void cacheResult(com.liferay.wiki.model.WikiNode wikiNode) {
		getPersistence().cacheResult(wikiNode);
	}

	/**
	* Caches the wiki nodes in the entity cache if it is enabled.
	*
	* @param wikiNodes the wiki nodes
	*/
	public static void cacheResult(
		java.util.List<com.liferay.wiki.model.WikiNode> wikiNodes) {
		getPersistence().cacheResult(wikiNodes);
	}

	/**
	* Creates a new wiki node with the primary key. Does not add the wiki node to the database.
	*
	* @param nodeId the primary key for the new wiki node
	* @return the new wiki node
	*/
	public static com.liferay.wiki.model.WikiNode create(long nodeId) {
		return getPersistence().create(nodeId);
	}

	/**
	* Removes the wiki node with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node that was removed
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode remove(long nodeId)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().remove(nodeId);
	}

	public static com.liferay.wiki.model.WikiNode updateImpl(
		com.liferay.wiki.model.WikiNode wikiNode) {
		return getPersistence().updateImpl(wikiNode);
	}

	/**
	* Returns the wiki node with the primary key or throws a {@link com.liferay.wiki.NoSuchNodeException} if it could not be found.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node
	* @throws com.liferay.wiki.NoSuchNodeException if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode findByPrimaryKey(long nodeId)
		throws com.liferay.wiki.exception.NoSuchNodeException {
		return getPersistence().findByPrimaryKey(nodeId);
	}

	/**
	* Returns the wiki node with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param nodeId the primary key of the wiki node
	* @return the wiki node, or <code>null</code> if a wiki node with the primary key could not be found
	*/
	public static com.liferay.wiki.model.WikiNode fetchByPrimaryKey(long nodeId) {
		return getPersistence().fetchByPrimaryKey(nodeId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.wiki.model.WikiNode> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the wiki nodes.
	*
	* @return the wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the wiki nodes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @return the range of wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the wiki nodes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki nodes
	* @param end the upper bound of the range of wiki nodes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wiki nodes
	*/
	public static java.util.List<com.liferay.wiki.model.WikiNode> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the wiki nodes from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of wiki nodes.
	*
	* @return the number of wiki nodes
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WikiNodePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(WikiNodePersistence persistence) {
	}

	private static ServiceTracker<WikiNodePersistence, WikiNodePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WikiNodeUtil.class);

		_serviceTracker = new ServiceTracker<WikiNodePersistence, WikiNodePersistence>(bundle.getBundleContext(),
				WikiNodePersistence.class, null);

		_serviceTracker.open();
	}
}