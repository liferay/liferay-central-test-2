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
 * <a href="ExpandoTableLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ExpandoTableLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ExpandoTableLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ExpandoTableLocalService
 * @see com.liferay.portal.service.ExpandoTableLocalServiceFactory
 *
 */
public class ExpandoTableLocalServiceUtil {
	public static com.liferay.portal.model.ExpandoTable addExpandoTable(
		com.liferay.portal.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.addExpandoTable(expandoTable);
	}

	public static void deleteExpandoTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.deleteExpandoTable(tableId);
	}

	public static void deleteExpandoTable(
		com.liferay.portal.model.ExpandoTable expandoTable)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.deleteExpandoTable(expandoTable);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTable> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTable> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portal.model.ExpandoTable updateExpandoTable(
		com.liferay.portal.model.ExpandoTable expandoTable)
		throws com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.updateExpandoTable(expandoTable);
	}

	public static com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getAccountPersistence();
	}

	public static void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setAccountPersistence(accountPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerPersistence getActivityTrackerPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getActivityTrackerPersistence();
	}

	public static void setActivityTrackerPersistence(
		com.liferay.portal.service.persistence.ActivityTrackerPersistence activityTrackerPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setActivityTrackerPersistence(activityTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerFinder getActivityTrackerFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getActivityTrackerFinder();
	}

	public static void setActivityTrackerFinder(
		com.liferay.portal.service.persistence.ActivityTrackerFinder activityTrackerFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setActivityTrackerFinder(activityTrackerFinder);
	}

	public static com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getAddressPersistence();
	}

	public static void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setAddressPersistence(addressPersistence);
	}

	public static com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getClassNamePersistence();
	}

	public static void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setClassNamePersistence(classNamePersistence);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.ContactPersistence getContactPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getContactPersistence();
	}

	public static void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setContactPersistence(contactPersistence);
	}

	public static com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getCountryPersistence();
	}

	public static void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setCountryPersistence(countryPersistence);
	}

	public static com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getEmailAddressPersistence();
	}

	public static void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setEmailAddressPersistence(emailAddressPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoColumnPersistence getExpandoColumnPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getExpandoColumnPersistence();
	}

	public static void setExpandoColumnPersistence(
		com.liferay.portal.service.persistence.ExpandoColumnPersistence expandoColumnPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setExpandoColumnPersistence(expandoColumnPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTablePersistence getExpandoTablePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getExpandoTablePersistence();
	}

	public static void setExpandoTablePersistence(
		com.liferay.portal.service.persistence.ExpandoTablePersistence expandoTablePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setExpandoTablePersistence(expandoTablePersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTableRowPersistence getExpandoTableRowPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getExpandoTableRowPersistence();
	}

	public static void setExpandoTableRowPersistence(
		com.liferay.portal.service.persistence.ExpandoTableRowPersistence expandoTableRowPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setExpandoTableRowPersistence(expandoTableRowPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoValuePersistence getExpandoValuePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getExpandoValuePersistence();
	}

	public static void setExpandoValuePersistence(
		com.liferay.portal.service.persistence.ExpandoValuePersistence expandoValuePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setExpandoValuePersistence(expandoValuePersistence);
	}

	public static com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getGroupPersistence();
	}

	public static void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setGroupPersistence(groupPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupFinder getGroupFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getGroupFinder();
	}

	public static void setGroupFinder(
		com.liferay.portal.service.persistence.GroupFinder groupFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setGroupFinder(groupFinder);
	}

	public static com.liferay.portal.service.persistence.ImagePersistence getImagePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getImagePersistence();
	}

	public static void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setImagePersistence(imagePersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getLayoutPersistence();
	}

	public static void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setLayoutPersistence(layoutPersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutFinder getLayoutFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getLayoutFinder();
	}

	public static void setLayoutFinder(
		com.liferay.portal.service.persistence.LayoutFinder layoutFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setLayoutFinder(layoutFinder);
	}

	public static com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getLayoutSetPersistence();
	}

	public static void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setLayoutSetPersistence(layoutSetPersistence);
	}

	public static com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getListTypePersistence();
	}

	public static void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setListTypePersistence(listTypePersistence);
	}

	public static com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getMembershipRequestPersistence();
	}

	public static void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setMembershipRequestPersistence(membershipRequestPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getOrganizationPersistence();
	}

	public static void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setOrganizationPersistence(organizationPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationFinder getOrganizationFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getOrganizationFinder();
	}

	public static void setOrganizationFinder(
		com.liferay.portal.service.persistence.OrganizationFinder organizationFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setOrganizationFinder(organizationFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getOrgGroupPermissionPersistence();
	}

	public static void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setOrgGroupPermissionPersistence(orgGroupPermissionPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionFinder getOrgGroupPermissionFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getOrgGroupPermissionFinder();
	}

	public static void setOrgGroupPermissionFinder(
		com.liferay.portal.service.persistence.OrgGroupPermissionFinder orgGroupPermissionFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setOrgGroupPermissionFinder(orgGroupPermissionFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getOrgGroupRolePersistence();
	}

	public static void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setOrgGroupRolePersistence(orgGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getOrgLaborPersistence();
	}

	public static void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setOrgLaborPersistence(orgLaborPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPasswordPolicyPersistence();
	}

	public static void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPasswordPolicyPersistence(passwordPolicyPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyFinder getPasswordPolicyFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPasswordPolicyFinder();
	}

	public static void setPasswordPolicyFinder(
		com.liferay.portal.service.persistence.PasswordPolicyFinder passwordPolicyFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPasswordPolicyFinder(passwordPolicyFinder);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPasswordPolicyRelPersistence();
	}

	public static void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPasswordPolicyRelPersistence(passwordPolicyRelPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPasswordTrackerPersistence();
	}

	public static void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPasswordTrackerPersistence(passwordTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPermissionPersistence();
	}

	public static void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPermissionPersistence(permissionPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionFinder getPermissionFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPermissionFinder();
	}

	public static void setPermissionFinder(
		com.liferay.portal.service.persistence.PermissionFinder permissionFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPermissionFinder(permissionFinder);
	}

	public static com.liferay.portal.service.persistence.PermissionUserFinder getPermissionUserFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPermissionUserFinder();
	}

	public static void setPermissionUserFinder(
		com.liferay.portal.service.persistence.PermissionUserFinder permissionUserFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPermissionUserFinder(permissionUserFinder);
	}

	public static com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPhonePersistence();
	}

	public static void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPhonePersistence(phonePersistence);
	}

	public static com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPluginSettingPersistence();
	}

	public static void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPluginSettingPersistence(pluginSettingPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPortletPersistence();
	}

	public static void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPortletPersistence(portletPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesFinder getPortletPreferencesFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPortletPreferencesFinder();
	}

	public static void setPortletPreferencesFinder(
		com.liferay.portal.service.persistence.PortletPreferencesFinder portletPreferencesFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPortletPreferencesFinder(portletPreferencesFinder);
	}

	public static com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getRegionPersistence();
	}

	public static void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setRegionPersistence(regionPersistence);
	}

	public static com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getReleasePersistence();
	}

	public static void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setReleasePersistence(releasePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getResourceCodePersistence();
	}

	public static void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setResourceCodePersistence(resourceCodePersistence);
	}

	public static com.liferay.portal.service.persistence.RolePersistence getRolePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getRolePersistence();
	}

	public static void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setRolePersistence(rolePersistence);
	}

	public static com.liferay.portal.service.persistence.RoleFinder getRoleFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getRoleFinder();
	}

	public static void setRoleFinder(
		com.liferay.portal.service.persistence.RoleFinder roleFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setRoleFinder(roleFinder);
	}

	public static com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getServiceComponentPersistence();
	}

	public static void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setServiceComponentPersistence(serviceComponentPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletItemPersistence getPortletItemPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getPortletItemPersistence();
	}

	public static void setPortletItemPersistence(
		com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setPortletItemPersistence(portletItemPersistence);
	}

	public static com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getSubscriptionPersistence();
	}

	public static void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setSubscriptionPersistence(subscriptionPersistence);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserFinder(userFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserGroupPersistence();
	}

	public static void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserGroupPersistence(userGroupPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupFinder getUserGroupFinder() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserGroupFinder();
	}

	public static void setUserGroupFinder(
		com.liferay.portal.service.persistence.UserGroupFinder userGroupFinder) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserGroupFinder(userGroupFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserGroupRolePersistence();
	}

	public static void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserGroupRolePersistence(userGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserIdMapperPersistence();
	}

	public static void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserIdMapperPersistence(userIdMapperPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserTrackerPersistence();
	}

	public static void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserTrackerPersistence(userTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getUserTrackerPathPersistence();
	}

	public static void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setUserTrackerPathPersistence(userTrackerPathPersistence);
	}

	public static com.liferay.portal.service.persistence.WebDAVPropsPersistence getWebDAVPropsPersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getWebDAVPropsPersistence();
	}

	public static void setWebDAVPropsPersistence(
		com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setWebDAVPropsPersistence(webDAVPropsPersistence);
	}

	public static com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getWebsitePersistence();
	}

	public static void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence) {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.setWebsitePersistence(websitePersistence);
	}

	public static void afterPropertiesSet() {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.afterPropertiesSet();
	}

	public static com.liferay.portal.model.ExpandoTable addTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.addTable(classNameId, name);
	}

	public static void deleteTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		expandoTableLocalService.deleteTable(tableId);
	}

	public static com.liferay.portal.model.ExpandoTable getTable(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getTable(tableId);
	}

	public static com.liferay.portal.model.ExpandoTable getTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getTable(classNameId, name);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTable> getTables(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.getTables(classNameId);
	}

	public static com.liferay.portal.model.ExpandoTable setTable(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.setTable(classNameId, name);
	}

	public static com.liferay.portal.model.ExpandoTable updateTableName(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableLocalService expandoTableLocalService = ExpandoTableLocalServiceFactory.getService();

		return expandoTableLocalService.updateTableName(tableId, name);
	}
}