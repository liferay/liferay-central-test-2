<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

TrashRenderer trashRenderer = null;

if (row != null) {
	trashRenderer = (TrashRenderer)row.getObject();
}
else {
	trashRenderer = (TrashRenderer)request.getAttribute(WebKeys.TRASH_RENDERER);
}

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashRenderer.getClassName());
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= trashHandler.isMovable() %>">
		<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/view_container_model.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="className" value="<%= trashRenderer.getClassName() %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(trashRenderer.getClassPK()) %>" />
			<portlet:param name="containerModelClassName" value="<%= trashHandler.getRootContainerModelClassName() %>" />
			<portlet:param name="containerModelId" value="<%= String.valueOf(trashHandler.getRootContainerModelId(trashRenderer.getClassPK())) %>" />
			<portlet:param name="rootContainerModelMovable" value="<%= String.valueOf(trashHandler.isRootContainerModelMovable()) %>" />
		</portlet:renderURL>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
		%>

		<liferay-ui:icon
			iconCssClass="icon-undo"
			message="restore"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= trashHandler.isDeletable() %>">
		<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="className" value="<%= trashRenderer.getClassName() %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(trashRenderer.getClassPK()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteEntryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>