<#include "../init.ftl">

<#assign selected = (field.value == fieldValue)>

<#if parentType == "select">
	<@aui.option label=field.name selected=selected value=field.value />
<#else>
	<@aui.input checked=selected label=field.name name=parentName type="radio" value=field.value />
</#if>