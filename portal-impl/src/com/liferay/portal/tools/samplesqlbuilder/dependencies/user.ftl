<#setting number_format = "0">

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, loginDate, failedLoginAttempts, agreedToTermsOfUse, active_) values ('${portalUUIDUtil.generate()}', ${user.userId}, ${companyId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, <#if user.defaultUser>TRUE<#else>FALSE</#if>, ${contact.contactId}, 'test', FALSE, FALSE, '${user.screenName}', '${user.emailAddress}', 'Welcome ${contact.fullName}!', CURRENT_TIMESTAMP, 0, TRUE, TRUE);
insert into Contact_ values (${contact.contactId}, ${companyId}, ${user.userId}, '${contact.fullName}', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ${contact.accountId}, 0, '${contact.firstName}', '', '${contact.lastName}', 0, 0, TRUE, '01/01/1970', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');

<#if group??>
	${sampleSQLBuilder.insertGroup(group, privateLayouts, publicLayouts)}
</#if>

<#if roles??>
	<#list roles as role>
		insert into Users_Roles values (${user.userId}, ${role.roleId});
	</#list>
</#if>

<#if groups??>
	<#list groups as group>
		insert into Users_Groups values (${user.userId}, ${group.groupId});
	</#list>
</#if>

<#if organizations??>
	<#list organizations as organization>
		insert into Users_Orgs values (${user.userId}, ${organization.organizationId});
	</#list>
</#if>

<#if userGroupRoles??>
    <#list userGroupRoles as userGroupRole>
		insert into UserGroupRole values (${user.userId}, ${userGroupRole.groupId}, ${userGroupRole.roleId});
    </#list>
</#if>