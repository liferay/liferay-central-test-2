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
String className = (String)request.getAttribute("liferay-ui:flags:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:flags:classPK"));
long userId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:flags:userId"));
String title = GetterUtil.getString((String)request.getAttribute("liferay-ui:flags:title"));
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:flags:message"), "flag");
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:flags:label"), true);
String randomClass = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
String contentType = LanguageUtil.get(pageContext, "model.resource." + className);
%>

<div class="taglib-flags">
	<liferay-ui:icon image="../ratings/flagged_icon" imageHover="../ratings/flagged_icon_hover" message="<%= message %>" cssClass="<%= randomClass  %>" label="<%= label %>" url="javascript: ;" />
</div>

<c:choose>
	<c:when test="<%= PropsValues.FLAGS_GUEST_USERS || themeDisplay.isSignedIn() %>">
		<script type="text/javascript">
			jQuery('.<%= randomClass %>').click(
				function() {
					var popup = new Expanse.Popup(
						{
							header: '<%= LanguageUtil.format(pageContext, "report-this-x-as-inappropriate", contentType) %>',
							constraintoviewport: true,
							position:[150,150],
							modal:true,
							width:500,
							xy: ['center', 100],
							url:  '<liferay-portlet:renderURL portletName="<%= PortletKeys.FLAGS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="struts_action" value="/flags/edit_flags_entry" /></liferay-portlet:renderURL> ',
							urlData: {
								className: '<%= className %>',
								classPK: '<%= classPK %>',
								userId: '<%= userId %>',
								title: '<%= title %>',
								contentURL: '<%= PortalUtil.getPortalURL(request) + currentURL %>'
							}
						}
					);

					return false;
				}
			);
		</script>
	</c:when>
	<c:otherwise>
		<div id="<portlet:namespace />signIn" style="display:none">
			<p><%= LanguageUtil.format(pageContext, "please-sign-in-to-be-able-to-flag-this-x-as-inappropriate", contentType) %></p>

			<div class="button-holder">
				<input type="button" value="<liferay-ui:message key="close" />" onclick="Expanse.Popup.close(this)" />
			</div>
		</div>

		<script type="text/javascript">
			jQuery('.<%= randomClass %>').click(
				function(){
					var popup = new Expanse.Popup(
						{
							header: '<%= LanguageUtil.format(pageContext, "report-this-x-as-inappropriate", contentType) %>',
							constraintoviewport: true,
							position:[150,150],
							modal:true,
							width:500,
							xy: ['center', 100],
							body: jQuery('#<portlet:namespace />signIn').html()
						}
					);

					return false;
				}
			);
		</script>
	</c:otherwise>
</c:choose>