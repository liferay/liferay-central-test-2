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

package com.liferay.portlet.blogs.service.ejb;

import com.liferay.portlet.blogs.service.BlogsCategoryLocalService;
import com.liferay.portlet.blogs.service.BlogsCategoryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="BlogsCategoryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.blogs.service.BlogsCategoryLocalService
 * @see com.liferay.portlet.blogs.service.BlogsCategoryLocalServiceUtil
 * @see com.liferay.portlet.blogs.service.ejb.BlogsCategoryLocalServiceEJB
 * @see com.liferay.portlet.blogs.service.ejb.BlogsCategoryLocalServiceHome
 * @see com.liferay.portlet.blogs.service.impl.BlogsCategoryLocalServiceImpl
 *
 */
public class BlogsCategoryLocalServiceEJBImpl
	implements BlogsCategoryLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			parentCategoryId, name, description, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalServiceFactory.getTxImpl().addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.blogs.model.BlogsCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalServiceFactory.getTxImpl().addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalServiceFactory.getTxImpl().addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.blogs.model.BlogsCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalServiceFactory.getTxImpl().addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalServiceFactory.getTxImpl().deleteCategory(categoryId);
	}

	public void deleteCategory(
		com.liferay.portlet.blogs.model.BlogsCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalServiceFactory.getTxImpl().deleteCategory(category);
	}

	public java.util.List getCategories(long parentCategoryId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().getCategories(parentCategoryId,
			begin, end);
	}

	public int getCategoriesCount(long parentCategoryId)
		throws com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().getCategoriesCount(parentCategoryId);
	}

	public com.liferay.portlet.blogs.model.BlogsCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().getCategory(categoryId);
	}

	public void getSubcategoryIds(java.util.List categoryIds, long categoryId)
		throws com.liferay.portal.SystemException {
		BlogsCategoryLocalServiceFactory.getTxImpl().getSubcategoryIds(categoryIds,
			categoryId);
	}

	public com.liferay.portlet.blogs.model.BlogsCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return BlogsCategoryLocalServiceFactory.getTxImpl().updateCategory(categoryId,
			parentCategoryId, name, description);
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