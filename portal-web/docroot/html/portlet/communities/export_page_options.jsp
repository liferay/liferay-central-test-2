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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Layout curLayout = (Layout)row.getObject();
%>

<div class="layout">

	<%
	String taglibHref = "javascript:Liferay.LayoutExporter.details({toggle: '#" + renderResponse.getNamespace() + "_detail_" + curLayout.getPlid() + "_toggle img', detail: '#_detail_" + curLayout.getPlid() + "'});";
	%>

	<em class='<%= curLayout.getAncestors().isEmpty() ? "aui-helper-hidden" : StringPool.BLANK %>' id="<portlet:namespace /><%= curLayout.getPlid() %>includeAncestor"><liferay-ui:message key="include-ancestor-pages" /></em>

	<em class="aui-helper-hidden" id="<portlet:namespace /><%= curLayout.getPlid() %>deleteLivePage"><liferay-ui:message key="delete-live-page" /></em>

	<em class='<%= curLayout.getChildren().isEmpty() ? "aui-helper-hidden" : StringPool.BLANK %>' id="<portlet:namespace /><%= curLayout.getPlid() %>includeChildren"><liferay-ui:message key="include-all-descendent-pages" /></em>

	<liferay-ui:icon cssClass="nobr" id='<%= "_detail_" + curLayout.getPlid() + "_toggle" %>' image="../arrows/01_plus" label="<%= true %>" message="change" target="_self"  toolTip="options" url="<%= taglibHref %>" />
</div>

<div class="aui-helper-hidden export-layout-detail" id="_detail_<%= curLayout.getPlid() %>" style="border-top: 1px solid #CCC; margin-top: 4px; padding-top: 4px; width: 95%;">
	<c:if test="<%= !curLayout.getAncestors().isEmpty() %>">
		<aui:input checked="<%= true %>" disabled="<%= true %>" label="include-ancestor-pages" name='<%= "includeAncestors_" + curLayout.getPlid() %>' type="checkbox" value="1" />
	</c:if>

	<aui:input label="delete-live-page" name='<%= "delete_" + curLayout.getPlid() %>' type="checkbox" />

	<c:if test="<%= !curLayout.getChildren().isEmpty() %>">
		<aui:input label="include-all-descendent-pages" name='<%= "includeChildren_" + curLayout.getPlid() %>' type="checkbox" value="1" />
	</c:if>
</div>

<aui:script use="aui-base">
	<c:if test="<%= !curLayout.getAncestors().isEmpty() %>">
		A.one('#<portlet:namespace />includeAncestors_<%= curLayout.getPlid() %>Checkbox').on(
			'change', function (event) {
				A.one('#<portlet:namespace /><%= curLayout.getPlid() %>includeAncestor').toggle();
			}
		);
	</c:if>

	A.one('#<portlet:namespace />delete_<%= curLayout.getPlid() %>Checkbox').on(
		'change', function (event) {
			A.one('#<portlet:namespace /><%= curLayout.getPlid() %>deleteLivePage').toggle();
		}
	);

	<c:if test="<%= !curLayout.getChildren().isEmpty() %>">
		A.one('#<portlet:namespace />includeChildren_<%= curLayout.getPlid() %>Checkbox').on(
			'change', function (event) {
				A.one('#<portlet:namespace /><%= curLayout.getPlid() %>includeChildren').toggle();
			}
		);
	</c:if>
</aui:script>