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
boolean showIconLabel = ((Boolean)request.getAttribute("view.jsp-showIconLabel")).booleanValue();

AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute("view.jsp-assetRenderer");

PortletURL editPortletURL = assetRenderer.getURLEdit((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse);

if (editPortletURL != null) {
	editPortletURL.setPortletMode(PortletMode.VIEW);

	editPortletURL.setParameter("redirect", currentURL);
}

Group stageableGroup = themeDisplay.getScopeGroup();

if (themeDisplay.getScopeGroup().isLayout()) {
	stageableGroup = layout.getGroup();
}
%>

<c:if test="<%= (editPortletURL != null) && !stageableGroup.hasStagingGroup() %>">
	<div class="lfr-meta-actions asset-actions">
		<liferay-ui:icon
			image="edit"
			label="<%= showIconLabel %>"
			message='<%= LanguageUtil.format(pageContext, "edit-x-x", new Object[] {"aui-helper-hidden-accessible", assetRenderer.getTitle()}) %>'
			url="<%= editPortletURL.toString() %>"
		/>
	</div>
</c:if>