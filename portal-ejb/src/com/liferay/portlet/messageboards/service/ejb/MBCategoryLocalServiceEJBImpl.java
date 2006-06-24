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

package com.liferay.portlet.messageboards.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalService;

import org.springframework.context.ApplicationContext;

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
	public static final String CLASS_NAME = MBCategoryLocalService.class.getName() +
		".transaction";

	public static MBCategoryLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (MBCategoryLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addCategory(userId, plid, parentCategoryId, name,
			description, addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(java.lang.String categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addCategoryResources(categoryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addCategoryResources(category, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteCategories(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteCategories(groupId);
	}

	public void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteCategory(categoryId);
	}

	public void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteCategory(category);
	}

	public java.util.List getCategories(java.lang.String groupId,
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getService().getCategories(groupId, parentCategoryId, begin, end);
	}

	public int getCategoriesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesCount(groupId);
	}

	public int getCategoriesCount(java.lang.String groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryId);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getCategory(
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getCategory(categoryId);
	}

	public void getSubcategoryIds(java.util.List categoryIds,
		java.lang.String groupId, java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		getService().getSubcategoryIds(categoryIds, groupId, categoryId);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		getService().reIndex(ids);
	}

	public com.liferay.util.lucene.Hits search(java.lang.String companyId,
		java.lang.String groupId, java.lang.String[] categoryIds,
		java.lang.String[] topicIds, java.lang.String threadId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return getService().search(companyId, groupId, categoryIds, topicIds,
			threadId, keywords);
	}

	public com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		java.lang.String companyId, java.lang.String categoryId,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateCategory(companyId, categoryId,
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