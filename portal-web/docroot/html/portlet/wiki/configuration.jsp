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

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("display-settings") %>'>
			document.<portlet:namespace />fm.<portlet:namespace />visibleNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentVisibleNodes);
			document.<portlet:namespace />fm.<portlet:namespace />hiddenNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />availableVisibleNodes);
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

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
					<liferay-ui:message key="the-company-id-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_MX$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-mx-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-name-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMMUNITY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-community-name-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_USER_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-user-who-added-the-page" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_USER_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-user-who-added-the-page" />
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
		<c:when test='<%= tabs2.startsWith("page-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("page-added-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailPageAddedEnabled" type="checkbox" value="<%= WikiUtil.getEmailPageAddedEnabled(preferences) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("page-updated-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailPageUpdatedEnabled" type="checkbox" value="<%= WikiUtil.getEmailPageUpdatedEnabled(preferences) %>" />
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test='<%= tabs2.equals("page-added-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject-prefix" name="emailPageAddedSubjectPrefix" type="text" value="<%= emailPageAddedSubjectPrefix %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("page-updated-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject-prefix" name="emailPageUpdatedSubjectPrefix" type="text" value="<%= emailPageUpdatedSubjectPrefix %>" />
					</c:when>
				</c:choose>

				<aui:input cssClass="lfr-textarea-container" label="body" name="<%= bodyEditorParam %>" type="textarea" value="<%= bodyEditorBody %>" />

				<aui:input cssClass="lfr-textarea-container" label="signature" name="<%= signatureEditorParam %>" type="textarea" value="<%= signatureEditorBody %>" wrap="soft" />
			</aui:fieldset>

			<strong><liferay-ui:message key="definition-of-terms" /></strong>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<strong>[$COMPANY_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-id-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_MX$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-mx-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-name-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMMUNITY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-community-name-associated-with-the-wiki" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$DIFFS_URL$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-url-of-the-page-comparing-this-page-content-with-the-previous-version" />
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
					<strong>[$NODE_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-node-in-which-the-page-was-added" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_CONTENT$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-page-content" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_DATE_UPDATE$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-date-of-the-modifications" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_DIFFS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-page-content-compared-with-the-previous-version-page-content" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-page-id" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_SUMMARY$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-summary-of-the-page-or-the-modifications" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_TITLE$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-page-title" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_URL$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-page-url" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_USER_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-user-who-added-the-page" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PAGE_USER_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-user-who-added-the-page" />
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
			<aui:fieldset>
				<c:if test="<%= PropsValues.WIKI_PAGE_RATINGS_ENABLED || PropsValues.WIKI_PAGE_COMMENTS_ENABLED %>">
					<c:if test="<%= PropsValues.WIKI_PAGE_RATINGS_ENABLED %>">
						<aui:input inlineLabel="left" name="enablePageRatings" type="checkbox" value="<%= enablePageRatings %>" />
					</c:if>

					<c:if test="<%= PropsValues.WIKI_PAGE_COMMENTS_ENABLED %>">
						<aui:input inlineLabel="left" name="enableComments" type="checkbox" value="<%= enableComments %>" />

						<aui:input inlineLabel="left" name="enableCommentRatings" type="checkbox" value="<%= enableCommentRatings %>" />
					</c:if>
				</c:if>
			</aui:fieldset>

			<aui:fieldset>
				<aui:legend label="visible-wikis" />
				<aui:input name="visibleNodes" type="hidden" />
				<aui:input name="hiddenNodes" type="hidden" />

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
			</aui:fieldset>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>

</aui:form>