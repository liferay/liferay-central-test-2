<#assign aui = PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />

<#assign label = field.label!"">
<#assign parentName = parentField.name!"">
<#assign parentType = parentField.type!"">

<#if field.showLabel?? && (field.showLabel == "false")>
	<#assign label = "">
</#if>