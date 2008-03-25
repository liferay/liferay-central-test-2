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
 * <a href="ExpandoTableRowLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ExpandoTableRowLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ExpandoTableRowLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ExpandoTableRowLocalService
 * @see com.liferay.portal.service.ExpandoTableRowLocalServiceFactory
 *
 */
public class ExpandoTableRowLocalServiceUtil {
	public static com.liferay.portal.model.ExpandoTableRow addExpandoTableRow(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow)
		throws com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.addExpandoTableRow(expandoTableRow);
	}

	public static void deleteExpandoTableRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.deleteExpandoTableRow(rowId);
	}

	public static void deleteExpandoTableRow(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.deleteExpandoTableRow(expandoTableRow);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.dynamicQuery(queryInitializer,
			begin, end);
	}

	public static com.liferay.portal.model.ExpandoTableRow updateExpandoTableRow(
		com.liferay.portal.model.ExpandoTableRow expandoTableRow)
		throws com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.updateExpandoTableRow(expandoTableRow);
	}

	public static com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getAccountPersistence();
	}

	public static void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setAccountPersistence(accountPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerPersistence getActivityTrackerPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getActivityTrackerPersistence();
	}

	public static void setActivityTrackerPersistence(
		com.liferay.portal.service.persistence.ActivityTrackerPersistence activityTrackerPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setActivityTrackerPersistence(activityTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerFinder getActivityTrackerFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getActivityTrackerFinder();
	}

	public static void setActivityTrackerFinder(
		com.liferay.portal.service.persistence.ActivityTrackerFinder activityTrackerFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setActivityTrackerFinder(activityTrackerFinder);
	}

	public static com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getAddressPersistence();
	}

	public static void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setAddressPersistence(addressPersistence);
	}

	public static com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getClassNamePersistence();
	}

	public static void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setClassNamePersistence(classNamePersistence);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.ContactPersistence getContactPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getContactPersistence();
	}

	public static void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setContactPersistence(contactPersistence);
	}

	public static com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getCountryPersistence();
	}

	public static void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setCountryPersistence(countryPersistence);
	}

	public static com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getEmailAddressPersistence();
	}

	public static void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setEmailAddressPersistence(emailAddressPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoColumnPersistence getExpandoColumnPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getExpandoColumnPersistence();
	}

	public static void setExpandoColumnPersistence(
		com.liferay.portal.service.persistence.ExpandoColumnPersistence expandoColumnPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setExpandoColumnPersistence(expandoColumnPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTablePersistence getExpandoTablePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getExpandoTablePersistence();
	}

	public static void setExpandoTablePersistence(
		com.liferay.portal.service.persistence.ExpandoTablePersistence expandoTablePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setExpandoTablePersistence(expandoTablePersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoTableRowPersistence getExpandoTableRowPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getExpandoTableRowPersistence();
	}

	public static void setExpandoTableRowPersistence(
		com.liferay.portal.service.persistence.ExpandoTableRowPersistence expandoTableRowPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setExpandoTableRowPersistence(expandoTableRowPersistence);
	}

	public static com.liferay.portal.service.persistence.ExpandoValuePersistence getExpandoValuePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getExpandoValuePersistence();
	}

	public static void setExpandoValuePersistence(
		com.liferay.portal.service.persistence.ExpandoValuePersistence expandoValuePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setExpandoValuePersistence(expandoValuePersistence);
	}

	public static com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getGroupPersistence();
	}

	public static void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setGroupPersistence(groupPersistence);
	}

	public static com.liferay.portal.service.persistence.GroupFinder getGroupFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getGroupFinder();
	}

	public static void setGroupFinder(
		com.liferay.portal.service.persistence.GroupFinder groupFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setGroupFinder(groupFinder);
	}

	public static com.liferay.portal.service.persistence.ImagePersistence getImagePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getImagePersistence();
	}

	public static void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setImagePersistence(imagePersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getLayoutPersistence();
	}

	public static void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setLayoutPersistence(layoutPersistence);
	}

	public static com.liferay.portal.service.persistence.LayoutFinder getLayoutFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getLayoutFinder();
	}

	public static void setLayoutFinder(
		com.liferay.portal.service.persistence.LayoutFinder layoutFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setLayoutFinder(layoutFinder);
	}

	public static com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getLayoutSetPersistence();
	}

	public static void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setLayoutSetPersistence(layoutSetPersistence);
	}

	public static com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getListTypePersistence();
	}

	public static void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setListTypePersistence(listTypePersistence);
	}

	public static com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getMembershipRequestPersistence();
	}

	public static void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setMembershipRequestPersistence(membershipRequestPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getOrganizationPersistence();
	}

	public static void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setOrganizationPersistence(organizationPersistence);
	}

	public static com.liferay.portal.service.persistence.OrganizationFinder getOrganizationFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getOrganizationFinder();
	}

	public static void setOrganizationFinder(
		com.liferay.portal.service.persistence.OrganizationFinder organizationFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setOrganizationFinder(organizationFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getOrgGroupPermissionPersistence();
	}

	public static void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setOrgGroupPermissionPersistence(orgGroupPermissionPersistence);
	}

	public static com.liferay.portal.service.persistence.OrgGroupPermissionFinder getOrgGroupPermissionFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getOrgGroupPermissionFinder();
	}

	public static void setOrgGroupPermissionFinder(
		com.liferay.portal.service.persistence.OrgGroupPermissionFinder orgGroupPermissionFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setOrgGroupPermissionFinder(orgGroupPermissionFinder);
	}

	public static com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getOrgGroupRolePersistence();
	}

	public static void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setOrgGroupRolePersistence(orgGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getOrgLaborPersistence();
	}

	public static void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setOrgLaborPersistence(orgLaborPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPasswordPolicyPersistence();
	}

	public static void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPasswordPolicyPersistence(passwordPolicyPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyFinder getPasswordPolicyFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPasswordPolicyFinder();
	}

	public static void setPasswordPolicyFinder(
		com.liferay.portal.service.persistence.PasswordPolicyFinder passwordPolicyFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPasswordPolicyFinder(passwordPolicyFinder);
	}

	public static com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPasswordPolicyRelPersistence();
	}

	public static void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPasswordPolicyRelPersistence(passwordPolicyRelPersistence);
	}

	public static com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPasswordTrackerPersistence();
	}

	public static void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPasswordTrackerPersistence(passwordTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPermissionPersistence();
	}

	public static void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPermissionPersistence(permissionPersistence);
	}

	public static com.liferay.portal.service.persistence.PermissionFinder getPermissionFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPermissionFinder();
	}

	public static void setPermissionFinder(
		com.liferay.portal.service.persistence.PermissionFinder permissionFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPermissionFinder(permissionFinder);
	}

	public static com.liferay.portal.service.persistence.PermissionUserFinder getPermissionUserFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPermissionUserFinder();
	}

	public static void setPermissionUserFinder(
		com.liferay.portal.service.persistence.PermissionUserFinder permissionUserFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPermissionUserFinder(permissionUserFinder);
	}

	public static com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPhonePersistence();
	}

	public static void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPhonePersistence(phonePersistence);
	}

	public static com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPluginSettingPersistence();
	}

	public static void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPluginSettingPersistence(pluginSettingPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPortletPersistence();
	}

	public static void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPortletPersistence(portletPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesFinder getPortletPreferencesFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPortletPreferencesFinder();
	}

	public static void setPortletPreferencesFinder(
		com.liferay.portal.service.persistence.PortletPreferencesFinder portletPreferencesFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPortletPreferencesFinder(portletPreferencesFinder);
	}

	public static com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getRegionPersistence();
	}

	public static void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setRegionPersistence(regionPersistence);
	}

	public static com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getReleasePersistence();
	}

	public static void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setReleasePersistence(releasePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getResourceCodePersistence();
	}

	public static void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setResourceCodePersistence(resourceCodePersistence);
	}

	public static com.liferay.portal.service.persistence.RolePersistence getRolePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getRolePersistence();
	}

	public static void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setRolePersistence(rolePersistence);
	}

	public static com.liferay.portal.service.persistence.RoleFinder getRoleFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getRoleFinder();
	}

	public static void setRoleFinder(
		com.liferay.portal.service.persistence.RoleFinder roleFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setRoleFinder(roleFinder);
	}

	public static com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getServiceComponentPersistence();
	}

	public static void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setServiceComponentPersistence(serviceComponentPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletItemPersistence getPortletItemPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getPortletItemPersistence();
	}

	public static void setPortletItemPersistence(
		com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setPortletItemPersistence(portletItemPersistence);
	}

	public static com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getSubscriptionPersistence();
	}

	public static void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setSubscriptionPersistence(subscriptionPersistence);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserFinder(userFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserGroupPersistence();
	}

	public static void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserGroupPersistence(userGroupPersistence);
	}

	public static com.liferay.portal.service.persistence.UserGroupFinder getUserGroupFinder() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserGroupFinder();
	}

	public static void setUserGroupFinder(
		com.liferay.portal.service.persistence.UserGroupFinder userGroupFinder) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserGroupFinder(userGroupFinder);
	}

	public static com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserGroupRolePersistence();
	}

	public static void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserGroupRolePersistence(userGroupRolePersistence);
	}

	public static com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserIdMapperPersistence();
	}

	public static void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserIdMapperPersistence(userIdMapperPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserTrackerPersistence();
	}

	public static void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserTrackerPersistence(userTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getUserTrackerPathPersistence();
	}

	public static void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setUserTrackerPathPersistence(userTrackerPathPersistence);
	}

	public static com.liferay.portal.service.persistence.WebDAVPropsPersistence getWebDAVPropsPersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getWebDAVPropsPersistence();
	}

	public static void setWebDAVPropsPersistence(
		com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setWebDAVPropsPersistence(webDAVPropsPersistence);
	}

	public static com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getWebsitePersistence();
	}

	public static void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence) {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.setWebsitePersistence(websitePersistence);
	}

	public static void afterPropertiesSet() {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.afterPropertiesSet();
	}

	public static com.liferay.portal.model.ExpandoTableRow addRow(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.addRow(tableId);
	}

	public static void deleteRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.deleteRow(rowId);
	}

	public static void deleteRows(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.deleteRows(tableId);
	}

	public static void deleteRows(long tableId, long[] rowIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		expandoTableRowLocalService.deleteRows(tableId, rowIds);
	}

	public static com.liferay.portal.model.ExpandoTableRow getRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getRow(rowId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> getRows(
		long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getRows(tableId);
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> getRows(
		long tableId, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getRows(tableId, begin, end);
	}

	public static int getRowsCount(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.getRowsCount(tableId);
	}

	public static com.liferay.portal.model.ExpandoTableRow setRow(
		long tableId, long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoTableRowLocalService expandoTableRowLocalService = ExpandoTableRowLocalServiceFactory.getService();

		return expandoTableRowLocalService.setRow(tableId, rowId);
	}
}