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

.portlet-quick-note {
	margin: 2px;
	padding: 5px;

	textarea {
		min-height: 100px;
		padding: 3px;
		width: 95%;
	}

	.note-color {
		border: 1px solid;
		cursor: pointer;
		float: left;
		font-size: 0;
		height: 10px;
		margin: 3px 5px;
		width: 10px;

		&.yellow {
			background-color: #FFC;
			border-color: #FC0;
			margin-left: 0;
		}

		&.green {
			background-color: #CFC;
			border-color: #0C0;
		}

		&.blue {
			background-color: #CCF;
			border-color: #309;
		}

		&.red {
			background-color: #FCC;
			border-color: #F00;
		}
	}

	a.close-note {
		float: right;
	}
}

.ie6 {
	.portlet-quick-note {
		textarea {
			height: expression(this.height < 100 ? '100px' : this.height);
		}
	}
}