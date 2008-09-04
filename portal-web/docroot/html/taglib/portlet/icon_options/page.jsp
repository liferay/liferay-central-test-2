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

<%@ include file="/html/taglib/init.jsp" %>

<liferay-ui:icon-menu
	message="options"
	showWhenSingleIcon="<%= true %>"
	align="auto"
	cssClass="portlet-options"
>
	<liferay-portlet:icon-refresh />

	<liferay-portlet:icon-portlet-css />

	<liferay-portlet:icon-configuration />

	<liferay-portlet:icon-edit />

	<liferay-portlet:icon-edit-defaults />

	<liferay-portlet:icon-edit-guest />

	<liferay-portlet:icon-help />

	<liferay-portlet:icon-print />

	<%
	Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);
	%>

	<c:if test="<%= portlet != null %>">

		<%
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portletDisplay.getId());

		boolean widgetShowAddAppLink = GetterUtil.getBoolean(portletSetup.getValue("lfr-widget-show-add-app-link", null), true);

		String shareppid = portletDisplay.getId();
		String sharepptitle = portletDisplay.getTitle();

		String facebookAPIKey = portletSetup.getValue("lfr-facebook-api-key", StringPool.BLANK);
		String facebookCanvasPageURL = portletSetup.getValue("lfr-facebook-canvas-page-url", StringPool.BLANK);
		boolean facebookShowAddAppLink = GetterUtil.getBoolean(portletSetup.getValue("lfr-facebook-show-add-app-link", null), true);

		if (Validator.isNull(facebookCanvasPageURL) || Validator.isNull(facebookAPIKey)) {
			facebookShowAddAppLink = false;
		}

		boolean appShowShareWithFriendsLink = GetterUtil.getBoolean(portletSetup.getValue("lfr-app-show-share-with-friends-link", StringPool.BLANK));
		%>

		<c:if test="<%= widgetShowAddAppLink || facebookShowAddAppLink || appShowShareWithFriendsLink %>">
			<c:if test="<%= widgetShowAddAppLink %>">
				<liferay-ui:icon
					image="../dock/add_content"
					message="add-to-any-website"
					url="javascript: ;"
					label="<%= true %>"
					cssClass='<%= portletDisplay.getNamespace() + "expose-as-widget" %>'
				/>
			</c:if>

			<c:if test="<%= facebookShowAddAppLink %>">
				<liferay-ui:icon
					image="../social_bookmarks/facebook"
					message="add-to-facebook"
					url='<%= "http://www.facebook.com/add.php?api_key=" + facebookAPIKey + "&ref=pd" %>'
					method="get"
					label="<%= true %>"
				/>
			</c:if>

			<c:if test="<%= appShowShareWithFriendsLink %>">
				<liferay-ui:icon
					image="share"
					message="share-this-application-with-friends"
					url="javascript: ;"
					method="get"
					label="<%= true %>"
					cssClass='<%= portletDisplay.getNamespace() + "share-with-friends" %>'
				/>
			</c:if>

			<c:if test="<%= widgetShowAddAppLink %>">
				<div class="lfr-widget-information" id="<portlet:namespace />widgetInformation">
					<p>
						<liferay-ui:message key="share-this-application-on-any-website" />
					</p>

					<textarea class="lfr-textarea">&lt;script src=&quot;<%= themeDisplay.getPortalURL() %><%= themeDisplay.getPathContext() %>/html/js/liferay/widget.js&quot; type=&quot;text/javascript&quot;&gt;&lt;/script&gt;
&lt;script type=&quot;text/javascript&quot;&gt;
Liferay.Widget({ url: &#x27;<%= PortalUtil.getWidgetURL(portlet, themeDisplay) %>&#x27;});
&lt;/script&gt;</textarea>
				</div>

				<script type="text/javascript">
					jQuery(
						function() {
							jQuery('.<portlet:namespace />expose-as-widget a').click(
								function(event) {
									var message = jQuery('#<portlet:namespace />widgetInformation').clone();

									var textarea = message.find('textarea');

									textarea.focus(
										function(event) {
											Liferay.Util.selectAndCopy(this);
										}
									);

									Liferay.Popup(
										{
											width: 550,
											modal: true,
											title: '<liferay-ui:message key="add-to-any-website" />',
											message: message[0]
										}
									);

									message.show();
								}
							);
							jQuery('.<portlet:namespace />share-with-friends a').click(
								function(event) {
									var share_dialog = Liferay.Popup(
										{
											width: 550,
											modal: true,
											title: '<liferay-ui:message key="request-share-widget" />',
											message: '<div class="loading-animation" />'
										}
										);

										jQuery.ajax(
											{
												url: themeDisplay.getPathMain() + '/portal/render_portlet',
												data: {
													p_l_id: themeDisplay.getPlid(),
													p_p_id: '124',
													p_p_state: 'exclusive',
													doAsUserId:themeDisplay.getDoAsUserIdEncoded()
												},
												success: function(message) {
													ss = '<script>var ppidForShare="<%=shareppid%>";var pptitleForShare="<%=sharepptitle%>"; <\/script>';
													share_dialog.html(message + ss);
												}
											}
										);
								}
							);
						}
					);
				</script>
			</c:if>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>