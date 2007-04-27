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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.OrganizationLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="OrganizationLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.OrganizationLocalService
 * @see com.liferay.portal.service.OrganizationLocalServiceUtil
 * @see com.liferay.portal.service.ejb.OrganizationLocalServiceEJB
 * @see com.liferay.portal.service.ejb.OrganizationLocalServiceHome
 * @see com.liferay.portal.service.impl.OrganizationLocalServiceImpl
 *
 */
public class OrganizationLocalServiceEJBImpl implements OrganizationLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void addGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl().addGroupOrganizations(groupId,
			organizationIds);
	}

	public com.liferay.portal.model.Organization addOrganization(long userId,
		java.lang.String parentOrganizationId, java.lang.String name,
		java.lang.String regionId, java.lang.String countryId, int statusId,
		boolean location)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().addOrganization(userId,
			parentOrganizationId, name, regionId, countryId, statusId, location);
	}

	public void addOrganizationResources(long userId,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl().addOrganizationResources(userId,
			organization);
	}

	public void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl()
									   .addPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public void deleteOrganization(java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl().deleteOrganization(organizationId);
	}

	public void deleteOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl().deleteOrganization(organization);
	}

	public java.util.List getGroupOrganizations(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl()
											  .getGroupOrganizations(groupId);
	}

	public com.liferay.portal.model.Organization getOrganization(
		java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().getOrganization(organizationId);
	}

	public java.lang.String getOrganizationId(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().getOrganizationId(companyId,
			name);
	}

	public java.util.List getUserOrganizations(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().getUserOrganizations(userId);
	}

	public boolean hasGroupOrganization(long groupId,
		java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().hasGroupOrganization(groupId,
			organizationId);
	}

	public boolean hasPasswordPolicyOrganization(long passwordPolicyId,
		java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl()
											  .hasPasswordPolicyOrganization(passwordPolicyId,
			organizationId);
	}

	public java.util.List search(long companyId,
		java.lang.String parentOrganizationId,
		java.lang.String parentOrganizationComparator, java.lang.String name,
		java.lang.String street, java.lang.String city, java.lang.String zip,
		java.lang.String regionId, java.lang.String countryId,
		java.util.LinkedHashMap params, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().search(companyId,
			parentOrganizationId, parentOrganizationComparator, name, street,
			city, zip, regionId, countryId, params, andOperator, begin, end);
	}

	public int searchCount(long companyId,
		java.lang.String parentOrganizationId,
		java.lang.String parentOrganizationComparator, java.lang.String name,
		java.lang.String street, java.lang.String city, java.lang.String zip,
		java.lang.String regionId, java.lang.String countryId,
		java.util.LinkedHashMap params, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().searchCount(companyId,
			parentOrganizationId, parentOrganizationComparator, name, street,
			city, zip, regionId, countryId, params, andOperator);
	}

	public void setGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl().setGroupOrganizations(groupId,
			organizationIds);
	}

	public void unsetGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl().unsetGroupOrganizations(groupId,
			organizationIds);
	}

	public void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		OrganizationLocalServiceFactory.getTxImpl()
									   .unsetPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public com.liferay.portal.model.Organization updateOrganization(
		long companyId, java.lang.String organizationId,
		java.lang.String parentOrganizationId, java.lang.String name,
		java.lang.String regionId, java.lang.String countryId, int statusId,
		boolean location)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().updateOrganization(companyId,
			organizationId, parentOrganizationId, name, regionId, countryId,
			statusId, location);
	}

	public com.liferay.portal.model.Organization updateOrganization(
		java.lang.String organizationId, java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return OrganizationLocalServiceFactory.getTxImpl().updateOrganization(organizationId,
			comments);
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