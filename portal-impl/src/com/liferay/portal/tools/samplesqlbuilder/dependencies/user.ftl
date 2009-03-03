insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('${uuid}', ${userId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE, ${contactId}, 'test', FALSE, FALSE, '${screenName}', '${emailAddress}', 'Welcome ${userName}!', CURRENT_TIMESTAMP, 0, TRUE, TRUE);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, accountId, parentContactId, firstName, middleName, lastName, male, birthday) values (${contactId}, ${companyId}, ${userId}, '${userName}', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${accountId}, 0, '${firstName}', '', '${lastName}', TRUE, '01/01/1970');

<#if groupId??>
	insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, friendlyURL, active_) values (${groupId}, ${companyId}, ${userId}, ${userClassNameId}, ${userId}, 0, 0, '${groupId}', '${friendlyURL}', TRUE);
	insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (${counter.getString()}, ${companyId}, ${groupId}, TRUE, FALSE, 'classic', '01', ${privateLayouts?size});
	insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (${counter.getString()}, ${companyId}, ${groupId}, FALSE, FALSE, 'classic', '01', ${publicLayouts?size});

	<#list privateLayouts as layout>
		insert into Layout (plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values (${counter.get()}, ${groupId}, ${companyId}, TRUE, 1, 0, '<?xml version="1.0"?>\n\n<root>\n  <name>${layout.name}</name>\n</root>', 'portlet', 'layout-template-id=2_columns_ii\ncolumn-1=3,\ncolumn-2=19,', FALSE, '${layout.friendlyURL}', 0);
	</#list>

	<#list publicLayouts as layout>
		insert into Layout (plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values (${counter.get()}, ${groupId}, ${companyId}, FALSE, 1, 0, '<?xml version="1.0"?>\n\n<root>\n  <name>${layout.name}</name>\n</root>', 'portlet', 'layout-template-id=2_columns_ii\ncolumn-1=3,\ncolumn-2=19,', FALSE, '${layout.friendlyURL}', 0);
	</#list>
</#if>

<#if roles??>
	<#list roles as role>
		insert into Users_Roles (userId, roleId) values (${userId}, ${role.roleId});
	</#list>
</#if>

<#if groups??>
	<#list groups as group>
		insert into Users_Groups values (${userId}, ${group.groupId});
	</#list>
</#if>

<#if organizations??>
	<#list organizations as organization>
		insert into Users_Orgs (userId, organizationId) values (${userId}, ${organization.organizationId});
	</#list>
</#if>

<#if userGroupRoles??>
    <#list userGroupRoles as userGroupRole>
		insert into UserGroupRole (userId, groupId, roleId) values (${userId}, ${userGroupRole.groupId}, ${userGroupRole.roleId});
    </#list>
</#if>