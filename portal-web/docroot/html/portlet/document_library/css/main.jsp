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

.portlet-document-library, .portlet-document-library-display {
	.file-entry-list-description {
		font-style: italic;
		margin-left: 10px;
	}

	.file-entry-tags {
		margin-top: 5px;
	}

	.document-container, .document-entries-paginator {
		clear: both;
	}

	.edit-document-type {
		h3 {
			margin: 1em 0 0.3em 0;
		}

		.select-metadata-set {
			display: block;
			margin-bottom: 2em;
		}
	}

	.document-container .search-info {
		background-color: #CCECF9;
		min-height: 40px;
		position: relative;

		.keywords {
			float: none;
			font-size: 1.4em;
			font-weight: bold;
			position: absolute;
			top: 7px;
		}

		.close-search {
			position: absolute;
			right: 20px;
			top: 10px;
		}
	}

	.move-list {
		.move-file {
			background: #f0faf0 url(<%= themeImagesPath %>/file_system/small/jpg.png) no-repeat 5px 50%;
			border-bottom: 1px solid #ccc;
			display: block;
			font-weight: bold;
			margin-bottom: 1px;
			padding: 5px;
			padding-left: 25px;
			position: relative;
		}

		.move-error {
			background-color: #FDD;
			background-image: url(<%= themeImagesPath %>/messages/error.png);
			font-weight: normal;
			opacity: 0.6;

			.error-message {
				position: absolute;
				right: 5px;
			}
		}

		.lfr-component {
			margin: 0;
		}
	}

	.move-list-info {
		margin: 5px 0;

		h4 {
			font-size: 1.3em;
		}
	}

	.select-documents {
		float: left;
		margin-right: 2em;

		.yui3-aui-field-element {
			border-width: 0;
			display: block;
			text-align: center;
			min-height: 22px;
			min-width: 20px;
		}

		input {
			float: none;
			margin: 0;
		}
	}

	.folder {
		position: relative;

		&:hover {
			background-color: #ccecf9;
		}
	}

	.expand-folder {
		float: right;
	}

	.folder-search {
		float: right;
		margin: 0 0 0.5em 0.5em;
	}

	img.shortcut-icon {
		display: inline;
		margin-left: 10px;
		margin-top: 75px;
		position: absolute;
		z-index: 10;
	}

	.document-display-style {
		a {
			color: #333333;
		}

		&.descriptive {
			display: block;
			height: 140px;
			margin: 5px;
			padding-bottom: 5px;
			padding-top: 5px;
			position: relative;

			.document-title {
				display: block;
				font-weight: bold;
			}

			.document-description {
				display: block;
			}

			.document-action {
				height: 20px;
				overflow: hidden;
				position: absolute;
				right: 6px;
				top: 10px;
			}

			.document-selector {
				bottom: 10px;
				margin-left: -20px;
				position: absolute;
				right: 10px;
			}

			.document-thumbnail {
				float: left;
				margin: 5px 10px;
				text-align: center;
			}

			.document-link {
				display: block;
				min-height: 150px;
				text-decoration: none;
			}

			img.locked-icon {
				bottom: 10px;
				left: 90px;
				position: absolute;
			}

			&:hover .document-selector, &.selected .document-selector {
				clip: auto;
				margin-left: -20px;
				position: absolute;
			}

			&.selected, .selected:hover {
				background-color: #00a2ea;
			}
		}

		&.icon {
			float: left;
			height: 170px;
			margin: 5px;
			padding-bottom: 5px;
			padding-top: 5px;
			position: relative;
			width: 200px;

			.document-action {
				overflow: hidden;
				position: absolute;
				right: 10px;
			}

			.document-selector {
				left: 10px;
				position: absolute;
				top: 10px;
			}

			.document-thumbnail {
				text-align: center;
			}

			.document-title {
				clear: both;
				display: block;
			}

			img.locked-icon {
				margin: 80px 45px;
				position: absolute;
			}

			&.selected, &.selected:hover {
				background-color: #00a2ea;
			}

			.document-selector, &:hover .document-selector, &.selected .document-selector {
				position: absolute;
			}
		}

		.document-action .direction-down {
			height: 20px;
		}

		&.selected a {
			color: #ffffff;
		}

		.document-link {
			display: block;
			min-height: 180px;
			text-align: center;
			text-decoration: none;
		}

		.overlay.document-action a {
			display: block;
			float: right;
			min-height: 20px;
		}

		&:hover, &.hover {
			background-color: #ccecf9;
		}

		&:hover .overlay, &.hover .overlay, &.selected .document-selector {
			clip: auto;
		}
	}

	.body-row li.selected {
		background-color: #00a2ea;

		a {
			color: #ffffff;
		}
	}

	.header-row {
		.yui3-aui-icon-display-icon {
			background: url(<%= themeImagesPath%>/document_library/layouts.png) no-repeat -35px 0;
		}

		.yui3-aui-icon-display-descriptive {
			background: url(<%= themeImagesPath%>/document_library/layouts.png) no-repeat -65px 0;
		}

		.yui3-aui-icon-display-list {
			background: url(<%= themeImagesPath%>/document_library/layouts.png) no-repeat -4px 0;
		}

		.yui3-aui-icon-edit {
			background: url(<%= themeImagesPath%>/common/edit.png) no-repeat 0 0;
		}

		.yui3-aui-icon-move {
			background: url(<%= themeImagesPath%>/common/submit.png) no-repeat 0 0;
		}

		.yui3-aui-icon-lock {
			background: url(<%= themeImagesPath%>/common/lock.png) no-repeat 0 0;
		}

		.yui3-aui-icon-unlock {
			background: url(<%= themeImagesPath%>/common/unlock.png) no-repeat 0 0;
		}

		.yui3-aui-icon-permissions {
			background: url(<%= themeImagesPath%>/common/permissions.png) no-repeat 0 0;
		}
	}

	.taglib-search-iterator .results-header input {
		display: none;
	}

	.document-display-style .overlay, .lfr-search-container .overlay {
		clip: rect(0 0 0 0);
	}

	.yui3-aui-button-holder.toolbar {
		display: inline;
		margin: 0;
	}

	.body-row {
		height: 100%;
		overflow: hidden;
		position: relative;
		width: 100%;

		.document-info {
			background-color: #CCECF9;
			min-height: 100px;
			padding-top: 0.5em;

			.document-description {
				display: block;
				margin-top: 5px;
			}

			.document-thumbnail {
				display: block;
				float: left;
				margin: 0 5px;

				.thumbnail {
					height: 90px;
				}
			}

			.lfr-asset-categories {
				margin-top: 0.5em;
			}

			.lfr-asset-ratings {
				float: right;
				margin: 0 20px 0 0;

				.taglib-ratings.stars {
					padding-top: 0;
				}

				.yui3-aui-rating-label-element {
					float: left;
				}
			}

			.lfr-asset-tags {
				padding-bottom: 1em;
			}
		}

		.lfr-input-resource {
			width: 250px;
		}

		.lfr-asset-summary {
			margin-left: 5px;

			.download-document, .last-updated, .webdav-url {
				display: block;
				margin: 0.7em 0;
			}

			.version {
				display: block;
				font-size: 1.4em;
				font-weight: bold;
			}
		}

		.user-date {
			font-weight: bold;
		}
	}

	.header-row {
		background-color: #DDD;
		min-height: 34px;
	}

	.header-row-content {
		padding: 0.2em;

		.toolbar, .add-button {
			float: left;
		}

		.toolbar .manage-button {
			display: inline-block;
			margin-left: 30px;
		}

		.display-style {
			float: right;
		}

		.edit-toolbar {
			margin: 0;
		}
	}

	.view {
		border: 1px solid #CCC;

		.view-content {
			background-color: #fafafa;
		}

		.search-button {
			float: right;
		}
	}

	.context-pane {
		border-left: 1px solid #CCC;
	}

	.taglib-webdav {
		margin-top: 3em;
	}

	.taglib-workflow-status {
		margin-bottom: 5px;
	}

	.workflow-status-pending, .workflow-status-pending a {
		color: orange;
	}

	.body-row {
		ul .expand-folder {
			height: 10px;
			position: absolute;
			right: 2px;
			top: 5px;
			width: 16px;

			.expand-folder-arrow {
				left: 2px;
				position: absolute;
				top: 1px;
			}

			&:hover {
				background-color: #FFFFFF;
				height: 10px;
			}
		}

		ul, li {
			list-style: none outside none;
			margin: 0;
			padding: 0;
			width: 95%;
		}

		ul, li a {
			display: block;
			padding: 6px 0 4px 4px;
		}

		ul li a {
			color: #34404F;
			text-decoration: none;
		}

		li.selected {
			background-color: #00a2ea;

			a {
				font-weight: bold;
			}
		}
	}

	.keywords {
		float: right;
		margin-left: 1em;
	}

	.yui3-aui-liferaylistview-data-container {
		background-color: #fafafa;
		height: 100%;
		position: absolute;
		width: 100%;
		z-index: 9999;
	}

	.yui3-aui-liferaylistview-data-container-hidden {
		display: none;
	}
}