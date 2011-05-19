<#include "../init.ftl">

<#if (fieldValue == "")>
	<#assign fieldValue = field.predefinedValue>
</#if>

<div class="yui3-aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input cssClass=cssClass helpMessage=field.tip label=label name=namespacedFieldName type="text" value=fieldValue>
		<@aui.validator name="date" />

		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${field.children}
</div>

<@aui.script use="aui-datepicker">
	new A.DatePicker(
		{
			calendar:
			{
				dates:
				[
					<#if (fieldValue != "")>
						'${fieldValue}'
					</#if>
				]
			},
			trigger: '#${portletNamespace}${namespacedFieldName}'
		}
	).render();
</@aui.script>