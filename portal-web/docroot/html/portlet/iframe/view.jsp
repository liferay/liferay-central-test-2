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

		if (hash != '#' && hash != '') {
			var src = '';
			var path = hash.substring(1);

			if (path.indexOf('http://')!=0){
				src = '<%= baseSrc %>';
			}

			src += path;

			jQuery('#<portlet:namespace />iframe').attr('src', src);
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

	function <portlet:namespace />startsWith(str, prefix) {
		if (str.match("^"+prefix) == prefix) {
			return true;
		}
		else {
			return false;
		}
	}

	function <portlet:namespace />monitorIframeUrl() {

		var url;

		try {
			var iframeDocument = document.getElementById('<portlet:namespace />iframe').contentWindow.document;
			url = iframeDocument.location;
		}
		catch (error) {
			return true;
		}

		var appUrl = url.href;

		var baseSrc = '<%= baseSrc %>';
		var iframeSrc = '<%= iframeSrc %>';

		if ((appUrl == iframeSrc) || (appUrl == iframeSrc + '/')) {
		}
		else if (<portlet:namespace />startsWith(appUrl, baseSrc)){
			appUrl = appUrl.substring(baseSrc.length);

			<portlet:namespace />updateHash(appUrl);
		}
		else {
			<portlet:namespace />updateHash(appUrl);
		}

		return true;
	}

	function  <portlet:namespace />updateHash(appUrl) {
		document.location.hash = appUrl;
		var maximize = jQuery('#p_p_id<portlet:namespace /> .portlet-maximize-icon a');

		if (maximize.length != 0) {
			var href = maximize.attr('href');

			if (href.indexOf('#')!= -1){
				href = href.substring(0, href.indexOf('#'));
			}

			maximize.attr('href', href + '#' + appUrl);
		}

		var restore = jQuery('#p_p_id<portlet:namespace /> a.portlet-icon-back');

		if (restore.length != 0) {
			var href = restore.attr('href');

			if (href.indexOf('#')!= -1){
				href = href.substring(0, href.indexOf('#'));
			}

			restore.attr('href', href + '#' + appUrl);
		}

	}

	function <portlet:namespace />resizeIframe() {
		var iframe = document.getElementById('<portlet:namespace />iframe');

		var iframeHeight;

		try {
			iframeHeight = iframe.contentWindow.document.body.scrollHeight;
		}
		catch (error) {
			<portlet:namespace />maximizeIframe(iframe);

			return true;
		}

		var extraHeight = 50;

		document.getElementById('<portlet:namespace />iframe').height = iframeHeight + extraHeight;

		return true;
	}
</script>

<c:choose>
	<c:when test="<%= auth && Validator.isNull(userName) && !themeDisplay.isSignedIn() %>">
		<div class="portlet-msg-info">
			<a href="<%= themeDisplay.getURLSignIn() %>" target="_top"><liferay-ui:message key="please-sign-in-to-access-this-application" /></a>
		</div>
	</c:when>
	<c:otherwise>
		<div id="<portlet:namespace />iframe_div">
			<iframe border="<%= border %>" bordercolor="<%= bordercolor %>" frameborder="<%= frameborder %>" height="<%= iframeHeight %>" hspace="<%= hspace %>" id="<portlet:namespace />iframe" name="<portlet:namespace />iframe" onLoad="<portlet:namespace />monitorIframeUrl(); <portlet:namespace />resizeIframe();" scrolling="<%= scrolling %>" src="<%= iframeSrc %>" vspace="<%= vspace %>" width="<%= width %>"></iframe>
		</div>
	</c:otherwise>
</c:choose>


<script type="text/javascript">
	<portlet:namespace />init();
</script>