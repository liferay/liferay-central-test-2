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

.portlet-staging-bar {
	.js .controls-hidden .staging-bar {
		display: none;
	}

	.tab-container {
		position: relative;
		margin-left: 5px;
	}

	.staging-bar {
		background-color: #777;

		.staging-tabs {
			background-color: #DDD;
			overflow: hidden;
			padding-top: 1em;

			.tab {
				background-color: #EEE;
				border: 1px solid #999;
				border-bottom: none;
				font-size: 1.5em;
				margin: 0.5em 0 0;
				min-width: 8em;
				padding: 10px 30px 5px 10px;
				position: relative;
				top: 2px;

				-moz-border-radius-topleft: 4px;
				-moz-border-radius-topright: 4px;
				-webkit-border-top-left-radius: 4px;
				-webkit-border-top-right-radius: 4px;
				border-top-left-radius: 4px;
				border-top-right-radius: 4px;

				&.selected {
					background-color: #777;
					border-right-color: #999;
					border-top-color: #DDD;
					border-left-color: #EEE;
					color: #FFF;
					font-weight: bold;
				}
			}
		}

		.staging-tabs-content {
			border-bottom: 1px solid #636364;
			color: #EEE;
			padding: 1em;

			.staging-icon {
				float: left;
				margin-right: 1em;
			}

			.layout-title {
				font-size: 1.3em;
			}

			.layout-revision-id {
				font-size: 0.6em;
			}

			.last-publication-variation-details {
				font-size: 0.8em;

				.layout-version {
					background: url(<%= themeImagesPath %>/common/pages.png) no-repeat 0 50%;
					padding: 2px 0 2px 20px;
				}

				.variation-name {
					background: url(<%= themeImagesPath %>/common/signal_instance.png) no-repeat 0 50%;
					margin-right: 10px;
					padding: 2px 0 2px 20px;
				}
			}

			.taglib-workflow-status {
				clear: left;
				color: #FFF;
				float: left;
				margin-right: 5em;

				.workflow-status, .workflow-version {
					color: #FFF;
				}

				.workflow-status-approved {
					color: #9F3;
				}

				.workflow-status-draft {
					color: #B8E9FF;
				}

				.workflow-status-expired {
					color: #FF8E8E;
				}
			}

			.last-publication-branch, .staging-live-group-name {
				display: block;
				font-size: 1.3em;
				padding: 5px 0 0;
			}
		}
	}
}