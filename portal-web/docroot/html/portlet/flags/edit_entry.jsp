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

<%@ include file="/html/portlet/init.jsp" %>

<%
String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");
String contentTitle = ParamUtil.getString(request, "contentTitle");
String contentURL = ParamUtil.getString(request, "contentURL");
long reportedUserId = ParamUtil.getLong(request, "reportedUserId");
%>

<style type="text/css">
	.portlet-flags .aui-form fieldset {
		border: none;
		padding: 0;
		width: 100%;
	}

	.portlet-flags .aui-form .aui-block-labels label {
		font-weight: bold;
	}
</style>

<div class="portlet-flags" id="<portlet:namespace />flagsPopup">
	<form class="aui-form" method="post" name="<portlet:namespace />flagsForm">
		<p>
			<%= LanguageUtil.format(pageContext, "you-are-about-to-report-a-violation-of-our-x-terms-of-use.-all-reports-are-strictly-confidential", themeDisplay.getPathMain() + "/portal/terms_of_use") %>
		</p>

		<fieldset class="aui-block-labels">
			<span class="aui-field">
				<span class="aui-field-content">
					<label for="<portlet:namespace />reason"><liferay-ui:message key="reason-for-the-report" /></label>

					<select name="<portlet:namespace />reason" id="<portlet:namespace />reason">
						<option value=""></option>

						<%
						for (String reason : PropsValues.FLAGS_REASONS) {
						%>

							<option value="<%= reason %>"><liferay-ui:message key="<%= reason %>" /></option>

						<%
						}
						%>

						<option value="other"><liferay-ui:message key="other" /></option>
					</select>
				</span>
			</span>

			<span class="aui-field" id="<portlet:namespace />otherReasonContainer">
				<span class="aui-field-content">
					<label for="<portlet:namespace />otherReason"><liferay-ui:message key="other-reason" /></label>

					<input id="<portlet:namespace />otherReason" name="<portlet:namespace />otherReason" type="text" value="" />
				</span>
			</span>

			<c:if test="<%= !themeDisplay.isSignedIn() %>">
				<span class="aui-field">
					<span class="aui-field-content">
						<label for="<portlet:namespace />reporterEmailAddress"><liferay-ui:message key="email-address" /></label>

						<input id="<portlet:namespace />reporterEmailAddress" name="<portlet:namespace />reporterEmailAddress" type="text" value="" />
					</span>
				</span>
			</c:if>

			<div class="aui-button-holder">
				<input type="button" value="<liferay-ui:message key="send" />" onclick="<portlet:namespace />flag(); return false;" />
			</div>
		</fieldset>
	</form>
</div>

<div class="aui-helper-hidden" id="<portlet:namespace />confirmation">
	<p><strong><liferay-ui:message key="thank-you-for-your-report" /></strong></p>

	<p><%= LanguageUtil.format(pageContext, "although-we-cannot-disclose-our-final-decision,-we-do-review-every-report-and-appreciate-your-effort-to-make-sure-x-is-a-safe-environment-for-everyone", company.getName()) %></p>
</div>

<div class="aui-helper-hidden" id="<portlet:namespace />error">
	<p><strong><liferay-ui:message key="an-error-occurred-while-sending-the-report.-please-try-again-in-a-few-minutes" /></strong></p>
</div>

<aui:script>
	function <portlet:namespace />flag() {
		AUI().use(
			'dialog',
			function(A) {
				var reasonNode = A.one('#<portlet:namespace />reason');
				var reason = (reasonNode && reasonNode.val()) || '';

				if (reason == 'other') {
					var otherReasonNode = A.one('#<portlet:namespace />otherReason');

					reason = (otherReasonNode && otherReasonNode.val()) || '<liferay-ui:message key="no-reason-specified" />';
				}

				var reporterEmailAddressNode = A.one('#<portlet:namespace />reporterEmailAddress');
				var reporterEmailAddress = (reporterEmailAddressNode && reporterEmailAddressNode.val()) || '';

				var flagsPopupNode = A.one('#<portlet:namespace />flagsPopup');
				var errorMessageNode = A.one('#<portlet:namespace />error');
				var confirmationMessageNode = A.one('#<portlet:namespace />confirmation');

				var errorMessage = (errorMessageNode && errorMessageNode.html()) || '';
				var confirmationMessage = (confirmationMessageNode && confirmationMessageNode.html()) || '';

				if (flagsPopupNode) {
					A.DialogManager.refreshByChild(
						flagsPopupNode,
						{
							cfg: {
								data: A.toQueryString(
									{
										className: '<%= HtmlUtil.escape(className) %>',
										classPK: '<%= classPK %>',
										contentTitle: '<%= HtmlUtil.escape(contentTitle) %>',
										contentURL: '<%= HtmlUtil.escape(contentURL) %>',
										reason: reason,
										reportedUserId: '<%= reportedUserId %>',
										reporterEmailAddress: reporterEmailAddress
									}
								),
								on: {
									failure: function() {
										this.get('host').setStdModContent('body', errorMessage);
									},
									success: function() {
										this.get('host').setStdModContent('body', confirmationMessage);
									}
								}
							},
							uri: '<liferay-portlet:actionURL portletName="<%= PortletKeys.FLAGS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="struts_action" value="/flags/edit_entry" /></liferay-portlet:actionURL>'
						}
					);
				}
			}
		);
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />reason', 'other', '<portlet:namespace />otherReasonContainer');
</aui:script>