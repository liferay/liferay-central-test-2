<#setting number_format="###################">
<#setting date_format="yyyy-MM-dd">
<#setting datetime_format="yyyy-MM-dd HH:mm:ss">
<#setting boolean_format="1,0">
<#-- Insert user -->
INSERT INTO User_ (uuid_, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, passwordModifiedDate, graceLoginCount, screenName, emailAddress, openId, portraitId, languageId, timeZoneId, greeting, comments, loginDate, loginIP, lastLoginDate, lastLoginIP, lastFailedLoginDate, failedLoginAttempts, lockout, lockoutDate, agreedToTermsOfUse, active_, userId) VALUES ('${user.uuid}', ${user.companyId}, '${user.createDate?datetime}', '${user.modifiedDate?datetime}', ${user.defaultUser?string}, ${user.contact.contactId}, '${user.password}', ${user.passwordEncrypted?string}, ${user.passwordReset?string}, '${user.passwordModifiedDate?datetime}', ${user.graceLoginCount}, '${user.screenName}', '${user.emailAddress}', '${user.openId}', ${user.portraitId},'${user.languageId}', '${user.timeZoneId}', '${user.greeting}', '${user.comments}', <#if user.loginDate??>'${user.loginDate?datetime}'<#else>null</#if>, <#if user.loginIP??>'${user.loginIP}'<#else>null</#if>, <#if user.lastLoginDate??>'${user.lastLoginDate?datetime}'<#else>null</#if>, <#if user.lastLoginIP??>'${user.lastLoginIP}'<#else>null</#if>, <#if user.lastFailedLoginDate??>'${user.lastFailedLoginDate?datetime}'<#else>null</#if>, ${user.failedLoginAttempts}, ${user.lockout?string}, <#if user.lockoutDate??>'${user.lockoutDate?datetime}'<#else>null</#if>, ${user.agreedToTermsOfUse?string}, ${user.active?string}, ${user.userId});
<#-- Insert contact information for user -->
INSERT INTO Contact_ (companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, prefixId, suffixId, male, birthday, smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn, employeeStatusId, employeeNumber, jobTitle, jobClass, hoursOfOperation, contactId) VALUES (${user.contact.companyId}, ${user.contact.userId}, '${user.contact.userName}', '${user.contact.createDate?datetime}', '${user.contact.modifiedDate?datetime}', ${user.contact.accountId}, ${user.contact.parentContactId}, '${user.contact.firstName}', '${user.contact.middleName}', '${user.contact.lastName}', ${user.contact.prefixId}, ${user.contact.suffixId}, ${user.contact.male?string}, '${user.contact.birthday?date} 00:00:00', '${user.contact.smsSn}', '${user.contact.aimSn}', '${user.contact.facebookSn}', '${user.contact.icqSn}', '${user.contact.jabberSn}', '${user.contact.msnSn}', '${user.contact.mySpaceSn}', '${user.contact.skypeSn}', '${user.contact.twitterSn}', '${user.contact.ymSn}', '${user.contact.employeeStatusId}', '${user.contact.employeeNumber}', '${user.contact.jobTitle}', '${user.contact.jobClass}', '${user.contact.hoursOfOperation}', ${user.contact.contactId});
<#-- Insert resource for the user.  This is used to secure the user -->
INSERT INTO Resource_ (codeId, primKey, resourceId) VALUES (${resource.codeId}, ${resource.primKey}, '${resource.resourceId}');
<#-- Insert permissions for the inserted user (DELETE, IMPERSONATE, PERMISSIONS, UPDATE, VIEW) -->
<#call create_permission(permissions) >
<#-- Create a group specifically for the user to hold the user's public and private pages -->
INSERT INTO Group_ (companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, description, type_, typeSettings, friendlyURL, active_, groupId) VALUES (${user.privateGroup.companyId}, ${user.privateGroup.creatorUserId}, ${user.privateGroup.classNameId}, ${user.privateGroup.classPK}, ${user.privateGroup.parentGroupId}, ${user.privateGroup.liveGroupId}, '${user.privateGroup.name}', '${user.privateGroup.description}',${user.privateGroup.type},'${user.privateGroup.typeSettings}','${user.privateGroup.friendlyURL}',${user.privateGroup.active?string},${user.privateGroup.groupId});
<#-- Add public and private page containers for user -->
<#call create_layout_set(user.publicLayoutSet) >
<#call create_layout_set(user.privateLayoutSet) >
<#-- Assign user roles within the portal -->
<#if user.roles??>
    <#list user.roles as role>
INSERT INTO Users_Roles (userId, roleId) VALUES (${user.userId}, ${role.roleId});
    </#list>
</#if>
<#-- Assign users to the proper communities-->
<#if user.communities??>
    <#list user.communities as community>
INSERT INTO Users_Groups (${user.userId}}, ${community.groupId});
    </#list>   
</#if>
<#-- assign the user a specific role within a user.group -->
<#if user.communityRoles??>
    <#list user.communityRoles as communityRole>
INSERT INTO UserGroupRole (userId, groupId, roleId) VALUES (${user.userId}, ${communityRole.groupId}, ${communityRole.roleId});
    </#list>
</#if>
<#-- Assign users to the proper organizations-->
<#if user.organizations??>
    <#list user.organizations as organization>
INSERT INTO User_Orgs (${user.userId}}, ${organization.organizationId});
    </#list>
</#if>
<#-- assign the user a specific role within a user.group -->
<#if user.organizationRoles??>
    <#list user.organizationRoles as organizationRole>
<#--INSERT INTO UserGroupRole (userId, groupId, roleId) VALUES (${user.userId}, ${communityRole.groupId}, ${communityRole.roleId});-->
    </#list>
</#if>

<#function create_permission(permissions)>
      <#list permissions as permission>
INSERT INTO Permission_ (companyId, actionId, resourceId, permissionId) VALUES (${permission.companyId}, '${permission.actionId}', ${permission.resourceId}, ${permission.permissionId});
      </#list>
</#function>
<#function create_layout_set(layoutSet)>
INSERT INTO LayoutSet (groupId, companyId, privateLayout, logo, logoId, themeId, colorSchemeId, wapThemeId, wapColorSchemeId, css, pageCount, virtualHost, layoutSetId) VALUES (${layoutSet.groupId}, ${layoutSet.companyId}, ${layoutSet.privateLayout?string}, ${layoutSet.logo?string}, ${layoutSet.logoId}, '${layoutSet.themeId}', '${layoutSet.colorSchemeId}','${layoutSet.wapThemeId}', '${layoutSet.wapColorSchemeId}', <#if layoutSet.css??>'${layoutSet.css}'<#else>null</#if>, ${layoutSet.pageCount}, '${layoutSet.virtualHost}', ${layoutSet.layoutSetId});
</#function>