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

package com.liferay.portal.service;


/**
 * <a href="SubscriptionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SubscriptionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SubscriptionLocalService
 * @generated
 */
public class SubscriptionLocalServiceWrapper implements SubscriptionLocalService {
	public SubscriptionLocalServiceWrapper(
		SubscriptionLocalService subscriptionLocalService) {
		_subscriptionLocalService = subscriptionLocalService;
	}

	public com.liferay.portal.model.Subscription addSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.addSubscription(subscription);
	}

	public com.liferay.portal.model.Subscription createSubscription(
		long subscriptionId) {
		return _subscriptionLocalService.createSubscription(subscriptionId);
	}

	public void deleteSubscription(long subscriptionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_subscriptionLocalService.deleteSubscription(subscriptionId);
	}

	public void deleteSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.SystemException {
		_subscriptionLocalService.deleteSubscription(subscription);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Subscription getSubscription(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.getSubscription(subscriptionId);
	}

	public java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.getSubscriptions(start, end);
	}

	public int getSubscriptionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.getSubscriptionsCount();
	}

	public com.liferay.portal.model.Subscription updateSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.updateSubscription(subscription);
	}

	public com.liferay.portal.model.Subscription updateSubscription(
		com.liferay.portal.model.Subscription subscription, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.updateSubscription(subscription, merge);
	}

	public com.liferay.portal.model.Subscription addSubscription(long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.addSubscription(userId, className,
			classPK);
	}

	public com.liferay.portal.model.Subscription addSubscription(long userId,
		java.lang.String className, long classPK, java.lang.String frequency)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.addSubscription(userId, className,
			classPK, frequency);
	}

	public void deleteSubscription(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_subscriptionLocalService.deleteSubscription(userId, className, classPK);
	}

	public void deleteSubscriptions(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_subscriptionLocalService.deleteSubscriptions(userId);
	}

	public void deleteSubscriptions(long companyId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_subscriptionLocalService.deleteSubscriptions(companyId, className,
			classPK);
	}

	public com.liferay.portal.model.Subscription getSubscription(
		long companyId, long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.getSubscription(companyId, userId,
			className, classPK);
	}

	public java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.getSubscriptions(companyId, className,
			classPK);
	}

	public java.util.List<com.liferay.portal.model.Subscription> getUserSubscriptions(
		long userId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.getUserSubscriptions(userId, className);
	}

	public boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscriptionLocalService.isSubscribed(companyId, userId,
			className, classPK);
	}

	public SubscriptionLocalService getWrappedSubscriptionLocalService() {
		return _subscriptionLocalService;
	}

	private SubscriptionLocalService _subscriptionLocalService;
}