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
PortletURL portletURL = renderResponse.createRenderURL();
String redirect = portletURL.toString();

long assetId = ParamUtil.getLong(renderRequest, "assetId", 0);	
System.out.println("assetId: " + assetId);
TagsAsset asset = null;

if (assetId != 0) {
	asset = TagsAssetLocalServiceUtil.getAsset(assetId);
}

if (asset != null) {
	String className = PortalUtil.getClassName(asset.getClassNameId());
	long classPK = asset.getClassPK();

	System.out.println("classPK: " + assetId);
	try {
%>

		<div align="right">
			&laquo; <a href="<%= redirect %>"><liferay-ui:message key="back" /></a>
		</div>
		<div>
			<%@ include file="/html/portlet/tagged_content/display_full_content.jspf" %>
		</div>

<%
	}
	catch (Exception e) {
	}
}
%>
