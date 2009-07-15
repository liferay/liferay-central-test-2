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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "mbCategoryId");

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID);

MBMailingList mailingList = null;

try {
	if (categoryId > 0) {
		mailingList = MBMailingListLocalServiceUtil.getCategoryMailingList(categoryId);
	}
}
catch (NoSuchMailingListException nsmle) {
}

boolean mailingListActive = BeanParamUtil.getBoolean(mailingList, request, "active");
%>

<script type="text/javascript">
	function <portlet:namespace />removeCategory() {
		document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = "<%= MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID %>";

		var nameEl = document.getElementById("<portlet:namespace />parentCategoryName");

		nameEl.href = "";
		nameEl.innerHTML = "";

		jQuery("#<portlet:namespace />merge-with-parent-checkbox-div").hide();
		jQuery("#<portlet:namespace />mergeWithParentCategoryCheckbox").attr("checked", false);
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

		if (parentCategoryId != <%= MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID %>) {
			jQuery("#<portlet:namespace />merge-with-parent-checkbox-div").show();
		}
	}
</script>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/message_boards/edit_category" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveCategory(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />mbCategoryId" type="hidden" value="<%= categoryId %>" />
<input name="<portlet:namespace />parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
<liferay-ui:error exception="<%= CategoryNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= MailingListEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error exception="<%= MailingListInServerNameException.class %>" message="please-enter-a-valid-incoming-server-name" />
<liferay-ui:error exception="<%= MailingListInUserNameException.class %>" message="please-enter-a-valid-incoming-user-name" />
<liferay-ui:error exception="<%= MailingListOutEmailAddressException.class %>" message="please-enter-a-valid-outgoing-email-address" />
<liferay-ui:error exception="<%= MailingListOutServerNameException.class %>" message="please-enter-a-valid-outgoing-server-name" />
<liferay-ui:error exception="<%= MailingListOutUserNameException.class %>" message="please-enter-a-valid-outgoing-user-name" />

<div class="breadcrumbs">
	<%= BreadcrumbsUtil.removeLastClass(MBUtil.getBreadcrumbs(parentCategoryId, 0, pageContext, renderRequest, renderResponse)) %> &raquo;

	<span class="last"><liferay-ui:message key='<%= ((category == null) ? Constants.ADD : Constants.UPDATE) + "-category" %>' /></span>
</div>

<table class="lfr-table">

<c:if test="<%= category != null %>">
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="parent-category" />
		</td>
		<td>
			<table class="lfr-table">
			<tr>
				<td>

					<%
					String parentCategoryName = StringPool.BLANK;

					try {
						MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

						parentCategoryName = parentCategory.getName();
					}
					catch (NoSuchCategoryException nscce) {
					}
					%>

					<a href="<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/view" /><portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" /></portlet:renderURL>" id="<portlet:namespace />parentCategoryName">
					<%= parentCategoryName %></a>

					<input type="button" value="<liferay-ui:message key="select" />" onClick="var categoryWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/message_boards/select_category" /><portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" /></portlet:renderURL>', 'category', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); categoryWindow.focus();" />

					<input id="<portlet:namespace />removeCategoryButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeCategory();" />
				</td>
				<td>
					<div id="<portlet:namespace />merge-with-parent-checkbox-div"
						<c:if test="<%= category.getParentCategoryId() == MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID %>">
							style="display: none;"
						</c:if>
					>
						<liferay-ui:input-checkbox param="mergeWithParentCategory" />

						<liferay-ui:message key="merge-with-parent-category" />
					</div>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
</c:if>

<tr>
	<td class="lfr-label">
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= MBCategory.class %>" bean="<%= category %>" field="name" />
	</td>
</tr>
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= MBCategory.class %>" bean="<%= category %>" field="description" />
	</td>
</tr>

<c:if test="<%= category == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= MBCategory.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br />

<liferay-ui:tabs names="mailing-list" />

<table class="lfr-table">
<tr>
	<td class="lfr-label">
		<liferay-ui:message key="active" />
	</td>
	<td>
		<liferay-ui:input-checkbox param="mailingListActive" defaultValue="<%= mailingListActive %>" />
	</td>
</tr>
<tbody id="<portlet:namespace />mailingListSettings">
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="email-address" />
		</td>
		<td>
			<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="emailAddress" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />

			<liferay-ui:tabs
				names="incoming,outgoing"
				refresh="<%= false %>"
			>
				<liferay-ui:section>
					<table class="lfr-table">
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="protocol" />
						</td>
						<td>
							<table class="lfr-table">
							<tr>

								<%
								String protocol = BeanParamUtil.getString(mailingList, request, "inProtocol", "pop3");
								%>

								<td>
									<input <%= protocol.startsWith("pop3") ? "checked" : "" %> name="<portlet:namespace />inProtocol" type="radio" value="pop3"> <liferay-ui:message key="pop" />
								</td>
								<td>
									<input <%= protocol.startsWith("imap") ? "checked" : "" %> name="<portlet:namespace />inProtocol" type="radio" value="imap"> <liferay-ui:message key="imap" />
								</td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="server-name" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="inServerName" />
						</td>
					</tr>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="server-port" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="inServerPort" defaultValue="110" />
						</td>
					</tr>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="use-a-secure-network-connection" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="inUseSSL" />
						</td>
					</tr>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="user-name" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="inUserName" />
						</td>
					</tr>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="password" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="inPassword" />
						</td>
					</tr>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="read-interval-minutes" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="inReadInterval" defaultValue="5" />
						</td>
					</tr>
					</table>
				</liferay-ui:section>
				<liferay-ui:section>
					<table class="lfr-table">
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="email-address" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="outEmailAddress" />
						</td>
					</tr>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="use-custom-outgoing-server" />
						</td>
						<td>
							<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="outCustom" />
						</td>
					</tr>
					<tbody id="<portlet:namespace />outCustomSettings">
						<tr>
							<td>
								<liferay-ui:message key="server-name" />
							</td>
							<td>
								<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="outServerName" />
							</td>
						</tr>
						<tr>
							<td class="lfr-label">
								<liferay-ui:message key="server-port" />
							</td>
							<td>
								<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="outServerPort" defaultValue="25" />
							</td>
						</tr>
						<tr>
							<td class="lfr-label">
								<liferay-ui:message key="use-a-secure-network-connection" />
							</td>
							<td>
								<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="outUseSSL" />
							</td>
						</tr>
						<tr>
							<td class="lfr-label">
								<liferay-ui:message key="user-name" />
							</td>
							<td>
								<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="outUserName" />
							</td>
						</tr>
						<tr>
							<td class="lfr-label">
								<liferay-ui:message key="password" />
							</td>
							<td>
								<liferay-ui:input-field model="<%= MBMailingList.class %>" bean="<%= mailingList %>" field="outPassword" />
							</td>
						</tr>
					</tbody>
					</table>
				</liferay-ui:section>
			</liferay-ui:tabs>
		</td>
	</tr>
</tbody>
</table>

<br />

<c:if test="<%= (category == null) && PropsValues.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY %>">
	<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
		<portlet:param name="struts_action" value="/message_boards/captcha" />
	</portlet:actionURL>

	<liferay-ui:captcha url="<%= captchaURL %>" />
</c:if>

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<script type="text/javascript">
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>

	Liferay.Util.toggleBoxes('<portlet:namespace />mailingListActiveCheckbox', '<portlet:namespace />mailingListSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />outCustomCheckbox', '<portlet:namespace />outCustomSettings');
</script>