/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutSetLocalServiceEJBImpl implements LayoutSetLocalService,
	SessionBean {
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

	public com.liferay.portal.model.LayoutSet updateLookAndFeel(
		java.lang.String ownerId, java.lang.String themeId,
		java.lang.String colorSchemeId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return LayoutSetLocalServiceFactory.getTxImpl().updateLookAndFeel(ownerId,
			themeId, colorSchemeId);
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