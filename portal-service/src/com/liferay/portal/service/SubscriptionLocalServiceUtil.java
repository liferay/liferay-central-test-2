/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

/**
 * <a href="SubscriptionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.SubscriptionLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.SubscriptionLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.SubscriptionLocalService
 * @see com.liferay.portal.service.SubscriptionLocalServiceFactory
 *
 */
public class SubscriptionLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

		return subscriptionLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

		return subscriptionLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portal.model.Subscription addSubscription(
		long userId, java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

		return subscriptionLocalService.addSubscription(userId, className,
			classPK);
	}

	public static com.liferay.portal.model.Subscription addSubscription(
		long userId, java.lang.String className, java.lang.String classPK,
		java.lang.String frequency)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

		return subscriptionLocalService.addSubscription(userId, className,
			classPK, frequency);
	}

	public static void deleteSubscription(long userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();
		subscriptionLocalService.deleteSubscription(userId, className, classPK);
	}

	public static void deleteSubscriptions(long userId)
		throws com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();
		subscriptionLocalService.deleteSubscriptions(userId);
	}

	public static void deleteSubscriptions(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();
		subscriptionLocalService.deleteSubscriptions(companyId, className,
			classPK);
	}

	public static com.liferay.portal.model.Subscription getSubscription(
		java.lang.String companyId, long userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

		return subscriptionLocalService.getSubscription(companyId, userId,
			className, classPK);
	}

	public static java.util.List getSubscriptions(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

		return subscriptionLocalService.getSubscriptions(companyId, className,
			classPK);
	}

	public static boolean isSubscribed(java.lang.String companyId, long userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

		return subscriptionLocalService.isSubscribed(companyId, userId,
			className, classPK);
	}
}