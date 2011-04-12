<#assign aui = PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />

<#assign fieldName = field.name>
<#assign fieldValue = "">
<#assign label = field.label!"">
<#assign parentName = parentField.name!"">
<#assign parentType = parentField.type!"">

<#if parentField.name??>
	<#assign fieldName = parentName>
</#if>

<#if fields?? && fields.get(fieldName)??>
	<#assign fieldValue = fields.get(fieldName).getValue()>
</#if>

<#if field.showLabel?? && (field.showLabel == "false")>
	<#assign label = "">
</#if>