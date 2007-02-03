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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.SubscriptionLocalService;
import com.liferay.portal.service.SubscriptionLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SubscriptionLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SubscriptionLocalServiceEJBImpl implements SubscriptionLocalService,
	SessionBean {
	public com.liferay.portal.model.Subscription addSubscription(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().addSubscription(userId,
			className, classPK);
	}

	public com.liferay.portal.model.Subscription addSubscription(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String frequency)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().addSubscription(userId,
			className, classPK, frequency);
	}

	public void deleteSubscription(java.lang.String userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalServiceFactory.getTxImpl().deleteSubscription(userId,
			className, classPK);
	}

	public void deleteSubscriptions(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		SubscriptionLocalServiceFactory.getTxImpl().deleteSubscriptions(userId);
	}

	public void deleteSubscriptions(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		SubscriptionLocalServiceFactory.getTxImpl().deleteSubscriptions(companyId,
			className, classPK);
	}

	public com.liferay.portal.model.Subscription getSubscription(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().getSubscription(companyId,
			userId, className, classPK);
	}

	public java.util.List getSubscriptions(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().getSubscriptions(companyId,
			className, classPK);
	}

	public boolean isSubscribed(java.lang.String companyId,
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().isSubscribed(companyId,
			userId, className, classPK);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}