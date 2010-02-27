<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects/>

<%
String src = (String)request.getAttribute("appUrl");
%>

<script type="text/javascript">
	function <portlet:namespace />init() {
		if (document.location.hash != '#') {
			document.getElementById('<portlet:namespace />ac_iframe').src = '<%= request.getContextPath() %>/' + document.location.hash.substring(1);
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

	function <portlet:namespace />monitorIframeUrl() {
		var iframeDocument = document.getElementById('<portlet:namespace />ac_iframe').contentWindow.document;

		var url

		try {
			url = iframeDocument.location;
		}
		catch (error) {
			return true;
		}

		var appUrl = url.pathname;

		console.log("monitoring: " + url);

		if (appUrl.indexOf('<%= request.getContextPath() %>') != -1) {
			appUrl = appUrl.substring('<%= request.getContextPath() %>'.length + 1);

			if (iframeDocument.location.search != '?') {
				appUrl += iframeDocument.location.search;
			}

			if ((appUrl == '') || (appUrl == '/')) {
				if (document.location.hash.length != 0) {
					document.location.hash = '/';
				}
			}
			else {
				document.location.hash = appUrl;
			}
		}

		return true;
	}

	function <portlet:namespace />resizeIframe() {
		var iframe = document.getElementById('<portlet:namespace />ac_iframe');

		var iframeHeight;

		try {
			iframeHeight = iframe.contentWindow.document.body.scrollHeight;
		}
		catch (error) {
			<portlet:namespace />maximizeIframe(iframe);

			return true;
		}

		var extraHeight = <%= renderRequest.getAttribute("wai.connector.iframe.height.extra") %>;

		document.getElementById('<portlet:namespace />ac_iframe').height = iframeHeight + extraHeight;

		return true;
	}
</script>

<div id="<portlet:namespace />ac_iframe_div">
	<iframe frameborder="0" height="100%" id="<portlet:namespace />ac_iframe" src="<%= src %>" width="100%" onLoad="<portlet:namespace />monitorIframeUrl(); <portlet:namespace />resizeIframe();"></iframe>
</div>

<script type="text/javascript">
	<portlet:namespace />init();
</script>