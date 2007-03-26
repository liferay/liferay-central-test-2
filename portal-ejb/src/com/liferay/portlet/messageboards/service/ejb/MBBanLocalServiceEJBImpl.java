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

package com.liferay.portlet.messageboards.service.ejb;

import com.liferay.portlet.messageboards.service.MBBanLocalService;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBBanLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.messageboards.service.MBBanLocalService
 * @see com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil
 * @see com.liferay.portlet.messageboards.service.ejb.MBBanLocalServiceEJB
 * @see com.liferay.portlet.messageboards.service.ejb.MBBanLocalServiceHome
 * @see com.liferay.portlet.messageboards.service.impl.MBBanLocalServiceImpl
 *
 */
public class MBBanLocalServiceEJBImpl implements MBBanLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return MBBanLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return MBBanLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.messageboards.model.MBBan addBan(
		java.lang.String userId, java.lang.String plid,
		java.lang.String banUserId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBBanLocalServiceFactory.getTxImpl().addBan(userId, plid,
			banUserId);
	}

	public void checkBan(long groupId, java.lang.String banUserId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBBanLocalServiceFactory.getTxImpl().checkBan(groupId, banUserId);
	}

	public void deleteBan(java.lang.String plid, java.lang.String banUserId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBBanLocalServiceFactory.getTxImpl().deleteBan(plid, banUserId);
	}

	public void deleteBans(long groupId)
		throws com.liferay.portal.SystemException {
		MBBanLocalServiceFactory.getTxImpl().deleteBans(groupId);
	}

	public void deleteBans(java.lang.String banUserId)
		throws com.liferay.portal.SystemException {
		MBBanLocalServiceFactory.getTxImpl().deleteBans(banUserId);
	}

	public java.util.List getBans(long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return MBBanLocalServiceFactory.getTxImpl().getBans(groupId, start, end);
	}

	public int getBansCount(long groupId)
		throws com.liferay.portal.SystemException {
		return MBBanLocalServiceFactory.getTxImpl().getBansCount(groupId);
	}

	public boolean hasBan(long groupId, java.lang.String banUserId)
		throws com.liferay.portal.SystemException {
		return MBBanLocalServiceFactory.getTxImpl().hasBan(groupId, banUserId);
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