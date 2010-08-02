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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialEquityHistory;

import java.util.List;

/**
 * The persistence utility for the social equity history service. This utility wraps {@link SocialEquityHistoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityHistoryPersistence
 * @see SocialEquityHistoryPersistenceImpl
 * @generated
 */
public class SocialEquityHistoryUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(SocialEquityHistory socialEquityHistory) {
		getPersistence().clearCache(socialEquityHistory);
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
	public static List<SocialEquityHistory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquityHistory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialEquityHistory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquityHistory remove(
		SocialEquityHistory socialEquityHistory) throws SystemException {
		return getPersistence().remove(socialEquityHistory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquityHistory update(
		SocialEquityHistory socialEquityHistory, boolean merge)
		throws SystemException {
		return getPersistence().update(socialEquityHistory, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SocialEquityHistory update(
		SocialEquityHistory socialEquityHistory, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(socialEquityHistory, merge, serviceContext);
	}

	/**
	* Caches the social equity history in the entity cache if it is enabled.
	*
	* @param socialEquityHistory the social equity history to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory) {
		getPersistence().cacheResult(socialEquityHistory);
	}

	/**
	* Caches the social equity histories in the entity cache if it is enabled.
	*
	* @param socialEquityHistories the social equity histories to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> socialEquityHistories) {
		getPersistence().cacheResult(socialEquityHistories);
	}

	/**
	* Creates a new social equity history with the primary key. Does not add the social equity history to the database.
	*
	* @param equityHistoryId the primary key for the new social equity history
	* @return the new social equity history
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory create(
		long equityHistoryId) {
		return getPersistence().create(equityHistoryId);
	}

	/**
	* Removes the social equity history with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityHistoryId the primary key of the social equity history to remove
	* @return the social equity history that was removed
	* @throws com.liferay.portlet.social.NoSuchEquityHistoryException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory remove(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException {
		return getPersistence().remove(equityHistoryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory updateImpl(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquityHistory, merge);
	}

	/**
	* Finds the social equity history with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityHistoryException} if it could not be found.
	*
	* @param equityHistoryId the primary key of the social equity history to find
	* @return the social equity history
	* @throws com.liferay.portlet.social.NoSuchEquityHistoryException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory findByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException {
		return getPersistence().findByPrimaryKey(equityHistoryId);
	}

	/**
	* Finds the social equity history with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equityHistoryId the primary key of the social equity history to find
	* @return the social equity history, or <code>null</code> if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory fetchByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityHistoryId);
	}

	/**
	* Finds all the social equity histories.
	*
	* @return the social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the social equity histories from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the social equity histories.
	*
	* @return the number of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityHistoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityHistoryPersistence)PortalBeanLocatorUtil.locate(SocialEquityHistoryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityHistoryPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialEquityHistoryPersistence _persistence;
}