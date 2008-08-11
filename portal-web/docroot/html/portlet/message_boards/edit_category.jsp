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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%

themeDisplay.setIncludeServiceJs(true);

String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

MBMailing mailing = (MBMailing)request.getAttribute(WebKeys.MESSAGE_BOARDS_MAILING);

long categoryId = BeanParamUtil.getLong(category, request, "categoryId");

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID);
%>


<div id="<portlet:namespace/>successMessage" class="portlet-msg-success" style="display:none ;">
</div>

<script type="text/javascript">
	function <portlet:namespace />removeCategory() {
		document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = "<%= MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID %>";

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

		nameEl.href = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view" /></portlet:renderURL>&<portlet:namespace />categoryId=" + parentCategoryId;
		nameEl.innerHTML = parentCategoryName + "&nbsp;";
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/edit_category" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveCategory(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />categoryId" type="hidden" value="<%= categoryId %>" />
<input name="<portlet:namespace />parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

<liferay-ui:tabs
	names="category"
	backURL="<%= redirect %>"
/>

<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
<liferay-ui:error exception="<%= CategoryNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= MailingListAddressException.class %>" message="please-enter-a-valid-mailing-list-address" />
<liferay-ui:error exception="<%= MailAddressException.class %>" message="please-enter-a-valid-mail-address" />
<liferay-ui:error exception="<%= MailInServerNameException.class %>" message="please-enter-a-valid-incoming-mail-server-name" />
<liferay-ui:error exception="<%= MailInUserNameException.class %>" message="please-enter-a-valid-incoming-user-name" />
<liferay-ui:error exception="<%= MailOutServerNameException.class %>" message="please-enter-a-valid-outgoing-mail-server-name" />
<liferay-ui:error exception="<%= MailOutUserNameException.class %>" message="please-enter-a-valid-outgoing-user-name" />

<c:if test="<%= parentCategoryId != MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID %>">
	<div class="breadcrumbs">
		<%= MBUtil.getBreadcrumbs(parentCategoryId, 0, pageContext, renderRequest, renderResponse) %>
	</div>
</c:if>

<table class="lfr-table">

<c:if test="<%= category != null %>">
	<tr>
		<td>
			<liferay-ui:message key="parent-category" />
		</td>
		<td>
			<table class="lfr-table">
			<tr>
				<td>

					<%
					String parentCategoryName = "";

					try {
						MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

						parentCategoryName = parentCategory.getName();
					}
					catch (NoSuchCategoryException nsce) {
					}
					%>

					<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/message_boards/view" /><portlet:param name="categoryId" value="<%= String.valueOf(parentCategoryId) %>" /></portlet:renderURL>" id="<portlet:namespace />parentCategoryName">
					<%= parentCategoryName %></a>

					<input type="button" value="<liferay-ui:message key="select" />" onClick="var categoryWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/message_boards/select_category" /><portlet:param name="categoryId" value="<%= String.valueOf(parentCategoryId) %>" /></portlet:renderURL>', 'category', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); categoryWindow.focus();" />

					<input id="<portlet:namespace />removeCategoryButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeCategory();" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="mergeWithParentCategory" />

					<liferay-ui:message key="merge-with-parent-category" />
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
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= MBCategory.class %>" bean="<%= category %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= MBCategory.class %>" bean="<%= category %>" field="description" />
	</td>
</tr>

<c:if test="<%= MBCategoryPermission.contains(permissionChecker, plid.longValue(), categoryId, ActionKeys.ADD_MAILING)%>">
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="mailing-list" />
	</td>
	<td>
	<div id="<portlet:namespace/>mailingListTabs" style="display: none;">
		<table class="lfr-table" >
			<c:if test="<%= mailing != null && MBMailingPermission.contains(permissionChecker, mailing, ActionKeys.ACTIVATE)%>">
			<tr>
				<td>
					<liferay-ui:message key="deactivate" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="active" defaultValue="<%= !mailing.isActive() %>" onClick="<%= renderResponse.getNamespace()+"changeActiveMailingStatus();"%>" />
				</td>
			</tr>
			</c:if>
			<tr>
				<td>
					<liferay-ui:message key="mailing-list-address" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailingListAddress" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-address" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailAddress" />
				</td>
			</tr>
		</table>
	<liferay-ui:tabs param="inOutServer" names="incoming,outgoing" refresh="<%= false %>" >
		<liferay-ui:section>
		<table class="lfr-table" >
			<tr>
				<td>
					<liferay-ui:message key="mail-protocol" />
				</td>
				<td>
					<table class="lfr-table">
					<tr>
						<td>
							<input id="<portlet:namespace />mailInProtocolPOP" name="<portlet:namespace />mailInProtocol" type="radio" value="pop3" <c:if test='<%= Validator.isNull(renderRequest.getParameter("mailInProtocol")) || "pop3".equals(renderRequest.getParameter("mailInProtocol")) || (mailing != null && "pop3".equals(mailing.getMailInProtocol()))%>'>checked="checked"</c:if> onclick="<portlet:namespace/>changeInPort();" > <liferay-ui:message key="pop" />
						</td>
						<td>
							<input id="<portlet:namespace />mailInProtocolIMAP"  name="<portlet:namespace />mailInProtocol" type="radio" value="imap" <c:if test='<%= "imap".equals(renderRequest.getParameter("mailInProtocol")) || (mailing != null && "imap".equals(mailing.getMailInProtocol()))%>'>checked="checked"</c:if> onclick="<portlet:namespace/>changeInPort();"> <liferay-ui:message key="imap" />
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-server-name" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailInServerName" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="use-a-secure-network-connection" />
				</td>
				<td>
					<liferay-ui:input-checkbox formName="<portlet:namespace />fm" param="mailInUseSSL" defaultValue="<%= (mailing == null) ? false : mailing.isMailInUseSSL() %>" onClick="<%= renderResponse.getNamespace()+"changeInPort();"%>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-server-port" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailInServerPort" defaultValue="110" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-user-name" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailInUserName" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-password" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailInPassword" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-read-interval" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailInReadInterval" />
				</td>
			</tr>
		</table>
		</liferay-ui:section>
		<liferay-ui:section>
		<table class="lfr-table" >
			<tr>
				<td>
					<liferay-ui:message key="use-configured-outgoing-sever" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="mailOutConfigured" defaultValue="<%= (mailing != null) ? mailing.isMailOutConfigured() : false%>" onClick="<%= renderResponse.getNamespace()+"changeMailOutConfigured();"%>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-server-name" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailOutServerName" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="use-a-secure-network-connection" />
				</td>
				<td>
					<liferay-ui:input-checkbox formName="<portlet:namespace />fm" param="mailOutUseSSL" defaultValue="<%= (mailing == null) ? false : mailing.isMailOutUseSSL() %>" onClick="<%= renderResponse.getNamespace()+"changeOutPort();"%>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-server-port" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailOutServerPort" defaultValue="25" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-user-name" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailOutUserName" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="mail-password" />
				</td>
				<td>
					<liferay-ui:input-field model="<%= MBMailing.class %>" bean="<%= mailing %>" field="mailOutPassword" />
				</td>
			</tr>
		</table>
		</liferay-ui:section>
	</liferay-ui:tabs>
	</div>
		<input id="<portlet:namespace/>mailingList" name="<portlet:namespace/>mailingList" type="hidden" value="false" />
		<div id="<portlet:namespace/>mailingListLink" style="display: ;">
			<a href="javascript: <portlet:namespace/>mailingListConfigure();"><liferay-ui:message key="configure" /> &raquo;</a>
		</div>
		<script type="text/javascript">
			<c:if test="<%= mailing != null%>">
			function <portlet:namespace/>changeActiveMailingStatus() {
				var active= !eval(jQuery("#<portlet:namespace />active").attr("value"));
				if (active) {
					jQuery("input[id^=<portlet:namespace />mail]").removeAttr("disabled");
					jQuery("#<portlet:namespace/>successMessage").hide();
					jQuery("#<portlet:namespace/>successMessage").text('<liferay-ui:message key="you-have-successfully-activated-mailing" />');
					jQuery("#<portlet:namespace/>successMessage").show("slow");
				} else {
					jQuery("input[id^=<portlet:namespace />mail]").attr("disabled", "disabled");
					jQuery("#<portlet:namespace/>successMessage").hide();
					jQuery("#<portlet:namespace/>successMessage").text('<liferay-ui:message key="you-have-successfully-deactivated-mailing" />');
					jQuery("#<portlet:namespace/>successMessage").show("slow");
				}
				var tmp= Liferay.Service.MB.MBMailing.updateActive({mailingId : <%=mailing.getMailingId()%>, active: active});
				alert(tmp);
			}
			</c:if>

			function <portlet:namespace/>changeMailOutConfigured() {
				var active= eval(jQuery("#<portlet:namespace />mailOutConfigured").attr("value"));
				<c:choose>
					<c:when test="<%= mailing != null%>">
					if (active) {
						jQuery("input[id^=<portlet:namespace />mailOut]").removeAttr("disabled");
						jQuery("#<portlet:namespace/>successMessage").hide();
						jQuery("#<portlet:namespace/>successMessage").text('<liferay-ui:message key="you-have-successfully-activated-a-configured-outgoing-server" />');
						jQuery("#<portlet:namespace/>successMessage").show("slow");
					} else {
						jQuery("input[id^=<portlet:namespace />mailOut][id!=<portlet:namespace/>mailOutConfiguredCheckbox][id!=<portlet:namespace/>mailOutConfigured]").attr("disabled", "disabled");
						jQuery("#<portlet:namespace/>successMessage").hide();
						jQuery("#<portlet:namespace/>successMessage").text('<liferay-ui:message key="you-have-successfully-deactivated-a-configured-outgoing-server" />');
						jQuery("#<portlet:namespace/>successMessage").show("slow");
					}
					Liferay.Service.MB.MBMailing.updateMailOutConfigured({mailingId : <%=mailing.getMailingId()%>, active: active});
					</c:when>
					<c:otherwise>
					if (active) {
						jQuery("input[id^=<portlet:namespace />mailOut]").removeAttr("disabled");
					} else {
						jQuery("input[id^=<portlet:namespace />mailOut][id!=<portlet:namespace/>mailOutConfiguredCheckbox][id!=<portlet:namespace/>mailOutConfigured]").attr("disabled", "disabled");
					}
					</c:otherwise>
				</c:choose>
			}

			function <portlet:namespace/>changeInPort() {
				var portNumber;
				if (eval(jQuery("#<portlet:namespace />mailInUseSSL").attr("value"))) {
					if (jQuery("#<portlet:namespace />mailInProtocolIMAP").attr("checked")) {
						portNumber= "993";
					}  else {
						portNumber= "995";
					}
				} else {
					if (jQuery("#<portlet:namespace />mailInProtocolIMAP").attr("checked")) {
						portNumber= "143";
					}  else {
						portNumber= "110";
					}
				}
				jQuery("#<portlet:namespace/>mailInServerPort").attr("value", portNumber);
			}

			function <portlet:namespace/>changeOutPort() {
				var portNumber;
				if (eval(jQuery("#<portlet:namespace />mailOutUseSSL").attr("value"))) {
					portNumber= "465";
				} else {
					portNumber= "25";
				}
				jQuery("#<portlet:namespace/>mailOutServerPort").attr("value", portNumber);
			}

			function <portlet:namespace/>mailingListConfigure() {
				jQuery("#<portlet:namespace/>mailingListTabs").show("slow");
				jQuery("#<portlet:namespace/>mailingListLink").hide();
				jQuery("<portlet:namespace/>mailingList").val(true);
			}

			<c:if test="<%= mailing == null %>">
				<c:if test='<%=Validator.isNull(ParamUtil.getString(renderRequest, "mailInServerPort"))%>'>
				jQuery("#<portlet:namespace/>mailInServerPort").attr("value", "110");
				</c:if>
				<c:if test='<%=Validator.isNull(ParamUtil.getString(renderRequest, "mailInReadInterval"))%>'>
				jQuery("#<portlet:namespace/>mailInReadInterval").attr("value", "5");
				</c:if>
				<c:if test='<%=Validator.isNull(ParamUtil.getString(renderRequest, "mailOutServerPort"))%>'>
				jQuery("#<portlet:namespace/>mailOutServerPort").attr("value", "25");
				</c:if>
			</c:if>
			if (!eval(jQuery("#<portlet:namespace/>mailOutConfigured").val())) {
				jQuery("input[id^=<portlet:namespace />mailOut][id!=<portlet:namespace/>mailOutConfigured][id!=<portlet:namespace/>mailOutConfiguredCheckbox]").attr("disabled", "disabled");
				if (eval(jQuery("#<portlet:namespace/>mailOutServerPort").attr("value")) == 0) {
					jQuery("#<portlet:namespace/>mailOutServerPort").attr("value", "25");
				}
			}
			if (eval(jQuery("#<portlet:namespace/>active").val())) {
				jQuery("input[id^=<portlet:namespace />mail]").attr("disabled", "disabled");
			}
			<c:if test="<%= SessionErrors.contains(renderRequest, MailingListAddressException.class.getName()) ||
				SessionErrors.contains(renderRequest, MailAddressException.class.getName()) ||
				SessionErrors.contains(renderRequest, MailInServerNameException.class.getName()) ||
				SessionErrors.contains(renderRequest, MailInUserNameException.class.getName()) %>">
				<portlet:namespace/>mailingListConfigure();
			</c:if>
			<c:if test="<%= SessionErrors.contains(renderRequest, MailOutServerNameException.class.getName()) ||
				SessionErrors.contains(renderRequest, MailOutUserNameException.class.getName()) %>">
				<portlet:namespace/>mailingListConfigure();
				Liferay.Portal.Tabs.show('<portlet:namespace/>inOutServer', ['<liferay-ui:message key="incoming" />','<liferay-ui:message key="outgoing" />'], '<liferay-ui:message key="outgoing" />');
			</c:if>
		</script>
		</td>
	</tr>
</c:if>

<c:if test="<%= category == null %>">
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
				modelName="<%= MBCategory.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

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

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</script>
</c:if>