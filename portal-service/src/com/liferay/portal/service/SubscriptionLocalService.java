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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the subscription local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionLocalServiceUtil
 * @see com.liferay.portal.service.base.SubscriptionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.SubscriptionLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SubscriptionLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SubscriptionLocalServiceUtil} to access the subscription local service. Add custom service methods to {@link com.liferay.portal.service.impl.SubscriptionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the subscription to the database. Also notifies the appropriate model listeners.
	*
	* @param subscription the subscription to add
	* @return the subscription that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Subscription addSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new subscription with the primary key. Does not add the subscription to the database.
	*
	* @param subscriptionId the primary key for the new subscription
	* @return the new subscription
	*/
	public com.liferay.portal.model.Subscription createSubscription(
		long subscriptionId);

	/**
	* Deletes the subscription with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param subscriptionId the primary key of the subscription to delete
	* @throws PortalException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSubscription(long subscriptionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the subscription from the database. Also notifies the appropriate model listeners.
	*
	* @param subscription the subscription to delete
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the subscription with the primary key.
	*
	* @param subscriptionId the primary key of the subscription to get
	* @return the subscription
	* @throws PortalException if a subscription with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Subscription getSubscription(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the subscriptions.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of subscriptions.
	*
	* @return the number of subscriptions
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSubscriptionsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the subscription in the database. Also notifies the appropriate model listeners.
	*
	* @param subscription the subscription to update
	* @return the subscription that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Subscription updateSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the subscription in the database. Also notifies the appropriate model listeners.
	*
	* @param subscription the subscription to update
	* @param merge whether to merge the subscription with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the subscription that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Subscription updateSubscription(
		com.liferay.portal.model.Subscription subscription, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier();

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier);

	public com.liferay.portal.model.Subscription addSubscription(long userId,
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Subscription addSubscription(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String frequency)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteSubscription(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteSubscriptions(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteSubscriptions(long companyId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Subscription getSubscription(
		long companyId, long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Subscription> getUserSubscriptions(
		long userId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;
}