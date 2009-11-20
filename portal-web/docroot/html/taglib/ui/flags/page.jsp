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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:flags:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:flags:classPK"));
String contentTitle = GetterUtil.getString((String)request.getAttribute("liferay-ui:flags:contentTitle"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:flags:label"), true);
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:flags:message"), "flag[action]");
long reportedUserId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:flags:reportedUserId"));
%>

<div class="taglib-flags">
	<liferay-ui:icon
		image="../ratings/flagged_icon"
		imageHover="../ratings/flagged_icon_hover"
		message="<%= message %>"
		url="javascript:;"
		label="<%= label %>"
		cssClass="<%= randomNamespace %>"
	/>
</div>

<c:choose>
	<c:when test="<%= PropsValues.FLAGS_GUEST_USERS_ENABLED || themeDisplay.isSignedIn() %>">
		<script type="text/javascript">
			AUI().use(
				'dialog',
				function(A) {
					var params = A.toQueryString({
						className: '<%= className %>',
						classPK: '<%= classPK %>',
						contentTitle: '<%= HtmlUtil.escape(contentTitle) %>',
						contentURL: '<%= PortalUtil.getPortalURL(request) + currentURL %>',
						reportedUserId: '<%= reportedUserId %>'
					});

					A.on('click', function() {
						var popup = new A.Dialog({
							centered: true,
							destroyOnClose: true,
							draggable: true,
							title: '<liferay-ui:message key="report-inappropriate-content" />',
							modal: true,
							width: 435,
							stack: true,
							io:	{
								uri: '<liferay-portlet:renderURL portletName="<%= PortletKeys.FLAGS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="struts_action" value="/flags/edit_entry" /></liferay-portlet:renderURL>',
								cfg: {
									data: params
								}
							}
						})
						.render();
					},
					'.<%= randomNamespace %>');
				}
			);
		</script>
	</c:when>
	<c:otherwise>
		<div id="<portlet:namespace />signIn" style="display:none">
			<liferay-ui:message key="please-sign-in-to-flag-this-as-inappropriate" />
		</div>

		<script type="text/javascript">
				AUI().use('dialog', function(A) {
					A.on('click', function(event) {
						var popup = new A.Dialog(
							{
								bodyContent: A.get('#<portlet:namespace />signIn').html(),
								centered: true,
								destroyOnClose: true,
								title: '<liferay-ui:message key="report-inappropriate-content" />',
								modal: true,
								width: 500
							}
						)
						.render();

						event.preventDefault();
					},
					'.<%= randomNamespace %>');
				});
		</script>
	</c:otherwise>
</c:choose>