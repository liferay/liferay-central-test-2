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
			<aui:select label="" name="<%= name %>" onChange='<%= "submitForm(document." + namespace + formName + ");" %>'>

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
					<a href="<%= formAction %>&<%= name %>=<%= locales[i].getLanguage() + "_" + locales[i].getCountry() %>" lang="<%= locales[i].getLanguage() %>">
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
						lang="<%= locales[i].getLanguage() %>"
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