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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.OrganizationFinder;
import com.liferay.portal.service.persistence.OrganizationUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.AddressLocalServiceUtil;
import com.liferay.portal.service.spring.CountryServiceUtil;
import com.liferay.portal.service.spring.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.ListTypeServiceUtil;
import com.liferay.portal.service.spring.OrganizationLocalService;
import com.liferay.portal.service.spring.PhoneLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.service.spring.WebsiteLocalServiceUtil;
import com.liferay.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * <a href="OrganizationLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrganizationLocalServiceImpl implements OrganizationLocalService {

	public boolean addGroupOrganizations(
			String groupId, String[] organizationIds)
		throws PortalException, SystemException {

		return GroupUtil.addOrganizations(groupId, organizationIds);
	}

	public Organization addOrganization(
			String userId, String parentOrganizationId, String name,
			String regionId, String countryId, String statusId,
			boolean location)
		throws PortalException, SystemException {

		// Organization

		User user = UserUtil.findByPrimaryKey(userId);
		parentOrganizationId = getParentOrganizationId(
			user.getCompanyId(), parentOrganizationId);

		validate(
			user.getCompanyId(), parentOrganizationId, name, countryId,
			statusId, location);

		String organizationId = Long.toString(CounterServiceUtil.increment(
			Organization.class.getName()));

		Organization organization = OrganizationUtil.create(organizationId);

		organization.setCompanyId(user.getCompanyId());
		organization.setParentOrganizationId(parentOrganizationId);
		organization.setName(name);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);

		OrganizationUtil.update(organization);

		// Group

		GroupLocalServiceUtil.addGroup(
			userId, Organization.class.getName(),
			organization.getPrimaryKey().toString(), null, null, null, null);

		// Resources

		addOrganizationResources(userId, organization);

		return organization;
	}

	public void addOrganizationResources(
			String userId, Organization organization)
		throws PortalException, SystemException {

		String name = Organization.class.getName();

		if (!organization.isRoot()) {
			name = "com.liferay.portal.model.Location";
		}

		ResourceLocalServiceUtil.addResources(
			organization.getCompanyId(), null, userId, name,
			organization.getPrimaryKey().toString(), false, false, false);
	}

	public void deleteOrganization(String organizationId)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationUtil.findByPrimaryKey(organizationId);

		deleteOrganization(organization);
	}

	public void deleteOrganization(Organization organization)
		throws PortalException, SystemException {

		if ((OrganizationUtil.containsUsers(
				organization.getOrganizationId())) ||
			(OrganizationUtil.countByC_P(
				organization.getCompanyId(),
				organization.getOrganizationId()) > 0)) {

			throw new RequiredOrganizationException();
		}

		// Addresses

		AddressLocalServiceUtil.deleteAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Email addresses

		EmailAddressLocalServiceUtil.deleteEmailAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Phone

		PhoneLocalServiceUtil.deletePhones(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Website

		WebsiteLocalServiceUtil.deleteWebsites(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Group

		Group group = organization.getGroup();

		if (group != null) {
			GroupLocalServiceUtil.deleteGroup(group.getGroupId());
		}

		// Resources

		String name = Organization.class.getName();

		if (!organization.isRoot()) {
			name = "com.liferay.portal.model.Location";
		}

		ResourceLocalServiceUtil.deleteResource(
			organization.getCompanyId(), name, Resource.TYPE_CLASS,
			Resource.SCOPE_INDIVIDUAL, organization.getPrimaryKey().toString());

		// Organization

		OrganizationUtil.remove(organization.getOrganizationId());
	}

	public Organization getOrganization(String organizationId)
		throws PortalException, SystemException {

		return OrganizationUtil.findByPrimaryKey(organizationId);
	}

	public List getGroupOrganizations(String groupId)
		throws PortalException, SystemException {

		return GroupUtil.getOrganizations(groupId);
	}

	public List getUserOrganizations(String userId)
		throws PortalException, SystemException {

		return UserUtil.getOrganizations(userId);
	}

	public boolean hasGroupOrganization(String groupId, String organizationId)
		throws PortalException, SystemException {

		return GroupUtil.containsOrganization(groupId, organizationId);
	}

	public List search(
			String companyId, String parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, String regionId, String countryId,
			Map params, boolean andOperator, int begin, int end)
		throws SystemException {

		return OrganizationFinder.findByC_PO_N_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationComparator, name,
			street, city, zip, regionId, countryId, params, andOperator, begin,
			end);
	}

	public int searchCount(
			String companyId, String parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, String regionId, String countryId,
			Map params, boolean andOperator)
		throws SystemException {

		return OrganizationFinder.countByC_PO_N_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationComparator, name,
			street, city, zip, regionId, countryId, params, andOperator);
	}

	public void setGroupOrganizations(String groupId, String[] organizationIds)
		throws PortalException, SystemException {

		GroupUtil.setOrganizations(groupId, organizationIds);
	}

	public boolean unsetGroupOrganizations(
			String groupId, String[] organizationIds)
		throws PortalException, SystemException {

		return GroupUtil.removeOrganizations(groupId, organizationIds);
	}

	public Organization updateOrganization(
			String companyId, String organizationId,
			String parentOrganizationId, String name, String regionId,
			String countryId, String statusId, boolean location)
		throws PortalException, SystemException {

		parentOrganizationId = getParentOrganizationId(
			companyId, parentOrganizationId);

		validate(
			companyId, organizationId, parentOrganizationId, name, countryId,
			statusId, location);

		Organization organization =
			OrganizationUtil.findByPrimaryKey(organizationId);

		organization.setParentOrganizationId(parentOrganizationId);
		organization.setName(name);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);

		OrganizationUtil.update(organization);

		return organization;
	}

	public Organization updateOrganization(
			String organizationId, String comments)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationUtil.findByPrimaryKey(organizationId);

		organization.setComments(comments);

		OrganizationUtil.update(organization);

		return organization;
	}

	protected String getParentOrganizationId(
			String companyId, String parentOrganizationId)
		throws PortalException, SystemException {

		if (!parentOrganizationId.equals(
				Organization.DEFAULT_PARENT_ORGANIZATION_ID)) {

			// Ensure parent organization exists and belongs to the proper
			// company

			try {
				Organization parentOrganization =
					OrganizationUtil.findByPrimaryKey(parentOrganizationId);

				if (!companyId.equals(parentOrganization.getCompanyId())) {
					parentOrganizationId =
						Organization.DEFAULT_PARENT_ORGANIZATION_ID;
				}
			}
			catch (NoSuchOrganizationException nsoe) {
				parentOrganizationId =
					Organization.DEFAULT_PARENT_ORGANIZATION_ID;
			}
		}

		return parentOrganizationId;
	}

	protected void validate(
			String companyId, String parentOrganizationId, String name,
			String countryId, String statusId, boolean location)
		throws PortalException, SystemException {

		validate(
			companyId, null, parentOrganizationId, name, countryId, statusId,
			location);
	}

	protected void validate(
			String companyId, String organizationId,
			String parentOrganizationId, String name, String countryId,
			String statusId, boolean location)
		throws PortalException, SystemException {

		if (location) {
			try {
				Organization organization =
					OrganizationUtil.findByPrimaryKey(parentOrganizationId);

				if (!companyId.equals(organization.getCompanyId())) {
					throw new OrganizationParentException();
				}
			}
			catch (NoSuchOrganizationException nsoe) {
				throw new OrganizationParentException();
			}

		}

		if (Validator.isNull(name)) {
			throw new OrganizationNameException();
		}
		else {
			try {
				Organization organization =
					OrganizationUtil.findByC_N(companyId, name);

				if (organization.getName().equalsIgnoreCase(name)) {
					if ((organizationId == null) ||
						(!organization.getOrganizationId().equals(
								organizationId))) {

						throw new DuplicateOrganizationException();
					}
				}
			}
			catch (NoSuchOrganizationException nsoe) {
			}
		}

		Country country = CountryServiceUtil.getCountry(countryId);

		ListTypeServiceUtil.validate(statusId, ListType.ORGANIZATION_STATUS);
	}

}