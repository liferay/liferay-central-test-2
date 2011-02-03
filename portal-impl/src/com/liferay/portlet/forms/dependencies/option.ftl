<#include "init.ftl">

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<#if parentType == "select">
		<@aui.option label=field.name value=field.value />
	<#else>
		<@aui.input label=field.name name=parentName type="radio" value=field.value />
	</#if>
</div>