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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = PortalUtil.getLayoutURL(layout, themeDisplay) + Portal.FRIENDLY_URL_SEPARATOR + "blogs";
}

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

//entry = entry.toEscapedModel();

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

BlogsEntry[] prevAndNext = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(entryId);

BlogsEntry previousEntry = prevAndNext[0];
BlogsEntry nextEntry = prevAndNext[2];

pageDisplayStyle = RSSUtil.DISPLAY_STYLE_FULL_CONTENT;

AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());

AssetEntryLocalServiceUtil.incrementViewCounter(BlogsEntry.class.getName(), entry.getEntryId());

AssetUtil.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(BlogsEntry.class.getName(), entry.getEntryId()));

request.setAttribute("view_entry_content.jsp-redirect", redirect);

request.setAttribute("view_entry_content.jsp-entry", entry);

request.setAttribute("view_entry_content.jsp-assetEntry", assetEntry);
%>

<portlet:actionURL var="editEntryURL">
	<portlet:param name="struts_action" value="/blogs/edit_entry" />
</portlet:actionURL>

<aui:form action="<%= editEntryURL %>" method="post" name="fm1" onSubmit='<%= renderResponse.getNamespace() + "saveEntry(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="entryId" type="hidden" value="<%= String.valueOf(entryId) %>" />

	<liferay-util:include page="/html/portlet/blogs/view_entry_content.jsp" />
</aui:form>

<div class="entry-navigation">
	<c:choose>
		<c:when test="<%= previousEntry != null %>">
			<portlet:renderURL var="previousEntryURL">
				<portlet:param name="struts_action" value="/blogs/view_entry" />
				<portlet:param name="entryId" value="<%= String.valueOf(previousEntry.getEntryId()) %>" />
			</portlet:renderURL>

			<aui:a cssClass="previous" href="<%= previousEntryURL %>" label="previous" />
		</c:when>
		<c:otherwise>
			<span class="previous"><liferay-ui:message key="previous" /></span>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= nextEntry != null %>">
			<portlet:renderURL var="nextEntryURL">
				<portlet:param name="struts_action" value="/blogs/view_entry" />
				<portlet:param name="entryId" value="<%= String.valueOf(nextEntry.getEntryId()) %>" />
			</portlet:renderURL>"

			<aui:a cssClass="next" href="<%= nextEntryURL %>" label="next" />
		</c:when>
		<c:otherwise>
			<span class="next"><liferay-ui:message key="next" /></span>
		</c:otherwise>
	</c:choose>
</div>

<c:if test="<%= enableComments %>">
	<br />

	<liferay-ui:tabs names="comments" />

	<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED && entry.isAllowTrackbacks() %>">
		<liferay-ui:message key="trackback-url" />:

		<liferay-ui:input-resource
			url='<%= PortalUtil.getLayoutFullURL(themeDisplay) + Portal.FRIENDLY_URL_SEPARATOR + "blogs/trackback/" + entry.getUrlTitle() %>'
		/>

		<br /><br />
	</c:if>

	<portlet:actionURL var="discussionURL">
		<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
	</portlet:actionURL>

	<liferay-ui:discussion
		formName="fm2"
		formAction="<%= discussionURL %>"
		className="<%= BlogsEntry.class.getName() %>"
		classPK="<%= entry.getEntryId() %>"
		userId="<%= entry.getUserId() %>"
		subject="<%= entry.getTitle() %>"
		redirect="<%= currentURL %>"
		ratingsEnabled="<%= enableCommentRatings %>"
	/>
</c:if>

<%
PortalUtil.setPageSubtitle(entry.getTitle(), request);

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(BlogsEntry.class.getName(), entry.getEntryId());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, "name"), request);

PortalUtil.addPortletBreadcrumbEntry(request, entry.getTitle(), currentURL);
%>