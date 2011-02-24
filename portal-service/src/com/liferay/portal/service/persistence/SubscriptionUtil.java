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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the subscription service. This utility wraps {@link SubscriptionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionPersistence
 * @see SubscriptionPersistenceImpl
 * @generated
 */
public class SubscriptionUtil {
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
	public static void clearCache(Subscription subscription) {
		getPersistence().clearCache(subscription);
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
	public static List<Subscription> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Subscription> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Subscription> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Subscription remove(Subscription subscription)
		throws SystemException {
		return getPersistence().remove(subscription);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Subscription update(Subscription subscription, boolean merge)
		throws SystemException {
		return getPersistence().update(subscription, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Subscription update(Subscription subscription, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(subscription, merge, serviceContext);
	}

	/**
	* Caches the subscription in the entity cache if it is enabled.
	*
	* @param subscription the subscription to cache
	*/
	public static void cacheResult(
		com.liferay.portal.model.Subscription subscription) {
		getPersistence().cacheResult(subscription);
	}

	/**
	* Caches the subscriptions in the entity cache if it is enabled.
	*
	* @param subscriptions the subscriptions to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Subscription> subscriptions) {
		getPersistence().cacheResult(subscriptions);
	}

	/**
	* Creates a new subscription with the primary key. Does not add the subscription to the database.
	*
	* @param subscriptionId the primary key for the new subscription
	* @return the new subscription
	*/
	public static com.liferay.portal.model.Subscription create(
		long subscriptionId) {
		return getPersistence().create(subscriptionId);
	}

	/**
	* Removes the subscription with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param subscriptionId the primary key of the subscription to remove
	* @return the subscription that was removed
	* @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription remove(
		long subscriptionId)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(subscriptionId);
	}

	public static com.liferay.portal.model.Subscription updateImpl(
		com.liferay.portal.model.Subscription subscription, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(subscription, merge);
	}

	/**
	* Finds the subscription with the primary key or throws a {@link com.liferay.portal.NoSuchSubscriptionException} if it could not be found.
	*
	* @param subscriptionId the primary key of the subscription to find
	* @return the subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByPrimaryKey(
		long subscriptionId)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(subscriptionId);
	}

	/**
	* Finds the subscription with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param subscriptionId the primary key of the subscription to find
	* @return the subscription, or <code>null</code> if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription fetchByPrimaryKey(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(subscriptionId);
	}

	/**
	* Finds all the subscriptions where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Finds a range of all the subscriptions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @return the range of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Finds an ordered range of all the subscriptions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Finds the first subscription in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Finds the last subscription in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Finds the subscriptions before and after the current subscription in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param subscriptionId the primary key of the current subscription
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription[] findByUserId_PrevAndNext(
		long subscriptionId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(subscriptionId, userId,
			orderByComparator);
	}

	/**
	* Finds all the subscriptions where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_C(userId, classNameId);
	}

	/**
	* Finds a range of all the subscriptions where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @return the range of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_C(userId, classNameId, start, end);
	}

	/**
	* Finds an ordered range of all the subscriptions where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_C(userId, classNameId, start, end, orderByComparator);
	}

	/**
	* Finds the first subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByU_C_First(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_C_First(userId, classNameId, orderByComparator);
	}

	/**
	* Finds the last subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByU_C_Last(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_C_Last(userId, classNameId, orderByComparator);
	}

	/**
	* Finds the subscriptions before and after the current subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param subscriptionId the primary key of the current subscription
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription[] findByU_C_PrevAndNext(
		long subscriptionId, long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_C_PrevAndNext(subscriptionId, userId, classNameId,
			orderByComparator);
	}

	/**
	* Finds all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Finds a range of all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @return the range of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end);
	}

	/**
	* Finds an ordered range of all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Finds the first subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_C_First(companyId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Finds the last subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_C_Last(companyId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Finds the subscriptions before and after the current subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param subscriptionId the primary key of the current subscription
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription[] findByC_C_C_PrevAndNext(
		long subscriptionId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_C_PrevAndNext(subscriptionId, companyId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Finds the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchSubscriptionException} if it could not be found.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching subscription
	* @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription findByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	/**
	* Finds the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching subscription, or <code>null</code> if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	/**
	* Finds the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching subscription, or <code>null</code> if a matching subscription could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Subscription fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_U_C_C(companyId, userId, classNameId, classPK,
			retrieveFromCache);
	}

	/**
	* Finds all the subscriptions.
	*
	* @return the subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the subscriptions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @return the range of subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the subscriptions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of subscriptions to return
	* @param end the upper bound of the range of subscriptions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Subscription> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the subscriptions where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the subscriptions where userId = &#63; and classNameId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_C(long userId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_C(userId, classNameId);
	}

	/**
	* Removes all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C_C(long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Removes the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchSubscriptionException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	/**
	* Removes all the subscriptions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the subscriptions where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Counts all the subscriptions where userId = &#63; and classNameId = &#63;.
	*
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_C(long userId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_C(userId, classNameId);
	}

	/**
	* Counts all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the number of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C_C(long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Counts all the subscriptions where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID to search with
	* @param userId the user ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the number of matching subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_U_C_C(companyId, userId, classNameId, classPK);
	}

	/**
	* Counts all the subscriptions.
	*
	* @return the number of subscriptions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SubscriptionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SubscriptionPersistence)PortalBeanLocatorUtil.locate(SubscriptionPersistence.class.getName());

			ReferenceRegistry.registerReference(SubscriptionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(SubscriptionPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(SubscriptionUtil.class,
			"_persistence");
	}

	private static SubscriptionPersistence _persistence;
}