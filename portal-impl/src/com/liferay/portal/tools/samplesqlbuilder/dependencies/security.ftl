<#setting number_format = "0">

insert into Resource_ (resourceId, codeId, primKey) values (${resource.resourceId}, ${resource.codeId}, '${resource.primKey}');

<#assign permissions = dataFactory.addPermissions(resource)>

<#list permissions as permission>
	insert into Permission_ (permissionId, companyId, actionId, resourceId) values (${permission.permissionId}, ${companyId}, '${permission.actionId}', ${permission.resourceId});
</#list>

<#assign rolesPermissions = dataFactory.addRolesPermissions(resource, permissions, dataFactory.communityMemberRole)>

<#list rolesPermissions as kvp>
	insert into Roles_Permissions (roleId, permissionId) values (${kvp.key}, ${kvp.value});
</#list>