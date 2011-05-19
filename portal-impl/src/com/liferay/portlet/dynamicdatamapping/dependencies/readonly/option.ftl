<#include "../init.ftl">

<#assign selected = (field.value == fieldValue)>

<#if selected>
	${field.label}
</#if>