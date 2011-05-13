<#include "../init.ftl">

<div class="yui3-aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input label=label name=namespacedFieldName type="textarea" value=fieldValue!field.predefinedValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${field.children}
</div>