<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "mbCategoryId");

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

MBMailingList mailingList = null;

try {
	if (categoryId > 0) {
		mailingList = MBMailingListLocalServiceUtil.getCategoryMailingList(scopeGroupId, categoryId);
	}
}
catch (NoSuchMailingListException nsmle) {
}

boolean mailingListActive = BeanParamUtil.getBoolean(mailingList, request, "active");
%>

<portlet:actionURL var="editCategoryURL">
	<portlet:param name="struts_action" value="/message_boards/edit_category" />
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCategory();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

	<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
	<liferay-ui:error exception="<%= CategoryNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= MailingListEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
	<liferay-ui:error exception="<%= MailingListInServerNameException.class %>" message="please-enter-a-valid-incoming-server-name" />
	<liferay-ui:error exception="<%= MailingListInUserNameException.class %>" message="please-enter-a-valid-incoming-user-name" />
	<liferay-ui:error exception="<%= MailingListOutEmailAddressException.class %>" message="please-enter-a-valid-outgoing-email-address" />
	<liferay-ui:error exception="<%= MailingListOutServerNameException.class %>" message="please-enter-a-valid-outgoing-server-name" />
	<liferay-ui:error exception="<%= MailingListOutUserNameException.class %>" message="please-enter-a-valid-outgoing-user-name" />

	<aui:model-context bean="<%= category %>" model="<%= MBCategory.class %>" />

	<aui:fieldset>
		<aui:field-wrapper label="parent-category">

			<%
			String parentCategoryName = StringPool.BLANK;

			try {
				MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

				parentCategoryName = parentCategory.getName();
			}
			catch (NoSuchCategoryException nscce) {
			}
			%>

			<portlet:renderURL var="viewCategoryURL">
				<portlet:param name="struts_action" value="/message_boards/view" />
				<portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" />
			</portlet:renderURL>

			<aui:a href="<%= viewCategoryURL %>" id="parentCategoryName"><%= parentCategoryName %></aui:a>

			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectCategoryURL">
				<portlet:param name="struts_action" value="/message_boards/select_category" />
				<portlet:param name="mbCategoryId" value="<%= String.valueOf((category == null) ? MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID : category.getParentCategoryId()) %>" />
			</portlet:renderURL>

			<%
			String taglibOpenCategoryWindow = "var categoryWindow = window.open('" + selectCategoryURL + "','category', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); categoryWindow.focus();";
			%>

			<aui:button onClick="<%= taglibOpenCategoryWindow %>" value="select" />

			<aui:button id="removeCategoryButton" onClick='<%= renderResponse.getNamespace() + "removeCategory();" %>' value="remove" />

			<aui:input inlineLabel="left" label="merge-with-parent-category" name="mergeWithParentCategory" type="checkbox" />
		</aui:field-wrapper>

		<aui:input name="name" />

		<aui:input name="description" />

		<liferay-ui:custom-attributes-available className="<%= MBCategory.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= MBCategory.class.getName() %>"
				classPK="<%= (category != null) ? category.getCategoryId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= category == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= MBCategory.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<br />

		<liferay-ui:panel-container extended="<%= true %>" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "mailing-list") %>'>

				<aui:model-context bean="<%= mailingList %>" model="<%= MBMailingList.class %>" />

				<aui:input inlineLabel="left" label="active" name="mailingListActive" type="checkbox" value="<%= mailingListActive %>" />

				<div id="<portlet:namespace />mailingListSettings">
					<aui:input name="emailAddress" />

					<br />

					<aui:fieldset label="incoming">

						<%
						String protocol = BeanParamUtil.getString(mailingList, request, "inProtocol", "pop3");
						%>

						<aui:field-wrapper label="protocol">
							<aui:input checked='<%= protocol.startsWith("pop3") %>' inlineLabel="left" label="pop" name="inProtocol" type="radio" value="pop3" />
							<aui:input checked='<%= protocol.startsWith("imap") %>' inlineLabel="left" label="imap" name="inProtocol" type="radio" value="imap" />
						</aui:field-wrapper>

						<aui:input label="server-name" name="inServerName" />

						<aui:input label="server-port" name="inServerPort" value="110" />

						<aui:input inlineLabel="left" label="use-a-secure-network-connection" name="inUseSSL" />

						<aui:input label="user-name" name="inUserName" />

						<aui:input label="password" name="inPassword" />

						<aui:input label="read-interval-minutes" name="inReadInterval" value="5" />
					</aui:fieldset>

					<aui:fieldset label="outgoing">
						<aui:input label="email-address" name="outEmailAddress" />

						<aui:input inlineLabel="left" label="use-custom-outgoing-server" name="outCustom" />

						<div id="<portlet:namespace />outCustomSettings">
							<aui:input label="server-name" name="outServerName" />

							<aui:input label="server-port" name="outServerPort" value="25" />

							<aui:input inlineLabel="left" label="use-a-secure-network-connection" name="outUseSSL" />

							<aui:input label="user-name" name="outUserName" />

							<aui:input label="password" name="outPassword" />
						</div>
					</aui:fieldset>
				</div>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>

	<br />

	<c:if test="<%= (category == null) && PropsValues.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY %>">
		<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
			<portlet:param name="struts_action" value="/message_boards/captcha" />
		</portlet:actionURL>

		<liferay-ui:captcha url="<%= captchaURL %>" />
	</c:if>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />removeCategory() {
		document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = "<%= MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>";

		var nameEl = document.getElementById("<portlet:namespace />parentCategoryName");

		nameEl.href = "";
		nameEl.innerHTML = "";
	}

	function <portlet:namespace />saveCategory() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= category == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectCategory(parentCategoryId, parentCategoryName) {
		document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = parentCategoryId;

		var nameEl = document.getElementById("<portlet:namespace />parentCategoryName");

		nameEl.href = "<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/view" /></portlet:renderURL>&<portlet:namespace />mbCategoryId=" + parentCategoryId;
		nameEl.innerHTML = parentCategoryName + "&nbsp;";
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>

	Liferay.Util.toggleBoxes('<portlet:namespace />mailingListActiveCheckbox', '<portlet:namespace />mailingListSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />outCustomCheckbox', '<portlet:namespace />outCustomSettings');
</aui:script>

<%
if (category != null) {
	MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "update-category"), currentURL);
}
else {
	if (parentCategoryId > 0) {
		MBUtil.addPortletBreadcrumbEntries(parentCategoryId, request, renderResponse);
	}

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-category"), currentURL);
}
%>