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
 * <a href="ExpandoValueLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ExpandoValueLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ExpandoValueLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ExpandoValueLocalService
 * @see com.liferay.portal.service.ExpandoValueLocalServiceFactory
 *
 */
public class ExpandoValueLocalServiceUtil {
	public static com.liferay.portal.model.ExpandoValue addExpandoValue(
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.addExpandoValue(expandoValue);
	}

	public static void deleteExpandoValue(long valueId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteExpandoValue(valueId);
	}

	public static void deleteExpandoValue(
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteExpandoValue(expandoValue);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portal.model.ExpandoValue updateExpandoValue(
		com.liferay.portal.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.updateExpandoValue(expandoValue);
	}

	public static com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getAccountPersistence();
	}

	public static void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setAccountPersistence(accountPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerPersistence getActivityTrackerPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getActivityTrackerPersistence();
	}

	public static void setActivityTrackerPersistence(
		com.liferay.portal.service.persistence.ActivityTrackerPersistence activityTrackerPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setActivityTrackerPersistence(activityTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerFinder getActivityTrackerFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getActivityTrackerFinder();
	}

	public static void setActivityTrackerFinder(
		com.liferay.portal.service.persistence.ActivityTrackerFinder activityTrackerFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setActivityTrackerFinder(activityTrackerFinder);
	}

	public static com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getAddressPersistence();
	}

	public static void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setAddressPersistence(addressPersistence);
	}

	public static com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getClassNamePersistence();
	}

	public static void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setClassNamePersistence(classNamePersistence);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.ContactPersistence getContactPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getContactPersistence();
	}

	public static void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setContactPersistence(contactPersistence);
	}

	public static com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getCountryPersistence();
	}

	public static void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setCountryPersistence(countryPersistence);
	}

	public static com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getEmailAddressPersistence();
	}

	public static void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setEmailAddressPersistence(emailAddressPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoColumnPersistence getExpandoColumnPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getExpandoColumnPersistence();
	}

	public static void setExpandoColumnPersistence(
		com.liferay.portal.service.persistence.ExpandoColumnPersistence expandoColumnPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setExpandoColumnPersistence(expandoColumnPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTablePersistence getExpandoTablePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getExpandoTablePersistence();
	}

	public static void setExpandoTablePersistence(
		com.liferay.portal.service.persistence.ExpandoTablePersistence expandoTablePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setExpandoTablePersistence(expandoTablePersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTableRowPersistence getExpandoTableRowPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getExpandoTableRowPersistence();
	}

	public static void setExpandoTableRowPersistence(
		com.liferay.portal.service.persistence.ExpandoTableRowPersistence expandoTableRowPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setExpandoTableRowPersistence(expandoTableRowPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoValuePersistence getExpandoValuePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getExpandoValuePersistence();
	}

	public static void setExpandoValuePersistence(
		com.liferay.portal.service.persistence.ExpandoValuePersistence expandoValuePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setExpandoValuePersistence(expandoValuePersistence);
	}

	public static com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getGroupPersistence();
	}

	public static void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setGroupPersistence(groupPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupFinder getGroupFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getGroupFinder();
	}

	public static void setGroupFinder(
		com.liferay.portal.service.persistence.GroupFinder groupFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setGroupFinder(groupFinder);
	}

	public static com.liferay.portal.service.persistence.ImagePersistence getImagePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getImagePersistence();
	}

	public static void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setImagePersistence(imagePersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getLayoutPersistence();
	}

	public static void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setLayoutPersistence(layoutPersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutFinder getLayoutFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getLayoutFinder();
	}

	public static void setLayoutFinder(
		com.liferay.portal.service.persistence.LayoutFinder layoutFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setLayoutFinder(layoutFinder);
	}

	public static com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getLayoutSetPersistence();
	}

	public static void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setLayoutSetPersistence(layoutSetPersistence);
	}

	public static com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getListTypePersistence();
	}

	public static void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setListTypePersistence(listTypePersistence);
	}

	public static com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getMembershipRequestPersistence();
	}

	public static void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setMembershipRequestPersistence(membershipRequestPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getOrganizationPersistence();
	}

	public static void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setOrganizationPersistence(organizationPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationFinder getOrganizationFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getOrganizationFinder();
	}

	public static void setOrganizationFinder(
		com.liferay.portal.service.persistence.OrganizationFinder organizationFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setOrganizationFinder(organizationFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getOrgGroupPermissionPersistence();
	}

	public static void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setOrgGroupPermissionPersistence(orgGroupPermissionPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionFinder getOrgGroupPermissionFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getOrgGroupPermissionFinder();
	}

	public static void setOrgGroupPermissionFinder(
		com.liferay.portal.service.persistence.OrgGroupPermissionFinder orgGroupPermissionFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setOrgGroupPermissionFinder(orgGroupPermissionFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getOrgGroupRolePersistence();
	}

	public static void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setOrgGroupRolePersistence(orgGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getOrgLaborPersistence();
	}

	public static void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setOrgLaborPersistence(orgLaborPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPasswordPolicyPersistence();
	}

	public static void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPasswordPolicyPersistence(passwordPolicyPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyFinder getPasswordPolicyFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPasswordPolicyFinder();
	}

	public static void setPasswordPolicyFinder(
		com.liferay.portal.service.persistence.PasswordPolicyFinder passwordPolicyFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPasswordPolicyFinder(passwordPolicyFinder);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPasswordPolicyRelPersistence();
	}

	public static void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPasswordPolicyRelPersistence(passwordPolicyRelPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPasswordTrackerPersistence();
	}

	public static void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPasswordTrackerPersistence(passwordTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPermissionPersistence();
	}

	public static void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPermissionPersistence(permissionPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionFinder getPermissionFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPermissionFinder();
	}

	public static void setPermissionFinder(
		com.liferay.portal.service.persistence.PermissionFinder permissionFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPermissionFinder(permissionFinder);
	}

	public static com.liferay.portal.service.persistence.PermissionUserFinder getPermissionUserFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPermissionUserFinder();
	}

	public static void setPermissionUserFinder(
		com.liferay.portal.service.persistence.PermissionUserFinder permissionUserFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPermissionUserFinder(permissionUserFinder);
	}

	public static com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPhonePersistence();
	}

	public static void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPhonePersistence(phonePersistence);
	}

	public static com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPluginSettingPersistence();
	}

	public static void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPluginSettingPersistence(pluginSettingPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPortletPersistence();
	}

	public static void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPortletPersistence(portletPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesFinder getPortletPreferencesFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPortletPreferencesFinder();
	}

	public static void setPortletPreferencesFinder(
		com.liferay.portal.service.persistence.PortletPreferencesFinder portletPreferencesFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPortletPreferencesFinder(portletPreferencesFinder);
	}

	public static com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getRegionPersistence();
	}

	public static void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setRegionPersistence(regionPersistence);
	}

	public static com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getReleasePersistence();
	}

	public static void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setReleasePersistence(releasePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getResourceCodePersistence();
	}

	public static void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setResourceCodePersistence(resourceCodePersistence);
	}

	public static com.liferay.portal.service.persistence.RolePersistence getRolePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getRolePersistence();
	}

	public static void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setRolePersistence(rolePersistence);
	}

	public static com.liferay.portal.service.persistence.RoleFinder getRoleFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getRoleFinder();
	}

	public static void setRoleFinder(
		com.liferay.portal.service.persistence.RoleFinder roleFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setRoleFinder(roleFinder);
	}

	public static com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getServiceComponentPersistence();
	}

	public static void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setServiceComponentPersistence(serviceComponentPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletItemPersistence getPortletItemPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getPortletItemPersistence();
	}

	public static void setPortletItemPersistence(
		com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setPortletItemPersistence(portletItemPersistence);
	}

	public static com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getSubscriptionPersistence();
	}

	public static void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setSubscriptionPersistence(subscriptionPersistence);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserFinder(userFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserGroupPersistence();
	}

	public static void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserGroupPersistence(userGroupPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupFinder getUserGroupFinder() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserGroupFinder();
	}

	public static void setUserGroupFinder(
		com.liferay.portal.service.persistence.UserGroupFinder userGroupFinder) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserGroupFinder(userGroupFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserGroupRolePersistence();
	}

	public static void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserGroupRolePersistence(userGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserIdMapperPersistence();
	}

	public static void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserIdMapperPersistence(userIdMapperPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserTrackerPersistence();
	}

	public static void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserTrackerPersistence(userTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getUserTrackerPathPersistence();
	}

	public static void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setUserTrackerPathPersistence(userTrackerPathPersistence);
	}

	public static com.liferay.portal.service.persistence.WebDAVPropsPersistence getWebDAVPropsPersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getWebDAVPropsPersistence();
	}

	public static void setWebDAVPropsPersistence(
		com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setWebDAVPropsPersistence(webDAVPropsPersistence);
	}

	public static com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getWebsitePersistence();
	}

	public static void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence) {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.setWebsitePersistence(websitePersistence);
	}

	public static void afterPropertiesSet() {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.afterPropertiesSet();
	}

	public static com.liferay.portal.model.ExpandoValue addValue(long classPK,
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.addValue(classPK, columnId);
	}

	public static com.liferay.portal.model.ExpandoValue addValue(long classPK,
		long columnId, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.addValue(classPK, columnId, value);
	}

	public static void deleteValue(long valueId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteValue(valueId);
	}

	public static void deleteValue(long classPK, long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteValue(classPK, columnId);
	}

	public static void deleteValues(long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteValues(classPK);
	}

	public static void deleteValues(long classPK, java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteValues(classPK, className);
	}

	public static void deleteValues(long classPK, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteValues(classPK, classNameId);
	}

	public static void deleteColumnValues(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteColumnValues(columnId);
	}

	public static void deleteRowValues(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		expandoValueLocalService.deleteRowValues(rowId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getColumnValues(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getColumnValues(columnId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getColumnValues(
		long columnId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getColumnValues(columnId, begin, end);
	}

	public static int getColumnValuesCount(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getColumnValuesCount(columnId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getRowValues(
		long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getRowValues(rowId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getRowValues(
		long rowId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getRowValues(rowId, begin, end);
	}

	public static int getRowValuesCount(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getRowValuesCount(rowId);
	}

	public static com.liferay.portal.model.ExpandoValue getValue(long classPK,
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getValue(classPK, columnId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getValues(
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getValues(classPK);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getValues(
		long classPK, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getValues(classPK, begin, end);
	}

	public static int getValuesCount(long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.getValuesCount(classPK);
	}

	public static com.liferay.portal.model.ExpandoValue setValue(long classPK,
		long columnId, java.lang.String value)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.setValue(classPK, columnId, value);
	}

	public static long setRowValues(long tableId,
		com.liferay.portal.model.ExpandoValue[] expandoValues)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.setRowValues(tableId, expandoValues);
	}

	public static long setRowValues(long tableId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.setRowValues(tableId, expandoValues);
	}

	public static long setRowValues(long tableId, long rowId,
		com.liferay.portal.model.ExpandoValue[] expandoValues)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.setRowValues(tableId, rowId,
			expandoValues);
	}

	public static long setRowValues(long tableId, long rowId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoValueLocalService expandoValueLocalService = ExpandoValueLocalServiceFactory.getService();

		return expandoValueLocalService.setRowValues(tableId, rowId,
			expandoValues);
	}
}