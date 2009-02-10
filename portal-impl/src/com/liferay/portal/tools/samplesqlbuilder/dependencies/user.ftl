insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('${userUuid}', ${userId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE, ${contactId}, 'test', FALSE, FALSE, '${screenName}', '${emailAddress}', 'Welcome ${userName}!', CURRENT_TIMESTAMP, 0, TRUE, TRUE);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, male, birthday) values (${contactId}, ${companyId}, ${userId}, '${userName}', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${accountId}, 0, '${firstName}', '', '${lastName}', TRUE, '01/01/1970');

<#if groupId??>
	insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (${groupId}, ${companyId}, ${userId}, ${userClassNameId}, ${userId}, 0, 0, '${groupId}', '${friendlyURL}', TRUE);
	insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (${counter.getString()}, ${companyId}, ${groupId}, TRUE, FALSE, 'classic', '01', 0);
	insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (${counter.getString()}, ${companyId}, ${groupId}, FALSE, FALSE, 'classic', '01', 0);
</#if>

<#if userRoles??>
	<#list userRoles as role>
		insert into Users_Roles (userId, roleId) values (${userId}, ${role.roleId});
	</#list>
</#if>

<#if userGroups??>
	<#list userGroups as group>
		insert into Users_Groups values (${userId}, ${group.groupId});
	</#list>
</#if>

<#if userOrganizations??>
	<#list userOrganizations as organization>
		insert into Users_Orgs (userId, organizationId) values (${userId}, ${organization.organizationId});
	</#list>
</#if>

<#if userGroupRoles??>
    <#list userGroupRoles as userGroupRole>
		insert into UserGroupRole (userId, groupId, roleId) values (${userId}, ${userGroupRole.groupId}, ${userGroupRole.roleId});
    </#list>
</#if>