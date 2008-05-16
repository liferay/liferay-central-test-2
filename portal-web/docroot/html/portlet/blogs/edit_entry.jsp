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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String content = BeanParamUtil.getString(entry, request, "content");

Calendar displayDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

if (entry != null) {
	if (entry.getDisplayDate() != null) {
		displayDate.setTime(entry.getDisplayDate());
	}
}
%>

<script type="text/javascript">
	function <portlet:namespace />getSuggestionsContent() {
		var content = '';

		content += document.<portlet:namespace />fm.<portlet:namespace/>title.value + ' ';
		content += window.<portlet:namespace />editor.getHTML();

		return content;
	}

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />saveEntry(draft) {
		var title = document.<portlet:namespace />fm.<portlet:namespace />title.value;
		var content = window.<portlet:namespace />editor.getHTML();

		var saveButton = jQuery('#<portlet:namespace />saveButton');
		var cancelButton = jQuery('#<portlet:namespace />cancelButton');

		var saveStatus = jQuery('#<portlet:namespace />saveStatus');
		var saveText = '<%= LanguageUtil.format(pageContext, "draft-saved-at-x", "[TIME]") %>';

		if (draft) {
			if ((title == '') || (content == '')) {
				return;
			}

			var url = document.<portlet:namespace />fm.action;

			url += '&<portlet:namespace /><%= Constants.CMD %>=<%= Constants.ADD %>';
			url += '&<portlet:namespace />redirect=' + document.<portlet:namespace />fm.<portlet:namespace />redirect.value;
			url += '&<portlet:namespace />referringPortletResource=' + document.<portlet:namespace />fm.<portlet:namespace />referringPortletResource.value;
			url += '&<portlet:namespace />entryId=' + document.<portlet:namespace />fm.<portlet:namespace />entryId.value;
			url += '&<portlet:namespace />title=' + encodeURIComponent(title);
			url += '&<portlet:namespace />content=' + encodeURIComponent(content);
			url += '&<portlet:namespace />displayDateMonth=' + document.<portlet:namespace />fm.<portlet:namespace />displayDateMonth.value;
			url += '&<portlet:namespace />displayDateDay=' + document.<portlet:namespace />fm.<portlet:namespace />displayDateDay.value;
			url += '&<portlet:namespace />displayDateYear=' + document.<portlet:namespace />fm.<portlet:namespace />displayDateYear.value;
			url += '&<portlet:namespace />displayDateHour=' + document.<portlet:namespace />fm.<portlet:namespace />displayDateHour.value;
			url += '&<portlet:namespace />displayDateMinute=' + document.<portlet:namespace />fm.<portlet:namespace />displayDateMinute.value;
			url += '&<portlet:namespace />displayDateAmPm=' + document.<portlet:namespace />fm.<portlet:namespace />displayDateAmPm.value;
			url += '&<portlet:namespace />draft=1';
			url += '&<portlet:namespace />tagsEntries=' + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />tagsEntries.value);

			jQuery.ajax(
				{
					url: url,
					dataType: 'json',
					beforeSend: function() {
						saveButton.attr('disabled', true);

						saveStatus.attr('class', 'save-status portlet-msg-info pending');

						saveStatus.html('<liferay-ui:message key="saving-draft" />');
					},
					success: function(message) {
						document.<portlet:namespace />fm.<portlet:namespace />entryId.value = message.entryId;
						document.<portlet:namespace />fm.<portlet:namespace />redirect.value = message.redirect;

						saveButton.attr('disabled', false);

						var tabs1BackButton = jQuery('#<portlet:namespace />tabs1TabsBack');

						tabs1BackButton.attr('href', message.redirect);

						cancelButton.unbind();

						cancelButton.click(
							function() {
								location.href = message.redirect;
							}
						);

						var now = saveText.replace(/\[TIME\]/gim, (new Date()).toString());

						saveStatus.attr('class', 'save-status portlet-msg-success');
						saveStatus.html(now);
					},
					error: function() {
						saveStatus.attr('class', 'save-status portlet-msg-error');
						saveStatus.html('<liferay-ui:message key="could-not-save-draft-to-the-server" />');
					}
				}
			);
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= entry == null ? Constants.ADD : Constants.UPDATE %>";
			document.<portlet:namespace />fm.<portlet:namespace />content.value = content;
			document.<portlet:namespace />fm.<portlet:namespace />draft.value = 0;
			submitForm(document.<portlet:namespace />fm);
		}
	}

	<c:if test="<%= (entry == null) || entry.isDraft() %>">
		setInterval('<portlet:namespace />saveEntry(true)', 10000);
	</c:if>

	jQuery(
		function() {
			jQuery('#<portlet:namespace />cancelButton').click(
				function() {
					location.href = '<%= HtmlUtil.escape(redirect) %>';
				}
			);
		}
	);
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/blogs/edit_entry" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(false); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />referringPortletResource" type="hidden" value="<%= HtmlUtil.escape(referringPortletResource) %>" />
<input name="<portlet:namespace />entryId" type="hidden" value="<%= entryId %>" />
<input name="<portlet:namespace />draft" type="hidden" value="0" />

<liferay-ui:tabs
	names="entry"
	backURL="<%= redirect %>"
/>

<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
<liferay-ui:tags-error />

<table class="lfr-table">
<c:if test="<%= (entry == null) || entry.isDraft() %>">
<tr>
	<td colspan="2">
		<div class="save-status" id="<portlet:namespace />saveStatus"></div>
	</td>
</tr>
</c:if>
<tr>
	<td>
		<liferay-ui:message key="title" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= BlogsEntry.class %>" bean="<%= entry %>" field="title" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="display-date" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= BlogsEntry.class %>" bean="<%= entry %>" field="displayDate" defaultValue="<%= displayDate %>" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="content" />
	</td>
	<td>
		<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

		<input name="<portlet:namespace />content" type="hidden" value="" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="tags" />
	</td>
	<td>

		<%
		long classPK = 0;

		if (entry != null) {
			classPK = entry.getEntryId();
		}
		%>

		<liferay-ui:tags-selector
			className="<%= BlogsEntry.class.getName() %>"
			classPK="<%= classPK %>"
			hiddenInput="tagsEntries"
			contentCallback='<%= renderResponse.getNamespace() + "getSuggestionsContent" %>'
		/>
	</td>
</tr>

<%--<c:if test="<%= entry == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= BlogsEntry.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>--%>

</table>

<br />

<input id="<portlet:namespace />saveButton" type="submit" value="<liferay-ui:message key='<%= ((entry == null) || entry.isDraft()) ? "publish" : "save" %>' />" />

<input id="<portlet:namespace />cancelButton" type="button" value="<liferay-ui:message key="cancel" />" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />title);
	</script>
</c:if>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp";
%>