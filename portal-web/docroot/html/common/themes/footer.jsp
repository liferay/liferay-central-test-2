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

<%@ include file="/html/common/init.jsp" %>

<%
List<Portlet> portlets = (List<Portlet>)request.getAttribute("portlets");

Set<String> headerPortletJavaScriptPaths = new LinkedHashSet<String>();

for (Portlet portlet : portlets) {
	List<String> headerPortletJavaScriptList = portlet.getHeaderPortletJavaScript();

	for (String headerPortletJavaScript : headerPortletJavaScriptList) {
		String headerPortletJavaScriptPath = portlet.getContextPath() + headerPortletJavaScript;

		if (!headerPortletJavaScriptPaths.contains(headerPortletJavaScriptPath)) {
			if (!PropsValues.JAVASCRIPT_FAST_LOAD && headerPortletJavaScriptPath.endsWith("packed.js")) {
				StringBuilder sb = new StringBuilder();

				sb.append(headerPortletJavaScriptPath.substring(0, headerPortletJavaScriptPath.length() - 9));
				sb.append("unpacked.js");

				headerPortletJavaScriptPath = sb.toString();
			}

			headerPortletJavaScriptPaths.add(headerPortletJavaScriptPath);
%>

			<script src="<%= headerPortletJavaScriptPath %>?t=<%= portlet.getTimestamp() %>" type="text/javascript"></script>

<%
		}
	}
}
%>

<c:if test="<%= themeDisplay.isIncludePortletCssJs() %>">
	<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/portlet_css_packed.js?bn=<%= ReleaseInfo.getBuildNumber() %>" type="text/javascript"></script>
</c:if>

<liferay-util:include page="/html/common/themes/footer-ext.jsp" />