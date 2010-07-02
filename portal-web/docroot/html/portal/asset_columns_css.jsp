<%@ include file="/html/portlet/css_init.jsp" %>

.asset-column-content {
	margin-right: 1em;
	padding: 0;
}

.asset-column-actions-content {
	background-color: #D7F1FF;
	overflow: visible;
}

.asset-column-actions .asset-summary {
	margin-bottom: 2em;
	overflow: hidden;
	text-align: center;
}

.asset-column-actions .asset-avatar img {
	margin: 0 auto;
	padding-right: 2em;
}

.asset-column-actions .asset-column-actions-content {
	border: 1px solid #88C5D9;
	padding: 0.7em;
}

.asset-column-details .asset-column-details-content {
	margin-right: 4em;
}

.asset-column-details .asset-categories .asset-category {
	color: #555;
}

.asset-column-details .asset-categories {
	color: #7D7D7D;
}

.asset-column-details .asset-description {
	color: #444;
	font-style: italic;
	margin: 0.5em auto 2em;
}

.asset-column-details .asset-field, .asset-column-actions .asset-field {
	clear: left;
	margin: 1em auto;
}

.asset-column-details .asset-icon {
	border-right: 1px solid #999;
	color: #999;
	float: left;
	margin-right: 10px;
	padding-left: 25px;
	padding-right: 10px;
}

.asset-column-details .asset-icon.last {
	border-width: 0;
}

.asset-column-details .asset-author {
	background: url(<%= themeImagesPath %>/portlet/edit_guest.png) no-repeat 0 50%;
}

.asset-column-details .asset-date {
	background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 50%;
	overflow: hidden;
}

.asset-column-details .asset-subfolders {
	background: url(<%= themeImagesPath %>/common/folder.png) no-repeat 0 50%;
}

.asset-column-details .asset-items {
	background: url(<%= themeImagesPath %>/common/page.png) no-repeat 0 50%;
	overflow: hidden;
}

.asset-column-details .asset-downloads {
	padding-left: 0px;
	overflow: hidden;
}

.asset-column-details .asset-metadata {
	clear: both;
	margin-bottom: 1.5em;
	padding-bottom: 1.5em;
}

.asset-column-details .asset-ratings {
	margin: 2em 0;
}

.asset-column-details .asset-tags .tag {
	color: #555;
}

.asset-column-details .asset-tags {
	color: #7D7D7D;
}

.asset-column-details .taglib-custom-attributes-list label, .asset-column-details .asset-field label {
	display: block;
	font-weight: bold;
}

.asset-column-details .taglib-custom-attributes-list {
	margin-bottom: 1em;
}

.asset-panels {
	clear: both;
	padding-top: 1em;
}

.asset-panels .lfr-panel, .asset-column-details .lfr-panel {
	clear: both;
	margin-bottom: 1.5em;
}

.asset-panels .lfr-panel.lfr-extended, .asset-column-details .lfr-panel.lfr-extended {
	margin-bottom: 0;
	border: 1px solid #CCC;
}

.asset-column-details .lfr-panel-container, .asset-panels .lfr-panel-container {
	border-width:0;
}