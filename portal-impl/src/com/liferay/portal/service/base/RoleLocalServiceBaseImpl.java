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

package com.liferay.portal.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.AccountLocalService;
import com.liferay.portal.service.AccountService;
import com.liferay.portal.service.AddressLocalService;
import com.liferay.portal.service.AddressService;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.ClassNameService;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.ContactLocalService;
import com.liferay.portal.service.ContactService;
import com.liferay.portal.service.CountryService;
import com.liferay.portal.service.EmailAddressLocalService;
import com.liferay.portal.service.EmailAddressService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutSetService;
import com.liferay.portal.service.LayoutTemplateLocalService;
import com.liferay.portal.service.ListTypeService;
import com.liferay.portal.service.MembershipRequestLocalService;
import com.liferay.portal.service.MembershipRequestService;
import com.liferay.portal.service.OrgLaborLocalService;
import com.liferay.portal.service.OrgLaborService;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.OrganizationService;
import com.liferay.portal.service.PasswordPolicyLocalService;
import com.liferay.portal.service.PasswordPolicyRelLocalService;
import com.liferay.portal.service.PasswordPolicyService;
import com.liferay.portal.service.PasswordTrackerLocalService;
import com.liferay.portal.service.PermissionLocalService;
import com.liferay.portal.service.PermissionService;
import com.liferay.portal.service.PhoneLocalService;
import com.liferay.portal.service.PhoneService;
import com.liferay.portal.service.PluginSettingLocalService;
import com.liferay.portal.service.PluginSettingService;
import com.liferay.portal.service.PortalService;
import com.liferay.portal.service.PortletItemLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.PortletPreferencesService;
import com.liferay.portal.service.PortletService;
import com.liferay.portal.service.RegionService;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.service.ResourceCodeLocalService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.ServiceComponentLocalService;
import com.liferay.portal.service.SubscriptionLocalService;
import com.liferay.portal.service.ThemeLocalService;
import com.liferay.portal.service.UserGroupLocalService;
import com.liferay.portal.service.UserGroupRoleLocalService;
import com.liferay.portal.service.UserGroupRoleService;
import com.liferay.portal.service.UserGroupService;
import com.liferay.portal.service.UserIdMapperLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.UserTrackerLocalService;
import com.liferay.portal.service.UserTrackerPathLocalService;
import com.liferay.portal.service.WebDAVPropsLocalService;
import com.liferay.portal.service.WebsiteLocalService;
import com.liferay.portal.service.WebsiteService;
import com.liferay.portal.service.persistence.AccountPersistence;
import com.liferay.portal.service.persistence.AddressPersistence;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.ContactPersistence;
import com.liferay.portal.service.persistence.CountryPersistence;
import com.liferay.portal.service.persistence.EmailAddressPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.LayoutSetPersistence;
import com.liferay.portal.service.persistence.ListTypePersistence;
import com.liferay.portal.service.persistence.MembershipRequestPersistence;
import com.liferay.portal.service.persistence.OrgGroupPermissionFinder;
import com.liferay.portal.service.persistence.OrgGroupPermissionPersistence;
import com.liferay.portal.service.persistence.OrgGroupRolePersistence;
import com.liferay.portal.service.persistence.OrgLaborPersistence;
import com.liferay.portal.service.persistence.OrganizationFinder;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.PasswordPolicyFinder;
import com.liferay.portal.service.persistence.PasswordPolicyPersistence;
import com.liferay.portal.service.persistence.PasswordPolicyRelPersistence;
import com.liferay.portal.service.persistence.PasswordTrackerPersistence;
import com.liferay.portal.service.persistence.PermissionFinder;
import com.liferay.portal.service.persistence.PermissionPersistence;
import com.liferay.portal.service.persistence.PermissionUserFinder;
import com.liferay.portal.service.persistence.PhonePersistence;
import com.liferay.portal.service.persistence.PluginSettingPersistence;
import com.liferay.portal.service.persistence.PortletItemPersistence;
import com.liferay.portal.service.persistence.PortletPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.RegionPersistence;
import com.liferay.portal.service.persistence.ReleasePersistence;
import com.liferay.portal.service.persistence.ResourceCodePersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.ServiceComponentPersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserGroupFinder;
import com.liferay.portal.service.persistence.UserGroupPersistence;
import com.liferay.portal.service.persistence.UserGroupRolePersistence;
import com.liferay.portal.service.persistence.UserIdMapperPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserTrackerPathPersistence;
import com.liferay.portal.service.persistence.UserTrackerPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.WebsitePersistence;

import java.util.List;

/**
 * <a href="RoleLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class RoleLocalServiceBaseImpl implements RoleLocalService,
	InitializingBean {
	public Role addRole(Role role) throws SystemException {
		role.setNew(true);

		return rolePersistence.update(role, false);
	}

	public void deleteRole(long roleId) throws PortalException, SystemException {
		rolePersistence.remove(roleId);
	}

	public void deleteRole(Role role) throws SystemException {
		rolePersistence.remove(role);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return rolePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return rolePersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	public Role getRole(long roleId) throws PortalException, SystemException {
		return rolePersistence.findByPrimaryKey(roleId);
	}

	public List<Role> getRoles(int start, int end) throws SystemException {
		return rolePersistence.findAll(start, end);
	}

	public int getRolesCount() throws SystemException {
		return rolePersistence.countAll();
	}

	public Role updateRole(Role role) throws SystemException {
		role.setNew(false);

		return rolePersistence.update(role, true);
	}

	public AccountLocalService getAccountLocalService() {
		return accountLocalService;
	}

	public void setAccountLocalService(AccountLocalService accountLocalService) {
		this.accountLocalService = accountLocalService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public AccountPersistence getAccountPersistence() {
		return accountPersistence;
	}

	public void setAccountPersistence(AccountPersistence accountPersistence) {
		this.accountPersistence = accountPersistence;
	}

	public AddressLocalService getAddressLocalService() {
		return addressLocalService;
	}

	public void setAddressLocalService(AddressLocalService addressLocalService) {
		this.addressLocalService = addressLocalService;
	}

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	public AddressPersistence getAddressPersistence() {
		return addressPersistence;
	}

	public void setAddressPersistence(AddressPersistence addressPersistence) {
		this.addressPersistence = addressPersistence;
	}

	public ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	public void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	public ClassNameService getClassNameService() {
		return classNameService;
	}

	public void setClassNameService(ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	public CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	public void setCompanyLocalService(CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	public ContactLocalService getContactLocalService() {
		return contactLocalService;
	}

	public void setContactLocalService(ContactLocalService contactLocalService) {
		this.contactLocalService = contactLocalService;
	}

	public ContactService getContactService() {
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public ContactPersistence getContactPersistence() {
		return contactPersistence;
	}

	public void setContactPersistence(ContactPersistence contactPersistence) {
		this.contactPersistence = contactPersistence;
	}

	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public CountryPersistence getCountryPersistence() {
		return countryPersistence;
	}

	public void setCountryPersistence(CountryPersistence countryPersistence) {
		this.countryPersistence = countryPersistence;
	}

	public EmailAddressLocalService getEmailAddressLocalService() {
		return emailAddressLocalService;
	}

	public void setEmailAddressLocalService(
		EmailAddressLocalService emailAddressLocalService) {
		this.emailAddressLocalService = emailAddressLocalService;
	}

	public EmailAddressService getEmailAddressService() {
		return emailAddressService;
	}

	public void setEmailAddressService(EmailAddressService emailAddressService) {
		this.emailAddressService = emailAddressService;
	}

	public EmailAddressPersistence getEmailAddressPersistence() {
		return emailAddressPersistence;
	}

	public void setEmailAddressPersistence(
		EmailAddressPersistence emailAddressPersistence) {
		this.emailAddressPersistence = emailAddressPersistence;
	}

	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	public ImageLocalService getImageLocalService() {
		return imageLocalService;
	}

	public void setImageLocalService(ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}

	public ImagePersistence getImagePersistence() {
		return imagePersistence;
	}

	public void setImagePersistence(ImagePersistence imagePersistence) {
		this.imagePersistence = imagePersistence;
	}

	public LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	public LayoutService getLayoutService() {
		return layoutService;
	}

	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	public LayoutSetLocalService getLayoutSetLocalService() {
		return layoutSetLocalService;
	}

	public void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {
		this.layoutSetLocalService = layoutSetLocalService;
	}

	public LayoutSetService getLayoutSetService() {
		return layoutSetService;
	}

	public void setLayoutSetService(LayoutSetService layoutSetService) {
		this.layoutSetService = layoutSetService;
	}

	public LayoutSetPersistence getLayoutSetPersistence() {
		return layoutSetPersistence;
	}

	public void setLayoutSetPersistence(
		LayoutSetPersistence layoutSetPersistence) {
		this.layoutSetPersistence = layoutSetPersistence;
	}

	public LayoutTemplateLocalService getLayoutTemplateLocalService() {
		return layoutTemplateLocalService;
	}

	public void setLayoutTemplateLocalService(
		LayoutTemplateLocalService layoutTemplateLocalService) {
		this.layoutTemplateLocalService = layoutTemplateLocalService;
	}

	public ListTypeService getListTypeService() {
		return listTypeService;
	}

	public void setListTypeService(ListTypeService listTypeService) {
		this.listTypeService = listTypeService;
	}

	public ListTypePersistence getListTypePersistence() {
		return listTypePersistence;
	}

	public void setListTypePersistence(ListTypePersistence listTypePersistence) {
		this.listTypePersistence = listTypePersistence;
	}

	public MembershipRequestLocalService getMembershipRequestLocalService() {
		return membershipRequestLocalService;
	}

	public void setMembershipRequestLocalService(
		MembershipRequestLocalService membershipRequestLocalService) {
		this.membershipRequestLocalService = membershipRequestLocalService;
	}

	public MembershipRequestService getMembershipRequestService() {
		return membershipRequestService;
	}

	public void setMembershipRequestService(
		MembershipRequestService membershipRequestService) {
		this.membershipRequestService = membershipRequestService;
	}

	public MembershipRequestPersistence getMembershipRequestPersistence() {
		return membershipRequestPersistence;
	}

	public void setMembershipRequestPersistence(
		MembershipRequestPersistence membershipRequestPersistence) {
		this.membershipRequestPersistence = membershipRequestPersistence;
	}

	public OrganizationLocalService getOrganizationLocalService() {
		return organizationLocalService;
	}

	public void setOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {
		this.organizationLocalService = organizationLocalService;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public OrganizationPersistence getOrganizationPersistence() {
		return organizationPersistence;
	}

	public void setOrganizationPersistence(
		OrganizationPersistence organizationPersistence) {
		this.organizationPersistence = organizationPersistence;
	}

	public OrganizationFinder getOrganizationFinder() {
		return organizationFinder;
	}

	public void setOrganizationFinder(OrganizationFinder organizationFinder) {
		this.organizationFinder = organizationFinder;
	}

	public OrgGroupPermissionPersistence getOrgGroupPermissionPersistence() {
		return orgGroupPermissionPersistence;
	}

	public void setOrgGroupPermissionPersistence(
		OrgGroupPermissionPersistence orgGroupPermissionPersistence) {
		this.orgGroupPermissionPersistence = orgGroupPermissionPersistence;
	}

	public OrgGroupPermissionFinder getOrgGroupPermissionFinder() {
		return orgGroupPermissionFinder;
	}

	public void setOrgGroupPermissionFinder(
		OrgGroupPermissionFinder orgGroupPermissionFinder) {
		this.orgGroupPermissionFinder = orgGroupPermissionFinder;
	}

	public OrgGroupRolePersistence getOrgGroupRolePersistence() {
		return orgGroupRolePersistence;
	}

	public void setOrgGroupRolePersistence(
		OrgGroupRolePersistence orgGroupRolePersistence) {
		this.orgGroupRolePersistence = orgGroupRolePersistence;
	}

	public OrgLaborLocalService getOrgLaborLocalService() {
		return orgLaborLocalService;
	}

	public void setOrgLaborLocalService(
		OrgLaborLocalService orgLaborLocalService) {
		this.orgLaborLocalService = orgLaborLocalService;
	}

	public OrgLaborService getOrgLaborService() {
		return orgLaborService;
	}

	public void setOrgLaborService(OrgLaborService orgLaborService) {
		this.orgLaborService = orgLaborService;
	}

	public OrgLaborPersistence getOrgLaborPersistence() {
		return orgLaborPersistence;
	}

	public void setOrgLaborPersistence(OrgLaborPersistence orgLaborPersistence) {
		this.orgLaborPersistence = orgLaborPersistence;
	}

	public PasswordPolicyLocalService getPasswordPolicyLocalService() {
		return passwordPolicyLocalService;
	}

	public void setPasswordPolicyLocalService(
		PasswordPolicyLocalService passwordPolicyLocalService) {
		this.passwordPolicyLocalService = passwordPolicyLocalService;
	}

	public PasswordPolicyService getPasswordPolicyService() {
		return passwordPolicyService;
	}

	public void setPasswordPolicyService(
		PasswordPolicyService passwordPolicyService) {
		this.passwordPolicyService = passwordPolicyService;
	}

	public PasswordPolicyPersistence getPasswordPolicyPersistence() {
		return passwordPolicyPersistence;
	}

	public void setPasswordPolicyPersistence(
		PasswordPolicyPersistence passwordPolicyPersistence) {
		this.passwordPolicyPersistence = passwordPolicyPersistence;
	}

	public PasswordPolicyFinder getPasswordPolicyFinder() {
		return passwordPolicyFinder;
	}

	public void setPasswordPolicyFinder(
		PasswordPolicyFinder passwordPolicyFinder) {
		this.passwordPolicyFinder = passwordPolicyFinder;
	}

	public PasswordPolicyRelLocalService getPasswordPolicyRelLocalService() {
		return passwordPolicyRelLocalService;
	}

	public void setPasswordPolicyRelLocalService(
		PasswordPolicyRelLocalService passwordPolicyRelLocalService) {
		this.passwordPolicyRelLocalService = passwordPolicyRelLocalService;
	}

	public PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		return passwordPolicyRelPersistence;
	}

	public void setPasswordPolicyRelPersistence(
		PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		this.passwordPolicyRelPersistence = passwordPolicyRelPersistence;
	}

	public PasswordTrackerLocalService getPasswordTrackerLocalService() {
		return passwordTrackerLocalService;
	}

	public void setPasswordTrackerLocalService(
		PasswordTrackerLocalService passwordTrackerLocalService) {
		this.passwordTrackerLocalService = passwordTrackerLocalService;
	}

	public PasswordTrackerPersistence getPasswordTrackerPersistence() {
		return passwordTrackerPersistence;
	}

	public void setPasswordTrackerPersistence(
		PasswordTrackerPersistence passwordTrackerPersistence) {
		this.passwordTrackerPersistence = passwordTrackerPersistence;
	}

	public PermissionLocalService getPermissionLocalService() {
		return permissionLocalService;
	}

	public void setPermissionLocalService(
		PermissionLocalService permissionLocalService) {
		this.permissionLocalService = permissionLocalService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public PermissionPersistence getPermissionPersistence() {
		return permissionPersistence;
	}

	public void setPermissionPersistence(
		PermissionPersistence permissionPersistence) {
		this.permissionPersistence = permissionPersistence;
	}

	public PermissionFinder getPermissionFinder() {
		return permissionFinder;
	}

	public void setPermissionFinder(PermissionFinder permissionFinder) {
		this.permissionFinder = permissionFinder;
	}

	public PermissionUserFinder getPermissionUserFinder() {
		return permissionUserFinder;
	}

	public void setPermissionUserFinder(
		PermissionUserFinder permissionUserFinder) {
		this.permissionUserFinder = permissionUserFinder;
	}

	public PhoneLocalService getPhoneLocalService() {
		return phoneLocalService;
	}

	public void setPhoneLocalService(PhoneLocalService phoneLocalService) {
		this.phoneLocalService = phoneLocalService;
	}

	public PhoneService getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(PhoneService phoneService) {
		this.phoneService = phoneService;
	}

	public PhonePersistence getPhonePersistence() {
		return phonePersistence;
	}

	public void setPhonePersistence(PhonePersistence phonePersistence) {
		this.phonePersistence = phonePersistence;
	}

	public PortalService getPortalService() {
		return portalService;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	public PluginSettingLocalService getPluginSettingLocalService() {
		return pluginSettingLocalService;
	}

	public void setPluginSettingLocalService(
		PluginSettingLocalService pluginSettingLocalService) {
		this.pluginSettingLocalService = pluginSettingLocalService;
	}

	public PluginSettingService getPluginSettingService() {
		return pluginSettingService;
	}

	public void setPluginSettingService(
		PluginSettingService pluginSettingService) {
		this.pluginSettingService = pluginSettingService;
	}

	public PluginSettingPersistence getPluginSettingPersistence() {
		return pluginSettingPersistence;
	}

	public void setPluginSettingPersistence(
		PluginSettingPersistence pluginSettingPersistence) {
		this.pluginSettingPersistence = pluginSettingPersistence;
	}

	public PortletLocalService getPortletLocalService() {
		return portletLocalService;
	}

	public void setPortletLocalService(PortletLocalService portletLocalService) {
		this.portletLocalService = portletLocalService;
	}

	public PortletService getPortletService() {
		return portletService;
	}

	public void setPortletService(PortletService portletService) {
		this.portletService = portletService;
	}

	public PortletPersistence getPortletPersistence() {
		return portletPersistence;
	}

	public void setPortletPersistence(PortletPersistence portletPersistence) {
		this.portletPersistence = portletPersistence;
	}

	public PortletPreferencesLocalService getPortletPreferencesLocalService() {
		return portletPreferencesLocalService;
	}

	public void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		this.portletPreferencesLocalService = portletPreferencesLocalService;
	}

	public PortletPreferencesService getPortletPreferencesService() {
		return portletPreferencesService;
	}

	public void setPortletPreferencesService(
		PortletPreferencesService portletPreferencesService) {
		this.portletPreferencesService = portletPreferencesService;
	}

	public PortletPreferencesPersistence getPortletPreferencesPersistence() {
		return portletPreferencesPersistence;
	}

	public void setPortletPreferencesPersistence(
		PortletPreferencesPersistence portletPreferencesPersistence) {
		this.portletPreferencesPersistence = portletPreferencesPersistence;
	}

	public PortletPreferencesFinder getPortletPreferencesFinder() {
		return portletPreferencesFinder;
	}

	public void setPortletPreferencesFinder(
		PortletPreferencesFinder portletPreferencesFinder) {
		this.portletPreferencesFinder = portletPreferencesFinder;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public RegionPersistence getRegionPersistence() {
		return regionPersistence;
	}

	public void setRegionPersistence(RegionPersistence regionPersistence) {
		this.regionPersistence = regionPersistence;
	}

	public ReleaseLocalService getReleaseLocalService() {
		return releaseLocalService;
	}

	public void setReleaseLocalService(ReleaseLocalService releaseLocalService) {
		this.releaseLocalService = releaseLocalService;
	}

	public ReleasePersistence getReleasePersistence() {
		return releasePersistence;
	}

	public void setReleasePersistence(ReleasePersistence releasePersistence) {
		this.releasePersistence = releasePersistence;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public ResourceCodeLocalService getResourceCodeLocalService() {
		return resourceCodeLocalService;
	}

	public void setResourceCodeLocalService(
		ResourceCodeLocalService resourceCodeLocalService) {
		this.resourceCodeLocalService = resourceCodeLocalService;
	}

	public ResourceCodePersistence getResourceCodePersistence() {
		return resourceCodePersistence;
	}

	public void setResourceCodePersistence(
		ResourceCodePersistence resourceCodePersistence) {
		this.resourceCodePersistence = resourceCodePersistence;
	}

	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	public RoleFinder getRoleFinder() {
		return roleFinder;
	}

	public void setRoleFinder(RoleFinder roleFinder) {
		this.roleFinder = roleFinder;
	}

	public ServiceComponentLocalService getServiceComponentLocalService() {
		return serviceComponentLocalService;
	}

	public void setServiceComponentLocalService(
		ServiceComponentLocalService serviceComponentLocalService) {
		this.serviceComponentLocalService = serviceComponentLocalService;
	}

	public ServiceComponentPersistence getServiceComponentPersistence() {
		return serviceComponentPersistence;
	}

	public void setServiceComponentPersistence(
		ServiceComponentPersistence serviceComponentPersistence) {
		this.serviceComponentPersistence = serviceComponentPersistence;
	}

	public PortletItemLocalService getPortletItemLocalService() {
		return portletItemLocalService;
	}

	public void setPortletItemLocalService(
		PortletItemLocalService portletItemLocalService) {
		this.portletItemLocalService = portletItemLocalService;
	}

	public PortletItemPersistence getPortletItemPersistence() {
		return portletItemPersistence;
	}

	public void setPortletItemPersistence(
		PortletItemPersistence portletItemPersistence) {
		this.portletItemPersistence = portletItemPersistence;
	}

	public SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	public void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {
		this.subscriptionLocalService = subscriptionLocalService;
	}

	public SubscriptionPersistence getSubscriptionPersistence() {
		return subscriptionPersistence;
	}

	public void setSubscriptionPersistence(
		SubscriptionPersistence subscriptionPersistence) {
		this.subscriptionPersistence = subscriptionPersistence;
	}

	public ThemeLocalService getThemeLocalService() {
		return themeLocalService;
	}

	public void setThemeLocalService(ThemeLocalService themeLocalService) {
		this.themeLocalService = themeLocalService;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public UserGroupLocalService getUserGroupLocalService() {
		return userGroupLocalService;
	}

	public void setUserGroupLocalService(
		UserGroupLocalService userGroupLocalService) {
		this.userGroupLocalService = userGroupLocalService;
	}

	public UserGroupService getUserGroupService() {
		return userGroupService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	public UserGroupPersistence getUserGroupPersistence() {
		return userGroupPersistence;
	}

	public void setUserGroupPersistence(
		UserGroupPersistence userGroupPersistence) {
		this.userGroupPersistence = userGroupPersistence;
	}

	public UserGroupFinder getUserGroupFinder() {
		return userGroupFinder;
	}

	public void setUserGroupFinder(UserGroupFinder userGroupFinder) {
		this.userGroupFinder = userGroupFinder;
	}

	public UserGroupRoleLocalService getUserGroupRoleLocalService() {
		return userGroupRoleLocalService;
	}

	public void setUserGroupRoleLocalService(
		UserGroupRoleLocalService userGroupRoleLocalService) {
		this.userGroupRoleLocalService = userGroupRoleLocalService;
	}

	public UserGroupRoleService getUserGroupRoleService() {
		return userGroupRoleService;
	}

	public void setUserGroupRoleService(
		UserGroupRoleService userGroupRoleService) {
		this.userGroupRoleService = userGroupRoleService;
	}

	public UserGroupRolePersistence getUserGroupRolePersistence() {
		return userGroupRolePersistence;
	}

	public void setUserGroupRolePersistence(
		UserGroupRolePersistence userGroupRolePersistence) {
		this.userGroupRolePersistence = userGroupRolePersistence;
	}

	public UserIdMapperLocalService getUserIdMapperLocalService() {
		return userIdMapperLocalService;
	}

	public void setUserIdMapperLocalService(
		UserIdMapperLocalService userIdMapperLocalService) {
		this.userIdMapperLocalService = userIdMapperLocalService;
	}

	public UserIdMapperPersistence getUserIdMapperPersistence() {
		return userIdMapperPersistence;
	}

	public void setUserIdMapperPersistence(
		UserIdMapperPersistence userIdMapperPersistence) {
		this.userIdMapperPersistence = userIdMapperPersistence;
	}

	public UserTrackerLocalService getUserTrackerLocalService() {
		return userTrackerLocalService;
	}

	public void setUserTrackerLocalService(
		UserTrackerLocalService userTrackerLocalService) {
		this.userTrackerLocalService = userTrackerLocalService;
	}

	public UserTrackerPersistence getUserTrackerPersistence() {
		return userTrackerPersistence;
	}

	public void setUserTrackerPersistence(
		UserTrackerPersistence userTrackerPersistence) {
		this.userTrackerPersistence = userTrackerPersistence;
	}

	public UserTrackerPathLocalService getUserTrackerPathLocalService() {
		return userTrackerPathLocalService;
	}

	public void setUserTrackerPathLocalService(
		UserTrackerPathLocalService userTrackerPathLocalService) {
		this.userTrackerPathLocalService = userTrackerPathLocalService;
	}

	public UserTrackerPathPersistence getUserTrackerPathPersistence() {
		return userTrackerPathPersistence;
	}

	public void setUserTrackerPathPersistence(
		UserTrackerPathPersistence userTrackerPathPersistence) {
		this.userTrackerPathPersistence = userTrackerPathPersistence;
	}

	public WebDAVPropsLocalService getWebDAVPropsLocalService() {
		return webDAVPropsLocalService;
	}

	public void setWebDAVPropsLocalService(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		this.webDAVPropsLocalService = webDAVPropsLocalService;
	}

	public WebDAVPropsPersistence getWebDAVPropsPersistence() {
		return webDAVPropsPersistence;
	}

	public void setWebDAVPropsPersistence(
		WebDAVPropsPersistence webDAVPropsPersistence) {
		this.webDAVPropsPersistence = webDAVPropsPersistence;
	}

	public WebsiteLocalService getWebsiteLocalService() {
		return websiteLocalService;
	}

	public void setWebsiteLocalService(WebsiteLocalService websiteLocalService) {
		this.websiteLocalService = websiteLocalService;
	}

	public WebsiteService getWebsiteService() {
		return websiteService;
	}

	public void setWebsiteService(WebsiteService websiteService) {
		this.websiteService = websiteService;
	}

	public WebsitePersistence getWebsitePersistence() {
		return websitePersistence;
	}

	public void setWebsitePersistence(WebsitePersistence websitePersistence) {
		this.websitePersistence = websitePersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public void afterPropertiesSet() {
		if (accountLocalService == null) {
			accountLocalService = (AccountLocalService)PortalBeanLocatorUtil.locate(AccountLocalService.class.getName() +
					".impl");
		}

		if (accountService == null) {
			accountService = (AccountService)PortalBeanLocatorUtil.locate(AccountService.class.getName() +
					".impl");
		}

		if (accountPersistence == null) {
			accountPersistence = (AccountPersistence)PortalBeanLocatorUtil.locate(AccountPersistence.class.getName() +
					".impl");
		}

		if (addressLocalService == null) {
			addressLocalService = (AddressLocalService)PortalBeanLocatorUtil.locate(AddressLocalService.class.getName() +
					".impl");
		}

		if (addressService == null) {
			addressService = (AddressService)PortalBeanLocatorUtil.locate(AddressService.class.getName() +
					".impl");
		}

		if (addressPersistence == null) {
			addressPersistence = (AddressPersistence)PortalBeanLocatorUtil.locate(AddressPersistence.class.getName() +
					".impl");
		}

		if (classNameLocalService == null) {
			classNameLocalService = (ClassNameLocalService)PortalBeanLocatorUtil.locate(ClassNameLocalService.class.getName() +
					".impl");
		}

		if (classNameService == null) {
			classNameService = (ClassNameService)PortalBeanLocatorUtil.locate(ClassNameService.class.getName() +
					".impl");
		}

		if (classNamePersistence == null) {
			classNamePersistence = (ClassNamePersistence)PortalBeanLocatorUtil.locate(ClassNamePersistence.class.getName() +
					".impl");
		}

		if (companyLocalService == null) {
			companyLocalService = (CompanyLocalService)PortalBeanLocatorUtil.locate(CompanyLocalService.class.getName() +
					".impl");
		}

		if (companyService == null) {
			companyService = (CompanyService)PortalBeanLocatorUtil.locate(CompanyService.class.getName() +
					".impl");
		}

		if (companyPersistence == null) {
			companyPersistence = (CompanyPersistence)PortalBeanLocatorUtil.locate(CompanyPersistence.class.getName() +
					".impl");
		}

		if (contactLocalService == null) {
			contactLocalService = (ContactLocalService)PortalBeanLocatorUtil.locate(ContactLocalService.class.getName() +
					".impl");
		}

		if (contactService == null) {
			contactService = (ContactService)PortalBeanLocatorUtil.locate(ContactService.class.getName() +
					".impl");
		}

		if (contactPersistence == null) {
			contactPersistence = (ContactPersistence)PortalBeanLocatorUtil.locate(ContactPersistence.class.getName() +
					".impl");
		}

		if (countryService == null) {
			countryService = (CountryService)PortalBeanLocatorUtil.locate(CountryService.class.getName() +
					".impl");
		}

		if (countryPersistence == null) {
			countryPersistence = (CountryPersistence)PortalBeanLocatorUtil.locate(CountryPersistence.class.getName() +
					".impl");
		}

		if (emailAddressLocalService == null) {
			emailAddressLocalService = (EmailAddressLocalService)PortalBeanLocatorUtil.locate(EmailAddressLocalService.class.getName() +
					".impl");
		}

		if (emailAddressService == null) {
			emailAddressService = (EmailAddressService)PortalBeanLocatorUtil.locate(EmailAddressService.class.getName() +
					".impl");
		}

		if (emailAddressPersistence == null) {
			emailAddressPersistence = (EmailAddressPersistence)PortalBeanLocatorUtil.locate(EmailAddressPersistence.class.getName() +
					".impl");
		}

		if (groupLocalService == null) {
			groupLocalService = (GroupLocalService)PortalBeanLocatorUtil.locate(GroupLocalService.class.getName() +
					".impl");
		}

		if (groupService == null) {
			groupService = (GroupService)PortalBeanLocatorUtil.locate(GroupService.class.getName() +
					".impl");
		}

		if (groupPersistence == null) {
			groupPersistence = (GroupPersistence)PortalBeanLocatorUtil.locate(GroupPersistence.class.getName() +
					".impl");
		}

		if (groupFinder == null) {
			groupFinder = (GroupFinder)PortalBeanLocatorUtil.locate(GroupFinder.class.getName() +
					".impl");
		}

		if (imageLocalService == null) {
			imageLocalService = (ImageLocalService)PortalBeanLocatorUtil.locate(ImageLocalService.class.getName() +
					".impl");
		}

		if (imagePersistence == null) {
			imagePersistence = (ImagePersistence)PortalBeanLocatorUtil.locate(ImagePersistence.class.getName() +
					".impl");
		}

		if (layoutLocalService == null) {
			layoutLocalService = (LayoutLocalService)PortalBeanLocatorUtil.locate(LayoutLocalService.class.getName() +
					".impl");
		}

		if (layoutService == null) {
			layoutService = (LayoutService)PortalBeanLocatorUtil.locate(LayoutService.class.getName() +
					".impl");
		}

		if (layoutPersistence == null) {
			layoutPersistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName() +
					".impl");
		}

		if (layoutFinder == null) {
			layoutFinder = (LayoutFinder)PortalBeanLocatorUtil.locate(LayoutFinder.class.getName() +
					".impl");
		}

		if (layoutSetLocalService == null) {
			layoutSetLocalService = (LayoutSetLocalService)PortalBeanLocatorUtil.locate(LayoutSetLocalService.class.getName() +
					".impl");
		}

		if (layoutSetService == null) {
			layoutSetService = (LayoutSetService)PortalBeanLocatorUtil.locate(LayoutSetService.class.getName() +
					".impl");
		}

		if (layoutSetPersistence == null) {
			layoutSetPersistence = (LayoutSetPersistence)PortalBeanLocatorUtil.locate(LayoutSetPersistence.class.getName() +
					".impl");
		}

		if (layoutTemplateLocalService == null) {
			layoutTemplateLocalService = (LayoutTemplateLocalService)PortalBeanLocatorUtil.locate(LayoutTemplateLocalService.class.getName() +
					".impl");
		}

		if (listTypeService == null) {
			listTypeService = (ListTypeService)PortalBeanLocatorUtil.locate(ListTypeService.class.getName() +
					".impl");
		}

		if (listTypePersistence == null) {
			listTypePersistence = (ListTypePersistence)PortalBeanLocatorUtil.locate(ListTypePersistence.class.getName() +
					".impl");
		}

		if (membershipRequestLocalService == null) {
			membershipRequestLocalService = (MembershipRequestLocalService)PortalBeanLocatorUtil.locate(MembershipRequestLocalService.class.getName() +
					".impl");
		}

		if (membershipRequestService == null) {
			membershipRequestService = (MembershipRequestService)PortalBeanLocatorUtil.locate(MembershipRequestService.class.getName() +
					".impl");
		}

		if (membershipRequestPersistence == null) {
			membershipRequestPersistence = (MembershipRequestPersistence)PortalBeanLocatorUtil.locate(MembershipRequestPersistence.class.getName() +
					".impl");
		}

		if (organizationLocalService == null) {
			organizationLocalService = (OrganizationLocalService)PortalBeanLocatorUtil.locate(OrganizationLocalService.class.getName() +
					".impl");
		}

		if (organizationService == null) {
			organizationService = (OrganizationService)PortalBeanLocatorUtil.locate(OrganizationService.class.getName() +
					".impl");
		}

		if (organizationPersistence == null) {
			organizationPersistence = (OrganizationPersistence)PortalBeanLocatorUtil.locate(OrganizationPersistence.class.getName() +
					".impl");
		}

		if (organizationFinder == null) {
			organizationFinder = (OrganizationFinder)PortalBeanLocatorUtil.locate(OrganizationFinder.class.getName() +
					".impl");
		}

		if (orgGroupPermissionPersistence == null) {
			orgGroupPermissionPersistence = (OrgGroupPermissionPersistence)PortalBeanLocatorUtil.locate(OrgGroupPermissionPersistence.class.getName() +
					".impl");
		}

		if (orgGroupPermissionFinder == null) {
			orgGroupPermissionFinder = (OrgGroupPermissionFinder)PortalBeanLocatorUtil.locate(OrgGroupPermissionFinder.class.getName() +
					".impl");
		}

		if (orgGroupRolePersistence == null) {
			orgGroupRolePersistence = (OrgGroupRolePersistence)PortalBeanLocatorUtil.locate(OrgGroupRolePersistence.class.getName() +
					".impl");
		}

		if (orgLaborLocalService == null) {
			orgLaborLocalService = (OrgLaborLocalService)PortalBeanLocatorUtil.locate(OrgLaborLocalService.class.getName() +
					".impl");
		}

		if (orgLaborService == null) {
			orgLaborService = (OrgLaborService)PortalBeanLocatorUtil.locate(OrgLaborService.class.getName() +
					".impl");
		}

		if (orgLaborPersistence == null) {
			orgLaborPersistence = (OrgLaborPersistence)PortalBeanLocatorUtil.locate(OrgLaborPersistence.class.getName() +
					".impl");
		}

		if (passwordPolicyLocalService == null) {
			passwordPolicyLocalService = (PasswordPolicyLocalService)PortalBeanLocatorUtil.locate(PasswordPolicyLocalService.class.getName() +
					".impl");
		}

		if (passwordPolicyService == null) {
			passwordPolicyService = (PasswordPolicyService)PortalBeanLocatorUtil.locate(PasswordPolicyService.class.getName() +
					".impl");
		}

		if (passwordPolicyPersistence == null) {
			passwordPolicyPersistence = (PasswordPolicyPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyPersistence.class.getName() +
					".impl");
		}

		if (passwordPolicyFinder == null) {
			passwordPolicyFinder = (PasswordPolicyFinder)PortalBeanLocatorUtil.locate(PasswordPolicyFinder.class.getName() +
					".impl");
		}

		if (passwordPolicyRelLocalService == null) {
			passwordPolicyRelLocalService = (PasswordPolicyRelLocalService)PortalBeanLocatorUtil.locate(PasswordPolicyRelLocalService.class.getName() +
					".impl");
		}

		if (passwordPolicyRelPersistence == null) {
			passwordPolicyRelPersistence = (PasswordPolicyRelPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyRelPersistence.class.getName() +
					".impl");
		}

		if (passwordTrackerLocalService == null) {
			passwordTrackerLocalService = (PasswordTrackerLocalService)PortalBeanLocatorUtil.locate(PasswordTrackerLocalService.class.getName() +
					".impl");
		}

		if (passwordTrackerPersistence == null) {
			passwordTrackerPersistence = (PasswordTrackerPersistence)PortalBeanLocatorUtil.locate(PasswordTrackerPersistence.class.getName() +
					".impl");
		}

		if (permissionLocalService == null) {
			permissionLocalService = (PermissionLocalService)PortalBeanLocatorUtil.locate(PermissionLocalService.class.getName() +
					".impl");
		}

		if (permissionService == null) {
			permissionService = (PermissionService)PortalBeanLocatorUtil.locate(PermissionService.class.getName() +
					".impl");
		}

		if (permissionPersistence == null) {
			permissionPersistence = (PermissionPersistence)PortalBeanLocatorUtil.locate(PermissionPersistence.class.getName() +
					".impl");
		}

		if (permissionFinder == null) {
			permissionFinder = (PermissionFinder)PortalBeanLocatorUtil.locate(PermissionFinder.class.getName() +
					".impl");
		}

		if (permissionUserFinder == null) {
			permissionUserFinder = (PermissionUserFinder)PortalBeanLocatorUtil.locate(PermissionUserFinder.class.getName() +
					".impl");
		}

		if (phoneLocalService == null) {
			phoneLocalService = (PhoneLocalService)PortalBeanLocatorUtil.locate(PhoneLocalService.class.getName() +
					".impl");
		}

		if (phoneService == null) {
			phoneService = (PhoneService)PortalBeanLocatorUtil.locate(PhoneService.class.getName() +
					".impl");
		}

		if (phonePersistence == null) {
			phonePersistence = (PhonePersistence)PortalBeanLocatorUtil.locate(PhonePersistence.class.getName() +
					".impl");
		}

		if (portalService == null) {
			portalService = (PortalService)PortalBeanLocatorUtil.locate(PortalService.class.getName() +
					".impl");
		}

		if (pluginSettingLocalService == null) {
			pluginSettingLocalService = (PluginSettingLocalService)PortalBeanLocatorUtil.locate(PluginSettingLocalService.class.getName() +
					".impl");
		}

		if (pluginSettingService == null) {
			pluginSettingService = (PluginSettingService)PortalBeanLocatorUtil.locate(PluginSettingService.class.getName() +
					".impl");
		}

		if (pluginSettingPersistence == null) {
			pluginSettingPersistence = (PluginSettingPersistence)PortalBeanLocatorUtil.locate(PluginSettingPersistence.class.getName() +
					".impl");
		}

		if (portletLocalService == null) {
			portletLocalService = (PortletLocalService)PortalBeanLocatorUtil.locate(PortletLocalService.class.getName() +
					".impl");
		}

		if (portletService == null) {
			portletService = (PortletService)PortalBeanLocatorUtil.locate(PortletService.class.getName() +
					".impl");
		}

		if (portletPersistence == null) {
			portletPersistence = (PortletPersistence)PortalBeanLocatorUtil.locate(PortletPersistence.class.getName() +
					".impl");
		}

		if (portletPreferencesLocalService == null) {
			portletPreferencesLocalService = (PortletPreferencesLocalService)PortalBeanLocatorUtil.locate(PortletPreferencesLocalService.class.getName() +
					".impl");
		}

		if (portletPreferencesService == null) {
			portletPreferencesService = (PortletPreferencesService)PortalBeanLocatorUtil.locate(PortletPreferencesService.class.getName() +
					".impl");
		}

		if (portletPreferencesPersistence == null) {
			portletPreferencesPersistence = (PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(PortletPreferencesPersistence.class.getName() +
					".impl");
		}

		if (portletPreferencesFinder == null) {
			portletPreferencesFinder = (PortletPreferencesFinder)PortalBeanLocatorUtil.locate(PortletPreferencesFinder.class.getName() +
					".impl");
		}

		if (regionService == null) {
			regionService = (RegionService)PortalBeanLocatorUtil.locate(RegionService.class.getName() +
					".impl");
		}

		if (regionPersistence == null) {
			regionPersistence = (RegionPersistence)PortalBeanLocatorUtil.locate(RegionPersistence.class.getName() +
					".impl");
		}

		if (releaseLocalService == null) {
			releaseLocalService = (ReleaseLocalService)PortalBeanLocatorUtil.locate(ReleaseLocalService.class.getName() +
					".impl");
		}

		if (releasePersistence == null) {
			releasePersistence = (ReleasePersistence)PortalBeanLocatorUtil.locate(ReleasePersistence.class.getName() +
					".impl");
		}

		if (resourceLocalService == null) {
			resourceLocalService = (ResourceLocalService)PortalBeanLocatorUtil.locate(ResourceLocalService.class.getName() +
					".impl");
		}

		if (resourceService == null) {
			resourceService = (ResourceService)PortalBeanLocatorUtil.locate(ResourceService.class.getName() +
					".impl");
		}

		if (resourcePersistence == null) {
			resourcePersistence = (ResourcePersistence)PortalBeanLocatorUtil.locate(ResourcePersistence.class.getName() +
					".impl");
		}

		if (resourceFinder == null) {
			resourceFinder = (ResourceFinder)PortalBeanLocatorUtil.locate(ResourceFinder.class.getName() +
					".impl");
		}

		if (resourceCodeLocalService == null) {
			resourceCodeLocalService = (ResourceCodeLocalService)PortalBeanLocatorUtil.locate(ResourceCodeLocalService.class.getName() +
					".impl");
		}

		if (resourceCodePersistence == null) {
			resourceCodePersistence = (ResourceCodePersistence)PortalBeanLocatorUtil.locate(ResourceCodePersistence.class.getName() +
					".impl");
		}

		if (rolePersistence == null) {
			rolePersistence = (RolePersistence)PortalBeanLocatorUtil.locate(RolePersistence.class.getName() +
					".impl");
		}

		if (roleFinder == null) {
			roleFinder = (RoleFinder)PortalBeanLocatorUtil.locate(RoleFinder.class.getName() +
					".impl");
		}

		if (serviceComponentLocalService == null) {
			serviceComponentLocalService = (ServiceComponentLocalService)PortalBeanLocatorUtil.locate(ServiceComponentLocalService.class.getName() +
					".impl");
		}

		if (serviceComponentPersistence == null) {
			serviceComponentPersistence = (ServiceComponentPersistence)PortalBeanLocatorUtil.locate(ServiceComponentPersistence.class.getName() +
					".impl");
		}

		if (portletItemLocalService == null) {
			portletItemLocalService = (PortletItemLocalService)PortalBeanLocatorUtil.locate(PortletItemLocalService.class.getName() +
					".impl");
		}

		if (portletItemPersistence == null) {
			portletItemPersistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName() +
					".impl");
		}

		if (subscriptionLocalService == null) {
			subscriptionLocalService = (SubscriptionLocalService)PortalBeanLocatorUtil.locate(SubscriptionLocalService.class.getName() +
					".impl");
		}

		if (subscriptionPersistence == null) {
			subscriptionPersistence = (SubscriptionPersistence)PortalBeanLocatorUtil.locate(SubscriptionPersistence.class.getName() +
					".impl");
		}

		if (themeLocalService == null) {
			themeLocalService = (ThemeLocalService)PortalBeanLocatorUtil.locate(ThemeLocalService.class.getName() +
					".impl");
		}

		if (userLocalService == null) {
			userLocalService = (UserLocalService)PortalBeanLocatorUtil.locate(UserLocalService.class.getName() +
					".impl");
		}

		if (userService == null) {
			userService = (UserService)PortalBeanLocatorUtil.locate(UserService.class.getName() +
					".impl");
		}

		if (userPersistence == null) {
			userPersistence = (UserPersistence)PortalBeanLocatorUtil.locate(UserPersistence.class.getName() +
					".impl");
		}

		if (userFinder == null) {
			userFinder = (UserFinder)PortalBeanLocatorUtil.locate(UserFinder.class.getName() +
					".impl");
		}

		if (userGroupLocalService == null) {
			userGroupLocalService = (UserGroupLocalService)PortalBeanLocatorUtil.locate(UserGroupLocalService.class.getName() +
					".impl");
		}

		if (userGroupService == null) {
			userGroupService = (UserGroupService)PortalBeanLocatorUtil.locate(UserGroupService.class.getName() +
					".impl");
		}

		if (userGroupPersistence == null) {
			userGroupPersistence = (UserGroupPersistence)PortalBeanLocatorUtil.locate(UserGroupPersistence.class.getName() +
					".impl");
		}

		if (userGroupFinder == null) {
			userGroupFinder = (UserGroupFinder)PortalBeanLocatorUtil.locate(UserGroupFinder.class.getName() +
					".impl");
		}

		if (userGroupRoleLocalService == null) {
			userGroupRoleLocalService = (UserGroupRoleLocalService)PortalBeanLocatorUtil.locate(UserGroupRoleLocalService.class.getName() +
					".impl");
		}

		if (userGroupRoleService == null) {
			userGroupRoleService = (UserGroupRoleService)PortalBeanLocatorUtil.locate(UserGroupRoleService.class.getName() +
					".impl");
		}

		if (userGroupRolePersistence == null) {
			userGroupRolePersistence = (UserGroupRolePersistence)PortalBeanLocatorUtil.locate(UserGroupRolePersistence.class.getName() +
					".impl");
		}

		if (userIdMapperLocalService == null) {
			userIdMapperLocalService = (UserIdMapperLocalService)PortalBeanLocatorUtil.locate(UserIdMapperLocalService.class.getName() +
					".impl");
		}

		if (userIdMapperPersistence == null) {
			userIdMapperPersistence = (UserIdMapperPersistence)PortalBeanLocatorUtil.locate(UserIdMapperPersistence.class.getName() +
					".impl");
		}

		if (userTrackerLocalService == null) {
			userTrackerLocalService = (UserTrackerLocalService)PortalBeanLocatorUtil.locate(UserTrackerLocalService.class.getName() +
					".impl");
		}

		if (userTrackerPersistence == null) {
			userTrackerPersistence = (UserTrackerPersistence)PortalBeanLocatorUtil.locate(UserTrackerPersistence.class.getName() +
					".impl");
		}

		if (userTrackerPathLocalService == null) {
			userTrackerPathLocalService = (UserTrackerPathLocalService)PortalBeanLocatorUtil.locate(UserTrackerPathLocalService.class.getName() +
					".impl");
		}

		if (userTrackerPathPersistence == null) {
			userTrackerPathPersistence = (UserTrackerPathPersistence)PortalBeanLocatorUtil.locate(UserTrackerPathPersistence.class.getName() +
					".impl");
		}

		if (webDAVPropsLocalService == null) {
			webDAVPropsLocalService = (WebDAVPropsLocalService)PortalBeanLocatorUtil.locate(WebDAVPropsLocalService.class.getName() +
					".impl");
		}

		if (webDAVPropsPersistence == null) {
			webDAVPropsPersistence = (WebDAVPropsPersistence)PortalBeanLocatorUtil.locate(WebDAVPropsPersistence.class.getName() +
					".impl");
		}

		if (websiteLocalService == null) {
			websiteLocalService = (WebsiteLocalService)PortalBeanLocatorUtil.locate(WebsiteLocalService.class.getName() +
					".impl");
		}

		if (websiteService == null) {
			websiteService = (WebsiteService)PortalBeanLocatorUtil.locate(WebsiteService.class.getName() +
					".impl");
		}

		if (websitePersistence == null) {
			websitePersistence = (WebsitePersistence)PortalBeanLocatorUtil.locate(WebsitePersistence.class.getName() +
					".impl");
		}

		if (counterLocalService == null) {
			counterLocalService = (CounterLocalService)PortalBeanLocatorUtil.locate(CounterLocalService.class.getName() +
					".impl");
		}

		if (counterService == null) {
			counterService = (CounterService)PortalBeanLocatorUtil.locate(CounterService.class.getName() +
					".impl");
		}
	}

	protected AccountLocalService accountLocalService;
	protected AccountService accountService;
	protected AccountPersistence accountPersistence;
	protected AddressLocalService addressLocalService;
	protected AddressService addressService;
	protected AddressPersistence addressPersistence;
	protected ClassNameLocalService classNameLocalService;
	protected ClassNameService classNameService;
	protected ClassNamePersistence classNamePersistence;
	protected CompanyLocalService companyLocalService;
	protected CompanyService companyService;
	protected CompanyPersistence companyPersistence;
	protected ContactLocalService contactLocalService;
	protected ContactService contactService;
	protected ContactPersistence contactPersistence;
	protected CountryService countryService;
	protected CountryPersistence countryPersistence;
	protected EmailAddressLocalService emailAddressLocalService;
	protected EmailAddressService emailAddressService;
	protected EmailAddressPersistence emailAddressPersistence;
	protected GroupLocalService groupLocalService;
	protected GroupService groupService;
	protected GroupPersistence groupPersistence;
	protected GroupFinder groupFinder;
	protected ImageLocalService imageLocalService;
	protected ImagePersistence imagePersistence;
	protected LayoutLocalService layoutLocalService;
	protected LayoutService layoutService;
	protected LayoutPersistence layoutPersistence;
	protected LayoutFinder layoutFinder;
	protected LayoutSetLocalService layoutSetLocalService;
	protected LayoutSetService layoutSetService;
	protected LayoutSetPersistence layoutSetPersistence;
	protected LayoutTemplateLocalService layoutTemplateLocalService;
	protected ListTypeService listTypeService;
	protected ListTypePersistence listTypePersistence;
	protected MembershipRequestLocalService membershipRequestLocalService;
	protected MembershipRequestService membershipRequestService;
	protected MembershipRequestPersistence membershipRequestPersistence;
	protected OrganizationLocalService organizationLocalService;
	protected OrganizationService organizationService;
	protected OrganizationPersistence organizationPersistence;
	protected OrganizationFinder organizationFinder;
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	protected OrgGroupPermissionFinder orgGroupPermissionFinder;
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	protected OrgLaborLocalService orgLaborLocalService;
	protected OrgLaborService orgLaborService;
	protected OrgLaborPersistence orgLaborPersistence;
	protected PasswordPolicyLocalService passwordPolicyLocalService;
	protected PasswordPolicyService passwordPolicyService;
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	protected PasswordPolicyFinder passwordPolicyFinder;
	protected PasswordPolicyRelLocalService passwordPolicyRelLocalService;
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	protected PasswordTrackerLocalService passwordTrackerLocalService;
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	protected PermissionLocalService permissionLocalService;
	protected PermissionService permissionService;
	protected PermissionPersistence permissionPersistence;
	protected PermissionFinder permissionFinder;
	protected PermissionUserFinder permissionUserFinder;
	protected PhoneLocalService phoneLocalService;
	protected PhoneService phoneService;
	protected PhonePersistence phonePersistence;
	protected PortalService portalService;
	protected PluginSettingLocalService pluginSettingLocalService;
	protected PluginSettingService pluginSettingService;
	protected PluginSettingPersistence pluginSettingPersistence;
	protected PortletLocalService portletLocalService;
	protected PortletService portletService;
	protected PortletPersistence portletPersistence;
	protected PortletPreferencesLocalService portletPreferencesLocalService;
	protected PortletPreferencesService portletPreferencesService;
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	protected PortletPreferencesFinder portletPreferencesFinder;
	protected RegionService regionService;
	protected RegionPersistence regionPersistence;
	protected ReleaseLocalService releaseLocalService;
	protected ReleasePersistence releasePersistence;
	protected ResourceLocalService resourceLocalService;
	protected ResourceService resourceService;
	protected ResourcePersistence resourcePersistence;
	protected ResourceFinder resourceFinder;
	protected ResourceCodeLocalService resourceCodeLocalService;
	protected ResourceCodePersistence resourceCodePersistence;
	protected RolePersistence rolePersistence;
	protected RoleFinder roleFinder;
	protected ServiceComponentLocalService serviceComponentLocalService;
	protected ServiceComponentPersistence serviceComponentPersistence;
	protected PortletItemLocalService portletItemLocalService;
	protected PortletItemPersistence portletItemPersistence;
	protected SubscriptionLocalService subscriptionLocalService;
	protected SubscriptionPersistence subscriptionPersistence;
	protected ThemeLocalService themeLocalService;
	protected UserLocalService userLocalService;
	protected UserService userService;
	protected UserPersistence userPersistence;
	protected UserFinder userFinder;
	protected UserGroupLocalService userGroupLocalService;
	protected UserGroupService userGroupService;
	protected UserGroupPersistence userGroupPersistence;
	protected UserGroupFinder userGroupFinder;
	protected UserGroupRoleLocalService userGroupRoleLocalService;
	protected UserGroupRoleService userGroupRoleService;
	protected UserGroupRolePersistence userGroupRolePersistence;
	protected UserIdMapperLocalService userIdMapperLocalService;
	protected UserIdMapperPersistence userIdMapperPersistence;
	protected UserTrackerLocalService userTrackerLocalService;
	protected UserTrackerPersistence userTrackerPersistence;
	protected UserTrackerPathLocalService userTrackerPathLocalService;
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	protected WebDAVPropsLocalService webDAVPropsLocalService;
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	protected WebsiteLocalService websiteLocalService;
	protected WebsiteService websiteService;
	protected WebsitePersistence websitePersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}