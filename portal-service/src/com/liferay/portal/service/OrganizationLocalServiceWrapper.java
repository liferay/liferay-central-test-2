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

package com.liferay.portal.service;


/**
 * <a href="OrganizationLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link OrganizationLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationLocalService
 * @generated
 */
public class OrganizationLocalServiceWrapper implements OrganizationLocalService {
	public OrganizationLocalServiceWrapper(
		OrganizationLocalService organizationLocalService) {
		_organizationLocalService = organizationLocalService;
	}

	public com.liferay.portal.model.Organization addOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.addOrganization(organization);
	}

	public com.liferay.portal.model.Organization createOrganization(
		long organizationId) {
		return _organizationLocalService.createOrganization(organizationId);
	}

	public void deleteOrganization(long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationLocalService.deleteOrganization(organizationId);
	}

	public void deleteOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException {
		_organizationLocalService.deleteOrganization(organization);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _organizationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getOrganization(organizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		int start, int end) throws com.liferay.portal.SystemException {
		return _organizationLocalService.getOrganizations(start, end);
	}

	public int getOrganizationsCount()
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.getOrganizationsCount();
	}

	public com.liferay.portal.model.Organization updateOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.updateOrganization(organization);
	}

	public com.liferay.portal.model.Organization updateOrganization(
		com.liferay.portal.model.Organization organization, boolean merge)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.updateOrganization(organization, merge);
	}

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.SystemException {
		_organizationLocalService.addGroupOrganizations(groupId, organizationIds);
	}

	public com.liferay.portal.model.Organization addOrganization(long userId,
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.addOrganization(userId,
			parentOrganizationId, name, type, recursable, regionId, countryId,
			statusId, comments, serviceContext);
	}

	public void addOrganizationResources(long userId,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationLocalService.addOrganizationResources(userId, organization);
	}

	public void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws com.liferay.portal.SystemException {
		_organizationLocalService.addPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public void deleteLogo(long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationLocalService.deleteLogo(organizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getGroupOrganizations(
		long groupId) throws com.liferay.portal.SystemException {
		return _organizationLocalService.getGroupOrganizations(groupId);
	}

	public com.liferay.portal.model.Organization getOrganization(
		long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getOrganization(companyId, name);
	}

	public long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.getOrganizationId(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getOrganizations(organizationIds);
	}

	public java.util.List<com.liferay.portal.model.Organization> getParentOrganizations(
		long organizationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getParentOrganizations(organizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations(
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.getSuborganizations(organizations);
	}

	public java.util.List<com.liferay.portal.model.Organization> getSubsetOrganizations(
		java.util.List<com.liferay.portal.model.Organization> allOrganizations,
		java.util.List<com.liferay.portal.model.Organization> availableOrganizations) {
		return _organizationLocalService.getSubsetOrganizations(allOrganizations,
			availableOrganizations);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getUserOrganizations(userId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, boolean inheritUserGroups)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getUserOrganizations(userId,
			inheritUserGroups);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getUserOrganizations(userId, start, end);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, boolean inheritUserGroups, int start, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.getUserOrganizations(userId,
			inheritUserGroups, start, end);
	}

	public int getUserOrganizationsCount(long userId)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.getUserOrganizationsCount(userId);
	}

	public boolean hasGroupOrganization(long groupId, long organizationId)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.hasGroupOrganization(groupId,
			organizationId);
	}

	public boolean hasUserOrganization(long userId, long organizationId)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.hasUserOrganization(userId,
			organizationId);
	}

	public boolean hasUserOrganization(long userId, long organizationId,
		boolean inheritSuborganizations, boolean inheritUserGroups,
		boolean includeSpecifiedOrganization)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.hasUserOrganization(userId,
			organizationId, inheritSuborganizations, inheritUserGroups,
			includeSpecifiedOrganization);
	}

	public boolean hasPasswordPolicyOrganization(long passwordPolicyId,
		long organizationId) throws com.liferay.portal.SystemException {
		return _organizationLocalService.hasPasswordPolicyOrganization(passwordPolicyId,
			organizationId);
	}

	public void rebuildTree(long companyId, boolean force)
		throws com.liferay.portal.SystemException {
		_organizationLocalService.rebuildTree(companyId, force);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		java.lang.String type, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, keywords, type, regionId, countryId, params,
			start, end);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		java.lang.String type, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, keywords, type, regionId, countryId, params,
			start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end) throws com.liferay.portal.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, name, type, street, city, zip, regionId,
			countryId, params, andOperator, start, end);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, name, type, street, city, zip, regionId,
			countryId, params, andOperator, start, end, obc);
	}

	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.searchCount(companyId,
			parentOrganizationId, keywords, type, regionId, countryId, params);
	}

	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return _organizationLocalService.searchCount(companyId,
			parentOrganizationId, name, type, street, city, zip, regionId,
			countryId, params, andOperator);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.SystemException {
		_organizationLocalService.setGroupOrganizations(groupId, organizationIds);
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.SystemException {
		_organizationLocalService.unsetGroupOrganizations(groupId,
			organizationIds);
	}

	public void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws com.liferay.portal.SystemException {
		_organizationLocalService.unsetPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public void updateAsset(long userId,
		com.liferay.portal.model.Organization organization,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_organizationLocalService.updateAsset(userId, organization,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portal.model.Organization updateOrganization(
		long companyId, long organizationId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, boolean recursable,
		long regionId, long countryId, int statusId, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _organizationLocalService.updateOrganization(companyId,
			organizationId, parentOrganizationId, name, type, recursable,
			regionId, countryId, statusId, comments, serviceContext);
	}

	public OrganizationLocalService getWrappedOrganizationLocalService() {
		return _organizationLocalService;
	}

	private OrganizationLocalService _organizationLocalService;
}