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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long organizationId = BeanParamUtil.getLong(organization, request, "organizationId");

String className = Organization.class.getName();
long classPK = 0;

if (organization!=null) {
	classPK = organization.getOrganizationId();
}

themeDisplay.setIncludeServiceJs(true);

// Form Sections

String[] mainSections = PropsValues.ORGANIZATIONS_PROFILE_ADD_MAIN;
String[] identificationSections = PropsValues.ORGANIZATIONS_PROFILE_ADD_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.ORGANIZATIONS_PROFILE_ADD_MISCELLANEOUS;

if (organization != null) {
	mainSections = PropsValues.ORGANIZATIONS_PROFILE_UPDATE_MAIN;
	identificationSections = PropsValues.ORGANIZATIONS_PROFILE_UPDATE_IDENTIFICATION;
	miscellaneousSections = PropsValues.ORGANIZATIONS_PROFILE_UPDATE_MISCELLANEOUS;
}

String[] tempSections = new String[mainSections.length + identificationSections.length];
String[] allSections = new String[mainSections.length + identificationSections.length + miscellaneousSections.length];
ArrayUtil.combine(mainSections, identificationSections, tempSections);
ArrayUtil.combine(tempSections, miscellaneousSections, allSections);

String[][] categorySections = {mainSections, identificationSections, miscellaneousSections};

String currentSection = mainSections[0];
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveOrganization(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /><portlet:param name="backURL" value="<%= HttpUtil.encodeURL(backURL) %>" /></portlet:renderURL>&<portlet:namespace />organizationId=" />
<input name="<portlet:namespace />backURL" type="hidden" value="<%= HtmlUtil.escape(backURL) %>" />
<input name="<portlet:namespace />organizationId" type="hidden" value="<%= organizationId %>" />

<liferay-util:include page="/html/portlet/enterprise_admin/organization/toolbar.jsp">
	<liferay-util:param name="toolbar-item" value='<%= (organization == null) ? "add-organization" : "view-organizations" %>' />
</liferay-util:include>

<liferay-ui:error exception="<%= DuplicateOrganizationException.class %>" message="the-organization-name-is-already-taken" />
<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />
<liferay-ui:error exception="<%= NoSuchListTypeException.class %>" message="please-select-a-valid-value-from-the-list" />
<liferay-ui:error exception="<%= OrganizationNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= OrganizationParentException.class %>" message="please-enter-a-valid-parent" />
<liferay-ui:error exception="<%= WebsiteURLException.class %>" message="please-enter-a-valid-website-url" />

<div id="organization">
	<table class="organization-table" width="100%">
	<tr>
		<td>
			<%
			request.setAttribute("organization.selOrganization", organization);
			request.setAttribute("className", className);

			List<Website> websites = null;

			if (classPK <= 0) {
				websites = Collections.EMPTY_LIST;
			}
			else {
				websites = WebsiteServiceUtil.getWebsites(className, classPK);
			}

			request.setAttribute("common.websites", websites);

			for (String section : allSections) {
				String sectionId = _getIdName(section);
				String jspPath = "/html/portlet/enterprise_admin/organization/" + _getJspName(section) + ".jsp";
			%>
				<div class="form-section <%= currentSection.equals(section)? "selected" : StringPool.BLANK %>" id="<%= sectionId %>">
					<liferay-util:include page="<%= jspPath %>" />
				</div>
			<%
			}
			%>

			<div class="lfr-component form-navigation">
				<div class="organization-info">
					<p class="float-container">
						<c:if test="<%= organization != null %>">
							<img class="avatar" src=" <%=themeDisplay.getPathThemeImages()%>/control_panel/avatar_organization_small.png" alt="<%= organization.getName() %>" />

							<liferay-ui:message key="editing-organization" />: <span><%= organization.getName() %></span>
						</c:if>
					</p>
				</div>

				<%
				for (int i = 0; i < _CATEGORY_NAMES.length; i++) {
					String category = _CATEGORY_NAMES[i];
					String[] sections = categorySections[i];

					if (sections.length > 0) {
				%>
						<div class="menu-group">
							<h3><liferay-ui:message key="<%= category %>" /></h3>
							<ul>
								<%
								for (String section : sections) {
									String sectionId = _getIdName(section);
								%>
									<li <%= currentSection.equals(section)? "class=\"selected\"" : StringPool.BLANK %>><a href="#<%= sectionId %>" id='<%= sectionId %>Link'><liferay-ui:message key="<%= section %>" /></a></li>
								<%
								}
								%>
							</ul>
						</div>
				<%
					}
				}
				%>

				<div class="button-holder">
					<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveOrganization();" />  &nbsp;

					<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HttpUtil.encodeURL(backURL) %>';" /><br />
				</div>
			</div>
		</td>
	</tr>
	</table>
</div>

<script type="text/javascript">
	jQuery(
		function () {
			var formNav = jQuery('.form-navigation');
			var formSections = jQuery('#organization .form-section');

			var revealSection = function(id, currentNavItem) {
				var li = currentNavItem || formNav.find('[@href$=' + id + ']').parent();
				id = id.split('#');

				if (!id[1]) {
					return;
				}

				id = '#' + id[1];

				var section = jQuery(id);

				formNav.find('.selected').removeClass('selected');
				formSections.removeClass('selected');

				section.addClass('selected');
				li.addClass('selected');
			};

			jQuery('.form-navigation li a').click(
				function(event) {
					var li = jQuery(this.parentNode);

					if (!li.is('.selected')) {
						revealSection(this.href, li);
					}

					return false;
				}
				);

			revealSection(location.hash);

			var markAsModified = function(id) {
				if (jQuery(id).text().indexOf(' (<liferay-ui:message key="Modified"/>)') == -1) {
					jQuery(id).append(' <b>(<liferay-ui:message key="Modified"/>)</b>');
				}
			}

			<%
			for (String section : allSections) {
				String sectionId = _getIdName(section);
			%>
				var markAsModified_<%= sectionId %> = function() {
					return markAsModified('#<%= sectionId %>Link');
				}

				jQuery('#<%= sectionId %> input').change(markAsModified_<%= sectionId %>)
				jQuery('#<%= sectionId %> select').change(markAsModified_<%= sectionId %>)
				jQuery('#<%= sectionId %> textarea').change(markAsModified_<%= sectionId %>)
			<%
			}
			%>
		}
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</script>

<%!
private static String[] _CATEGORY_NAMES = {"organization-information", "identification", "miscellaneous"};

private String _getIdName(String name) {
	int pos = name.indexOf(StringPool.DASH);

	if (pos == -1) {
		return name;
	}

	StringBuilder sb = new StringBuilder();

	sb.append(name.substring(0, pos));
	sb.append(name.substring(pos + 1, pos + 2).toUpperCase());
	sb.append(name.substring(pos + 2));

	return _getIdName(sb.toString());
}

private String _getJspName(String name) {
	return StringUtil.replace(name, StringPool.DASH, StringPool.UNDERLINE);
}
%>