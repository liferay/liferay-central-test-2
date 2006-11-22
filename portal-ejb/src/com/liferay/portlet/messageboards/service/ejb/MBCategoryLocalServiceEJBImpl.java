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

package com.liferay.portlet.messageboards.service.ejb;

import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBCategoryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBCategoryLocalServiceEJBImpl implements MBCategoryLocalService,
	SessionBean {
	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			plid, parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			plid, parentCategoryId, name, description, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().addCategory(userId,
			plid, parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addCategoryResources(java.lang.String categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(java.lang.String categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public void deleteCategories(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().deleteCategories(groupId);
	}

	public void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().deleteCategory(categoryId);
	}

	public void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().deleteCategory(category);
	}

	public java.util.List getCategories(java.lang.String groupId,
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().getCategories(groupId,
			parentCategoryId, begin, end);
	}

	public int getCategoriesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().getCategoriesCount(groupId);
	}

	public int getCategoriesCount(java.lang.String groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().getCategoriesCount(groupId,
			parentCategoryId);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getCategory(
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().getCategory(categoryId);
	}

	public void getSubcategoryIds(java.util.List categoryIds,
		java.lang.String groupId, java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().getSubcategoryIds(categoryIds,
			groupId, categoryId);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getSystemCategory()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().getSystemCategory();
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().reIndex(ids);
	}

	public com.liferay.util.lucene.Hits search(java.lang.String companyId,
		java.lang.String groupId, java.lang.String[] categoryIds,
		java.lang.String threadId, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().search(companyId,
			groupId, categoryIds, threadId, keywords);
	}

	public com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentCategory)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return MBCategoryLocalServiceFactory.getTxImpl().updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory);
	}

	public void subscribeCategory(java.lang.String userId,
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().subscribeCategory(userId,
			categoryId);
	}

	public void unsubscribeCategory(java.lang.String userId,
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalServiceFactory.getTxImpl().unsubscribeCategory(userId,
			categoryId);
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