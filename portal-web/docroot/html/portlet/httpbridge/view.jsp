<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/httpbridge/init.jsp" %>

<%
ServletContext httpBridgeCtx = pageContext.getServletContext().getContext("/httpbridge");

RequestDispatcher httpBridgeRd = httpBridgeCtx.getRequestDispatcher(TaskController.bridgeHttpServicePath);

HttpServletRequest portalRequest = (HttpServletRequest)((HttpServletRequestWrapper)request).getRequest();

String queryString = portalRequest.getQueryString();

if ((queryString == null) || (queryString.indexOf(TaskController.bridgeGotoTag) == -1)) {
	queryString = TaskController.bridgeGotoTag + src;
}
else {
	queryString = queryString.substring(queryString.indexOf(TaskController.bridgeGotoTag), queryString.length());
}

HttpBridgeServletRequest httpBridgeReq = new HttpBridgeServletRequest(portalRequest, queryString);
StringServletResponse httpBridgeRes = new StringServletResponse(new HttpBridgeServletResponse(response));

httpBridgeRd.include(httpBridgeReq, httpBridgeRes);

String requestURI = httpBridgeReq.getRequestURI() + StringPool.QUESTION;

PortletURLImpl portletURLImpl = (PortletURLImpl)renderResponse.createRenderURL();

portletURLImpl.setAnchor(false);

String portletURLImplToString = portletURLImpl.toString();

String httpBridgeURL = portletURLImplToString.substring(0, portletURLImplToString.indexOf(themeDisplay.getPathMain())) + "/httpbridge/http?";
String httpBridgePortletURL = portletURLImplToString.substring(portletURLImplToString.indexOf(themeDisplay.getPathMain()), portletURLImplToString.length());

String httpBridgeOutput = httpBridgeRes.getString();

// Make all URLs point to the HttpBridge portlet

httpBridgeOutput = StringUtil.replace(httpBridgeOutput, requestURI, httpBridgePortletURL);
httpBridgeOutput = StringUtil.replace(httpBridgeOutput, "/httpbridge/http?", httpBridgePortletURL);

// Make all images point to the HttpBridge servlet

HTMLParser parser = new HTMLParser(new StringReader(httpBridgeOutput));

List images = parser.getImages();

for (int i = 0; i < images.size(); i++) {
	String imageURL = (String)images.get(i);

	int pos = imageURL.indexOf(TaskController.bridgeGotoTag);

	if (pos != -1) {
		String gotoImageURL = imageURL.substring(pos, imageURL.length());

		httpBridgeOutput = StringUtil.replace(httpBridgeOutput, imageURL, httpBridgeURL + gotoImageURL);
	}
}
%>

<%= httpBridgeOutput %>