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
Folder[] defaultFolders = MailUtil.getDefaultFolders(request);
%>

<form method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this, '<portlet:renderURL><portlet:param name="struts_action" value="/mail/search_results" /></portlet:renderURL>', false); return false;">

<liferay-ui:box top="/html/common/themes/inner_top.jsp" bottom="/html/common/themes/inner_bottom.jsp">
	<liferay-util:param name="box_title" value='<%= LanguageUtil.get(pageContext, "search") %>' />

	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="3">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<input class="form-text" name="<portlet:namespace />search_query" type="text" size="50">
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search") %>">
				</td>
			</tr>
			</table>

			<br>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3">
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "folders") %>
					</b></font>
				</td>
			</tr>
			<tr>
				<td>
					<input checked name="<portlet:namespace />search_folder_names_allbox" type="checkbox" onClick="checkAll(document.<portlet:namespace />fm, '<portlet:namespace />search_folder_names', this);">
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font size="2"><b>*</b></font>
				</td>
			</tr>

			<%
			for (int i = 0; i < defaultFolders.length; i++) {
				Folder defaultFolder = defaultFolders[i];
			%>

			<tr>
				<td>
					<input checked name="<portlet:namespace />search_folder_names" type="checkbox" value="<%= defaultFolder.getName() %>" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_folder_names', document.<portlet:namespace />fm.<portlet:namespace />search_folder_names_allbox);">
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= LanguageUtil.get(pageContext, defaultFolder.getName()) %>
					</font>
				</td>
			</tr>

			<%
			}
			%>

			<%
			for (int i = 0; i < extraFolders.length; i++) {
			%>

			<tr>
				<td>
					<input checked name="<portlet:namespace />search_folder_names" type="checkbox" value="<%= extraFolders[i] %>" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_folder_names', document.<portlet:namespace />fm.<portlet:namespace />search_folder_names_allbox);">
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= extraFolders[i] %>
					</font>
				</td>
			</tr>

			<%
			}
			%>

			</table>
		</td>
		<td width="30">
			&nbsp;
		</td>
		<td valign="top">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="3">
							<font class="portlet-font" style="font-size: x-small;"><b>
							<%= LanguageUtil.get(pageContext, "area") %>
							</b></font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_area_allbox" type="checkbox" onClick="checkAll(document.<portlet:namespace />fm, '<portlet:namespace />search_area', this);">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font size="2"><b>*</b></font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_area" type="checkbox" value="from" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_area', document.<portlet:namespace />fm.<portlet:namespace />search_area_allbox);">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "from") %>
							</font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_area" type="checkbox" value="to" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_area', document.<portlet:namespace />fm.<portlet:namespace />search_area_allbox);">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "to") %>
							</font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_area" type="checkbox" value="cc" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_area', document.<portlet:namespace />fm.<portlet:namespace />search_area_allbox);">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "cc") %>
							</font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_area" type="checkbox" value="bcc" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_area', document.<portlet:namespace />fm.<portlet:namespace />search_area_allbox);">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "bcc") %>
							</font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_area" type="checkbox" value="subject" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_area', document.<portlet:namespace />fm.<portlet:namespace />search_area_allbox);">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "subject") %>
							</font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_area" type="checkbox" value="body" onClick="checkAllBox(document.<portlet:namespace />fm, '<portlet:namespace />search_area', document.<portlet:namespace />fm.<portlet:namespace />search_area_allbox);">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "body") %>
							</font>
						</td>
					</tr>
					</table>
				</td>
				<td width="30">
					&nbsp;
				</td>
				<td valign="top">
					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="3">
							<font class="portlet-font" style="font-size: x-small;"><b>
							<%= LanguageUtil.get(pageContext, "method") %>
							</b></font>
						</td>
					</tr>
					<tr>
						<td>
							<input name="<portlet:namespace />search_method" type="radio" value="and">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "and") %>
							</font>
						</td>
					</tr>
					<tr>
						<td>
							<input checked name="<portlet:namespace />search_method" type="radio" value="or">
						</td>
						<td width="10">
							&nbsp;
						</td>
						<td>
							<font class="portlet-font" style="font-size: x-small;">
							<%= LanguageUtil.get(pageContext, "or") %>
							</font>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>

			<br>

			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="5">
					<font class="portlet-font" style="font-size: x-small;"><b>
					<%= LanguageUtil.get(pageContext, "date") %>
					</b></font>
				</td>
			</tr>
			<tr>
				<td>
					<input checked name="<portlet:namespace />search_date_method" type="radio" value="tf">
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= LanguageUtil.get(pageContext, "by-time-frame") %>
					</font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<select name="<portlet:namespace />search_date_tf">
						<option value="0"><%= LanguageUtil.get(pageContext, "anytime") %></option>
						<option value="1"><%= LanguageUtil.get(pageContext, "today") %></option>
						<option value="2"><%= LanguageUtil.get(pageContext, "week") %></option>
						<option value="3">2 <%= LanguageUtil.get(pageContext, "weeks") %></option>
						<option value="4"><%= LanguageUtil.get(pageContext, "month") %></option>
						<option value="5">3 <%= LanguageUtil.get(pageContext, "months") %></option>
						<option value="6">6 <%= LanguageUtil.get(pageContext, "months") %></option>
						<option value="7"><%= LanguageUtil.get(pageContext, "year") %></option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<input name="<portlet:namespace />search_date_method" type="radio" value="dr">
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<font class="portlet-font" style="font-size: x-small;">
					<%= LanguageUtil.get(pageContext, "by-date-range") %>
					</font>
				</td>
				<td width="10">
					&nbsp;
				</td>
				<td>
					<input class="form-text" maxlength="10" name="<portlet:namespace />search_date_dr_from" size="10" type="text">

					<font class="portlet-font" style="font-size: x-small;">
					&nbsp;<%= LanguageUtil.get(pageContext, "to") %>&nbsp;
					</font>

					<input class="form-text" maxlength="10" name="<portlet:namespace />search_date_dr_to" size="10" type="text">

					<font class="portlet-font" style="font-size: x-small;">
					&nbsp;<%= LanguageUtil.get(pageContext, "mm-dd-yyyy") %>
					</font>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</liferay-ui:box>

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />search_query.focus();
</script>