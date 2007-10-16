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

package com.liferay.portal.service;

/**
 * <a href="ContactLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.ContactLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ContactLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ContactLocalService
 * @see com.liferay.portal.service.ContactLocalServiceFactory
 *
 */
public class ContactLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getAccountPersistence();
	}

	public static void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setAccountPersistence(accountPersistence);
	}

	public static com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getAddressPersistence();
	}

	public static void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setAddressPersistence(addressPersistence);
	}

	public static com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getClassNamePersistence();
	}

	public static void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setClassNamePersistence(classNamePersistence);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.ContactPersistence getContactPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getContactPersistence();
	}

	public static void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setContactPersistence(contactPersistence);
	}

	public static com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getCountryPersistence();
	}

	public static void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setCountryPersistence(countryPersistence);
	}

	public static com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getEmailAddressPersistence();
	}

	public static void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setEmailAddressPersistence(emailAddressPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getGroupPersistence();
	}

	public static void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setGroupPersistence(groupPersistence);
	}

	public static com.liferay.portal.service.persistence.ImagePersistence getImagePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getImagePersistence();
	}

	public static void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setImagePersistence(imagePersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getLayoutPersistence();
	}

	public static void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setLayoutPersistence(layoutPersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getLayoutSetPersistence();
	}

	public static void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setLayoutSetPersistence(layoutSetPersistence);
	}

	public static com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getListTypePersistence();
	}

	public static void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setListTypePersistence(listTypePersistence);
	}

	public static com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getMembershipRequestPersistence();
	}

	public static void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setMembershipRequestPersistence(membershipRequestPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getOrganizationPersistence();
	}

	public static void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setOrganizationPersistence(organizationPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getOrgGroupPermissionPersistence();
	}

	public static void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setOrgGroupPermissionPersistence(orgGroupPermissionPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getOrgGroupRolePersistence();
	}

	public static void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setOrgGroupRolePersistence(orgGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getOrgLaborPersistence();
	}

	public static void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setOrgLaborPersistence(orgLaborPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPasswordPolicyPersistence();
	}

	public static void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPasswordPolicyPersistence(passwordPolicyPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPasswordPolicyRelPersistence();
	}

	public static void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPasswordPolicyRelPersistence(passwordPolicyRelPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPasswordTrackerPersistence();
	}

	public static void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPasswordTrackerPersistence(passwordTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPermissionPersistence();
	}

	public static void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPermissionPersistence(permissionPersistence);
	}

	public static com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPhonePersistence();
	}

	public static void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPhonePersistence(phonePersistence);
	}

	public static com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPluginSettingPersistence();
	}

	public static void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPluginSettingPersistence(pluginSettingPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPortletPersistence();
	}

	public static void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPortletPersistence(portletPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getRegionPersistence();
	}

	public static void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setRegionPersistence(regionPersistence);
	}

	public static com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getReleasePersistence();
	}

	public static void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setReleasePersistence(releasePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getResourceCodePersistence();
	}

	public static void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setResourceCodePersistence(resourceCodePersistence);
	}

	public static com.liferay.portal.service.persistence.RolePersistence getRolePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getRolePersistence();
	}

	public static void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setRolePersistence(rolePersistence);
	}

	public static com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getServiceComponentPersistence();
	}

	public static void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setServiceComponentPersistence(serviceComponentPersistence);
	}

	public static com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getSubscriptionPersistence();
	}

	public static void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setSubscriptionPersistence(subscriptionPersistence);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getUserGroupPersistence();
	}

	public static void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setUserGroupPersistence(userGroupPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getUserGroupRolePersistence();
	}

	public static void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setUserGroupRolePersistence(userGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getUserIdMapperPersistence();
	}

	public static void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setUserIdMapperPersistence(userIdMapperPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getUserTrackerPersistence();
	}

	public static void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setUserTrackerPersistence(userTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getUserTrackerPathPersistence();
	}

	public static void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setUserTrackerPathPersistence(userTrackerPathPersistence);
	}

	public static com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getWebsitePersistence();
	}

	public static void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence) {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.setWebsitePersistence(websitePersistence);
	}

	public static void afterPropertiesSet() {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.afterPropertiesSet();
	}

	public static com.liferay.portal.model.Contact getContact(long contactId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();

		return contactLocalService.getContact(contactId);
	}

	public static void deleteContact(long contactId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.deleteContact(contactId);
	}

	public static void deleteContact(com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ContactLocalService contactLocalService = ContactLocalServiceFactory.getService();
		contactLocalService.deleteContact(contact);
	}
}