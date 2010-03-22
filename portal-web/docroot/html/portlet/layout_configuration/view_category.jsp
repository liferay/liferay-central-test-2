<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/layout_configuration/init.jsp" %>

<%
PortletCategory portletCategory = (PortletCategory)request.getAttribute(WebKeys.PORTLET_CATEGORY);

int portletCategoryIndex = GetterUtil.getInteger((String)request.getAttribute("portletCategoryIndex"), 0);

String oldCategoryPath = (String)request.getAttribute(WebKeys.PORTLET_CATEGORY_PATH);

String newCategoryPath = LanguageUtil.get(pageContext, portletCategory.getName());

Pattern pattern = Pattern.compile(".*");

Matcher matcher = pattern.matcher(newCategoryPath);

StringBundler divId = new StringBundler();

while (matcher.find()) {
	divId.append(matcher.group());
}

newCategoryPath = divId.toString();

if (Validator.isNotNull(oldCategoryPath)) {
	newCategoryPath = oldCategoryPath + ":" + newCategoryPath;
}

List categories = ListUtil.fromCollection(portletCategory.getCategories());

categories = ListUtil.sort(categories, new PortletCategoryComparator(locale));

List portlets = new ArrayList();

Iterator itr = portletCategory.getPortletIds().iterator();

String externalPortletCategory = null;

while (itr.hasNext()) {
	String portletId = (String)itr.next();

	Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

	if (portlet != null) {
		portlets.add(portlet);

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile() && Validator.isNull(externalPortletCategory)) {
			PortletConfig curPortletConfig = PortletConfigFactory.create(portlet, application);

			ResourceBundle resourceBundle = curPortletConfig.getResourceBundle(locale);

			try {
				externalPortletCategory = resourceBundle.getString(portletCategory.getName());
			}
			catch (MissingResourceException mre) {
			}
		}
	}
}

portlets = ListUtil.sort(portlets, new PortletTitleComparator(application, locale));

if (!categories.isEmpty() || !portlets.isEmpty()) {
%>

	<div class="lfr-add-content <%= (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) ? "collapsed" : "" %>" id="layoutConfigurationEntry<%= portletCategoryIndex %>">
		<h2>
			<span><%= Validator.isNotNull(externalPortletCategory) ? externalPortletCategory : LanguageUtil.get(pageContext, portletCategory.getName()) %></span>
		</h2>

		<div class="lfr-content-category <%= (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) ? "aui-helper-hidden" : "" %>">

			<%
			itr = categories.iterator();

			while (itr.hasNext()) {
				request.setAttribute(WebKeys.PORTLET_CATEGORY, itr.next());
				request.setAttribute(WebKeys.PORTLET_CATEGORY_PATH, newCategoryPath);

				request.setAttribute("portletCategoryIndex", portletCategoryIndex);
			%>

				<liferay-util:include page="/html/portlet/layout_configuration/view_category.jsp" />

			<%
				request.setAttribute(WebKeys.PORTLET_CATEGORY_PATH, oldCategoryPath);
			}

			itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)itr.next();

				divId.setIndex(0);

				divId.append(newCategoryPath);
				divId.append(":");

				matcher = pattern.matcher(PortalUtil.getPortletTitle(portlet, application, locale));

				while (matcher.find()) {
					divId.append(matcher.group());
				}

				boolean portletInstanceable = portlet.isInstanceable();
				boolean portletUsed = layoutTypePortlet.hasPortletId(portlet.getPortletId());
				boolean portletLocked = (!portletInstanceable && portletUsed);

				if (portletInstanceable && layout.getType().equals(LayoutConstants.TYPE_PANEL)) {
					continue;
				}
			%>

				<c:choose>
					<c:when test="<%= layout.getType().equals(LayoutConstants.TYPE_PORTLET) %>">

						<%
						Set<String> headerPortalCssSet = new LinkedHashSet<String>();

						for (String headerPortalCss : portlet.getHeaderPortalCss()) {
							if (!HttpUtil.hasProtocol(headerPortalCss)) {
								headerPortalCss = PortalUtil.getStaticResourceURL(request, request.getContextPath() + headerPortalCss, portlet.getTimestamp());
							}

							if (!headerPortalCssSet.contains(headerPortalCss)) {
								headerPortalCssSet.add(headerPortalCss);
							}
						}

						Set<String> headerPortletCssSet = new LinkedHashSet<String>();

						for (String headerPortletCss : portlet.getHeaderPortletCss()) {
							if (!HttpUtil.hasProtocol(headerPortletCss)) {
								headerPortletCss = PortalUtil.getStaticResourceURL(request, portlet.getContextPath() + headerPortletCss, portlet.getTimestamp());
							}

							if (!headerPortletCssSet.contains(headerPortletCss)) {
								headerPortletCssSet.add(headerPortletCss);
							}
						}

						Set<String> footerPortalCssSet = new LinkedHashSet<String>();

						for (String footerPortalCss : portlet.getFooterPortalCss()) {
							if (!HttpUtil.hasProtocol(footerPortalCss)) {
								footerPortalCss = PortalUtil.getStaticResourceURL(request, request.getContextPath() + footerPortalCss, portlet.getTimestamp());
							}

							if (!footerPortalCssSet.contains(footerPortalCss)) {
								footerPortalCssSet.add(footerPortalCss);
							}
						}

						Set<String> footerPortletCssSet = new LinkedHashSet<String>();

						for (String footerPortletCss : portlet.getFooterPortletCss()) {
							if (!HttpUtil.hasProtocol(footerPortletCss)) {
								footerPortletCss = PortalUtil.getStaticResourceURL(request, portlet.getContextPath() + footerPortletCss, portlet.getTimestamp());
							}

							if (!footerPortletCssSet.contains(footerPortletCss)) {
								footerPortletCssSet.add(footerPortletCss);
							}
						}
						%>

						<div
							class="lfr-portlet-item <c:if test="<%= portletLocked %>">lfr-portlet-used</c:if> <c:if test="<%= portletInstanceable %>">lfr-instanceable</c:if>"
							footerPortalCssPaths="<%= StringUtil.merge(footerPortalCssSet) %>"
							footerPortletCssPaths="<%= StringUtil.merge(footerPortletCssSet) %>"
							headerPortalCssPaths="<%= StringUtil.merge(headerPortalCssSet) %>"
							headerPortletCssPaths="<%= StringUtil.merge(headerPortletCssSet) %>"
							id="layoutConfigurationEntry<%= portlet.getPortletId() %>"
							instanceable="<%= portletInstanceable %>"
							plid="<%= plid %>"
							portletId="<%= portlet.getPortletId() %>"
							title="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>"
						>
							<p><%= PortalUtil.getPortletTitle(portlet, application, locale) %> <a href="javascript:;"><liferay-ui:message key="add" /></a></p>
						</div>

						<input id="layoutConfigurationEntry<%= portlet.getPortletId() %>CategoryPath" type="hidden" value="<%= divId.toString().replace(':', '-') %>" />
					</c:when>
					<c:otherwise>
						<div>
							<a href="<liferay-portlet:renderURL portletName="<%= portlet.getRootPortletId() %>"></liferay-portlet:renderURL>"><%= PortalUtil.getPortletTitle(portlet, application, locale) %></a>
						</div>
					</c:otherwise>
				</c:choose>

			<%
			}
			%>

		</div>
	</div>

	<input id="layoutConfigurationEntry<%= portletCategoryIndex %>CategoryPath" type="hidden" value="<%= newCategoryPath.replace(':', '-') %>" />
<%
}
%>