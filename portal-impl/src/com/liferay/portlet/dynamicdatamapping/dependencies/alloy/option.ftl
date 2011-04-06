<#include "../init.ftl">

<#if parentType == "select">
	<@aui.option label=field.name value=field.value />
<#else>
	<@aui.input label=field.name name=parentName type="radio" value=field.value />
</#if>