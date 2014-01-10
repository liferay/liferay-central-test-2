<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#assign fileEntryTitle = "">

<#if (fieldRawValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileEntry = getFileEntry(fileJSONObject)>

	<#if (fileEntry != "")>
		<#assign fileEntryTitle = fileEntry.getTitle()>
	</#if>
</#if>

<@aui["field-wrapper"] data=data>
	<@aui.input inlineField=true label=escape(label) name="${namespacedFieldName}Title" readonly="readonly" type="text" value=fileEntryTitle>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<@aui["button-row"]>
		<@aui.button id=namespacedFieldName value="select" />

		<@aui.button onClick="window['${portletNamespace}${namespacedFieldName}clearFileEntry']();" value="clear" />
	</@>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />
</@>

<@aui.script>
	window['${portletNamespace}${namespacedFieldName}clearFileEntry'] = function() {
		window['${portletNamespace}${namespacedFieldName}setFileEntry']('', '', '', '', '');
	};

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}setFileEntry',
		function(url, uuid, groupId, title, version) {
			var A = AUI();

			var inputNode = A.one('#${portletNamespace}${namespacedFieldName}');

			if (inputNode) {
				if (uuid) {
					inputNode.val(
						A.JSON.stringify(
							{
								groupId: groupId,
								uuid: uuid,
								version: version
							}
						)
					);
				}
				else {
					inputNode.val('');
				}
			}

			var titleNode = A.one('#${portletNamespace}${namespacedFieldName}Title');

			if (titleNode) {
				titleNode.val(title);
			}
		},
		['json']
	);
</@>

<@aui.script use="liferay-portlet-url">
	var namespacedField = A.one('#${namespacedFieldName}');

	if (namespacedField) {
		namespacedField.on(
			'click',
			function(event) {
				var portletURL = Liferay.PortletURL.createURL('${themeDisplay.getURLControlPanel()}');

				portletURL.setDoAsGroupId(${scopeGroupId?c});
				portletURL.setParameter('groupId', ${scopeGroupId?c});
				portletURL.setParameter('struts_action', '/dynamic_data_mapping/select_document_library');
				portletURL.setPortletId('166');
				portletURL.setWindowState('pop_up');

				Liferay.Util.openWindow(
					{
						id: '${portletNamespace}selectDocumentLibrary',
						uri: portletURL.toString()
					}
				);

				window['${portalUtil.getPortletNamespace("166")}selectDocumentLibrary'] = window['${portletNamespace}${namespacedFieldName}setFileEntry'];
			}
		);
	}
</@>