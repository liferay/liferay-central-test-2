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
long classPK =  ParamUtil.getLong(request, "classPK");
long userId = ParamUtil.getLong(request, "userId");
String title = ParamUtil.getString(request, "title");
String contentURL = ParamUtil.getString(request, "contentURL");
%>

<style type="text/css">
	.portlet-flags .uni-form fieldset {
		border: none;
		padding: 0;
		width: 100%;
	}

	.portlet-flags .uni-form .block-labels label {
		font-weight: bold;
	}
</style>

<div class="portlet-flags" id="<portlet:namespace />flagPopup">
	<form class="uni-form" method="post" name="<portlet:namespace />flagForm">
		<p><%= LanguageUtil.format(pageContext, "you-are-about-to-report-a-violation-of-our-x-terms-of-use-all-reports-are-strictly-confidential", themeDisplay.getPathMain() + "/portal/terms_of_use") %></p>

		<fieldset class="block-labels">
			<div class="ctrl-holder">
				<label for="<portlet:namespace />reason"><liferay-ui:message key="reason-for-the-report" /></label>

				<select name="<portlet:namespace />reason" id="<portlet:namespace />reason">
					<option value=""></option>

					<%
					String[] reasons = PropsValues.FLAGS_REASONS;

					for (String reason : reasons) {
					%>

						<option value="<%= reason %>"><liferay-ui:message key="<%= reason %>" /></option>

					<%
					}
					%>

					<option value="other"><liferay-ui:message key="other" /></option>
				</select>
			</div>

			<div class="ctrl-holder" id="<portlet:namespace />otherReasonDiv">
				<label for="<portlet:namespace />otherReason"><liferay-ui:message key="please-specify-the-reason" /></label>

				<input id="<portlet:namespace />otherReason" name="<portlet:namespace />otherReason" type="text" value="" />
			</div>

			<c:if test="<%= !themeDisplay.isSignedIn() %>">
				<div class="ctrl-holder">
					<label for="<portlet:namespace />emailAddress"><liferay-ui:message key="email-address" /></label>

					<input id="<portlet:namespace />emailAddress" name="<portlet:namespace />emailAddress" type="text" value="" />
				</div>
			</c:if>

			<div class="button-holder">
				<input type="button" value="<liferay-ui:message key="send" />" onclick="<portlet:namespace />flag(); return false;" />

				<input type="button" value="<liferay-ui:message key="cancel" />" onclick="Expanse.Popup.close(this)" />
			</div>
		</fieldset>
	</form>
</div>

<div id="<portlet:namespace />confirmation" style="display:none">
	<p><strong><liferay-ui:message key="thank-you-for-your-report" /></strong></p>

	<p><%= LanguageUtil.format(pageContext, "although-we-cannot-disclose-our-final-decision-we-do-review-every-report-and-appreciate-your-effort-to-make-sure-x-is-a-safe-environment-for-everyone", company.getName()) %></p>

	<div class="button-holder">
		<input type="button" value="<liferay-ui:message key="close" />" onclick="Expanse.Popup.close(this)" />
	</div>
</div>

<script type="text/javascript">
	jQuery(
		function(){
			Liferay.Util.toggleSelectBox('<portlet:namespace />reason', 'other', '<portlet:namespace />otherReasonDiv');
		}
	);

	function  <portlet:namespace />flag() {
		var reason = jQuery('#<portlet:namespace />reason').val();

		if (reason == 'other') {
			reason = jQuery('#<portlet:namespace />otherReason').val() || '<liferay-ui:message key="no-reason-specified" />';
		}

		var emailAddress = jQuery('#<portlet:namespace />emailAddress').val() || "";

		jQuery('#<portlet:namespace />flagPopup').html('<div class="loading-animation" />');

		jQuery.ajax(
				{
					url:  '<liferay-portlet:actionURL portletName="<%= PortletKeys.FLAGS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="struts_action" value="/flags/edit_flags_entry" /></liferay-portlet:actionURL>',
					data: {
						className: '<%= HtmlUtil.escape(className) %>',
						classPK: '<%= classPK %>',
						userId: '<%= userId %>',
						title: '<%= HtmlUtil.escape(title) %>',
						contentURL: '<%= HtmlUtil.escape(contentURL) %>',
						reason: reason,
						emailAddress: emailAddress
					},
					success: function() {
						var confirmationMessage = jQuery('#<portlet:namespace />confirmation');
						jQuery('#<portlet:namespace />flagPopup').html(confirmationMessage.html());
					}
				}
			);
	}
</script>