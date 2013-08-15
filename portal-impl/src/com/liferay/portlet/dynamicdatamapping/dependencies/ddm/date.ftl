<#include "../init.ftl">

<#if (fieldRawValue?is_date)>
	<#assign fieldDateValue = fieldRawValue>
<#else>
	<#if (validator.isNotNull(predefinedValue))>
		<#assign fieldDateValue = dateUtil.parseDate(predefinedValue, requestedLocale)>
	<#else>
		<#assign fieldDateValue = dateUtil.newDate()>
	</#if>
</#if>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label) required=required>
	<@liferay_ui["input-date"]
		cssClass=cssClass
		dayParam="${namespacedFieldName}Day"
		dayValue=fieldDateValue?string("dd")?number
		disabled=false
		monthParam="${namespacedFieldName}Month"
		monthValue=fieldDateValue?string("MM")?number - 1
		name="${namespacedFieldName}"
		yearParam="${namespacedFieldName}Year"
		yearValue=fieldDateValue?string("yyyy")?number
	/>

	${fieldStructure.children}
</@>