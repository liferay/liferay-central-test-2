<#include "../init.ftl">

<div class="lfr-forms-field-wrapper yui3-aui-field-radio yui3-aui-field-wrapper-content">
	<label class="yui3-aui-field-label">
		<@liferay_ui.message key=label />

		<#if required>
			<span class="yui3-aui-label-required">(<@liferay_ui.message key="required" />)</span>
		</#if>
	</label>

	${field.children}
</div>