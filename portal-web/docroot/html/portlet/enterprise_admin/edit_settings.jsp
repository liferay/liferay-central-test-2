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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String[] configurationSections = PropsValues.COMPANY_SETTINGS_FORM_CONFIGURATION;
String[] identificationSections = PropsValues.COMPANY_SETTINGS_FORM_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.COMPANY_SETTINGS_FORM_MISCELLANEOUS;


String[] allSections = ArrayUtil.append(configurationSections, ArrayUtil.append(identificationSections, miscellaneousSections));
String[][] categorySections = {configurationSections, identificationSections, miscellaneousSections};

String curSection = configurationSections[0];
%>

<div id="<portlet:namespace />sectionsContainer">
	<table class="user-table" width="100%">
	<tr>
		<td>

			<%
			request.setAttribute("addresses.className", Account.class.getName());
			request.setAttribute("emailAddresses.className", Account.class.getName());
			request.setAttribute("phones.className", Account.class.getName());
			request.setAttribute("websites.className", Account.class.getName());

			request.setAttribute("addresses.classPK", company.getAccountId());
			request.setAttribute("emailAddresses.classPK", company.getAccountId());
			request.setAttribute("phones.classPK", company.getAccountId());
			request.setAttribute("websites.classPK", company.getAccountId());

			for (String section : allSections) {
				String sectionId = _getSectionId(section);
				String sectionJsp = "/html/portlet/enterprise_admin/settings/" + _getSectionJsp(section) + ".jsp";
			%>

				<div class="form-section <%= curSection.equals(section)? "selected" : StringPool.BLANK %>" id="<%= sectionId %>">
					<liferay-util:include page="<%= sectionJsp %>" />
				</div>

			<%
			}
			%>

			<div class="lfr-component form-navigation">
				<div class="user-info">
					<p class="float-container">
						<img alt="<liferay-ui:message key="logo" />" class="company-logo" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= company.getLogoId() %>&t=<%= ImageServletTokenUtil.getToken(company.getLogoId()) %>" /><br />

						<span><%= company.getName() %></span>
					</p>
				</div>

				<%
				String[] categoryNames = _CATEGORY_NAMES;
				%>

				<%@ include file="/html/portlet/enterprise_admin/categories_navigation.jspf" %>

				<div class="aui-button-holder">
					<aui:button onClick='<%= renderResponse.getNamespace() + "saveCompany();" %>' value="save" />

					<%
					PortletURL portletURL = new PortletURLImpl(request, PortletKeys.ENTERPRISE_ADMIN_SETTINGS, plid, PortletRequest.RENDER_PHASE);

					portletURL.setWindowState(WindowState.MAXIMIZED);
					%>

					<aui:button onClick="<%= portletURL.toString() %>" type="cancel" />
				</div>
			</div>
		</td>
	</tr>
	</table>
</div>

<%!
private static String[] _CATEGORY_NAMES = {"configuration", "identification", "miscellaneous"};
%>