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

<%@ include file="/html/portal/init.jsp" %>

<%
int status = ParamUtil.getInteger(request, "status");

if (status > 0) {
	response.setStatus(status);
}

String exception = ParamUtil.getString(request, "exception");

String url = ParamUtil.getString(request, "previousURL");

if (Validator.isNull(url)) {
	url = PortalUtil.getCurrentURL(request);
}

url = themeDisplay.getPortalURL() + url;

boolean noSuchResourceException = false;

for (String key : SessionErrors.keySet(request)) {
	key = key.substring(key.lastIndexOf(StringPool.PERIOD) + 1);

	if (key.startsWith("NoSuch") && key.endsWith("Exception")) {
		noSuchResourceException = true;
	}
}

if (Validator.isNotNull(exception)) {
	exception = exception.substring(exception.lastIndexOf(StringPool.PERIOD) + 1);

	if (exception.startsWith("NoSuch") && exception.endsWith("Exception")) {
		noSuchResourceException = true;
	}
}
%>

<c:choose>
	<c:when test="<%= SessionErrors.contains(request, PrincipalException.class.getName()) %>">
		<h3 class="portlet-msg-error">
			<liferay-ui:message key="forbidden" />
		</h3>

		<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />

		<br /><br />

		<code><%= HtmlUtil.escape(url) %></code>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, PortalException.class.getName()) || SessionErrors.contains(request, SystemException.class.getName()) %>">
		<h3 class="portlet-msg-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

		<br /><br />

		<code><%= HtmlUtil.escape(url) %></code>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, TransformException.class.getName()) %>">
		<h3 class="portlet-msg-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message key="an-error-occurred-while-processing-the-requested-resource" />

		<br /><br />

		<code><%= HtmlUtil.escape(url) %></code>

		<br /><br />

		<%
		TransformException te = (TransformException)SessionErrors.get(request, TransformException.class.getName());
		%>

		<div>
			<%= StringUtil.replace(te.getMessage(), new String[] {"<", "\n"}, new String[] {"&lt;", "<br />\n"}) %>
		</div>
	</c:when>
	<c:when test="<%= noSuchResourceException %>">
		<h3 class="portlet-msg-error">
			<liferay-ui:message key="not-found" />
		</h3>

		<liferay-ui:message key="the-requested-resource-was-not-found" />

		<br /><br />

		<code><%= HtmlUtil.escape(url) %></code>
	</c:when>
	<c:otherwise>
		<h3 class="portlet-msg-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

		<br /><br />

		<code><%= HtmlUtil.escape(url) %></code>

		<%
		for (String key : SessionErrors.keySet(request)) {
			Object value = SessionErrors.get(request, key);

			if (value instanceof Exception) {
				Exception e = (Exception)value;

				_log.error(e, e);
			}
		}
		%>

	</c:otherwise>
</c:choose>

<div class="separator"><!-- --></div>

<a href="javascript:history.go(-1);">&laquo; <liferay-ui:message key="back" /></a>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portal.status.jsp");
%>