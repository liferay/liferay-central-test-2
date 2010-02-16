/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;


/**
 * <a href="MBCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBCategoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBCategoryLocalService
 * @generated
 */
public class MBCategoryLocalServiceWrapper implements MBCategoryLocalService {
	public MBCategoryLocalServiceWrapper(
		MBCategoryLocalService mbCategoryLocalService) {
		_mbCategoryLocalService = mbCategoryLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBCategory addMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.addMBCategory(mbCategory);
	}

	public com.liferay.portlet.messageboards.model.MBCategory createMBCategory(
		long categoryId) {
		return _mbCategoryLocalService.createMBCategory(categoryId);
	}

	public void deleteMBCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.deleteMBCategory(categoryId);
	}

	public void deleteMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.deleteMBCategory(mbCategory);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getMBCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getMBCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getMBCategories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getMBCategories(start, end);
	}

	public int getMBCategoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getMBCategoriesCount();
	}

	public com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.updateMBCategory(mbCategory);
	}

	public com.liferay.portlet.messageboards.model.MBCategory updateMBCategory(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.updateMBCategory(mbCategory, merge);
	}

	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.addCategory(userId, parentCategoryId,
			name, description, emailAddress, inProtocol, inServerName,
			inServerPort, inUseSSL, inUserName, inPassword, inReadInterval,
			outEmailAddress, outCustom, outServerName, outServerPort,
			outUseSSL, outUserName, outPassword, mailingListActive,
			serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String uuid, long userId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.addCategory(uuid, userId,
			parentCategoryId, name, description, emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, serviceContext);
	}

	public void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public void deleteCategories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.deleteCategories(groupId);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.deleteCategory(categoryId);
	}

	public void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.deleteCategory(category);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCategories(groupId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCategories(groupId, parentCategoryId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCategories(groupId, parentCategoryId,
			start, end);
	}

	public int getCategoriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCategoriesCount(groupId);
	}

	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryId);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCompanyCategories(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCompanyCategories(companyId, start,
			end);
	}

	public int getCompanyCategoriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getCompanyCategoriesCount(companyId);
	}

	public void getSubcategoryIds(java.util.List<Long> categoryIds,
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getSubscribedCategories(groupId, userId,
			start, end);
	}

	public int getSubscribedCategoriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getSubscribedCategoriesCount(groupId,
			userId);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getSystemCategory()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.getSystemCategory();
	}

	public void subscribeCategory(long userId, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.subscribeCategory(userId, groupId, categoryId);
	}

	public void unsubscribeCategory(long userId, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbCategoryLocalService.unsubscribeCategory(userId, groupId, categoryId);
	}

	public com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategoryLocalService.updateCategory(categoryId,
			parentCategoryId, name, description, emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, mergeWithParentCategory, serviceContext);
	}

	public MBCategoryLocalService getWrappedMBCategoryLocalService() {
		return _mbCategoryLocalService;
	}

	private MBCategoryLocalService _mbCategoryLocalService;
}