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

import com.liferay.portlet.journal.model.JournalContentSearch;

import java.util.List;

/**
 * <a href="JournalContentSearchUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearchPersistence
 * @see       JournalContentSearchPersistenceImpl
 * @generated
 */
public class JournalContentSearchUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(JournalContentSearch)
	 */
	public static void clearCache(JournalContentSearch journalContentSearch) {
		getPersistence().clearCache(journalContentSearch);
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
	public static List<JournalContentSearch> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalContentSearch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static JournalContentSearch remove(
		JournalContentSearch journalContentSearch) throws SystemException {
		return getPersistence().remove(journalContentSearch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalContentSearch update(
		JournalContentSearch journalContentSearch, boolean merge)
		throws SystemException {
		return getPersistence().update(journalContentSearch, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch) {
		getPersistence().cacheResult(journalContentSearch);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> journalContentSearchs) {
		getPersistence().cacheResult(journalContentSearchs);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch create(
		long contentSearchId) {
		return getPersistence().create(contentSearchId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch remove(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().remove(contentSearchId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateImpl(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalContentSearch, merge);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByPrimaryKey(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByPrimaryKey(contentSearchId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch fetchByPrimaryKey(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(contentSearchId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByArticleId(articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByArticleId(articleId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByArticleId(articleId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByArticleId_First(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByArticleId_First(articleId, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByArticleId_Last(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByArticleId_Last(articleId, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByArticleId_PrevAndNext(
		long contentSearchId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByArticleId_PrevAndNext(contentSearchId, articleId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_First(groupId, privateLayout, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_Last(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_PrevAndNext(contentSearchId, groupId,
			privateLayout, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A(groupId, articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A(groupId, articleId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_A(groupId, articleId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_A_First(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_A_First(groupId, articleId, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_A_Last(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_A_Last(groupId, articleId, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByG_A_PrevAndNext(
		long contentSearchId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_A_PrevAndNext(contentSearchId, groupId, articleId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_L(groupId, privateLayout, layoutId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_L(groupId, privateLayout, layoutId, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_First(groupId, privateLayout, layoutId,
			orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_Last(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_Last(groupId, privateLayout, layoutId,
			orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_PrevAndNext(contentSearchId, groupId,
			privateLayout, layoutId, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_A(groupId, privateLayout, articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_A(groupId, privateLayout, articleId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_A(groupId, privateLayout, articleId, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_A_First(
		long groupId, boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_A_First(groupId, privateLayout, articleId,
			orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_A_Last(
		long groupId, boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_A_Last(groupId, privateLayout, articleId,
			orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_A_PrevAndNext(contentSearchId, groupId,
			privateLayout, articleId, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_L_P(groupId, privateLayout, layoutId, portletId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_First(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_First(groupId, privateLayout, layoutId,
			portletId, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_Last(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_Last(groupId, privateLayout, layoutId,
			portletId, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_PrevAndNext(contentSearchId, groupId,
			privateLayout, layoutId, portletId, orderByComparator);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByArticleId(articleId);
	}

	public static void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	public static void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_A(groupId, articleId);
	}

	public static void removeByG_P_L(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	public static void removeByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_A(groupId, privateLayout, articleId);
	}

	public static void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByG_P_L_P(groupId, privateLayout, layoutId, portletId);
	}

	public static void removeByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException {
		getPersistence()
			.removeByG_P_L_P_A(groupId, privateLayout, layoutId, portletId,
			articleId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByArticleId(articleId);
	}

	public static int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	public static int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_A(groupId, articleId);
	}

	public static int countByG_P_L(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	public static int countByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_A(groupId, privateLayout, articleId);
	}

	public static int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_P_L_P(groupId, privateLayout, layoutId, portletId);
	}

	public static int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalContentSearchPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalContentSearchPersistence)PortalBeanLocatorUtil.locate(JournalContentSearchPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(JournalContentSearchPersistence persistence) {
		_persistence = persistence;
	}

	private static JournalContentSearchPersistence _persistence;
}