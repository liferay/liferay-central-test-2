<#list dataFactory.resourceCodes as resourceCode>
	insert into ResourceCode (codeId, companyId, name, scope) values (${resourceCode.codeId}, ${companyId}, '${resourceCode.name}', ${resourceCode.scope});
</#list>