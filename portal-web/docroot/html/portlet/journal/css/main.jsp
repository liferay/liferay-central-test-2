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

.journal-article-component-list {
	margin: 10px 0 0 0;
}

.portlet-journal {
	.add-permission-button-row {
		float: left;
	}

	.article-separator {
		clear: both;
	}

	.lfr-table .article-translation-toolbar {
		background: #DFF4FF url(<%= themeImagesPath %>/journal/language.png) no-repeat 6px 50%;
		border: 1px solid #A7CEDF;
		color: #34404F;
		display: block;
		margin: 2px auto 14px;
		padding: 6px 6px 6px 30px;

		label {
			font-weight: bold;
		}

		.add-translations-menu {
			float: right;
			margin-left: 3em;
			position: relative;
			top: -3px;
		}

		.contains-translations {
			margin-top: 1em;
		}

		.journal-article-default-translation {
			background-color: #B3DAEF;
			font-weight: bold;
			padding: 0.3em 0.5em 0.3em;
			margin: 0 0.2em;
		}

		.journal-article-translation {
			background-color: #B3DAEF;
			border: 1px solid #5FA8FF;
			color: #444;
			padding: 0.3em 0.5em 0.3em;
			margin: 0 0.2em;
			text-decoration: none;

			&:hover {
				background-color: #D1E5EF;
			}

			img {
				margin-right: 0.3em;
			}
		}
	}

	.subscribe-link {
		float: right;
		margin-bottom: 1em;
		margin-top: 2em;
	}

	.lfr-panel-basic {
		.lfr-panel-title {
			border-bottom: 1px solid #ccc;
			float: none;
			position: relative;
			top: -0.5em;

			span {
				background: #fff;
				padding: 0 8px 0 4px;
				position: relative;
				top: 0.55em;
			}
		}

		.lfr-panel-content {
			background-color: #F0F5F7;
			padding: 10px;
		}

		.lfr-tag-selector-input {
			width: 100%;
		}
	}

	.journal-extras {
		border-width: 0;

		.lfr-panel {
			margin-bottom: 1em;
		}
	}

	.journal-article-container ul {
		margin: 0;
	}

	li {
		list-style: none;
	}

	.structure-tree {
		position: relative;

		li.structure-field {
			&.repeatable-border {
				background: #F7FAFB;
			}

			&.selected {
				background: #EBFFEE;
				border: 1px #C3E7CC solid;
			}

			&.repeated-field .journal-article-variable-name {
				display: none;
			}
		}

		.placeholder {
			display: none;
		}

		.aui-tree-sub-placeholder {
			margin-top: 10px;
		}

		.folder .field-container .journal-article-required-message {
			display: none;
		}

		.folder .field-container.required-field .journal-article-required-message {
			display: block;
			margin: 0;
		}

		.journal-article-field-label {
			display: block;
			font-weight: bold;
			margin-left: 3px;
			padding-bottom: 5px;
		}

		.journal-subfield input {
			float: left;
		}

		.journal-subfield .journal-article-field-label {
			float: left;
			font-weight: normal;
			padding: 0 0 0 3px;
		}

		.journal-article-move-handler {
			display: none;
		}

		.journal-article-localized {
			font-size: 13px;
			padding-top: 5px;
		}
	}

	.component-group .aui-tree-placeholder {
		display: none;
	}

	.journal-article-localized-checkbox {
		display: block;
		margin-top: 10px;
	}

	 .journal-article-header-edit .journal-article-localized-checkbox {
		margin-bottom: 10px;
	}

	.journal-article-variable-name .aui-field-label {
		font-weight: normal;
		margin-right: 5px;
	}

	.component-group-title {
		font-size: 12px;
		font-weight: bold;
		text-decoration: underline;
		padding: 4px 0 0;
	}

	.journal-article-component-container {
		margin: 3px;
		overflow: hidden;
	}

	.journal-component {
		color: #0E3F6F;
		cursor: move;
		line-height: 25px;
		padding-left: 30px;

		&.dragging {
			font-weight: bold;
		}
	}

	.journal-article-instructions-container {
		display: normal;
	}

	.journal-field-template {
		display: none;
	}

	.journal-fieldmodel-container {
		display: none;
	}

	.journal-icon-button {
		cursor: pointer;
	}

	.portlet-section-header td {
		background: #CFE5FF;
	}

	.journal-form-presentation-label {
		color: #0E3F6F;
		padding-top: 3px;
	}

	.journal-edit-field-control, .journal-list-subfield .journal-icon-button {
		display: none;
	}

	.journal-icon-button span img {
		margin-bottom: 3px;
	}

	.journal-article-instructions-message {
		margin: 5px 0 0 0;
	}

	.structure-controls {
		margin-top: 5px;
	}

	.structure-links {
		display: block;
		margin-top: 5px;

		a {
			margin-right: 10px;
		}
	}

	.default-link {
		font-size: 0.9em;
		font-weight: normal;
	}

	.journal-image-preview {
		border: 1px dotted;
		margin-top: 2px;
		overflow: scroll;
		padding: 4px;
		width: 500px;
	}

	.repeatable-field-image {
		cursor: pointer;
		position: absolute;
		right: 0;
		top: 0;
	}

	.lfr-textarea {
		width: 350px;
	}

	.localization-disabled {
		.journal-article-language-options, .structure-field .journal-article-localized-checkbox {
			display: none;
		}
	}

	.journal-article-buttons {
		display: none;
	}


}

.portlet-journal-edit-mode {
	.structure-tree {
		li {
			background: url(<%= themeImagesPath %>/journal/form_builder_bg.png);
			border: 1px #C6D9F0 solid;
			margin: 15px;
			padding: 10px 10px 10px 22px;

			&.structure-field {
				&.repeated-field {
					background: #F7FAFB;
					border: 1px dashed #C6D9F0;

					&.selected {
						border: 1px dashed #C3E7CC;
					}

					.journal-edit-field-control, .journal-delete-field {
						display: none;
					}
				}

				&.aui-dd-draggable .journal-article-move-handler {
					background: transparent url(<%= themeImagesPath %>/application/handle_sort_vertical.png) no-repeat scroll right 50%;
					cursor: move;
					display: block;
					height: 20px;
					left: 7px;
					position: absolute;
					top: 8px;
					width: 16px;
					z-index: 420;
				}

				.journal-article-close {
					background: url(<%= themeImagesPath %>/journal/form_builder_close.png);
					cursor: pointer;
					display: block;
					height: 29px;
					position: absolute;
					right: -10px;
					top: -9px;
					width: 29px;
					z-index: 420;
				}

				.journal-edit-field-control {
					display: block;
				}

				.journal-article-instructions-container {
					display: none;
				}
			}

			&.parent-structure-field {
				background: none;
				background-color: #FFFFE6;
				border: 1px dotted #FFE67F;
				padding-bottom: 30px;

				.journal-article-close, .journal-delete-field {
					display: none;
				}
			}
		}

		span.folder, span.file {
			display: block;
			padding: 10px;
			padding-top: 0;
		}

		.aui-placeholder, .aui-tree-placeholder, .aui-tree-sub-placeholder {
			-ms-filter: alpha(opacity=75);
			background: #fff;
			border: 1px #cdcdcd dashed;
			filter: alpha(opacity=75);
			height: 100px;
			opacity: 0.75;
		}

		li {
			border-top: 1px solid #CCC;
			margin: 10px;
			padding-top: 5px;
			position: relative;
		}
	}

	.journal-article-buttons {
		display: block;
		height: 27px;
		margin-top: 18px;
		text-align: right;

		.edit-button, .repeatable-button {
			float: left;
			margin-left: 3px;
		}
	}

	.journal-list-subfield .journal-icon-button {
		display: inline;
	}

	.portlet-journal .structure-field .journal-article-localized-checkbox {
		display: none;
	}
}

.ie {
	.journal-article-edit-field-wrapper form {
		width: auto;
	}

	.journal-article-helper {
		-ms-filter: alpha(opacity=80);

		&.not-intersecting .forbidden-action {
			right: 2px;
			top: 2px;
		}
	}

	.portlet-journal {
		.structure-tree li {
			zoom: 1;
		}
	}
}

.ie6, .ie7 {
	.journal-article-helper {
		filter: alpha(opacity=80);
	}
}

.journal-component {
	background: transparent url() no-repeat scroll 3px 3px;
}

.journal-component-text {
	background-image: url(<%= themeImagesPath %>/journal/text_field.png);
	background-position: 3px 9px;
}

.journal-component-textbox {
	background-image: url(<%= themeImagesPath %>/journal/textbox.png);
	background-position: 3px 6px;
}

.journal-component-textarea {
	background-image: url(<%= themeImagesPath %>/journal/textarea.png);
	background-position: 3px 4px;
}

.journal-component-image {
	background-image: url(<%= themeImagesPath %>/journal/image_uploader.png);
	background-position: 3px 7px;
}

.journal-component-imagegallery {
	background-image: url(<%= themeImagesPath %>/journal/image_gallery.png);
	background-position: 3px 5px;
}

.journal-component-documentlibrary {
	background-image: url(<%= themeImagesPath %>/journal/document_library.png);
}

.journal-component-boolean {
	background-image: url(<%= themeImagesPath %>/journal/checkbox.png);
	background-position: 3px 7px;
}

.journal-component-options {
	background-image: url(<%= themeImagesPath %>/journal/options.png);
	background-position: 3px 5px;
}

.journal-component-list {
	background-image: url(<%= themeImagesPath %>/journal/selectbox.png);
	background-position: 3px 9px;
}

.journal-component-multilist {
	background-image: url(<%= themeImagesPath %>/journal/multiselection_list.png);
	background-position: 3px 4px;
}

.journal-component-linktolayout {
	background-image: url(<%= themeImagesPath %>/journal/link_to_page.png);
	background-position: 3px 9px;
}

.journal-component-formgroup {
	background-image: url(<%= themeImagesPath %>/journal/form_group.png);
	background-position: 3px 5px;
}

.journal-component-selectionbreak {
	background-image: url(<%= themeImagesPath %>/journal/selection_break.png);
	background-position: 3px 12px;
}

.component-group {
	&.form-controls {
		border-top: 1px solid #E0ECFF;
	}

	.component-dragging {
		background-color: #fff !important;
	}
}

.journal-article-helper {
	background: #dedede;
	border: 1px #555 dashed;
	cursor: move;
	opacity: 0.8;
	position: absolute;
	visibility: hidden;
	width: 100px;

	&.aui-draggable-dragging {
		font-size: 15px;
	}

	&.not-intersecting .forbidden-action {
		background: url(<%= themeImagesPath %>/application/forbidden_action.png) no-repeat;
		height: 32px;
		position: absolute;
		right: -15px;
		top: -15px;
		width: 32px;
	}

	.journal-component {
		height: 25px;
		line-height: 25px;
		margin-left: 5px;
		padding-left: 25px;
	}
}

.journal-article-edit-field-wrapper {
	&.aui-overlaycontextpanel {
		margin: 0 13px 0 0;
		padding: 0;
		position: relative;
	}

	&.aui-overlaycontextpanel-container, .aui-overlaycontextpanel-container {
		background-color: #EBFFEE;
		border-color: #C3E7CC;
	}

	&.aui-overlaycontextpanel-arrow-tl .aui-overlaycontextpanel-pointer-inner {
		border-bottom: 10px solid #EBFFEE;
	}

	.container-close {
		display: none;
	}
}

.journal-article-edit-field {
	padding: 5px;
	position: relative;
	width: 180px;
	z-index: 420;

	.aui-field {
		padding: 0;

		.textarea {
			height: 6em;
		}
	}

	strong {
		font-size: 14px;
		text-decoration: underline;
	}

	.journal-edit-label {
		margin-top: 10px;
	}
}

.journal-article-edit-field-wrapper {
	.cancel-button, .save-button {
		display: none;
	}

	&.save-mode {
		.close-button {
			display: none;
		}

		.save-button, .cancel-button {
			display: inline;
		}
	}

	.user-instructions {
		border-width: 0;
		padding: 0;
		margin-bottom: 1em;
	}

	.button-holder {
		margin-top: 1.5em;
	}
}

.structure-message {
	margin-top: 5px;
}

.structure-name-label {
	font-weight: bold;
}

.save-structure-template-dialog textarea {
	height: 150px;
	width: 450px;
}

.save-structure-name {
	width: 470px;
}

.save-structure-description {
	height: 150px;
	width: 470px;
}

.journal-template-error {
	.scroll-pane {
		border: 1px solid #BFBFBF;
		max-height: 200px;
		min-height: 50px;
		overflow: auto;
		width: 100%;

		.inner-scroll-pane {
			min-width: 104%;
		}

		.error-line {
			background: #fdd;
		}

		pre {
			margin: 0;
			white-space: pre;

			span {
				background: #B5BFC4;
				border-right: 1px solid #BFBFBF;
				display: block;
				float: left;
				margin-right: 4px;
				padding-right: 4px;
				text-align: right;
				width: 40px;
			}
		}
	}
}