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

<%@ include file="/html/portlet/invitation/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");
String redirect = ParamUtil.getString(request, "redirect");

PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);

String emailMessageSubject = ParamUtil.getString(request, "emailMessageSubject", InvitationUtil.getEmailMessageSubject(preferences));
String emailMessageBody = ParamUtil.getString(request, "emailMessageBody", InvitationUtil.getEmailMessageBody(preferences));

String editorParam = "emailMessageBody";
String editorContent = emailMessageBody;
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-ui:error key="emailMessageBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailMessageSubject" message="please-enter-a-valid-subject" />

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" label="subject" name="emailMessageSubject" type="text" value="<%= emailMessageSubject %>" />

		<aui:field-wrapper label="body">
			<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

			<aui:input name="<%= editorParam %>" type="hidden" />
		</aui:field-wrapper>
	</aui:fieldset>

	<div class="definition-of-terms">
		<h4><liferay-ui:message key="definition-of-terms" /></h4>

		<dl>
			<dt>
				[$FROM_ADDRESS$]
			</dt>
			<dd>
				<liferay-ui:message key="the-address-of-the-email-sender" />
			</dd>
			<dt>
				[$FROM_NAME$]
			</dt>
			<dd>
				<liferay-ui:message key="the-name-of-the-email-sender" />
			</dd>
			<dt>
				[$PAGE_URL$]
			</dt>
			<dd>
				<%= PortalUtil.getLayoutFullURL(layout, themeDisplay) %>
			</dd>
			<dt>
				  [$PORTAL_URL$]
			</dt>
			<dd>
				<%= company.getVirtualHost() %>
			</dd>
		</dl>
	</div>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.invitation.edit_configuration.jsp";
%>