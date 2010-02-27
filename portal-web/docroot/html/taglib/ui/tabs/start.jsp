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

<%@ include file="/html/taglib/ui/tabs/init.jsp" %>

<%

// URL

LiferayPortletURL portletURL = (LiferayPortletURL)request.getAttribute("liferay-ui:tabs:portletURL");

String url = GetterUtil.getString((String)request.getAttribute("liferay-ui:tabs:url"));
String anchor = StringPool.BLANK;

if (url != null) {

	// Strip existing tab parameter and value from the URL

	int x = url.indexOf(param + "=");

	if (x != -1) {
		x = url.lastIndexOf("&", x);

		if (x == -1) {
			x = url.lastIndexOf("?", x);
		}

		int y = url.indexOf("&", x + 1);

		if (y == -1) {
			y = url.length();
		}

		url = url.substring(0, x) + url.substring(y, url.length());
	}

	// Strip trailing &

	if (url.endsWith("&")) {
		url = url.substring(0, url.length() - 1);
	}

	// Strip anchor

	x = url.indexOf("&#");

	if (x != -1) {
		url = url.substring(0, x);
		anchor = url.substring(x, url.length());
	}
}

// Back url

String backURL = (String)request.getAttribute("liferay-ui:tabs:backURL");

// Refresh

boolean refresh = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:tabs:refresh"));

// onClick

String onClick = GetterUtil.getString((String)request.getAttribute("liferay-ui:tabs:onClick"));
%>

<c:if test="<%= names.length > 0 %>">

	<%
	String oldPortletURLValue = null;

	if (portletURL != null) {
		oldPortletURLValue = portletURL.getParameter(param);
	}
	%>

	<c:choose>
		<c:when test="<%= themeDisplay.isFacebook() %>">
			<fb:tabs>
		</c:when>
		<c:otherwise>
			<input name="<%= namespace %><%= param %>TabsScroll" type="hidden" />

			<ul class="aui-tabview-list">
		</c:otherwise>
	</c:choose>

	<%
	for (int i = 0; i < values.length; i++) {
		String curURL = (String)request.getAttribute("liferay-ui:tabs:url" + i);

		if (Validator.isNull(curURL)) {
			if (values.length == 1) {
				/*if (Validator.isNotNull(backURL)) {
					curURL = backURL;
				}*/
			}
			else {
				if (refresh) {
					if (portletURL != null) {
						portletURL.setParameter(param, values[i]);

						curURL = portletURL.toString();
					}
					else {
						if (values[i].equals("&raquo;")) {
							curURL = url + "&" + param + "=" + values[0] + anchor;
						}
						else {
							curURL = url + "&" + param + "=" + values[i] + anchor;
						}
					}
				}
				else {
					curURL = "javascript:";

					if (Validator.isNotNull(formName)) {
						curURL += "document." + namespace + formName + "." + namespace + param + ".value = '" + names[i] + "';";
					}

					curURL += "Liferay.Portal.Tabs.show('" + namespace + param + "', " + namesJS + ", '" + names[i] + "');";
				}
			}
		}

		String curOnClick = StringPool.BLANK;

		if (Validator.isNotNull(onClick)) {
			curOnClick = onClick + "('" + curURL + "', '" + values[i] + "'); return false;";
		}

		boolean selected = (values.length == 1) || value.equals(values[i]);

		String cssClassName = "aui-tab aui-state-default";

		if (selected) {
			cssClassName += " current aui-tab-active aui-state-active";
		}

		if (i == 0) {
			cssClassName += " first";
		}

		if ((i == values.length - 1) && Validator.isNull(backURL)) {
			cssClassName += " last";
		}
	%>

		<c:choose>
			<c:when test="<%= themeDisplay.isFacebook() %>">
				<fb:tab_item
					align="left"
					href="<%= curURL %>"
					selected="<%= selected %>"
					title="<%= LanguageUtil.get(pageContext, names[i]) %>"
				/>
			</c:when>
			<c:otherwise>
				<li class="<%= cssClassName %>" id="<%= namespace %><%= param %><%= values[i] %>TabsId">
					<span class="aui-tab-content">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(curURL) %>">
								<a href="<%= curURL %>"
									<c:if test="<%= Validator.isNotNull(curOnClick) %>">
										onClick="<%= curOnClick %>"
									</c:if>
								>
							</c:when>
							<c:otherwise>
								<span>
							</c:otherwise>
						</c:choose>

						<%= LanguageUtil.get(pageContext, names[i]) %>

						<c:choose>
							<c:when test="<%= Validator.isNotNull(curURL) %>">
								</a>
							</c:when>
							<c:otherwise>
								</span>
							</c:otherwise>
						</c:choose>
					</span>
				</li>
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

	<c:if test="<%= Validator.isNotNull(backURL) %>">

		<%
		if (!backURL.startsWith("javascript:")) {
			backURL = HtmlUtil.escape(PortalUtil.escapeRedirect(backURL));
		}
		%>

		<c:choose>
			<c:when test="<%= themeDisplay.isFacebook() %>">
				<fb:tab_item
					align="left"
					href="<%= backURL %>"
					selected="<%= false %>"
					title="&laquo; <%= LanguageUtil.get(pageContext, "back") %>"
				/>
			</c:when>
			<c:otherwise>
				<li class="aui-tab toggle last">
					<span class="aui-tab-content">
						<a href="<%= backURL %>" id="<%= namespace %><%= param %>TabsBack">&laquo; <liferay-ui:message key="back" /></a>
					</span>
				</li>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:choose>
		<c:when test="<%= themeDisplay.isFacebook() %>">
			</fb:tabs>
		</c:when>
		<c:otherwise>
			</ul>
		</c:otherwise>
	</c:choose>

	<%
	if (portletURL != null) {
		portletURL.setParameter(param, oldPortletURLValue);
	}
	%>

</c:if>