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

	public void deletePortletPreferences(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalServiceFactory.getTxImpl()
											 .deletePortletPreferences(ownerId);
	}

	public void deletePortletPreferences(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		PortletPreferencesLocalServiceFactory.getTxImpl()
											 .deletePortletPreferences(layoutId,
			ownerId);
	}

	public void deletePortletPreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalServiceFactory.getTxImpl()
											 .deletePortletPreferences(pk);
	}

	public javax.portlet.PortletPreferences getDefaultPreferences(
		java.lang.String companyId, java.lang.String portletId)
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

	public com.liferay.portal.model.PortletPreferences getPortletPreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferences(pk);
	}

	public java.util.List getPortletPreferencesByLayout(
		java.lang.String layoutId, java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferencesByLayout(layoutId,
			ownerId);
	}

	public java.util.List getPortletPreferencesByOwnerId(
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferencesByOwnerId(ownerId);
	}

	public java.util.List getPortletPreferencesByPortletId(
		java.lang.String portletId) throws com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.getPortletPreferencesByPortletId(portletId);
	}

	public javax.portlet.PortletPreferences getPreferences(
		java.lang.String companyId,
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl().getPreferences(companyId,
			pk);
	}

	public com.liferay.portal.model.PortletPreferences updatePreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return PortletPreferencesLocalServiceFactory.getTxImpl()
													.updatePreferences(pk, prefs);
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