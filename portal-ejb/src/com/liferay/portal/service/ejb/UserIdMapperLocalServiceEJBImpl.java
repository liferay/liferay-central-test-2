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

import com.liferay.portal.service.UserIdMapperLocalService;
import com.liferay.portal.service.UserIdMapperLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="UserIdMapperLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.UserIdMapperLocalService
 * @see com.liferay.portal.service.UserIdMapperLocalServiceUtil
 * @see com.liferay.portal.service.ejb.UserIdMapperLocalServiceEJB
 * @see com.liferay.portal.service.ejb.UserIdMapperLocalServiceHome
 * @see com.liferay.portal.service.impl.UserIdMapperLocalServiceImpl
 *
 */
public class UserIdMapperLocalServiceEJBImpl implements UserIdMapperLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return UserIdMapperLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return UserIdMapperLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void deleteUserIdMappers(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		UserIdMapperLocalServiceFactory.getTxImpl().deleteUserIdMappers(userId);
	}

	public com.liferay.portal.model.UserIdMapper getUserIdMapper(
		java.lang.String userId, java.lang.String type)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserIdMapperLocalServiceFactory.getTxImpl().getUserIdMapper(userId,
			type);
	}

	public java.util.List getUserIdMappers(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return UserIdMapperLocalServiceFactory.getTxImpl().getUserIdMappers(userId);
	}

	public com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		java.lang.String userId, java.lang.String type,
		java.lang.String description, java.lang.String externalUserId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserIdMapperLocalServiceFactory.getTxImpl().updateUserIdMapper(userId,
			type, description, externalUserId);
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