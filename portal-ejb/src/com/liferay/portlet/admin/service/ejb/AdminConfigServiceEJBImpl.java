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

package com.liferay.portlet.admin.service.ejb;

import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.admin.service.spring.AdminConfigService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="AdminConfigServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AdminConfigServiceEJBImpl implements AdminConfigService,
	SessionBean {
	public static final String CLASS_NAME = AdminConfigService.class.getName() +
		".transaction";

	public static AdminConfigService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (AdminConfigService)ctx.getBean(CLASS_NAME);
	}

	public java.util.List getAdminConfig(java.lang.String companyId,
		java.lang.String type)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getAdminConfig(companyId, type);
	}

	public com.liferay.portlet.admin.model.JournalConfig getJournalConfig(
		java.lang.String companyId, java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getJournalConfig(companyId, portletId);
	}

	public com.liferay.portlet.admin.model.ShoppingConfig getShoppingConfig(
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getShoppingConfig(companyId);
	}

	public com.liferay.portlet.admin.model.UserConfig getUserConfig(
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getUserConfig(companyId);
	}

	public void updateJournalConfig(
		com.liferay.portlet.admin.model.JournalConfig journalConfig,
		java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().updateJournalConfig(journalConfig, portletId);
	}

	public void updateShoppingConfig(
		com.liferay.portlet.admin.model.ShoppingConfig shoppingConfig)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().updateShoppingConfig(shoppingConfig);
	}

	public void updateUserConfig(
		com.liferay.portlet.admin.model.UserConfig userConfig)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().updateUserConfig(userConfig);
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