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

<%@ include file="/html/portlet/address_book/init.jsp" %>

<%
String importerType = ParamUtil.get(request, "importer_type", ABUtil.IE_OUTLOOK);
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/address_book/import" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL><portlet:param name="struts_action" value="/address_book/view_all" /></portlet:renderURL>">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "import") %>' />

	<table border="0" cellpadding="0" cellspacing="2">
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= LanguageUtil.get(pageContext, "choose-a-program-to-import-contacts-from") %>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<select name="<portlet:namespace />importer_type">
						<option <%= importerType.equals(ABUtil.IE_OUTLOOK) ? "selected" : "" %> value="<%= ABUtil.IE_OUTLOOK %>"><%= LanguageUtil.get(pageContext, "microsoft-outlook-csv-file") %></option>
						<option <%= importerType.equals(ABUtil.IE_YAHOO) ? "selected" : "" %> value="<%= ABUtil.IE_YAHOO %>"><%= LanguageUtil.get(pageContext, "yahoo-csv-file") %></option>
					</select>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">

			<c:if test="<%= SessionErrors.contains(renderRequest, UploadException.class.getName()) %>">
				<tr>
					<td>
						<font class="portlet-msg-error" style="font-size: xx-small;"><%= LanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-uploading-your-file") %></font>
					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><%= LanguageUtil.get(pageContext, "specify-the-file-to-import") %></font>
				</td>
			</tr>
			<tr>
				<td>
					<input class="form-text" name="<portlet:namespace />import_file" size="50" type="file">
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<br>
		</td>
	</tr>
	<tr>
		<td align="center">
			<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "import") %>">
		</td>
	</tr>
	</table>
</liferay-ui:box>

<br>

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "export") %>' />

	<table border="0" cellpadding="0" cellspacing="2">
	<tr>
		<td>
			<font class="portlet-font" style="font-size: x-small;">
			<%= LanguageUtil.get(pageContext, "choose-the-program-you-want-to-export-to-and-click-the-corresponding-button") %>
			</font>
		</td>
	</tr>
	<tr>
		<td>
			<br>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "microsoft-outlook") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "export") %>" onClick="self.location = '<%= themeDisplay.getPathMain() %>/address_book/export?exporter_type=<%= ABUtil.IE_OUTLOOK %>';">
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<font class="portlet-font" style="font-size: x-small;"><b><%= LanguageUtil.get(pageContext, "yahoo-cvs") %></b></font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="portlet-form-button" type="button" value="<%= LanguageUtil.get(pageContext, "export") %>" onClick="self.location = '<%= themeDisplay.getPathMain() %>/address_book/export?exporter_type=<%= ABUtil.IE_YAHOO %>';">
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />import_file.focus();
</script>