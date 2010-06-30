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

<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="javax.portlet.PortletURL" %>

<portlet:defineObjects/>

<%
String appURL = (String)request.getParameter("appURL");
if (Validator.isNull(appURL)) {
	appURL = request.getContextPath();
}

String defaultHeight = (String)renderRequest.getAttribute("wai.connector.iframe.height.default");
%>

<div id="<portlet:namespace />iframe_div">
	<iframe id="<portlet:namespace />iframe" frameborder="0" height="<%= defaultHeight %>" width="100%" src="<%= appURL %>"></iframe>
</div>
<div id="<portlet:namespace />bookmark_div"><a href="#">Permanent link</a></div>
<script>
AUI().use('aui-base', function(A) {
	var iframe = A.one('#<portlet:namespace />iframe');

	var getURL = function() {
		var location = iframe.get('contentWindow.document.location');
		if (location) {
			return location.pathname + location.search;
		}
		return null;
	};

	var bookmarkLink = A.one('#<portlet:namespace />bookmark_div a');

	var getHeight = function() {
		var body = iframe.get('contentWindow.document.body');

		if (body) {
			var max = 0;

			// The scrollHeight of the body does not always account for every
			// element. One solution is to manually check the position of the
			// bottom edge of every div.
			body.all('div').each(function(div) {
				var height = div.getY() + div.get('scrollHeight');
				if (height > max) {
					max = height;
				}
			});

			var scrollHeight = body.get('scrollHeight');

			if (scrollHeight > max) {
				return scrollHeight;
			} else {
				return max;
			}
		} else {
			return <%= defaultHeight %>;
		}
	}

	var resizeIframe = function() {
		iframe.set('height', getHeight());
	};

<%
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("appURL", "");
%>

	var updateIframe = function() {
		bookmarkLink.set('href', '<%= portletURL.toString() %>' + escape(getURL()));
		resizeIframe();
	};

	iframe.on('load', updateIframe);

	updateIframe();
});
</script>