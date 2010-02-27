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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.taglib.ui.LanguageTag" %>

<%
String formName = (String)request.getAttribute("liferay-ui:language:formName");

String formAction = (String)request.getAttribute("liferay-ui:language:formAction");

if (Validator.isNull(formAction)) {
	PortletURLImpl portletURLImpl = new PortletURLImpl(request, PortletKeys.LANGUAGE, plid, PortletRequest.ACTION_PHASE);

	portletURLImpl.setWindowState(WindowState.NORMAL);
	portletURLImpl.setPortletMode(PortletMode.VIEW);
	portletURLImpl.setAnchor(false);

	portletURLImpl.setParameter("struts_action", "/language/view");
	//portletURLImpl.setParameter("redirect", currentURL);

	formAction = portletURLImpl.toString();
}

String name = (String)request.getAttribute("liferay-ui:language:name");
Locale[] locales = (Locale[])request.getAttribute("liferay-ui:language:locales");
int displayStyle = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:language:displayStyle"));

Map langCounts = new HashMap();

for (int i = 0; i < locales.length; i++) {
	Integer count = (Integer)langCounts.get(locales[i].getLanguage());

	if (count == null) {
		count = new Integer(1);
	}
	else {
		count = new Integer(count.intValue() + 1);
	}

	langCounts.put(locales[i].getLanguage(), count);
}

Set duplicateLanguages = new HashSet();

for (int i = 0; i < locales.length; i++) {
	Integer count = (Integer)langCounts.get(locales[i].getLanguage());

	if (count.intValue() != 1) {
		duplicateLanguages.add(locales[i].getLanguage());
	}
}
%>

<c:choose>
	<c:when test="<%= displayStyle == LanguageTag.SELECT_BOX %>">
		<aui:form action="<%= formAction %>" method="post" name="<%= formName %>">
			<aui:select changesContext="<%= true %>" label="" name="<%= name %>" onChange='<%= "submitForm(document." + namespace + formName + ");" %>' title="language">

				<%
				for (int i = 0; i < locales.length; i++) {
				%>

					<aui:option cssClass="taglib-language-option" label="<%= locales[i].getDisplayName(locales[i]) %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>" selected="<%= (locale.getLanguage().equals(locales[i].getLanguage()) && locale.getCountry().equals(locales[i].getCountry())) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

				<%
				}
				%>

			</aui:select>
		</aui:form>

		<aui:script>

			<%
			for (int i = 0; i < locales.length; i++) {
			%>

				document.<%= namespace + formName %>.<%= namespace + name %>.options[<%= i %>].style.backgroundImage = "url(<%= themeDisplay.getPathThemeImages() %>/language/<%= LocaleUtil.toLanguageId(locales[i]) %>.png)";

			<%
			}
			%>

		</aui:script>
	</c:when>
	<c:otherwise>

		<%
		for (int i = 0; i < locales.length; i++) {
			String language = locales[i].getDisplayLanguage(locales[i]);
			String country = locales[i].getDisplayCountry(locales[i]);

			if (displayStyle == LanguageTag.LIST_SHORT_TEXT) {
				if (language.length() > 3) {
					language = locales[i].getLanguage().toUpperCase();
				}

				country = locales[i].getCountry().toUpperCase();
			}
		%>

			<c:choose>
				<c:when test="<%= (displayStyle == LanguageTag.LIST_LONG_TEXT) || (displayStyle == LanguageTag.LIST_SHORT_TEXT) %>">
					<a href="<%= formAction %>&<%= name %>=<%= locales[i].getLanguage() + "_" + locales[i].getCountry() %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>">
						<%= language %>

						<c:if test="<%= duplicateLanguages.contains(locales[i].getLanguage()) %>">
							(<%= country %>)
						</c:if>
					</a>

					<c:if test="<%= i + 1 < locales.length %>">
						|
					</c:if>
				</c:when>
				<c:otherwise>
					<liferay-ui:icon
						image='<%= "../language/" + LocaleUtil.toLanguageId(locales[i]) %>'
						lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>"
						message="<%= locales[i].getDisplayName(locales[i]) %>"
						url='<%= formAction + "&" + name + "=" + locales[i].getLanguage() + "_" + locales[i].getCountry() %>'
					/>
				</c:otherwise>
			</c:choose>

		<%
		}
		%>

	</c:otherwise>
</c:choose>