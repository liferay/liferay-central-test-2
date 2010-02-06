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

	<liferay-portlet:icon-export-import />

	<liferay-portlet:icon-help />

	<liferay-portlet:icon-print />

	<%
	Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);
	%>

	<c:if test="<%= portlet != null %>">

		<%
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portletDisplay.getId());

		boolean widgetShowAddAppLink = GetterUtil.getBoolean(portletSetup.getValue("lfr-widget-show-add-app-link", null), PropsValues.THEME_PORTLET_SHARING_DEFAULT);

		String facebookAPIKey = portletSetup.getValue("lfr-facebook-api-key", StringPool.BLANK);
		String facebookCanvasPageURL = portletSetup.getValue("lfr-facebook-canvas-page-url", StringPool.BLANK);
		boolean facebookShowAddAppLink = GetterUtil.getBoolean(portletSetup.getValue("lfr-facebook-show-add-app-link", null), true);

		if (Validator.isNull(facebookCanvasPageURL) || Validator.isNull(facebookAPIKey)) {
			facebookShowAddAppLink = false;
		}

		boolean iGoogleShowAddAppLink = GetterUtil.getBoolean(portletSetup.getValue("lfr-igoogle-show-add-app-link", StringPool.BLANK));
		boolean netvibesShowAddAppLinks = GetterUtil.getBoolean(portletSetup.getValue("lfr-netvibes-show-add-app-link", StringPool.BLANK));
		boolean appShowShareWithFriendsLink = GetterUtil.getBoolean(portletSetup.getValue("lfr-app-show-share-with-friends-link", StringPool.BLANK));
		%>

		<c:if test="<%= widgetShowAddAppLink || facebookShowAddAppLink || iGoogleShowAddAppLink || netvibesShowAddAppLinks || appShowShareWithFriendsLink %>">
			<c:if test="<%= widgetShowAddAppLink %>">

				<%
				String widgetHREF = "javascript:Liferay.PortletSharing.showWidgetInfo('" + PortalUtil.getWidgetURL(portlet, themeDisplay) + "');";
				%>

				<liferay-ui:icon
					image="../dock/add_content"
					message="add-to-any-website"
					url="<%= widgetHREF %>"
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

			<c:if test="<%= iGoogleShowAddAppLink %>">

				<%
				String googleGadgetHREF = "http://fusion.google.com/add?source=atgs&moduleurl=" + PortalUtil.getGoogleGadgetURL(portlet, themeDisplay);
				%>

				<liferay-ui:icon
					image="../dock/add_content"
					message="add-to-igoogle"
					url="<%= googleGadgetHREF %>"
					label="<%= true %>"
					cssClass='<%= portletDisplay.getNamespace() + "expose-as-widget" %>'
				/>
			</c:if>

			<c:if test="<%= netvibesShowAddAppLinks %>">

				<%
				String netvibesHREF = "javascript:Liferay.PortletSharing.showNetvibesInfo('" + PortalUtil.getNetvibesURL(portlet, themeDisplay) + "');";
				%>

				<liferay-ui:icon
					image="../dock/add_content"
					message="add-to-netvibes"
					url="<%= netvibesHREF %>"
					method="get"
					label="<%= true %>"
				/>
			</c:if>

			<c:if test="<%= appShowShareWithFriendsLink %>">
				<liferay-ui:icon
					image="share"
					message="share-this-application-with-friends"
					url="javascript:;"
					method="get"
					label="<%= true %>"
				/>
			</c:if>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>