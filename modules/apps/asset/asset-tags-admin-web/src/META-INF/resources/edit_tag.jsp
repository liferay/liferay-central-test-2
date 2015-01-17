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
String redirect = ParamUtil.getString(renderRequest, "redirect");

long tagId = ParamUtil.getLong(request, "tagId");

AssetTag tag = AssetTagLocalServiceUtil.fetchAssetTag(tagId);
%>

<liferay-ui:header
	title='<%= (tag != null) ? tag.getName() : "add-tag" %>'
/>

<portlet:actionURL name="editTag" var="editTagURL" />

<aui:form action="<%= editTagURL %>" method="post" name="fm">
	<aui:input name="mvcPath" type="hidden" value="/edit_tag.jsp" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= AssetTagException.class %>">

		<%
		AssetTagException ate = (AssetTagException)errorException;
		%>

		<c:if test="<%= ate.getType() == AssetTagException.INVALID_CHARACTER %>">
			<liferay-ui:message key="please-enter-a-valid-name" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DuplicateTagException.class %>" message="a-tag-with-that-name-already-exists" />

	<aui:model-context bean="<%= tag %>" model="<%= AssetTag.class %>" />

	<aui:fieldset>
		<div>
			<div class="add-tag-layer asset-tag-layer">
				<aui:input name="tagId" type="hidden" value="<%= tagId %>" />

				<aui:input autoFocus="<%= true %>" cssClass="tag-name" name="name" />

				<aui:button-row>
					<aui:button type="submit" />

					<aui:button href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</div>
		</div>
	</aui:fieldset>
</aui:form>