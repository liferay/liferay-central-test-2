<#include "../init.ftl">

<div class="yui3-aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.select helpMessage=field.tip label=label name=namespacedFieldName>
		${field.children}
	</@aui.select>
</div>