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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List results = (List)request.getAttribute("view.jsp-results");

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory assetRendererFactory = (AssetRendererFactory)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute("view.jsp-assetRenderer");

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle();
}

boolean show = ((Boolean)request.getAttribute("view.jsp-show")).booleanValue();

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
viewFullContentURL.setParameter("type", assetRendererFactory.getType());

if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
	viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
}

String viewURL = viewInContext ? assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, viewFullContentURL.toString()) : viewFullContentURL.toString();

viewURL = _checkViewURL(viewURL, currentURL, themeDisplay);
%>

<c:if test="<%= assetEntryIndex == 0 %>">
	<table class="taglib-search-iterator">
	<tr class="portlet-section-header results-header">
		<th>
			<liferay-ui:message key="title" />
		</th>

		<%
		for (int m = 0; m < metadataFields.length; m++) {
		%>
			<th>
				<liferay-ui:message key="<%= metadataFields[m] %>" />
			</th>
		<%
		}
		%>

		<th></th>
	</tr>
</c:if>

<c:if test="<%= show %>">

	<%
	String style = "class=\"portlet-section-body results-row\" onmouseover=\"this.className = 'portlet-section-body-hover results-row hover';\" onmouseout=\"this.className = 'portlet-section-body results-row';\"";

	if (assetEntryIndex % 2 == 0) {
		style = "class=\"portlet-section-alternate results-row alt\" onmouseover=\"this.className = 'portlet-section-alternate-hover results-row alt hover';\" onmouseout=\"this.className = 'portlet-section-alternate results-row alt';\"";
	}
	%>

	<tr <%= style %>>
		<td>
			<c:choose>
				<c:when test="<%= Validator.isNotNull(viewURL) %>">
					<a href="<%= viewURL %>"><%= title %></a>
				</c:when>
				<c:otherwise>
					<%= title %>
				</c:otherwise>
			</c:choose>
		</td>

		<%
		for (int m = 0; m < metadataFields.length; m++) {
			String value = null;

			if (metadataFields[m].equals("create-date")) {
				value = dateFormatDate.format(assetEntry.getCreateDate());
			}
			else if (metadataFields[m].equals("modified-date")) {
				value = dateFormatDate.format(assetEntry.getModifiedDate());
			}
			else if (metadataFields[m].equals("publish-date")) {
				if (assetEntry.getPublishDate() == null) {
					value = StringPool.BLANK;
				}
				else {
					value = dateFormatDate.format(assetEntry.getPublishDate());
				}
			}
			else if (metadataFields[m].equals("expiration-date")) {
				if (assetEntry.getExpirationDate() == null) {
					value = StringPool.BLANK;
				}
				else {
					value = dateFormatDate.format(assetEntry.getExpirationDate());
				}
			}
			else if (metadataFields[m].equals("priority")) {
				value = String.valueOf(assetEntry.getPriority());
			}
			else if (metadataFields[m].equals("author")) {
				value = assetEntry.getUserName();
			}
			else if (metadataFields[m].equals("view-count")) {
				value = String.valueOf(assetEntry.getViewCount());
			}
			else if (metadataFields[m].equals("categories")) {
			%>

				<td>
					<liferay-ui:asset-categories-summary
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK () %>"
						portletURL="<%= renderResponse.createRenderURL() %>"
					/>
				</td>

			<%
			}
			else if (metadataFields[m].equals("tags")) {
			%>

				<td>
					<liferay-ui:asset-tags-summary
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK () %>"
						portletURL="<%= renderResponse.createRenderURL() %>"
					/>
				</td>

			<%
			}

			if (value != null) {
		%>

				<td>
					<liferay-ui:message key="<%= value %>" />
				</td>

		<%
			}
		}
		%>

		<td></td>
	</tr>
</c:if>

<c:if test="<%= (assetEntryIndex + 1) == results.size() %>">
	</table>
</c:if>