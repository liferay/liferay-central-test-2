<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.util.SessionClicks" %>
<%@ page import="com.liferay.portlet.PortletURLFactoryUtil" %>
<%@ page import="com.liferay.product.navigation.product.menu.web.constants.ProductNavigationProductMenuPortletKeys" %>

<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.RenderRequest" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
String productMenuState = SessionClicks.get(request, "com.liferay.control.menu.web_productMenuState", "closed");

PortletURL portletURL = PortletURLFactoryUtil.create(request, ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU, plid, RenderRequest.RENDER_PHASE);

portletURL.setParameter("mvcPath", "/portlet/product_menu.jsp");
portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
%>

<div class="toolbar-group-content <%= Validator.equals(productMenuState, "open") ? "active" : StringPool.BLANK %>">
	<a class="control-menu-icon product-menu-toggle sidenav-toggler" data-content="body" data-qa-id="productMenu" data-target="#sidenavSliderId,#wrapper" data-title="<%= HtmlUtil.escape(LanguageUtil.get(request, "menu")) %>" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" <%= Validator.equals(productMenuState, "open") ? StringPool.BLANK : "data-url='" + portletURL.toString() + "'" %> href="javascript:;" id="sidenavToggleId" onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "menu")) %>')">
		<div class="toast-animation">
			<div class="pm"></div>

			<div class="cn"></div>
		</div>
	</a>
</div>