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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.SubscriptionLocalService
 * @see com.liferay.portal.service.SubscriptionLocalServiceUtil
 * @see com.liferay.portal.service.ejb.SubscriptionLocalServiceEJB
 * @see com.liferay.portal.service.ejb.SubscriptionLocalServiceHome
 * @see com.liferay.portal.service.impl.SubscriptionLocalServiceImpl
 *
 */
public class SubscriptionLocalServiceEJBImpl implements SubscriptionLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.Subscription addSubscription(long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().addSubscription(userId,
			className, classPK);
	}

	public com.liferay.portal.model.Subscription addSubscription(long userId,
		java.lang.String className, long classPK, java.lang.String frequency)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().addSubscription(userId,
			className, classPK, frequency);
	}

	public void deleteSubscription(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SubscriptionLocalServiceFactory.getTxImpl().deleteSubscription(userId,
			className, classPK);
	}

	public void deleteSubscriptions(long userId)
		throws com.liferay.portal.SystemException {
		SubscriptionLocalServiceFactory.getTxImpl().deleteSubscriptions(userId);
	}

	public void deleteSubscriptions(long companyId, java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException {
		SubscriptionLocalServiceFactory.getTxImpl().deleteSubscriptions(companyId,
			className, classPK);
	}

	public com.liferay.portal.model.Subscription getSubscription(
		long companyId, long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().getSubscription(companyId,
			userId, className, classPK);
	}

	public java.util.List getSubscriptions(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SubscriptionLocalServiceFactory.getTxImpl().getSubscriptions(companyId,
			className, classPK);
	}

	public boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long classPK)
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