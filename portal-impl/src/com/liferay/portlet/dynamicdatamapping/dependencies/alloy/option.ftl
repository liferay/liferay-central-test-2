<#include "../init.ftl">

<#if fieldValue != "">
	<#assign selected = (field.value == fieldValue)>
<#else>
	<#assign selected = (field.value == parentField.predefinedValue)>
</#if>

<#if parentType == "select">
	<@aui.option cssClass=cssClass label=field.label selected=selected value=field.value />
<#else>
	<@aui.input checked=selected cssClass=cssClass label=field.label name=parentName type="radio" value=field.value />
</#if>