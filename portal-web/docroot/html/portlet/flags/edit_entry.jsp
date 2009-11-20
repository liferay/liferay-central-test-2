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

<script type="text/javascript">
	AUI().ready(
		function() {
			Liferay.Util.toggleSelectBox('<portlet:namespace />reason', 'other', '<portlet:namespace />otherReasonDiv');
		}
	);

	function <portlet:namespace />flag() {
		var reason = jQuery('#<portlet:namespace />reason').val();

		if (reason == 'other') {
			reason = jQuery('#<portlet:namespace />otherReason').val() || '<liferay-ui:message key="no-reason-specified" />';
		}

		var reporterEmailAddress = jQuery('#<portlet:namespace />reporterEmailAddress').val() || "";

		jQuery('#<portlet:namespace />flagsPopup').html('<div class="loading-animation" />');

		jQuery.ajax(
				{
					url: '<liferay-portlet:actionURL portletName="<%= PortletKeys.FLAGS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="struts_action" value="/flags/edit_entry" /></liferay-portlet:actionURL>',
					data: {
						className: '<%= HtmlUtil.escape(className) %>',
						classPK: '<%= classPK %>',
						reporterEmailAddress: reporterEmailAddress,
						reportedUserId: '<%= reportedUserId %>',
						contentTitle: '<%= HtmlUtil.escape(contentTitle) %>',
						contentURL: '<%= HtmlUtil.escape(contentURL) %>',
						reason: reason
					},
					success: function() {
						var confirmationMessage = jQuery('#<portlet:namespace />confirmation');

						jQuery('#<portlet:namespace />flagsPopup').html(confirmationMessage.html());
					},
					error: function() {
						var errorMessage = jQuery('#<portlet:namespace />error');

						jQuery('#<portlet:namespace />flagsPopup').html(errorMessage.html());
					}
				}
			);
	}
</script>

<div class="portlet-flags" id="<portlet:namespace />flagsPopup">
	<form class="aui-form" method="post" name="<portlet:namespace />flagsForm">
		<p>
			<%= LanguageUtil.format(pageContext, "you-are-about-to-report-a-violation-of-our-x-terms-of-use.-all-reports-are-strictly-confidential", themeDisplay.getPathMain() + "/portal/terms_of_use") %>
		</p>

		<fieldset class="aui-block-labels">
			<div class="aui-ctrl-holder">
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
			</div>

			<div class="aui-ctrl-holder" id="<portlet:namespace />otherReasonDiv">
				<label for="<portlet:namespace />otherReason"><liferay-ui:message key="other-reason" /></label>

				<input id="<portlet:namespace />otherReason" name="<portlet:namespace />otherReason" type="text" value="" />
			</div>

			<c:if test="<%= !themeDisplay.isSignedIn() %>">
				<div class="aui-ctrl-holder">
					<label for="<portlet:namespace />reporterEmailAddress"><liferay-ui:message key="email-address" /></label>

					<input id="<portlet:namespace />reporterEmailAddress" name="<portlet:namespace />reporterEmailAddress" type="text" value="" />
				</div>
			</c:if>

			<div class="aui-button-holder">
				<input type="button" value="<liferay-ui:message key="send" />" onclick="<portlet:namespace />flag(); return false;" />
			</div>
		</fieldset>
	</form>
</div>

<div id="<portlet:namespace />confirmation" style="display: none;">
	<p><strong><liferay-ui:message key="thank-you-for-your-report" /></strong></p>

	<p><%= LanguageUtil.format(pageContext, "although-we-cannot-disclose-our-final-decision,-we-do-review-every-report-and-appreciate-your-effort-to-make-sure-x-is-a-safe-environment-for-everyone", company.getName()) %></p>
</div>

<div id="<portlet:namespace />error" style="display: none;">
	<p><strong><liferay-ui:message key="an-error-occurred-while-sending-the-report.-please-try-again-in-a-few-minutes" /></strong></p>
</div>