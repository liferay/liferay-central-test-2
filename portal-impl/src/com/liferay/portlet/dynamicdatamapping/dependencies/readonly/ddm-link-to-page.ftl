<#include "../init.ftl">

<#if (fieldRawValue?? && fieldRawValue != "")>
	<#assign fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

	<#if (fieldLayoutJSONObject.getLong("groupId") > 0)>
		<#assign fieldLayoutGroupId = fieldLayoutJSONObject.getLong("groupId")>
	<#else>
		<#assign fieldLayoutGroupId = scopeGroupId>
	</#if>

	<#assign fieldLayout = layoutLocalService.fetchLayout(fieldLayoutGroupId, fieldLayoutJSONObject.getBoolean("privateLayout"), fieldLayoutJSONObject.getLong("layoutId"))!"">

	<#if (fieldLayout?? && fieldLayout != "")>
		<div class="field-wrapper-content lfr-forms-field-wrapper">
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

			<label>
				<@liferay_ui.message key=escape(label) />
			</label>

			<a href="${fieldLayout.getRegularURL(request)}">${escape(fieldLayout.getName(requestedLocale))}</a>

			${fieldStructure.children}
		</div>
	</#if>
</#if>