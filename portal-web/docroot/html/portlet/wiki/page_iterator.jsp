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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<script type="text/javascript">
	function <portlet:namespace />compare() {
		var rowIds = jQuery('input[@name=<portlet:namespace />rowIds]:checked');
		var sourceVersion = jQuery('input[@name="<portlet:namespace />sourceVersion"]');
		var targetVersion = jQuery('input[@name="<portlet:namespace />targetVersion"]');

		if (rowIds.length == 1) {
			sourceVersion.val(rowIds[0].value);
		}
		else if (rowIds.length == 2) {
			sourceVersion.val(rowIds[1].value);
			targetVersion.val(rowIds[0].value);
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />inactivateRowIds(element) {
  		var rowIds = jQuery('input[@name=<portlet:namespace />rowIds]');

  		var found = 0;
  		var totalChecked = jQuery('input[@name=<portlet:namespace />rowIds]:checked').length;

  		for (i = 0; i < rowIds.length; i++) {
  			if (rowIds[i].checked && (found < 2)) {
  				fund++;
  			}
  			else if (totalChecked == 0) {

				// Enable everything

				rowIds[i].checked = false;
      			rowIds[i].disabled = false;
    		}
    		else if ((found == 0) && (totalChecked == 1)) {

				// Disable everything up to the first one

				rowIds[i].checked = false;
     			rowIds[i].disabled = true;
    		}
    		else if ((found == 1) && (totalChecked >= 1)) {

				// Unselect everything after the first one

				rowIds[i].checked = false;
      			rowIds[i].disabled = false;
    		}
    		else if ((found == 2) && (totalChecked >= 2)) {

				// Disable elements after the second one
				rowIds[i].checked = false;
      			rowIds[i].disabled = true;
    		}
		}
	}

	jQuery(document).ready(
		function() {
			jQuery('input[@name=<portlet:namespace />rowIds]').click(
				function() {
					<portlet:namespace />inactivateRowIds(this);
				}
			);
		}
	);
</script>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

String type = ParamUtil.getString(request, "type");

boolean comparePages = false;

if (type.equals("page_history")) {
	comparePages = true;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

if (wikiPage != null) {
	portletURL.setParameter("title", wikiPage.getTitle());
}

List headerNames = new ArrayList();

headerNames.add("page");
headerNames.add("revision");
headerNames.add("date");

if (type.equals("page_history")) {
	headerNames.add(StringPool.BLANK);
}

String emptyResultsMessage = null;

if (type.equals("page_links")) {
	emptyResultsMessage = "there-are-no-pages-that-link-to-this-page";
}
else if (type.equals("recent_changes")) {
	emptyResultsMessage = "there-are-no-recent-changes";
}

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, emptyResultsMessage);

if (comparePages) {
	searchContainer.setRowChecker(new RowChecker(renderResponse, RowChecker.ALIGN, RowChecker.VALIGN, RowChecker.FORM_NAME, null, RowChecker.ROW_IDS));
}

int total = 0;
List results = null;

if (type.equals("all_pages")) {
	total = WikiPageLocalServiceUtil.getPagesCount(node.getNodeId(), true);

	searchContainer.setTotal(total);

	results = WikiPageLocalServiceUtil.getPages(node.getNodeId(), true, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("orphan_pages")) {
	List orphans = WikiPageLocalServiceUtil.getOrphans(node.getNodeId());

	total = orphans.size();

	searchContainer.setTotal(total);

	results = ListUtil.subList(orphans, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("page_history")) {
	total = WikiPageLocalServiceUtil.getPagesCount(wikiPage.getNodeId(), wikiPage.getTitle());

	searchContainer.setTotal(total);

	results = WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("page_links")) {
	List links = WikiPageLocalServiceUtil.getIncomingLinks(wikiPage.getNodeId(), wikiPage.getTitle());

	total = links.size();

	searchContainer.setTotal(total);

	results = ListUtil.subList(links, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("recent_changes")) {
	total = WikiPageLocalServiceUtil.getRecentChangesCount(node.getNodeId());

	searchContainer.setTotal(total);

	results = WikiPageLocalServiceUtil.getRecentChanges(node.getNodeId(), searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	WikiPage curWikiPage = (WikiPage)results.get(i);

	ResultRow row = new ResultRow(curWikiPage, String.valueOf(curWikiPage.getVersion()), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setParameter("struts_action", "/wiki/view");
	rowURL.setParameter("nodeId", String.valueOf(curWikiPage.getNodeId()));
	rowURL.setParameter("title", curWikiPage.getTitle());
	rowURL.setParameter("version", String.valueOf(curWikiPage.getVersion()));

	// Title

	row.addText(curWikiPage.getTitle(), rowURL);

	// Revision

	row.addText(String.valueOf(curWikiPage.getVersion()), rowURL);

	// Date

	row.addText(dateFormatDateTime.format(curWikiPage.getCreateDate()), rowURL);

	// Action

	if (type.equals("page_history")) {
		if (curWikiPage.isHead()) {
			row.addText(StringPool.BLANK);
		}
		else {
			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wiki/page_history_action.jsp");
		}
	}

	// Add result row

	resultRows.add(row);
}
%>

<%
if (comparePages && (results.size() > 1)) {
	WikiPage lastWikiPageVersion = (WikiPage)results.get(1);
%>

	<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/wiki/compare_versions" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />compare(); return false;">
	<input name="<portlet:namespace />backURL" type="hidden" value="<%= currentURL %>" />
	<input name="<portlet:namespace />nodeId" type="hidden" value="<%= node.getNodeId() %>" />
	<input name="<portlet:namespace />title" type="hidden" value="<%= wikiPage.getTitle() %>" />
	<input name="<portlet:namespace />sourceVersion" type="hidden" value="<%= lastWikiPageVersion.getVersion() %>" />
	<input name="<portlet:namespace />targetVersion" type="hidden" value="<%= wikiPage.getVersion() %>" />

	<input type="submit" value="<liferay-ui:message key="compare-versions" />" />

	</form>

	<br />

<%
}
%>
<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />