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

import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutSetLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="LayoutSetLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.LayoutSetLocalService
 * @see com.liferay.portal.service.LayoutSetLocalServiceUtil
 * @see com.liferay.portal.service.ejb.LayoutSetLocalServiceEJB
 * @see com.liferay.portal.service.ejb.LayoutSetLocalServiceHome
 * @see com.liferay.portal.service.impl.LayoutSetLocalServiceImpl
 *
 */
public class LayoutSetLocalServiceEJBImpl implements LayoutSetLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portal.model.LayoutSet addLayoutSet(
		java.lang.String ownerId, java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().addLayoutSet(ownerId,
			companyId);
	}

	public void deleteLayoutSet(java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalServiceFactory.getTxImpl().deleteLayoutSet(ownerId);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().getLayoutSet(ownerId);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String companyId, java.lang.String virtualHost)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().getLayoutSet(companyId,
			virtualHost);
	}

	public void updateLogo(java.lang.String ownerId, boolean logo,
		java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		LayoutSetLocalServiceFactory.getTxImpl().updateLogo(ownerId, logo, file);
	}

	public com.liferay.portal.model.LayoutSet updateLookAndFeel(
		java.lang.String ownerId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().updateLookAndFeel(ownerId,
			themeId, colorSchemeId, css);
	}

	public com.liferay.portal.model.LayoutSet updatePageCount(
		java.lang.String ownerId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().updatePageCount(ownerId);
	}

	public com.liferay.portal.model.LayoutSet updateVirtualHost(
		java.lang.String ownerId, java.lang.String virtualHost)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().updateVirtualHost(ownerId,
			virtualHost);
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