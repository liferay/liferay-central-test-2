/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.OrganizationTypeException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.OrganizationLocalServiceBaseImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.OrganizationNameComparator;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 * @author Hugo Huijser
 */
public class OrganizationLocalServiceImpl
	extends OrganizationLocalServiceBaseImpl {

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		groupPersistence.addOrganizations(groupId, organizationIds);

		Indexer indexer = IndexerRegistryUtil.getIndexer(Organization.class);

		indexer.reindex(organizationIds);

		PermissionCacheUtil.clearCache();
	}

	public Organization addOrganization(
			long userId, long parentOrganizationId, String name,
			String type, boolean recursable, long regionId, long countryId,
			int statusId, String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Organization

		User user = userPersistence.findByPrimaryKey(userId);
		parentOrganizationId = getParentOrganizationId(
			user.getCompanyId(), parentOrganizationId);
		recursable = true;

		validate(
			user.getCompanyId(), parentOrganizationId, name, type, countryId,
			statusId);

		long organizationId = counterLocalService.increment();

		Organization organization = organizationPersistence.create(
			organizationId);

		organization.setCompanyId(user.getCompanyId());
		organization.setParentOrganizationId(parentOrganizationId);
		organization.setName(name);
		organization.setType(type);
		organization.setRecursable(recursable);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);
		organization.setComments(comments);

		organizationPersistence.update(organization, false);

		// Group

		Group group = groupLocalService.addGroup(
			userId, Organization.class.getName(), organizationId, name, null, 0,
			null, true, null);

		if (PropsValues.ORGANIZATIONS_ASSIGNMENT_AUTO) {

			// Role

			Role role = roleLocalService.getRole(
				organization.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

			userGroupRoleLocalService.addUserGroupRoles(
				userId, group.getGroupId(), new long[] {role.getRoleId()});

			// User

			userPersistence.addOrganization(userId, organizationId);
		}

		// Resources

		addOrganizationResources(userId, organization);

		// Asset

		if (serviceContext != null) {
			updateAsset(
				userId, organization, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}

		// Expando

		ExpandoBridge expandoBridge = organization.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(Organization.class);

		indexer.reindex(
			new String[] {String.valueOf(organization.getCompanyId())});

		return organization;
	}

	public void addOrganizationResources(long userId, Organization organization)
		throws PortalException, SystemException {

		String name = Organization.class.getName();

		resourceLocalService.addResources(
			organization.getCompanyId(), 0, userId, name,
			organization.getOrganizationId(), false, false, false);
	}

	public void addPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws SystemException {

		passwordPolicyRelLocalService.addPasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	public void deleteLogo(long organizationId)
		throws PortalException, SystemException {

		Organization organization = getOrganization(organizationId);

		Group group = organization.getGroup();

		LayoutSet publicLayoutSet =	layoutSetLocalService.getLayoutSet(
			group.getGroupId(), false);

		if (publicLayoutSet.isLogo()) {
			long logoId = publicLayoutSet.getLogoId();

			publicLayoutSet.setLogo(false);
			publicLayoutSet.setLogoId(0);

			layoutSetPersistence.update(publicLayoutSet, false);

			imageLocalService.deleteImage(logoId);
		}

		LayoutSet privateLayoutSet = layoutSetLocalService.getLayoutSet(
			group.getGroupId(), true);

		if (privateLayoutSet.isLogo()) {
			long logoId = privateLayoutSet.getLogoId();

			privateLayoutSet.setLogo(false);
			privateLayoutSet.setLogoId(0);

			layoutSetPersistence.update(privateLayoutSet, false);

			if (imageLocalService.getImage(logoId) != null) {
				imageLocalService.deleteImage(logoId);
			}
		}
	}

	public void deleteOrganization(long organizationId)
		throws PortalException, SystemException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		if ((userLocalService.getOrganizationUsersCount(
				organization.getOrganizationId(), true) > 0) ||
			(organizationPersistence.countByC_P(
				organization.getCompanyId(),
				organization.getOrganizationId()) > 0)) {

			throw new RequiredOrganizationException();
		}

		// Asset

		assetEntryLocalService.deleteEntry(
			Organization.class.getName(), organization.getOrganizationId());

		// Addresses

		addressLocalService.deleteAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Email addresses

		emailAddressLocalService.deleteEmailAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Expando

		expandoValueLocalService.deleteValues(
			Organization.class.getName(), organization.getOrganizationId());

		// Password policy relation

		passwordPolicyRelLocalService.deletePasswordPolicyRel(
			Organization.class.getName(), organization.getOrganizationId());

		// Phone

		phoneLocalService.deletePhones(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Website

		websiteLocalService.deleteWebsites(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Group

		Group group = organization.getGroup();

		groupLocalService.deleteGroup(group.getGroupId());

		// Resources

		String name = Organization.class.getName();

		resourceLocalService.deleteResource(
			organization.getCompanyId(), name,
			ResourceConstants.SCOPE_INDIVIDUAL,
			organization.getOrganizationId());

		// Organization

		organizationPersistence.remove(organization);

		// Permission cache

		PermissionCacheUtil.clearCache();

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(Organization.class);

		indexer.delete(organization);

		indexer.reindex(
			new String[] {String.valueOf(organization.getCompanyId())});
	}

	public List<Organization> getGroupOrganizations(long groupId)
		throws SystemException {

		return groupPersistence.getOrganizations(groupId);
	}

	public Organization getOrganization(long organizationId)
		throws PortalException, SystemException {

		return organizationPersistence.findByPrimaryKey(organizationId);
	}

	public Organization getOrganization(long companyId, String name)
		throws PortalException, SystemException {

		return organizationPersistence.findByC_N(companyId, name);
	}

	public long getOrganizationId(long companyId, String name)
		throws SystemException {

		Organization organization = organizationPersistence.fetchByC_N(
			companyId, name);

		if (organization != null) {
			return organization.getOrganizationId();
		}
		else {
			return 0;
		}
	}

	public List<Organization> getOrganizations(
			long companyId, long parentOrganizationId)
		throws SystemException {

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {
			return organizationPersistence.findByCompanyId(companyId);
		}
		else {
			return organizationPersistence.findByC_P(
				companyId, parentOrganizationId);
		}
	}

	public List<Organization> getOrganizations(
			long companyId, long parentOrganizationId, int start, int end)
		throws SystemException {

		return organizationPersistence.findByC_P(
			companyId, parentOrganizationId, start, end);
	}

	public List<Organization> getOrganizations(long[] organizationIds)
		throws PortalException, SystemException {

		List<Organization> organizations = new ArrayList<Organization>(
			organizationIds.length);

		for (long organizationId : organizationIds) {
			Organization organization = getOrganization(organizationId);

			organizations.add(organization);
		}

		return organizations;
	}

	public int getOrganizationsCount(long companyId, long parentOrganizationId)
		throws SystemException {

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {
			return organizationPersistence.countByCompanyId(companyId);
		}
		else {
			return organizationPersistence.countByC_P(
				companyId, parentOrganizationId);
		}
	}

	public List<Organization> getParentOrganizations(long organizationId)
		throws PortalException, SystemException {

		if (organizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return new ArrayList<Organization>();
		}

		Organization organization =
			organizationPersistence.findByPrimaryKey(organizationId);

		return getParentOrganizations(organization, true);
	}

	public List<Organization> getSuborganizations(
			List<Organization> organizations)
		throws SystemException {

		List<Organization> allSuborganizations = new ArrayList<Organization>();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			List<Organization> suborganizations =
				organizationPersistence.findByC_P(
					organization.getCompanyId(),
					organization.getOrganizationId());

			addSuborganizations(allSuborganizations, suborganizations);
		}

		return allSuborganizations;
	}

	public List<Organization> getSubsetOrganizations(
		List<Organization> allOrganizations,
		List<Organization> availableOrganizations) {

		List<Organization> subsetOrganizations = new ArrayList<Organization>();

		Iterator<Organization> itr = allOrganizations.iterator();

		while (itr.hasNext()) {
			Organization organization = itr.next();

			if (availableOrganizations.contains(organization)) {
				subsetOrganizations.add(organization);
			}
		}

		return subsetOrganizations;
	}

	public List<Organization> getUserOrganizations(long userId)
		throws PortalException, SystemException {

		return getUserOrganizations(userId, false);
	}

	public List<Organization> getUserOrganizations(
			long userId, boolean inheritUserGroups)
		throws PortalException, SystemException {

		return getUserOrganizations(
			userId, inheritUserGroups, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Organization> getUserOrganizations(
			long userId, boolean inheritUserGroups, int start, int end)
		throws PortalException, SystemException {

		if (inheritUserGroups &&
			PropsValues.ORGANIZATIONS_USER_GROUP_MEMBERSHIP_ENABLED) {

			User user = userPersistence.findByPrimaryKey(userId);

			LinkedHashMap<String, Object> organizationParams =
				new LinkedHashMap<String, Object>();

			organizationParams.put("usersOrgs", new Long(userId));

			return search(
				user.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,	null,
				null, null, organizationParams, start, end);
		}
		else {
			return userPersistence.getOrganizations(userId, start, end);
		}
	}

	public List<Organization> getUserOrganizations(
			long userId, int start, int end)
		throws PortalException, SystemException {

		return getUserOrganizations(userId, false, start, end);
	}

	@ThreadLocalCachable
	public int getUserOrganizationsCount(long userId) throws SystemException {
		return userPersistence.getOrganizationsSize(userId);
	}

	public boolean hasGroupOrganization(long groupId, long organizationId)
		throws SystemException {

		return groupPersistence.containsOrganization(groupId, organizationId);
	}

	public boolean hasPasswordPolicyOrganization(
			long passwordPolicyId, long organizationId)
		throws SystemException {

		return passwordPolicyRelLocalService.hasPasswordPolicyRel(
			passwordPolicyId, Organization.class.getName(), organizationId);
	}

	public boolean hasUserOrganization(long userId, long organizationId)
		throws SystemException {

		return userPersistence.containsOrganization(userId, organizationId);
	}

	public boolean hasUserOrganization(
			long userId, long organizationId, boolean inheritSuborganizations,
			boolean inheritUserGroups, boolean includeSpecifiedOrganization)
		throws PortalException, SystemException {

		if (!inheritSuborganizations && !inheritUserGroups) {
			return userPersistence.containsOrganization(userId, organizationId);
		}

		if (inheritSuborganizations) {
			LinkedHashMap<String, Object> params =
				new LinkedHashMap<String, Object>();

			Long[][] leftAndRightOrganizationIds =
				EnterpriseAdminUtil.getLeftAndRightOrganizationIds(
					organizationId);

			if (!includeSpecifiedOrganization) {
				leftAndRightOrganizationIds[0][0] =
					leftAndRightOrganizationIds[0][0].longValue() + 1;
			}

			params.put("usersOrgsTree", leftAndRightOrganizationIds);

			if (userFinder.countByUser(userId, params) > 0) {
				return true;
			}
		}

		if (inheritUserGroups) {
			if (organizationFinder.countByO_U(organizationId, userId) > 0) {
				return true;
			}
		}

		return false;
	}

	public void rebuildTree(long companyId, boolean force)
		throws SystemException {

		organizationPersistence.rebuildTree(companyId, force);
	}

	public Hits search(
			long companyId, long parentOrganizationId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws SystemException {

		String name = null;
		String type = null;
		String street = null;
		String city = null;
		String zip = null;
		String region = null;
		String country = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			name = keywords;
			type = keywords;
			street = keywords;
			city = keywords;
			zip = keywords;
			region = keywords;
			country = keywords;
		}
		else {
			andOperator = true;
		}

		return search(
			companyId, parentOrganizationId, name, type, street, city, zip,
			region, country, params, andOperator, start, end, sort);
	}

	public List<Organization> search(
			long companyId, long parentOrganizationId, String keywords,
			String type, Long regionId, Long countryId,
			LinkedHashMap<String, Object> params,
			int start, int end)
		throws SystemException {

		return search(
			companyId, parentOrganizationId, keywords, type, regionId,
			countryId, params, start, end,
			new OrganizationNameComparator(true));
	}

	public List<Organization> search(
			long companyId, long parentOrganizationId, String keywords,
			String type, Long regionId, Long countryId,
			LinkedHashMap<String, Object> params,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		String parentOrganizationIdComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationIdComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.findByKeywords(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			keywords, type, regionId, countryId, params, start, end,
			obc);
	}

	public List<Organization> search(
			long companyId, long parentOrganizationId, String name, String type,
			String street, String city, String zip,
			Long regionId, Long countryId,
			LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end)
		throws SystemException {

		return search(
			companyId, parentOrganizationId, name, type, street, city, zip,
			regionId, countryId, params, andOperator, start, end,
			new OrganizationNameComparator(true));
	}

	public List<Organization> search(
			long companyId, long parentOrganizationId, String name, String type,
			String street, String city, String zip,
			Long regionId, Long countryId, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end, OrderByComparator obc)
		throws SystemException {

		String parentOrganizationIdComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationIdComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.findByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			name, type, street, city, zip, regionId, countryId, params,
			andOperator, start, end, obc);
	}

	public Hits search(
			long companyId, long parentOrganizationId, String name, String type,
			String street, String city, String zip, String region,
			String country, LinkedHashMap<String, Object> params,
			boolean andSearch, int start, int end, Sort sort)
		throws SystemException {

		try {
			Map<String, Serializable> attributes =
				new HashMap<String, Serializable>();

			attributes.put("city", city);
			attributes.put("country", country);
			attributes.put("name", name);
			attributes.put("params", params);

			if (parentOrganizationId > 0) {
				attributes.put(
					"parentOrganizationId",
					String.valueOf(parentOrganizationId));
			}

			attributes.put("region", region);
			attributes.put("street", street);
			attributes.put("type", type);
			attributes.put("zip", zip);

			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(andSearch);
			searchContext.setAttributes(attributes);
			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setSorts(new Sort[] {sort});
			searchContext.setStart(start);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				Organization.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public int searchCount(
			long companyId, long parentOrganizationId, String keywords,
			String type, Long regionId, Long countryId,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		String parentOrganizationIdComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationIdComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.countByKeywords(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			keywords, type, regionId, countryId, params);
	}

	public int searchCount(
			long companyId, long parentOrganizationId, String name, String type,
			String street, String city, String zip,
			Long regionId, Long countryId, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		String parentOrganizationIdComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationIdComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.countByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationIdComparator,
			name, type, street, city, zip, regionId, countryId, params,
			andOperator);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		groupPersistence.setOrganizations(groupId, organizationIds);

		Indexer indexer = IndexerRegistryUtil.getIndexer(Organization.class);

		indexer.reindex(organizationIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		groupPersistence.removeOrganizations(groupId, organizationIds);

		Indexer indexer = IndexerRegistryUtil.getIndexer(Organization.class);

		indexer.reindex(organizationIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws SystemException {

		passwordPolicyRelLocalService.deletePasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	public void updateAsset(
			long userId, Organization organization, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		Group companyGroup = company.getGroup();

		assetEntryLocalService.updateEntry(
			userId, companyGroup.getGroupId(), Organization.class.getName(),
			organization.getOrganizationId(), null, assetCategoryIds,
			assetTagNames, false, null, null, null, null, null,
			organization.getName(), StringPool.BLANK, null, null, 0, 0, null,
			false);
	}

	public Organization updateOrganization(
			long companyId, long organizationId, long parentOrganizationId,
			String name, String type, boolean recursable, long regionId,
			long countryId, int statusId, String comments,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Organization

		parentOrganizationId = getParentOrganizationId(
			companyId, parentOrganizationId);
		recursable = true;

		validate(
			companyId, organizationId, parentOrganizationId, name, type,
			countryId, statusId);

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		organization.setParentOrganizationId(parentOrganizationId);
		organization.setName(name);
		organization.setType(type);
		organization.setRecursable(recursable);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);
		organization.setComments(comments);

		organizationPersistence.update(organization, false);

		// Asset

		if (serviceContext != null) {
			updateAsset(
				serviceContext.getUserId(), organization,
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}

		// Expando

		ExpandoBridge expandoBridge = organization.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(Organization.class);

		indexer.reindex(
			new String[] {String.valueOf(organization.getCompanyId())});

		return organization;
	}

	protected void addSuborganizations(
			List<Organization> allSuborganizations,
			List<Organization> organizations)
		throws SystemException {

		for (Organization organization : organizations) {
			if (!allSuborganizations.contains(organization)) {
				allSuborganizations.add(organization);

				List<Organization> suborganizations =
					organizationPersistence.findByC_P(
						organization.getCompanyId(),
						organization.getOrganizationId());

				addSuborganizations(allSuborganizations, suborganizations);
			}
		}
	}

	protected long getParentOrganizationId(
			long companyId, long parentOrganizationId)
		throws SystemException {

		if (parentOrganizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			// Ensure parent organization exists and belongs to the proper
			// company

			Organization parentOrganization =
				organizationPersistence.fetchByPrimaryKey(parentOrganizationId);

			if ((parentOrganization == null) ||
				(companyId != parentOrganization.getCompanyId())) {

				parentOrganizationId =
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;
			}
		}

		return parentOrganizationId;
	}

	protected List<Organization> getParentOrganizations(
			Organization organization, boolean lastOrganization)
		throws PortalException, SystemException {

		List<Organization> organizations = new ArrayList<Organization>();

		if (!lastOrganization) {
			organizations.add(organization);
		}

		long parentOrganizationId = organization.getParentOrganizationId();

		if (parentOrganizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return organizations;
		}

		Organization parentOrganization =
			organizationPersistence.findByPrimaryKey(parentOrganizationId);

		List<Organization> parentOrganizatons = getParentOrganizations(
			parentOrganization, false);

		organizations.addAll(parentOrganizatons);

		return organizations;
	}

	protected boolean isParentOrganization(
			long parentOrganizationId, long organizationId)
		throws PortalException, SystemException {

		// Return true if parentOrganizationId is among the parent organizatons
		// of organizationId

		Organization parentOrganization =
			organizationPersistence.findByPrimaryKey(
				parentOrganizationId);

		List<Organization> parentOrganizations = getParentOrganizations(
			organizationId);

		if (parentOrganizations.contains(parentOrganization)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void validate(
			long companyId, long organizationId, long parentOrganizationId,
			String name, String type, long countryId, int statusId)
		throws PortalException, SystemException {

		if (!ArrayUtil.contains(PropsValues.ORGANIZATIONS_TYPES, type)) {
			throw new OrganizationTypeException(
				"Invalid organization type " + type);
		}

		if ((parentOrganizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID)) {

			if (!OrganizationImpl.isRootable(type)) {
				throw new OrganizationParentException(
					"Organization of type " + type + " cannot be a root");
			}
		}
		else {
			Organization parentOrganization =
				organizationPersistence.fetchByPrimaryKey(
					parentOrganizationId);

			if (parentOrganization == null) {
				throw new OrganizationParentException(
					"Organization " + parentOrganizationId + " doesn't exist");
			}

			String[] childrenTypes = OrganizationImpl.getChildrenTypes(
				parentOrganization.getType());

			if (childrenTypes.length == 0) {
				throw new OrganizationParentException(
					"Organization of type " + type + " cannot have children");
			}

			if ((companyId != parentOrganization.getCompanyId()) ||
				(parentOrganizationId == organizationId)) {

				throw new OrganizationParentException();
			}

			if (!ArrayUtil.contains(childrenTypes, type)) {
				throw new OrganizationParentException(
					"Type " + type + " not allowed as child of " +
						parentOrganization.getType());
			}
		}

		if ((organizationId > 0) &&
			(parentOrganizationId !=
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID)) {

			// Prevent circular organizational references

			if (isParentOrganization(organizationId, parentOrganizationId)) {
				throw new OrganizationParentException();
			}
		}

		if (Validator.isNull(name)) {
			throw new OrganizationNameException();
		}
		else {
			Organization organization = organizationPersistence.fetchByC_N(
				companyId, name);

			if ((organization != null) &&
				(organization.getName().equalsIgnoreCase(name))) {

				if ((organizationId <= 0) ||
					(organization.getOrganizationId() != organizationId)) {

					throw new DuplicateOrganizationException();
				}
			}
		}

		boolean countryRequired = GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.ORGANIZATIONS_COUNTRY_REQUIRED, new Filter(type)));

		if (countryRequired || (countryId > 0)) {
			countryPersistence.findByPrimaryKey(countryId);
		}

		listTypeService.validate(
			statusId, ListTypeConstants.ORGANIZATION_STATUS);
	}

	protected void validate(
			long companyId, long parentOrganizationId, String name, String type,
			long countryId, int statusId)
		throws PortalException, SystemException {

		validate(
			companyId, 0, parentOrganizationId, name, type, countryId,
			statusId);
	}

}