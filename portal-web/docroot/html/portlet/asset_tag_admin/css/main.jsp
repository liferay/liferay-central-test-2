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

.portlet-asset-tag-admin {
	.tags-admin-actions {
		clear: none;
		float: right;
		margin: 0;
	}

	.tags-admin-container .results-header {
		background: #F0F5F7;
		font-weight: bold;
		margin: 2px 0;
		padding: 5px 10px;
		position: relative;
	}

	.tags-admin-toolbar {
		background: #F6F8FB;
		border-bottom: 1px solid #dedede;
		overflow: hidden;
		padding: 5px 0;
	}

	.tags-admin-search {
		.aui-field-content {
			display: inline-block;
		}

		.aui-field-input {
			background-image: url(<%= themeImagesPath %>/common/search.png);
			background-repeat: no-repeat;
			background-position: 5px 50%;
			padding-left: 25px;
			width: 250px;
		}
	}

	.tags-admin-content-wrapper {
		position: relative;
	}

	.tags-admin-content-wrapper, .header-title, .aui-tree-node-content .aui-tree-label  {
		word-wrap: break-word;
	}

	.tags-admin-edit-tag {
		.tag-view-container {
			padding: 0 0.5em 0 0.5em;
		}

		.results-header {
			background: #AEB9BE;
		}
	}

	.tag-item a {
		display: block;
		padding: 8px 20px 8px 10px;
		text-decoration: none;
	}

	.tag-item-actions-trigger {
		background-image: url(<%= themeImagesPath %>/common/edit.png);
		background-repeat: no-repeat;
		clip: rect(0pt, 0pt, 0pt, 0pt);
		display: block;
		height: 16px;
		margin-top: -8px;
		padding: 0;
		position: absolute;
		right: 0;
		top: 50%;
		width: 16px;
	}

	.tags-admin-content {
		li.tag-item-container {
			padding: 1px 0;
		}

		li.tag-item-container, li.tag-item {
			font-weight: bold;
			list-style: none;
		}
	}

	.tag-item-container {
		&:hover, &:focus, &:focus {
			.tag-item-actions-trigger {
				clip: auto;
			}
		}
	}

	.tags-admin-list {
		li {
			border: 1px solid transparent;
		}

		.selected {
			a:hover {
				color: #FFB683;
			}

			a, .tags-admin-content-wrapper, .tags-admin-content-wrapper:hover {
				background-color: #6F7D83;
				color: #FFF;
			}
		}

		.active-area {
			border: 1px solid #008000;

			a {
				background-color: #90EE90;
				color: #90EE90;
			}
		}

		.tags-admin-content-wrapper {
			background-color: #F5F5F5;

			&:hover {
				background: #D3DADD;
			}
		}
	}

	.tags-admin-list-container .results-header {
		background: #D3DADD;
	}

	.tags-admin-search-bar {
		float: left;
	}

	.view-tag {
		margin: 1em;

		label {
			display: block;
			font-weight: bold;
		}

		.tag-field {
			clear: left;
			margin: 1em auto;
		}
	}
}

.tag-item-merge {
	border: 1px solid #4E4848;
	display: block;
	font-weight: bold;
	list-style: none;
	padding: 8px 20px 8px 10px;

	a {
		text-decoration: none;
	}
}

.portlet-asset-tag-admin-dialog {
	.aui-fieldset {
		margin-bottom: 0;
	}

	.lfr-panel-container {
		background-color: transparent;
		border-width: 0;
	}

	.asset-tag-layer .aui-overlay {
		overflow: visible;
		width: 230px;
	}

	.aui-widget-bd {
		.asset-tag-layer {
			padding: 0 10px;

			.aui-field-content {
				margin-bottom: 10px;
			}

			label {
				display: block;
				font-weight: bold;
			}

			.aui-field {
				input, select {
					width: 200px;
				}
			}

			.button-holder {
				margin-top: 10px;
			}
		}
	}
}

.lfr-position-helper {
	z-index: 10000;
}

.ie {
	.portlet-asset-tag-admin {
		.tag-item a {
			zoom: 1;
		}
	}
}

.ie6, .ie7 {
	.portlet-asset-tag-admin {
		.tags-admin-content-wrapper {
			width: 100%;
		}

		.tag-item-actions-trigger {
			cursor: pointer;
		}
	}

	.portlet-asset-tag-admin-dialog {
		.aui-widget-bd .asset-tag-layer .aui-field {
			width: 200px;
		}
	}
}

.ie6, .ie7, .ie8 {
	.portlet-asset-tag-admin {
		.tag-item-merge {
			font-weight: normal;
			font-size: 120%;
		}
	}
}