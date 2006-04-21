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

<%@ include file="/html/taglib/init.jsp" %>

<%
String formName = namespace + request.getAttribute("liferay-ui:page-iterator:formName");
String curParam = (String)request.getAttribute("liferay-ui:page-iterator:curParam");
int curValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:curValue"));
int delta = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:delta"));
String jsCall = (String)request.getAttribute("liferay-ui:page-iterator:jsCall");
int maxPages = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:maxPages"));
String target = (String)request.getAttribute("liferay-ui:page-iterator:target");
int total = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:total"));
String url = (String)request.getAttribute("liferay-ui:page-iterator:url");
String urlAnchor = (String)request.getAttribute("liferay-ui:page-iterator:urlAnchor");
int pages = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:pages"));
%>

<c:choose>
	<c:when test="<%= curValue == 1 %>">
		<span class="portlet-msg-error">&laquo;&laquo;</span>
	</c:when>
	<c:otherwise>
		<a class="portlet-msg-error" href="<%= _getHREF(formName, curParam, curValue - 1, jsCall, url, urlAnchor) %>" target="<%= target %>">&laquo;&laquo;</a>
	</c:otherwise>
</c:choose>

<%
int pageStart = 1;
int pageEnd = maxPages;

if (curValue > 1) {
	pageStart = curValue - maxPages + 1;
	pageEnd = curValue + maxPages - 1;
}

if (pageStart < 1) {
	pageStart = 1;
}

int divisions = total / delta;

if (total % delta > 0) {
	divisions++;
}

if (pageEnd > divisions) {
	pageEnd = divisions;
}

for (int i = pageStart; i <= pageEnd; i++) {
	out.print("&nbsp;");
%>

	<c:choose>
		<c:when test="<%= curValue == i %>">
			[<%= i %>]
		</c:when>
		<c:otherwise>
			<a href="<%= _getHREF(formName, curParam, i, jsCall, url, urlAnchor) %>" target="<%= target %>"><u><%= i %></u></a>
		</c:otherwise>
	</c:choose>

<%
	if (i == pageEnd) {
		out.print("&nbsp;");
	}
}
%>

<c:choose>
	<c:when test="<%= curValue == pages %>">
		<span class="portlet-msg-error">&raquo;&raquo;</span>
	</c:when>
	<c:otherwise>
		<a class="portlet-msg-error" href="<%= _getHREF(formName, curParam, curValue + 1, jsCall, url, urlAnchor) %>" target="<%= target %>">&raquo;&raquo;</a>
	</c:otherwise>
</c:choose>

<%!
private String _getHREF(String formName, String curParam, int curValue, String jsCall, String url, String urlAnchor) throws Exception {
	String href = null;

	if (Validator.isNotNull(url)) {
		href = url + curParam + "=" + curValue + urlAnchor;
	}
	else {
		href = "javascript: document." + formName + "." + curParam + ".value = '" + curValue + "'; " + jsCall;
	}

	return href;
}
%>