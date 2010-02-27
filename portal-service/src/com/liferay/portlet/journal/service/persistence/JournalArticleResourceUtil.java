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

import com.liferay.portlet.journal.model.JournalArticleResource;

import java.util.List;

/**
 * <a href="JournalArticleResourceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResourcePersistence
 * @see       JournalArticleResourcePersistenceImpl
 * @generated
 */
public class JournalArticleResourceUtil {
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
	public static JournalArticleResource remove(
		JournalArticleResource journalArticleResource)
		throws SystemException {
		return getPersistence().remove(journalArticleResource);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalArticleResource update(
		JournalArticleResource journalArticleResource, boolean merge)
		throws SystemException {
		return getPersistence().update(journalArticleResource, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource) {
		getPersistence().cacheResult(journalArticleResource);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> journalArticleResources) {
		getPersistence().cacheResult(journalArticleResources);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource create(
		long resourcePrimKey) {
		return getPersistence().create(resourcePrimKey);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource remove(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException {
		return getPersistence().remove(resourcePrimKey);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource updateImpl(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalArticleResource, merge);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource findByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException {
		return getPersistence().findByPrimaryKey(resourcePrimKey);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource fetchByPrimaryKey(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(resourcePrimKey);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource[] findByGroupId_PrevAndNext(
		long resourcePrimKey, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(resourcePrimKey, groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException {
		return getPersistence().findByG_A(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource fetchByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_A(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource fetchByG_A(
		long groupId, java.lang.String articleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_A(groupId, articleId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleResourceException {
		getPersistence().removeByG_A(groupId, articleId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_A(groupId, articleId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalArticleResourcePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalArticleResourcePersistence)PortalBeanLocatorUtil.locate(JournalArticleResourcePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(JournalArticleResourcePersistence persistence) {
		_persistence = persistence;
	}

	private static JournalArticleResourcePersistence _persistence;
}