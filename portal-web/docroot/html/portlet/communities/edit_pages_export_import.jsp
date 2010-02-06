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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs4 = (String)request.getAttribute("edit_pages.jsp-tab4");

String redirect = ParamUtil.getString(request, "redirect");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();

String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");
%>

<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

<%
List portletsList = new ArrayList();
Set portletIdsSet = new HashSet();

Iterator itr1 = LayoutLocalServiceUtil.getLayouts(liveGroupId, privateLayout).iterator();

while (itr1.hasNext()) {
	Layout curLayout = (Layout)itr1.next();

	if (curLayout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
		LayoutTypePortlet curLayoutTypePortlet = (LayoutTypePortlet)curLayout.getLayoutType();

		Iterator itr2 = curLayoutTypePortlet.getPortletIds().iterator();

		while (itr2.hasNext()) {
			Portlet curPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), (String)itr2.next());

			if (curPortlet != null) {
				PortletDataHandler portletDataHandler = curPortlet.getPortletDataHandlerInstance();

				if ((portletDataHandler != null) && !portletIdsSet.contains(curPortlet.getRootPortletId())) {
					portletIdsSet.add(curPortlet.getRootPortletId());

					portletsList.add(curPortlet);
				}
			}
		}
	}
}

List<Portlet> alwaysExportablePortlets = LayoutExporter.getAlwaysExportablePortlets(company.getCompanyId());

for (Portlet alwaysExportablePortlet : alwaysExportablePortlets) {
	if (!portletIdsSet.contains(alwaysExportablePortlet.getRootPortletId())) {
		portletIdsSet.add(alwaysExportablePortlet.getRootPortletId());

		portletsList.add(alwaysExportablePortlet);
	}
}

portletsList = ListUtil.sort(portletsList, new PortletTitleComparator(application, locale));

String tabs4Names = "export,import";

if (!StringUtil.contains(tabs4Names, tabs4)) {
	tabs4 = "export";
}
%>

<aui:fieldset>
	<liferay-ui:tabs
		names="<%= tabs4Names %>"
		param="tabs4"
		url="<%= portletURL.toString() %>"
	/>

	<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
	<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
	<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

	<c:choose>
		<c:when test='<%= tabs4.equals("export") %>'>
			<aui:input label="export-the-selected-data-to-the-given-lar-file-name" name="exportFileName" size="50" value='<%= HtmlUtil.escape(StringUtil.replace(rootNodeName, " ", "_")) + "-" + Time.getShortTimestamp() + ".lar" %>' />

			<aui:field-wrapper label="what-would-you-like-to-export">
				<%@ include file="/html/portlet/communities/edit_pages_export_import_options.jspf" %>
			</aui:field-wrapper>

			<aui:button-row>
				<aui:button onClick='<%= renderResponse.getNamespace() + "exportPages();" %>' value="export" />

				<aui:button onClick="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</c:when>
		<c:when test='<%= tabs4.equals("import") %>'>
			<c:choose>
				<c:when test="<%= (layout.getGroupId() != groupId) || (layout.isPrivateLayout() != privateLayout) %>">
					<aui:input label="import-a-lar-file-to-overwrite-the-selected-data" name="importFileName" size="50" type="file" />

					<aui:field-wrapper label="what-would-you-like-to-import">
						<%@ include file="/html/portlet/communities/edit_pages_export_import_options.jspf" %>
					</aui:field-wrapper>

					<aui:button-row>
						<aui:button onClick='<%= renderResponse.getNamespace() + "importPages();" %>' type="button" value="import" />

						<aui:button onClick="<%= redirect %>" type="cancel" />
					</aui:button-row>
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="import-from-within-the-target-community-can-cause-conflicts" />
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</aui:fieldset>

<aui:script use="selector-css3">
	var toggleHandlerControl = function(item, index, collection) {
		var container = item.ancestor('.<portlet:namespace />handler-control').one('ul');

		if (container) {
			var action = 'hide';

			if (item.get('checked')) {
				action = 'show';
			}

			container[action]();
		}
	};

	var checkboxes = A.all('.<portlet:namespace />handler-control input[type=checkbox]');

	if (checkboxes) {
		var uncheckedBoxes = checkboxes.filter(':not(:checked)');

		if (uncheckedBoxes) {
			uncheckedBoxes.each(toggleHandlerControl);
		}

		checkboxes.detach('click');

		checkboxes.on(
			'click',
			function(event) {
				toggleHandlerControl(event.currentTarget);
			}
		);
	}
</aui:script>