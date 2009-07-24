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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long assetEntryId = ParamUtil.getLong(request, "assetEntryId");
String type = ParamUtil.getString(request, "type");
String urlTitle = ParamUtil.getString(request, "urlTitle");

boolean print = Validator.equals(Constants.PRINT, ParamUtil.getString(request, "viewMode"));

List results = new ArrayList();

int assetEntryIndex = 0;

AssetEntry assetEntry = null;

String className = StringPool.BLANK;
long classPK = 0;

try {
	if (Validator.isNotNull(urlTitle)) {
		if (type.equals (AssetPublisherUtil.TYPE_BLOG)) {
			BlogsEntry entry = BlogsEntryServiceUtil.getEntry(scopeGroupId, urlTitle);

			className = BlogsEntry.class.getName();
			classPK = entry.getPrimaryKey();
		}
		else if (type.equals (AssetPublisherUtil.TYPE_CONTENT)) {
			JournalArticle article = JournalArticleServiceUtil.getArticleByUrlTitle(scopeGroupId, urlTitle);

			className = JournalArticle.class.getName();
			classPK = article.getResourcePrimKey();
		}

		assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);
	}
	else {
		assetEntry = AssetEntryLocalServiceUtil.getEntry(assetEntryId);

		className = PortalUtil.getClassName(assetEntry.getClassNameId());
		classPK = assetEntry.getClassPK();
	}

	String title = assetEntry.getTitle();
	String summary = StringPool.BLANK;
	String viewURL = StringPool.BLANK;
	String viewURLMessage = StringPool.BLANK;
	String editURL = StringPool.BLANK;

	boolean show = true;

	request.setAttribute("view.jsp-results", results);

	request.setAttribute("view.jsp-assetEntryIndex", new Integer(assetEntryIndex));

	request.setAttribute("view.jsp-assetEntry", assetEntry);

	request.setAttribute("view.jsp-title", title);
	request.setAttribute("view.jsp-summary", summary);
	request.setAttribute("view.jsp-viewURL", viewURL);
	request.setAttribute("view.jsp-viewURLMessage", viewURLMessage);

	request.setAttribute("view.jsp-className", className);
	request.setAttribute("view.jsp-classPK", new Long(classPK));

	request.setAttribute("view.jsp-show", new Boolean(show));
	request.setAttribute("view.jsp-print", new Boolean(print));
%>

	<c:if test="<%= !print %>">
		<div align="right">
			&laquo; <a href="<%= HtmlUtil.escape(redirect) %>"><liferay-ui:message key="back" /></a>
		</div>
	</c:if>

	<div>
		<liferay-util:include page="/html/portlet/asset_publisher/display/full_content.jsp" />
	</div>

	<liferay-util:include page="/html/portlet/asset_publisher/asset_html_metadata.jsp" />

<%
}
catch (Exception e) {
	_log.error(e.getMessage());
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.asset_publisher.view_content.jsp");
%>