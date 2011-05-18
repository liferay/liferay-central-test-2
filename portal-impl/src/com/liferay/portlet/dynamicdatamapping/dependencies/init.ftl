<#assign aui = PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<#assign cssClass = field.fieldCssClass!"">
<#assign fieldName = field.name>
<#assign namespacedFieldName = "${namespace}${fieldName}">
<#assign parentName = parentField.name!"">
<#assign parentType = parentField.type!"">

<#if parentName?? && (parentName != "") && ((parentType == "radio") || (parentType == "select"))>
	<#assign fieldName = parentName>
</#if>

<#assign fieldValue = "">

<#if fields?? && fields.get(fieldName)??>
	<#assign fieldValue = fields.get(fieldName).getValue()>
</#if>

<#assign label = field.label!"">

<#if field.showLabel?? && (field.showLabel == "false")>
	<#assign label = "">
</#if>

<#assign required = false>

<#if field.required?? && (field.required == "true")>
	<#assign required = true>
</#if>