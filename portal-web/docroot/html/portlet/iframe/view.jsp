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

<%@ include file="/html/portlet/iframe/init.jsp" %>

<%
String iframeSrc = StringPool.BLANK;

if (relative) {
	iframeSrc = themeDisplay.getPathContext();
}

iframeSrc += (String)request.getAttribute(WebKeys.IFRAME_SRC);

if (Validator.isNotNull(iframeVariables)) {
	StringBuilder sb = new StringBuilder();

	sb.append(iframeSrc);

	if (iframeSrc.indexOf(StringPool.QUESTION) != -1) {
		sb.append(StringPool.AMPERSAND);
	}
	else {
		sb.append(StringPool.QUESTION);
	}

	sb.append(StringUtil.merge(iframeVariables, StringPool.AMPERSAND));

	iframeSrc = sb.toString();
}

String baseSrc = iframeSrc;

int lastSlashPos = iframeSrc.substring(7).lastIndexOf(StringPool.SLASH);

if (lastSlashPos != -1) {
	baseSrc = iframeSrc.substring(0, lastSlashPos + 8);
}

String iframeHeight = heightNormal;

if (windowState.equals(WindowState.MAXIMIZED)) {
	iframeHeight = heightMaximized;
}
%>

<script type="text/javascript">
	function <portlet:namespace />init() {
		var hash = document.location.hash;

		if ((hash != '#') && (hash != '')) {
			var src = '';

			var path = hash.substring(1);

			if (path.indexOf('http://') != 0) {
				src = '<%= baseSrc %>';
			}

			src += path;

			var iframe = AUI().one('#<portlet:namespace />iframe');

			if (iframe) {
				iframe.attr('src', src);
			}
		}
	}

	function <portlet:namespace />maximizeIframe(iframe) {
		var winHeight = 0;

		if (typeof(window.innerWidth) == 'number') {

			// Non-IE

			winHeight = window.innerHeight;
		}
		else if ((document.documentElement) &&
				 (document.documentElement.clientWidth || document.documentElement.clientHeight)) {

			// IE 6+

			winHeight = document.documentElement.clientHeight;
		}
		else if ((document.body) &&
				 (document.body.clientWidth || document.body.clientHeight)) {

			// IE 4 compatible

			winHeight = document.body.clientHeight;
		}

		// The value 139 here is derived (tab_height * num_tab_levels) +
		// height_of_banner + bottom_spacer. 139 just happend to work in
		// this instance in IE and Firefox at the time.

		iframe.height = (winHeight - 139);
	}

	function <portlet:namespace />monitorIframe() {
		var url = null;

		try {
			var iframe = document.getElementById('<portlet:namespace />iframe');

			url = iframe.contentWindow.document.location.href;
		}
		catch (e) {
			return true;
		}

		var baseSrc = '<%= baseSrc %>';
		var iframeSrc = '<%= iframeSrc %>';

		if ((url == iframeSrc) || (url == iframeSrc + '/')) {
		}
		else if (Liferay.Util.startsWith(url, baseSrc)) {
			url = url.substring(baseSrc.length);

			<portlet:namespace />updateHash(url);
		}
		else {
			<portlet:namespace />updateHash(url);
		}

		return true;
	}

	function <portlet:namespace />resizeIframe() {
		var iframe = document.getElementById('<portlet:namespace />iframe');

		var height = null;

		try {
			height = iframe.contentWindow.document.body.scrollHeight;
		}
		catch (e) {
			if (themeDisplay.isStateMaximized()) {
				<portlet:namespace />maximizeIframe(iframe);
			}
			else {
				iframe.height = <%= heightNormal %>;
			}

			return true;
		}

		iframe.height = height + 50;

		return true;
	}

	function <portlet:namespace />updateHash(url) {
		document.location.hash = url;

		var maximize = AUI().one('#p_p_id<portlet:namespace /> .portlet-maximize-icon a');

		if (maximize) {
			var href = maximize.attr('href');

			if (href.indexOf('#') != -1) {
				href = href.substring(0, href.indexOf('#'));
			}

			maximize.attr('href', href + '#' + url);
		}

		var restore = AUI().one('#p_p_id<portlet:namespace /> a.portlet-icon-back');

		if (restore) {
			var href = restore.attr('href');

			if (href.indexOf('#') != -1) {
				href = href.substring(0, href.indexOf('#'));
			}

			restore.attr('href', href + '#' + url);
		}
	}

	AUI().ready(
		function() {
			<portlet:namespace />init();
		}
	);
</script>

<c:choose>
	<c:when test="<%= auth && Validator.isNull(userName) && !themeDisplay.isSignedIn() %>">
		<div class="portlet-msg-info">
			<a href="<%= themeDisplay.getURLSignIn() %>" target="_top"><liferay-ui:message key="please-sign-in-to-access-this-application" /></a>
		</div>
	</c:when>
	<c:otherwise>
		<div>
			<iframe alt="<%= alt %>" border="<%= border %>" bordercolor="<%= bordercolor %>" frameborder="<%= frameborder %>" height="<%= iframeHeight %>" hspace="<%= hspace %>" id="<portlet:namespace />iframe" longdesc="<%= longdesc%>" name="<portlet:namespace />iframe" onload="<portlet:namespace />monitorIframe(); <portlet:namespace />resizeIframe();" scrolling="<%= scrolling %>" src="<%= iframeSrc %>" vspace="<%= vspace %>" width="<%= width %>">
				<%= LanguageUtil.format(pageContext, "your-browser-does-not-support-inline-frames-or-is-currently-configured-not-to-display-inline-frames.-content-can-be-viewed-at-actual-source-page-x", iframeSrc) %>
			</iframe>
		</div>
	</c:otherwise>
</c:choose>