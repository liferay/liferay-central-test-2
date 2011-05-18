<!-- Tag libraries -->

<#assign aui = PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<!-- CSS class -->

<#assign cssClass = field.fieldCssClass!"">

<!-- Field name -->

<#assign fieldName = field.name>

<#assign parentName = parentField.name!"">
<#assign parentType = parentField.type!"">

<#if parentName?? && (parentName != "") && ((parentType == "radio") || (parentType == "select"))>
	<#assign fieldName = parentName>
</#if>

<#assign namespacedFieldName = "${namespace}${fieldName}">

<!-- Field value -->

<#assign fieldValue = "">

<#if fields?? && fields.get(fieldName)??>
	<#assign fieldValue = fields.get(fieldName).getValue()>
</#if>

<!-- Label -->

<#assign label = field.label!"">

<#if field.showLabel?? && (field.showLabel == "false")>
	<#assign label = "">
</#if>

<!-- Required -->

<#assign required = false>

<#if field.required?? && (field.required == "true")>
	<#assign required = true>
</#if>