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

<%@ include file="/html/taglib/init.jsp" %>

<%
Map<String, PortletURL> addPortletURLs = (Map<String, PortletURL>)request.getAttribute("liferay-ui:asset-add-button:addPortletURLs");
boolean defaultAssetPublisher = GetterUtil.getBoolean(request.getAttribute("liferay-ui:asset-add-button:defaultAssetPublisher"));
long groupCount = GetterUtil.getLong(request.getAttribute("liferay-ui:asset-add-button:groupCount"));
long groupId = GetterUtil.getLong(request.getAttribute("liferay-ui:asset-add-button:groupId"));
%>

<c:if test="<%= (addPortletURLs != null) && !addPortletURLs.isEmpty() %>">
	<aui:nav cssClass="navbar-nav">
		<c:choose>
			<c:when test="<%= addPortletURLs.size() == 1 %>">

				<%
				Set<Map.Entry<String, PortletURL>> addPortletURLsSet = addPortletURLs.entrySet();

				Iterator<Map.Entry<String, PortletURL>> iterator = addPortletURLsSet.iterator();

				Map.Entry<String, PortletURL> entry = iterator.next();

				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(_getClassName(entry.getKey()));

				String message = _getMessage(entry.getKey(), addPortletURLs, locale);
				%>

				<aui:nav-item
					href="<%= _getURL(groupId, plid, entry.getValue(), assetRendererFactory.getPortletId(), message, defaultAssetPublisher, layout, pageContext, portletResponse) %>"
					iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
					iconSrc="<%= assetRendererFactory.getIconPath(portletRequest) %>"
					label='<%= LanguageUtil.format(request, (groupCount == 1) ? "add-x" : "add-x-in-x", new Object [] {HtmlUtil.escape(message), HtmlUtil.escape((GroupLocalServiceUtil.getGroup(groupId)).getDescriptiveName(locale))}, false) %>'
				/>
			</c:when>
			<c:otherwise>
				<aui:nav-item
					dropdown="<%= true %>"
					iconCssClass="icon-plus"
					label='<%= LanguageUtil.format(request, (groupCount == 1) ? "add-new" : "add-new-in-x", HtmlUtil.escape((GroupLocalServiceUtil.getGroup(groupId)).getDescriptiveName(locale)), false) %>'
				>

					<%
					for (Map.Entry<String, PortletURL> entry : addPortletURLs.entrySet()) {
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(_getClassName(entry.getKey()));

						String message = _getMessage(entry.getKey(), addPortletURLs, locale);
					%>

						<aui:nav-item
							href="<%= _getURL(groupId, plid, entry.getValue(), assetRendererFactory.getPortletId(), message, defaultAssetPublisher, layout, pageContext, portletResponse) %>"
							iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
							iconSrc="<%= assetRendererFactory.getIconPath(portletRequest) %>"
							label="<%= HtmlUtil.escape(message) %>"
						/>

					<%
					}
					%>

				</aui:nav-item>
			</c:otherwise>
		</c:choose>
</aui:nav>
</c:if>

<%!
private String _getClassName(String className) {
	int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

	if (pos != -1) {
		className = className.substring(0, pos);
	}

	return className;
}

private String _getMessage(String className, Map<String, PortletURL> addPortletURLs, Locale locale) {
	String message = null;

	int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

	if (pos != -1) {
		message = className.substring(pos + AssetUtil.CLASSNAME_SEPARATOR.length());

		className = className.substring(0, pos);
	}

	AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

	if (pos == -1) {
		message = assetRendererFactory.getTypeName(locale);
	}

	return message;
}

private String _getURL(long groupId, long plid, PortletURL addPortletURL, String portletId, String message, boolean defaultAssetPublisher, Layout layout, PageContext pageContext, PortletResponse portletResponse) {
	addPortletURL.setParameter("groupId", String.valueOf(groupId));
	addPortletURL.setParameter("showHeader", Boolean.FALSE.toString());

	String addPortletURLString = addPortletURL.toString();

	addPortletURLString = HttpUtil.addParameter(addPortletURLString, "doAsGroupId", groupId);
	addPortletURLString = HttpUtil.addParameter(addPortletURLString, "refererPlid", plid);

	String namespace = PortalUtil.getPortletNamespace(portletId);

	if (defaultAssetPublisher) {
		addPortletURLString = HttpUtil.addParameter(addPortletURLString, namespace + "layoutUuid", layout.getUuid());
	}

	return "javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: true}, id: '" + portletResponse.getNamespace() + "editAsset', title: '" + HtmlUtil.escapeJS(LanguageUtil.format((HttpServletRequest)pageContext.getRequest(), "new-x", HtmlUtil.escape(message), false)) + "', uri: '" + HtmlUtil.escapeJS(addPortletURLString) + "'});";
}
%>