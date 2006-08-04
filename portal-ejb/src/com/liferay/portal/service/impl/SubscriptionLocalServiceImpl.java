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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchSubscriptionException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.SubscriptionUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.SubscriptionLocalService;

import java.util.Date;
import java.util.List;

/**
 * <a href="SubscriptionLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Charles May
 *
 */
public class SubscriptionLocalServiceImpl implements SubscriptionLocalService {

	public Subscription addSubscription(
			String userId, String className, String classPK)
		throws PortalException, SystemException {

		return addSubscription(
			userId, className, classPK, Subscription.FREQUENCY_INSTANT);
	}

	public Subscription addSubscription(
			String userId, String className, String classPK, String frequency)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		String subscriptionId = Long.toString(CounterLocalServiceUtil.increment(
			Subscription.class.getName()));

		Subscription subscription = SubscriptionUtil.create(subscriptionId);

		subscription.setCompanyId(user.getCompanyId());
		subscription.setUserId(user.getUserId());
		subscription.setUserName(user.getFullName());
		subscription.setCreateDate(now);
		subscription.setModifiedDate(now);
		subscription.setClassName(className);
		subscription.setClassPK(classPK);
		subscription.setFrequency(frequency);

		SubscriptionUtil.update(subscription);

		return subscription;
	}

	public void deleteSubscription(
			String userId, String className, String classPK)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		SubscriptionUtil.removeByC_U_C_C(
			user.getCompanyId(), userId, className, classPK);
	}

	public void deleteSubscriptions(String userId) throws SystemException {
		SubscriptionUtil.removeByUserId(userId);
	}

	public void deleteSubscriptions(
			String companyId, String className, String classPK)
		throws SystemException {

		SubscriptionUtil.removeByC_C_C(companyId, className, classPK);
	}

	public Subscription getSubscription(
			String companyId, String userId, String className, String classPK)
		throws PortalException, SystemException {

		return SubscriptionUtil.findByC_U_C_C(
			companyId, userId, className, classPK);
	}

	public List getSubscriptions(
			String companyId, String className, String classPK)
		throws PortalException, SystemException {

		return SubscriptionUtil.findByC_C_C(companyId, className, classPK);
	}

	public boolean isSubscribed(
			String companyId, String userId, String className, String classPK)
		throws PortalException, SystemException {

		try {
			SubscriptionUtil.findByC_U_C_C(
				companyId, userId, className, classPK);

			return true;
		}
		catch (NoSuchSubscriptionException nsse) {
			return false;
		}
	}

}