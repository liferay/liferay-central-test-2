<#include "../init.ftl">

<div class="lfr-forms-field-wrapper aui-field-radio aui-field-wrapper-content">
	<label class="aui-field-label">
		<@liferay_ui.message key=label />

		<#if required>
			<span class="aui-label-required">(<@liferay_ui.message key="required" />)</span>
		</#if>
	</label>

	${field.children}
</div>