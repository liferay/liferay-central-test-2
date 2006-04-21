<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/mail/init.jsp" %>

<%
String folderName = request.getParameter("folder_name");
if ((folderName == null) || (folderName.equals(StringPool.NULL))) {
	folderName = "";
}
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/mail/add_folder" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/mail/view_folders" /></portlet:renderURL>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "add-folder") %>' />

	<table border="0" cellpadding="0" cellspacing="0">

	<c:if test='<%= SessionErrors.contains(renderRequest, "folder_name_already_taken") %>'>
		<tr>
			<td colspan="3">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-unique-folder-name") %></font>
			</td>
		</tr>
	</c:if>

	<c:if test='<%= SessionErrors.contains(renderRequest, "folder_name_invalid") %>'>
		<tr>
			<td colspan="3">
				<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "please-enter-a-valid-folder-name") %></font>
			</td>
		</tr>
	</c:if>

	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "folder-name") %></b></font>
		</td>
		<td width="10">
			&nbsp;
		</td>
		<td>
			<input class="form-text" name="<portlet:namespace />folder_name" type="text" value="<%= folderName %>">
		</td>
	</tr>
	</table>

	<br>

	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "add-folder") %>">
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />folder_name.focus();
</script>