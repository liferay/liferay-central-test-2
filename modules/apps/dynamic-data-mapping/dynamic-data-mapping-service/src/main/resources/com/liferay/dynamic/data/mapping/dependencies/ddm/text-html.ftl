<#include "../init.ftl">

<#assign editorName = propsUtil.get("editor.wysiwyg.portal-impl.portlet.ddm.text_html.ftl")>

<#assign inputEditorName = "${namespacedFieldName}Editor">

<#assign fieldValue = paramUtil.getString(request, "${inputEditorName}", fieldValue)>

<@aui["field-wrapper"] cssClass="field-wrapper-html" data=data helpMessage=escape(fieldStructure.tip) label=escape(label) name=inputEditorName required=required>
	<#assign skipEditorLoading = paramUtil.getBoolean(request, "p_p_isolated")>

	<@liferay_ui["input-editor"]
		contentsLanguageId="${requestedLocale}"
		cssClass="form-control"
		editorName="${editorName}"
		initMethod=""
		name="${namespacedFieldName}Editor"
		onChangeMethod="${namespacedFieldName}OnChangeEditor"
		skipEditorLoading=skipEditorLoading
	/>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@>

	${fieldStructure.children}

	<@aui.script>
		Liferay.provide(
			window,
			'${portletNamespace}${namespacedFieldName}OnChangeEditor',
			function() {
				var A = AUI();

				var field = A.one('#${portletNamespace}${namespacedFieldName}');

				field.val(window['${portletNamespace}${namespacedFieldName}Editor'].getHTML());

				var form = field.get('form');

				if (form) {
					var formName = form.get('name');

					var formValidator = Liferay.Form.get(formName).formValidator;

					if (formValidator) {
						formValidator.validateField(field);
					}
				}
			},
			['liferay-form']
		);
	</@>
</@>