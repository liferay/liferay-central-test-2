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
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", BlogsUtil.getEmailFromName(preferences));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", BlogsUtil.getEmailFromAddress(preferences));

String emailEntryAddedSubject = ParamUtil.getString(request, "emailEntryAddedSubject", BlogsUtil.getEmailEntryAddedSubject(preferences));
String emailEntryAddedBody = ParamUtil.getString(request, "emailEntryAddedBody", BlogsUtil.getEmailEntryAddedBody(preferences));

String emailEntryUpdatedSubject = ParamUtil.getString(request, "emailEntryUpdatedSubject", BlogsUtil.getEmailEntryUpdatedSubject(preferences));
String emailEntryUpdatedBody = ParamUtil.getString(request, "emailEntryUpdatedBody", BlogsUtil.getEmailEntryUpdatedBody(preferences));

String bodyEditorParam = StringPool.BLANK;
String bodyEditorBody = StringPool.BLANK;

if (tabs2.equals("entry-added-email")) {
	bodyEditorParam = "emailEntryAddedBody";
	bodyEditorBody = emailEntryAddedBody;
}
else if (tabs2.equals("entry-updated-email")) {
	bodyEditorParam = "emailEntryUpdatedBody";
	bodyEditorBody = emailEntryUpdatedBody;
}
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">
	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("display-settings") %>'>
			document.<portlet:namespace />fm.<portlet:namespace />visibleNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentVisibleNodes);
			document.<portlet:namespace />fm.<portlet:namespace />hiddenNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />availableVisibleNodes);
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:tabs
		names="email-from,entry-added-email,entry-updated-email,display-settings,rss"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailEntryAddedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailEntryUpdatedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

	<c:choose>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="emailFromName" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="emailFromAddress" value="<%= emailFromAddress %>" />
			</aui:fieldset>

			<strong><liferay-ui:message key="definition-of-terms" /></strong>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<strong>[$COMPANY_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-id-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_MX$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-mx-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-name-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMMUNITY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-community-name-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$BLOGS_ENTRY_USER_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-user-who-added-the-blog-entry" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$BLOGS_ENTRY_USER_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-user-who-added-the-blog-entry" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTLET_NAME$]</strong>
				</td>
				<td>
					<%= ((RenderResponseImpl)renderResponse).getTitle() %>
				</td>
			</tr>
			</table>

			<br />
		</c:when>
		<c:when test='<%= tabs2.startsWith("entry-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("entry-added-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailEntryAddedEnabled" type="checkbox" value="<%= BlogsUtil.getEmailEntryAddedEnabled(preferences) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("entry-updated-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailEntryUpdatedEnabled" type="checkbox" value="<%= BlogsUtil.getEmailEntryUpdatedEnabled(preferences) %>" />
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test='<%= tabs2.equals("entry-added-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="emailEntryAddedSubject" type="text" value="<%= emailEntryAddedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("entry-updated-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="emailEntryUpdatedSubject" type="text" value="<%= emailEntryUpdatedSubject %>" />
					</c:when>
				</c:choose>

				<aui:input cssClass="lfr-textarea-container" label="body" name="<%= bodyEditorParam %>" type="textarea" value="<%= bodyEditorBody %>" />
			</aui:fieldset>

			<strong><liferay-ui:message key="definition-of-terms" /></strong>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<strong>[$COMPANY_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-id-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_MX$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-mx-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-name-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMMUNITY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-community-name-associated-with-the-blog" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$BLOGS_ENTRY_USER_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-user-who-added-the-blog-entry" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$BLOGS_ENTRY_USER_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-user-who-added-the-blog-entry" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$BLOGS_ENTRY_URL$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-blog-entry-url" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$FROM_ADDRESS$]</strong>
				</td>
				<td>
					<%= emailFromAddress %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$FROM_NAME$]</strong>
				</td>
				<td>
					<%= emailFromName %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTAL_URL$]</strong>
				</td>
				<td>
					<%= company.getVirtualHost() %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTLET_NAME$]</strong>
				</td>
				<td>
					<%= ((RenderResponseImpl)renderResponse).getTitle() %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$TO_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-address-of-the-email-recipient" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$TO_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-name-of-the-email-recipient" />
				</td>
			</tr>
			</table>

			<br />
		</c:when>
		<c:when test='<%= tabs2.equals("display-settings") %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="set-the-display-styles-used-to-display-blogs-when-viewed-via-as-a-regular-page-or-as-an-rss" />
			</div>

			<aui:fieldset>
				<aui:select label="maximum-items-to-display" name="pageDelta">
					<aui:option label="1" selected="<%= pageDelta == 1 %>" />
					<aui:option label="2" selected="<%= pageDelta == 2 %>" />
					<aui:option label="3" selected="<%= pageDelta == 3 %>" />
					<aui:option label="4" selected="<%= pageDelta == 4 %>" />
					<aui:option label="5" selected="<%= pageDelta == 5 %>" />
					<aui:option label="10" selected="<%= pageDelta == 10 %>" />
					<aui:option label="15" selected="<%= pageDelta == 15 %>" />
					<aui:option label="20" selected="<%= pageDelta == 20 %>" />
					<aui:option label="25" selected="<%= pageDelta == 25 %>" />
					<aui:option label="30" selected="<%= pageDelta == 30 %>" />
					<aui:option label="40" selected="<%= pageDelta == 40 %>" />
					<aui:option label="50" selected="<%= pageDelta == 50 %>" />
					<aui:option label="60" selected="<%= pageDelta == 60 %>" />
					<aui:option label="70" selected="<%= pageDelta == 70 %>" />
					<aui:option label="80" selected="<%= pageDelta == 80 %>" />
					<aui:option label="90" selected="<%= pageDelta == 90 %>" />
					<aui:option label="100" selected="<%= pageDelta == 100 %>" />
				</aui:select>

				<aui:select label="display-style" name="pageDisplayStyle">
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
				</aui:select>

				<aui:input inlineLabel="left" name="enableFlags" type="checkbox" value="<%= enableFlags %>" />

				<aui:input inlineLabel="left" name="enableRatings" type="checkbox" value="<%= enableRatings %>" />

				<c:if test="<%= PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED %>">
					<aui:input inlineLabel="left" name="enableComments" type="checkbox" value="<%= enableComments %>" />

					<aui:input inlineLabel="left" name="enableCommentRatings" type="checkbox" value="<%= enableCommentRatings %>" />
				</c:if>
			</aui:fieldset>

		</c:when>
		<c:when test='<%= tabs2.equals("rss") %>'>
			<aui:fieldset>
				<aui:select label="maximum-items-to-display" name="rssDelta">
					<aui:option label="1" selected="<%= rssDelta == 1 %>" />
					<aui:option label="2" selected="<%= rssDelta == 2 %>" />
					<aui:option label="3" selected="<%= rssDelta == 3 %>" />
					<aui:option label="4" selected="<%= rssDelta == 4 %>" />
					<aui:option label="5" selected="<%= rssDelta == 5 %>" />
					<aui:option label="10" selected="<%= rssDelta == 10 %>" />
					<aui:option label="15" selected="<%= rssDelta == 15 %>" />
					<aui:option label="20" selected="<%= rssDelta == 20 %>" />
					<aui:option label="25" selected="<%= rssDelta == 25 %>" />
					<aui:option label="30" selected="<%= rssDelta == 30 %>" />
					<aui:option label="40" selected="<%= rssDelta == 40 %>" />
					<aui:option label="50" selected="<%= rssDelta == 50 %>" />
					<aui:option label="60" selected="<%= rssDelta == 60 %>" />
					<aui:option label="70" selected="<%= rssDelta == 70 %>" />
					<aui:option label="80" selected="<%= rssDelta == 80 %>" />
					<aui:option label="90" selected="<%= rssDelta == 90 %>" />
					<aui:option label="100" selected="<%= rssDelta == 100 %>" />
				</aui:select>

				<aui:select label="display-style" name="rssDisplayStyle">
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
				</aui:select>

				<aui:select label="format" name="rssFormat">
					<aui:option label="RSS 1.0" selected='<%= rssFormat.equals("rss10") %>' value="rss10" />
					<aui:option label="RSS 2.0" selected='<%= rssFormat.equals("rss20") %>' value="rss20" />
					<aui:option label="Atom 1.0" selected='<%= rssFormat.equals("atom10") %>' value="atom10" />
				</aui:select>
			</aui:fieldset>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>