<%--
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
DLFileEntry fileEntry = null;
Set<String> keys = null;
List<String> keysList = null;
List<String> duplicateNames = null;

try {
	fileEntry = DLRepositoryLocalServiceUtil.getExtraSettingsFileEntries(0, 1).get(0);

	List<DLFileVersion> fileVersions = fileEntry.getFileVersions(WorkflowConstants.STATUS_ANY);

	for (DLFileVersion fileVersion : fileVersions) {
		UnicodeProperties extraSettings = fileVersion.getExtraSettingsProperties();

		if (!extraSettings.isEmpty()) {
			keys = extraSettings.keySet();

			keysList = new ArrayList<String>(extraSettings.size());
			duplicateNames = new ArrayList<String>(extraSettings.size());

			for (String key : keys) {
				if (fileEntry.getExpandoBridge().hasAttribute(key)) {
					duplicateNames.add(key);
				}
				else {
					keysList.add(key);
				}
			}

			break;
		}
	}
}
catch (Exception e) {
}
%>

<c:choose>
	<c:when test="<%= fileEntry == null %>">
			<div class="portlet-msg-success">
				<liferay-ui:message key="there-are-no-longer-any-document-library-files-with-extra-settings" />
			</div>
	</c:when>
	<c:otherwise>
		<c:if test="<%= (duplicateNames != null) && !duplicateNames.isEmpty() %>">
			<div class="portlet-msg-error">
				<%= LanguageUtil.format(pageContext, "custom-fields-already-exist-for-these-extra-settings-x", StringUtil.merge(duplicateNames)) %>
			</div>
		</c:if>

		<portlet:actionURL var="convertExtraSettingsURL">
			<portlet:param name="struts_action" value="/admin_server/edit_extra_settings" />
		</portlet:actionURL>

		<aui:form action="<%= convertExtraSettingsURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "convertExtraSettings();" %>'>
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="mergedKeys" type="hidden" value="<%= StringUtil.merge(keysList) %>" />

			<%
			for (String key : keysList) {
				String selectName = "type_" + key;
			%>
				<aui:fieldset>
					<%= LanguageUtil.format(pageContext, "convert-extra-settings-key-from-x-to", key) %>

					<br />

					<aui:select helpMessage="custom-field-type-help" label="type" name='<%= selectName %>'>
						<optgroup label="<liferay-ui:message key="presets" />">
							<aui:option label="selection-of-integer-values" value="PresetSelectionIntegerArray()" />
							<aui:option label="selection-of-decimal-values" value="PresetSelectionDoubleArray()" />
							<aui:option label="selection-of-text-values" value="PresetSelectionStringArray()" />
							<aui:option label="text-box" value="PresetTextBox()" />
							<aui:option label="text-box-indexed" value="PresetTextBoxIndexed()" />
							<aui:option label="text-field-secret" value="PresetTextFieldSecret()" />
							<aui:option label="text-field-indexed" selected="<%= true %>" value="PresetTextFieldIndexed()" />
						</optgroup>
						<optgroup label="<liferay-ui:message key="primitives" />">

							<%
							for (int curType : ExpandoColumnConstants.TYPES) {
								if ((curType == ExpandoColumnConstants.BOOLEAN_ARRAY) || (curType == ExpandoColumnConstants.DATE_ARRAY)) {
									continue;
								}
							%>

								<aui:option label="<%= ExpandoColumnConstants.getTypeLabel(curType) %>" value="<%= curType %>" />

							<%
							}
							%>

						</optgroup>
					</aui:select>
				</aui:fieldset>
			<%
			}
			%>

				<aui:button-row>
					<aui:button type="submit" />
				</aui:button-row>
			</aui:form>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />convertExtraSettings(options) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "convert";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>