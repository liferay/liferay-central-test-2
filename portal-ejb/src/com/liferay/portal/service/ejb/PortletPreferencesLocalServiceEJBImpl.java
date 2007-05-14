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

import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.PortletPreferencesLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="PortletPreferencesLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.PortletPreferencesLocalService
 * @see com.liferay.portal.service.PortletPreferencesLocalServiceUtil
 * @see com.liferay.portal.service.ejb.PortletPreferencesLocalServiceEJB
 * @see com.liferay.portal.service.ejb.PortletPreferencesLocalServiceHome
 * @see com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl
 *
 */
public class PortletPreferencesLocalServiceEJBImpl
	implements PortletPreferencesLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void deletePortletPreferences(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalServiceFactory.getTxImpl()
											 .deletePortletPreferences(ownerId,
			ownerType, plid);
	}

	public void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalServiceFactory.getTxImpl()
											 .deletePortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	public javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getDefaultPreferences(companyId,
			portletId);
	}

	public java.util.List getPortletPreferences()
		throws com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferences();
	}

	public java.util.List getPortletPreferences(long plid)
		throws com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferences(plid);
	}

	public java.util.List getPortletPreferences(long ownerId, int ownerType,
		long plid)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferences(ownerId,
			ownerType, plid);
	}

	public com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	public javax.portlet.PortletPreferences getPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl().getPreferences(portletPreferencesIds);
	}

	public javax.portlet.PortletPreferences getPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl().getPreferences(companyId,
			ownerId, ownerType, plid, portletId);
	}

	public com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.updatePreferences(ownerId,
			ownerType, plid, portletId, prefs);
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