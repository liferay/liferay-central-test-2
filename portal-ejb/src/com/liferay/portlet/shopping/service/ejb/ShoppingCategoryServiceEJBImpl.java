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

package com.liferay.portlet.shopping.service.ejb;

import com.liferay.portal.service.impl.PrincipalSessionBean;
import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.shopping.service.spring.ShoppingCategoryService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingCategoryServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryServiceEJBImpl implements ShoppingCategoryService,
	SessionBean {
	public static final String CLASS_NAME = ShoppingCategoryService.class.getName() +
		".transaction";

	public static ShoppingCategoryService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (ShoppingCategoryService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		java.lang.String parentCategoryId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().addCategory(parentCategoryId, name);
	}

	public void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteCategory(categoryId);
	}

	public void deleteCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		getService().deleteCategory(category);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getCategory(
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCategory(categoryId);
	}

	public java.util.List getCategories(java.lang.String companyId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCategories(companyId);
	}

	public java.util.List getCategories(java.lang.String companyId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCategories(companyId, parentCategoryId);
	}

	public java.util.List getCategories(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCategories(category);
	}

	public int getCategoriesSize(java.lang.String companyId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCategoriesSize(companyId);
	}

	public int getCategoriesSize(java.lang.String companyId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCategoriesSize(companyId, parentCategoryId);
	}

	public int getCategoriesSize(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getCategoriesSize(category);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getParentCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getParentCategory(category);
	}

	public java.util.List getParentCategories(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getParentCategories(categoryId);
	}

	public java.util.List getParentCategories(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().getParentCategories(category);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory updateCategory(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().updateCategory(categoryId, parentCategoryId, name);
	}

	public boolean hasAdmin()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().hasAdmin();
	}

	public boolean hasAdmin(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return getService().hasAdmin(categoryId);
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