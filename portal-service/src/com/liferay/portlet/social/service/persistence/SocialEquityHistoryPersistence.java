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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialEquityHistory;

/**
 * The persistence interface for the social equity history service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityHistoryPersistenceImpl
 * @see SocialEquityHistoryUtil
 * @generated
 */
public interface SocialEquityHistoryPersistence extends BasePersistence<SocialEquityHistory> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialEquityHistoryUtil} to access the social equity history persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	public SocialEquityHistory remove(SocialEquityHistory socialEquityHistory)
		throws SystemException;

	/**
	* Caches the social equity history in the entity cache if it is enabled.
	*
	* @param socialEquityHistory the social equity history to cache
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory);

	/**
	* Caches the social equity histories in the entity cache if it is enabled.
	*
	* @param socialEquityHistories the social equity histories to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> socialEquityHistories);

	/**
	* Creates a new social equity history with the primary key. Does not add the social equity history to the database.
	*
	* @param equityHistoryId the primary key for the new social equity history
	* @return the new social equity history
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory create(
		long equityHistoryId);

	/**
	* Removes the social equity history with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityHistoryId the primary key of the social equity history to remove
	* @return the social equity history that was removed
	* @throws com.liferay.portlet.social.NoSuchEquityHistoryException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory remove(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException;

	public com.liferay.portlet.social.model.SocialEquityHistory updateImpl(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the social equity history with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityHistoryException} if it could not be found.
	*
	* @param equityHistoryId the primary key of the social equity history to find
	* @return the social equity history
	* @throws com.liferay.portlet.social.NoSuchEquityHistoryException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory findByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException;

	/**
	* Finds the social equity history with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equityHistoryId the primary key of the social equity history to find
	* @return the social equity history, or <code>null</code> if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityHistory fetchByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the social equity histories.
	*
	* @return the social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the social equity histories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity histories to return
	* @param end the upper bound of the range of social equity histories to return (not inclusive)
	* @return the range of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the social equity histories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity histories to return
	* @param end the upper bound of the range of social equity histories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social equity histories from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the social equity histories.
	*
	* @return the number of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}