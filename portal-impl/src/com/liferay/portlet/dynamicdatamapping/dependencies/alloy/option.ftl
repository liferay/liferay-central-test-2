<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#if fieldRawValue == "">
	<#assign fieldRawValue = "[]">
</#if>

<#assign selected = jsonFactoryUtil.looseDeserialize(fieldRawValue)?seq_contains(fieldStructure.value)>

<#if parentType == "select">
	<@aui.option cssClass=cssClass label=escapeAttribute(fieldStructure.label) selected=selected value=escape(fieldStructure.value) />
<#else>
	<#assign parentFieldNamespace = "">

	<@aui.input checked=selected cssClass=cssClass label=escape(fieldStructure.label) name=namespacedParentName type="radio" value=fieldStructure.value>
		<#if parentFieldStructure.required?? && (parentFieldStructure.required == "true")>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>
</#if>