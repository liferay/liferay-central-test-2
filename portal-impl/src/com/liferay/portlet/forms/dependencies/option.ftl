<#include "init.ftl">

<#if parentType == "select">
	<@aui.option label=field.name value=field.value />
<#else>
	<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
		<@aui.input label=field.name name=parentName type="radio" value=field.value />
	</div>
</#if>