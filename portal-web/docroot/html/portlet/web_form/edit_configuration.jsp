<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%
String redirect = ParamUtil.getString(request, "redirect");

String title = PrefsParamUtil.getString(prefs, request, "title");
String description = PrefsParamUtil.getString(prefs, request, "description");
String subject = PrefsParamUtil.getString(prefs, request, "subject");
String emailAddress = PrefsParamUtil.getString(prefs, request, "emailAddress");
%>

<script type="text/javascript">
	function <portlet:namespace />toggleOptions(fieldsPos) {
		var fieldType = jQuery('#<portlet:namespace/>fieldType' + fieldsPos);
		var fieldOptionsRow = jQuery('#<portlet:namespace/>fieldOptionsRow' + fieldsPos);

		if (fieldType.val() == 'options') {
			fieldOptionsRow.show();
		}
		else {
			fieldOptionsRow.hide();
		}
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />

<fieldset>
	<legend><bean:message key="form-information" /></legend>

	<liferay-ui:error key="titleRequired" message="please-enter-a-title" />

	<table class="liferay-table">
	<tr>
		<td>
			<bean:message key="title" />
		</td>
		<td>
			<input name="<portlet:namespace />title" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= title %>" />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="description" />
		</td>
		<td>
			<textarea name="<portlet:namespace/>description" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_WIDTH %>px;" wrap="soft"><%= description %></textarea>
		</td>
	</tr>
	</table>
</fieldset>

<fieldset>
	<legend><bean:message key="email-to" /></legend>

	<liferay-ui:error key="subjectRequired" message="please-enter-a-subject" />
	<liferay-ui:error key="emailAddressRequired" message="please-enter-an-email-address" />
	<liferay-ui:error key="emailAddressInvalid" message="please-enter-a-valid-email-address" />

	<table class="liferay-table">
	<tr>
		<td>
			<bean:message key="subject" />
		</td>
		<td>
			<input name="<portlet:namespace />subject" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= subject %>" />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="email-address" />
		</td>
		<td>
			<input name="<portlet:namespace />emailAddress" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= emailAddress %>" />
		</td>
	</tr>
	</table>
</fieldset>

<fieldset>
	<legend><bean:message key="form-fields" /></legend>

	<table class="liferay-table">

	<%
	for (int i = 1; i <= 10; i++) {
		String fieldLabel = PrefsParamUtil.getString(prefs, request, "fieldLabel" + i);
		String fieldType = PrefsParamUtil.getString(prefs, request, "fieldType" + i);
		String fieldOptions = PrefsParamUtil.getString(prefs, request, "fieldOptions" + i);
	%>

		<tr>
			<td colspan="2">
				<b><bean:message key="field" /> <%= i %></b>
			</td>
		</tr>
		<tr>
			<td>
				<bean:message key="name" />
			</td>
			<td>
				<input name="<portlet:namespace/>fieldLabel<%= i %>" size="50" type="text" value="<%= fieldLabel %>" />
			</td>
		</tr>
		<tr>
			<td>
				<bean:message key="type" />
			</td>
			<td>
				<select id="<portlet:namespace/>fieldType<%= i %>" name="<portlet:namespace/>fieldType<%= i %>" onchange="<portlet:namespace/>toggleOptions(<%= i %>);">
					<option <%= (fieldType.equals("text")) ? "selected" : "" %> value="text"><bean:message key="text" /></option>
					<option <%= (fieldType.equals("textarea")) ? "selected" : "" %> value="textarea"><bean:message key="text-box" /></option>
					<option <%= (fieldType.equals("options")) ? "selected" : "" %> value="options"><bean:message key="options" /></option>
				</select>
			</td>
		</tr>
		<tr id="<portlet:namespace/>fieldOptionsRow<%= i %>">
			<td>
				<bean:message key="options" />
			</td>
			<td>
				<input id="<portlet:namespace/>fieldOptions<%= i %>" name="<portlet:namespace/>fieldOptions<%= i %>" type="text" size="50" value="<%= fieldOptions %>" />

				(<bean:message key="add-options-separated-by-commas" />)

				<script type="text/javascript">
					<portlet:namespace/>toggleOptions(<%= i %>);
				</script>
			</td>
		</tr>

		<c:if test="<%= (i + 1) <= 10 %>">
			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
		</c:if>

	<%
	}
	%>

	</table>
</fieldset>

<br />

<input type="submit" value="<bean:message key="save" />" />

<input type="button" value="<bean:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>