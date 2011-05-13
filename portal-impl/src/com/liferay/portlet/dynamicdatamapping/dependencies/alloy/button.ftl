<#include "../init.ftl">

<span class="lfr-forms-field-wrapper lfr-forms-field-wrapper-button">
	<@aui.input cssClass=cssClass helpMessage=field.tip label=label name=namespacedFieldName type="button" value=field.predefinedValue />

	${field.children}
</span>