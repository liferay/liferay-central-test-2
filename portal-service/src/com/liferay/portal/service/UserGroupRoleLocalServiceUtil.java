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
 * <a href="UserGroupRoleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.UserGroupRoleLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.UserGroupRoleLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserGroupRoleLocalService
 * @see com.liferay.portal.service.UserGroupRoleLocalServiceFactory
 *
 */
public class UserGroupRoleLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getAccountPersistence();
	}

	public static void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setAccountPersistence(accountPersistence);
	}

	public static com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getAddressPersistence();
	}

	public static void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setAddressPersistence(addressPersistence);
	}

	public static com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getClassNamePersistence();
	}

	public static void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setClassNamePersistence(classNamePersistence);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.ContactPersistence getContactPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getContactPersistence();
	}

	public static void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setContactPersistence(contactPersistence);
	}

	public static com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getCountryPersistence();
	}

	public static void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setCountryPersistence(countryPersistence);
	}

	public static com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getEmailAddressPersistence();
	}

	public static void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setEmailAddressPersistence(emailAddressPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getGroupPersistence();
	}

	public static void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setGroupPersistence(groupPersistence);
	}

	public static com.liferay.portal.service.persistence.ImagePersistence getImagePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getImagePersistence();
	}

	public static void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setImagePersistence(imagePersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getLayoutPersistence();
	}

	public static void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setLayoutPersistence(layoutPersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getLayoutSetPersistence();
	}

	public static void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setLayoutSetPersistence(layoutSetPersistence);
	}

	public static com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getListTypePersistence();
	}

	public static void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setListTypePersistence(listTypePersistence);
	}

	public static com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getMembershipRequestPersistence();
	}

	public static void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setMembershipRequestPersistence(membershipRequestPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getOrganizationPersistence();
	}

	public static void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setOrganizationPersistence(organizationPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getOrgGroupPermissionPersistence();
	}

	public static void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setOrgGroupPermissionPersistence(orgGroupPermissionPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getOrgGroupRolePersistence();
	}

	public static void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setOrgGroupRolePersistence(orgGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getOrgLaborPersistence();
	}

	public static void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setOrgLaborPersistence(orgLaborPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPasswordPolicyPersistence();
	}

	public static void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPasswordPolicyPersistence(passwordPolicyPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPasswordPolicyRelPersistence();
	}

	public static void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPasswordPolicyRelPersistence(passwordPolicyRelPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPasswordTrackerPersistence();
	}

	public static void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPasswordTrackerPersistence(passwordTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPermissionPersistence();
	}

	public static void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPermissionPersistence(permissionPersistence);
	}

	public static com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPhonePersistence();
	}

	public static void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPhonePersistence(phonePersistence);
	}

	public static com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPluginSettingPersistence();
	}

	public static void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPluginSettingPersistence(pluginSettingPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPortletPersistence();
	}

	public static void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPortletPersistence(portletPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getRegionPersistence();
	}

	public static void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setRegionPersistence(regionPersistence);
	}

	public static com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getReleasePersistence();
	}

	public static void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setReleasePersistence(releasePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getResourceCodePersistence();
	}

	public static void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setResourceCodePersistence(resourceCodePersistence);
	}

	public static com.liferay.portal.service.persistence.RolePersistence getRolePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getRolePersistence();
	}

	public static void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setRolePersistence(rolePersistence);
	}

	public static com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getServiceComponentPersistence();
	}

	public static void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setServiceComponentPersistence(serviceComponentPersistence);
	}

	public static com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getSubscriptionPersistence();
	}

	public static void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setSubscriptionPersistence(subscriptionPersistence);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserGroupPersistence();
	}

	public static void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setUserGroupPersistence(userGroupPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserGroupRolePersistence();
	}

	public static void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setUserGroupRolePersistence(userGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserIdMapperPersistence();
	}

	public static void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setUserIdMapperPersistence(userIdMapperPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserTrackerPersistence();
	}

	public static void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setUserTrackerPersistence(userTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserTrackerPathPersistence();
	}

	public static void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setUserTrackerPathPersistence(userTrackerPathPersistence);
	}

	public static com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence() {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getWebsitePersistence();
	}

	public static void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence) {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.setWebsitePersistence(websitePersistence);
	}

	public static void addUserGroupRoles(long userId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, roleIds);
	}

	public static void addUserGroupRoles(long[] userIds, long groupId,
		long roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.addUserGroupRoles(userIds, groupId, roleId);
	}

	public static void deleteUserGroupRoles(long userId, long groupId,
		long[] roleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.deleteUserGroupRoles(userId, groupId, roleIds);
	}

	public static void deleteUserGroupRoles(long[] userIds, long groupId,
		long roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.deleteUserGroupRoles(userIds, groupId, roleId);
	}

	public static void deleteUserGroupRoles(long[] userIds, long groupId)
		throws com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.deleteUserGroupRoles(userIds, groupId);
	}

	public static void deleteUserGroupRolesByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.deleteUserGroupRolesByGroupId(groupId);
	}

	public static void deleteUserGroupRolesByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.deleteUserGroupRolesByRoleId(roleId);
	}

	public static void deleteUserGroupRolesByUserId(long userId)
		throws com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();
		userGroupRoleLocalService.deleteUserGroupRolesByUserId(userId);
	}

	public static java.util.List getUserGroupRoles(long userId, long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserGroupRoles(userId, groupId);
	}

	public static java.util.List getUserGroupRolesByGroupAndRole(long groupId,
		long roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(groupId,
			roleId);
	}

	public static boolean hasUserGroupRole(long userId, long groupId,
		long roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupRoleLocalService userGroupRoleLocalService = UserGroupRoleLocalServiceFactory.getService();

		return userGroupRoleLocalService.hasUserGroupRole(userId, groupId,
			roleId);
	}
}