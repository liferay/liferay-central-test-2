<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/web_form/init.jsp" %>

<script type="text/javascript">
	function <portlet:namespace />swapValues(fieldA, fieldB) {
		var tempValue = fieldA.val();
		fieldA.val(fieldB.val());
		fieldB.val(tempValue);
	}

	function <portlet:namespace />moveDown(index) {
		var typeA = jQuery('#<portlet:namespace/>fieldType' + index);
		var labelA = jQuery('#<portlet:namespace/>fieldLabel' + index);
		var optionalA = jQuery('#<portlet:namespace/>fieldOptional' + index);
		var optionsA = jQuery('#<portlet:namespace/>fieldOptions' + index);

		var typeB = jQuery('#<portlet:namespace/>fieldType' + (index + 1));
		var labelB = jQuery('#<portlet:namespace/>fieldLabel' + (index + 1));
		var optionalB = jQuery('#<portlet:namespace/>fieldOptional' + (index + 1));
		var optionsB = jQuery('#<portlet:namespace/>fieldOptions' + (index + 1));

		if ( index < jQuery('#<portlet:namespace/>webFields>fieldset').length ) {
			<portlet:namespace />swapValues(typeA, typeB);
			<portlet:namespace />swapValues(labelA, labelB);
			<portlet:namespace />swapValues(optionsA, optionsB);

			var tmpA = optionalA.attr('checked');
			var tmpB = optionalB.attr('checked');
			if( tmpA && ! tmpB ) { optionalA.removeAttr('checked'); optionalB.attr('checked', 'true'); }
			if( ! tmpA && tmpB ) { optionalB.removeAttr('checked'); optionalA.attr('checked', 'true'); }

			typeA.change();
			typeB.change();
		}
	}

	function <portlet:namespace />moveUp(index) {
		if( index > 1 ) 
			<portlet:namespace />moveDown(index - 1);
	}
</script>

<%
String redirect = ParamUtil.getString(request, "redirect");

String title = PrefsParamUtil.getString(prefs, request, "title");
String description = PrefsParamUtil.getString(prefs, request, "description");
boolean requireCaptcha = PrefsParamUtil.getBoolean(prefs, request, "requireCaptcha");
String successURL = PrefsParamUtil.getString(prefs, request, "successURL");

boolean sendAsEmail = PrefsParamUtil.getBoolean(prefs, request, "sendAsEmail");
String subject = PrefsParamUtil.getString(prefs, request, "subject");
String emailAddress = PrefsParamUtil.getString(prefs, request, "emailAddress");

boolean saveToDatabase = PrefsParamUtil.getBoolean(prefs, request, "saveToDatabase");
String databaseTableName = prefs.getValue("databaseTableName", StringPool.BLANK);

boolean saveToFile = PrefsParamUtil.getBoolean(prefs, request, "saveToFile");
String fileName = PrefsParamUtil.getString(prefs, request, "fileName");

boolean fieldsEditingDisabled = false;

if (WebFormUtil.getTableRowsCount(databaseTableName) > 0) {
	fieldsEditingDisabled = true;
}
%>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" class="uni-form" method="post" id="<portlet:namespace />fm" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />

<fieldset class="block-labels">
	<legend><liferay-ui:message key="form-information" /></legend>

	<liferay-ui:error key="titleRequired" message="please-enter-a-title" />

	<div class="ctrl-holder">
		<label for="<portlet:namespace />title"><liferay-ui:message key="title" /></label>

		<input class="lfr-input-text" id="<portlet:namespace />title" name="<portlet:namespace />title" type="text" value="<%= HtmlUtil.toInputSafe(title) %>" />
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace/>description"><liferay-ui:message key="description" /></label>

		<textarea class="lfr-textarea" id="<portlet:namespace/>description" name="<portlet:namespace/>description" wrap="soft"><%= description %></textarea>
	</div>

	<div class="ctrl-holder">
		<label><liferay-ui:message key="require-captcha" /> <liferay-ui:input-checkbox param="requireCaptcha" defaultValue="<%= requireCaptcha %>" /></label>
	</div>

	<div class="ctrl-holder">
		<label for="<portlet:namespace />successURL"><liferay-ui:message key="redirect-url-on-success" /></label>

		<input class="lfr-input-text" id="<portlet:namespace />successURL" name="<portlet:namespace />successURL" type="text" value="<%= HtmlUtil.toInputSafe(successURL) %>" />
	</div>
</fieldset>

<fieldset class="block-labels">
	<legend><liferay-ui:message key="handling-of-form-data" /></legend>

	<fieldset class="block-labels">
		<legend><liferay-ui:message key="email" /></legend>

		<liferay-ui:error key="subjectRequired" message="please-enter-a-subject" />
		<liferay-ui:error key="handlingRequired" message="please-select-an-action-for-the-handling-of-form-data" />
		<liferay-ui:error key="emailAddressInvalid" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailAddressRequired" message="please-enter-an-email-address" />
		<liferay-ui:error key="fileNameInvalid" message="please-enter-a-valid-path-and-filename" />

		<div class="ctrl-holder">
			<label><liferay-ui:message key="send-as-email" /> <liferay-ui:input-checkbox param="sendAsEmail" defaultValue="<%= sendAsEmail %>" /></label>
		</div>

		<div class="ctrl-holder">
			<label for="<portlet:namespace />subject"><liferay-ui:message key="subject" /></label>

			<input class="lfr-input-text" id="<portlet:namespace />subject" name="<portlet:namespace />subject" type="text" value="<%= subject %>" />
		</div>

		<div class="ctrl-holder">
			<label for="<portlet:namespace />emailAddress"><liferay-ui:message key="email-address" /></label>

			<input class="lfr-input-text" id="<portlet:namespace />emailAddress" name="<portlet:namespace />emailAddress" type="text" value="<%= emailAddress %>" />
		</div>
	</fieldset>

	<fieldset class="block-labels">
		<legend><liferay-ui:message key="database" /></legend>

		<div class="ctrl-holder">
			<label><liferay-ui:message key="save-to-database" /> <liferay-ui:input-checkbox param="saveToDatabase" defaultValue="<%= saveToDatabase %>" /></label>
		</div>
	</fieldset>

	<fieldset class="block-labels">
		<legend><liferay-ui:message key="file" /></legend>

		<div class="ctrl-holder">
			<label><liferay-ui:message key="save-to-file" /> <liferay-ui:input-checkbox param="saveToFile" defaultValue="<%= saveToFile %>" /></label>
		</div>

		<div class="ctrl-holder">
			<label for="<portlet:namespace />filename"><liferay-ui:message key="path-and-file-name" /></label>

			<input class="lfr-input-text" id="<portlet:namespace />filename" name="<portlet:namespace />fileName" type="text" value="<%= fileName %>" />
		</div>
	</fieldset>
</fieldset>

<fieldset class="block-labels" id="<portlet:namespace/>webFields">
	<legend><liferay-ui:message key="form-fields" /></legend>

	<c:if test="<%= fieldsEditingDisabled %>">
		<div class="portlet-msg-alert">
			<liferay-ui:message key="there-is-existing-form-data-please-export-and-delete-it-before-making-changes-to-the-fields" />
		</div>

		<liferay-portlet:actionURL var="exportURL" portletName="<%= portletResource %>">
			<portlet:param name="struts_action" value="/web_form/export_data" />
		</liferay-portlet:actionURL>

		<input type="button" value="<liferay-ui:message key="export-data" />" onclick="submitForm(document.hrefFm, '<%= exportURL %>');" />

		<liferay-portlet:actionURL var="deleteURL" portletName="<%= portletResource %>">
			<portlet:param name="struts_action" value="/web_form/delete_data" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<input type="button" value="<liferay-ui:message key="delete-data" />" onclick="submitForm(document.<portlet:namespace/>fm, '<%= deleteURL %>');" />

		<br /><br />
	</c:if>

	<input name="<portlet:namespace/>updateFields" type="hidden" value="<%= !fieldsEditingDisabled %>" />

	<%
	int i = 1;

	String fieldLabel = PrefsParamUtil.getString(prefs, request, "fieldLabel" + i);
	String fieldType = PrefsParamUtil.getString(prefs, request, "fieldType" + i);
	boolean fieldOptional = PrefsParamUtil.getBoolean(prefs, request, "fieldOptional" + i);
	String fieldOptions = PrefsParamUtil.getString(prefs, request, "fieldOptions" + i);

	while ((i == 1) || (fieldLabel.trim().length() > 0)) {
	%>

		<fieldset>
			<legend><liferay-ui:message key="field" /> <%= i %></legend>

			<div class="ctrl-holder">
				<label for="<portlet:namespace/>fieldLabel<%= i %>"><liferay-ui:message key="name" /></label>

				<c:choose>
					<c:when test="<%= !fieldsEditingDisabled %>">
						<input class="lfr-input-text" id="<portlet:namespace/>fieldLabel<%= i %>" name="<portlet:namespace/>fieldLabel<%= i %>" size="50" type="text" value="<%= fieldLabel %>" />
					</c:when>
					<c:otherwise>
						<b><%= fieldLabel %></b>
					</c:otherwise>
				</c:choose>

				<br />

				<c:choose>
					<c:when test='<%= fieldType.equals("paragraph") %>'>
						<input id="<portlet:namespace/>fieldOptional<%= i %>" name="<portlet:namespace/>fieldOptional<%= i %>" type="hidden" value="on" />
					</c:when>
					<c:when test="<%= !fieldsEditingDisabled %>">
						<input <c:if test="<%= fieldOptional %>">checked</c:if> type="checkbox" id="<portlet:namespace/>fieldOptional<%= i %>" name="<portlet:namespace/>fieldOptional<%= i %>" /> <liferay-ui:message key="optional" />
					</c:when>
					<c:otherwise>
						<label><liferay-ui:message key="optional" /></label>
						<b><%= LanguageUtil.get(pageContext, fieldOptional ? "yes" : "no") %></b>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="ctrl-holder">
				<label for="<portlet:namespace/>fieldType<%= i %>"><liferay-ui:message key="type" /></label>

				<c:choose>
					<c:when test="<%= !fieldsEditingDisabled %>">
						<select id="<portlet:namespace/>fieldType<%= i %>" name="<portlet:namespace/>fieldType<%= i %>">
							<option <%= (fieldType.equals("text")) ? "selected" : "" %> value="text"><liferay-ui:message key="text" /></option>
							<option <%= (fieldType.equals("textarea")) ? "selected" : "" %> value="textarea"><liferay-ui:message key="text-box" /></option>
							<option <%= (fieldType.equals("options")) ? "selected" : "" %> value="options"><liferay-ui:message key="options" /></option>
							<option <%= (fieldType.equals("radio")) ? "selected" : "" %> value="radio"><liferay-ui:message key="radiobuttons" /></option>
							<option <%= (fieldType.equals("paragraph")) ? "selected" : "" %> value="paragraph"><liferay-ui:message key="paragraph" /></option>
							<option <%= (fieldType.equals("checkbox")) ? "selected" : "" %> value="checkbox"><liferay-ui:message key="checkbox" /></option>
						</select>
					</c:when>
					<c:otherwise>
						<b><%= LanguageUtil.get(pageContext, fieldType) %></b>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="ctrl-holder" id="<portlet:namespace/>optionsGroup<%= i %>">
				<label for="<portlet:namespace/>fieldOptions<%= i %>"><liferay-ui:message key="options" /></label>

				<c:choose>
					<c:when test="<%= !fieldsEditingDisabled %>">
						<span>(<liferay-ui:message key="add-options-separated-by-commas" />)</span><br />

						<input class="lfr-input-text" id="<portlet:namespace/>fieldOptions<%= i %>" name="<portlet:namespace/>fieldOptions<%= i %>" value="<%= fieldOptions %>" />
					</c:when>
					<c:otherwise>
						<b><%= fieldOptions %></b>
					</c:otherwise>
				</c:choose>
			</div>

			<c:if test="<%= !fieldsEditingDisabled %>">
				<div class="ctrl-holder">
					<a href="javascript:<portlet:namespace />moveUp(<%= i %>);"><img src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_up.png" /></a>
					<a href="javascript:<portlet:namespace />moveDown(<%= i %>);"><img src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_down.png" /></a>
				</div>
			</c:if>
		</fieldset>

	<%
		i++;

		fieldLabel = PrefsParamUtil.getString(prefs, request, "fieldLabel" + i);
		fieldType = PrefsParamUtil.getString(prefs, request, "fieldType" + i);
		fieldOptional = PrefsParamUtil.getBoolean(prefs, request, "fieldOptional" + i, false);
		fieldOptions = PrefsParamUtil.getString(prefs, request, "fieldOptions" + i);
	}
	%>

</fieldset>

<br />

<div class="button-holder">
	<input type="submit" value="<liferay-ui:message key="save" />" />

	<input type="button" value="<liferay-ui:message key="cancel" />" onclick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
</div>

</form>

<script type="text/javascript">
	jQuery(
		function() {
			var selects = jQuery('#<portlet:namespace/>webFields select');

			var toggleOptions = function() {
				var select = jQuery(this);
				var div = select.parent().next();
				var value = select.find('option:selected').val();

				if (value == 'options' || value == 'radio') {
					div.children().show();
					div.show();
				}
				else if (value == 'paragraph') {

					// Show just the textarea, not the labels since there are multiple choice inputs.

					div.children().hide();
					div.children(".<portlet:namespace />input-field").show();
					div.show();
				}
				else {
					div.hide();
				}
			};

			selects.change(toggleOptions);
			selects.each(toggleOptions);

			<c:if test="<%= !fieldsEditingDisabled %>">
				var <portlet:namespace/>autoFields = new Liferay.autoFields(
					{
						addText: '<liferay-ui:message key="add-another-form-field" />',
						removeText: '<liferay-ui:message key="remove-the-last-form-field" />',

						html: '#<portlet:namespace />webFields fieldset:first',
						container: '#<portlet:namespace />webFields',
						rowType: 'fieldset',
						init: function() {
							var instance = this;

							instance._numField = <%= (i - 1) %>;

							if (instance._numField > 1) {
								var removeLink = instance._controlLinks.find('a:eq(1)');

								if (removeLink.is(':hidden')) {
									removeLink.show();
								}
							}
						},
						onAdd: function(newField) {
							var instance = this;

							var numField = instance._numField;
							var inputs = newField.find('[@class$=lfr-input-text]');
							var selects = newField.find('select');
							var label = newField.find('label');
							var selectId = '<portlet:namespace/>fieldType' + numField;
							var legend = newField.find('legend');
							var legText = legend.text();
							var re = /([0-9])+$/;
							var links = newField.find('a');

							legText = legText.replace(re, numField);
							legend.text(legText);

							label.each(
								function() {
									var label = jQuery(this);
									var labelFor = label.attr('for');

									labelFor = labelFor.replace(re, numField);
									label.attr('for', labelFor);
								}
							);

							inputs.each(
								function() {
									var input = jQuery(this);
									var inputAttr = input.attr('name');

									inputAttr = inputAttr.replace(re, numField);

									this.value = '';

									input.attr(
										{
											id: inputAttr,
											name: inputAttr
										}
									);
								}
							);

							links.each(
								function() {
									var link = jQuery(this);
									var linkHref = link.attr('href');

									linkHref = linkHref.replace(/([0-9])+\)/, numField + ')');
									link.attr('href', linkHref);
								}
							);

							selects.attr(
								{
									id: selectId,
									name: selectId
								}
							);

							selects.change(toggleOptions);
							selects.each(toggleOptions);
						}
					}
				);
			</c:if>
		}
	);
</script>