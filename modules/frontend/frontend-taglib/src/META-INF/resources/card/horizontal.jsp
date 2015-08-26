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

<%@ include file="/card/init.jsp" %>

<div class="<%= cssClass %>">
	<div class="card-horizontal">
		<div class="card-row card-row-padded <%= showCheckbox ? "selectable" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %> >
			<div class="card-col-field">
				<c:choose>
					<c:when test="<%= (rowChecker != null) && (resultRow != null) %>">
						<%= rowChecker.getRowCheckBox(request, rowChecker.isChecked(resultRow.getObject()), rowChecker.isDisabled(resultRow.getObject()), resultRow.getPrimaryKey()) %>
					</c:when>
					<c:when test="<%= showCheckbox %>">
						<aui:input checked="<%= checkboxChecked %>" cssClass="<%= checkboxCSSClass %>" data="<%= checkboxData %>" disabled="<%= checkboxDisabled %>" id="<%= checkboxId %>" label="" name="<%= checkboxName %>" title='<%= LanguageUtil.format(request, "select-x", new Object[] {HtmlUtil.escape(title)}) %>' type="checkbox" useNamespace="<%= false %>" value="<%= checkboxValue %>" wrappedField="<%= true %>" />
					</c:when>
				</c:choose>
			</div>

			<div class="card-col-field">
				<span class="<%= imageCSSClass %> <%= imageUrl %>"></span>
			</div>

			<div class="card-col-content card-col-gutters">
				<h4>
					<aui:a href="<%= url %>" label="<%= HtmlUtil.escape(title) %>" />
				</h4>
			</div>

			<div class="card-col-content card-col-gutters">
				<liferay-util:include page="<%= actionJsp %>" servletContext="<%= actionJspServletContext %>" />
			</div>
		</div>
	</div>
</div>