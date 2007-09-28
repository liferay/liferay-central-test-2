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

<%@ include file="/html/portlet/tagged_content/init.jsp" %>

<%

// Merge URL tags

String[] compilerEntries = (String[])request.getAttribute(WebKeys.TAGS_COMPILER_ENTRIES);

if ((compilerEntries != null) && (compilerEntries.length > 0)) {
	String[] newEntries = ArrayUtil.append(entries, compilerEntries);

	entries = newEntries;
}

if (themeDisplay.isSignedIn()) {

	// Merge my global tags

	PortalPreferences myGlobalPrefs = PortletPreferencesFactoryUtil.getPortalPreferences(request);

	String[] myGlobalEntries = myGlobalPrefs.getValues(PortletKeys.MY_GLOBAL_TAGS, "entries", new String[0]);

	if ((myGlobalEntries != null) && (myGlobalEntries.length > 0)) {
		String[] newEntries = ArrayUtil.append(entries, myGlobalEntries);

		entries = newEntries;
	}

	// Merge my community tags

	PortletPreferences myCommunityPrefs = PortletPreferencesFactoryUtil.getPortletPreferences(request, PortletKeys.MY_COMMUNITY_TAGS);

	String[] myCommunityEntries = myCommunityPrefs.getValues("entries", new String[0]);

	if ((myCommunityEntries != null) && (myCommunityEntries.length > 0)) {
		String[] newEntries = ArrayUtil.append(entries, myCommunityEntries);

		entries = newEntries;
	}
}

entries = ArrayUtil.distinct(entries, new StringComparator());
%>

<%@ include file="/html/portlet/tagged_content/add_asset.jspf" %>

<%
if (showQueryLogic) {
	StringMaker tagsText = new StringMaker();

	if (entries.length > 0) {
		tagsText.append("( ");
	}

	for (int i = 0; i < entries.length; i++) {
		if ((i + 1) == entries.length) {
			tagsText.append(entries[i]);
			tagsText.append(" )");
		}
		else {
			tagsText.append(entries[i]);

			if (andOperator) {
				tagsText.append(" AND ");
			}
			else {
				tagsText.append(" OR ");
			}
		}
	}

	if (entries.length > 0 && notEntries.length > 0) {
		tagsText.append(" AND NOT ( ");
	}

	for (int i = 0; i < notEntries.length; i++) {
		if ((i + 1) == notEntries.length) {
			tagsText.append(notEntries[i]);
			tagsText.append(" )");
		}
		else {
			tagsText.append(notEntries[i]);
			tagsText.append(" OR ");
		}
	}
%>

	<liferay-ui:message key="tags" />:

	<%= tagsText %>

	<div class="separator"><!-- --></div>

<%
}

// Display content

PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);
%>

<c:choose>
	<c:when test='<%= selectionStyle.equals("dynamic") %>'>

		<%
		long[] entryIds = TagsEntryLocalServiceUtil.getEntryIds(company.getCompanyId(), entries);
		long[] notEntryIds = TagsEntryLocalServiceUtil.getEntryIds(company.getCompanyId(), notEntries);

		Date now = new Date();

		int total = TagsAssetLocalServiceUtil.getAssetsCount(entryIds, notEntryIds, andOperator, now, now);

		searchContainer.setTotal(total);

		List results = TagsAssetLocalServiceUtil.getAssets(entryIds, notEntryIds, andOperator, now, now, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<c:if test="<%= results.size() > 0 %>">
			<br />
		</c:if>

		<%
		for (int i = 0; i < results.size(); i++) {
			TagsAsset asset = (TagsAsset)results.get(i);

			String className = PortalUtil.getClassName(asset.getClassNameId());
			long classPK = asset.getClassPK();

			try {
		%>

				<div>
					<c:choose>
						<c:when test='<%= displayStyle.equals("full-content") %>'>
							<%@ include file="/html/portlet/tagged_content/display_full_content.jspf" %>
						</c:when>
						<c:when test='<%= displayStyle.equals("abstracts") %>'>
							<%@ include file="/html/portlet/tagged_content/display_abstract.jspf" %>
						</c:when>
						<c:otherwise>
							<%= LanguageUtil.format(pageContext, "x-is-not-a-display-type", displayStyle) %>
						</c:otherwise>
					</c:choose>

					<%@ include file="/html/portlet/tagged_content/asset_actions.jspf" %>
				</div>

		<%
			}
			catch (Exception e) {
				_log.error(e.getMessage());
			}
		%>

			<c:if test="<%= (i + 1) < results.size() %>">
				<div class="separator"><!-- --></div>
			</c:if>

		<%
		}
		%>

	</c:when>
	<c:when test='<%= selectionStyle.equals("manual") %>'>

		<%
		int total = manualEntries.length;

		searchContainer.setTotal(total);

		List results = ListUtil.fromArray(manualEntries);

		int end = (manualEntries.length < searchContainer.getEnd()) ? manualEntries.length : searchContainer.getEnd();

		results = results.subList(searchContainer.getStart(), end);

		searchContainer.setResults(results);
		%>

		<c:if test="<%= results.size() > 0 %>">
			<br />
		</c:if>

		<%
		for (int i = 0; i < results.size(); i++) {
			String assetEntry = (String)results.get(i);

			SAXReader reader = new SAXReader();

			Document assetDoc = reader.read(new StringReader(assetEntry));

			Element root = assetDoc.getRootElement();

			long assetId = GetterUtil.getLong(root.element("asset-id").getText());

			TagsAsset asset = TagsAssetLocalServiceUtil.getAsset(assetId);

			String className = PortalUtil.getClassName(asset.getClassNameId());
			long classPK = asset.getClassPK();

			try {
		%>

				<div>
					<c:choose>
						<c:when test='<%= displayStyle.equals("full-content") %>'>
							<%@ include file="/html/portlet/tagged_content/display_full_content.jspf" %>
						</c:when>
						<c:when test='<%= displayStyle.equals("abstracts") %>'>
							<%@ include file="/html/portlet/tagged_content/display_abstract.jspf" %>
						</c:when>
						<c:otherwise>
							<%= LanguageUtil.format(pageContext, "x-is-not-a-display-type", displayStyle) %>
						</c:otherwise>
					</c:choose>

					<%@ include file="/html/portlet/tagged_content/asset_actions.jspf" %>
				</div>

		<%
			}
			catch (Exception e) {
				_log.error(e.getMessage());
			}
		%>

			<c:if test="<%= (i + 1) < results.size() %>">
				<div class="separator"><!-- --></div>
			</c:if>

		<%
		}
		%>

	</c:when>
</c:choose>

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.tagged_content.view.jsp");
%>