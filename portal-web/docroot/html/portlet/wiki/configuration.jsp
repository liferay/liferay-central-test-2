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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", WikiUtil.getEmailFromName(preferences));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", WikiUtil.getEmailFromAddress(preferences));

String emailPageAddedSubjectPrefix = ParamUtil.getString(request, "emailPageAddedSubjectPrefix", WikiUtil.getEmailPageAddedSubjectPrefix(preferences));
String emailPageAddedBody = ParamUtil.getString(request, "emailPageAddedBody", WikiUtil.getEmailPageAddedBody(preferences));
String emailPageAddedSignature = ParamUtil.getString(request, "emailPageAddedSignature", WikiUtil.getEmailPageAddedSignature(preferences));

String emailPageUpdatedSubjectPrefix = ParamUtil.getString(request, "emailPageUpdatedSubjectPrefix", WikiUtil.getEmailPageUpdatedSubjectPrefix(preferences));
String emailPageUpdatedBody = ParamUtil.getString(request, "emailPageUpdatedBody", WikiUtil.getEmailPageUpdatedBody(preferences));
String emailPageUpdatedSignature = ParamUtil.getString(request, "emailPageUpdatedSignature", WikiUtil.getEmailPageUpdatedSignature(preferences));
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String bodyEditorParam = "";
	String bodyEditorBody = "";
	String signatureEditorParam = "";
	String signatureEditorBody = "";

	if (tabs2.equals("page-added-email")) {
		bodyEditorParam = "emailPageAddedBody";
		bodyEditorBody = emailPageAddedBody;
		signatureEditorParam = "emailPageAddedSignature";
		signatureEditorBody = emailPageAddedSignature;
	}
	else if (tabs2.equals("page-updated-email")) {
		bodyEditorParam = "emailPageUpdatedBody";
		bodyEditorBody = emailPageUpdatedBody;
		signatureEditorParam = "emailPageUpdatedSignature";
		signatureEditorBody = emailPageUpdatedSignature;
	}
	%>

	function <portlet:namespace />save() {
		<c:if test='<%= tabs2.equals("display-settings") %>'>
			document.<portlet:namespace />fm.<portlet:namespace />visibleNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentVisibleNodes);
			document.<portlet:namespace />fm.<portlet:namespace />hiddenNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />availableVisibleNodes);
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />save(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />

<liferay-ui:tabs
	names="email-from,page-added-email,page-updated-email,display-settings,rss"
	param="tabs2"
	url="<%= portletURL %>"
/>

<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
<liferay-ui:error key="emailPageAddedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailPageAddedSignature" message="please-enter-a-valid-signature" />
<liferay-ui:error key="emailPageAddedSubjectPrefix" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailPageUpdatedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailPageUpdatedSignature" message="please-enter-a-valid-signature" />
<liferay-ui:error key="emailPageUpdatedSubjectPrefix" message="please-enter-a-valid-subject" />
<liferay-ui:error key="visibleNodesCount" message="please-specify-at-least-one-visible-node" />

<c:choose>
	<c:when test='<%= tabs2.equals("email-from") %>'>
		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="name" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />emailFromName" type="text" value="<%= emailFromName %>" />
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="address" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />emailFromAddress" type="text" value="<%= emailFromAddress %>" />
			</td>
		</tr>
		</table>

		<br />

		<b><liferay-ui:message key="definition-of-terms" /></b>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<b>[$COMPANY_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-id-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_MX$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-mx-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMMUNITY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-community-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTLET_NAME$]</b>
			</td>
			<td>
				<%= ((RenderResponseImpl)renderResponse).getTitle() %>
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.startsWith("page-") %>'>
		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="enabled" />
			</td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("page-added-email") %>'>
						<liferay-ui:input-checkbox param="emailPageAddedEnabled" defaultValue="<%= WikiUtil.getEmailPageAddedEnabled(preferences) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("page-updated-email") %>'>
						<liferay-ui:input-checkbox param="emailPageUpdatedEnabled" defaultValue="<%= WikiUtil.getEmailPageUpdatedEnabled(preferences) %>" />
					</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="subject-prefix" />
			</td>
			<td>
				<c:choose>
					<c:when test='<%= tabs2.equals("page-added-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailPageAddedSubjectPrefix" type="text" value="<%= emailPageAddedSubjectPrefix %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("page-updated-email") %>'>
						<input class="lfr-input-text" name="<portlet:namespace />emailPageUpdatedSubjectPrefix" type="text" value="<%= emailPageUpdatedSubjectPrefix %>" />
					</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="body" />
			</td>
			<td>
				<textarea class="lfr-textarea" name="<%= bodyEditorParam %>" wrap="soft"><%= bodyEditorBody %></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<br />
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="signature" />
			</td>
			<td>
				<textarea class="lfr-textarea" name="<%= signatureEditorParam %>" wrap="soft"><%= signatureEditorBody %></textarea>
			</td>
		</tr>
		</table>

		<br />

		<b><liferay-ui:message key="definition-of-terms" /></b>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<b>[$COMPANY_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-id-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_MX$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-mx-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMPANY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-company-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$COMMUNITY_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-community-name-associated-with-the-wiki" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$FROM_ADDRESS$]</b>
			</td>
			<td>
				<%= emailFromAddress %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$FROM_NAME$]</b>
			</td>
			<td>
				<%= emailFromName %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$NODE_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-node-in-which-the-page-was-added" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_CONTENT$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-content" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_ID$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-id" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_TITLE$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-title" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_URL$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-page-url" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-email-address-of-the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PAGE_USER_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-user-who-added-the-page" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTAL_URL$]</b>
			</td>
			<td>
				<%= company.getVirtualHost() %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$PORTLET_NAME$]</b>
			</td>
			<td>
				<%= ((RenderResponseImpl)renderResponse).getTitle() %>
			</td>
		</tr>
		<tr>
			<td>
				<b>[$TO_ADDRESS$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-address-of-the-email-recipient" />
			</td>
		</tr>
		<tr>
			<td>
				<b>[$TO_NAME$]</b>
			</td>
			<td>
				<liferay-ui:message key="the-name-of-the-email-recipient" />
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.equals("display-settings") %>'>
		<c:if test="<%= PropsValues.WIKI_PAGE_COMMENTS_ENABLED %>">
			<table class="lfr-table">
			<tr>
				<td class="lfr-label">
					<liferay-ui:message key="enable-comments" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="enableComments" defaultValue="<%= enableComments %>" />
				</td>
			</tr>
			<tr>
				<td class="lfr-label">
					<liferay-ui:message key="enable-comment-ratings" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="enableCommentRatings" defaultValue="<%= enableCommentRatings %>" />
				</td>
			</tr>
			</table>
		</c:if>

		<br />

		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="visible-wikis" />
			</td>
		</tr>
		<tr>
			<td>
				<input name="<portlet:namespace />visibleNodes" type="hidden" value="" />
				<input name="<portlet:namespace />hiddenNodes" type="hidden" value="" />

				<%
				Set<String> currentVisibleNodes = SetUtil.fromArray(StringUtil.split(allNodes));

				// Left list

				List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

				for (int i = 0; i < visibleNodes.length; i++) {
					String folderColumn = visibleNodes[i];

					leftList.add(new KeyValuePair(folderColumn, LanguageUtil.get(pageContext, folderColumn)));
				}

				Arrays.sort(visibleNodes);
				Arrays.sort(hiddenNodes);

				Iterator<String> itr = currentVisibleNodes.iterator();

				while (itr.hasNext()) {
					String folderColumn = itr.next();

					if ((Arrays.binarySearch(hiddenNodes, folderColumn) < 0) && (Arrays.binarySearch(visibleNodes, folderColumn) < 0)) {
						leftList.add(new KeyValuePair(folderColumn, LanguageUtil.get(pageContext, folderColumn)));
					}
				}

				// Right list

				List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

				for (int i = 0; i < hiddenNodes.length; i++) {
					String folderColumn = hiddenNodes[i];

					if (Arrays.binarySearch(visibleNodes, folderColumn) < 0) {
						rightList.add(new KeyValuePair(folderColumn, LanguageUtil.get(pageContext, folderColumn)));
					}
				}

				rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));

				%>

				<liferay-ui:input-move-boxes
					formName="fm"
					leftTitle="visible"
					rightTitle="hidden"
					leftBoxName="currentVisibleNodes"
					rightBoxName="availableVisibleNodes"
					leftReorder="true"
					leftList="<%= leftList %>"
					rightList="<%= rightList %>"
				/>
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test='<%= tabs2.equals("rss") %>'>
		<table class="lfr-table">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="maximum-items-to-display" />
			</td>
			<td>
				<select name="<portlet:namespace />rssDelta">
					<option <%= (rssDelta == 1) ? "selected" : "" %> value="1">1</option>
					<option <%= (rssDelta == 2) ? "selected" : "" %> value="2">2</option>
					<option <%= (rssDelta == 3) ? "selected" : "" %> value="3">3</option>
					<option <%= (rssDelta == 4) ? "selected" : "" %> value="4">4</option>
					<option <%= (rssDelta == 5) ? "selected" : "" %> value="5">5</option>
					<option <%= (rssDelta == 10) ? "selected" : "" %> value="10">10</option>
					<option <%= (rssDelta == 15) ? "selected" : "" %> value="15">15</option>
					<option <%= (rssDelta == 20) ? "selected" : "" %> value="20">20</option>
					<option <%= (rssDelta == 25) ? "selected" : "" %> value="25">25</option>
					<option <%= (rssDelta == 30) ? "selected" : "" %> value="30">30</option>
					<option <%= (rssDelta == 40) ? "selected" : "" %> value="40">40</option>
					<option <%= (rssDelta == 50) ? "selected" : "" %> value="50">50</option>
					<option <%= (rssDelta == 60) ? "selected" : "" %> value="60">60</option>
					<option <%= (rssDelta == 70) ? "selected" : "" %> value="70">70</option>
					<option <%= (rssDelta == 80) ? "selected" : "" %> value="80">80</option>
					<option <%= (rssDelta == 90) ? "selected" : "" %> value="90">90</option>
					<option <%= (rssDelta == 100) ? "selected" : "" %> value="100">100</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="display-style" />
			</td>
			<td>
				<select name="<portlet:namespace />rssDisplayStyle">
					<option <%= (rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) ? "selected" : "" %> value="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>"><liferay-ui:message key="full-content" /></option>
					<option <%= (rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) ? "selected" : "" %> value="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>"><liferay-ui:message key="abstract" /></option>
					<option <%= (rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) ? "selected" : "" %> value="<%= RSSUtil.DISPLAY_STYLE_TITLE %>"><liferay-ui:message key="title" /></option>
				</select>
			</td>
		</tr>
		</table>
	</c:when>
</c:choose>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>