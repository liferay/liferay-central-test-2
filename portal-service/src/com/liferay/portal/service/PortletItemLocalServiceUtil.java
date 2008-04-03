/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="PortletItemLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.PortletItemLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.PortletItemLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PortletItemLocalService
 * @see com.liferay.portal.service.PortletItemLocalServiceFactory
 *
 */
public class PortletItemLocalServiceUtil {
	public static com.liferay.portal.model.PortletItem addPortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.addPortletItem(portletItem);
	}

	public static void deletePortletItem(long portletItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.deletePortletItem(portletItemId);
	}

	public static void deletePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.deletePortletItem(portletItem);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.updatePortletItem(portletItem);
	}

	public static com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getAccountPersistence();
	}

	public static void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setAccountPersistence(accountPersistence);
	}

	public static com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getAddressPersistence();
	}

	public static void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setAddressPersistence(addressPersistence);
	}

	public static com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getClassNamePersistence();
	}

	public static void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setClassNamePersistence(classNamePersistence);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.ContactPersistence getContactPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getContactPersistence();
	}

	public static void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setContactPersistence(contactPersistence);
	}

	public static com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getCountryPersistence();
	}

	public static void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setCountryPersistence(countryPersistence);
	}

	public static com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getEmailAddressPersistence();
	}

	public static void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setEmailAddressPersistence(emailAddressPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getGroupPersistence();
	}

	public static void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setGroupPersistence(groupPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupFinder getGroupFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getGroupFinder();
	}

	public static void setGroupFinder(
		com.liferay.portal.service.persistence.GroupFinder groupFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setGroupFinder(groupFinder);
	}

	public static com.liferay.portal.service.persistence.ImagePersistence getImagePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getImagePersistence();
	}

	public static void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setImagePersistence(imagePersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getLayoutPersistence();
	}

	public static void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setLayoutPersistence(layoutPersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutFinder getLayoutFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getLayoutFinder();
	}

	public static void setLayoutFinder(
		com.liferay.portal.service.persistence.LayoutFinder layoutFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setLayoutFinder(layoutFinder);
	}

	public static com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getLayoutSetPersistence();
	}

	public static void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setLayoutSetPersistence(layoutSetPersistence);
	}

	public static com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getListTypePersistence();
	}

	public static void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setListTypePersistence(listTypePersistence);
	}

	public static com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getMembershipRequestPersistence();
	}

	public static void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setMembershipRequestPersistence(membershipRequestPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getOrganizationPersistence();
	}

	public static void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setOrganizationPersistence(organizationPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationFinder getOrganizationFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getOrganizationFinder();
	}

	public static void setOrganizationFinder(
		com.liferay.portal.service.persistence.OrganizationFinder organizationFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setOrganizationFinder(organizationFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getOrgGroupPermissionPersistence();
	}

	public static void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setOrgGroupPermissionPersistence(orgGroupPermissionPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionFinder getOrgGroupPermissionFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getOrgGroupPermissionFinder();
	}

	public static void setOrgGroupPermissionFinder(
		com.liferay.portal.service.persistence.OrgGroupPermissionFinder orgGroupPermissionFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setOrgGroupPermissionFinder(orgGroupPermissionFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getOrgGroupRolePersistence();
	}

	public static void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setOrgGroupRolePersistence(orgGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getOrgLaborPersistence();
	}

	public static void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setOrgLaborPersistence(orgLaborPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPasswordPolicyPersistence();
	}

	public static void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPasswordPolicyPersistence(passwordPolicyPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyFinder getPasswordPolicyFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPasswordPolicyFinder();
	}

	public static void setPasswordPolicyFinder(
		com.liferay.portal.service.persistence.PasswordPolicyFinder passwordPolicyFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPasswordPolicyFinder(passwordPolicyFinder);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPasswordPolicyRelPersistence();
	}

	public static void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPasswordPolicyRelPersistence(passwordPolicyRelPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPasswordTrackerPersistence();
	}

	public static void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPasswordTrackerPersistence(passwordTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPermissionPersistence();
	}

	public static void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPermissionPersistence(permissionPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionFinder getPermissionFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPermissionFinder();
	}

	public static void setPermissionFinder(
		com.liferay.portal.service.persistence.PermissionFinder permissionFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPermissionFinder(permissionFinder);
	}

	public static com.liferay.portal.service.persistence.PermissionUserFinder getPermissionUserFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPermissionUserFinder();
	}

	public static void setPermissionUserFinder(
		com.liferay.portal.service.persistence.PermissionUserFinder permissionUserFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPermissionUserFinder(permissionUserFinder);
	}

	public static com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPhonePersistence();
	}

	public static void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPhonePersistence(phonePersistence);
	}

	public static com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPluginSettingPersistence();
	}

	public static void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPluginSettingPersistence(pluginSettingPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletPersistence();
	}

	public static void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPortletPersistence(portletPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesFinder getPortletPreferencesFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletPreferencesFinder();
	}

	public static void setPortletPreferencesFinder(
		com.liferay.portal.service.persistence.PortletPreferencesFinder portletPreferencesFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPortletPreferencesFinder(portletPreferencesFinder);
	}

	public static com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getRegionPersistence();
	}

	public static void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setRegionPersistence(regionPersistence);
	}

	public static com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getReleasePersistence();
	}

	public static void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setReleasePersistence(releasePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getResourceCodePersistence();
	}

	public static void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setResourceCodePersistence(resourceCodePersistence);
	}

	public static com.liferay.portal.service.persistence.RolePersistence getRolePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getRolePersistence();
	}

	public static void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setRolePersistence(rolePersistence);
	}

	public static com.liferay.portal.service.persistence.RoleFinder getRoleFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getRoleFinder();
	}

	public static void setRoleFinder(
		com.liferay.portal.service.persistence.RoleFinder roleFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setRoleFinder(roleFinder);
	}

	public static com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getServiceComponentPersistence();
	}

	public static void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setServiceComponentPersistence(serviceComponentPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletItemPersistence getPortletItemPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItemPersistence();
	}

	public static void setPortletItemPersistence(
		com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setPortletItemPersistence(portletItemPersistence);
	}

	public static com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getSubscriptionPersistence();
	}

	public static void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setSubscriptionPersistence(subscriptionPersistence);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserFinder(userFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserGroupPersistence();
	}

	public static void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserGroupPersistence(userGroupPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupFinder getUserGroupFinder() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserGroupFinder();
	}

	public static void setUserGroupFinder(
		com.liferay.portal.service.persistence.UserGroupFinder userGroupFinder) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserGroupFinder(userGroupFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserGroupRolePersistence();
	}

	public static void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserGroupRolePersistence(userGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserIdMapperPersistence();
	}

	public static void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserIdMapperPersistence(userIdMapperPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserTrackerPersistence();
	}

	public static void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserTrackerPersistence(userTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getUserTrackerPathPersistence();
	}

	public static void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setUserTrackerPathPersistence(userTrackerPathPersistence);
	}

	public static com.liferay.portal.service.persistence.WebDAVPropsPersistence getWebDAVPropsPersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getWebDAVPropsPersistence();
	}

	public static void setWebDAVPropsPersistence(
		com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setWebDAVPropsPersistence(webDAVPropsPersistence);
	}

	public static com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getWebsitePersistence();
	}

	public static void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence) {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.setWebsitePersistence(websitePersistence);
	}

	public static void afterPropertiesSet() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.afterPropertiesSet();
	}

	public static com.liferay.portal.model.PortletItem addPortletItem(
		long userId, long groupId, java.lang.String name,
		java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.addPortletItem(userId, groupId, name,
			portletId, className);
	}

	public static com.liferay.portal.model.PortletItem getPortletItem(
		long portletItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItem(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem getPortletItem(
		long groupId, java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItem(groupId, name, portletId,
			className);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String className)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItems(groupId, className);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItems(groupId, portletId,
			className);
	}

	public static com.liferay.portal.model.PortletItem updatePortletItem(
		long userId, long groupId, java.lang.String name,
		java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.updatePortletItem(userId, groupId, name,
			portletId, className);
	}
}