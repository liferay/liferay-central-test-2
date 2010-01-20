<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

boolean allowTrackbacks = PropsValues.BLOGS_TRACKBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowTrackbacks", true);
%>

<aui:script>
	var <portlet:namespace />saveDraftIntervalId = null;
	var <portlet:namespace />oldTitle = null;
	var <portlet:namespace />oldContent = null;

	function <portlet:namespace />clearSaveDraftIntervalId() {
		if (<portlet:namespace />saveDraftIntervalId != null) {
			clearInterval(<portlet:namespace />saveDraftIntervalId);
		}
	}

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

		var saveButton = AUI().one('#<portlet:namespace />saveButton');
		var cancelButton = AUI().one('#<portlet:namespace />cancelButton');

		var saveStatus = AUI().one('#<portlet:namespace />saveStatus');
		var saveText = '<%= UnicodeLanguageUtil.format(pageContext, "draft-saved-at-x", "[TIME]", false) %>';

		if (draft) {
			if ((title == '') || (content == '')) {
				return;
			}

			if ((<portlet:namespace />oldTitle == title) &&
				(<portlet:namespace />oldContent == content)) {

				return;
			}

			<portlet:namespace />oldTitle = title;
			<portlet:namespace />oldContent = content;

			var url = '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/blogs/edit_entry" /></portlet:actionURL>';

			AUI().io(
				url,
				{
					data: AUI().toQueryString(
						{
							<portlet:namespace /><%= Constants.CMD %>: '<%= Constants.ADD %>',
							<portlet:namespace />redirect: document.<portlet:namespace />fm.<portlet:namespace />redirect.value,
							<portlet:namespace />referringPortletResource: document.<portlet:namespace />fm.<portlet:namespace />referringPortletResource.value,
							<portlet:namespace />entryId: document.<portlet:namespace />fm.<portlet:namespace />entryId.value,
							<portlet:namespace />title: title,
							<portlet:namespace />content: content,
							<portlet:namespace />displayDateMonth: document.<portlet:namespace />fm.<portlet:namespace />displayDateMonth.value,
							<portlet:namespace />displayDateDay: document.<portlet:namespace />fm.<portlet:namespace />displayDateDay.value,
							<portlet:namespace />displayDateYear: document.<portlet:namespace />fm.<portlet:namespace />displayDateYear.value,
							<portlet:namespace />displayDateHour: document.<portlet:namespace />fm.<portlet:namespace />displayDateHour.value,
							<portlet:namespace />displayDateMinute: document.<portlet:namespace />fm.<portlet:namespace />displayDateMinute.value,
							<portlet:namespace />displayDateAmPm: document.<portlet:namespace />fm.<portlet:namespace />displayDateAmPm.value,
							<portlet:namespace />status: <%= StatusConstants.DRAFT %>,
							<portlet:namespace />assetTagNames: document.<portlet:namespace />fm.<portlet:namespace />assetTagNames.value
						}
					),
					method: 'POST',
					on: {
						failure: function() {
							if (saveStatus) {
								saveStatus.set('className', 'save-status portlet-msg-error');
								saveStatus.html('<%= UnicodeLanguageUtil.get(pageContext, "could-not-save-draft-to-the-server") %>');
							}
						},
						start: function() {
							if (saveButton) {
								saveButton.attr('disabled', true);
							}

							if (saveStatus) {
								saveStatus.set('className', 'save-status portlet-msg-info pending');
								saveStatus.html('<%= UnicodeLanguageUtil.get(pageContext, "saving-draft") %>');
							}
						},
						success: function(id, obj) {
							var instance = this;

							var message = AUI().JSON.parse(obj.responseText);

							document.<portlet:namespace />fm.<portlet:namespace />entryId.value = message.entryId;
							document.<portlet:namespace />fm.<portlet:namespace />redirect.value = message.redirect;

							if (saveButton) {
								saveButton.attr('disabled', false);
							}

							var tabs1BackButton = AUI().one('#<portlet:namespace />tabs1TabsBack');

							if (tabs1BackButton) {
								tabs1BackButton.attr('href', message.redirect);
							}

							if (cancelButton) {
								cancelButton.detach('click');

								cancelButton.on(
									'click',
									function() {
										location.href = message.redirect;
									}
								);
							}

							var now = saveText.replace(/\[TIME\]/gim, (new Date()).toString());

							if (saveStatus) {
								saveStatus.set('className', 'save-status portlet-msg-success');
								saveStatus.html(now);
							}
						}
					}
				}
			);
		}
		else {
			<portlet:namespace />clearSaveDraftIntervalId();

			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= entry == null ? Constants.ADD : Constants.UPDATE %>";
			document.<portlet:namespace />fm.<portlet:namespace />content.value = content;
			document.<portlet:namespace />fm.<portlet:namespace />status.value = <%= StatusConstants.APPROVED %>;
			submitForm(document.<portlet:namespace />fm);
		}
	}
</aui:script>

<aui:script use="io,json">
	var cancelButton = A.one('#<portlet:namespace />cancelButton');

	if (cancelButton) {
		cancelButton.on(
			'click',
			function() {
				<portlet:namespace />clearSaveDraftIntervalId();

				location.href = '<%= UnicodeFormatter.toString(redirect) %>';
			}
		);
	}

	<c:if test="<%= (entry == null) || (entry.getStatus() == StatusConstants.DRAFT) %>">
		<portlet:namespace />saveDraftIntervalId = setInterval('<portlet:namespace />saveEntry(true)', 30000);
		<portlet:namespace />oldTitle = document.<portlet:namespace />fm.<portlet:namespace />title.value;
		<portlet:namespace />oldContent = <portlet:namespace />initEditor();
	</c:if>
</aui:script>

<portlet:actionURL var="editEntryURL">
	<portlet:param name="struts_action" value="/blogs/edit_entry" />
</portlet:actionURL>

<aui:form action="<%= editEntryURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveEntry(false); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
	<aui:input name="status" type="hidden" value="<%= StatusConstants.APPROVED %>" />

	<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= entry %>" model="<%= BlogsEntry.class %>" />

	<aui:fieldset>
		<c:if test="<%= (entry == null) || (entry.getStatus() == StatusConstants.DRAFT) %>">
			<div class="save-status" id="<portlet:namespace />saveStatus"></div>
		</c:if>

		<aui:input name="title" />

		<aui:input name="displayDate" value="<%= displayDate %>" />

		<aui:field-wrapper label="content">
			<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

			<aui:input name="content" type="hidden" />
		</aui:field-wrapper>

		<liferay-ui:custom-attributes-available className="<%= BlogsEntry.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED %>">
			<aui:input defaultValue="<%= allowTrackbacks %>" helpMessage="to-allow-trackbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" inlineLabel="left" label="allow-incoming-trackbacks" name="allowTrackbacks" />

			<aui:input label="trackbacks-to-send" name="trackbacks" />

			<c:if test="<%= (entry != null) && Validator.isNotNull(entry.getTrackbacks()) %>">
				<aui:field-wrapper name="trackbacks-already-sent">

					<%
					for (String trackback : StringUtil.split(entry.getTrackbacks())) {
					%>

						<%= HtmlUtil.escape(trackback) %><br />

					<%
					}
					%>

				</aui:field-wrapper>
			</c:if>
		</c:if>

		<aui:input name="categories" type="assetCategories" />

		<aui:input name="tags" type="assetTags" />

		<c:if test="<%= entry == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= BlogsEntry.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<c:if test="<%= (entry == null) || (entry.getStatus() == StatusConstants.DRAFT) %>">
				<aui:button name="saveDraftButton" onClick='<%= renderResponse.getNamespace() + "saveEntry(true);" %>' type="button" value="save-draft" />
			</c:if>

			<aui:button name="saveButton" type="submit" value='<%= ((entry == null) || (entry.getStatus() == StatusConstants.DRAFT)) ? "publish" : "save" %>' />

			<aui:button name="cancelButton" onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />title);
	</aui:script>
</c:if>

<%
if (entry != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/blogs/view_entry");
	portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

	PortalUtil.addPortletBreadcrumbEntry(request, entry.getTitle(), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-entry"), currentURL);
}
%>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp";
%>