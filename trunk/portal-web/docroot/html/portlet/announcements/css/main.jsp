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

.portlet-alerts {
	.entry {
		border: 6px solid #c00;
		margin: 8px 0px;
		padding: 6px;
	}

	.entry-content {
		display: block;
		padding: 2px 2px 2px 30px;
	}

	.entry-scope {
		color: #ccc;
		display: block;
	}

	.entry-type-general {
		background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
		clear: both;
	}

	.entry-type-news {
		background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
		clear: both;
	}

	.entry-type-test {
		background: #fff url('<%= themeImagesPath %>/common/page.png') left top no-repeat;
		clear: both;
	}
}



.portlet-announcements {
	.entry {
		margin: 4px 0px 1.2em;
		padding-bottom: 0.5em;
	}

	.entry.last {
	}

	.entry-content {
		display: block;
		margin-bottom: 0.5em;
		padding: 2px 2px 2px 30px;
	}

	.entry-scope {
		color: #555;
		display: block;
	}

	.delete-entry {
		padding-right: 2em;
	}

	.entry-type-general {
		background: #fff url('<%= themeImagesPath %>/common/all_pages.png') 4px 4px no-repeat;
		clear: both;
	}

	.entry-type-news {
		background: #fff url('<%= themeImagesPath %>/common/page.png') 4px 4px no-repeat;
		clear: both;
	}

	.entry-type-test {
		background: #fff url('<%= themeImagesPath %>/common/page.png') 4px 4px no-repeat;
		clear: both;
	}
}

.portlet-announcements, .portlet-alerts {
	.important {
		font-weight: normal;
	}

	.entry-title {
		display: block;
		margin-bottom: 0;
		position: relative;
	}

	.read-entries .entry-title {
		opacity: 0.5;
	}

	.edit-actions {
		float: right;
		font-size: 0.7em;
		font-weight: normal;
		right: 0;
		top: 0;
	}
}