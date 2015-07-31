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

<%@ include file="/deletions/init.jsp" %>

<c:if test="<%= cmd.equals(Constants.PUBLISH) || cmd.equals(Constants.IMPORT) %>">
	<aui:fieldset cssClass="options-group" label="deletions">
		<aui:input disabled="<%= disableInputs %>" label="delete-portlet-data-before-importing" name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" />

		<div class="alert alert-warning" id="<portlet:namespace />showDeleteContentWarning">
			<liferay-ui:message key="delete-content-before-importing-warning" />

			<liferay-ui:message key="delete-content-before-importing-suggestion" />
		</div>

		<aui:script>
			Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>', '<portlet:namespace />showDeleteContentWarning');
		</aui:script>

		<c:if test="<%= !cmd.equals(Constants.IMPORT) %>">
			<aui:input disabled="<%= disableInputs %>" helpMessage="deletions-help" label="replicate-individual-deletions" name="<%= PortletDataHandlerKeys.DELETIONS %>" type="checkbox" />
		</c:if>
	</aui:fieldset>
</c:if>