<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
String popupId = ParamUtil.getString(request, "popupId");

String tabs2 = ParamUtil.getString(request, "tabs2", "public");

String pagesRedirect = ParamUtil.getString(request, "pagesRedirect");

boolean publish = ParamUtil.getBoolean(request, "publish");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;
Group group = selGroup;

int pagesCount = 0;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else {
	liveGroup = selGroup;

	if (selGroup.hasStagingGroup()) {
		stagingGroup = selGroup.getStagingGroup();
	}
}

long groupId = liveGroup.getGroupId();

if (group != null) {
	groupId = group.getGroupId();
}

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

long[] selectedPlids = new long[0];

if (selPlid > 0) {
	selectedPlids = new long[] {selPlid};
	publish = true;
}
else {
	selectedPlids = GetterUtil.getLongValues(StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, "exportLayoutsTreeSelected"), ","));
}

List results = new ArrayList();

for (int i = 0; i < selectedPlids.length; i++) {
	try {
		results.add(LayoutLocalServiceUtil.getLayout(selectedPlids[i]));
	}
	catch (NoSuchLayoutException nsle) {
	}
}

boolean privateLayout = ParamUtil.getBoolean(request, "private");

if (privateLayout) {
	if (group != null) {
		pagesCount = group.getPrivateLayoutsPageCount();
	}
}
else {
	if (group != null) {
		pagesCount = group.getPublicLayoutsPageCount();
	}
}

Properties groupTypeSettings = null;

if (group != null) {
	groupTypeSettings = group.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new Properties();
}

Organization organization = null;
User user2 = null;

if (liveGroup.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(liveGroup.getClassPK());
}
else if (liveGroup.isUser()) {
	user2 = UserLocalServiceUtil.getUserById(liveGroup.getClassPK());
}

String rootNodeName = liveGroup.getName();

if (liveGroup.isOrganization()) {
	rootNodeName = organization.getName();
}
else if (liveGroup.isUser()) {
	rootNodeName = user2.getFullName();
}

LayoutLister layoutLister = new LayoutLister();

LayoutView layoutView = layoutLister.getLayoutView(groupId, privateLayout, rootNodeName, locale);

List layoutList = layoutView.getList();

PortletURL portletURL = renderResponse.createActionURL();

if (themeDisplay.isStatePopUp()) {
	portletURL.setWindowState(LiferayWindowState.POP_UP);
}
else {
	portletURL.setWindowState(WindowState.MAXIMIZED);
}

portletURL.setParameter("struts_action", "/communities/edit_pages");
portletURL.setParameter("groupId", String.valueOf(liveGroupId));
portletURL.setParameter("private", String.valueOf(privateLayout));

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));
%>

<style type="text/css">
	#<portlet:namespace />pane th.col-1 {
		width: 3%;
	}

	#<portlet:namespace />pane th.col-2 {
		text-align: left;
		width: 74%;
	}

	#<portlet:namespace />pane th.col-3 {
		padding-right: 40px;
		text-align: center;
		width: 23%;
	}

	#<portlet:namespace />pane td.col-1 {
		padding-top: 5px;
	}
</style>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm2">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="publish_to_live">
<input name="<portlet:namespace />pagesRedirect" type="hidden" value="<%= pagesRedirect %>">
<input name="<portlet:namespace />stagingGroupId" type="hidden" value="<%= stagingGroupId %>">

<fieldset>
	<legend><liferay-ui:message key="scope" /></legend>

	<c:choose>
		<c:when test="<%= !publish %>">
			<input <%= (results.size() == 0) ? "checked":"" %> id="<portlet:namespace />scope_all_pages" name="<portlet:namespace />scope" type="radio" value="all-pages" onClick="Liferay.LayoutExporter.all({pane: '#<portlet:namespace />pane', obj: this, publish: <%= publish %>});">

			<label for="<portlet:namespace />scope_all_pages"><liferay-ui:message key="all-pages" /></label><br />

			<input <%= (results.size() > 0) ? "checked" : "" %> id="<portlet:namespace />scope_selected_pages" name="<portlet:namespace />scope" type="radio" value="selected-pages" onClick="Liferay.LayoutExporter.selected({pane: '#<portlet:namespace />pane', obj: this, publish: <%= publish %>});">

			<label for="<portlet:namespace />scope_selected_pages"><liferay-ui:message key="selected-pages" /></label>
		</c:when>
		<c:otherwise>
			<input <%= (results.size() == 0) ? "checked":"" %> id="<portlet:namespace />scope_all_pages" name="<portlet:namespace />scope" type="radio" value="all-pages" onClick="Liferay.LayoutExporter.all({pane: '#<portlet:namespace />pane', obj: this, publish: <%= publish %>});">

			<label for="<portlet:namespace />scope_all_pages"><liferay-ui:message key="all-pages" /></label>

			<c:if test="<%= results.size() > 0 %>">
				<br />

				<input <%= (results.size() > 0) ? "checked" : "" %> id="<portlet:namespace />scope_selected_pages" name="<portlet:namespace />scope" type="radio" value="selected-pages" onClick="Liferay.LayoutExporter.selected({pane: '#<portlet:namespace />pane', obj: this, publish: <%= publish %>});">

				<label for="<portlet:namespace />scope_selected_pages"><liferay-ui:message key="selected-pages" /></label>
			</c:if>
		</c:otherwise>
	</c:choose>
</fieldset>

<div id="<portlet:namespace />pane" style="border: 1px solid #CCC; <%= (results.size() == 0) ? "display: none;" : "" %> height: 380px; overflow: auto;">
	<c:choose>
		<c:when test="<%= !publish %>">
			<div id="<portlet:namespace />select-tree-output" style="margin: 4px;"></div>

			<%@ include file="/html/portlet/communities/tree_js.jspf" %>

			<script type="text/javascript">
				jQuery(
					function() {
						new Tree(
							{
								className: "gamma",
								icons: <portlet:namespace />layoutIcons,
								nodes: <portlet:namespace />layoutArray,
								openNodes: '<%= SessionTreeJSClicks.getOpenNodes(request, "exportLayoutsTree") %>',
								outputId: '#<%= renderResponse.getNamespace() %>select-tree-output',
								selectable: true,
								selectedNodes: '<%= SessionTreeJSClicks.getOpenNodes(request, "exportLayoutsTreeSelected") %>',
								treeId: "exportLayoutsTree"
							}
						);
					}
				);
			</script>
		</c:when>
		<c:otherwise>

			<%
			int total = results.size();

			List headerNames = new ArrayList();

			headerNames.add("pages");
			headerNames.add("type");

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, total, portletURL, headerNames, null);

			searchContainer.setTotal(total);
			searchContainer.setResults(results);
			searchContainer.setRowChecker(new ExportPageChecker(renderResponse, "left", "top", RowChecker.COLSPAN, RowChecker.FORM_NAME, RowChecker.ALL_ROW_IDS, RowChecker.ROW_IDS));

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				Layout curLayout = (Layout)results.get(i);

				ResultRow row = new ResultRow(curLayout, curLayout.getPrimaryKey(), i);

				row.addJSP("left", "top", 2, "/html/portlet/communities/export_page_options.jsp");

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
		</c:otherwise>
	</c:choose>
</div>

<br />

<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="selectURL">
	<portlet:param name="struts_action" value="/communities/export_pages" />
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="pagesRedirect" value="<%= pagesRedirect %>" />
	<portlet:param name="popupId" value="<%= popupId %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="stagingGroupId" value="<%= String.valueOf(stagingGroupId) %>" />
</portlet:renderURL>

<c:choose>
	<c:when test="<%= !publish %>">
		<input <%= (results.size() == 0)? "style=\"display: none;\"" :"" %> id="select_btn" type="button" value="<liferay-ui:message key="select" />" onClick="Liferay.Popup.update('#<%= popupId %>', '<%= selectURL %>&<portlet:namespace />publish=true');" />

		<input <%= (results.size() > 0)? "style=\"display: none;\"" :"" %> id="publish_btn" type="button" value="<liferay-ui:message key="publish" />" onClick="submitForm(document.<portlet:namespace />fm2);" />
	</c:when>
	<c:otherwise>
		<c:if test="<%= selPlid <= LayoutImpl.DEFAULT_PARENT_LAYOUT_ID %>">
			<input type="button" id="change_btn" value="<liferay-ui:message key="change-selection" />" onClick="Liferay.Popup.update('#<%= popupId %>', '<%= selectURL %>&<portlet:namespace />publish=false');" />
		</c:if>

		<input type="button" id="publish_btn" value="<liferay-ui:message key="publish" />" onClick="submitForm(document.<portlet:namespace />fm2);" />
	</c:otherwise>
</c:choose>

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="Liferay.Popup.close(this);" />

</form>