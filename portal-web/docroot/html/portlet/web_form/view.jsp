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
String title = prefs.getValue("title", StringPool.BLANK);
String description = prefs.getValue("description", StringPool.BLANK);
boolean requireCaptcha = GetterUtil.getBoolean(prefs.getValue("requireCaptcha", StringPool.BLANK));
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/web_form/view" /></portlet:actionURL>" method="post">

<h3><%= title %></h3>

<p class="liferay-web-form-descr"><%= description %></p>

<liferay-ui:success key="emailSent" message="the-email-was-sent-successfuly" />
<liferay-ui:success key="saveToFileSuccees" message="the-file-was-saved-successfully" />

<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
<liferay-ui:error key="allFieldsRequired" message="please-complete-all-fields" />
<liferay-ui:error key="emailNotSent" message="the-email-could-not-be-sent" />
<liferay-ui:error key="saveToFileFailure" message="the-file-could-not-be-saved" />

<%
int i = 1;

String fieldName = "field" + i;
String fieldLabel = prefs.getValue("fieldLabel" + i, "");
boolean fieldOptional = PrefsParamUtil.getBoolean(prefs, request, "fieldOptional" + i, false);
String fieldValue = ParamUtil.getString(request, fieldName);

while ((i == 1) || (fieldLabel.trim().length() > 0)) {
	if (Validator.isNull(fieldLabel)) {
       continue;
    }

	String fieldType = prefs.getValue("fieldType" + i, "text");
	String fieldOptions = prefs.getValue("fieldOptions" + i, "unknown");
%>

	<c:choose>
		<c:when test='<%= fieldType.equals("paragraph") %>'>
			<p class="liferay-webform"><%= fieldOptions %></p>
		</c:when>
		<c:when test='<%= fieldType.equals("text") %>'>
			<label class="liferay-web-form <%= fieldOptional ? "optional" : "" %>" for="<portlet:namespace /><%= fieldName %>"><%= fieldLabel %></label>

			<input class="liferay-input-text <%= fieldOptional ? "optional" : "" %>" id="<portlet:namespace /><%= fieldName %>" name="<portlet:namespace /><%= fieldName %>" type="text" value="<%= fieldValue %>" />
		</c:when>
		<c:when test='<%= fieldType.equals("textarea") %>'>
			<label class="liferay-web-form <%= fieldOptional ? "optional" : "" %>" for="<portlet:namespace /><%= fieldName %>"><%= fieldLabel %></label>

			<textarea class="liferay-textarea <%= fieldOptional ? "optional" : "" %>" id="<portlet:namespace /><%= fieldName %>" name="<portlet:namespace /><%= fieldName %>" wrap="soft"><%= fieldValue %></textarea>
		</c:when>
		<c:when test='<%= fieldType.equals("checkbox") %>'>
			<label class="liferay-web-form <%= fieldOptional ? "optional" : "" %>" for="<portlet:namespace /><%= fieldName %>"><%= fieldLabel %></label>

			<div class="liferay-input-checkbox <%= fieldOptional ? "optional" : "" %>">
				<input <%= Validator.isNotNull(fieldValue) ? "checked" : "" %> id="<portlet:namespace /><%= fieldName %>" name="<portlet:namespace /><%= fieldName %>" type="checkbox" />
			</div>
		</c:when>
		<c:when test='<%= fieldType.equals("radio") %>'>
			<label class="liferay-web-form <%= fieldOptional ? "optional" : "" %>" for="<portlet:namespace /><%= fieldName %>"><%= fieldLabel %></label>

			<div class="liferay-input-radio <%= fieldOptional ? "optional" : "" %>">

				<%
				String[] options = WebFormUtil.split(fieldOptions);

				for (int j = 0; j < options.length; j++) {
				%>

					<div class="liferay-input-radiobutton">
						<input type="radio" name="<portlet:namespace /><%= fieldName %>" <%= fieldValue.equals(options[j]) ? "checked=\"true\"" : "" %> value="<%= options[j] %>" /><%= options[j] %>
					</div>

				<%
				}
				%>

			</div>
		</c:when>
		<c:when test='<%= fieldType.equals("options") %>'>
			<label class="liferay-web-form <%= fieldOptional ? "optional" : "" %>" for="<portlet:namespace /><%= fieldName %>"><%= fieldLabel %></label>

			<div class="liferay-input-radio <%= fieldOptional ? "optional" : "" %>">

				<%
				String[] options = WebFormUtil.split(fieldOptions);
				%>

				<select name="<portlet:namespace /><%= fieldName %>">

					<%
					for (int j = 0; j < options.length; j++) {
					%>

						<option <%= fieldValue.equals(options[j]) ? "selected" : "" %> value="<%= options[j] %>"><%= options[j] %></option>

					<%
					}
					%>

				</select>
			</div>
		</c:when>
	</c:choose>

	<br />

<%
    i++;

    fieldName = "field" + i;
    fieldLabel = prefs.getValue("fieldLabel" + i, "");
    fieldOptional = PrefsParamUtil.getBoolean(prefs, request, "fieldOptional" + i, false);
    fieldValue = ParamUtil.getString(request, fieldName);
}
%>

<c:if test="<%= requireCaptcha %>">
	<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
		<portlet:param name="struts_action" value="/web_form/captcha" />
	</portlet:actionURL>

	<liferay-ui:captcha url="<%= captchaURL %>" />
</c:if>

<div class="liferay-web-form-submit">
	<input type="submit" value="<liferay-ui:message key="send" />" />
</div>

</form>