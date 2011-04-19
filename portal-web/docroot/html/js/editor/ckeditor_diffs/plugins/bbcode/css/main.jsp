<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/css_init.jsp" %>

blockquote {
	background: #EEF0F2 url(<%= themeImagesPath %>/message_boards/quoteleft.png) left top no-repeat;
	border: 1px solid #777;
	padding: 0.5em;
}

blockquote > p:last-child, blockquote > div:last-child {
	background: url(<%= themeImagesPath %>/message_boards/quoteright.png) right bottom no-repeat;
	padding: 0 1.5em 1.5em 0;
}

pre {
	background: #F9F9F9;
	border: 1px solid #777;
	padding: 0.5em;
}