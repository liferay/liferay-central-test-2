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

<%@ include file="/management_bar/init.jsp" %>

<c:if test="<%= Validator.isContent(bodyContentString) %>">
	<div class="management-bar management-bar-default <%= cssClass %>" id="<%= namespace + id %>">
		<div class="container-fluid-1280">
			<div class="management-bar-header">
				<c:if test="<%= includeCheckBox %>">
					<label class="checkbox-default">
						<aui:input cssClass="<%= checkBoxCssClass %>" inline="<%= true %>" label="" name="<%= RowChecker.ALL_ROW_IDS %>" title="select-all" type="checkbox" />
					</label>
				</c:if>
			</div>

			<%= bodyContentString %>
		</div>
	</div>
</c:if>