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

<%@ include file="/html/portlet/webform/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String title = ParamUtil.getString(request, "title", prefs.getValue("title", ""));
String description = ParamUtil.getString(request, "description", prefs.getValue("description", ""));
String subject = ParamUtil.getString(request, "subject", prefs.getValue("subject", ""));
String email = ParamUtil.getString(request, "email", prefs.getValue("email", ""));

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);
ServletContext ctx = (ServletContext)request.getAttribute(WebKeys.CTX);
PortletConfig webformPortletConfig = PortletConfigFactory.create(portlet, ctx);

int maxNumOfFields = GetterUtil.getInteger(webformPortletConfig.getInitParameter("max-num-of-fields"), 10);
%>

<script type="text/javascript">
	function <portlet:namespace />showOptions(typeField, optionsField) {
		if (typeField.selectedIndex == '2') {
			optionsField.disabled = false;
		} else {
			optionsField.disabled = true;
		}
	}
</script>

<style type="text/css">
	fieldset {
		margin: 10px;
	}
</style>

<form action='<liferay-portlet:actionURL portletConfiguration="true" />' method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">

<fieldset>
	<legend><%=LanguageUtil.get(pageContext, "information-for-users")%></legend>

	<liferay-ui:error key="titleRequired" message="please-enter-a-title" />

	<table>
	<tr><td>
		<%=LanguageUtil.get(pageContext, "title")%>
	</td><td>
		<input name="<portlet:namespace/>title" type="text" size="50" value="<%= title%>"/>
	</td></tr>

	<tr><td>
		<%=LanguageUtil.get(pageContext, "description")%>
	</td><td>
		<textarea name="<portlet:namespace/>description" cols="100"><%= description %></textarea>
	</td></tr>
	</table>
</fieldset>

<fieldset>
	<legend><%=LanguageUtil.get(pageContext, "information-for-email")%></legend>

	<liferay-ui:error key="subjectRequired" message="please-enter-a-subject"/>
	<liferay-ui:error key="emailRequired" message="please-enter-an-email-address"/>
	<liferay-ui:error key="invalidEmail" message="please-enter-a-valid-email-address"/>

	<table>
	<tr><td>
		<%=LanguageUtil.get(pageContext, "subject")%>
	</td><td>
		<input name="<portlet:namespace/>subject" type="text" size="50"  value="<%= subject %>"/>
	</td></tr>

	<tr><td>
		<%=LanguageUtil.get(pageContext, "email")%>
	</td><td>
		<input name="<portlet:namespace/>email" type="text" size="50"  value="<%= email%>"/>
	</td></tr>
	</table>
</fieldset>

<fieldset>
	<legend><%=LanguageUtil.get(pageContext, "form-fields")%></legend>

	<table>
	<% for (int i = 1; i <= maxNumOfFields; i++){ %>
		<%
		String fieldLabel = ParamUtil.getString(request, "fieldLabel" + i, prefs.getValue("fieldLabel" + i, ""));
		String fieldType = ParamUtil.getString(request, "fieldType" + i, prefs.getValue("fieldType" + i, ""));
		String fieldOptions = ParamUtil.getString(request, "fieldOptions" + i, prefs.getValue("fieldOptions" + i, ""));

		%>

		<tr><td colspan="2" style="border-bottom: 1px dashed gray; border-top: 1px dashed gray; margin-top: 15px">
		<%=LanguageUtil.get(pageContext, "field")%> <%=i%>
		</td>
		</tr>
		<tr>
			<td valign="top">
				<%=LanguageUtil.get(pageContext, "name")%>
			</td>
			<td>
				<input name="<portlet:namespace/>fieldLabel<%=i%>" type="text" size="50" value="<%= fieldLabel %>"/>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<%=LanguageUtil.get(pageContext, "type")%>
			</td>
			<td>
				<select name="<portlet:namespace/>fieldType<%=i%>" onchange="<portlet:namespace/>showOptions(this, this.form.<portlet:namespace/>fieldOptions<%=i%>)">
					<option <%= (fieldType.equals("text"))?"selected":"" %> value="text"><%=LanguageUtil.get(pageContext, "text")%></option>
					<option <%= (fieldType.equals("textarea"))?"selected":"" %> value="textarea"><%=LanguageUtil.get(pageContext, "text-box")%></option>
					<option <%= (fieldType.equals("options"))?"selected":"" %> value="options"><%=LanguageUtil.get(pageContext, "options")%></option>
				</select>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<%=LanguageUtil.get(pageContext, "options")%>
			</td>
			<td>
			<input name="<portlet:namespace/>fieldOptions<%=i%>" type="text" size="50" value="<%= fieldOptions %>"><br>
			<span style="font-size: 0.8em">(<%=LanguageUtil.get(pageContext, "add-options-separated-by-commas")%>)</span>

			<script type="text/javascript">
				<portlet:namespace/>showOptions(document.<portlet:namespace />fm.<portlet:namespace/>fieldType<%=i%>, document.<portlet:namespace />fm.<portlet:namespace/>fieldOptions<%=i%>)
			</script>

			</td>
		</tr>
	<% } %>

	</table>
</fieldset>

<input type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>' class="portlet-form-button">

<input type="button" value='<%= LanguageUtil.get(pageContext, "cancel") %>' onClick="self.location = '<%= redirect %>';" class="portlet-form-button">

</form>