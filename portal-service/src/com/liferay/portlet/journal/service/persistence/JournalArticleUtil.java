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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.journal.model.JournalArticle;

import java.util.List;

/**
 * The persistence utility for the journal article service. This utility wraps {@link JournalArticlePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticlePersistence
 * @see JournalArticlePersistenceImpl
 * @generated
 */
public class JournalArticleUtil {
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
	public static void clearCache(JournalArticle journalArticle) {
		getPersistence().clearCache(journalArticle);
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
	public static List<JournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static JournalArticle remove(JournalArticle journalArticle)
		throws SystemException {
		return getPersistence().remove(journalArticle);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalArticle update(JournalArticle journalArticle,
		boolean merge) throws SystemException {
		return getPersistence().update(journalArticle, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static JournalArticle update(JournalArticle journalArticle,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(journalArticle, merge, serviceContext);
	}

	/**
	* Caches the journal article in the entity cache if it is enabled.
	*
	* @param journalArticle the journal article to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalArticle journalArticle) {
		getPersistence().cacheResult(journalArticle);
	}

	/**
	* Caches the journal articles in the entity cache if it is enabled.
	*
	* @param journalArticles the journal articles to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalArticle> journalArticles) {
		getPersistence().cacheResult(journalArticles);
	}

	/**
	* Creates a new journal article with the primary key. Does not add the journal article to the database.
	*
	* @param id the primary key for the new journal article
	* @return the new journal article
	*/
	public static com.liferay.portlet.journal.model.JournalArticle create(
		long id) {
		return getPersistence().create(id);
	}

	/**
	* Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal article to remove
	* @return the journal article that was removed
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle remove(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().remove(id);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateImpl(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalArticle, merge);
	}

	/**
	* Finds the journal article with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	*
	* @param id the primary key of the journal article to find
	* @return the journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByPrimaryKey(id);
	}

	/**
	* Finds the journal article with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the journal article to find
	* @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(id);
	}

	/**
	* Finds all the journal articles where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the journal articles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByUuid_PrevAndNext(id, uuid, orderByComparator);
	}

	/**
	* Finds the journal article where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Finds the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Finds the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Finds all the journal articles where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByResourcePrimKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Finds a range of all the journal articles where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByResourcePrimKey_First(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByResourcePrimKey_Last(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param resourcePrimKey the resource prim key to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByResourcePrimKey_PrevAndNext(
		long id, long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByResourcePrimKey_PrevAndNext(id, resourcePrimKey,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(id, groupId, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds all the journal articles where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Finds a range of all the journal articles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(id, companyId, orderByComparator);
	}

	/**
	* Finds all the journal articles where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	/**
	* Finds a range of all the journal articles where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param smallImageId the small image ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySmallImageId(smallImageId, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param smallImageId the small image ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBySmallImageId(smallImageId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param smallImageId the small image ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findBySmallImageId_First(
		long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findBySmallImageId_First(smallImageId, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param smallImageId the small image ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findBySmallImageId_Last(
		long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findBySmallImageId_Last(smallImageId, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param smallImageId the small image ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findBySmallImageId_PrevAndNext(
		long id, long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findBySmallImageId_PrevAndNext(id, smallImageId,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_ST(
		long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_ST(resourcePrimKey, status);
	}

	/**
	* Finds a range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_ST(resourcePrimKey, status, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_ST(resourcePrimKey, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByR_ST_First(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByR_ST_First(resourcePrimKey, status, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByR_ST_Last(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByR_ST_Last(resourcePrimKey, status, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByR_ST_PrevAndNext(
		long id, long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByR_ST_PrevAndNext(id, resourcePrimKey, status,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A(groupId, articleId);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A(groupId, articleId, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_A(groupId, articleId, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_First(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_First(groupId, articleId, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_Last(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_Last(groupId, articleId, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_A_PrevAndNext(
		long id, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_PrevAndNext(id, groupId, articleId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_A(groupId, articleId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_A(groupId, articleId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_A(groupId, articleId, start, end,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, structureId);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, structureId, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S(groupId, structureId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_S_First(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_S_First(groupId, structureId, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_S_Last(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_S_Last(groupId, structureId, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_S_PrevAndNext(id, groupId, structureId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_S(groupId, structureId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_S(groupId, structureId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_S(groupId, structureId, start, end,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, templateId);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, templateId, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_T(groupId, templateId, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63; and templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_T_First(
		long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_T_First(groupId, templateId, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63; and templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_T_Last(
		long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_T_Last(groupId, templateId, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_T_PrevAndNext(
		long id, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_T_PrevAndNext(id, groupId, templateId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_T(groupId, templateId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_T(
		long groupId, java.lang.String templateId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_T(groupId, templateId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_T(
		long groupId, java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_T(groupId, templateId, start, end,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_UT(groupId, urlTitle);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_UT(groupId, urlTitle, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_UT(groupId, urlTitle, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_First(
		long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_First(groupId, urlTitle, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_Last(
		long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_Last(groupId, urlTitle, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_UT_PrevAndNext(
		long id, long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_PrevAndNext(id, groupId, urlTitle,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_UT(groupId, urlTitle);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_UT(groupId, urlTitle, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_UT(groupId, urlTitle, start, end,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_ST(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_ST(groupId, status);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_ST(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_ST(groupId, status, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_ST(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_ST(groupId, status, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_ST_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_ST_First(groupId, status, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_ST_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_ST_Last(groupId, status, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_ST_PrevAndNext(
		long id, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_ST_PrevAndNext(id, groupId, status,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_ST(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_ST(groupId, status);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_ST(groupId, status, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_ST(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_ST(
		long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_ST(companyId, status);
	}

	/**
	* Finds a range of all the journal articles where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_ST(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_ST(companyId, status, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_ST(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_ST(companyId, status, start, end, orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByC_ST_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByC_ST_First(companyId, status, orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByC_ST_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByC_ST_Last(companyId, status, orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByC_ST_PrevAndNext(
		long id, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByC_ST_PrevAndNext(id, companyId, status,
			orderByComparator);
	}

	/**
	* Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @return the matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_V(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_A_V(groupId, articleId, version);
	}

	/**
	* Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle fetchByG_A_V(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_A_V(groupId, articleId, version);
	}

	/**
	* Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle fetchByG_A_V(
		long groupId, java.lang.String articleId, double version,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_A_V(groupId, articleId, version, retrieveFromCache);
	}

	/**
	* Finds all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_ST(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A_ST(groupId, articleId, status);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_A_ST(groupId, articleId, status, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_A_ST(groupId, articleId, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_ST_First(
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_ST_First(groupId, articleId, status,
			orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_ST_Last(
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_ST_Last(groupId, articleId, status,
			orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_A_ST_PrevAndNext(
		long id, long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_ST_PrevAndNext(id, groupId, articleId, status,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A_ST(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_A_ST(groupId, articleId, status);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_A_ST(groupId, articleId, status, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_A_ST(groupId, articleId, status, start, end,
			orderByComparator);
	}

	/**
	* Finds all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	* Finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_UT_ST(groupId, urlTitle, status, start, end);
	}

	/**
	* Finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_UT_ST(groupId, urlTitle, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_ST_First(
		long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_ST_First(groupId, urlTitle, status,
			orderByComparator);
	}

	/**
	* Finds the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_ST_Last(
		long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_ST_Last(groupId, urlTitle, status,
			orderByComparator);
	}

	/**
	* Finds the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_UT_ST_PrevAndNext(
		long id, long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_ST_PrevAndNext(id, groupId, urlTitle, status,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	* Filters by the user's permissions and finds a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_UT_ST(groupId, urlTitle, status, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_UT_ST(groupId, urlTitle, status, start, end,
			orderByComparator);
	}

	/**
	* Finds all the journal articles.
	*
	* @return the journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @return the range of journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal articles to return
	* @param end the upper bound of the range of journal articles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the journal articles where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the journal article where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByResourcePrimKey(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Removes all the journal articles where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the journal articles where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the journal articles where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBySmallImageId(smallImageId);
	}

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; and status = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_ST(long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_ST(resourcePrimKey, status);
	}

	/**
	* Removes all the journal articles where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_A(groupId, articleId);
	}

	/**
	* Removes all the journal articles where groupId = &#63; and structureId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_S(groupId, structureId);
	}

	/**
	* Removes all the journal articles where groupId = &#63; and templateId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_T(groupId, templateId);
	}

	/**
	* Removes all the journal articles where groupId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_UT(groupId, urlTitle);
	}

	/**
	* Removes all the journal articles where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_ST(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_ST(groupId, status);
	}

	/**
	* Removes all the journal articles where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_ST(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_ST(companyId, status);
	}

	/**
	* Removes the journal article where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_A_V(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		getPersistence().removeByG_A_V(groupId, articleId, version);
	}

	/**
	* Removes all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_A_ST(long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_A_ST(groupId, articleId, status);
	}

	/**
	* Removes all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	* Removes all the journal articles from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the journal articles where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the journal articles where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Counts all the journal articles where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByResourcePrimKey(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Counts all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Counts all the journal articles where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Counts all the journal articles where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	/**
	* Counts all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_ST(long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_ST(resourcePrimKey, status);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_A(groupId, articleId);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_A(groupId, articleId);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, structureId);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_S(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_S(groupId, structureId);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_T(groupId, templateId);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_T(groupId, templateId);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_UT(groupId, urlTitle);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_UT(groupId, urlTitle);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_ST(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_ST(groupId, status);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_ST(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_ST(groupId, status);
	}

	/**
	* Counts all the journal articles where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_ST(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_ST(companyId, status);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_A_V(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_A_V(groupId, articleId, version);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_A_ST(long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_A_ST(groupId, articleId, status);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_A_ST(long groupId,
		java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_A_ST(groupId, articleId, status);
	}

	/**
	* Counts all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	* Counts all the journal articles.
	*
	* @return the number of journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalArticlePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalArticlePersistence)PortalBeanLocatorUtil.locate(JournalArticlePersistence.class.getName());

			ReferenceRegistry.registerReference(JournalArticleUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(JournalArticlePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(JournalArticleUtil.class,
			"_persistence");
	}

	private static JournalArticlePersistence _persistence;
}