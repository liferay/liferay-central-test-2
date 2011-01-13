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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalArticle;

/**
 * The persistence interface for the journal article service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticlePersistenceImpl
 * @see JournalArticleUtil
 * @generated
 */
public interface JournalArticlePersistence extends BasePersistence<JournalArticle> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalArticleUtil} to access the journal article persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	public JournalArticle remove(JournalArticle journalArticle)
		throws SystemException;

	/**
	* Caches the journal article in the entity cache if it is enabled.
	*
	* @param journalArticle the journal article to cache
	*/
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalArticle journalArticle);

	/**
	* Caches the journal articles in the entity cache if it is enabled.
	*
	* @param journalArticles the journal articles to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalArticle> journalArticles);

	/**
	* Creates a new journal article with the primary key. Does not add the journal article to the database.
	*
	* @param id the primary key for the new journal article
	* @return the new journal article
	*/
	public com.liferay.portlet.journal.model.JournalArticle create(long id);

	/**
	* Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal article to remove
	* @return the journal article that was removed
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle remove(long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle updateImpl(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the journal article with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	*
	* @param id the primary key of the journal article to find
	* @return the journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds the journal article with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the journal article to find
	* @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds the journal article where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching journal article
	* @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByResourcePrimKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByResourcePrimKey_First(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByResourcePrimKey_Last(
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByResourcePrimKey_PrevAndNext(
		long id, long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds all the journal articles where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findBySmallImageId_First(
		long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findBySmallImageId_Last(
		long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findBySmallImageId_PrevAndNext(
		long id, long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_ST(
		long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByR_ST_First(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByR_ST_Last(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByR_ST_PrevAndNext(
		long id, long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_A_First(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_A_Last(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByG_A_PrevAndNext(
		long id, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_S_First(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_S_Last(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_T_First(
		long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_T_Last(
		long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByG_T_PrevAndNext(
		long id, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_T(
		long groupId, java.lang.String templateId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_T(
		long groupId, java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_UT_First(
		long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_UT_Last(
		long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByG_UT_PrevAndNext(
		long id, long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_ST(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_ST(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_ST(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_ST_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_ST_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByG_ST_PrevAndNext(
		long id, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_ST(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_ST(
		long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_ST(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_ST(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByC_ST_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByC_ST_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByC_ST_PrevAndNext(
		long id, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_A_V(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle fetchByG_A_V(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticle fetchByG_A_V(
		long groupId, java.lang.String articleId, double version,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_ST(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_A_ST_First(
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_A_ST_Last(
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByG_A_ST_PrevAndNext(
		long id, long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A_ST(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_A_ST(
		long groupId, java.lang.String articleId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_UT_ST_First(
		long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle findByG_UT_ST_Last(
		long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

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
	public com.liferay.portlet.journal.model.JournalArticle[] findByG_UT_ST_PrevAndNext(
		long id, long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Filters by the user's permissions and finds all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> filterFindByG_UT_ST(
		long groupId, java.lang.String urlTitle, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the journal articles.
	*
	* @return the journal articles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the journal article where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByResourcePrimKey(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; and status = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByR_ST(long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where groupId = &#63; and structureId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where groupId = &#63; and templateId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where groupId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_ST(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_ST(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the journal article where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_A_V(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_A_ST(long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal articles from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByResourcePrimKey(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByR_ST(long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param structureId the structure ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param templateId the template ID to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_ST(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_ST(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_ST(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param version the version to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_A_V(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_A_ST(long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param articleId the article ID to search with
	* @param status the status to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_A_ST(long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the number of matching journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID to search with
	* @param urlTitle the url title to search with
	* @param status the status to search with
	* @return the number of matching journal articles that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the journal articles.
	*
	* @return the number of journal articles
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}