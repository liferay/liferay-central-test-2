<#include "../init.ftl">

<div class="yui3-aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input label=label name=namespacedName type="textarea" value=fieldValue!field.predefinedValue />

	${field.children}
</div>