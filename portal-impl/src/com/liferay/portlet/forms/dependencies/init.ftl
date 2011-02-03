<#assign aui=PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />

<#assign label = field.label!"">
<#assign parentType = field.parentType!"">

<#if field.showLabel?? && (field.showLabel == "false")>
	<#assign label = "">
</#if>