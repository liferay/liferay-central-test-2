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

<%@ include file="/document_library/init.jsp" %>

<ul class="sidebar-block tabular-list-group-unstyled">

	<%
	FileEntry fileEntry = (FileEntry)request.getAttribute("info_panel.jsp-fileEntry");

	int status = WorkflowConstants.STATUS_APPROVED;

	if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	List<FileVersion> fileVersions = fileEntry.getFileVersions(status);

	for (FileVersion fileVersion : fileVersions) {
		request.setAttribute("info_panel.jsp-fileVersion", fileVersion);
	%>

		<li class="list-group-item">
			<div class="list-group-item-content">
				<div class="h5">
					<liferay-ui:message arguments="<%= fileVersion.getVersion() %>" key="version-x" />
				</div>

				<c:choose>
					<c:when test="<%= Validator.isNull(fileVersion.getChangeLog()) %>">
						<div class="h6 sidebar-caption">
							<liferay-ui:message key="no-change-log" />
						</div>
					</c:when>
					<c:otherwise>
						<%= fileVersion.getChangeLog() %>
					</c:otherwise>
				</c:choose>

				<div class="h6">
					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(fileVersion.getUserName()), dateFormatDateTime.format(fileVersion.getCreateDate())} %>" key="by-x-on-x" translateArguments="<%= false %>" />
				</div>
			</div>

			<div class="list-group-item-field">
				<liferay-util:include page="/document_library/file_entry_history_action.jsp" servletContext="<%= application %>" />
			</div>
		</li>

	<%
	}
	%>

</ul>