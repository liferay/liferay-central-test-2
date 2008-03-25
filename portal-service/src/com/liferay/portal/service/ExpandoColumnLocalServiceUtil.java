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
 * <a href="ExpandoColumnLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ExpandoColumnLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ExpandoColumnLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ExpandoColumnLocalService
 * @see com.liferay.portal.service.ExpandoColumnLocalServiceFactory
 *
 */
public class ExpandoColumnLocalServiceUtil {
	public static com.liferay.portal.model.ExpandoColumn addExpandoColumn(
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.addExpandoColumn(expandoColumn);
	}

	public static void deleteExpandoColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteExpandoColumn(columnId);
	}

	public static void deleteExpandoColumn(
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteExpandoColumn(expandoColumn);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portal.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portal.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	public static com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getAccountPersistence();
	}

	public static void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setAccountPersistence(accountPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerPersistence getActivityTrackerPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getActivityTrackerPersistence();
	}

	public static void setActivityTrackerPersistence(
		com.liferay.portal.service.persistence.ActivityTrackerPersistence activityTrackerPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setActivityTrackerPersistence(activityTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerFinder getActivityTrackerFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getActivityTrackerFinder();
	}

	public static void setActivityTrackerFinder(
		com.liferay.portal.service.persistence.ActivityTrackerFinder activityTrackerFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setActivityTrackerFinder(activityTrackerFinder);
	}

	public static com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getAddressPersistence();
	}

	public static void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setAddressPersistence(addressPersistence);
	}

	public static com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getClassNamePersistence();
	}

	public static void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setClassNamePersistence(classNamePersistence);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.ContactPersistence getContactPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getContactPersistence();
	}

	public static void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setContactPersistence(contactPersistence);
	}

	public static com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getCountryPersistence();
	}

	public static void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setCountryPersistence(countryPersistence);
	}

	public static com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getEmailAddressPersistence();
	}

	public static void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setEmailAddressPersistence(emailAddressPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoColumnPersistence getExpandoColumnPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getExpandoColumnPersistence();
	}

	public static void setExpandoColumnPersistence(
		com.liferay.portal.service.persistence.ExpandoColumnPersistence expandoColumnPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setExpandoColumnPersistence(expandoColumnPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTablePersistence getExpandoTablePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getExpandoTablePersistence();
	}

	public static void setExpandoTablePersistence(
		com.liferay.portal.service.persistence.ExpandoTablePersistence expandoTablePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setExpandoTablePersistence(expandoTablePersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTableRowPersistence getExpandoTableRowPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getExpandoTableRowPersistence();
	}

	public static void setExpandoTableRowPersistence(
		com.liferay.portal.service.persistence.ExpandoTableRowPersistence expandoTableRowPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setExpandoTableRowPersistence(expandoTableRowPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoValuePersistence getExpandoValuePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getExpandoValuePersistence();
	}

	public static void setExpandoValuePersistence(
		com.liferay.portal.service.persistence.ExpandoValuePersistence expandoValuePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setExpandoValuePersistence(expandoValuePersistence);
	}

	public static com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getGroupPersistence();
	}

	public static void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setGroupPersistence(groupPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupFinder getGroupFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getGroupFinder();
	}

	public static void setGroupFinder(
		com.liferay.portal.service.persistence.GroupFinder groupFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setGroupFinder(groupFinder);
	}

	public static com.liferay.portal.service.persistence.ImagePersistence getImagePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getImagePersistence();
	}

	public static void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setImagePersistence(imagePersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getLayoutPersistence();
	}

	public static void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setLayoutPersistence(layoutPersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutFinder getLayoutFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getLayoutFinder();
	}

	public static void setLayoutFinder(
		com.liferay.portal.service.persistence.LayoutFinder layoutFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setLayoutFinder(layoutFinder);
	}

	public static com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getLayoutSetPersistence();
	}

	public static void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setLayoutSetPersistence(layoutSetPersistence);
	}

	public static com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getListTypePersistence();
	}

	public static void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setListTypePersistence(listTypePersistence);
	}

	public static com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getMembershipRequestPersistence();
	}

	public static void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setMembershipRequestPersistence(membershipRequestPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getOrganizationPersistence();
	}

	public static void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setOrganizationPersistence(organizationPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationFinder getOrganizationFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getOrganizationFinder();
	}

	public static void setOrganizationFinder(
		com.liferay.portal.service.persistence.OrganizationFinder organizationFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setOrganizationFinder(organizationFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getOrgGroupPermissionPersistence();
	}

	public static void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setOrgGroupPermissionPersistence(orgGroupPermissionPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionFinder getOrgGroupPermissionFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getOrgGroupPermissionFinder();
	}

	public static void setOrgGroupPermissionFinder(
		com.liferay.portal.service.persistence.OrgGroupPermissionFinder orgGroupPermissionFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setOrgGroupPermissionFinder(orgGroupPermissionFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getOrgGroupRolePersistence();
	}

	public static void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setOrgGroupRolePersistence(orgGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getOrgLaborPersistence();
	}

	public static void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setOrgLaborPersistence(orgLaborPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPasswordPolicyPersistence();
	}

	public static void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPasswordPolicyPersistence(passwordPolicyPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyFinder getPasswordPolicyFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPasswordPolicyFinder();
	}

	public static void setPasswordPolicyFinder(
		com.liferay.portal.service.persistence.PasswordPolicyFinder passwordPolicyFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPasswordPolicyFinder(passwordPolicyFinder);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPasswordPolicyRelPersistence();
	}

	public static void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPasswordPolicyRelPersistence(passwordPolicyRelPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPasswordTrackerPersistence();
	}

	public static void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPasswordTrackerPersistence(passwordTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPermissionPersistence();
	}

	public static void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPermissionPersistence(permissionPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionFinder getPermissionFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPermissionFinder();
	}

	public static void setPermissionFinder(
		com.liferay.portal.service.persistence.PermissionFinder permissionFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPermissionFinder(permissionFinder);
	}

	public static com.liferay.portal.service.persistence.PermissionUserFinder getPermissionUserFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPermissionUserFinder();
	}

	public static void setPermissionUserFinder(
		com.liferay.portal.service.persistence.PermissionUserFinder permissionUserFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPermissionUserFinder(permissionUserFinder);
	}

	public static com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPhonePersistence();
	}

	public static void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPhonePersistence(phonePersistence);
	}

	public static com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPluginSettingPersistence();
	}

	public static void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPluginSettingPersistence(pluginSettingPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPortletPersistence();
	}

	public static void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPortletPersistence(portletPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesFinder getPortletPreferencesFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPortletPreferencesFinder();
	}

	public static void setPortletPreferencesFinder(
		com.liferay.portal.service.persistence.PortletPreferencesFinder portletPreferencesFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPortletPreferencesFinder(portletPreferencesFinder);
	}

	public static com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getRegionPersistence();
	}

	public static void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setRegionPersistence(regionPersistence);
	}

	public static com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getReleasePersistence();
	}

	public static void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setReleasePersistence(releasePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getResourceCodePersistence();
	}

	public static void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setResourceCodePersistence(resourceCodePersistence);
	}

	public static com.liferay.portal.service.persistence.RolePersistence getRolePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getRolePersistence();
	}

	public static void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setRolePersistence(rolePersistence);
	}

	public static com.liferay.portal.service.persistence.RoleFinder getRoleFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getRoleFinder();
	}

	public static void setRoleFinder(
		com.liferay.portal.service.persistence.RoleFinder roleFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setRoleFinder(roleFinder);
	}

	public static com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getServiceComponentPersistence();
	}

	public static void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setServiceComponentPersistence(serviceComponentPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletItemPersistence getPortletItemPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getPortletItemPersistence();
	}

	public static void setPortletItemPersistence(
		com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setPortletItemPersistence(portletItemPersistence);
	}

	public static com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getSubscriptionPersistence();
	}

	public static void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setSubscriptionPersistence(subscriptionPersistence);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserFinder(userFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserGroupPersistence();
	}

	public static void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserGroupPersistence(userGroupPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupFinder getUserGroupFinder() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserGroupFinder();
	}

	public static void setUserGroupFinder(
		com.liferay.portal.service.persistence.UserGroupFinder userGroupFinder) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserGroupFinder(userGroupFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserGroupRolePersistence();
	}

	public static void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserGroupRolePersistence(userGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserIdMapperPersistence();
	}

	public static void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserIdMapperPersistence(userIdMapperPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserTrackerPersistence();
	}

	public static void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserTrackerPersistence(userTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getUserTrackerPathPersistence();
	}

	public static void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setUserTrackerPathPersistence(userTrackerPathPersistence);
	}

	public static com.liferay.portal.service.persistence.WebDAVPropsPersistence getWebDAVPropsPersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getWebDAVPropsPersistence();
	}

	public static void setWebDAVPropsPersistence(
		com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setWebDAVPropsPersistence(webDAVPropsPersistence);
	}

	public static com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getWebsitePersistence();
	}

	public static void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence) {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.setWebsitePersistence(websitePersistence);
	}

	public static void afterPropertiesSet() {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.afterPropertiesSet();
	}

	public static com.liferay.portal.model.ExpandoColumn addColumn(
		long classNameId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.addColumn(classNameId, name, type);
	}

	public static com.liferay.portal.model.ExpandoColumn addColumn(
		long classNameId, java.lang.String name, int type,
		java.util.Properties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.addColumn(classNameId, name, type,
			properties);
	}

	public static void addTableColumns(long tableId, long[] columnIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.addTableColumns(tableId, columnIds);
	}

	public static void addTableColumns(long tableId,
		java.util.List<com.liferay.portal.model.ExpandoColumn> columns)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.addTableColumns(tableId, columns);
	}

	public static void deleteColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumn(columnId);
	}

	public static void deleteTableColumns(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteTableColumns(tableId);
	}

	public static void deleteTableColumns(long tableId, long[] columnIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteTableColumns(tableId, columnIds);
	}

	public static void deleteTableColumns(long tableId,
		java.util.List<com.liferay.portal.model.ExpandoColumn> columns)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteTableColumns(tableId, columns);
	}

	public static com.liferay.portal.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumn(columnId);
	}

	public static com.liferay.portal.model.ExpandoColumn getColumn(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumn(classNameId, name);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getColumns(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumns(classNameId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getColumns(
		long classNameId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumns(classNameId, begin, end);
	}

	public static int getColumnsCount(long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumnsCount(classNameId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getTableColumns(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getTableColumns(tableId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoColumn> getTableColumns(
		long tableId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getTableColumns(tableId, begin, end);
	}

	public static int getTableColumnsCount(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getTableColumnsCount(tableId);
	}

	public static com.liferay.portal.model.ExpandoColumn setColumn(
		long classNameId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.setColumn(classNameId, name, type);
	}

	public static com.liferay.portal.model.ExpandoColumn setColumn(
		long classNameId, java.lang.String name, int type,
		java.util.Properties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.setColumn(classNameId, name, type,
			properties);
	}

	public static com.liferay.portal.model.ExpandoColumn updateColumnType(
		long columnId, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.updateColumnType(columnId, type);
	}

	public static com.liferay.portal.model.ExpandoColumn updateColumnName(
		long columnId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.updateColumnName(columnId, name);
	}

	public static com.liferay.portal.model.ExpandoColumn updateColumnProperties(
		long columnId, java.util.Properties properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.updateColumnProperties(columnId,
			properties);
	}
}