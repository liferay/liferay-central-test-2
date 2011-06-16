<#include "../init.ftl">

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.select cssClass=cssClass helpMessage=field.tip label=label name=namespacedFieldName>
		${field.children}
	</@aui.select>
</div>