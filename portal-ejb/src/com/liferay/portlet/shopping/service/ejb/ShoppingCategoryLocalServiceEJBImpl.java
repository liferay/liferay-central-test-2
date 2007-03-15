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

package com.liferay.portlet.shopping.service.ejb;

import com.liferay.portlet.shopping.service.ShoppingCategoryLocalService;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ShoppingCategoryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.shopping.service.ShoppingCategoryLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceUtil
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingCategoryLocalServiceEJB
 * @see com.liferay.portlet.shopping.service.ejb.ShoppingCategoryLocalServiceHome
 * @see com.liferay.portlet.shopping.service.impl.ShoppingCategoryLocalServiceImpl
 *
 */
public class ShoppingCategoryLocalServiceEJBImpl
	implements ShoppingCategoryLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			plid, parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			plid, parentCategoryId, name, description, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			plid, parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addCategoryResources(java.lang.String categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalServiceFactory.getTxImpl().addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalServiceFactory.getTxImpl().addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(java.lang.String categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalServiceFactory.getTxImpl().addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalServiceFactory.getTxImpl().addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalServiceFactory.getTxImpl().deleteCategory(categoryId);
	}

	public void deleteCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalServiceFactory.getTxImpl().deleteCategory(category);
	}

	public java.util.List getCategories(long groupId)
		throws com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().getCategories(groupId);
	}

	public java.util.List getCategories(long groupId,
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().getCategories(groupId,
			parentCategoryId, begin, end);
	}

	public int getCategoriesCount(long groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl()
												  .getCategoriesCount(groupId,
			parentCategoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getCategory(
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().getCategory(categoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getParentCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl()
												  .getParentCategory(category);
	}

	public java.util.List getParentCategories(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl()
												  .getParentCategories(categoryId);
	}

	public java.util.List getParentCategories(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl()
												  .getParentCategories(category);
	}

	public void getSubcategoryIds(java.util.List categoryIds, long groupId,
		java.lang.String categoryId) throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalServiceFactory.getTxImpl().getSubcategoryIds(categoryIds,
			groupId, categoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory updateCategory(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentCategory)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ShoppingCategoryLocalServiceFactory.getTxImpl().updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory);
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