/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.spring;

/**
 * <a href="SubscriptionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SubscriptionLocalServiceUtil {
	public static com.liferay.portal.model.Subscription addSubscription(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

			return subscriptionLocalService.addSubscription(userId, className,
				classPK);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Subscription addSubscription(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String frequency)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

			return subscriptionLocalService.addSubscription(userId, className,
				classPK, frequency);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteSubscription(java.lang.String userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();
			subscriptionLocalService.deleteSubscription(userId, className,
				classPK);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteSubscriptions(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();
			subscriptionLocalService.deleteSubscriptions(userId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteSubscriptions(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();
			subscriptionLocalService.deleteSubscriptions(companyId, className,
				classPK);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.Subscription getSubscription(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

			return subscriptionLocalService.getSubscription(companyId, userId,
				className, classPK);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getSubscriptions(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

			return subscriptionLocalService.getSubscriptions(companyId,
				className, classPK);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean isSubscribed(java.lang.String companyId,
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			SubscriptionLocalService subscriptionLocalService = SubscriptionLocalServiceFactory.getService();

			return subscriptionLocalService.isSubscribed(companyId, userId,
				className, classPK);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}