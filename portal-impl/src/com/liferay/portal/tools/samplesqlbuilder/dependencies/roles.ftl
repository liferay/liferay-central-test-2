<#list dataFactory.roles as role>
	insert into Role_ (roleId, companyId, classNameId, classPK, name, description, type_) values (${role.roleId}, ${companyId}, 0, 0, '${role.name}', '', 1);
</#list>