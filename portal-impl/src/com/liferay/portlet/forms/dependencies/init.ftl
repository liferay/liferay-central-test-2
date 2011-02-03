<#assign aui = PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />

<#assign label = field.label!"">

<#if field.showLabel?? && (field.showLabel == "false")>
	<#assign label = "">
</#if>

<#assign parentName = "">
<#assign parentType = "">

<#if parentContext??>
	<#assign parentName = parentContext.field.name!"">
	<#assign parentType = parentContext.field.type!"">
</#if>