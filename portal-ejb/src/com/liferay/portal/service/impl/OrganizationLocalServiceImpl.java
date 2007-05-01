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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ListTypeImpl;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portal.service.base.OrganizationLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.OrganizationFinder;
import com.liferay.portal.service.persistence.OrganizationUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.util.Validator;

import java.rmi.RemoteException;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="OrganizationLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrganizationLocalServiceImpl
	extends OrganizationLocalServiceBaseImpl {

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupUtil.addOrganizations(groupId, organizationIds);
	}

	public Organization addOrganization(
			long userId, long parentOrganizationId, String name, long regionId,
			long countryId, int statusId, boolean location)
		throws PortalException, SystemException {

		// Organization

		User user = UserUtil.findByPrimaryKey(userId);
		parentOrganizationId = getParentOrganizationId(
			user.getCompanyId(), parentOrganizationId);

		validate(
			user.getCompanyId(), parentOrganizationId, name, countryId,
			statusId, location);

		long organizationId = CounterLocalServiceUtil.increment();

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
			String.valueOf(organization.getOrganizationId()), null, null, null,
			null, true);

		// Resources

		addOrganizationResources(userId, organization);

		return organization;
	}

	public void addOrganizationResources(long userId, Organization organization)
		throws PortalException, SystemException {

		String name = Organization.class.getName();

		if (!organization.isRoot()) {
			name = "com.liferay.portal.model.Location";
		}

		ResourceLocalServiceUtil.addResources(
			organization.getCompanyId(), 0, userId, name,
			organization.getOrganizationId(), false, false, false);
	}

	public void addPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		PasswordPolicyRelLocalServiceUtil.addPasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	public void deleteOrganization(long organizationId)
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
			String.valueOf(organization.getOrganizationId()));

		// Email addresses

		EmailAddressLocalServiceUtil.deleteEmailAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			String.valueOf(organization.getOrganizationId()));

		// Password policy relation

		PasswordPolicyRelLocalServiceUtil.deletePasswordPolicyRel(
			Organization.class.getName(),
			String.valueOf(organization.getOrganizationId()));

		// Phone

		PhoneLocalServiceUtil.deletePhones(
			organization.getCompanyId(), Organization.class.getName(),
			String.valueOf(organization.getOrganizationId()));

		// Website

		WebsiteLocalServiceUtil.deleteWebsites(
			organization.getCompanyId(), Organization.class.getName(),
			String.valueOf(organization.getOrganizationId()));

		// Group

		Group group = organization.getGroup();

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		// Resources

		String name = Organization.class.getName();

		if (!organization.isRoot()) {
			name = "com.liferay.portal.model.Location";
		}

		ResourceLocalServiceUtil.deleteResource(
			organization.getCompanyId(), name, ResourceImpl.SCOPE_INDIVIDUAL,
			organization.getOrganizationId());

		// Organization

		OrganizationUtil.remove(organization.getOrganizationId());
	}

	public List getGroupOrganizations(long groupId)
		throws PortalException, SystemException {

		return GroupUtil.getOrganizations(groupId);
	}

	public Organization getOrganization(long organizationId)
		throws PortalException, SystemException {

		return OrganizationUtil.findByPrimaryKey(organizationId);
	}

	public long getOrganizationId(long companyId, String name)
		throws PortalException, SystemException {

		try {
			Organization organization = OrganizationUtil.findByC_N(
				companyId, name);

			return organization.getOrganizationId();
		}
		catch (NoSuchOrganizationException nsoge) {
			return 0;
		}
	}

	public List getUserOrganizations(long userId)
		throws PortalException, SystemException {

		return UserUtil.getOrganizations(userId);
	}

	public boolean hasGroupOrganization(long groupId, long organizationId)
		throws PortalException, SystemException {

		return GroupUtil.containsOrganization(groupId, organizationId);
	}

	public boolean hasPasswordPolicyOrganization(
			long passwordPolicyId, long organizationId)
		throws PortalException, SystemException {

		return PasswordPolicyRelLocalServiceUtil.hasPasswordPolicyRel(
			passwordPolicyId, Organization.class.getName(),
			String.valueOf(organizationId));
	}

	public List search(
			long companyId, long parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, Long regionId, Long countryId,
			LinkedHashMap params, boolean andOperator, int begin, int end)
		throws SystemException {

		return OrganizationFinder.findByC_PO_N_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationComparator, name,
			street, city, zip, regionId, countryId, params, andOperator, begin,
			end);
	}

	public int searchCount(
			long companyId, long parentOrganizationId,
			String parentOrganizationComparator, String name, String street,
			String city, String zip, Long regionId, Long countryId,
			LinkedHashMap params, boolean andOperator)
		throws SystemException {

		return OrganizationFinder.countByC_PO_N_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationComparator, name,
			street, city, zip, regionId, countryId, params, andOperator);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupUtil.setOrganizations(groupId, organizationIds);
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		GroupUtil.removeOrganizations(groupId, organizationIds);
	}

	public void unsetPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		PasswordPolicyRelLocalServiceUtil.deletePasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	public Organization updateOrganization(
			long companyId, long organizationId, long parentOrganizationId,
			String name, long regionId, long countryId, int statusId,
			boolean location)
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

	public Organization updateOrganization(long organizationId, String comments)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationUtil.findByPrimaryKey(organizationId);

		organization.setComments(comments);

		OrganizationUtil.update(organization);

		return organization;
	}

	protected long getParentOrganizationId(
			long companyId, long parentOrganizationId)
		throws PortalException, SystemException {

		if (parentOrganizationId !=
				OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID) {

			// Ensure parent organization exists and belongs to the proper
			// company

			try {
				Organization parentOrganization =
					OrganizationUtil.findByPrimaryKey(parentOrganizationId);

				if (companyId != parentOrganization.getCompanyId()) {
					parentOrganizationId =
						OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID;
				}
			}
			catch (NoSuchOrganizationException nsoe) {
				parentOrganizationId =
					OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID;
			}
		}

		return parentOrganizationId;
	}

	protected void validate(
			long companyId, long parentOrganizationId, String name,
			long countryId, int statusId, boolean location)
		throws PortalException, SystemException {

		validate(
			companyId, 0, parentOrganizationId, name, countryId, statusId,
			location);
	}

	protected void validate(
			long companyId, long organizationId, long parentOrganizationId,
			String name, long countryId, int statusId, boolean location)
		throws PortalException, SystemException {

		if (location) {
			try {
				Organization organization =
					OrganizationUtil.findByPrimaryKey(parentOrganizationId);

				if (companyId != organization.getCompanyId()) {
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
					if ((organizationId <= 0) ||
						(organization.getOrganizationId() != organizationId)) {

						throw new DuplicateOrganizationException();
					}
				}
			}
			catch (NoSuchOrganizationException nsoe) {
			}
		}

		try {
			CountryServiceUtil.getCountry(countryId);

			ListTypeServiceUtil.validate(
				statusId, ListTypeImpl.ORGANIZATION_STATUS);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

}