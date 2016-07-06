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

<%@ include file="/asset_tags_selector/init.jsp" %>

<%
String addCallback = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:addCallback"));
boolean allowAddEntry = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-selector:allowAddEntry"));
boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-selector:autoFocus"));
String className = (String)request.getAttribute("liferay-asset:asset-tags-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-tags-selector:classPK"));
String curTags = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:curTags"));
long[] groupIds = (long[])request.getAttribute("liferay-asset:asset-tags-selector:groupIds");
String hiddenInput = (String)request.getAttribute("liferay-asset:asset-tags-selector:hiddenInput");
String id = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean(request.getAttribute("liferay-asset:asset-tags-selector:ignoreRequestValue"));
String removeCallback = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-selector:removeCallback"));

if (Validator.isNotNull(className) && (classPK > 0)) {
	List<AssetTag> tags = AssetTagServiceUtil.getTags(className, classPK);

	curTags = ListUtil.toString(tags, AssetTag.NAME_ACCESSOR);
}

if (!ignoreRequestValue) {
	String curTagsParam = request.getParameter(hiddenInput);

	if (Validator.isNotNull(curTagsParam)) {
		curTags = curTagsParam;
	}
}
%>

<div class="lfr-tags-selector-content" id="<portlet:namespace /><%= id %>assetTagsSelector">
	<aui:input name="<%= hiddenInput %>" type="hidden" />

	<c:if test="<%= allowAddEntry %>">
		<input class="form-control lfr-tag-selector-input" id="<%= id %>assetTagNames" maxlength="<%= ModelHintsConstants.TEXT_MAX_LENGTH %>" size="15" title="<liferay-ui:message key="add-tags" />" type="text" />
	</c:if>
</div>

<aui:script use="liferay-asset-taglib-tags-selector">
	var assetTaglibTagsSelector = new Liferay.AssetTaglibTagsSelector(
		{
			allowAddEntry: <%= allowAddEntry %>,
			contentBox: '#<portlet:namespace /><%= id %>assetTagsSelector',
			curEntries: '<%= HtmlUtil.escapeJS(curTags) %>',

			<c:if test="<%= groupIds != null %>">
				groupIds: '<%= StringUtil.merge(groupIds) %>',
			</c:if>

			hiddenInput: '#<portlet:namespace /><%= hiddenInput %>',

			<c:if test="<%= allowAddEntry %>">
				input: '#<%= id %>assetTagNames',
			</c:if>

			<%
			String portletId = PortletProviderUtil.getPortletId(AssetTag.class.getName(), PortletProvider.Action.BROWSE);
			%>

			<c:if test="<%= Validator.isNotNull(portletId) %>">
				namespace: '<%= PortalUtil.getPortletNamespace(portletId) %>',
			</c:if>

			<%
			PortletURL portletURL = PortletProviderUtil.getPortletURL(request, AssetTag.class.getName(), PortletProvider.Action.BROWSE);
			%>

			<c:if test="<%= portletURL != null %>">

				<%
				portletURL.setWindowState(LiferayWindowState.POP_UP);
				%>

				portletURL: '<%= portletURL.toString() %>'
			</c:if>
		}
	).render();

	Liferay.component('<portlet:namespace />tagsSelector', assetTaglibTagsSelector);

	<c:if test="<%= Validator.isNotNull(addCallback) %>">
		assetTaglibTagsSelector.entries.on(
			'add',
			function(event) {
				window['<portlet:namespace /><%= addCallback %>'](event.item);
			}
		);
	</c:if>

	<c:if test="<%= Validator.isNotNull(removeCallback) %>">
		assetTaglibTagsSelector.entries.on(
			'remove',
			function(event) {
				window['<portlet:namespace /><%= removeCallback %>'](event.item);
			}
		);
	</c:if>

	<c:if test="<%= autoFocus %>">
		Liferay.Util.focusFormField('#<%= id %>assetTagNames');
	</c:if>
</aui:script>