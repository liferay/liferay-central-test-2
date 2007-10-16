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
 * <a href="UserLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portal.service.impl.UserLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be accessed
 * from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserServiceFactory
 * @see com.liferay.portal.service.UserServiceUtil
 *
 */
public interface UserLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence();

	public void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence);

	public com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence();

	public void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence);

	public com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence();

	public void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence);

	public com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence();

	public void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence);

	public com.liferay.portal.service.persistence.ContactPersistence getContactPersistence();

	public void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence);

	public com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence();

	public void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence);

	public com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence();

	public void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence);

	public com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence();

	public void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence);

	public com.liferay.portal.service.persistence.ImagePersistence getImagePersistence();

	public void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence);

	public com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence();

	public void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence);

	public com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence();

	public void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence);

	public com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence();

	public void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence);

	public com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence();

	public void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence);

	public com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence();

	public void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence);

	public com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence();

	public void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence);

	public com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence();

	public void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence);

	public com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence();

	public void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence);

	public com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence();

	public void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence);

	public com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence();

	public void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence);

	public com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence();

	public void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence);

	public com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence();

	public void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence);

	public com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence();

	public void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence);

	public com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence();

	public void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence);

	public com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence();

	public void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence);

	public com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence();

	public void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence);

	public com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence();

	public void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence);

	public com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence();

	public void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence);

	public com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence();

	public void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence);

	public com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence();

	public void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence);

	public com.liferay.portal.service.persistence.RolePersistence getRolePersistence();

	public void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence);

	public com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence();

	public void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence);

	public com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence();

	public void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence);

	public com.liferay.portal.service.persistence.UserPersistence getUserPersistence();

	public void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence);

	public com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence();

	public void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence);

	public com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence();

	public void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence);

	public com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence();

	public void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence);

	public com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence();

	public void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence);

	public com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence();

	public void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence);

	public com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence();

	public void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence);

	public void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle, long organizationId,
		long locationId, boolean sendEmail)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle, long[] organizationIds,
		boolean sendEmail)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByUserId(long companyId, long userId,
		java.lang.String password, java.util.Map headerMap,
		java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean authenticateForJAAS(long userId, java.lang.String encPwd)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailureById(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void clearUserGroupUsers(long userGroupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deletePasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteUser(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public long getDefaultUserId(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getGroupUsers(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getPermissionUsers(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int getPermissionUsersCount(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getRoleUsers(long roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getUserGroupUsers(long userGroupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByContactId(long contactId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserById(long companyId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByPortraitId(long portraitId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean isPasswordExpiringSoon(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List search(long companyId, java.lang.String keywords,
		java.lang.Boolean active, java.util.LinkedHashMap params, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public java.util.List search(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active, java.util.LinkedHashMap params,
		boolean andSearch, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, java.lang.String keywords,
		java.lang.Boolean active, java.util.LinkedHashMap params)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active, java.util.LinkedHashMap params,
		boolean andSearch) throws com.liferay.portal.SystemException;

	public void sendPassword(long companyId, java.lang.String emailAddress,
		java.lang.String remoteAddr, java.lang.String remoteHost,
		java.lang.String userAgent)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateAgreedToTermsOfUse(long userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateCreateDate(long userId,
		java.util.Date createDate)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updateOrganizations(long userId, long organizationId,
		long locationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updateOrganizations(long userId, long[] organizationIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean silentUpdate)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updatePasswordManually(long userId,
		java.lang.String password, boolean passwordEncrypted,
		boolean passwordReset, java.util.Date passwordModifiedDate)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updatePasswordReset(long userId, boolean passwordReset)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String password, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String icqSn, java.lang.String jabberSn,
		java.lang.String msnSn, java.lang.String skypeSn,
		java.lang.String ymSn, java.lang.String jobTitle, long organizationId,
		long locationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String password, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String icqSn, java.lang.String jabberSn,
		java.lang.String msnSn, java.lang.String skypeSn,
		java.lang.String ymSn, java.lang.String jobTitle, long[] organizationIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}