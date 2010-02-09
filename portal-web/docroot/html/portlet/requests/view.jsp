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

<%@ include file="/html/portlet/requests/init.jsp" %>

<%
List<SocialRequest> requests = (List<SocialRequest>)request.getAttribute(WebKeys.SOCIAL_REQUESTS);
%>

<c:if test="<%= requests != null %>">

	<%
	PortletURL portletURL = renderResponse.createActionURL();

	portletURL.setParameter("struts_action", "/requests/update_request");
	portletURL.setParameter("redirect", currentURL);
	%>

	<table class="lfr-table" width="100%">

	<%
	for (int i = 0; i < requests.size(); i++) {
		SocialRequest socialRequest = requests.get(i);

		SocialRequestFeedEntry requestFeedEntry = SocialRequestInterpreterLocalServiceUtil.interpret(socialRequest, themeDisplay);
	%>

		<tr>
			<td align="center" class="lfr-top">
				<liferay-ui:user-display
					userId="<%= socialRequest.getUserId() %>"
					displayStyle="<%= 2 %>"
				/>
			</td>
			<td class="lfr-top" width="99%">
				<c:choose>
					<c:when test="<%= requestFeedEntry == null %>">
						<div class="portlet-msg-error">
							<liferay-ui:message key="request-cannot-be-interpreted-because-it-does-not-have-an-associated-interpreter" />
						</div>
					</c:when>
					<c:otherwise>

						<%
						portletURL.setParameter("requestId", String.valueOf(socialRequest.getRequestId()));
						%>

						<div>
							<%= requestFeedEntry.getTitle() %>
						</div>

						<br />

						<c:if test="<%= Validator.isNotNull(requestFeedEntry.getBody()) %>">
							<div>
								<%= requestFeedEntry.getBody() %>
							</div>

							<br />
						</c:if>

						<liferay-ui:icon-list>

							<%
							portletURL.setParameter("status", String.valueOf(SocialRequestConstants.STATUS_CONFIRM));
							%>

							<liferay-ui:icon
								image="activate"
								message="confirm"
								url="<%= portletURL.toString() %>"
							/>

							<%
							portletURL.setParameter("status", String.valueOf(SocialRequestConstants.STATUS_IGNORE));
							%>

							<liferay-ui:icon
								image="deactivate"
								message="ignore"
								url="<%= portletURL.toString() %>"
							/>
						</liferay-ui:icon-list>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>

		<c:if test="<%= (i + 1) < requests.size() %>">
			<tr>
				<td colspan="2">
					<div class="separator"><!-- --></div>
				</td>
			</tr>
		</c:if>

	<%
	}
	%>

	</table>
</c:if>