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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.SubscriptionConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.SubscriptionLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Charles May
 * @author Zsolt Berentey
 */
public class SubscriptionLocalServiceImpl
	extends SubscriptionLocalServiceBaseImpl {

	@Deprecated
	public Subscription addSubscription(
			long userId, String className, long classPK)
		throws PortalException, SystemException {

		return addSubscription(
			userId, -1, className, classPK,
			SubscriptionConstants.FREQUENCY_INSTANT);
	}

	@Deprecated
	public Subscription addSubscription(
			long userId, String className, long classPK, String frequency)
		throws PortalException, SystemException {

		return addSubscription(userId, -1, className, classPK, frequency);
	}

	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		return addSubscription(
			userId, groupId, className, classPK,
			SubscriptionConstants.FREQUENCY_INSTANT);
	}

	public Subscription addSubscription(
			long userId, long groupId, String className, long classPK,
			String frequency)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long subscriptionId = counterLocalService.increment();

		Subscription subscription = subscriptionPersistence.fetchByC_U_C_C(
			user.getCompanyId(), userId, classNameId, classPK);

		if (subscription == null) {
			subscription = subscriptionPersistence.create(subscriptionId);

			subscription.setCompanyId(user.getCompanyId());
			subscription.setUserId(user.getUserId());
			subscription.setUserName(user.getFullName());
			subscription.setCreateDate(now);
			subscription.setModifiedDate(now);
			subscription.setClassNameId(classNameId);
			subscription.setClassPK(classPK);
			subscription.setFrequency(frequency);

			subscriptionPersistence.update(subscription, false);
		}

		// social - we need an asset for social equity

		if (groupId > -1) {
			try {
				assetEntryLocalService.getEntry(className, classPK);
			}
			catch (Exception e) {
				assetEntryLocalService.updateEntry(
					userId, groupId, className, classPK, null,
					null, null, false, null, null, null, null, null,
					String.valueOf(groupId), null, null, null, 0, 0, null,
					false);
			}

			socialEquityLogLocalService.addEquityLogs(
				userId, className, classPK, ActionKeys.SUBSCRIBE);
		}

		return subscription;
	}

	public void deleteSubscription(long subscriptionId)
		throws PortalException, SystemException {

		subscriptionPersistence.remove(subscriptionId);
	}

	public void deleteSubscription(
			long userId, String className, long classPK)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		subscriptionPersistence.removeByC_U_C_C(
			user.getCompanyId(), userId, classNameId, classPK);
	}

	public void deleteSubscriptions(long userId) throws SystemException {
		subscriptionPersistence.removeByUserId(userId);
	}

	public void deleteSubscriptions(
			long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		subscriptionPersistence.removeByC_C_C(companyId, classNameId, classPK);
	}

	public Subscription getSubscription(
			long companyId, long userId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return subscriptionPersistence.findByC_U_C_C(
			companyId, userId, classNameId, classPK);
	}

	public List<Subscription> getSubscriptions(
			long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return subscriptionPersistence.findByC_C_C(
			companyId, classNameId, classPK);
	}

	public List<Subscription> getUserSubscriptions(
			long userId, String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return subscriptionPersistence.findByU_C(userId, classNameId);
	}

	public boolean isSubscribed(
			long companyId, long userId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		Subscription subscription = subscriptionPersistence.fetchByC_U_C_C(
			companyId, userId, classNameId, classPK);

		if (subscription != null) {
			return true;
		}
		else {
			return false;
		}
	}

}