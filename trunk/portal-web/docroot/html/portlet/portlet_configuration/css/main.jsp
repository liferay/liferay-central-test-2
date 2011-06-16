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

.portlet-configuration {
	.archived-setups {
		margin-bottom: 1em;
		margin-right: 0.5em;
		text-align: right;
	}

	.edit-permissions {
		form:after {
			clear: both;
			content: ".";
			display: block;
			height: 0;
			visibility: hidden;
		}

		.assign-permissions {
			float: left;

			.aui-button-holder {
				position: relative;

				.finished {
					position: absolute;
					right: 0;
				}
			}
		}
	}
}

.ie {
	.portlet-configuration {
		.edit-permissions form {
			height: 1%;
		}
	}
}