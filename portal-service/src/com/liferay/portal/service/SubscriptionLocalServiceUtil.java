/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service;

public class SubscriptionLocalServiceUtil {
	public static com.liferay.portal.model.Subscription addSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.SystemException {
		return getService().addSubscription(subscription);
	}

	public static com.liferay.portal.model.Subscription createSubscription(
		long subscriptionId) {
		return getService().createSubscription(subscriptionId);
	}

	public static void deleteSubscription(long subscriptionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteSubscription(subscriptionId);
	}

	public static void deleteSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.SystemException {
		getService().deleteSubscription(subscription);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Subscription getSubscription(
		long subscriptionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getSubscription(subscriptionId);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getSubscriptions(start, end);
	}

	public static int getSubscriptionsCount()
		throws com.liferay.portal.SystemException {
		return getService().getSubscriptionsCount();
	}

	public static com.liferay.portal.model.Subscription updateSubscription(
		com.liferay.portal.model.Subscription subscription)
		throws com.liferay.portal.SystemException {
		return getService().updateSubscription(subscription);
	}

	public static com.liferay.portal.model.Subscription updateSubscription(
		com.liferay.portal.model.Subscription subscription, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateSubscription(subscription, merge);
	}

	public static com.liferay.portal.model.Subscription addSubscription(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addSubscription(userId, className, classPK);
	}

	public static com.liferay.portal.model.Subscription addSubscription(
		long userId, java.lang.String className, long classPK,
		java.lang.String frequency)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addSubscription(userId, className, classPK, frequency);
	}

	public static void deleteSubscription(long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteSubscription(userId, className, classPK);
	}

	public static void deleteSubscriptions(long userId)
		throws com.liferay.portal.SystemException {
		getService().deleteSubscriptions(userId);
	}

	public static void deleteSubscriptions(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		getService().deleteSubscriptions(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Subscription getSubscription(
		long companyId, long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getSubscription(companyId, userId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> getSubscriptions(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getSubscriptions(companyId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Subscription> getUserSubscriptions(
		long userId, java.lang.String className)
		throws com.liferay.portal.SystemException {
		return getService().getUserSubscriptions(userId, className);
	}

	public static boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().isSubscribed(companyId, userId, className, classPK);
	}

	public static SubscriptionLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("SubscriptionLocalService is not set");
		}

		return _service;
	}

	public void setService(SubscriptionLocalService service) {
		_service = service;
	}

	private static SubscriptionLocalService _service;
}